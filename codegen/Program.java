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

class Formal extends Node {
	Type type;
	String name;

	Formal(Tree parseTree){
		this.type = new Type(parseTree.getChild(0));
		this.name = parseTree.getChild(1).IDVal;
	}

	//public List<Instruction> CodeGen(){
	//	List<Instruction> asm = new ArrayList<Instruction>();
	//	SymbolTable.addSymbol(new VarSymbol(this.name, this.type.type));
	//	return asm;
	//}
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		for(Stmt stmt : this.stmtList){
			asm.addAll(stmt.CodeGen());
		}
		return asm;
	}
}

class VarDeclAssignStmt extends Stmt {
	String varType;
	IDExpr varID;
	Expr value;

	VarDeclAssignStmt(Tree parseTree){
		if(parseTree.getChild(0).data.equals("ID")){
			this.varType = parseTree.getChild(0).IDVal;
		} else{
			this.varType = parseTree.getChild(0).data;
		}

		Tree Assignment;
		String varName;
		if(parseTree.getChild(0).data.equals("ID")){
			varName = parseTree.getChild(2).getChild(0).IDVal;
			Assignment = parseTree.getChild(2).getChild(2);
		} else if(parseTree.getChild(0).data.equals("int")){
			varName = parseTree.getChild(2).IDVal;
			Assignment = parseTree.getChild(4);
		}else{
			varName = parseTree.getChild(1).IDVal;
			Assignment = parseTree.getChild(3);
		}
		varID = new IDExpr(varName);

		if(Assignment.getChild(0).data.equals("=")){
			this.value = Expr.getInstance(Assignment.getChild(1));
		}
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();

		VarSymbol varSymbol = new VarSymbol(this.varID.id, this.varType);
		varSymbol.index = SymbolTable.localIdx++;

		SymbolTable.addSymbol(varSymbol);

		asm.add(new ArithOp("-", Register.SP, 4)); //make space on stack 

		if(this.value != null){
			asm.addAll(this.value.CodeGen());

			RegOffset address = varID.getAddress();

			if(address == null){ //classVar type
				VarSymbol classVar = (VarSymbol) SymbolTable.getSymbol(this.varID.id);
				asm.add(new MovOp(Register.CX, Register.BP, 8));
				asm.add(new MovOp(Register.CX, classVar.index, Register.AX));
			} else {
				asm.add(new MovOp(address.reg, address.offset, Register.AX));
			}

		}
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		
		IfSymbol ifSymbol = new IfSymbol("if_" + SymbolTable.getIfWhileID());
		SymbolTable.addSymbol(ifSymbol);
		SymbolTable.scopeAdd(ifSymbol);

		Label trueLabel = new Label(ifSymbol.name + "_true");
		Label falseLabel = new Label(ifSymbol.name + "_false");
		Label endLabel = new Label(ifSymbol.name + "_end");

		asm.addAll(this.condition.CodeGen());

		JumpOp jumpTrue = new JumpOp(trueLabel, false);
		if(this.condition instanceof OpExpr){
			switch(((OpExpr)this.condition).operation){
				case "<":
					jumpTrue = new JumpOp(Condition.LESS, trueLabel);
					break;
				case "<=":
					jumpTrue = new JumpOp(Condition.LESSEQUAL, trueLabel);
					break;
				case ">":
					jumpTrue = new JumpOp(Condition.GREATER, trueLabel);
					break;
				case ">=":
					jumpTrue = new JumpOp(Condition.GREATEREQUAL, trueLabel);
					break;
				case "==":
					jumpTrue = new JumpOp(Condition.EQUAL, trueLabel);
					break;
				case "!=":
					jumpTrue = new JumpOp(Condition.NOTEQUAL, trueLabel);
					break;
				case "&&":
					jumpTrue = new JumpOp(Condition.AND, trueLabel);
					break;
				case "||":
					jumpTrue = new JumpOp(Condition.OR, trueLabel);
					break;
				default:
					//fail
					System.out.println("Invalid if condition");
					break;
			}
		} else{
			asm.add(new ArithOp("==", Register.AX, 1));
		}

		asm.add(jumpTrue);							//jump if true
		asm.add(falseLabel);
		asm.addAll(this.falseStmt.CodeGen());		//else do 'else' code
		asm.add(new JumpOp(endLabel, false));		//jump to end after 'else' code
		
		asm.add(trueLabel);
		asm.addAll(this.trueStmt.CodeGen());		//true code

		asm.add(endLabel);

		SymbolTable.scopePop();
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		
		WhileSymbol whileSymbol = new WhileSymbol("while_" + SymbolTable.getIfWhileID());
		SymbolTable.addSymbol(whileSymbol);
		SymbolTable.scopeAdd(whileSymbol);

		Label startLabel = new Label(whileSymbol.name + "_start");
		Label trueLabel = new Label(whileSymbol.name + "_true");
		Label endLabel = new Label(whileSymbol.name + "_end");

		asm.add(startLabel);
		asm.addAll(this.condition.CodeGen());

		JumpOp jumpTrue = new JumpOp(trueLabel, false);
		if(this.condition instanceof OpExpr){
			switch(((OpExpr)this.condition).operation){
				case "<":
					jumpTrue = new JumpOp(Condition.LESS, trueLabel);
					break;
				case "<=":
					jumpTrue = new JumpOp(Condition.LESSEQUAL, trueLabel);
					break;
				case ">":
					jumpTrue = new JumpOp(Condition.GREATER, trueLabel);
					break;
				case ">=":
					jumpTrue = new JumpOp(Condition.GREATEREQUAL, trueLabel);
					break;
				case "==":
					jumpTrue = new JumpOp(Condition.EQUAL, trueLabel);
					break;
				case "!=":
					jumpTrue = new JumpOp(Condition.NOTEQUAL, trueLabel);
					break;
				case "&&":
					jumpTrue = new JumpOp(Condition.AND, trueLabel);
					break;
				case "||":
					jumpTrue = new JumpOp(Condition.OR, trueLabel);
					break;
				default:
					//fail
					System.out.println("Invalid if condition");
					break;
			}
		} else{
			asm.add(new ArithOp("==", Register.AX, 1));
		}

		asm.add(jumpTrue);							//jump if true
		asm.add(new JumpOp(endLabel, false));		//jump to end if not true
		
		asm.add(trueLabel);
		asm.addAll(this.trueStmt.CodeGen());		//true code
		asm.add(startLabel);						//loop

		asm.add(endLabel);

		SymbolTable.scopePop();
		return asm;
	}
}

