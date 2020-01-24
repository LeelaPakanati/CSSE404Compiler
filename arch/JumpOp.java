package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;

public class JumpOp extends Instruction {
	Label jumpLabel;

	public JumpOp(){
		this.op = Operation.RETQ;
	}

	public JumpOp(Label jumpLabel, boolean call){
		if(call){
			this.op = Operation.CALLQ;
		} else {
			this.op = Operation.JMP;
		}
		this.jumpLabel = jumpLabel;
	}

	public JumpOp(Condition cond, Label jumpLabel){
		switch(cond){
			case EQUAL:
				this.op = Operation.JE;
				break;
			case NOTEQUAL:
				this.op = Operation.JNE;
				break;
			case LESS:
				this.op = Operation.JL;
				break;
			case LESSEQUAL:
				this.op = Operation.JLE;
				break;
			case GREATER:
				this.op = Operation.JG;
				break;
			case GREATEREQUAL:
				this.op = Operation.JGE;
				break;
			case AND: //TODO Fix
				this.op = Operation.JE;
				break;
			case OR:
				this.op = Operation.JE;
				break;
		}
		this.jumpLabel = jumpLabel;
	}

	public String toX86(){
		if(this.op == Operation.RETQ){
			return super.toX86();
		}
		return super.toX86() + " " + this.jumpLabel.toX86();
	}
}


