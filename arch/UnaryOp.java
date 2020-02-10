package arch;
import arch.Operation;
import arch.Register;

public class UnaryOp extends Instruction {

	public UnaryOp(Operation op, Register RS1){
		this.RS1 = RS1;
		this.op = op;
	}

	public String toX86(){
		String toRet = super.toX86() + " " + this.RS1.label;
		if(this.op == Operation.CMP){
			toRet += ", 0";
		}
		return toRet;
	}
}

