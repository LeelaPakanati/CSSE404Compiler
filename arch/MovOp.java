package arch;
import symbol.VarSymbol;
import arch.Operation;
import arch.Register;

public class MovOp extends Instruction {
	Register regOffset;
	int intOffset = -1;
	boolean toVar = false;

	public MovOp(Register RS1, int imm){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.imm = imm;
	}

	public MovOp(Register RS1, Register RS2){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, Register RS2, int offset){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
		this.intOffset = offset;
		this.toVar = false;
	}

	public MovOp(Register RS1, int offset, Register RS2){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.intOffset = offset;
		this.toVar = true;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, int offset, int imm){
		this.op = Operation.MOV;
		this.intOffset = offset;
		this.imm = imm;
	}

	public String toX86(){
		String toRet = super.toX86() + " ";

		if((this.toVar) && (this.intOffset != -1)){
			toRet += "[" + this.RS1.label + " + " + this.intOffset + "], ";
		}else{
			toRet += this.RS1.label + ", ";
		}

		if(this.RS2 != null){
			if((!this.toVar) && (this.intOffset != -1)){
				toRet += "[" + this.RS2.label + " + " + this.intOffset + "] ";
			} else{
				toRet += this.RS2.label + " ";
			}
		}else{
			toRet += this.imm;
		}

		return toRet;
	}


}
