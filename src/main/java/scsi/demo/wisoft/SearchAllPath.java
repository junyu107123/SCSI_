package scsi.demo.wisoft;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class SearchAllPath {
	
	private String nodenetworks[][] = {};
	private TestNode[] node = null ;
	private int maxPathnode = 0 ;
	/* 臨時保存路徑節點 */
	public static Stack<TestNode> stack = new Stack<TestNode>();
	
	/* 儲存路徑的集合 */
	public static ArrayList<Object[]> sers = new ArrayList<Object[]>();
	
	/* 檢查節點是否在臨時保存節點中 */
	public static boolean isNodeInStack(TestNode node) {
		Iterator<TestNode> it = stack.iterator();
		while (it.hasNext()) {
			TestNode node1 = (TestNode) it.next();
			if (node == node1)
				return true;
		}
		return false;
	}

	/* 找到一條路 */
	public static void showAndSavePath() {
		Object[] o = stack.toArray();
		for (int i = 0; i < o.length; i++) {
			TestNode nNode = (TestNode) o[i];
	
			//if (i < (o.length - 1))
			//	System.out.print(nNode.getName() + "->");
			//else
			//	System.out.print(nNode.getName());
		}
		sers.add(o); //儲存
		//System.out.println("\n");
	}
	
	/*
	 *cNode: 起始節點
	 *pNode: 當前起始節點的上一個節點
	 *sNode: 最初的起始節點
	 *eNode: 終點節點
	 */
	public static boolean getPaths(TestNode cNode, TestNode pNode, TestNode sNode, TestNode eNode) {
		TestNode nNode = null;
		//System.out.println(cNode+"/"+pNode+"/"+sNode+"/"+eNode);
		/* 如果符合條件,表示出現循環就跳脫, return false */
		if (cNode != null && pNode != null && cNode == pNode)
			return false;
		
		if (cNode != null) {
			int i = 0;
			stack.push(cNode);
			
			/* 如果起始節點就是終點,就是找到一條路 */
			if (cNode == eNode) {
				showAndSavePath();
				return true;
			}else{
				//如果不是,繼續找路
				/* 從cNode有連接關係的節點按順序得到一個節點, 做為下一次的起始點 */
				try{
					nNode = cNode.getRelationNodes().get(i);
				}catch (Exception e){
					return false ;
				}
				while (nNode != null) {
					/*
					 *如果nNode是sNode或nNode是pNode或nNode在臨時保存路徑節點中,表示產生迴路
					 *重新在與當前起始點有關聯的節點中找路
					 *pNode: cNode的上一節點
					 */
					if (pNode != null && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {
						i++;
						if (i >= cNode.getRelationNodes().size())
							nNode = null;
						else
							nNode = cNode.getRelationNodes().get(i);
						continue;
					}
					
					/* 以nNode為新的起始點, cNode為上一個節點, 重複尋路 */
					if (getPaths(nNode, cNode, sNode, eNode))
					{
						/* 如果找到一條路 */
						stack.pop();
					}
					
					/* 繼續在與cNode有關聯的節點集中測試 */
					i++;
					if (i >= cNode.getRelationNodes().size())
						nNode = null;
					else
						nNode = cNode.getRelationNodes().get(i);
				}
				/*
				 *找完所有和cNode有關聯的節點				 *
				 */
				stack.pop();
				return false;
			}
		}else{
			return false;
		}
	}

	public void setNetworks(String[][] nodeRelations , String[] sitename){
		node = null ;
		nodenetworks = null;
		sers = new ArrayList<Object[]>();
		stack = new Stack<TestNode>();
		nodenetworks = nodeRelations ;
			/* 建立節點 */
		node = new TestNode[nodenetworks.length];
		
		/* 設定節點名稱 */
		for (int i = 0; i < nodenetworks.length; i++) {
			node[i] = new TestNode();
			node[i].setName("node"+i);
			node[i].setWeight((float)(i*1.1));
			node[i].setShowName(sitename[i]);
		}
		
		/* 定義與節點相關聯的節點集合 */
		for (int i = 0; i < nodenetworks.length; i++) {
			ArrayList<TestNode> List = new ArrayList<TestNode>();

			for (int j = 0; j < nodenetworks[i].length; j++) {
				for (int z = 0; z < nodenetworks.length; z++) {
					if (node[z].getName().equals("node" + nodenetworks[i][j])) {
						List.add(node[z]);
						break;
					}
				}
			}
			node[i].setRelationNodes(List);
			List = null;
		}
	}
	
	public ArrayList getResultArray(){
		return sers ;
	}
	
	public int getMaxPathNode(){
		return maxPathnode;
	}
	
	public ArrayList runNow(int a, int b){
		getPaths(node[a], null, null, node[b]);
		maxPathnode = 0 ;
		ArrayList al = new ArrayList();
		for (int u= 0 ; u<sers.size() ; u++){
			if(maxPathnode < sers.get(u).length) maxPathnode = sers.get(u).length;
			String tmp = Integer.toString(sers.get(u).length)+",";
			float cnt = 0.0f;
			for (int v=0 ; v<sers.get(u).length ; v++){
				TestNode tnd = (TestNode) sers.get(u)[v];
				tmp += (v>0?"->":"")+tnd.getName()+"/"+tnd.getWeight()+"/"+tnd.getShowName();
				cnt += tnd.getWeight();
			}
			tmp += ","+Float.toString(cnt);
			if(al.size() == 0){
				al.add(tmp);
			}else{
				int insertnum = 0 ;
				for (int x=0 ; x < al.size() ; x++){
					int q = Integer.parseInt(al.get(x).toString().split(",")[0]);
					if(sers.get(u).length < q){
						al.add(x,tmp);
						insertnum = 1 ;
						break;
					}
				}
				if(insertnum ==0) al.add(tmp);
			}
		}
		return al ;
	}
	
	public void runPath(int a, int b){
		/* 定義節點關係 */
		String nodeRalation[][] = { { "10" }, // 0,A
				{ "10", "13", "14", "15", "17" }, // 1,B
				{ "9", "10" }, // 2,C
				{ "10", "17" }, // 3,D
				{ "8", "9", "11", "12", "17" }, // 4,E
				{ "9", "12" }, // 5,F
				{ "" }, // 6,G
				{ "9", "12" }, // 7,H
				{ "4" }, // 8,I
				{ "2", "4", "5", "7" }, // 9,J
				{ "0", "1", "2", "3", "12", "14", "16" }, // 10,K
				{ "4" }, // 11,L
				{ "4", "5", "7", "10" }, // 12,M
				{ "1" }, // 13,N
				{ "1", "10" }, // 14,O
				{ "1" }, // 15,P
				{ "10" }, // 16,Q
				{ "1", "3", "4" } // 17,R
		};
		
		/* 建立節點 */
		TestNode[] node1 = new TestNode[nodeRalation.length];
		
		/* 設定節點名稱 */
		for (int i = 0; i < nodeRalation.length; i++) {
			node1[i] = new TestNode();
			node1[i].setName("node" + i);
			node1[i].setWeight((float)(i*1.1));
			node1[i].setShowName("AA");
		}
		
		/* 定義與節點相關聯的節點集合 */
		for (int i = 0; i < nodeRalation.length; i++) {
			ArrayList<TestNode> List = new ArrayList<TestNode>();

			for (int j = 0; j < nodeRalation[i].length; j++) {
				for (int z = 0; z < nodeRalation.length; z++) {
					if (node1[z].getName().equals("node" + nodeRalation[i][j])) {
						List.add(node1[z]);
						break;
					}
				}
			}
			node1[i].setRelationNodes(List);
			List = null;
		}	
		
		/* 第一次開始執行中間必須是null */
		getPaths(node1[a], null, null, node1[b]);
		
		//System.out.println(sers.size()); //全部路徑數量
		/* sers.get(0)是TestNode的集合 */
		//System.out.println(((TestNode) sers.get(0)[0]).getName());
	}
	
	public static void main(String[] args) {

	}	
}