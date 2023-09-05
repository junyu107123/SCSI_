package scsi.demo.wisoft;
import java.util.ArrayList;

public class TestNode {
	public String name = null;
	public float weight = 0.0f;
	public String showname = null;
	public ArrayList<TestNode> relationNodes = new ArrayList<TestNode>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowName() {
		return showname;
	}

	public void setShowName(String sname) {
		this.showname = sname;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float wt) {
		this.weight = wt;
	}
	

	public ArrayList<TestNode> getRelationNodes() {
		return relationNodes;
	}

	public void setRelationNodes(ArrayList<TestNode> relationNodes) {
		this.relationNodes = relationNodes;
	}
}