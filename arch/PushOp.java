package arch;
import arch.Operation;
import arch.Register;
import arch.Condition;
import symbol.*;

public class PushOp extends Instruction {
	Label label;

	public PushOp(Register RS2){
		this.op = Operation.PUSH;
		this.fromType = ArgType.REG;
		this.RS2 = RS2;
	}

	public PushOp(VarSymbol varSymbol){
		this.op = Operation.PUSH;
		this.varSymbol = varSymbol;
		this.fromType = ArgType.VAR;
	}

	public PushOp(VarSymbol varSymbol, Register regArrRef){
		this.op = Operation.PUSH;
		this.fromType = ArgType.VAR;
		this.varSymbol = varSymbol;
		this.regArrRef = regArrRef;
		this.arrRefByReg = true;
	}

	public PushOp(VarSymbol varSymbol, int intArrRef){
		this.op = Operation.PUSH;
		this.fromType = ArgType.VAR;
		this.varSymbol = varSymbol;
		this.intArrRef = intArrRef;
		this.arrRefByReg = false;
	}

	public PushOp(int imm){
		this.op = Operation.PUSH;
		this.fromType = ArgType.IMM;
		this.imm = imm;
	}

	public PushOp(Label label){
		this.op = Operation.PUSH;
		this.fromType = null;
		this.label = label;
	}

	public String toX86(){
		if(this.fromType == null){
			return super.toX86() + " " + this.label.toX86(false);
		} else {
			String toRet = " ";

			if (this.fromType == ArgType.VAR){
				if(this.varSymbol.varType == VarType.classVar){
					toRet += this.loadClass();
				}
			} else if (this.fromType == ArgType.ARR){
				toRet += this.loadArrRef();
			}

			toRet += super.toX86() + " " + this.getOperand(false);

			return toRet;
		}
	}
}
