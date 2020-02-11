package arch;
import symbol.VarSymbol;
import arch.Operation;
import arch.Register;

public class MovOp extends Instruction {
	public MovArgType fromType;
	public MovArgType toType;

	//RS1 = toReg
	//RS2 = fromReg

	public int toIntOffset;
	public int fromIntOffset;

	public Register toRegisterOffset;
	public Register fromRegisterOffset;

	public MovOp(Register RS1, int imm){
		this.toType = MovArgType.REG;
		this.fromType = MovArgType.IMM;

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.imm = imm;
	}

	public MovOp(Register RS1, Register RS2){
		this.toType = MovArgType.REG;
		this.fromType = MovArgType.REG;

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, Register RS2, int intOffset){
		this.toType = MovArgType.REG;
		this.fromType = MovArgType.VAR;

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
		this.fromIntOffset = intOffset;
	}

	public MovOp(Register RS1, int intOffset, Register RS2){
		this.toType = MovArgType.VAR;
		this.fromType = MovArgType.REG;

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.toIntOffset = intOffset;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, Register regOffset, Register RS2, boolean toArr){
		if(toArr){
			this.toType = MovArgType.ARR;
			this.fromType = MovArgType.REG;
			this.toRegisterOffset = regOffset;
		} else{
			this.toType = MovArgType.REG;
			this.fromType = MovArgType.ARR;
			this.fromRegisterOffset = regOffset;
		}

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, int toIntOffset, Register RS2, int fromIntOffset){
		this.toType = MovArgType.VAR;
		this.fromType = MovArgType.VAR;

		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.toIntOffset = toIntOffset;
		this.RS2 = RS2;
		this.fromIntOffset = fromIntOffset;
	}

	public String toX86(){
		String toRet = super.toX86() + " ";

		switch(this.toType){
			case REG:
				toRet += this.RS1.label + ", ";
				break;
			case VAR:
				toRet += "[" + this.RS1.label + " + " + this.toIntOffset + "], ";
				break;
			case ARR:
				toRet += "[" + this.RS1.label + " + " + this.toRegisterOffset.label + "], ";
				break;
			default:
				System.out.println("Cannot mov to immediate type");
				//fail
				break;
		}

		switch(this.fromType){
			case REG:
				toRet += this.RS2.label;
				break;
			case IMM:
				toRet += this.imm;
				break;
			case VAR:
				toRet += "[" + this.RS2.label + " + " + this.fromIntOffset + "]";
				break;
			case ARR:
				toRet += "[" + this.RS2.label + " + " + this.fromRegisterOffset.label + "]";
				break;
			default:
				System.out.println("MOV From var type failed");
				//fail
				break;
		}

		return toRet;
	}
}
