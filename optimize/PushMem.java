package optimize;
import arch.*;
import java.util.*;


public class PushMem implements Optimizer{
	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		// this is an optimization for instance for combining loading to reg and pushing to stack: AX <- VAR/REG/INT/ARR; push AX
		// turns into: push VAR/ARR/INT/REG
		for(int i = 0; i < insts.size(); ++i){		
			Instruction inst = insts.get(i);

			//if not inst is an Mov op
			if( !(inst instanceof MovOp)){
				toRet.add(inst);
				continue;
			}

			// to REG
			if(inst.toType != ArgType.REG) {
				toRet.add(inst);
				continue;
			}


			Instruction nextInst = insts.get(i+1);
			//if not inst is an push op w/ same reg
			if(!(nextInst instanceof PushOp)){
				toRet.add(inst);
				continue;
			}

			if(nextInst.RS2 != inst.RS1){	// to of the move must equal from of the jump
				toRet.add(inst);
				continue;
			}


			switch(inst.fromType){
				case REG:
					toRet.add(new PushOp(inst.RS2));
					break;
				//case IMM:
				//	toRet.add(new PushOp(inst.imm));
				//	break;
				case VAR:
					toRet.add(new PushOp(inst.varSymbol));
					break;
				//case ARR:
				//		if(inst.arrRefByReg){
				//			toRet.add(new PushOp(inst.varSymbol, inst.regArrRef));
				//		} else{
				//			toRet.add(new PushOp(inst.varSymbol, inst.intArrRef));
				//		}
				//	break;
				default:
					toRet.add(inst);
					continue;
			}

			i++; //skip next instruction;
		}

		return toRet;
	}
}
