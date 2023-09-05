package scsi.demo.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class TxtController {

	
	@RequestMapping(value= {"/taiwanSMC/APCN2.txt","/scsi/taiwanSMC/APCN2.txt"})
	public @ResponseBody String APCN2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/APCN2.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/APG.txt","/scsi/taiwanSMC/APG.txt"})
	public @ResponseBody String APG() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/APG.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/EAC-C2C.txt","/scsi/taiwanSMC/EAC-C2C.txt"})
	public @ResponseBody String EAC_C2C() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/EAC-C2C.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/FASTER.txt","/scsi/taiwanSMC/FASTER.txt"})
	public @ResponseBody String FASTER() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/FASTER.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/FNAL-RNAL.txt","/scsi/taiwanSMC/FNAL-RNAL.txt"})
	public @ResponseBody String FNAL_RNAL() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/FNAL-RNAL.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/NCP.txt","/scsi/taiwanSMC/NCP.txt"})
	public @ResponseBody String NCP() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/NCP.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/PLCN.txt","/scsi/taiwanSMC/PLCN.txt"})
	public @ResponseBody String PLCN() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/PLCN.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/plcn1.txt","/scsi/taiwanSMC/plcn1.txt"})
	public @ResponseBody String plcn1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/plcn1.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/SMW3.txt","/scsi/taiwanSMC/SMW3.txt"})
	public @ResponseBody String SMW3() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/SMW3.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/TPE.txt","/scsi/taiwanSMC/TPE.txt"})
	public @ResponseBody String TPE() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/TPE.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/taiwanSMC/TSE1.txt","/scsi/taiwanSMC/TSE1.txt"})
	public @ResponseBody String TSE1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("taiwanSMC/TSE1.txt");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
}
