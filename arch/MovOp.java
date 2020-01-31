package arch;
import symbol.VarSymbol;
import arch.Operation;
import arch.Register;

public class MovOp extends Instruction {
	VarSymbol toVar;
	VarSymbol fromVar;
	Register ref;

	public MovOp(Register RS1, int imm){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.imm = imm;
	}

	public MovOp(Register RS1, VarSymbol fromVar){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.fromVar = fromVar;
	}

	public MovOp(Register RS1, Register RS2){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, VarSymbol fromVar, Register ref){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.fromVar = fromVar;
		this.ref = ref;
	}


	public MovOp(VarSymbol toVar, Register RS1){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.toVar = toVar;
	}

	public MovOp(VarSymbol toVar, int imm){
		this.op = Operation.MOV;
		this.toVar = toVar;
		this.imm = imm;
	}

	public MovOp(VarSymbol toVar, VarSymbol fromVar){
		this.op = Operation.MOV;
		this.toVar = toVar;
		this.fromVar = fromVar;
	}

	public MovOp(VarSymbol toVar, Register ref, Register RS1){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.ref = ref;
		this.toVar = toVar;
	}

	public String toX86(){
		String toRet = super.toX86() + " ";
		if (this.toVar != null){
			toRet += "Mem->" + this.toVar.name;
			if(this.ref != null){
				toRet += "[" + this.ref.label + "]";
			}
			toRet += " ";
		} else {
			toRet += this.RS1.label + " ";
		}
		
		if (this.fromVar != null) {
			toRet += "Mem->" + this.fromVar.name;
			if(this.ref != null){
				toRet += "[" + this.ref.label + "]";
			}
		} else if (this.RS1 != null) {
			toRet += this.RS1.label;
		} else {
			toRet += this.imm;
		}

		return toRet;
	}


}
