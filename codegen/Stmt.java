package codegen;
import java.util.*;
import parser.Tree;
import symbol.*;
import arch.*;

public class Stmt extends Node {
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
					System.err.println("Invalid Statement with starting word of type ID.");
					return null;
				}
			default:
				//fail
				System.err.println("Invalid statement");
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

		if(this.varType.equals("int") && parseTree.getChild(1).getChild(0) != null){
			this.varType = "int[]";
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
		this.varID = new IDExpr(varName);
		this.varID.type = this.varType;

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

			if(!this.value.type.equals(this.varType)){
				System.err.println("Type mismatch at declaration of " + this.varID.id + " : " + this.varType + " cannot be set to " + this.value.type);
				//fail
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

		this.varID.loadVarSymbol();
		if(!this.varID.type.equals(this.value.type)){
			System.err.println("Type mismatch for setting expr " + this.varID.id +  " : " + varID.type + " cannot be set to " + value.type);
			//fail
		}

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

		if(!this.value.type.equals("int")){
			System.err.println("Type mismatch: " + value.type + " is not of type int");
			//fail
		}

		return asm;
	}
}


