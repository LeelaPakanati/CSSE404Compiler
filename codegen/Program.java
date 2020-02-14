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
					System.err.println("Stmt->ID Failed");
					return null;
				}
			default:
				//fail
				System.err.println("Stmt Switch Failed");
				System.err.println(parseTree.toString(0));
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
		varSymbol.isArr = true;

		SymbolTable.addSymbol(varSymbol);

		asm.add(new ArithOp(Operation.SUB, Register.SP, 4)); //make space on stack 

		if(this.value != null){
			asm.addAll(this.value.CodeGen());
			asm.add(new MovOp(varSymbol, Register.AX));
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

		asm.addAll(this.condition.CodeGen());				// expression return 1 if true;

		asm.add(new ArithOp(Operation.CMP, Register.AX, 0));
		JumpOp jumpTrue = new JumpOp(Condition.GREATER, trueLabel);

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
		asm.addAll(this.condition.CodeGen());				// expression return 1 if true;

		asm.add(new ArithOp(Operation.CMP, Register.AX, 0));		
		JumpOp jumpTrue = new JumpOp(Condition.GREATER, trueLabel);

		asm.add(jumpTrue);							//jump if true
		asm.add(new JumpOp(endLabel, false));		//jump to end if not true
		
		asm.add(trueLabel);
		asm.addAll(this.trueStmt.CodeGen());		//true code
		asm.add(new JumpOp(startLabel, false));						//loop

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
		asm.add(new ArithOp(Operation.ADD, Register.SP, 4));
		return asm;
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
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.varID.id);
		asm.add(new MovOp(varSymbol, Register.AX));
		return asm;
	}
}

class ArrVarAssignStmt extends Stmt {
	IDExpr varName;
	Expr arrRef;
	Expr value;

	ArrVarAssignStmt(Tree parseTree){
		//this.varName = parseTree.getChild(0).IDVal;
		this.varName = new IDExpr(parseTree);
		this.arrRef = Expr.getInstance(parseTree.getChild(1).getChild(1).getChild(0));
		this.value = Expr.getInstance(parseTree.getChild(2).getChild(1));
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.varName.id);

		asm.addAll(this.arrRef.CodeGen());			  // get arr reference
		asm.add(new PushOp(Register.AX));

		asm.addAll(this.value.CodeGen());			  // get value into AX

		asm.add(new PopOp(Register.CX));

		asm.add(new MovOp(varSymbol, Register.CX, Register.AX)); //move reference to cx register Var[CX] <- AX
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
								System.err.println("Expr->ID->. Failed");
								return null;
							}
						} else if(value.getChild(1).getChild(0).data.equals("[")){
							return new ArrExpr(value);
						} else{
							//fail
							System.err.println("Expr->ID Failed");
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
							System.err.println("Expr->new->ID Failed");
							return null;
						}
					} else if(newWhat.getChild(0).data.equals("int")){
						if(newWhat.getChild(1).data.equals("[")){
							return new IntArrConstructorExpr(value);
						} else{
							//fail
							System.err.println("Expr->new->int Failed");
							return null;
						}
					} else{
						//fail
						System.err.println("Expr->new Failed");
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
							System.err.println("Expr->( Failed");
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
								System.err.println("Expr->this->. Failed");
								return null;
							}
						} else{
							//fail
							System.err.println("Expr->this Failed");
							return null;
						}
					} else{
						return new ThisExpr(value);
					}

				case "Integer":
					return new IntegerExpr(value);

				case "true":
					return new BoolExpr(value);

				case "false":
					return new BoolExpr(value);
				
				default:
					System.err.println("Invalid Expr: " + valueFirstChild.data);
					//fail
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

		asm.addAll(this.rightExpr.CodeGen());
		asm.add(new PushOp(Register.AX));
		asm.addAll(this.leftExpr.CodeGen());

		if(this.operation.equals("*")){
			asm.add(new PopOp(Register.DX));
			asm.add(new ArithOp(Operation.IMUL, Register.DX)); // ax <- leftexpr; op rightexpr

		} else if(this.operation.equals("/")){
			asm.add(new PopOp(Register.BX));

			asm.add(new PushOp(Register.DX));	// Div uses 
			asm.add(new MovOp(Register.DX, 0));

			asm.add(new ArithOp(Operation.DIV, Register.BX)); // ax <- leftexpr; op rightexpr

			asm.add(new PopOp(Register.DX));

		} else {
			asm.add(new PopOp(Register.DX));
			
			Operation op = Operation.ADD;
			Condition condition = Condition.EQUAL;
			switch(this.operation){
				case "+":
					op = Operation.ADD;
					break;
				case "-":
					op =  Operation.SUB;
					break;
				case "*":
					op = Operation.IMUL;
					break;
				case "/":
					op = Operation.DIV;
					break;
				case "<":
					condition = Condition.LESS;
					op = Operation.CMP;
					break;
				case "<=":
					condition = Condition.LESSEQUAL;
					op = Operation.CMP;
					break;
				case ">":
					condition = Condition.GREATER;
					op = Operation.CMP;
					break;
				case "=>":
					condition = Condition.GREATEREQUAL;
					op = Operation.CMP;
					break;
				case "&&":
					condition = Condition.AND;
					op = Operation.CMP;
					break;
				case "||":
					condition = Condition.OR;
				case "==":
					condition = Condition.EQUAL;
					op =  Operation.CMP;
					break;
				default:
					//fail
					System.err.println("Bi Op: " + operation + "  not recognized");
					break;
			}

			asm.add(new ArithOp(op, Register.AX, Register.DX)); // op leftExpr rightExpr

			if(op == Operation.CMP){
				Label ift = new Label("ift_"+ ArithOp.iftlc);
				Label ifend = new Label("ifend_"+ ArithOp.iftlc++);
				JumpOp jmpt = new JumpOp(condition, ift);
				JumpOp jmpend = new JumpOp(ifend, false);
				MovOp f = new MovOp(Register.AX, 0);
				MovOp t = new MovOp(Register.AX, 1);

				List<Instruction> cmpops = new ArrayList<Instruction>();
				asm.add(jmpt);	//jump to true
				asm.add(f);		//set ax to 0
				asm.add(jmpend);//jump to end
				asm.add(ift);	//true label
				asm.add(t);		//set ax to 1
				asm.add(ifend); //end label
			}
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
		if(this.operation.equals("!")){
			asm.add(new UnaryOp(Operation.CMP, Register.AX));

			Label ift = new Label("ift_"+ ArithOp.iftlc);
			Label ifend = new Label("ifend_"+ ArithOp.iftlc++);
			JumpOp jmpt = new JumpOp(Condition.EQUAL, ift);
			JumpOp jmpend = new JumpOp(ifend, false);
			MovOp f = new MovOp(Register.AX, 0);
			MovOp t = new MovOp(Register.AX, 1);

			List<Instruction> cmpops = new ArrayList<Instruction>();
			asm.add(jmpt);	//jump to true
			asm.add(f);		//set ax to 0
			asm.add(jmpend);//jump to end
			asm.add(ift);	//true label
			asm.add(t);		//set ax to 1
			asm.add(ifend); //end label
		} else if (this.operation.equals("-")){
			asm.add(new UnaryOp(Operation.NEG, Register.AX));
		} else {
			System.err.println("Invalid unary op type");
			//fail
		}
		return asm;
	}
}

