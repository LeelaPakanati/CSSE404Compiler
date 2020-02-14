package arch;
import arch.Register;
import symbol.*;

public abstract class Instruction {
	public Operation op;
	public ArgType fromType;
	public ArgType toType;

	public Register RS1;
	public Register RS2;
	public int imm;

	public Register regArrRef;
	public int intArrRef;
	public boolean arrRefaByReg;
	public VarSymbol varSymbol;


	public String toX86(){
		return "\t" + this.op.name().toLowerCase();
	}

	public RegOffset getVarAddr(VarSymbol varSymbol){
		if(varSymbol.name.equals("this")){
			//args | ClassAddr | ret ins | BP | 
			return new RegOffset(Register.BP, 8);
		} else{
			switch(varSymbol.varType){
				case localVar:
					return new RegOffset(Register.BP, -4*varSymbol.index);
				case classVar:
					return new RegOffset(Register.BX, 4*varSymbol.index);
				case inputArg:
					return new RegOffset(Register.BP, 4*varSymbol.index);
			}
		}
		return new RegOffset(Register.ERROR, 0);
	}

	public String loadClass(){
		MovOp loadReg = new MovOp(Register.BX, new VarSymbol("this", null));		//load 'this' address into BX
		return loadReg.toX86() + "\n";
	}

	public String loadArrRef(){
		String toRet = "";
		boolean saveReg = (this.fromType == ArgType.REG);
		
		PushOp push = new PushOp(RS2);									// should just be AX 

		ArithOp add1 = new ArithOp(Operation.ADD, this.regArrRef, 1); 	// the first item is the length so add 1 to ref value
		MovOp mov4  = new MovOp(Register.DX, 4);						// load 4 
		ArithOp mul4  = new ArithOp(Operation.IMUL, Register.DX);  		// mul arr ref by 4
		MovOp movCX = new MovOp(Register.CX, Register.AX);		   		// move offset to cx register

		MovOp loadVar = new MovOp(Register.BX, this.varSymbol);	   		// load array pointer into BX

		PopOp pop = new PopOp(RS2);										// should just be AX 

		if(saveReg){ toRet += push.toX86() + "\n"; }
		toRet += add1.toX86() + "\n";
		toRet += mov4.toX86() + "\n";
		toRet += mul4.toX86() + "\n";
		toRet += movCX.toX86() + "\n";
		toRet += loadVar.toX86() + "\n";
		if(saveReg){ toRet += pop.toX86() + "\n"; }

		return toRet;
	}

	public String getOperand(boolean dest){
		String toRet = "";
		Register reg;
		ArgType type;

		if (dest) {
			reg = this.RS1;
			type = this.toType;
		} else {
			reg = this.RS2;
			type = this.fromType;
		}
			
		RegOffset varAddr;
		switch(type){
			case REG:
				toRet += reg.label;
				break;
			case VAR:
				varAddr = this.getVarAddr(this.varSymbol);
				toRet += "dword [" + varAddr.reg.label + " + " + varAddr.offset + "]";
				break;
			case ARR:
				varAddr = this.getVarAddr(this.varSymbol);
				toRet += "dword [" + varAddr.reg.label + " + " + varAddr.offset + "]";
				break;
			case IMM:
				toRet += this.imm;
			default:
				System.err.println("Inst Operands failed");
				//fail
				break;
		}
		return toRet;
	}

}

