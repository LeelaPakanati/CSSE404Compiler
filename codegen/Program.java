package codegen;
import java.util.*;
import parser.Tree;
import symbol.*;

abstract class Node{
	public String CodeGen(){
		return " ";
	}
}

public class Program extends Node {
	MainClassDecl mainClass;
	List<ClassDecl> classList = new ArrayList<ClassDecl>();

	public Program(Tree parseTree){
		this.mainClass = new MainClassDecl(parseTree.getChild(0));

		Tree classDecl = parseTree.getChild(1);
		while(classDecl.getChild(0) != null){
			this.classList.add(new ClassDecl(classDecl.getChild(0)));
			classDecl = classDecl.getChild(1);
		}
	}

	public String CodeGen(){
		String asm = "";
		asm += "Main Class:\n";
		asm += mainClass.CodeGen();
		asm += "\nOther Classes:\n\n";
		for(ClassDecl classDecl : this.classList){
			asm += classDecl.CodeGen();
			asm += "\n";
		}
		return asm;
	}
}

class MainClassDecl extends Node {
	String classname;
	String args;
	List<Stmt> stmtList = new ArrayList<Stmt>();

	MainClassDecl(Tree parseTree){
		this.classname = parseTree.getChild(1).IDVal;

		ClassSymbol classSymbol = new ClassSymbol(this.classname);
		SymbolTable.addClass(this.classname, classSymbol);
		SymbolTable.ScopeAdd(classSymbol);

		this.args = parseTree.getChild(11).IDVal;

		Tree stmt = parseTree.getChild(14);
		do{
			this.stmtList.add(Stmt.getInstance(stmt.getChild(0)));
			stmt = stmt.getChild(1);
		}while(stmt.getChild(0) != null);

		SymbolTable.ScopePop();
	}

	public String CodeGen(){
		String asm = "";
		asm += this.classname + " <= " + this.args;

		for(Stmt stmt : this.stmtList){
			asm += stmt.CodeGen();
		}
		asm += "\n";
		return asm;
	}
}

class ClassDecl extends Node {
	String classname;
	String superclass;
	List<ClassVarDecl> varDeclList = new ArrayList<ClassVarDecl>();
	List<MethodDecl> methodDeclList = new ArrayList<MethodDecl>();

	ClassDecl(Tree parseTree){
		this.classname = parseTree.getChild(1).IDVal;

		ClassSymbol classSymbol = new ClassSymbol(this.classname);
		SymbolTable.addClass(this.classname, classSymbol);
		SymbolTable.ScopeAdd(classSymbol);

		if(parseTree.getChild(2).getChild(1) != null){
			this.superclass = parseTree.getChild(2).getChild(1).IDVal;
		}

		Tree varDecl = parseTree.getChild(4);
		while(varDecl.getChild(0) != null){
			this.varDeclList.add(new ClassVarDecl(varDecl.getChild(0)));
			varDecl = varDecl.getChild(1);
		}

		Tree methodDecl = parseTree.getChild(5);
		do{
			this.methodDeclList.add(new MethodDecl(methodDecl.getChild(0)));
			methodDecl = methodDecl.getChild(1);
		}while(methodDecl.getChild(0) != null);

		SymbolTable.ScopePop();
	}

	public String CodeGen(){
		String asm = "ClassDecl: ";
		asm += this.classname;
		if(this.superclass != null){
			asm += " extends " + this.superclass;
		}

		asm += "\nClass Variables:\n";
		for(ClassVarDecl varDecl : this.varDeclList){
			asm += varDecl.CodeGen();
		}

		asm += "\nClass Methods:\n";
		for(MethodDecl methodDecl : this.methodDeclList){
			asm += methodDecl.CodeGen();
		}
		asm += "\n";
		return asm;
	}
}

class ClassVarDecl extends Node {
	Type varType;
	String varName;

	ClassVarDecl(Tree parseTree){
		this.varType = new Type(parseTree.getChild(1).getChild(0));
		this.varName = parseTree.getChild(0).getChild(1).IDVal;
		VarSymbol varSymbol = new VarSymbol(this.varName, varType.type);
		SymbolTable.addSymbol(varSymbol);

	}
}