class ArrExpr extends Expr {
	IDExpr arrName;
	Expr arrRef;

	ArrExpr(Tree parseTree){
		//this.arrName = parseTree.getChild(0).IDVal;
		this.arrName = new IDExpr(parseTree);
		this.arrRef = Expr.getInstance(parseTree.getChild(1).getChild(1));
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.arrName.id);
		//TODO Check if ref in range of length

		asm.addAll(this.arrRef.CodeGen());			  // get arr reference
		asm.add(new MovOp(Register.CX, Register.AX)); // move reference to CX register

		asm.add(new MovOp(Register.AX, varSymbol, Register.CX)); // AX <- Var[CX]
		return asm;
	}
}

class LengthExpr extends Expr {
	IDExpr arrName;

	LengthExpr(Tree parseTree){
		//this.IDExpr = parseTree.getChild(0).IDVal;
		this.arrName = new IDExpr(parseTree);
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.arrName.id);

		asm.add(new MovOp(Register.AX, varSymbol, -1));	//value at base of symbol is length
		return asm;
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
				System.err.println("ClassMethodCallExpr not right");
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
				System.err.println("Class method call gone wrong; has type: " + this.classID.getClass());
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

		asm.add(new ArithOp(Operation.ADD, Register.SP, (this.argList.size()+1)*4)); //pop args and class off stack
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
		ClassSymbol classSymbol = SymbolTable.getClassSymbol(this.className);

		int ClassVarCnt = 0;
		for(Symbol symbol : classSymbol.symbols.values()){
			if(symbol instanceof VarSymbol){
				ClassVarCnt++;
			}
		}
		//TODO: Test wheter or not malloc was successful
		asm.add(new MovOp(Register.AX, 4*(ClassVarCnt+2)));
		asm.add(new PushOp(Register.AX));
		asm.add(new JumpOp(new Label("malloc"), true));
		asm.add(new ArithOp(Operation.ADD, Register.SP, 4));
		return asm;
	}
}

class IntArrConstructorExpr extends Expr {
	Expr arrLength;

	IntArrConstructorExpr(Tree parseTree){
		this.arrLength = Expr.getInstance(parseTree.getChild(1).getChild(2));
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();

		asm.addAll(this.arrLength.CodeGen());

		asm.add(new PushOp(Register.AX)); //push unaltered length onto stack
		asm.add(new ArithOp(Operation.ADD, Register.AX, 1));

		asm.add(new MovOp(Register.DX, 4));
		asm.add(new ArithOp(Operation.IMUL, Register.DX));

		asm.add(new PushOp(Register.AX));
		asm.add(new JumpOp(new Label("malloc"), true));
		asm.add(new ArithOp(Operation.ADD, Register.SP, 4));

		asm.add(new PopOp(Register.CX));	//get lenth of arr into cx
		
		asm.add(new MovOp(Register.AX, 0, Register.CX)); //load array spot with array length [AX] <- CX

		return asm;
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol;
		if(this.id.equals("this")){
			varSymbol = new VarSymbol("this", null);
		} else{
			varSymbol = (VarSymbol) SymbolTable.getSymbol(this.id);
		}
		asm.add(new MovOp(Register.AX, varSymbol));
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

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.add(new MovOp(Register.AX, new VarSymbol("this", null)));
		return asm;
	}
}

class BoolExpr extends Expr {
	int bool;

	BoolExpr(Tree parseTree){
		if(parseTree.getChild(0).data.equals("true")){
			this.bool = 1;
		} else if (parseTree.getChild(0).data.equals("false")){
			this.bool = 0;
		} else{
			System.err.println("Boolean Expression failed");
			//fail
		}
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		asm.add(new MovOp(Register.AX, bool));
		return asm;
	}
}

class TExpr extends Expr {
	String word;

	TExpr(Tree parseTree){
		this.word = parseTree.getChild(0).data;
	}
}
