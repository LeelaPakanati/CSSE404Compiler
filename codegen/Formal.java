package codegen;
import java.util.*;
import parser.Tree;
import symbol.*;
import arch.*;
import codegen.Type;

public class Formal extends Node {
	Type type;
	String name;

	Formal(Tree parseTree){
		this.type = new Type(parseTree.getChild(0));
		this.name = parseTree.getChild(1).IDVal;
	}

	public String getType(){
		return this.type.type;
	}
}