class MethodDecl extends Node {
	Type methodtype;
	String methodname;
	List<Formal> argList;
	List<Stmt> stmtList;
	Expr returnVal;

	MethodDecl(Tree parseTree){
		this.argList  = new ArrayList<Formal>();
		this.stmtList = new ArrayList<Stmt>();

		this.methodtype = new Type(parseTree.getChild(1).getChild(0));
		this.methodname = parseTree.getChild(1).getChild(1).IDVal;

		MethodSymbol methodSymbol = new MethodSymbol(this.methodname, this.methodtype.type);
		SymbolTable.addSymbol(this.methodname, methodSymbol);
		SymbolTable.ScopeAdd(methodSymbol);

		Tree arg = parseTree.getChild(3);
		if(arg.getChild(0) != null){
			this.argList.add(new Formal(arg.getChild(0)));
			arg = arg.getChild(1);

			while(arg.getChild(0) != null){
				this.argList.add(new Formal(arg.getChild(1)));
				arg = arg.getChild(2);
			}
		}

		Tree stmt = parseTree.getChild(6);
		while(stmt.getChild(0) != null){
			this.stmtList.add(Stmt.getInstance(stmt.getChild(0)));
			stmt = stmt.getChild(1);
		}
		this.returnVal = Expr.getInstance(parseTree.getChild(8));

		SymbolTable.ScopePop();
	}

	public String CodeGen(){
		String asm = "Method: ";
		asm += this.methodname + "\nreturn type: " + this.methodtype.CodeGen();
		asm += "\n";
		asm += "Arguments: ";
		for(Formal arg : this.argList){
			asm += arg.CodeGen();
			asm += " ";
		}
		asm += "\n";

		for(Stmt stmt : this.stmtList){
			asm += stmt.CodeGen();
		}
		asm += "\n";
		return asm;
	}
}

class Formal extends Node {
	Type type;
	String name;

	Formal(Tree parseTree){
		this.type = new Type(parseTree.getChild(0));
		this.name = parseTree.getChild(1).IDVal;
	}

	public String CodeGen(){
		String asm = "";
		asm += this.type.CodeGen() + ":" + this.name;
		return asm;
	}
}

class Type extends Node {
	String type;

	Type(Tree parseTree){
		if(parseTree.getChild(0).data.equals("ID")){
			this.type = parseTree.getChild(0).IDVal;
		} else{
			this.type = parseTree.getChild(0).data;
		}

		if(this.type == "int" && parseTree.getChild(1) != null){
			this.type = "int[]";
		}
	}

	public String CodeGen(){
		String asm = this.type;
		return asm;
	}

}

class Stmt extends Node {
	static Stmt getInstance(Tree parseTree){
		Tree firstChild = parseTree.getChild(0);
		switch(firstChild.data){
			case "if":
				return new IfStmt(parseTree);
			case "while":
				return new WhileStmt(parseTree);
			case "AtomicStmt":
				//recurse to statement
				return Stmt.getInstance(firstChild);
			case "Stmt":
				//recurse to statement
				return Stmt.getInstance(firstChild);
			case "{":
				return new BracketStmt(parseTree);
			case "System.out.println":
				return new PrintStmt(parseTree);
			case "int":
				return new VarDeclAssignStmt(parseTree);
			case "boolean":
				return new VarDeclAssignStmt(parseTree);
			case "ID":
				if(parseTree.getChild(1).getChild(0) != null){
					return new ArrVarAssignStmt(parseTree);
				} else if(parseTree.getChild(2).getChild(0).data.equals("=")){
					return new VarAssignStmt(parseTree);
				} else if(parseTree.getChild(2).getChild(0).data.equals("ID")){
					return new VarDeclAssignStmt(parseTree);
				} else {
					//fail
					System.out.println("Stmt->ID Failed");
					return null;
				}
			default:
				//fail
				System.out.println("Stmt Switch Failed");
				System.out.println(parseTree.toString(0));
				return null;
		}
	}
}

