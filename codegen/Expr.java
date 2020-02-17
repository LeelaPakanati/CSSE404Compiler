package codegen;
import java.util.*;
import parser.Tree;
import symbol.*;
import arch.*;

public class Expr extends Node {
	String type;

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
								System.err.println("Invalid Expression ID with following '.'");
								return null;
							}
						} else if(value.getChild(1).getChild(0).data.equals("[")){
							return new ArrExpr(value);
						} else{
							//fail
							System.err.println("Invalid Expression ID");
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
							System.err.println("Invalid Constructor: missing parenthesis");
							return null;
						}
					} else if(newWhat.getChild(0).data.equals("int")){
						if(newWhat.getChild(1).data.equals("[")){
							return new IntArrConstructorExpr(value);
						} else{
							//fail
							System.err.println("'Int' type does not require instantiation");
							return null;
						}
					} else{
						//fail
						System.err.println("Constructor failed");
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
							System.err.println("Invalid Expression");
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
								System.err.println("Expression of 'this' not valid");
								return null;
							}
						} else{
							//fail
							System.err.println("Expression of 'this' not valid");
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

			this.type = "int";

		} else if(this.operation.equals("/")){
			asm.add(new PopOp(Register.BX));

			asm.add(new PushOp(Register.DX));	// Div uses 
			asm.add(new MovOp(Register.DX, 0));

			asm.add(new ArithOp(Operation.DIV, Register.BX)); // ax <- leftexpr; op rightexpr

			asm.add(new PopOp(Register.DX));

			this.type = "int";

		} else {
			asm.add(new PopOp(Register.DX));
			
			Operation op = Operation.ADD;
			Condition condition = Condition.EQUAL;
			switch(this.operation){
				case "+":
					op = Operation.ADD;
					this.type = "int";
					break;
				case "-":
					op =  Operation.SUB;
					this.type = "int";
					break;
				case "<":
					condition = Condition.LESS;
					op = Operation.CMP;
					this.type = "boolean";
					break;
				case "<=":
					condition = Condition.LESSEQUAL;
					op = Operation.CMP;
					this.type = "boolean";
					break;
				case ">":
					condition = Condition.GREATER;
					op = Operation.CMP;
					this.type = "boolean";
					break;
				case "=>":
					condition = Condition.GREATEREQUAL;
					op = Operation.CMP;
					this.type = "boolean";
					break;
				case "&&":
					condition = Condition.AND;
					op = Operation.CMP;
					this.type = "boolean";
					break;
				case "||":
					condition = Condition.OR;
					op =  Operation.CMP;
					this.type = "boolean";
					break;
				case "==":
					condition = Condition.EQUAL;
					op =  Operation.CMP;
					this.type = "boolean";
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

			this.type = "boolean";
		} else if (this.operation.equals("-")){
			asm.add(new UnaryOp(Operation.NEG, Register.AX));

			this.type = "int";
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

		this.type = "int";
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		VarSymbol varSymbol = (VarSymbol) SymbolTable.getSymbol(this.arrName.id);
		//TODO Check if ref in range of length

		asm.addAll(this.arrRef.CodeGen());			  // get arr reference
		asm.add(new MovOp(Register.CX, Register.AX)); // move reference to CX register

		asm.add(new MovOp(Register.AX, varSymbol, Register.CX)); // AX <- Var[CX]

		if(!this.arrRef.type.equals("int")){				//type check reference type
			System.err.println("Array reference for " + arrName + " must be of type int");
		}

		return asm;
	}
}

class LengthExpr extends Expr {
	IDExpr arrName;

	LengthExpr(Tree parseTree){
		//this.IDExpr = parseTree.getChild(0).IDVal;
		this.arrName = new IDExpr(parseTree);

		this.type = "int";
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
				System.err.println("ClassMethodCallExpr not valid");
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
				System.err.println("Class method call gone wrong; has type: " + this.classID.getClass());
				//fail
			}
			break;
		}

		return classSymbol;
	}

	private String getProfile(){
		String toRet = this.method + "(";
		for(Expr arg : this.argList){
			toRet += arg.type + ", ";
		}
		toRet = toRet.substring(0, toRet.length()-2) + ")";
		return toRet;
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();


		List<Symbol> oldScope = new ArrayList<Symbol>(SymbolTable.getScope());
		List<Symbol> newScope = new ArrayList<Symbol>();

		ClassSymbol classSymbol = this.getMethodClass();
		newScope.add(classSymbol);

		// Get Method Symbol
		MethodSymbol methodSymbol = (MethodSymbol) SymbolTable.getSymbol(newScope, this.method);
		newScope.add(methodSymbol);
		this.type = methodSymbol.retType;

		// Add arguments
		for(Expr arg : this.argList){
			asm.addAll(arg.CodeGen());
			asm.add(new PushOp(Register.AX));
		}

		// Check Arguments		//we check after adding because calling codegen is required to set the type
		if(this.argList.size() != methodSymbol.argList.size()){
			System.err.println("Method" + methodSymbol.name + " takes " + methodSymbol.argList.size() + " arguments. Gave: " + this.argList.size());
			System.err.println("Method profile called: " + this.getProfile());
			System.err.println("Method profile required: " + methodSymbol.getProfile());
			//fail
		} else {
			for(int i = 0; i < this.argList.size(); i++){
				String argType = this.argList.get(0).type;
				String decArgType = methodSymbol.argList.get(0).type.type;
				if(!argType.equals(decArgType)){
					System.err.println("Method profile called: " + this.getProfile());
					System.err.println("Method profile required: " + methodSymbol.getProfile());
				}
				//fail
			}
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
		this.type = className;
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
		this.type = "int[]";
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

		asm.add(new PopOp(Register.CX));	//get length of arr into cx
		
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
		List<Instruction> toRet = this.expr.CodeGen();
		this.type = this.expr.type;
		return toRet;
	}
}

class IDExpr extends Expr {
	String id;
	VarSymbol varSymbol;

	IDExpr(String id){
		this.id = id;
	}

	IDExpr(Tree parseTree){
		this.id = parseTree.getChild(0).IDVal;
	}

	void loadVarSymbol(){
		if(this.id.equals("this")){
			this.varSymbol = new VarSymbol("this", null);
			this.type = SymbolTable.getScope().get(0).name;
		} else{
			this.varSymbol = (VarSymbol) SymbolTable.getSymbol(this.id);
			this.type = varSymbol.type;
		}
	}

	public List<Instruction> CodeGen(){
		List<Instruction> asm = new ArrayList<Instruction>();
		this.loadVarSymbol();
		asm.add(new MovOp(Register.AX, this.varSymbol));
		return asm;
	}
}

class IntegerExpr extends Expr {
	int intVal;

	IntegerExpr(Tree parseTree){
		this.intVal = parseTree.getChild(0).intVal;
		this.type = "int";
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
		this.type = SymbolTable.getScope().get(0).name;
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
		this.type = "boolean";
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
