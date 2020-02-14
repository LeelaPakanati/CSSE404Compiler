package arch;
import arch.*;
import symbol.*;
import java.util.*;

public abstract class Instruction {
	public Operation op;
	public ArgType fromType;
	public ArgType toType;

	public Register RS1;
	public Register RS2;
	public int imm;

	public Register regArrRef;
	public int intArrRef;
	public boolean arrRefByReg;
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
		List<Instruction> insts = new ArrayList<Instruction>();
		
		if(saveReg){
			insts.add(new PushOp(RS2));									// should just be AX 
		}

		if(this.arrRefByReg){
			insts.add(new MovOp(Register.AX, this.regArrRef));		   		// move ref to ax register
		} else{
			insts.add(new MovOp(Register.AX, this.intArrRef));
		}
		insts.add(new ArithOp(Operation.ADD, Register.AX, 1)); 	// the first item is the length so add 1 to ref value

		insts.add(new MovOp(Register.DX, 4));						// load 4 
		insts.add(new ArithOp(Operation.IMUL, Register.DX));  		// mul arr ref by 4
		insts.add(new MovOp(Register.CX, Register.AX));		   		// move offset to cx register

		insts.add(new MovOp(Register.BX, this.varSymbol));	   		// load array pointer into BX

		if(saveReg){
			insts.add(new PopOp(RS2));										// should just be AX 
		}

		for(Instruction inst : insts){
			toRet += inst.toX86() + "\n";
		}

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
			
		switch(type){
			case REG:
				toRet += reg.label;
				break;
			case VAR:
				RegOffset varAddr = this.getVarAddr(this.varSymbol);
				toRet += "dword [" + varAddr.reg.label + " + " + varAddr.offset + "]";
				break;
			case ARR:
				toRet += "dword [" + Register.BX.label + " + " + Register.CX.label + "]";
				break;
			case IMM:
				toRet += this.imm;
				break;
			default:
				System.err.println("Inst Operands failed");
				//fail
				break;
		}
		return toRet;
	}

}

