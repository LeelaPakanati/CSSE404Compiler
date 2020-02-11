package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;

public class PushOp extends Instruction {
	Label label;
	Register sourceRegisterOffset;
	MovArgType sourceType;

	public PushOp(Register RS1){
		this.op = Operation.PUSH;
		this.RS1 = RS1;
		this.sourceType = MovArgType.REG;
	}

	public PushOp(Register RS1, int offset){
		this.op = Operation.PUSH;
		this.RS1 = RS1;
		this.imm = offset;
		this.sourceType = MovArgType.VAR;
	}

	public PushOp(Register RS1, Register registerOffset){
		this.op = Operation.PUSH;
		this.RS1 = RS1;
		this.sourceRegisterOffset = registerOffset;
		this.sourceType = MovArgType.ARR;
	}

	public PushOp(int imm){
		this.op = Operation.PUSH;
		this.imm = imm;
		this.sourceType = MovArgType.IMM;
	}

	public PushOp(Label label){
		this.op = Operation.PUSH;
		this.label = label;
		this.sourceType = null;
	}

	public String toX86(){
		if(this.sourceType == null){
			return super.toX86() + " " + this.label.toX86(false);
		} else {
			String toRet = super.toX86() + " ";

			switch(this.sourceType){
				case REG:
					toRet += this.RS1.label;
					break;
				case IMM:
					toRet += this.imm;
					break;
				case VAR:
					toRet += "dword " + "[" + this.RS1.label + " + " + this.imm + "]";
					break;
				case ARR:
					toRet += "dword " + "[" + this.RS1.label + " + " + this.sourceRegisterOffset.label + "]";
					break;
				default:
					System.out.println("Push From var type failed");
					//fail
					break;
			}

			return toRet;
		}
	}
}
