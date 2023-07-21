package scsi.demo.wisoft;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import scsi.demo.wisoft.*;

public class SearchAllPath {
	
	private String nodenetworks[][] = {};
	private TestNode[] node = null ;
	private int maxPathnode = 0 ;
	/* �{�ɫO�s���|�`�I */
	public static Stack<TestNode> stack = new Stack<TestNode>();
	
	/* �x�s���|�����X */
	public static ArrayList<Object[]> sers = new ArrayList<Object[]>();
	
	/* �ˬd�`�I�O�_�b�{�ɫO�s�`�I�� */
	public static boolean isNodeInStack(TestNode node) {
		Iterator<TestNode> it = stack.iterator();
		while (it.hasNext()) {
			TestNode node1 = (TestNode) it.next();
			if (node == node1)
				return true;
		}
		return false;
	}

	/* ���@���� */
	public static void showAndSavePath() {
		Object[] o = stack.toArray();
		for (int i = 0; i < o.length; i++) {
			TestNode nNode = (TestNode) o[i];
	
			//if (i < (o.length - 1))
			//	System.out.print(nNode.getName() + "->");
			//else
			//	System.out.print(nNode.getName());
		}
		sers.add(o); //�x�s
		//System.out.println("\n");
	}
	
	/*
	 *cNode: �_�l�`�I
	 *pNode: ��e�_�l�`�I���W�@�Ӹ`�I
	 *sNode: �̪쪺�_�l�`�I
	 *eNode: ���I�`�I
	 */
	public static boolean getPaths(TestNode cNode, TestNode pNode, TestNode sNode, TestNode eNode) {
		TestNode nNode = null;
		//System.out.println(cNode+"/"+pNode+"/"+sNode+"/"+eNode);
		/* �p�G�ŦX����,��ܥX�{�`���N����, return false */
		if (cNode != null && pNode != null && cNode == pNode)
			return false;
		
		if (cNode != null) {
			int i = 0;
			stack.push(cNode);
			
			/* �p�G�_�l�`�I�N�O���I,�N�O���@���� */
			if (cNode == eNode) {
				showAndSavePath();
				return true;
			}else{
				//�p�G���O,�~����
				/* �qcNode���s�����Y���`�I�����Ǳo��@�Ӹ`�I, �����U�@�����_�l�I */
				try{
					nNode = cNode.getRelationNodes().get(i);
				}catch (Exception e){
					return false ;
				}
				while (nNode != null) {
					/*
					 *�p�GnNode�OsNode��nNode�OpNode��nNode�b�{�ɫO�s���|�`�I��,��ܲ��Ͱj��
					 *���s�b�P��e�_�l�I�����p���`�I�����
					 *pNode: cNode���W�@�`�I
					 */
					if (pNode != null && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {
						i++;
						if (i >= cNode.getRelationNodes().size())
							nNode = null;
						else
							nNode = cNode.getRelationNodes().get(i);
						continue;
					}
					
					/* �HnNode���s���_�l�I, cNode���W�@�Ӹ`�I, ���ƴM�� */
					if (getPaths(nNode, cNode, sNode, eNode))
					{
						/* �p�G���@���� */
						stack.pop();
					}
					
					/* �~��b�PcNode�����p���`�I�������� */
					i++;
					if (i >= cNode.getRelationNodes().size())
						nNode = null;
					else
						nNode = cNode.getRelationNodes().get(i);
				}
				/*
				 *�䧹�Ҧ��McNode�����p���`�I				 *
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
			/* �إ߸`�I */
		node = new TestNode[nodenetworks.length];
		
		/* �]�w�`�I�W�� */
		for (int i = 0; i < nodenetworks.length; i++) {
			node[i] = new TestNode();
			node[i].setName("node"+i);
			node[i].setWeight((float)(i*1.1));
			node[i].setShowName(sitename[i]);
		}
		
		/* �w�q�P�`�I�����p���`�I���X */
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
		/* �w�q�`�I���Y */
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
		
		/* �إ߸`�I */
		TestNode[] node1 = new TestNode[nodeRalation.length];
		
		/* �]�w�`�I�W�� */
		for (int i = 0; i < nodeRalation.length; i++) {
			node1[i] = new TestNode();
			node1[i].setName("node" + i);
			node1[i].setWeight((float)(i*1.1));
			node1[i].setShowName("AA");
		}
		
		/* �w�q�P�`�I�����p���`�I���X */
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
		
		/* �Ĥ@���}�l���椤�������Onull */
		getPaths(node1[a], null, null, node1[b]);
		
		//System.out.println(sers.size()); //�������|�ƶq
		/* sers.get(0)�OTestNode�����X */
		//System.out.println(((TestNode) sers.get(0)[0]).getName());
	}
	
	public static void main(String[] args) {

	}	
}