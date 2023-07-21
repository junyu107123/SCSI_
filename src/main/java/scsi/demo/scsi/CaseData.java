package scsi.demo.scsi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;


@Controller

public class CaseData extends getData {

	public static void main(String[] args) {
	}

	public CaseData() {
	}

	public void closeall() throws IOException, SQLException {
		super.closeall();
	}
	public void SaveLog(String st2, String st3, String st4, String st5, String st6,
			String st7, String st8, String st9,String st10,String st11,String st12)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,rec_id=st2,donor=st3,alms=st4,type=st5,donate=st6,note=st7,handler=st8,
		 * sdate=st9,usernow=st10,active=st11,updatetime=st12
		 * 
		 * 
		 */
		ReserveData rst = new ReserveData();
//		Utility util1 = new Utility();
//		getData gdt = new getData();
		
                	  System.out.println("Create log");
                	//  String sysid = this.todaytime();
//                		updateData("insert into detail_log (rec_id,donor,alms,type,donate,note,handler,sdate,usernow,active,updatetime)values('"
//        						+ st2 + "','" + st3 + "','" + st4 + "','" + st5
//        						+ "','" + st6 + "','" + st7 + "','" + st8 + "','"+st9+"','"+st10+"','"+st11+"','"+sysid+"')");
                	  pst=conn.prepareStatement("insert into detail_log(rec_id,donor,alms,type,donate,note,handler,sdate,usernow,active,updatetime)values(?,?,?,?,?,?,?,?,?,?,?)");
                	  pst.setString(1, st2);
                	  pst.setString(2, st3);
                	  pst.setString(3, st4);
                	  pst.setString(4, st5);
                	  pst.setString(5, st6);
                	  pst.setString(6, st7);
                	  pst.setString(7, st8);
                	  pst.setString(8, st9);
                	  pst.setString(9, st10);
                	  pst.setString(10, st11);
        //        	  pst.setString(11, sysid);
                	  pst.execute();
//                	  util1.closeall();
		rst.closeall();
//		gdt.closeall();
	}
	

	public String Savenode(String st0, String st1, String st2, String st3, String st4, String st5, String st6,
			String st7, String st8, String st9,String st10,String st11)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,id=st2,name=st3,owner=st4,address=st5,lon=st6,lat=st7,type=st8,
		 * country=st9,pos=st10,failure=st11
		 * 
		 */
		System.out.println("Sn: ck="+st0+"/sysid="+st1+"/Sid="+st2+"/name="+st3);
		String ret = "";
		CaseData cst=new CaseData();
		ReserveData rst = new ReserveData();
		ReserveData rst1 = new ReserveData();
		
		rs1=rst1.getNId(st2);
		System.out.println("N ="+rs1.getRow());
		
			if(st0.equals("1") && rs1.next()){
				ret="EXIST";
			}
			else {
			rs=rst.getNodeId(st1);
		rs.beforeFirst();
		if (rs.next() ) {
			
				System.out.println("node:mody");
				 //updateData("update detail set rec_id='" + st2 + "',donor='"+st3+"',alms='"+st4+"',type='"+st5+"',donate='"+st6+"',note='"+st7+"',handler='"+st8+"',sdate='"+st9+"'   where sysid='"
				 //			+ rs.getString("sysid") + "'");
				  ret = "MODY";
//				  cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"update");
							pst=conn.prepareStatement("update sc_nodes set node_id=?,node_name=?,owner=?,address=?,lon=?,lat=?,node_type=?,node_country=?,pos=?,node_failure=?where sysid=?");
							pst.setString(1, st2);
							pst.setString(2, st3);
							pst.setString(3, st4);
							pst.setString(4, st5);
							pst.setString(5, st6);
							pst.setString(6, st7);
							pst.setString(7, st8);
							pst.setString(8, st9);
							pst.setString(9, st10);
							pst.setString(10, st11);
							pst.setString(11, rs.getString("sysid"));
							//System.out.println("pst="+pst);
							pst.execute();
			
		} else {
                  
                	  System.out.println("node:new");
                	  String sysid = this.todaytime2();
//                		updateData("insert into detail (sysid,rec_id,donor,alms,type,donate,note,handler,sdate) " + "values('"
//        						+ sysid + "','" + st2 + "','" + st3 + "','" + st4 + "','" + st5
//        						+ "','" + st6 + "','" + st7 + "','" + st8 + "','"+st9+"')");
        				ret = "OK";
        				pst=conn.prepareStatement("insert into sc_nodes (node_id,node_name,owner,address,lon,lat,node_type,node_country,pos,node_failure)values(?,?,?,?,?,?,?,?,?,?)");
        				pst.setString(1, st2);
						pst.setString(2, st3);
						pst.setString(3, st4);
						pst.setString(4, st5);
						pst.setString(5, st6);
						pst.setString(6, st7);
						pst.setString(7, st8);
						pst.setString(8, st9);
						pst.setString(9, st10);
						pst.setString(10, st11);
						//System.out.println("node ="+pst);
						pst.execute();
        				
//        		cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"create");
		}
		}
		rst.closeall();
		rst1.closeall();
		cst.closeall();
		rs.close();
		
		System.out.println("sn ret="+ret);
		return ret;
	}
	
	public String Saveconn(String st0, String st1, String st2, String st3, String st4, String st5, String st6,
			String st7, String st8)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,cid=st2,cA=st3,cB=st4,voice=st5,net=st6,cline=st7,cbw=st8,
		 * 
		 * 
		 */
		System.out.println("Sc: ck="+st0+"/sysid="+st1+"/cid="+st2);
		String ret = "";
		ReserveData rst = new ReserveData();
		ReserveData rst1 = new ReserveData();
		
			boolean A=rst.checknoden(st3);
			boolean B=rst.checknoden(st4);
			System.out.println(" A="+st3+"/"+A);
			System.out.println(" B="+st4+"/"+B);
			try {
				if(A==true && B==true) {
					System.out.println("1:");
		rs1=rst1.getCIdd(st2,st3,st4);
		System.out.println("N ="+rs1.getRow());
		
			if(rs1.next()){//id A B :O
				System.out.println("id A B 都一致");
				  ret = "MODY";
							pst=conn.prepareStatement("update sc_connection set InterVoice=?,Internet=?,Leasing_circuit_line=?,Leasing_circuit_bw=?where sysid=?",ResultSet.TYPE_SCROLL_SENSITIVE,
		                            ResultSet.CONCUR_READ_ONLY);
							pst.setString(1, st5);
							pst.setString(2, st6);
							pst.setString(3, st7);
							pst.setString(4, st8);
							pst.setString(5, rs1.getString("sysid"));
							
							System.out.println("conn_m="+pst);
							pst.execute();
			}else {// id A B :X
				System.out.println("id A B 不一致");
			rs=rst.getCIdf(st2);// id :exist
			rs.last();
			System.out.println("NN ="+rs.getRow());
		rs.beforeFirst();
		if(rs.next()) {
			System.out.println("id :已存在");
			ret="EXIST";
		}else {
			System.out.println("id :不存在");
			boolean b=false;
			b=rst.checkABBA(st3, st4);
			System.out.println("checkAB_BA="+b);
			if(b==true) {
				System.out.println("AB點 BA點 :存在");
				ret="EXIST";
			}else {	
			ret="OK";
			pst=conn.prepareStatement("insert into sc_connection (connection_id,connection_A,connection_B,notes,level,linecolor,InterVoice,Internet,Leasing_circuit_line,Leasing_circuit_bw)values(?,?,?,?,?,?,?,?,?,?)",ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
						
						pst.setString(1, st2);
						pst.setString(2, st3);
						pst.setString(3, st4);
						pst.setString(4, "服務尚無影響");
						pst.setInt(5, 0);
						pst.setString(6, "#0acf24");
						pst.setString(7, st5);
						pst.setString(8, st6);
						pst.setString(9, st7);
						pst.setString(10, st8);
						
						System.out.println("conn_n="+pst);
						pst.execute();
			}
		}
		}
		}else {
					System.out.println("2:");
					ret="NONE";
			}
	}catch (Exception e) {
		System.out.println("e ="+e.toString());
}finally {
		
		rst.closeall();
		rst1.closeall();
		if(rs1!=null) {
			rs1.close();
			}
			if(rs!=null) {
			rs.close();
			}
	}
		
		System.out.println("sc ret="+ret);
		return ret;
	}
	
	public String Savefail(String st0, String st1, String st2, String st3, String st4, String st5, String st6,
			String st7)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,cid=st2,cdate=st3,voice=st4,net=st5,cline=st6,cbw=st7,
		 * 
		 * 
		 */
		System.out.println("Sf: ck="+st0+"/sysid="+st1+"/cid="+st2+"/ft="+st3+"/voice="+st4+"/net="+st5+"/cline="+st6+"/cbw="+st7);
		String ret = "";
		
		try {
		
		ReserveData rst = new ReserveData();
		ReserveData rst1 = new ReserveData();
		ReserveData rst2 = new ReserveData();
		Utility ul=new Utility();
		int total =0;
		int t5=Integer.parseInt(st4);
		int	t6=Integer.parseInt(st5);
		int	t7=Integer.parseInt(st6);
		int	t8=Integer.parseInt(st7);
			total = t5+t6+t7+t8;
			if(st3.equals("")) {
				st3=ul.todaytime();
			}
			System.out.println("total="+total);
			try {
		rs1=rst1.getCIdf(st2);
		rs1.last();
		System.out.println("N ="+rs1.getRow());
		rs1.beforeFirst();
			if(rs1.next()){  //cid :O
				System.out.println("1:");
				 rs=rst2.getfail0(st2);
				 rs.last();
				 int up=rs.getRow();
					System.out.println("up ="+rs.getRow());
					rs.beforeFirst();
 				if(total>0 && up==0) {
 					
 					System.out.println("1-1:");
 				pst=conn.prepareStatement("insert into sc_connectionfail (connectionid,faildatetime,failceased,failvoice,failinternet,failleasing_circuit_line,failleasing_circuit_bw)values(?,?,'0',?,?,?,?)",ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
 				pst.setString(1, st2);
					pst.setString(2, st3);
					pst.setString(3, st4);
					pst.setString(4, st5);
					pst.setString(5, st6);
					pst.setString(6, st7);
					
					System.out.println("fail pst="+pst);
					pst.execute();
					pst1=conn.prepareStatement("update sc_connection set level ='1',notes='服務影響:',linecolor='#C14242' where sysid =?",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
					pst1.setString(1, rs1.getString("sysid"));
					System.out.println("fail pst1="+pst1);
					pst1.execute();
					ret = "OK";
 				}else if(total>0&&up>0){//total>0
 					System.out.println("1-2:");
 					
 						rs=rst2.getfail0(st2);
 						rs.last();
 						up=rs.getRow();
 							System.out.println("up ="+rs.getRow());
 						rs.beforeFirst();
 						while(rs.next()) {
 					pst=conn.prepareStatement("update sc_connectionfail set failceased ='1', ceaseddatetime = ? where sysid =? ",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
 	 				pst.setString(1, ul.todaytime());
 						
 						pst.setString(2, rs.getString("sysid"));
 						
 						System.out.println("fail pst="+pst);
 						pst.execute();
 						}
 					pst1=conn.prepareStatement("update sc_connection set level ='1',notes='服務影響:',linecolor='#C14242' where sysid =?",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
 					pst1.setString(1, rs1.getString("sysid"));
 					System.out.println("fail pst1="+pst1);
 					pst1.execute();
 					pst2=conn.prepareStatement("insert into sc_connectionfail (connectionid,faildatetime,failceased,failvoice,failinternet,failleasing_circuit_line,failleasing_circuit_bw)values(?,?,'0',?,?,?,?)",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
 	 				pst2.setString(1, st2);
 						pst2.setString(2, st3);
 						pst2.setString(3, st4);
 						pst2.setString(4, st5);
 						pst2.setString(5, st6);
 						pst2.setString(6, st7);
 						
 						System.out.println("fail pst2="+pst2);
 						pst2.execute();
 					ret = "OK";
 				}else if(total==0){
 					System.out.println("1-3:");
 					rs=rst2.getfail0(st2);
 					rs.last();
 					//up=rs.getRow();
 						System.out.println("up ="+rs.getRow());
 					rs.beforeFirst();
 						try {
 					
 					while(rs.next()) {
 						System.out.println("1  :"+ul.todaytime());
 						System.out.println("2  :"+st4);
 						System.out.println("3  :"+st5);
 						System.out.println("4  :"+st6);
 						System.out.println("5  :"+st7);
 						System.out.println("6  :"+rs.getString("sysid"));
 					pst=conn.prepareStatement("update sc_connectionfail set failceased ='1', ceaseddatetime = ? where sysid =? ",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
 	 				pst.setString(1, ul.todaytime());
 						
 						pst.setString(2, rs.getString("sysid"));
 						
 						System.out.println("fail pst="+pst);
 						pst.execute();
 					}
 					}catch (Exception e) {
 						System.out.println("EXception..."+e.toString());
 					}
 					try {
 					pst1=conn.prepareStatement("update sc_connection set level ='0',notes='服務尚無影響',linecolor='#0acf24' where sysid =?",ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
 					
 					pst1.setString(1, rs1.getString("sysid"));
 					System.out.println("fail pst1="+pst1);
 					pst1.execute();
 					}catch(Exception e) {
 						System.out.println("AgainException :"+e.toString());
 					}
 					ret = "OK";
// 					System.out.println("1-4 ret=="+ret);
// 					return ret;
			}else {
				System.out.println("1-4:");
				ret="NONE";
			}	
			}else {// cid: X
				System.out.println("2:");
					ret="NONE";
		}
			}catch (Exception e) {
				System.out.println("X e ="+e.toString());
		}finally {
			rst.closeall();
			rst1.closeall();
			rst2.closeall();
			ul.closeall();
				rs.close();
				rs1.close();
		}
		}catch (Exception e) {
			System.out.println("BIG..."+e.toString());
		}
		System.out.println("sf ret=="+ret);
		return ret;
	}
	
	public String Saveuser(String st0, String st1, String st2, String st3, String st4,String st5)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,id=st2,password=st3,name=st4,gr=st5
		 * 
		 */
		String ret = "";
		ReserveData rst = new ReserveData();
//		Utility util1 = new Utility();
//		CaseData cst =new CaseData();
//		getData gdt = new getData();
		
		init("sc_users");
		// System.out.print("userid='"+st1+"'");
		this.queryMe("user_id='" + st2 + "' and del_mark is null");
		if (st0.equals("1")) {
			if (this.showCount() == 1) {
				ret = "EXIST";
			} else {
				System.out.println("user:new");
				pst=conn.prepareStatement("insert into sc_users (user_id,password,user_name,user_gr)values(?,?,?,?)");
				pst.setString(1, st2);
				pst.setString(2, st3);
				pst.setString(3, st4);
				pst.setString(4, st5);
				//System.out.println("node ="+pst);
				pst.execute();
				ret = "OK";
			}

		} else if (!st0.equals("1")) {
			
			rs=rst.getUserId(st1);
			rs.beforeFirst();
			while(rs.next()) {
			System.out.println("user:mody");
			pst=conn.prepareStatement("update sc_users set user_id=?,password=?,user_name=?,user_gr=? where user_sysid=?");
			pst.setString(1, st2);
			pst.setString(2, st3);
			pst.setString(3, st4);
			pst.setString(4, st5);
			pst.setString(5, rs.getString("user_sysid"));
			//System.out.println("pst="+pst);
			pst.execute();
			ret = "MODY";
			
			}
		}
		rs.close();
		rst.closeall();
		this.closeall();
		return ret;
	}
	
	public String Saveugr(String st0, String st1, String st2, String st3, String st4)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,id=st2,name=st3,gr=st4
		 * 
		 */
		String ret = "";
		ReserveData rst = new ReserveData();
		
		init("sc_users");
		// System.out.print("userid='"+st1+"'");
		this.queryMe("user_id='" + st2 + "' and del_mark is null");

			rs=rst.getUserId(st1);
			rs.beforeFirst();
			while(rs.next()) {
			System.out.println("user:modygr");
			pst=conn.prepareStatement("update sc_users set user_id=?,user_name=?,user_gr=? where user_sysid=?");
			pst.setString(1, st2);
			pst.setString(2, st3);
			pst.setString(3, st4);
			pst.setString(4, rs.getString("user_sysid"));
			//System.out.println("pst="+pst);
			pst.execute();
			ret = "MODY";
			
			}
		rs.close();
		rst.closeall();
		this.closeall();
		return ret;
	}
	
	public String Savedetail(String st0, String st1, String st2, String st3, String st4, String st5, String st6,
			String st7, String st8)
			throws Exception {

		/* ck=st0
		 * sysid=st1,link=st2
		 * ,id=st3,type=st4,op=st5,band=st6,fail=st7,failb=st8,
		 * 
		 */
		String ret = "";
		ReserveData rst = new ReserveData();
//		ReserveData rst2 = new ReserveData();
//		Utility util1 = new Utility();
//		CaseData cst =new CaseData();
//		getData gdt = new getData();
		
		rs=rst.getDetailId(st1);
		rs.beforeFirst();
		String sysid = "";
		if (rs.next() ) {
				System.out.println("detail:mody");
				 //updateData("update detail set rec_id='" + st2 + "',donor='"+st3+"',alms='"+st4+"',type='"+st5+"',donate='"+st6+"',note='"+st7+"',handler='"+st8+"',sdate='"+st9+"'   where sysid='"
				 //			+ rs.getString("sysid") + "'");
				  ret = "MODY";
				//  cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"update");
							pst=conn.prepareStatement("update sc_links_details set details_id=?,details_type=?,details_operator=?,details_bandwidth=?,details_failure=?,details_failure_bandwidth=?where sysid=?");
							pst.setString(1, st3);
							pst.setString(2, st4);
							pst.setString(3, st5);
							pst.setString(4, st6);
							pst.setString(5, st7);
							pst.setString(6, st8);
							pst.setString(7, rs.getString("sysid"));
							//System.out.println("pst="+pst);
							pst.execute();
		} else {
                  
                	  System.out.println("detail:new");
                	  
//                		updateData("insert into detail (sysid,rec_id,donor,alms,type,donate,note,handler,sdate) " + "values('"
//        						+ sysid + "','" + st2 + "','" + st3 + "','" + st4 + "','" + st5
//        						+ "','" + st6 + "','" + st7 + "','" + st8 + "','"+st9+"')");
        				ret = "OK";
        				pst=conn.prepareStatement("insert into sc_links_details (links_sysid,details_id,details_type,details_operator,details_bandwidth,details_failure,details_failure_bandwidth)values(?,?,?,?,?,?,?)");
        				pst.setString(1, st2);
						pst.setString(2, st3);
						pst.setString(3, st4);
						pst.setString(4, st5);
						pst.setString(5, st6);
						pst.setString(6, st7);
						pst.setString(7, st8);
						
						//System.out.println("node ="+pst);
						pst.execute();
        				
        		//cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"create");
		}
//		 util1.closeall();
		rst.closeall();
//		rst2.closeall();
//		gdt.closeall();
//		cst.closeall();
		rs.close();
		return ret;
	}
	
	public String Savef(String st0, String st1, String st2, String st3, String st4)
			throws Exception {

		/* seq=st0
		 * codeid=st1,namezh=st2
		 * ,namedesc=st3,namech=st4
		 * 
		 */
		String ret = "";
		ReserveData rst = new ReserveData();
//		ReserveData rst2 = new ReserveData();
//		Utility util1 = new Utility();
//		CaseData cst =new CaseData();
//		getData gdt = new getData();
		
		rs=rst.getEditF(st0);
		rs.beforeFirst();
		if (rs.next() ) {
				System.out.println("file:mody");
				 //updateData("update detail set rec_id='" + st2 + "',donor='"+st3+"',alms='"+st4+"',type='"+st5+"',donate='"+st6+"',note='"+st7+"',handler='"+st8+"',sdate='"+st9+"'   where sysid='"
				 //			+ rs.getString("sysid") + "'");
				  ret = "MODY";
				//  cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"update");
							pst=conn.prepareStatement("update sc_code_tab set code_id=?,name_zh=?,name_desc_zh=?,name_ch=?where seq=?");
							pst.setString(1, st1);
							pst.setString(2, st2);
							pst.setString(3, st3);
							pst.setString(4, st4);
							pst.setString(5, rs.getString("seq"));
							//System.out.println("pst="+pst);
							pst.execute();
		} else {
                  
                	  System.out.println("file:new");
                	  
//                		updateData("insert into detail (sysid,rec_id,donor,alms,type,donate,note,handler,sdate) " + "values('"
//        						+ sysid + "','" + st2 + "','" + st3 + "','" + st4 + "','" + st5
//        						+ "','" + st6 + "','" + st7 + "','" + st8 + "','"+st9+"')");
        				ret = "OK";
        				pst=conn.prepareStatement("insert into sc_code_tab (code_id,name_zh,name_desc_zh,name_ch)values(?,?,?,?)");
        				pst.setString(1, st1);
						pst.setString(2, st2);
						pst.setString(3, st3);
						pst.setString(4, st4);
						//System.out.println("node ="+pst);
						pst.execute();
        				
        		//cst.SaveLog(st2, st3,st4 ,st5,st6,st7,st8,st9,st10,st11,"create");
		}
//		 util1.closeall();
		rst.closeall();
//		rst2.closeall();
//		gdt.closeall();
//		cst.closeall();
		rs.close();
		return ret;
	}

	public void deletenode(String st0) throws IOException, SQLException, InterruptedException {
		// st0 loc st1 startdate st2 enddate st3 rid st4 admin st5 reason
		//System.out.println("delete from readerinfo where sysid='"+st0+"'");
		//updateData("update detail set del_mark='1' where sysid='" + st0 + "'");
		pst=conn.prepareStatement("update sc_nodes set del_mark='1' where sysid =?");
		pst.setString(1, ""+st0+"");
		pst.execute();
		
	}
	
	public void deleteuser(String st0) throws IOException, SQLException, InterruptedException {
		// st0 loc st1 startdate st2 enddate st3 rid st4 admin st5 reason
		//System.out.println("delete from readerinfo where sysid='"+st0+"'");
		//updateData("update detail set del_mark='1' where sysid='" + st0 + "'");
		pst=conn.prepareStatement("update sc_users set del_mark='1' where user_sysid =?");
		pst.setString(1, ""+st0+"");
		pst.execute();
		
	}
	
	public void deletef(String st0) throws IOException, SQLException, InterruptedException {
		// st0 loc st1 startdate st2 enddate st3 rid st4 admin st5 reason
		//System.out.println("delete from readerinfo where sysid='"+st0+"'");
		//updateData("update detail set del_mark='1' where sysid='" + st0 + "'");
		pst=conn.prepareStatement("update sc_code_tab set del_mark ='1' where seq =?");
		pst.setString(1, ""+st0+"");
		pst.execute();
		
	}
	
	public void deletelink(String st0) throws IOException, SQLException, InterruptedException {
		// st0 loc st1 startdate st2 enddate st3 rid st4 admin st5 reason
		//System.out.println("delete from readerinfo where sysid='"+st0+"'");
		//updateData("update detail set del_mark='1' where sysid='" + st0 + "'");
		pst=conn.prepareStatement("update sc_links set del_mark ='1' where sysid =?");
		pst.setString(1, ""+st0+"");
		pst.execute();
		
	}
	
	public void deletede(String st0) throws IOException, SQLException, InterruptedException {
		// st0 loc st1 startdate st2 enddate st3 rid st4 admin st5 reason
		//System.out.println("delete from readerinfo where sysid='"+st0+"'");
		//updateData("update detail set del_mark='1' where sysid='" + st0 + "'");
		pst=conn.prepareStatement("update sc_links_details set del_mark='1' where sysid =?");
		pst.setString(1, ""+st0+"");
		pst.execute();
		
	}

	public String Savelink(String st0, String st1, String st2, String st3, String st4,String st5,String st6,String st7,String st8,String st9,String st10,String st11,String st12,String st13,String st14,String st15,String st16,String st17,String st18)
			throws Exception {

		/* ck=st0
		 * sysid=st1
		 * ,id=st2,name=st3,owner=st4,nodeA=st5,nodeB=st6,max=st7,using=st8,rest=st9,file=st10
		 * data=st11,type=st12,pro=st13,f=st14,fb=st15,w1=st16,w2=st17,w3=st18
		 * 
		 */
		String ret = "";
		ReserveData rst = new ReserveData();
		ReserveData rst1=new ReserveData();
		
		rs1=rst1.getLId(st2);
		if(st0.equals("1") && rs1.next()){
			ret="EXIST";
		}else {
			rs=rst.getLinkId(st1);	// use
		rs.beforeFirst();
//		System.out.println("sl =sysid="+st1+"/"+"id="+st2+"/"+"name="+st3+"/"+"owner="+st4+"/"+"A="+st5+"/"+"B="+st6+"/"+"max="+st7+"/"+"using="+st8+"/"+"rest="+st9+"/"+"file="+st10+"/"+"data="+st11+"/"+"type="+st12+"/"+"pro="+st13+"/"+"fail="+st14+"/"+"failb="+st15);
		if (rs.next() ) {
				System.out.println("link:mody");
//				 updateData("update user1 set password='"+st3+"',username='"+st4+"' ,note='"+st5+"' where sysid='"
//							+ rs.getString("sysid") + "'");
				pst=conn.prepareStatement("update sc_links set link_id =?,link_name=?,link_owner=?,link_nodeA=?,link_nodeB=?,max_bandwidth=?,using_bandwidth=?,rest_bandwidth=?,pathfile=?,pathdata=?,link_type=?,link_protocol=?,failure=?,failure_bandwidth=?,w_length=?,w_latency=?,w_weight=? where sysid =?");
				pst.setString(1, st2); 
				pst.setString(2, st3); 
				pst.setString(3, st4); 
				pst.setString(4, st5); 
				pst.setString(5, st6); 
				pst.setString(6, st7); 
				pst.setString(7, st8); 
				pst.setString(8, st9); 
				pst.setString(9, st10); 
				pst.setString(10, st11); 
				pst.setString(11, st12); 
				pst.setString(12, st13); 
				pst.setString(13, st14); 
				pst.setString(14, st15); 
				pst.setString(15, st16); 
				pst.setString(16, st17); 
				pst.setString(17, st18); 
				pst.setString(18, rs.getString("sysid")); 
				//System.out.println("sl pst="+pst);
				pst.execute();
				ret = "MODY";
			
		} else {
                  
                	  System.out.println("link:new");
                	  String sysid = this.todaytime2();
//                		updateData("insert into user1 (sysid,userid,password,username,note,last_active_time,del_mark) " + "values('"
//        						+ sysid + "','" + st2 + "','" + st3 + "','" + st4 + "','"+st5+"','',null)");
                	  pst=conn.prepareStatement("insert into sc_links (link_id,link_name,link_owner,link_nodeA,link_nodeB,max_bandwidth,using_bandwidth,rest_bandwidth,pathfile,pathdata,link_type,link_protocol,failure,failure_bandwidth,w_length,w_latency,w_weight)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                	  pst.setString(1, st2); 
      				pst.setString(2, st3); 
      				pst.setString(3, st4); 
      				pst.setString(4, st5); 
      				pst.setString(5, st6); 
      				pst.setString(6, st7); 
      				pst.setString(7, st8); 
      				pst.setString(8, st9); 
      				pst.setString(9, st10); 
      				pst.setString(10, st11); 
      				pst.setString(11, st12); 
      				pst.setString(12, st13); 
      				pst.setString(13, st14); 
      				pst.setString(14, st15);
      				pst.setString(15, st16); 
      				pst.setString(16, st17); 
      				pst.setString(17, st18); 
                	  pst.execute();
                	  ret = "OK";
		}
		}
		rst1.closeall();
		rst.closeall();
//		gdt.closeall();
		rs.close();
		return ret;
	}
	public void failtimes(int int0,String st0) throws SQLException {
		pst=conn.prepareStatement("update sc_users set fail_times =? where user_sysid =?");
		pst.setString(1, ""+int0+"");
		pst.setString(2, ""+st0+"");
//		System.out.println("fail="+pst);
		pst.execute();
	}
	
	public void golock(String st0) throws SQLException, IOException {
		pst=conn.prepareStatement("update sc_users set fail_times ='0' ,lock_flag='1',lock_st= '"+todaytime_hhmm()+"' where user_sysid =?");
		pst.setString(1, ""+st0+"");
//		System.out.println("lock="+pst);
		pst.execute();
	}
	
	public void timeover(String st0) throws SQLException, IOException {
		pst=conn.prepareStatement("update sc_users set fail_times ='0' ,lock_flag='0',lock_st= '' where user_sysid =?");
		pst.setString(1, ""+st0+"");
//		System.out.println("over="+pst);
		pst.execute();
	}
	
	public void clearfail(String st0) throws SQLException, IOException {
		pst=conn.prepareStatement("update sc_users set fail_times ='0' ,lock_flag='0',lock_st= '' where user_sysid =?");
		pst.setString(1, ""+st0+"");
//		System.out.println("clear="+pst);
		pst.execute();
	}
	
	public void logs(String st0,String st1,String st2,String st3) throws SQLException, IOException 
	{//,st0=userid,st1=time,st2=action,st3=detail
		pst=conn.prepareStatement("insert into sc_logs(sysid,user_id,totime,action,detail)value('"+todaytime2()+"',?,?,?,?) ");
		pst.setString(1, ""+st0+"");
		pst.setString(2, ""+st1+"");
		pst.setString(3, ""+st2+"");
		pst.setString(4, ""+st3+"");
//		System.out.println("logs="+pst);
		pst.execute();
	}
	public boolean deleteDir(File dir) {
		 //System.out.print("1"+dir);
		
	   if (dir.isDirectory()) {
	      String[] children = dir.list();
	      for (int i = 0; i < children.length; i++) {
	         boolean success = deleteDir(new File(dir, children[i]));
	         if (!success) {
	            return false;
	         }
	      }
	   }
	   //System.out.print(dir);
	   return dir.delete();
	   
	}
	
	public String readnode(String filename, int sheetpage)
			throws IOException, SQLException, InterruptedException, ParseException {
		System.out.println("readnd="+filename);
        int count_success = 0;
        StringBuilder count_fail = new StringBuilder();
        String SN="";
        //int count_fail = 0;
		// 1.Read Excel File into workbook
        //System.out.println("file="+filename);
        FileInputStream inp = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		inp.close();

		// 2.get wb sheet(0)
		XSSFSheet sheet = wb.getSheetAt(sheetpage);
		// get total num of row
		int rowLength = sheet.getLastRowNum();
		String sheetName = sheet.getSheetName();
		// 3.get wb row
		XSSFRow row = sheet.getRow(0);
		// total num of cols(cell)
		int cellLength = row.getLastCellNum();
		// 4.get wb cols(cell
		XSSFCell cell = row.getCell(0);
		DecimalFormat df = new DecimalFormat("0");

		// this loop will scane all info at each cell
		System.out.println("rowLength ="+rowLength);
		for (int i = 1; i <= rowLength; i++) {
			// get each row

			if (sheetpage == 0) {
				try
				{
					String addr="";
					String lon="";
					String lat="";
					String pos="";
					String nf="";
				XSSFRow row1 = sheet.getRow(i);
				System.out.println("");
				XSSFCell cellnid = row1.getCell(0);
				cellnid.setCellType(CellType.STRING);
				System.out.println("nid="+cellnid.toString());
				XSSFCell cellname = row1.getCell(1);
				cellname.setCellType(CellType.STRING);
				System.out.println("cellname="+cellname.toString());
				XSSFCell cellowner = row1.getCell(2);
				cellowner.setCellType(CellType.STRING);
				System.out.println("cellowner="+cellowner.toString());
				XSSFCell celladdr = row1.getCell(3);
				celladdr.setCellType(CellType.STRING);
				System.out.println("celladdr="+celladdr.toString());
				XSSFCell celllon = row1.getCell(4);
				celllon.setCellType(CellType.STRING);
				System.out.println("celllon="+celllon.toString());
				XSSFCell celllat = row1.getCell(5);
				celllat.setCellType(CellType.STRING);
				System.out.println("celllat="+celllat.toString());
				XSSFCell cellntype = row1.getCell(6);
				cellntype.setCellType(CellType.STRING);
				System.out.println("cellntype="+cellntype.toString());
				XSSFCell cellctry = row1.getCell(7);
				cellctry.setCellType(CellType.STRING);
				System.out.println("cellctry="+cellctry.toString());
				XSSFCell cellpos = row1.getCell(8);
				cellpos.setCellType(CellType.STRING);
				System.out.println("cellpos="+cellpos.toString());
				XSSFCell cellnf = row1.getCell(9);
				cellnf.setCellType(CellType.STRING);
				System.out.println("cellnf="+cellnf.toString());
				/*
				 uid=st1
				 * ,name=st2,msg=st3,type=st4,address=st5,tel=st6,hexcardid=st7,
				 * note=st8,box=st9,photo=st10
				 *  
				 */

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				
				
				SN=this.Savenode("1","",cellnid.toString(), cellname.toString(),cellowner.toString(),celladdr.toString(),
						celllon.toString(),celllat.toString(),cellntype.toString(),cellctry.toString(),cellpos.toString(),cellnf.toString());
				System.out.println("SN="+SN);
				//if needed can writ card to card marchine here
				count_success = count_success+1;

				}catch(Exception e) {
					if(rowLength > 0)
					{
						System.out.println(e.toString());
						 // appends the string argument to the StringBuilder
						 count_fail.append(i+",");
					}	
						
					
					
				}
			} 

		}

		return Integer.toString(count_success)+"/"+count_fail+"/"+SN;
	}
	
	public String readconn(String filename, int sheetpage)
			throws IOException, SQLException, InterruptedException, ParseException {
		System.out.println("readconn="+filename);
        int count_success = 0;
        StringBuilder count_fail = new StringBuilder();
        String SC="";
        //int count_fail = 0;
		// 1.Read Excel File into workbook
        //System.out.println("file="+filename);
        FileInputStream inp = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(inp);

		// 2.get wb sheet(0)
		XSSFSheet sheet = wb.getSheetAt(sheetpage);
		// get total num of row
		int rowLength = sheet.getLastRowNum();
		String sheetName = sheet.getSheetName();
		// 3.get wb row
		XSSFRow row = sheet.getRow(0);
		// total num of cols(cell)
		int cellLength = row.getLastCellNum();
		// 4.get wb cols(cell
		XSSFCell cell = row.getCell(0);
		DecimalFormat df = new DecimalFormat("0");

		// this loop will scane all info at each cell
		System.out.println("rowLength ="+rowLength);
		for (int i = 1; i <= rowLength; i++) {
			// get each row

			if (sheetpage == 0) {
				try
				{
				XSSFRow row1 = sheet.getRow(i);
				System.out.println("");
				XSSFCell cellcid = row1.getCell(0);
				cellcid.setCellType(CellType.STRING);
				System.out.println("cid="+cellcid.toString());
				XSSFCell cellcA = row1.getCell(1);
				cellcA.setCellType(CellType.STRING);
				System.out.println("cellcA="+cellcA.toString());
				XSSFCell cellcB = row1.getCell(2);
				cellcB.setCellType(CellType.STRING);
				System.out.println("cellcB="+cellcB.toString());
				XSSFCell cellvoice = row1.getCell(3);
				cellvoice.setCellType(CellType.STRING);
				System.out.println("cellvoice="+cellvoice.toString());
				XSSFCell cellnet = row1.getCell(4);
				cellnet.setCellType(CellType.STRING);
				System.out.println("cellnet="+cellnet.toString());
				XSSFCell cellcl = row1.getCell(5);
				cellcl.setCellType(CellType.STRING);
				System.out.println("cellcl="+cellcl.toString());
				XSSFCell cellcbw = row1.getCell(6);
				cellcbw.setCellType(CellType.STRING);
				System.out.println("cellcbw="+cellcbw.toString());
				
				/*
				 cid=st1
				 * ,cA=st2,msg=st3,type=st4,address=st5,tel=st6,hexcardid=st7,
				 * note=st8,box=st9,photo=st10
				 *  
				 */

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				
				
				SC=this.Saveconn("1","",cellcid.toString(), cellcA.toString(),cellcB.toString(),cellvoice.toString(),
						cellnet.toString(),cellcl.toString(),cellcbw.toString());
				System.out.println("SC="+SC);
				if(!SC.equals("OK")) {
					 count_fail.append(i+",");
				}else {
				count_success = count_success+1;
				}
				}catch(Exception e) {
					if(rowLength > 0)
					{
						System.out.println(e.toString());
						 // appends the string argument to the StringBuilder
						 count_fail.append(i+",");
					}	
						
					
					
				}finally {
					inp.close();
					wb.close();
				}
			} 

		}

		return Integer.toString(count_success)+"/"+count_fail+"/"+SC;
	}
	
	public String readfail(String filename, int sheetpage)
			throws IOException, SQLException, InterruptedException, ParseException {
		System.out.println("readfail="+filename);
        int count_success = 0;
        StringBuilder count_fail = new StringBuilder();
        String SF="";
        //int count_fail = 0;
		// 1.Read Excel File into workbook
        //System.out.println("file="+filename);
        FileInputStream inp = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		

		// 2.get wb sheet(0)
		XSSFSheet sheet = wb.getSheetAt(sheetpage);
		// get total num of row
		int rowLength = sheet.getLastRowNum();
		String sheetName = sheet.getSheetName();
		// 3.get wb row
		XSSFRow row = sheet.getRow(0);
		// total num of cols(cell)
		int cellLength = row.getLastCellNum();
		// 4.get wb cols(cell
		XSSFCell cell = row.getCell(0);
		DecimalFormat df = new DecimalFormat("0");

		// this loop will scane all info at each cell
		System.out.println("rowLength ="+rowLength);
		for (int i = 1; i <= rowLength; i++) {
			// get each row

			if (sheetpage == 0) {
				try
				{
					String datetime="";
				XSSFRow row1 = sheet.getRow(i);
				System.out.println("");
				XSSFCell cellcid = row1.getCell(0);
				cellcid.setCellType(CellType.STRING);
				System.out.println("cid="+cellcid.toString());
				XSSFCell cellftime = row1.getCell(3);
				cellftime.setCellType(CellType.STRING);
				System.out.println("cellftime="+cellftime.toString());
				if (cellftime.getCellType() == CellType.BLANK) {
					datetime=this.todaytime();
				}else {
					datetime=cellftime.toString();
				}
				XSSFCell cellvoice = row1.getCell(4);
				cellvoice.setCellType(CellType.STRING);
				System.out.println("cellvoice="+cellvoice.toString());
				XSSFCell cellnet = row1.getCell(5);
				cellnet.setCellType(CellType.STRING);
				System.out.println("cellnet="+cellnet.toString());
				XSSFCell cellcl = row1.getCell(6);
				cellcl.setCellType(CellType.STRING);
				System.out.println("cellcl="+cellcl.toString());
				XSSFCell cellcbw = row1.getCell(7);
				cellcbw.setCellType(CellType.STRING);
				System.out.println("cellcbw="+cellcbw.toString());
				
				/*
				 cid=st1
				 * ,cA=st2,msg=st3,type=st4,address=st5,tel=st6,hexcardid=st7,
				 * note=st8,box=st9,photo=st10
				 *  
				 */

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				
				
				SF=this.Savefail("1","",cellcid.toString(),datetime,cellvoice.toString(),
						cellnet.toString(),cellcl.toString(),cellcbw.toString());
				System.out.println("SF="+SF);
				//if needed can writ card to card marchine here
				if(!SF.equals("OK")) {
					 count_fail.append(i+",");
				}else {
				count_success = count_success+1;
				}
				}catch(Exception e) {
					System.out.println("e="+e.toString());
					if(rowLength > 0)
					{
						System.out.println(e.toString());
						 // appends the string argument to the StringBuilder
						 count_fail.append(i+",");
					}	
						
					
					
				}finally {
					inp.close();
					wb.close();
				}
			} 

		}

		return Integer.toString(count_success)+"/"+count_fail+"/"+SF;
	}
	
	public String readlink(String filename, int sheetpage)
			throws IOException, SQLException, InterruptedException, ParseException {
		System.out.println("readlk="+filename);
        int count_success = 0;
        StringBuilder count_fail = new StringBuilder();
        String SL="";
        //int count_fail = 0;
		// 1.Read Excel File into workbook
        //System.out.println("file="+filename);
        FileInputStream inp = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		inp.close();

		// 2.get wb sheet(0)
		XSSFSheet sheet = wb.getSheetAt(sheetpage);
		// get total num of row
		int rowLength = sheet.getLastRowNum();
		String sheetName = sheet.getSheetName();
		//System.out.println(sheetName);
		// 3.get wb row
		XSSFRow row = sheet.getRow(0);
		// total num of cols(cell)
		int cellLength = row.getLastCellNum();
		// 4.get wb cols(cell
		XSSFCell cell = row.getCell(0);
		DecimalFormat df = new DecimalFormat("0");

		// this loop will scane all info at each cell
		for (int i = 1; i <= rowLength; i++) {
			// get each row

			if (sheetpage == 0) {
				
				try
				{
					String max="";
					String use="";
					String rest="";
					String path="";
					String data="";
					String pro="";
					String f="";
					String fb="";
					String leng="";
					String late="";
					String wei="";
				XSSFRow row1 = sheet.getRow(i);
				System.out.println("");
				XSSFCell celllid = row1.getCell(0);
				celllid.setCellType(CellType.STRING);
				System.out.println("celllid="+celllid.toString());
				XSSFCell cellname = row1.getCell(1);
				cellname.setCellType(CellType.STRING);
				System.out.println("cellname="+cellname.toString());
				XSSFCell cellowner = row1.getCell(2);
				cellowner.setCellType(CellType.STRING);
				System.out.println("cellowner="+cellowner.toString());
				XSSFCell cellnA = row1.getCell(3);
				cellnA.setCellType(CellType.STRING);
				System.out.println("cellnA="+cellnA.toString());
				XSSFCell cellnB = row1.getCell(4);
				cellnB.setCellType(CellType.STRING);
				System.out.println("cellnB="+cellnB.toString());
				XSSFCell cellmax = row1.getCell(5);
				cellmax.setCellType(CellType.STRING);
				System.out.println("cellmax="+cellmax.toString());
				XSSFCell celluse = row1.getCell(6);
				celluse.setCellType(CellType.STRING);
				System.out.println("celluse="+celluse.toString());
				XSSFCell cellrest = row1.getCell(7);
				cellrest.setCellType(CellType.STRING);
				System.out.println("cellrest="+cellrest.toString());
				XSSFCell cellpath = row1.getCell(8);
				cellpath.setCellType(CellType.STRING);
				System.out.println("cellpath="+cellpath.toString());
				XSSFCell celldata = row1.getCell(9);
				celldata.setCellType(CellType.STRING);
				System.out.println("celldata="+celldata.toString());
				XSSFCell celltype = row1.getCell(10);
				celltype.setCellType(CellType.STRING);
				System.out.println("celltype="+celltype.toString());
				XSSFCell cellpro = row1.getCell(11);
				cellpro.setCellType(CellType.STRING);
				System.out.println("cellpro="+cellpro.toString());
				XSSFCell cellf = row1.getCell(12);
				cellf.setCellType(CellType.STRING);
				System.out.println("cellf="+cellf.toString());
				XSSFCell cellfb = row1.getCell(13);
				cellfb.setCellType(CellType.STRING);
				System.out.println("cellfb="+cellfb.toString());
				XSSFCell cellleng = row1.getCell(14);
				cellleng.setCellType(CellType.STRING);
				System.out.println("cellleng="+cellleng.toString());
				XSSFCell celllate = row1.getCell(15);
				celllate.setCellType(CellType.STRING);
				System.out.println("celllate="+celllate.toString());
				XSSFCell cellwei = row1.getCell(16);
				cellwei.setCellType(CellType.STRING);
				System.out.println("cellwei="+cellwei.toString());
				
				/*
				 uid=st1
				 * ,name=st2,msg=st3,type=st4,address=st5,tel=st6,hexcardid=st7,
				 * note=st8,box=st9,photo=st10
				 *  
				 */

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				
				SL=this.Savelink("1","",celllid.toString(), cellname.toString(),cellowner.toString(), cellnA.toString(),
						cellnB.toString(),cellmax.toString(),celluse.toString(),cellrest.toString(),cellpath.toString(),celldata.toString(),
						celltype.toString(),cellpro.toString(),cellf.toString(),cellfb.toString(),cellleng.toString(),
						celllate.toString(),cellwei.toString());
				System.out.println("SL="+SL);	
				//if needed can writ card to card marchine here
				count_success = count_success+1;

				}catch(Exception e) {
					if(rowLength > 0)
					{
						System.out.println(e);
						 // appends the string argument to the StringBuilder
						 count_fail.append(i+",");
					}	
						
					
					
				}
			} 

		}

		return Integer.toString(count_success)+"/"+count_fail+"/"+SL;
	}
	
	public void resetnodef() throws SQLException {
		pst=conn.prepareStatement("update sc_nodes set node_failure =0 where sysid >0 and del_mark is null;");
		pst.execute();
	}
	
	public void resetlinkf() throws SQLException {
		pst=conn.prepareStatement("update sc_links set failure =0 ,failure_bandwidth='0' where sysid >0;");
		pst.execute();
	}
	
	public void savepas(String st0,String st1,String st2) throws SQLException {
		pst=conn.prepareStatement("insert into sc_pass(userid,pass,cdate)values(?,?,?)");
		pst.setString(1, st0);
		pst.setString(2, st1);
		pst.setString(3, st2);
		pst.execute();
	}
} // end of class