package scsi.demo.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;

import lombok.RequiredArgsConstructor;
import scsi.demo.model.CodeTab;
import scsi.demo.model.Link;
import scsi.demo.model.Nodes;
import scsi.demo.model.User;
import scsi.demo.repository.CodeTabRepository;
import scsi.demo.repository.LinkRepository;
import scsi.demo.repository.NodeRepository;
import scsi.demo.repository.PassRepository;
import scsi.demo.repository.UserRepository;
import scsi.demo.scsi.CaseData;
import scsi.demo.service.UserService;
import scsi.demo.wisoft.Data;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NodeRepository nodeRepository;

	@Autowired
	private LinkRepository linkRepository;

	@Autowired
	private CodeTabRepository codetabRepository;

	@Autowired
	private Producer producer;

	@Autowired
	private UserService userService;
	
	@Autowired
    public PasswordEncoder pwdEncoder;
	
	@Autowired
	public PassRepository passrepository;
	
	@GetMapping("/register")
	public String viewRegisterPage(Model model) {
		model.addAttribute("name", "註冊");
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping(value = {"/allo", "/scsi/allo"})
	public @ResponseBody List<Nodes> flist() {
		
		return nodeRepository.findAll();
	}

	@PostMapping(value = {"/newuser", "/scsi/newuser"})
	public @ResponseBody String create(User user, @RequestParam(value = "sysid", defaultValue = "0000") String sysid,
			@Param(value = "user_id") String user_id, @Param(value = "password") String password,
			@Param(value = "user_name") String user_name, @Param(value = "user_gr") String user_gr,CaseData cst) throws IOException, SQLException {
		int nid = userRepository.existsUser_id(user_id);
//			System.out.println("nid="+nid+"/nam="+nam);
		if (sysid.equals("0000")) {
			if (nid < 1) {
				String pass=pwdEncoder.encode(password);
				userRepository.newuser(user_id, pass, user_name, user_gr);
//				passrepository.savepass(user_id, pass, cst.todaytime());
				return "OK";
			} else {
				return "exist";
			}
		} else {
			userRepository.adduser(sysid, user_id, user_name, user_gr);
			return "OK";
		}
	}

	@RequestMapping(path = "/login?error")
	public String login(CaseData cst,@ModelAttribute("userid") String userid) throws IOException, SQLException {
		userRepository.logs(cst.todaytime2(),userid,cst.todaytime(),"login","登入失敗");
		return "/";
	}

	@RequestMapping(value = {"/main", "/scsi/main"})
	public ModelAndView main(@ModelAttribute("userid") String userid,CaseData cst) throws SQLException, IOException {
		ModelAndView model = new ModelAndView("main");
		String gr="";
		gr=userRepository.getgr(userid);
		System.out.println("usergr=" + gr);
		model.addObject("userid", userid);
		System.out.println("userid=" + userid);
		userRepository.logs(cst.todaytime2(),userid,cst.todaytime(),"login","登入成功");
		return model;
	}

	@RequestMapping(value = {"/upfile", "/scsi/upfile"})
	public ModelAndView upfile(@ModelAttribute("userid") String userid) {
		ModelAndView model = new ModelAndView("upfile");
		model.addObject("userid", userid);
		return model;
	}

	@RequestMapping(value = {"/", "/login"})
	public String welcome() {
		return "login";
	}

	@RequestMapping(value = {"/user_list", "/scsi/user_list"})
	public ModelAndView userlist(@ModelAttribute("userid")String user_id) {
		ModelAndView model = new ModelAndView("user_list");
		String gr="";
		gr=userRepository.getgr(user_id);
		if(gr.equals("admin")) {
		Iterable<User> allUserList = userRepository.findAll();
		Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
		model.addObject("userlist", allUserList);
		model.addObject("ownerlist", allOwnerList);
		return model;
		}else {
			Iterable<User> allUserList = userRepository.findAllgr(gr);
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwnergr(gr);
			model.addObject("userlist", allUserList);
			model.addObject("ownerlist", allOwnerList);
			return model;
		}
	}

	/**
	 * 生成验证码
	 */
	@GetMapping(value = {"/verify-code", "/scsi/verify-code"})
	public void getVerifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setContentType("image/jpeg");
		// 生成图形校验码内容
		String text = producer.createText();
		// 将验证码内容存入HttpSession
		session.setAttribute("verify_code", text);
		// 生成图形校验码图片
		BufferedImage image = producer.createImage(text);
		// 使用try-with-resources 方式，可以自动关闭流
		try (ServletOutputStream out = resp.getOutputStream()) {
			// 将校验码图片信息输出到浏览器
			ImageIO.write(image, "jpeg", out);
		}
	}

	@GetMapping(value = {"/mody_user", "/scsi/mody_user"})
	public ModelAndView modynode(@ModelAttribute("sysid") Integer sysid, @ModelAttribute("userid") String userid) {
//		System.out.println("modynode="+sysid);
		ModelAndView model = new ModelAndView("mody_user");
		String gr="";
		gr=userRepository.getgr(userid);
		
		if(gr.equals("admin")) {
		Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
		model.addObject("ownerlist", allOwnerList);
		model.addObject("sysid", sysid);
		model.addObject("userid", userRepository.findByUsersysid(sysid).getUserid());
		model.addObject("username", userRepository.findByUsersysid(sysid).getUsername());
		model.addObject("usergr", userRepository.findByUsersysid(sysid).getUser_gr());
		if (userid.equals("admin")) {
			model.addObject("usergr", userid);
		}
		return model;
		}else {
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwnergr(gr);
			model.addObject("ownerlist", allOwnerList);
			model.addObject("sysid", sysid);
			model.addObject("userid", userRepository.findByUsersysid(sysid).getUserid());
			model.addObject("username", userRepository.findByUsersysid(sysid).getUsername());
			model.addObject("usergr", userRepository.findByUsersysid(sysid).getUser_gr());
			if (userid.equals("admin")) {
				model.addObject("usergr", userid);
			}
			return model;
		}
	}

	@GetMapping(value = {"/passwords", "/scsi/passwords"})
	public ModelAndView passwords(@ModelAttribute("sysid") Integer sysid, @ModelAttribute("user_id") String userid) {
//		System.out.println("modynode="+sysid);
		ModelAndView model = new ModelAndView("passwords");
		model.addObject("sysid", userRepository.findByUsersysid(sysid).getUsersysid());
		model.addObject("userid", userRepository.findByUsersysid(sysid).getUserid());
		model.addObject("username", userRepository.findByUsersysid(sysid).getUsername());
		model.addObject("password", userRepository.findByUsersysid(sysid).getPassword());
		if (userid.equals("admin")) {
			model.addObject("usergr", userid);
		}

		return model;
	}

	@PostMapping(value = {"/deluser", "/scsi/deluser"})
	public @ResponseBody String delnode(@ModelAttribute("sysid") Integer sysid) {
		ModelAndView model = new ModelAndView("user_list");
//		System.out.println("delnode="+sysid);
		userRepository.deluser(sysid);
		return "OK";
	}
	
	@PostMapping(value = {"/resetf", "/scsi/resetf"})
	public @ResponseBody String resetf(@ModelAttribute("userid") String userid) {
		ModelAndView model = new ModelAndView("node_list");
		System.out.println("resetf="+userid);
		nodeRepository.resetN();
		linkRepository.resetL();
		return "OK";
	}
	
	@PostMapping(value = {"/uppass", "/scsi/uppass"})
	public @ResponseBody String uppass(@ModelAttribute("sysid") Integer sysid, @ModelAttribute("userid") String userid,@ModelAttribute("password") String password,@ModelAttribute("old")String old,CaseData cst) throws IOException, SQLException {
		ModelAndView model = new ModelAndView("user_list");
		boolean pc =pwdEncoder.matches(old, userRepository.findByUsersysid(sysid).getPassword());
//		System.out.println("password="+old+"/old="+userRepository.findByUsersysid(sysid).getPassword()+"/PC="+pc);
		if(pc==true) {
		String pass=pwdEncoder.encode(password);
//		Integer up=passrepository.check3pass(pass);
//		if(up<1) {
		userRepository.uppass(sysid,pass);
//		passrepository.savepass(userid, pass, cst.todaytime());
		return "OK";
//		}else {
//			return "exist";
//		}
		}else {
			return "wrong";
		}
	}
	
	@RequestMapping(value = {"/logout", "/scsi/logout"})
	public @ResponseBody String logout(@ModelAttribute("sysid") Integer sysid, @ModelAttribute("userid") String userid,@ModelAttribute("password") String password,HttpServletRequest request) {
		ModelAndView model = new ModelAndView("user_list");
		request.getSession().removeAttribute(userid);
		return "redirect:/";
	}
	
	@GetMapping(value = {"/index1", "/scsi/index1"})
	public ModelAndView index1(@ModelAttribute("id") Integer id) {
//		System.out.println("modynode="+sysid);
		ModelAndView model = new ModelAndView("index1");
		model.addObject("getid", id);
		return model;
	}
	
	@PostMapping(value = {"/test2", "/scsi/test2"})
	public @ResponseBody String test2(HttpSession session) {
		ModelAndView model = new ModelAndView("test2");
		String getSessionID = session.getId();
		if(session.getAttribute("myid") == null){
			session.setAttribute("myid",getSessionID);
		}
		String thisCode = "none";
		if(getSessionID.equals((String)session.getAttribute("myid"))){

			//DataProcess dpc = new DataProcess();
			//thisCode = dpc.gencode(20);
			//session.setAttribute("mykey",thisCode);
			//dpc.closeall();
		}
		return(thisCode);
	}
}
