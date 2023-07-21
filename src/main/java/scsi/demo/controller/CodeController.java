package scsi.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

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
import scsi.demo.repository.CodeTabRepository;
import scsi.demo.repository.UserRepository;
import scsi.demo.scsi.CaseData;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class CodeController {

	@Autowired
	CodeTabRepository codetabRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = {"/edit_list", "/scsi/edit_list"})
	public ModelAndView edit(@ModelAttribute("userid") String userid, @ModelAttribute("gk") String gk,
			@ModelAttribute("ck") String ck) {
		if (gk.equals("2")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allCounList = codetabRepository.findAllCountryWT();
			model.addObject("code_id", gk);
			model.addObject("gk", "國家代碼");
			model.addObject("counlist", allCounList);
			return model;
		} else if (gk.equals("3")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allNType = codetabRepository.findAllNType();
			model.addObject("code_id", gk);
			model.addObject("gk", "節點類型");
			model.addObject("counlist", allNType);
			return model;
		} else if (gk.equals("4")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
			model.addObject("code_id", gk);
			model.addObject("gk", "業者管理");
			model.addObject("counlist", allOwnerList);
			return model;
		} else if (gk.equals("5")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllLType();
			model.addObject("code_id", gk);
			model.addObject("gk", "路由類型");
			model.addObject("counlist", allOwnerList);
			return model;
		} else if (gk.equals("6")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllLFail();
			model.addObject("code_id", gk);
			model.addObject("gk", "路由狀態");
			model.addObject("counlist", allOwnerList);
			return model;
		} else if (gk.equals("7")) {
			ModelAndView model = new ModelAndView("edit_list");
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllNFail();
			model.addObject("code_id", gk);
			model.addObject("gk", "節點狀態");
			model.addObject("counlist", allOwnerList);
			return model;
		} else {
			return null;
		}
	}

	@RequestMapping(value = {"/newedit", "/scsi/newedit"})
	public @ResponseBody String editf(@RequestParam(value = "seq", defaultValue = "0000") String seq,
			@Param(value = "code_id") Integer code_id, @Param(value = "name_zh") String name_zh,
			@Param(value = "name_desc_zh") String name_desc_zh,CaseData cst,
			@ModelAttribute("userid")String user_id) throws IOException, SQLException {
		if (seq.equals("0000")) {
			codetabRepository.newedit(code_id, name_zh, name_desc_zh);
			userRepository.logs(cst.todaytime2(), user_id, cst.todaytime(), "new code", "code_id="+code_id+"/name_zh="+name_zh+"/name_desc_zh="+name_desc_zh);
			return "OK";
		} else {
			codetabRepository.addedit(seq, code_id, name_zh, name_desc_zh);
			userRepository.logs(cst.todaytime2(), user_id, cst.todaytime(), "mody code", "seq="+seq+"/code_id="+code_id+"/name_zh="+name_zh+"/name_desc_zh="+name_desc_zh);
			return "OK";
		}
	}

	@GetMapping(value = {"/mody_edit", "/scsi/mody_edit"})
	public ModelAndView modyedit(@ModelAttribute("seq") String seq, @Param(value = "code_id") Integer code_id) {
//		System.out.println("modynode="+sysid);
		ModelAndView model = new ModelAndView("mody_edit");

		Iterable<CodeTab> allCounList = codetabRepository.findAllCountryWT();
		model.addObject("counlist", allCounList);
		model.addObject("seq", codetabRepository.findByseq(seq).getSeq());
		model.addObject("code_id", codetabRepository.findByseq(seq).getCode_id());
		model.addObject("name_zh", codetabRepository.findByseq(seq).getName_zh());
		model.addObject("name_desc_zh", codetabRepository.findByseq(seq).getName_desc_zh());

		return model;
	}

	@PostMapping(value = {"/deledit", "/scsi/deledit"})
	public @ResponseBody String deledit(@ModelAttribute("seq") String seq) {
		ModelAndView model = new ModelAndView("del_edit");
//		System.out.println("delnode="+sysid);
		codetabRepository.deledit(seq);
		return "OK";
	}

}
