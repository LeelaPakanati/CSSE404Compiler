package arch;
import arch.Register;

public abstract class Instruction {
	Operation op;
	Register RS1;
	Register RS2;
	int imm;
	boolean bool;

	public String toX86(){
		return "\t" + this.op.name().toLowerCase();
	}
}

