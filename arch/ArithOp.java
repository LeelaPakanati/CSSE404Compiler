package arch;
import arch.Operation;
import arch.Register;

public class ArithOp extends Instruction {
	
	public ArithOp(String operation, Register RS1, Register RS2){
		this.op = this.getOp(operation);
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public ArithOp(String operation, Register RS1, int val){
		this.op = this.getOp(operation);
		this.RS1 = RS1;
		this.imm = val;
	}

	private Operation getOp(String operation){
		switch(operation){
			case "+":
				return Operation.ADD;
			case "-":
				return Operation.SUB;
			case "*":
				return Operation.MUL;
			case "/":
				return Operation.DIV;
			case "<":
				return Operation.CMPQ;
			case "<=":
				return Operation.CMPQ;
			case ">":
				return Operation.CMPQ;
			case "=>":
				return Operation.CMPQ;
			case "&&":
				return Operation.CMPQ;
			case "||":
				return Operation.CMPQ;
			case "==":
				return Operation.CMPQ;
			default:
				//fail
				System.out.println("Bi Op: " + operation + "  not recognized");
				break;
		}
		return null;
	}

	public String toX86(){
		String retVal = super.toX86() + " " + this.RS1.name();
		if (this.RS2 != null){
			retVal +=  " " + this.RS2.name();
		} else {
			retVal +=  " " + this.imm;
		}
		return retVal;
	}
}
