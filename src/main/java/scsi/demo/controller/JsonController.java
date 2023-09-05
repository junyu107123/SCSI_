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
public class JsonController {
	
	@RequestMapping(value= {"/used_scable/TK2.json","/scsi/used_scable/TK2.json"})
	public @ResponseBody String TK2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TK2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/APCN2.json","/scsi/used_scable/APCN2.json"})
	public @ResponseBody String APCN2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/APCN2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/APG.json","/scsi/used_scable/APG.json"})
	public @ResponseBody String APG() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/APG.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/C2C.json","/scsi/used_scable/C2C.json"})
	public @ResponseBody String C2C() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/C2C.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/CSCN.json","/scsi/used_scable/CSCN.json"})
	public @ResponseBody String CSCN() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/CSCN.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/DX.json","/scsi/used_scable/DX.json"})
	public @ResponseBody String DX() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/DX.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/EAC.json","/scsi/used_scable/EAC.json"})
	public @ResponseBody String EAC() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/EAC.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/EAC1.json","/scsi/used_scable/EAC1.json"})
	public @ResponseBody String EAC1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/EAC1.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/EAC2.json","/scsi/used_scable/EAC2.json"})
	public @ResponseBody String EAC2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/EAC2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/FASTER_landing.json","/scsi/used_scable/FASTER_landing.json"})
	public @ResponseBody String FASTER_landing() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/FASTER_landing.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/FASTER.json","/scsi/used_scable/FASTER.json"})
	public @ResponseBody String FASTER() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/FASTER.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/FNAL.json","/scsi/used_scable/FNAL.json"})
	public @ResponseBody String FNAL() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/FNAL.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/LAND_1.json","/scsi/used_scable/LAND_1.json"})
	public @ResponseBody String LAND_1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/LAND_1.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/NB2.json","/scsi/used_scable/NB2.json"})
	public @ResponseBody String NB2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/NB2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/NCP_org.json","/scsi/used_scable/NCP_org.json"})
	public @ResponseBody String NCP_org() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/NCP_org.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/NCP.json","/scsi/used_scable/NCP.json"})
	public @ResponseBody String NCP() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/NCP.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/PK1-A.json","/scsi/used_scable/PK1-A.json"})
	public @ResponseBody String PK1A() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/PK1-A.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/PK1-B.json","/scsi/used_scable/PK1-B.json"})
	public @ResponseBody String PK1B() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/PK1-B.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/PK3-A.json","/scsi/used_scable/PK3-A.json"})
	public @ResponseBody String PK3A() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/PK3-A.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/PK3-B.json","/scsi/used_scable/PK3-B.json"})
	public @ResponseBody String PK3B() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/PK3-B.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/PLCN.json","/scsi/used_scable/PLCN.json"})
	public @ResponseBody String PLCN() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/PLCN.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/RNAL.json","/scsi/used_scable/RNAL.json"})
	public @ResponseBody String RNAL() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/RNAL.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/SMW3.json","/scsi/used_scable/SMW3.json"})
	public @ResponseBody String SMW3() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/SMW3.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TDM2.json","/scsi/used_scable/TDM2.json"})
	public @ResponseBody String TDM2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TDM2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TL1.json","/scsi/used_scable/TL1.json"})
	public @ResponseBody String TL1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TL1.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TL2.json","/scsi/used_scable/TL2.json"})
	public @ResponseBody String TL2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TL2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM1-A.json","/scsi/used_scable/TM1-A.json"})
	public @ResponseBody String TM1A() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM1-A.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM1-B.json","/scsi/used_scable/TM1-B.json"})
	public @ResponseBody String TM1B() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM1-B.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM1-C.json","/scsi/used_scable/TM1-C.json"})
	public @ResponseBody String TM1C() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM1-C.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM1-D.json","/scsi/used_scable/TM1-D.json"})
	public @ResponseBody String TM1D() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM1-D.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM3-A.json","/scsi/used_scable/TM3-A.json"})
	public @ResponseBody String TM3A() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM3-A.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM3-B.json","/scsi/used_scable/TM3-B.json"})
	public @ResponseBody String TM3B() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM3-B.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM3-C.json","/scsi/used_scable/TM3-C.json"})
	public @ResponseBody String TM3C() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM3-C.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TM3-D.json","/scsi/used_scable/TM3-D.json"})
	public @ResponseBody String TM3D() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TM3-D.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TP1.json","/scsi/used_scable/TP1.json"})
	public @ResponseBody String TP1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TP1.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TP2.json","/scsi/used_scable/TP2.json"})
	public @ResponseBody String TP2() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TP2.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TP3.json","/scsi/used_scable/TP3.json"})
	public @ResponseBody String TP3() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TP3.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TPE.json","/scsi/used_scable/TPE.json"})
	public @ResponseBody String TPE() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TPE.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
	@RequestMapping(value= {"/used_scable/TSE-1.json","/scsi/used_scable/TSE-1.json"})
	public @ResponseBody String TSE1() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("usedscable2/TSE-1.json");
		File sourceFile = resource.getFile();
		String json =FileUtils.readFileToString(sourceFile, "UTF-8");
		return json;
	}
	
}
