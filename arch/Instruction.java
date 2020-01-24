package arch;
import arch.Register;

public abstract class Instruction {
	Operation op;
	Register RS1;
	Register RS2;
	int imm;
	boolean bool;

	public String toX86(){
		return "\t" + this.op.name();
	}
}

enum Operation {
	ADD,
	SUB,
	MUL,
	DIV,
	NOT,
	AND,
	NEGQ,
	MOV,
	JMP,
	JE,
	JNE,
	JL,
	JLE,
	JG,
	JGE,
	JNZ,
	CALLQ,
	RETQ,
	CMPQ
}
