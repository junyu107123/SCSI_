package scsi.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import scsi.demo.repository.UserRepository;
import scsi.demo.wisoft.Data;
import scsi.demo.scsi.DataAPI;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class DataController {

	@Autowired
	public UserRepository UserRepository;

	@PostMapping(value = {"/DataAPI", "/scsi/DataAPI"})
	public @ResponseBody String DataAPI(@ModelAttribute("func") String myfunc,
			@ModelAttribute("scable") String get_scable, @ModelAttribute("loc") String get_loc, Data dta,DataAPI api) throws IOException, SQLException {
		ModelAndView model = new ModelAndView("DataAPI");
		System.out.println("myfunc=" + myfunc + "/get_scable=" + get_scable + "/get_loc=" + get_loc);
		String[] typevalue = {};
		String[] para = {};
		int goNext = 0;
		String feedback = "no";

		if (get_scable != null) {
			if (myfunc.equals("6") || myfunc.equals("7") || myfunc.equals("8") || myfunc.equals("13")) {
				goNext++;
			} else {
				get_scable = get_scable.matches("[A-Za-z0-9,-_%]+") ? get_scable : "";
				if (dta.cleanXSS(dta.cleanSQLInject(get_scable)).indexOf("forbid") < 0)
					goNext++;
			}
		} else {
			if (myfunc.equals("1"))
				goNext++;
		}

		if (get_loc != null) {
			get_loc = get_loc.matches("[A-Za-z0-9,-_%]+") ? get_loc : "";
			if (dta.cleanXSS(dta.cleanSQLInject(get_loc)).indexOf("forbid") < 0)
				goNext++;
		} else {
			if (myfunc.equals("1") || myfunc.equals("2") || myfunc.equals("5"))
				goNext++;
		}

		if(goNext == 2){
			if(myfunc.equals("1")){
				String[] typevaluex = {};
				String[] parax = {};
				typevalue = typevaluex;
				para = parax;
			}


			if(myfunc.equals("2")){
				String[] typevaluex = {"s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
				typevalue = typevaluex;
				para = parax;
			}

			if(myfunc.equals("3")){
				String[] typevaluex = {"s","s","s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim()};
				typevalue = typevaluex;
				para = parax;
			}


			if(myfunc.equals("4")){
				String[] typevaluex = {"s","s"};
				if(get_scable.indexOf("EAC")>=0) get_scable="EAC";
				if(get_scable.indexOf("NAL")>=0) get_scable="FNALRNAL";
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().replaceAll("-",""),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim()};
				typevalue = typevaluex;
				para = parax;
			}
			
			if(myfunc.equals("5")){
				String[] typevaluex = {"s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("6")){
				String[] typevaluex = {"s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("7")){
				String[] typevaluex = {"s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("8")){
				String[] typevaluex = {};
				String[] parax = {};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("9")){
				String[] typevaluex = {"i","i"};
				String[] parax = dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().split("-");
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("10")){
				String[] typevaluex = {"i","i"};
				String[] parax = dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().split("-");
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("11")){
				String[] typevaluex = {};
				String[] parax = {};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("12")){
				String[] typevaluex = {"s","s"};
				String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
				typevalue = typevaluex;
				para = parax;
			}
			if(myfunc.equals("13")){
				String[] typevaluex = {};
				String[] parax = {};
				typevalue = typevaluex;
				para = parax;
			}
		}
			System.out.println("myfunc="+myfunc+"/typevalue="+typevalue+"/para="+para);
			feedback = api.getNewData(myfunc,typevalue,para).trim();
			System.out.println("feedback="+feedback);
//		return "redirect:/getNewData?myfunc="+myfunc+"&typevalue="+typevalue+"&para="+para;
		return "OK";	
	}
	
//	@RequestMapping(value = {"/getNewData", "/scsi/getNewData"})
//	public @ResponseBody String getNewData(@ModelAttribute("myfunc") String myfunc,
//			@ModelAttribute("typevalue") String[] typevalue, @ModelAttribute("para") String[] para) throws IOException, SQLException {
//		System.out.println("getnewdata myfunc="+myfunc+"/typevalue"+typevalue+"/para"+para);
//		
//				return "OK";
//	}
}
