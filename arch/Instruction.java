package arch;
import arch.Register;

public abstract class Instruction {
	public Operation op;
	public Register RS1;
	public Register RS2;
	public int imm;
	public boolean bool;

	public String toX86(){
		return "\t" + this.op.name().toLowerCase();
	}
}