class PrintStmt extends Stmt {
	Expr printStmt;

	PrintStmt(Tree parseTree){
		this.printStmt = Expr.getInstance(parseTree.getChild(2));
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.addAll(this.printStmt.CodeGen());
		asm.add(new PushOp(Register.AX));
		asm.add(new JumpOp(new Label("PrintNum"), true));
		asm.add(new PopOp(Register.AX)); return asm;
	}
}


class VarAssignStmt extends Stmt {
	IDExpr varID;
	Expr value;

	VarAssignStmt(Tree parseTree){
		this.varID = new IDExpr(parseTree);
		this.value = Expr.getInstance(parseTree.getChild(2).getChild(1));
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.addAll(this.value.CodeGen());

		RegOffset address = varID.getAddress();

		if(address == null){ //classVar type
			VarSymbol classVar = (VarSymbol) SymbolTable.getSymbol(this.varID.id);
			asm.add(new MovOp(Register.CX, Register.BP, 8));
			asm.add(new MovOp(Register.CX, classVar.index, Register.AX));
		} else {
			asm.add(new MovOp(address.reg, address.offset, Register.AX));
		}

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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		//VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.varName);
		//asm.addAll(this.arrRef.CodeGen());
		//asm.add(new MovOp(Register.DX, Register.AX)); //move AX -> DX to save ref
		//asm.addAll(this.value.CodeGen());
		//asm.add(new MovOp(varSymbol, Register.DX, Register.AX)); //varSymbol[ref] = value
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
							if(newWhat.getChild(3).getChild(0) != null){
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();

		//TODO Fix mul and div handling
		asm.addAll(this.rightExpr.CodeGen());
		asm.add(new PushOp(Register.AX));
		asm.addAll(this.leftExpr.CodeGen());

		if(this.operation.equals("*")){
			asm.add(new PopOp(Register.BX));
			asm.add(new ArithOp(this.operation, Register.BX)); // ax <- leftexpr; op rightexpr

		} else if(this.operation.equals("/")){
			asm.add(new PopOp(Register.BX));

			asm.add(new PushOp(Register.DX));	// Div uses 
			asm.add(new MovOp(Register.DX, 0));

			asm.add(new ArithOp(this.operation, Register.BX)); // ax <- leftexpr; op rightexpr

			asm.add(new PopOp(Register.DX));

		} else {
			asm.add(new PopOp(Register.DX));
			asm.add(new ArithOp(this.operation, Register.AX, Register.DX)); // op leftExpr rightExpr
		}

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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.addAll(this.expr.CodeGen());
		asm.add(new UnaryOp(this.operation, Register.AX));
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.arrName);
		//asm.addAll(this.arrRef.CodeGen());
		//asm.add(new MovOp(Register.AX, varSymbol, Register.AX));
		return asm;
	}
}

