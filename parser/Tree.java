package parser;
import java.util.*;

public class Tree{
	public String data;
	public int intVal;
	public String IDVal;
	public Tree parent;
	public boolean terminal;
	public List<Tree> children;

    public Tree(String data, Tree parent, boolean terminal) {
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<Tree>();
		this.terminal = terminal;
    }

    public Tree() {
		this.data = null;
		this.parent = null;
		this.children = new ArrayList<Tree>();
		this.terminal = false;
    }

	public void addChild(Tree child){
		this.children.add(child);
	}

	public Tree getChild(int num){
		try{
			return this.children.get(num);
		} catch(Exception e) {
			return null;
		}
	}

	public String toString(int indent){
		String toReturn = " ".repeat(indent) + (this.terminal ? "T: ":"") +  this.data;
		if(this.data.equals("ID")){
			toReturn += " : " + this.IDVal;
		} else if(this.data.equals("Integer")){
			toReturn += " : " + this.intVal;
		}
		toReturn += "\n";

		for(Tree child : this.children){
			toReturn += child.toString(indent+1);
		}

		return toReturn;
	}

	public Tree getExprValue(){
		Tree currNode = this;
		while(!currNode.data.equals("Value")){
			currNode = currNode.getChild(0);
		}
		return currNode;
	}

	public Tree getExprOp(){
		Tree currNode = this;

		if(currNode.data.equals("Value") || currNode.data.equals("ValuePrime")){
			return null;
		}

		while(currNode.getChild(1).getChild(0) == null){
			currNode = currNode.getChild(0);
			if(currNode.data.equals("Value") || currNode.data.equals("ValuePrime")){
				return null;
			}
		}

		return currNode.getChild(1);
	}
}
