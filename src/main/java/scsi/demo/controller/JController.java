package scsi.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class JController {
	@RequestMapping(value= {"/world/jsondata/countries.geo.json","/scsi/world/jsondata/countries.geo.json"})
	public @ResponseBody String GEO() throws IOException
	{
		File jsonFile = ResourceUtils.getFile("classpath:countries.geo.json");
		String json =FileUtils.readFileToString(jsonFile, "UTF-8");
		//JSONArray jsonArray =JSON.parseArray(json);
		return json;
	}
}
