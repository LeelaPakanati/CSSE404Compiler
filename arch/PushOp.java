package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;
import symbol.*;

public class PushOp extends Instruction {
	Label label;
	Register sourceRegisterOffset;

	public PushOp(Register RS2){
		this.op = Operation.PUSH;
		this.RS2 = RS2;
		this.fromType = ArgType.REG;
	}

	public PushOp(Register RS2, int offset){
		this.op = Operation.PUSH;
		this.RS2 = RS2;
		this.imm = offset;
		this.fromType = ArgType.VAR;
	}

	public PushOp(VarSymbol varSymbol){
		this.op = Operation.PUSH;
		this.varSymbol = varSymbol;
		this.fromType = ArgType.VAR;
	}

	//public PushOp(Register RS2, Register registerOffset){
	//	this.op = Operation.PUSH;
	//	this.RS2 = RS2;
	//	this.sourceRegisterOffset = registerOffset;
	//	this.fromType = ArgType.ARR;
	//}

	public PushOp(int imm){
		this.op = Operation.PUSH;
		this.imm = imm;
		this.fromType = ArgType.IMM;
	}

	public PushOp(Label label){
		this.op = Operation.PUSH;
		this.label = label;
		this.fromType = null;
	}

	public String toX86(){
		if(this.fromType == null){
			return super.toX86() + " " + this.label.toX86(false);
		} else {
			String toRet = " ";
			toRet = super.toX86() + " ";

			toRet += this.getOperand(false);

			//switch(this.fromType){
			//	case REG:
			//		toRet += this.RS2.label;
			//		break;
			//	case IMM:
			//		toRet += this.imm;
			//		break;
			//	case VAR:
			//		toRet += "dword " + "[" + this.RS2.label + " + " + this.imm + "]";
			//		break;
			//	case ARR:
			//		toRet += "dword " + "[" + this.RS2.label + " + " + this.sourceRegisterOffset.label + "]";
			//		break;
			//	default:
			//		System.out.println("Push From var type failed");
			//		//fail
			//		break;
			//}

			return toRet;
		}
	}
}
