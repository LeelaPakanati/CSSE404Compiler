package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;

public class PushOp extends Instruction {
	Label label;

	public PushOp(Register RS1){
		this.op = Operation.PUSH;
		this.RS1 = RS1;
	}

	public PushOp(Label label){
		this.op = Operation.PUSH;
		this.label = label;
	}

	public String toX86(){
		if(this.RS1 != null){
			return super.toX86() + " " + this.RS1.label;
		} else {
			return super.toX86() + " " + this.label.toX86(false);
		}
	}
}
