package scsi.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import scsi.demo.model.CodeTab;
import scsi.demo.model.Link;
import scsi.demo.model.Nodes;
import scsi.demo.repository.CodeTabRepository;
import scsi.demo.repository.LinkRepository;
import scsi.demo.repository.NodeRepository;
import scsi.demo.repository.UserRepository;
import scsi.demo.scsi.CaseData;
@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class LinkController {
	
	
	@Autowired
	private LinkRepository linkRepository;
	
	@Autowired
	private CodeTabRepository codetabRepository;
	
	@Autowired
	private NodeRepository nodeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value= {"/link_list","/scsi/link_list"})
	public ModelAndView linklist(@ModelAttribute("userid") String userid,@ModelAttribute("gk") String gk ) {
		ModelAndView model = new ModelAndView("link_list");
//		System.out.println("userid_link="+userid+"/gk="+gk);
		String gr="";
		gr=userRepository.getgr(userid);
		model.addObject("userid", userid);
		if(gr.equals("admin")) {
		Iterable<Link> allLinkList = linkRepository.findAll();
		if(!gk.equals("")) {
			allLinkList = linkRepository.findLinkKey(gk);
		}
		Iterable<CodeTab> allTypeList = codetabRepository.findAllLType();
		Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
		Iterable<Nodes> allNodeAList = nodeRepository.findAll();
		Iterable<CodeTab> allFailList = codetabRepository.findAllLFail();
		
		model.addObject("linklist", allLinkList);
		model.addObject("ownerlist", allOwnerList);
		model.addObject("typelist", allTypeList);
		model.addObject("nodeAlist", allNodeAList);
		model.addObject("faillist", allFailList);
		return model;
		}else {
			Iterable<Link> allLinkList = linkRepository.findAllgr(gr);
			if(!gk.equals("")) {
				allLinkList = linkRepository.findLinkKeygr(gk,gr);
			}
			Iterable<CodeTab> allTypeList = codetabRepository.findAllLType();
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwnergr(gr);
			Iterable<Nodes> allNodeAList = nodeRepository.findAllgr(gr);
			Iterable<CodeTab> allFailList = codetabRepository.findAllLFail();
			
			model.addObject("linklist", allLinkList);
			model.addObject("ownerlist", allOwnerList);
			model.addObject("typelist", allTypeList);
			model.addObject("nodeAlist", allNodeAList);
			model.addObject("faillist", allFailList);
			return model;
		}
	}
	
	@PostMapping(value= {"/newlink","/scsi/newlink"})
	public @ResponseBody String create(@ModelAttribute("userid") String user_id,Link link,@RequestParam(value="sysid",defaultValue="0000") String sysid,@Param(value = "link_id")String link_id,@Param(value = "link_name")String link_name,@Param(value = "link_type")String link_type,@Param(value = "link_owner")String link_owner,@Param(value = "link_nodeA")String link_nodeA,@Param(value = "link_nodeB")String link_nodeB,@Param(value = "max_bandwidth")String max_bandwidth,@Param(value = "using_bandwidth")String using_bandwidth,@Param(value = "rest_bandwidth")String rest_bandwidth,@Param(value = "failure")String failure,@Param(value = "failure_bandwidth")String failure_bandwidth,@Param(value = "w_length")Float w_length,@Param(value = "w_latency")Float w_latency,@Param(value = "w_weight")Float w_weight,CaseData cst) throws IOException, SQLException {
		int lid= linkRepository.existsLink_id(link_id);
		int lam =linkRepository.existsLink_name(link_name);
//		System.out.println("nid="+nid+"/nam="+nam);
		if(sysid.equals("0000")) {
			if(lid<1 && lam<1) {
        linkRepository.save(link);
        userRepository.logs(cst.todaytime2(),user_id,cst.todaytime(),"new link","link_id="+link_id+"/name="+link_name+"/link_type="+link_type+"/link_owner="+link_owner+"/link_nodeA="+link_nodeA+"/link_nodeB="+link_nodeB+"/max_bandwidth="+max_bandwidth+"/using_bandwidth="+using_bandwidth+"/rest_bandwidth="+rest_bandwidth+"/failure="+failure+"/failure_bandwidth="+failure_bandwidth+"/w_length="+w_length+"/w_latency="+w_latency+"/w_weight="+w_weight);
        return "OK";
			}else {
				return "exist";
			}
		}else {
		linkRepository.addlink(sysid, link_id, link_name,  link_type, link_owner, link_nodeA, link_nodeB, max_bandwidth, using_bandwidth, rest_bandwidth, failure, failure_bandwidth, w_length, w_latency, w_weight);	
		userRepository.logs(cst.todaytime2(),user_id,cst.todaytime(),"mody link","sysid="+sysid+"/link_id="+link_id+"/name="+link_name+"/link_type="+link_type+"/link_owner="+link_owner+"/link_nodeA="+link_nodeA+"/link_nodeB="+link_nodeB+"/max_bandwidth="+max_bandwidth+"/using_bandwidth="+using_bandwidth+"/rest_bandwidth="+rest_bandwidth+"/failure="+failure+"/failure_bandwidth="+failure_bandwidth+"/w_length="+w_length+"/w_latency="+w_latency+"/w_weight="+w_weight);
		return "OK";
		}
    }
	
	@GetMapping(value= {"/mody_link","/scsi/mody_link"})
	public ModelAndView modylink(@ModelAttribute("userid") String user_id,@ModelAttribute("sysid")String sysid)
	{
//		System.out.println("modylink="+sysid);
		ModelAndView model =new ModelAndView("mody_link");
		String gr="";
		gr=userRepository.getgr(user_id);
		Iterable<Nodes> node=nodeRepository.findAll();
		Iterable<Link> link=linkRepository.findBySysid(sysid);
		Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
		if(!gr.equals("admin")) {
			node=nodeRepository.findAllgr(gr);
			allOwnerList = codetabRepository.findAllOwnergr(gr);
		}
		Iterable<CodeTab> allTypeList = codetabRepository.findAllLType();
		Iterable<CodeTab> allFailList = codetabRepository.findAllLFail();
		model.addObject("nodelist", node);
		model.addObject("ownerlist", allOwnerList);
		model.addObject("typelist", allTypeList);
		model.addObject("faillist", allFailList);
		model.addObject("linklist",link);
		model.addObject("sysid",sysid);
		model.addObject("link_id",linkRepository.findBysysid(sysid).getLink_id());
		model.addObject("link_name",linkRepository.findBysysid(sysid).getLink_name());
		model.addObject("link_type",linkRepository.findBysysid(sysid).getLink_type());
		model.addObject("link_owner",linkRepository.findBysysid(sysid).getLink_owner());
		model.addObject("link_nodeA",linkRepository.findBysysid(sysid).getLink_nodeA());
		model.addObject("link_nodeB",linkRepository.findBysysid(sysid).getLink_nodeB());
		model.addObject("max_bandwidth",linkRepository.findBysysid(sysid).getMax_bandwidth());
		model.addObject("using_bandwidth",linkRepository.findBysysid(sysid).getUsing_bandwidth());
		model.addObject("rest_bandwidth",linkRepository.findBysysid(sysid).getRest_bandwidth());
		model.addObject("failure",linkRepository.findBysysid(sysid).getFailure());
		model.addObject("failure_bandwidth",linkRepository.findBysysid(sysid).getFailure_bandwidth());
		model.addObject("w_length",linkRepository.findBysysid(sysid).getW_length());
		model.addObject("w_latency",linkRepository.findBysysid(sysid).getW_latency());
		model.addObject("w_weight",linkRepository.findBysysid(sysid).getW_weight());
		
		return model;
	}
	
	@PostMapping(value= {"/dellink","/scsi/dellink"})
	public @ResponseBody String dellink(@ModelAttribute("userid") String user_id,@ModelAttribute("sysid") String sysid,CaseData cst) throws IOException, SQLException {
		ModelAndView model = new ModelAndView("link_list");
//		System.out.println("dellink="+sysid);
		linkRepository.dellink(sysid);
		userRepository.logs(cst.todaytime2(), user_id, cst.todaytime(), "del link", "sysid="+sysid);
		return "OK";
	}
}