class BracketStmt extends Stmt {
	List<Stmt> stmtList = new ArrayList<Stmt>();

	BracketStmt(Tree parseTree){
		Tree stmt = parseTree.getChild(1);
		do{
			this.stmtList.add(Stmt.getInstance(stmt.getChild(0)));
			stmt = stmt.getChild(1);
		}while(stmt.getChild(0) != null);
	}

	public String CodeGen(){
		String asm = "";
		
		for(Stmt stmt : this.stmtList){
			asm += stmt.CodeGen();
		}

		asm += "\n";
		return asm;
	}
}

class VarDeclAssignStmt extends Stmt {
	String varType;
	String varName;
	Expr value;

	VarDeclAssignStmt(Tree parseTree){
		if(parseTree.getChild(0).data.equals("ID")){
			this.varType = parseTree.getChild(0).IDVal;
		} else{
			this.varType = parseTree.getChild(0).data;
		}

		Tree Assignment;
		if(parseTree.getChild(0).data.equals("ID")){
			this.varName = parseTree.getChild(2).getChild(0).IDVal;
			Assignment = parseTree.getChild(2).getChild(2);
		} else if(parseTree.getChild(0).data.equals("int")){
			this.varName = parseTree.getChild(2).IDVal;
			Assignment = parseTree.getChild(4);
		}else{
			this.varName = parseTree.getChild(1).IDVal;
			Assignment = parseTree.getChild(3);
		}

		if(Assignment.getChild(0).data.equals("=")){
			this.value = Expr.getInstance(Assignment.getChild(1));
		}
	}

	public String CodeGen(){
		String asm = "Var Declaration: ";
		asm += " Type: " + this.varType; 
		asm += " Name: " + this.varName; 
		if(this.value != null){
			asm += " <= " + this.value.CodeGen();
		}
		asm += "\n";
		return asm;
	}
}

class IfStmt extends Stmt {
	Expr condition;
	Stmt trueStmt;
	Stmt falseStmt;

	IfStmt(Tree parseTree){
		this.condition = Expr.getInstance(parseTree.getChild(2));
		this.trueStmt = Stmt.getInstance(parseTree.getChild(4));
		this.falseStmt = Stmt.getInstance(parseTree.getChild(6));
	}

	public String CodeGen(){
		String asm = "";
		asm += this.condition.CodeGen() + "\n";
		asm += "R ? jumpTo TrueStmt : jumpTo FalseStmt\n";
		asm += "TrueStmt:\n" + this.trueStmt.CodeGen();
		asm += "FalseStmt:\n" + this.falseStmt.CodeGen();
		return asm;
	}
}

class WhileStmt extends Stmt {
	Expr condition;
	Stmt trueStmt;

	WhileStmt(Tree parseTree){
		this.condition = Expr.getInstance(parseTree.getChild(2));
		this.trueStmt = Stmt.getInstance(parseTree.getChild(4));
	}

	public String CodeGen(){
		String asm = "WhileStart:\n";
		asm += this.condition.CodeGen() + "\n";
		asm += "R ? jumpTo WhileEnd\n";
		asm += this.trueStmt.CodeGen();
		asm += "jumpTo WhileStart\n";
		asm = "WhileEnd:\n";
		return asm;
	}
}

class PrintStmt extends Stmt {
	Expr printStmt;

	PrintStmt(Tree parseTree){
		this.printStmt = Expr.getInstance(parseTree.getChild(2));
	}

	public String CodeGen(){
		String asm = "";
		asm += "rP <= " + this.printStmt.CodeGen();
		asm += "Store at ScreenPrint rP";
		return asm;
	}
}


class VarAssignStmt extends Stmt {
	String varName;
	Expr value;

	VarAssignStmt(Tree parseTree){
		this.varName = parseTree.getChild(0).IDVal;
		this.value = Expr.getInstance(parseTree.getChild(2).getChild(1));
	}

	public String CodeGen(){
		String asm = "";
		asm += this.value.CodeGen();
		asm += "Reg[" + this.varName + "]" + " <= R\n";
		return asm;
	}
}

class ArrVarAssignStmt extends Stmt {
	String varName;
	Expr arrRef;
	Expr value;

