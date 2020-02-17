package codegen;
import java.util.*;
import parser.Tree;
import symbol.*;
import arch.*;

abstract class Node{
	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		return asm;
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.add(new StartOp()); 

		asm.addAll(mainClass.CodeGen());
		for(ClassDecl classDecl : this.classList){
			asm.addAll(classDecl.CodeGen());
		}

		asm.add(new SysCallOp(SysCall.EXIT));
		asm.add(new PrintOp());

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
		SymbolTable.addClass(classSymbol);
		SymbolTable.scopeAdd(classSymbol);

		MethodSymbol methodSymbol = new MethodSymbol("main", "void");
		SymbolTable.addSymbol(methodSymbol);

		this.args = parseTree.getChild(11).IDVal;

		Tree stmt = parseTree.getChild(14);
		do{
			this.stmtList.add(Stmt.getInstance(stmt.getChild(0)));
			stmt = stmt.getChild(1);
		}while(stmt.getChild(0) != null);

		SymbolTable.scopePop();
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		ClassSymbol classSymbol = SymbolTable.getClassSymbol(this.classname);
		SymbolTable.scopeAdd(classSymbol);

		Label mainClassLabel = new Label("MainClass_" + this.classname);
		classSymbol.label = mainClassLabel;
		asm.add(mainClassLabel);

		MethodSymbol methodSymbol = (MethodSymbol) SymbolTable.getSymbol("main");
		SymbolTable.scopeAdd(methodSymbol);

		Label mainLabel = new Label("main");
		methodSymbol.label = mainLabel;
		asm.add(mainLabel);

		asm.add(new PushOp(Register.BP));			//push frame pointer
		asm.add(new MovOp(Register.BP, Register.SP)); //save stack pointer as new frame pointer

		//VarSymbol argsSymbol = new VarSymbol(this.args, "String");
		//SymbolTable.addSymbol(argsSymbol);
		//TODO Fix input args handling
		//asm.add(new MovOp(Register.AX, Register.ARGS));

		for(Stmt stmt : this.stmtList){
			asm.addAll(stmt.CodeGen());
		}

		SymbolTable.scopePop();

		asm.add(new MovOp(Register.SP, Register.BP)); //restore sp to bp
		asm.add(new PopOp(Register.BP)); //restore frame pointer
		asm.add(new JumpOp()); //return

		SymbolTable.scopePop();
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
		if(parseTree.getChild(2).getChild(1) != null){
			this.superclass = parseTree.getChild(2).getChild(1).IDVal;
			classSymbol.superclass = SymbolTable.getClassSymbol(this.superclass);
		}

		SymbolTable.addClass(classSymbol);
		SymbolTable.scopeAdd(classSymbol);

		Tree varDecl = parseTree.getChild(4);
		int varIndex = 1;
		while(varDecl.getChild(0) != null){
			this.varDeclList.add(new ClassVarDecl(varDecl.getChild(0), varIndex++));
			varDecl = varDecl.getChild(1);
		}

		Tree methodDecl = parseTree.getChild(5);
		do{
			this.methodDeclList.add(new MethodDecl(methodDecl.getChild(0)));
			methodDecl = methodDecl.getChild(1);
		}while(methodDecl.getChild(0) != null);

		SymbolTable.scopePop();
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		ClassSymbol classSymbol = SymbolTable.getClassSymbol(this.classname);
		SymbolTable.scopeAdd(classSymbol);

		Label classLabel = new Label("Class_" + this.classname);
		classSymbol.label = classLabel;
		asm.add(classLabel);

		for(ClassVarDecl varDecl : this.varDeclList){
			asm.addAll(varDecl.CodeGen());
		}

		for(MethodDecl methodDecl : this.methodDeclList){
			asm.addAll(methodDecl.CodeGen());
		}

		SymbolTable.scopePop();
		return asm;
	}
}

class ClassVarDecl extends Node {
	Type varType;
	String varName;

	ClassVarDecl(Tree parseTree){
		this.varType = new Type(parseTree.getChild(0).getChild(0));
		this.varName = parseTree.getChild(0).getChild(1).IDVal;
		VarSymbol varSymbol = new VarSymbol(this.varName, varType.type);
		varSymbol.varType = VarType.classVar;
		if(this.varType.type.equals("int[]")){
			varSymbol.isArr = true;
		}
		SymbolTable.addSymbol(varSymbol);
	}

	ClassVarDecl(Tree parseTree, int varIndex){
		this.varType = new Type(parseTree.getChild(0).getChild(0));
		this.varName = parseTree.getChild(0).getChild(1).IDVal;
		VarSymbol varSymbol = new VarSymbol(this.varName, varType.type);
		varSymbol.varType = VarType.classVar;
		varSymbol.index = varIndex;
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
		ClassSymbol classSymbol = (ClassSymbol) SymbolTable.getScope().get(0);
		Label methodLabel = new Label(classSymbol.name + "_Method_" + this.methodname);
		methodSymbol.label = methodLabel;

		SymbolTable.addSymbol(methodSymbol);
		SymbolTable.scopeAdd(methodSymbol);

		Tree arg = parseTree.getChild(3);
		if(arg.getChild(0) != null){
			this.argList.add(new Formal(arg.getChild(0)));
			arg = arg.getChild(1);

			while(arg.getChild(0) != null){
				this.argList.add(new Formal(arg.getChild(1)));
				arg = arg.getChild(2);
			}
		}

		methodSymbol.argList = this.argList;

		int idx = -1;
		for(Formal argL : this.argList){
			VarSymbol varSymbol = new VarSymbol(argL.name, argL.type.type);
			varSymbol.varType = VarType.inputArg;
			varSymbol.index = argList.size() - (idx++) + 1;
			SymbolTable.addSymbol(varSymbol);
		}

		Tree stmt = parseTree.getChild(6);
		while(stmt.getChild(0) != null){
			this.stmtList.add(Stmt.getInstance(stmt.getChild(0)));
			stmt = stmt.getChild(1);
		}
		this.returnVal = Expr.getInstance(parseTree.getChild(8));

		SymbolTable.scopePop();
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		MethodSymbol methodSymbol = (MethodSymbol) SymbolTable.getSymbol(this.methodname);
		SymbolTable.scopeAdd(methodSymbol);

		int oldLocalIdx = SymbolTable.localIdx; //store and reset localidx
		SymbolTable.localIdx = 1;

		asm.add(methodSymbol.label);

		asm.add(new PushOp(Register.BP));			//push frame pointer
		asm.add(new MovOp(Register.BP, Register.SP)); //save stack pointer as new frame pointer

		for(Stmt stmt : this.stmtList){
			asm.addAll(stmt.CodeGen());
		}

		asm.addAll(this.returnVal.CodeGen()); //stores value to RegisterAX

		asm.add(new MovOp(Register.SP, Register.BP)); //restore sp to bp

		asm.add(new PopOp(Register.BP)); //restore frame pointer
		
		SymbolTable.localIdx = oldLocalIdx; //restore localidx

		asm.add(new JumpOp()); //return
		SymbolTable.scopePop();
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

		if(this.type.equals("int") && parseTree.getChild(1).getChild(0) != null){
			this.type = "int[]";
		}
	}
}


