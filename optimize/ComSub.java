package optimize;
import arch.*;
import java.util.*;


public class ComSub implements Optimizer{
	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		// this is an optimization for instance for combining multiple arithmetic operations of the same type w/ immediates
		for(int i = 0; i < insts.size(); ++i){		
			Instruction inst = insts.get(i);

			//if not inst is an Arith with imm
			if(!(inst instanceof ArithOp) || (inst.imm == 0)){
				toRet.add(inst);
				continue;
			}

			Operation inOp = inst.op;
			Register reg = inst.RS1;
			int totalImm = inst.imm;
			int j = 1;

			Instruction nextInst;
			while(true){
				nextInst = insts.get(i + j);

				//if following insts follow have same Op and same Register
				if((nextInst.op == inOp) && (nextInst.RS1 == reg) && (nextInst.imm != 0)){
					totalImm += nextInst.imm;
				} else{
					j--;
					break;
				}
				++j;
			}

			i += j;
			inst.imm = totalImm;
			toRet.add(inst);
		}

		return toRet;
	}
}