	ArrVarAssignStmt(Tree parseTree){
		this.varName = parseTree.getChild(0).IDVal;
		this.arrRef = Expr.getInstance(parseTree.getChild(1).getChild(1).getChild(0));
		this.value = Expr.getInstance(parseTree.getChild(2).getChild(1));
	}

	public String CodeGen(){
		String asm = "";
		asm += this.arrRef.CodeGen();
		asm += "rA <= R";
		asm += this.value.CodeGen();
		asm += "Reg[" + this.varName + " + rA ]" + " <= R\n";
		return asm;
	}
}

class Expr extends Node {

	static Expr getInstance(Tree parseTree){
		if(parseTree.getExprOp() != null){
			return new OpExpr(parseTree);
		}else{
			Tree value = parseTree.getExprValue();
			Tree valueFirstChild = value.getChild(0);
			switch(valueFirstChild.data){
				case "ID":
					if(value.getChild(1).getChild(0) != null){
						if(value.getChild(1).getChild(0).data.equals(".")){
							Tree dotWhat = value.getChild(1).getChild(1);

							if(dotWhat.getChild(0).data.equals("ID")){
								return new ClassMethodCallExpr(value);
							} else if(dotWhat.getChild(0).data.equals("length")){
								return new LengthExpr(value);
							} else{
								//fail
								System.out.println("Expr->ID->. Failed");
								return null;
							}
						} else if(value.getChild(1).getChild(0).data.equals("[")){
							return new ArrExpr(value);
						} else{
							//fail
							System.out.println("Expr->ID Failed");
							return null;
						}
					} else{
						return new IDExpr(value);
					}

				case "new":
					Tree newWhat = value.getChild(1);
					if(newWhat.getChild(0).data.equals("ID")){
						if(newWhat.getChild(1).data.equals("(")){
							if(newWhat.getChild(2).getChild(0) != null){
								return new ClassMethodCallExpr(value);
							} else{
								return new ClassConstructorExpr(value);
							}
						} else{
							//fail
							System.out.println("Expr->new->ID Failed");
							return null;
						}
					} else if(newWhat.getChild(0).data.equals("int")){
						if(newWhat.getChild(1).data.equals("[")){
							return new IntArrConstructorExpr(value);
						} else{
							//fail
							System.out.println("Expr->new->int Failed");
							return null;
						}
					} else{
						//fail
						System.out.println("Expr->new Failed");
						return null;
					}

				case "!":
					return new UnaryExpr(value);

				case "-":
					return new UnaryExpr(value);

				case "(":
					if(value.getChild(3).getChild(0) != null){
						if(value.getChild(3).getChild(0).data.equals(".")){
							Tree dotWhat = value.getChild(3).getChild(1);

							if(dotWhat.getChild(0).data.equals("ID")){
								return new ClassMethodCallExpr(value);
							} else{
								break;
							}
						} else{
							//fail
							System.out.println("Expr->( Failed");
							return null;
						}
					} else{
						return new ParenExpr(value);
					}

				case "this":
					if(value.getChild(1).getChild(0) != null){
						if(value.getChild(1).getChild(0).data.equals(".")){
							Tree dotWhat = value.getChild(1).getChild(1);

							if(dotWhat.getChild(0).data.equals("ID")){
								return new ClassMethodCallExpr(value);
							} else{
								//fail
								System.out.println("Expr->this->. Failed");
								return null;
							}
						} else{
							//fail
							System.out.println("Expr->this Failed");
							return null;
						}
					} else{
						return new ThisExpr(value);
					}

				case "Integer":
					return new IntegerExpr(value);

				default:
					return new TExpr(value);
			}
		}
		return null;
	}

}

class OpExpr extends Expr {
	Expr leftExpr;
	String operation;
	Expr rightExpr;

	OpExpr(Tree parseTree){
		this.leftExpr = Expr.getInstance(parseTree.getExprValue());
		Tree prime = parseTree.getExprOp();
		this.operation = prime.getChild(0).data;
		this.rightExpr = Expr.getInstance(prime.getChild(1));
	}

