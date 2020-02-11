package optimize;
import arch.*;
import java.util.*;


public class PushMem implements Optimizer{
	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		// this is an optimization for instance for combining loading to reg and pushing to stack: AX <- <Something>; push AX
		// turns into: push <Var or INT>
		for(int i = 0; i < insts.size(); ++i){		
			Instruction inst = insts.get(i);

			//if not inst is an Mov op w/ Reg <- Mem
			if( !(	(inst instanceof MovOp) && 
					(((MovOp)inst).toType == MovArgType.REG)	)) {

				toRet.add(inst);
				continue;
			}

			Register reg = inst.RS1;

			Instruction nextInst = insts.get(i+1);
			//if not inst is an push op w/ same reg
			if(!(	(nextInst instanceof PushOp) && 
					(nextInst.RS1 == reg)	)){
				toRet.add(inst);
				continue;
			}

			PushOp newOp = new PushOp(00000);
			switch(((MovOp)inst).fromType){
				case REG:
					newOp = new PushOp(inst.RS1);
					break;
				case IMM:
					newOp = new PushOp(inst.imm);
					break;
				case VAR:
					newOp = new PushOp(inst.RS2, ((MovOp)inst).fromIntOffset);
					break;
				case ARR:
					newOp = new PushOp(inst.RS2, ((MovOp)inst).fromRegisterOffset);
					break;
				default:
					System.out.println("Push From var type failed");
					//fail
					break;
			}

			i++; //skip next instruction;
			toRet.add(newOp);
		}

		return toRet;
	}
}
