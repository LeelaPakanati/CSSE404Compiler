package parser;
import java.util.*;

public class Tree<T> {
	public T data;
	public Tree<T> parent;
	public List<Tree<T>> children;

    public Tree(T data, Tree<T> parent) {
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<Tree<T>>();
    }

    public Tree() {
		this.data = null;
		this.parent = null;
		this.children = new ArrayList<Tree<T>>();
    }

	public void addChild(Tree<T> child){
		this.children.add(child);
	}

	public String toString(int indent){
		String toReturn = " ".repeat(indent) + this.data + "\n";

		for(Tree<T> child : this.children){
			toReturn = toReturn + child.toString(indent+1);
		}

		return toReturn;
	}
}
