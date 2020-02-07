package arch;
import arch.Operation;
import arch.Register;

public class SysCallOp extends Instruction {
	SysCall call;

	public SysCallOp(SysCall call){
		this.op = Operation.INT;
		this.call = call;
	}

	public String toX86(){
		Label exit = new Label("Syscall_" + this.call.name());
		MovOp loadAX = new MovOp(Register.AX, this.call.value);
		return exit.toX86() + "\n" + loadAX.toX86() + "\n" + super.toX86() + " 0x80";
	}
}

