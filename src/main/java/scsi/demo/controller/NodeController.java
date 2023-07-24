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
import scsi.demo.model.Nodes;
import scsi.demo.repository.CodeTabRepository;
import scsi.demo.repository.NodeRepository;
import scsi.demo.repository.UserRepository;
import scsi.demo.scsi.CaseData;
@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class NodeController {
	
	
	@Autowired
	private NodeRepository nodeRepository;
	
	@Autowired
	private CodeTabRepository codetabRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value= {"/node_list","/scsi/node_list"})
	public ModelAndView nodelist(@ModelAttribute("userid") String userid,@ModelAttribute("gk") String gk ) {
		
		ModelAndView model = new ModelAndView("node_list");
//		System.out.println("userid_node="+userid+"/gk="+gk);
		model.addObject("userid", userid);
		String gr="";
		gr=userRepository.getgr(userid);
		System.out.println("usergr=" + gr);
		if(gr.equals("admin")) {
			Iterable<Nodes> allNodeList = nodeRepository.findAll();
			if(!gk.equals("")) {
				allNodeList = nodeRepository.findNodeKey(gk);
			}
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
			Iterable<CodeTab> allTypeList = codetabRepository.findAllNType();
			Iterable<CodeTab> allCounList = codetabRepository.findAllCountry();
			Iterable<CodeTab> allFailList = codetabRepository.findAllNFail();
			
			model.addObject("nodeslist", allNodeList);
			model.addObject("ownerlist", allOwnerList);
			model.addObject("typelist", allTypeList);
			model.addObject("counlist", allCounList);
			model.addObject("faillist", allFailList);
			return model;
		}else {
			Iterable<Nodes> allNodeList = nodeRepository.findAllgr(gr);
			if(!gk.equals("")) {
				allNodeList = nodeRepository.findNodeKeygr(gk,gr);
			}
			Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwnergr(gr);
			Iterable<CodeTab> allTypeList = codetabRepository.findAllNType();
			Iterable<CodeTab> allCounList = codetabRepository.findAllCountry();
			Iterable<CodeTab> allFailList = codetabRepository.findAllNFail();
			
			model.addObject("nodeslist", allNodeList);
			model.addObject("ownerlist", allOwnerList);
			model.addObject("typelist", allTypeList);
			model.addObject("counlist", allCounList);
			model.addObject("faillist", allFailList);
			return model;
		}
		
	}
	
	@PostMapping(value= {"/newnode","/scsi/newnode"})
	public @ResponseBody String create(@ModelAttribute("userid") String user_id,CaseData cst,Nodes nodes,@RequestParam(value="sysid",defaultValue="0000") String sysid,@Param(value = "node_id")String node_id,@Param(value = "node_name")String node_name,@Param(value = "owner")String owner,@Param(value = "address")String address,@Param(value = "lon")String lon,@Param(value = "lat")String lat,@Param(value = "node_type")String node_type,@Param(value = "node_country")String node_country,@Param(value = "pos")String pos,@Param(value = "node_failure")String node_failure) throws IOException, SQLException {
		int nid= nodeRepository.existsNode_id(node_id);
		int nam =nodeRepository.existsNode_name(node_name);
//		System.out.println("nid="+nid+"/nam="+nam);
		String gr="";
		gr=userRepository.getgr(user_id);
		System.out.println("usergr=" + gr);
		if(sysid.equals("0000")) {
			if(nid<1 && nam<1) {
        nodeRepository.save(nodes);
        userRepository.logs(cst.todaytime2(),user_id,cst.todaytime(),"new node","node_id="+node_id+"/name="+node_name+"/owner="+owner+"/address="+address+"/lon="+lon+"/lat="+lat+"/node_type="+node_type+"/country="+node_country+"/pos="+pos+"/failure="+node_failure);
        return "OK";
			}else {
				return "exist";
			}
		}else {
		nodeRepository.addnode(sysid, node_id, node_name, owner, address, lon, lat, node_type, node_country, pos, node_failure);
		userRepository.logs(cst.todaytime2(),user_id,cst.todaytime(),"mody node","sysid="+sysid+"/node_id="+node_id+"/name="+node_name+"/owner="+owner+"/address="+address+"/lon="+lon+"/lat="+lat+"/node_type="+node_type+"/country="+node_country+"/pos="+pos+"/failure="+node_failure);
		return "OK";
		}
    }
	
	@GetMapping(value= {"/mody_node","/scsi/mody_node"})
	public ModelAndView modynode(@ModelAttribute("userid") String user_id,@ModelAttribute("sysid")String sysid)
	{
//		System.out.println("modynode="+sysid);
		ModelAndView model =new ModelAndView("mody_node");
		String gr="";
		gr=userRepository.getgr(user_id);
		System.out.println("usergr=" + gr);
		Iterable<Nodes> node=nodeRepository.findBySysid(sysid);
		
		Iterable<CodeTab> allOwnerList = codetabRepository.findAllOwner();
		if(!gr.equals("admin")) {
			allOwnerList = codetabRepository.findAllOwnergr(gr);
		}
		Iterable<CodeTab> allTypeList = codetabRepository.findAllNType();
		Iterable<CodeTab> allCounList = codetabRepository.findAllCountryWT();
		Iterable<CodeTab> allFailList = codetabRepository.findAllNFail();
		model.addObject("ownerlist", allOwnerList);
		model.addObject("typelist", allTypeList);
		model.addObject("counlist", allCounList);
		model.addObject("faillist", allFailList);
		model.addObject("nodelist",node);
		model.addObject("sysid",sysid);
		model.addObject("node_id",nodeRepository.findBysysid(sysid).getNode_id());
		model.addObject("node_name",nodeRepository.findBysysid(sysid).getNode_name());
		model.addObject("owner",nodeRepository.findBysysid(sysid).getOwner());
		model.addObject("address",nodeRepository.findBysysid(sysid).getAddress());
		model.addObject("lat",nodeRepository.findBysysid(sysid).getLat());
		model.addObject("lon",nodeRepository.findBysysid(sysid).getLon());
		model.addObject("node_type",nodeRepository.findBysysid(sysid).getNode_type());
		model.addObject("node_country",nodeRepository.findBysysid(sysid).getNode_country());
		model.addObject("pos",nodeRepository.findBysysid(sysid).getPos());
		model.addObject("node_fail",nodeRepository.findBysysid(sysid).getNode_failure());
		
		return model;
	}
	
	@PostMapping(value= {"/delnode","/scsi/delnode"})
	public @ResponseBody String delnode(@ModelAttribute("userid") String user_id,@ModelAttribute("sysid") String sysid,CaseData cst) throws IOException, SQLException {
		ModelAndView model = new ModelAndView("node_list");
//		System.out.println("delnode="+sysid);
		nodeRepository.delnode(sysid);
		userRepository.logs(cst.todaytime2(), user_id, cst.todaytime(), "del node", "sysid="+sysid);
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping(value= {"/nodeA","/scsi/nodeA"})
	public List<Nodes> nodeA(@ModelAttribute("userid")String user_id,@ModelAttribute("rt") String rt){
		System.out.println("RT ="+rt);
		String gr="";
		gr=userRepository.getgr(user_id);
		
		if(!gr.equals("admin")) {
			if(rt.equals("ISLAND")) {
				return nodeRepository.findIslandgr(gr);
			}else if(rt.equals("INTR")) {
				return nodeRepository.findIntrgr(gr);
			}else if(rt.equals("FOREIGN")) {
				return nodeRepository.findForeigngr(gr);
			}else {
				return null;
			}
		}
		else {
			if(rt.equals("ISLAND")) {
				return nodeRepository.findIsland();
			}else if(rt.equals("INTR")) {
				return nodeRepository.findIntr();
			}else if(rt.equals("FOREIGN")) {
				return nodeRepository.findForeign();
			}else {
				return null;
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value= {"/nodeB","/scsi/nodeB"})
	public List<Nodes> nodeB(@ModelAttribute("userid") String user_id,String rt,String g11,String name_zh){
//		System.out.println("RT ="+rt+"/g11="+g11);
		String gr="";
		gr=userRepository.getgr(user_id);
	if(!gr.equals("admin")) {
		if(g11.equals("國內")) {
			return nodeRepository.getIslandgr(rt,gr);
		}else if(g11.equals("陸鏈")) {
			return nodeRepository.getIntlgr(rt,gr);
		}else if(g11.equals("國際")) {
			return nodeRepository.getForeigngr(rt,gr);
		}else {
			return null;
		}
	}else {
		if(g11.equals("國內")) {
			return nodeRepository.getIsland(rt);
		}else if(g11.equals("陸鏈")) {
			return nodeRepository.getIntl(rt);
		}else if(g11.equals("國際")) {
			return nodeRepository.getForeign(rt);
		}else {
			return null;
		}
	}
	}
}