class LengthExpr extends Expr {
	String arrName;

	LengthExpr(Tree parseTree){
		this.arrName = parseTree.getChild(0).IDVal;
	}
}

class ClassMethodCallExpr extends Expr {
	Expr classID;
	String method;
	List<Expr> argList = new ArrayList<Expr>();

	ClassMethodCallExpr(Tree parseTree){
		Tree firstChild = parseTree.getChild(0);
		Tree dotWhat;

		switch(firstChild.data){
			case "(":
				this.classID = new ParenExpr(parseTree);
				dotWhat = parseTree.getChild(3).getChild(1);
				break;
			case "ID":
				this.classID = new IDExpr(parseTree);
				dotWhat = parseTree.getChild(1).getChild(1);
				break;
			case "this":
				this.classID = new ThisExpr(parseTree);
				dotWhat = parseTree.getChild(1).getChild(1);
				break;
			case "new":
				this.classID = new ClassConstructorExpr(parseTree);
				dotWhat = parseTree.getChild(1).getChild(3).getChild(1);
				break;
			default:
				//fail
				System.out.println("ClassMethodCallExpr not right");
				dotWhat = new Tree();
				break;
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

	ClassSymbol getMethodClass(){
		ClassSymbol classSymbol = new ClassSymbol("NULL");
		while(true){
			if (this.classID instanceof IDExpr){
				IDExpr idExprClassID = (IDExpr) this.classID;
				if(idExprClassID.id.equals("this")){
					// this may not work if there is some weird scope train 
					classSymbol = (ClassSymbol) SymbolTable.getScope().get(0);
				} else{
					VarSymbol obj = (VarSymbol) SymbolTable.getSymbol(idExprClassID.id);
					classSymbol = SymbolTable.getClassSymbol(obj.type);
				}

			} else if(this.classID instanceof ThisExpr){
				classSymbol = (ClassSymbol) SymbolTable.getScope().get(0);

			} else if(this.classID instanceof ClassConstructorExpr){
				ClassConstructorExpr cons = (ClassConstructorExpr) this.classID;
				classSymbol = SymbolTable.getClassSymbol(cons.className);

			} else if(this.classID instanceof ParenExpr){
				this.classID = ((ParenExpr) this.classID).expr;
				continue;

			} else if(this.classID instanceof ClassMethodCallExpr){
				ClassMethodCallExpr methodCall = (ClassMethodCallExpr) this.classID;
				ClassSymbol methodClass = methodCall.getMethodClass();
				List<Symbol> scope = new ArrayList<Symbol>();
				scope.add(methodClass);

				MethodSymbol internalMethod = (MethodSymbol) SymbolTable.getSymbol(scope, methodCall.method);
				classSymbol = SymbolTable.getClassSymbol(internalMethod.retType);

			} else{
				//fail
				System.out.println("Class method call gone wrong; has type: " + this.classID.getClass());
			}
			break;
		}

		return classSymbol;
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();


		List<Symbol> oldScope = new ArrayList<Symbol>(SymbolTable.getScope());
		List<Symbol> newScope = new ArrayList<Symbol>();

		ClassSymbol classSymbol = this.getMethodClass();
		newScope.add(classSymbol);

		MethodSymbol methodSymbol = (MethodSymbol) SymbolTable.getSymbol(newScope, this.method);
		newScope.add(methodSymbol);

		for(Expr arg : this.argList){
			asm.addAll(arg.CodeGen());
			asm.add(new PushOp(Register.AX));
		}

		asm.addAll(this.classID.CodeGen());
		asm.add(new PushOp(Register.AX)); //push class pointer onto stack
		
		SymbolTable.setScope(newScope); //change scope

		asm.add(new JumpOp(methodSymbol.label, true));		

		SymbolTable.setScope(oldScope); //restore scope

		asm.add(new ArithOp("+", Register.SP, (this.argList.size()+1)*4)); //pop args and class off stack
		return asm;
	}
}

class ClassConstructorExpr extends Expr {
	String className;

	ClassConstructorExpr(Tree parseTree){
		this.className = parseTree.getChild(1).getChild(0).IDVal;
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		//TODO: Memory allocate for object
		//asm.add(new Label("CONSTRUCT_" + this.className));
		ClassSymbol classSymbol = SymbolTable.getClassSymbol(this.className);

		int ClassVarCnt = 0;
		for(Symbol symbol : classSymbol.symbols.values()){
			if(symbol instanceof VarSymbol){
				ClassVarCnt++;
			}
		}
		//TODO: Test malloc
		asm.add(new MovOp(Register.AX, 4*ClassVarCnt));
		asm.add(new PushOp(Register.AX));
		asm.add(new JumpOp(new Label("malloc"), true));
		asm.add(new ArithOp("+", Register.SP, 4));
		return asm;
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

	public List<Instruction> CodeGen(){
		return this.expr.CodeGen();
	}
}

class IDExpr extends Expr {
	String id;

	IDExpr(String id){
		this.id = id;
	}

	IDExpr(Tree parseTree){
		this.id = parseTree.getChild(0).IDVal;
	}

	public RegOffset getAddress(){
		if(this.id.equals("this")){
			//args | ClassAddr | ret ins | BP | 
			return new RegOffset(Register.BP, 8);
		} else{
			VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.id);
			switch(varSymbol.varType){
				case localVar:
					return new RegOffset(Register.BP, -4*varSymbol.index);
				case classVar:
					return null;
				case inputArg:
					return new RegOffset(Register.BP, 4*varSymbol.index);
			}
		}
		return new RegOffset(Register.ERROR, 0);
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		RegOffset address = this.getAddress();
		if(address == null){ //classVar type
			VarSymbol classVar = (VarSymbol) SymbolTable.getSymbol(this.id);
			asm.add(new MovOp(Register.AX, Register.BP, 8));
			asm.add(new MovOp(Register.AX, Register.AX, classVar.index));
		} else {
			asm.add(new MovOp(Register.AX, address.reg, address.offset));
		}
		return asm;
	}
}

class IntegerExpr extends Expr {
	int intVal;

	IntegerExpr(Tree parseTree){
		this.intVal = parseTree.getChild(0).intVal;
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.add(new MovOp(Register.AX, this.intVal));
		return asm;
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
}

class RegOffset {
	Register reg;
	int offset;

	public RegOffset(Register reg, int offset){
		this.reg = reg;
		this.offset = offset;
	}
}