	public String CodeGen(){
		String asm = "";
		asm += this.leftExpr.CodeGen() + "\n";
		asm += "rL <= R\n";
		asm += this.rightExpr.CodeGen() + "\n";
		asm += "rR <= R\n";
		asm += "R <= " + this.operation + " rL rR" + "\n";
		return asm;
	}
}

class UnaryExpr extends Expr {
	String operation;
	Expr expr;

	UnaryExpr(Tree parseTree){
		this.operation = parseTree.getChild(0).data;
		this.expr = Expr.getInstance(parseTree.getChild(1));
	}

	public String CodeGen(){
		String asm = "";
		asm += this.expr.CodeGen() + "\n";
		asm += "R <= " + this.operation + " R" + "\n";
		return asm;
	}
}

class ArrExpr extends Expr {
	String arrName;
	Expr arrRef;

	ArrExpr(Tree parseTree){
		this.arrName = parseTree.getChild(0).IDVal;
		this.arrRef = Expr.getInstance(parseTree.getChild(1).getChild(1));
	}
}

class LengthExpr extends Expr {
	String arrName;

	LengthExpr(Tree parseTree){
		this.arrName = parseTree.getChild(0).IDVal;
	}
}

class ClassMethodCallExpr extends Expr {
	Expr className;
	String method;
	List<Expr> argList = new ArrayList<Expr>();

	ClassMethodCallExpr(Tree parseTree){
		Tree firstChild = parseTree.getChild(0);
		Tree dotWhat;

		if(firstChild.data.equals("(")){
			this.className = new ParenExpr(parseTree);
			dotWhat = parseTree.getChild(3).getChild(1);

		} else if(firstChild.data.equals("ID")){
			this.className = new IDExpr(parseTree);
			dotWhat = parseTree.getChild(1).getChild(1);

		} else if(firstChild.data.equals("this")){
			this.className = new ThisExpr(parseTree);
			dotWhat = parseTree.getChild(1).getChild(1);

		} else if(firstChild.data.equals("new")){
			this.className = new ClassConstructorExpr(parseTree);
			dotWhat = parseTree.getChild(1).getChild(2).getChild(4);

		} else{
			//fail
			return;
		}

		this.method = dotWhat.getChild(0).IDVal;

		Tree arg = dotWhat.getChild(2);
		if(arg.getChild(0) != null){
			this.argList.add(Expr.getInstance(arg.getChild(0)));
			arg = arg.getChild(1);

			while(arg.getChild(0) != null){
				this.argList.add(Expr.getInstance(arg.getChild(1)));
				arg = arg.getChild(2);
			}
		}

	}
}

class ClassConstructorExpr extends Expr {
	String className;

	ClassConstructorExpr(Tree parseTree){
		this.className = parseTree.getChild(1).getChild(0).IDVal;
	}
}

class IntArrConstructorExpr extends Expr {
	Expr arrLength;

	IntArrConstructorExpr(Tree parseTree){
		this.arrLength = Expr.getInstance(parseTree.getChild(1).getChild(2));
	}
}

class ParenExpr extends Expr {
	Expr expr;

	ParenExpr(Tree parseTree){
		this.expr = Expr.getInstance(parseTree.getChild(1));
	}
	public String CodeGen(){
		return this.expr.CodeGen();
	}
}

class IDExpr extends Expr {
	String id;

	IDExpr(Tree parseTree){
		this.id = parseTree.getChild(0).IDVal;
	}
	public String CodeGen(){
		return "R <= R[" + this.id + "]\n";
	}
}

class IntegerExpr extends Expr {
	int intVal;

	IntegerExpr(Tree parseTree){
		this.intVal = parseTree.intVal;
	}

	public String CodeGen(){
		return "R <= loadi " + this.intVal + "\n";
	}
}

class ThisExpr extends Expr {
	ThisExpr(Tree parseTree){
	}
}

class TExpr extends Expr {
	String word;

	TExpr(Tree parseTree){
		this.word = parseTree.getChild(0).data;
	}
	public String CodeGen(){
		return "R <= load " + this.word + "\n";
	}
}
