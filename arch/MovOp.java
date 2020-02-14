package arch;
import symbol.VarSymbol;
import symbol.VarType;
import arch.Operation;
import arch.Register;

public class MovOp extends Instruction {
	//TODO Assumes standard AX accumilation format; check and add push/pops as necesary later 
	//	   Doesn't preserve other regs

	public MovOp(Register RS1, int imm){
		this.op = Operation.MOV;
		this.toType = ArgType.REG;
		this.fromType = ArgType.IMM;

		this.RS1 = RS1;
		this.imm = imm;
	}

	public MovOp(Register RS1, Register RS2){
		this.op = Operation.MOV;
		this.toType = ArgType.REG;
		this.fromType = ArgType.REG;

		this.RS1 = RS1;
		this.RS2 = RS2;
	}

	public MovOp(Register RS1, VarSymbol toVar){
		this.op = Operation.MOV;
		this.toType = ArgType.REG;
		this.fromType = ArgType.VAR;

		this.RS1 = RS1;
		this.varSymbol = toVar;
	}

	public MovOp(VarSymbol fromVar, Register RS2){
		this.op = Operation.MOV;
		this.toType = ArgType.VAR;
		this.fromType = ArgType.REG;

		this.varSymbol = fromVar;
		this.RS2 = RS2;
	}

	public MovOp(VarSymbol fromVar, int imm){
		this.op = Operation.MOV;
		this.toType = ArgType.VAR;
		this.fromType = ArgType.IMM;

		this.varSymbol = fromVar;
		this.imm = imm;
	}

	public MovOp(VarSymbol toVar, Register regArrRef, Register RS2){
		this.op = Operation.MOV;
		this.toType = ArgType.ARR;
		this.fromType = ArgType.REG;
		this.arrRefaByReg = true;

		this.varSymbol = toVar;
		this.regArrRef = regArrRef;
		this.RS2 = RS2;
	}

	public MovOp(VarSymbol toVar, int intArrRef, Register RS2){
		this.op = Operation.MOV;
		this.toType = ArgType.ARR;
		this.fromType = ArgType.REG;
		this.arrRefaByReg = false;

		this.varSymbol = toVar;
		this.intArrRef = intArrRef;
		this.RS2 = RS2;
	}

	public MovOp(VarSymbol toVar, Register regArrRef, int imm){
		this.op = Operation.MOV;
		this.toType = ArgType.ARR;
		this.fromType = ArgType.IMM;
		this.arrRefaByReg = true;

		this.varSymbol = toVar;
		this.regArrRef = regArrRef;
		this.imm = imm;
	}

	public MovOp(VarSymbol toVar, int intArrRef, int imm){
		this.op = Operation.MOV;
		this.toType = ArgType.ARR;
		this.fromType = ArgType.IMM;
		this.arrRefaByReg = false;

		this.varSymbol = toVar;
		this.intArrRef = intArrRef;
		this.imm = imm;
	}

	public MovOp(Register RS1, VarSymbol fromVar, Register regArrRef){
		this.op = Operation.MOV;
		this.toType = ArgType.REG;
		this.fromType = ArgType.ARR;
		this.arrRefaByReg = true;

		this.RS1 = RS1;
		this.varSymbol = fromVar;
		this.regArrRef = regArrRef;
	}

	public MovOp(Register RS1, VarSymbol fromVar, int intArrRef){
		this.op = Operation.MOV;
		this.toType = ArgType.REG;
		this.fromType = ArgType.ARR;
		this.arrRefaByReg = false;

		this.RS1 = RS1;
		this.intArrRef = intArrRef;
		this.regArrRef = regArrRef;
	}

	//stupid case
	public MovOp(Register RS1, int intArrRef, Register RS2){
		this.op = Operation.MOV;
		this.RS1 = RS1;
		this.intArrRef = intArrRef;
		this.RS2 = RS2;
	}

	public String toX86(){
		String toRet = "";

		if (this.fromType == null){ //weird case; i hate it but ya know
			toRet += super.toX86() + " [" + this.RS1.label + " + " + this.intArrRef + "], " + this.RS2.label;

		}
		
		if ((this.fromType == ArgType.VAR) || (this.toType == ArgType.VAR)){
			if(this.varSymbol.varType == VarType.classVar){
				toRet += this.loadClass();
			}
		} else if ((this.fromType == ArgType.ARR) || (this.toType == ArgType.ARR)){
			toRet += this.loadArrRef();
		}

		toRet += super.toX86() + " " + this.getOperand(true) + ", " + this.getOperand(false);
		return toRet;
	}
}

