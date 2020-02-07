package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;
import arch.Label;

public class StartOp extends Instruction {
	public StartOp(){
		this.op = Operation.GLOBAL;
	}

	public String toX86(){
		return super.toX86() + " main\n" + "extern printf\nextern malloc\nextern free\nsection .text\n";
	}
}


