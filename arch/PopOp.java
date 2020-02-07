package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;

public class PopOp extends Instruction {

	public PopOp(Register RS1){
		this.op = Operation.POP;
		this.RS1 = RS1;
	}

	public String toX86(){
		return super.toX86() + " " + this.RS1.label;
	}
}
