package parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;

public class Parser {
	ArrayList<String[]> toParse;
	int parseIndex;
	HashMap<String, String[]> table = new HashMap<String, String[]>();
	String[] terminals;
	public Tree parseTree;

	public Parser(ArrayList<String[]> toParse) {
		this.toParse = toParse;
		this.parseIndex = 0;

		this.terminals = new String[]{"class","ID","{","public","static","void","main","(","String","[","]",")","}","extends",";","return",",","int","boolean","if","else","while","System.out.println","=","||","&&","==","!=","<","<=",">=",">","+","-","*","/","new","!","this","Integer","null","true","false",".","length","$"};
		
		this.table.put("Program", new String[] {"MainClassDecl ClassDeclList $",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("MainClassDecl", new String[] {"class ID { public static void main ( String [ ] ID ) { StmtList } }",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("ClassDeclList", new String[] {"ClassDecl ClassDeclList",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"''"});
		this.table.put("ClassDecl", new String[] {"class ID ClassDeclPrime { ClassVarDeclList MethodDeclList }",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("ClassDeclPrime", new String[] {null,null,"''",null,null,null,null,null,null,null,null,null,null,"extends ID",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("ClassVarDeclList", new String[] {"''","ClassVarDecl ClassVarDeclList",null,"''",null,null,null,null,null,null,null,null,"''",null,null,null,null,"ClassVarDecl ClassVarDeclList","ClassVarDecl ClassVarDeclList",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"''"});
		this.table.put("ClassVarDecl", new String[] {null,"Formal ;",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Formal ;","Formal ;",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("MethodDeclList", new String[] {null,null,null,"MethodDecl MethodDeclList",null,null,null,null,null,null,null,null,"''",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("MethodDecl", new String[] {null,null,null,"public Formal ( FormalList ) { StmtList return Expr ; }",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("FormalList", new String[] {null,"Formal FormalListPrime",null,null,null,null,null,null,null,null,null,"''",null,null,null,null,null,"Formal FormalListPrime","Formal FormalListPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("FormalListPrime", new String[] {null,null,null,null,null,null,null,null,null,null,null,"''",null,null,null,null,", Formal FormalListPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("Formal", new String[] {null,"Type ID",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Type ID","Type ID",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("Type", new String[] {null,"ID",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"int IsArr","boolean",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("IsArr", new String[] {null,"''",null,null,null,null,null,null,null,"[ HasExpr ]",null,null,null,null,"''",null,null,null,null,null,null,null,null,"''",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("HasExpr", new String[] {null,"Expr",null,null,null,null,null,"Expr",null,null,"''",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Expr",null,null,"Expr","Expr","Expr","Expr","Expr","Expr","Expr",null,null,null});
		this.table.put("StmtList", new String[] {null,"Stmt StmtList",null,null,null,null,null,null,null,null,null,null,"''",null,null,"''",null,"Stmt StmtList","Stmt StmtList","Stmt StmtList",null,"Stmt StmtList","Stmt StmtList",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("Stmt", new String[] {null,"AtomicStmt",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"AtomicStmt","AtomicStmt","if ( Expr ) BracketStmt else BracketStmt",null,"while ( Expr ) BracketStmt","AtomicStmt",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("BracketStmt", new String[] {null,"Stmt","{ StmtList }",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Stmt","Stmt","Stmt",null,"Stmt","Stmt",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("AtomicStmt", new String[] {null,"ID IsArr IDAssignment",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"int IsArr ID IsArr Assignment","boolean ID IsArr Assignment",null,null,null,"System.out.println ( Expr ) ;",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("IDAssignment", new String[] {null,"ID IsArr Assignment",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"= Expr ;",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("Assignment", new String[] {null,null,null,null,null,null,null,null,null,null,null,null,null,null,";",null,null,null,null,null,null,null,null,"= Expr ;",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("ExprList", new String[] {null,"Expr ExprListPrime",null,null,null,null,null,"Expr ExprListPrime",null,null,null,"''",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Expr ExprListPrime",null,null,"Expr ExprListPrime","Expr ExprListPrime","Expr ExprListPrime","Expr ExprListPrime","Expr ExprListPrime","Expr ExprListPrime","Expr ExprListPrime",null,null,null});
		this.table.put("ExprListPrime", new String[] {null,null,null,null,null,null,null,null,null,null,null,"''",null,null,null,null,", Expr ExprListPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("Expr", new String[] {null,"OrOperand ExprPrime",null,null,null,null,null,"OrOperand ExprPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"OrOperand ExprPrime",null,null,"OrOperand ExprPrime","OrOperand ExprPrime","OrOperand ExprPrime","OrOperand ExprPrime","OrOperand ExprPrime","OrOperand ExprPrime","OrOperand ExprPrime",null,null,null});
		this.table.put("ExprPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"|| OrOperand ExprPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("OrOperand", new String[] {null,"AndOperand OrOperandPrime",null,null,null,null,null,"AndOperand OrOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"AndOperand OrOperandPrime",null,null,"AndOperand OrOperandPrime","AndOperand OrOperandPrime","AndOperand OrOperandPrime","AndOperand OrOperandPrime","AndOperand OrOperandPrime","AndOperand OrOperandPrime","AndOperand OrOperandPrime",null,null,null});
		this.table.put("OrOperandPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","&& AndOperand OrOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("AndOperand", new String[] {null,"EqualityOperand AndOperandPrime",null,null,null,null,null,"EqualityOperand AndOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"EqualityOperand AndOperandPrime",null,null,"EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime","EqualityOperand AndOperandPrime",null,null,null});
		this.table.put("AndOperandPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","''","== EqualityOperand AndOperandPrime","!= EqualityOperand AndOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("EqualityOperand", new String[] {null,"RelOperand EqualityOperandPrime",null,null,null,null,null,"RelOperand EqualityOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"RelOperand EqualityOperandPrime",null,null,"RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime","RelOperand EqualityOperandPrime",null,null,null});
		this.table.put("EqualityOperandPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","''","''","''","< RelOperand EqualityOperandPrime","<= RelOperand EqualityOperandPrime",">= RelOperand EqualityOperandPrime","> RelOperand EqualityOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("RelOperand", new String[] {null,"AddOperand RelOperandPrime",null,null,null,null,null,"AddOperand RelOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"AddOperand RelOperandPrime",null,null,"AddOperand RelOperandPrime","AddOperand RelOperandPrime","AddOperand RelOperandPrime","AddOperand RelOperandPrime","AddOperand RelOperandPrime","AddOperand RelOperandPrime","AddOperand RelOperandPrime",null,null,null});
		this.table.put("RelOperandPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","''","''","''","''","''","''","''","+ AddOperand RelOperandPrime","- AddOperand RelOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("AddOperand", new String[] {null,"Value AddOperandPrime",null,null,null,null,null,"Value AddOperandPrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"Value AddOperandPrime",null,null,"Value AddOperandPrime","Value AddOperandPrime","Value AddOperandPrime","Value AddOperandPrime","Value AddOperandPrime","Value AddOperandPrime","Value AddOperandPrime",null,null,null});
		this.table.put("AddOperandPrime", new String[] {null,null,null,null,null,null,null,null,null,null,"''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","''","''","''","''","''","''","''","''","''","* Value AddOperandPrime","/ Value AddOperandPrime",null,null,null,null,null,null,null,null,null,null});
		this.table.put("Value", new String[] {null,"ID ValuePrime",null,null,null,null,null,"( Expr ) ValuePrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"- Value",null,null,"new NewWhat","! Value","this ValuePrime","Integer","null","true","false",null,null,null});
		this.table.put("NewWhat", new String[] {null,"ID ( ) ValuePrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"int [ Expr ]",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null});
		this.table.put("ValuePrime", new String[] {null,null,null,null,null,null,null,null,null,"[ Expr ] ValuePrime","''","''",null,null,"''",null,"''",null,null,null,null,null,null,null,"''","''","''","''","''","''","''","''","''","''","''","''",null,null,null,null,null,null,null,". DotWhat",null,null});
		this.table.put("DotWhat", new String[] {null,"ID ( ExprList ) ValuePrime",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"length",null});

		//	System.out.print(key + " : ");
		//	for(String str : this.table.get(key)){
		//		if(str != null){
		//			System.out.print(str + ", ");
		//		}
		//	}
		//	System.out.print("\n");
		//}
	}

	private String[] nextWord(){
		return this.toParse.get(this.parseIndex++);
	}

	public Boolean parseProgram(){
		Stack<String> symbolStack = new Stack<String>();
		symbolStack.push("Program");

		String[] lexed = nextWord();
		this.parseTree = new Tree("Tree: ", null, false);
		Tree currNode = this.parseTree;
		String focus = symbolStack.peek();

		while(true){
			//System.out.println("Focus : " + focus + "\tWord : " + lexed[1]);
			if (focus.equals("../")){
				//System.out.println("Returning to parent: " + currNode.parent.data);
				currNode = currNode.parent;
				symbolStack.pop();
			}else if (focus.equals("$") && lexed[1].equals("$")){
				break;
			}else if (Arrays.asList(terminals).contains(focus) || focus.equals("$")){
				//If focus matches word
				if( ((focus.equals("ID") || focus.equals("Integer")) && focus.equals(lexed[0])) || focus.equals(lexed[1]) ){
					String poppedString = symbolStack.pop();

					Tree nextNode;

					nextNode = new Tree(poppedString, currNode, true);
					if(focus.equals("ID")) {
						nextNode.IDVal = lexed[1];
					}else if(focus.equals("Integer")){
						nextNode.intVal = Integer.valueOf(lexed[1]);
					}

					currNode.addChild(nextNode);
					
					lexed = nextWord();
				}else{
					System.out.println("Error at reading symbol " + focus + "; looking at word " + lexed[0] + " : " + lexed[1]);
					return false;
				}
			}else{
				int wordIndex;
				if(lexed[0] == "ID" || lexed[0] == "Integer"){
					wordIndex = Arrays.asList(this.terminals).indexOf(lexed[0]);
				}else{
					wordIndex = Arrays.asList(this.terminals).indexOf(lexed[1]);
				}
				String symbolLookup = this.table.get(focus)[wordIndex];
				if(symbolLookup != null){
					List<String> symbols = Arrays.asList(symbolLookup.split(" "));
					String poppedString = symbolStack.pop();

					Tree nextNode = new Tree(poppedString, currNode, false);
					currNode.addChild(nextNode);
					currNode = nextNode;
					
					Collections.reverse(symbols);
					symbolStack.push("../");
					if(!symbols.get(0).equals("''")){
						for(String symbol : symbols){
							symbolStack.push(symbol);
						}
					}

				}else{
					System.out.println("Error following " + focus);
					return false;
				}
			}
			focus = symbolStack.peek();
		}

		return true;
	}
}
