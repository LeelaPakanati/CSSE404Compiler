package arch;
import arch.Operation;
import arch.Register;

public class ArithOp extends Instruction {
	boolean unary = false;
	Condition condition;

	public static int iftlc = 0;

	public ArithOp(Operation operation, Register RS1){
		this.op = operation;
		this.RS1 = RS1;
		this.unary = true;
	}

	public ArithOp(Operation operation, Register RS1, Register RS2){
		this.op = operation;
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public ArithOp(Operation operation, Register RS1, int val){
		this.op = operation;
		this.RS1 = RS1;
		this.imm = val;
	}

	public String toX86(){
		String retVal = super.toX86() + " " + this.RS1.label;
		if(!this.unary){
			if (this.RS2 != null){
				retVal +=  ", " + this.RS2.label;
			} else {
				retVal +=  ", " + this.imm;
			}
		}

		return retVal;
	}
}
