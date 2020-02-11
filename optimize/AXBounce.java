package optimize;
import arch.*;
import java.util.*;


public class AXBounce implements Optimizer{
	public AXBounce(){

	}

	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		// this is an optimization for instance of: Mem[Var] <- RegAx; RegAx <- Mem[Var]
		for(int i = 0; i < insts.size(); i++){		
			Instruction inst = insts.get(i);

			//if inst is a MovOp
			if(!(inst instanceof MovOp)){
				toRet.add(inst);
				continue;
			}

			MovOp movinst = (MovOp) inst;

			//if moving to Register and from Var
			if((movinst.toType == MovArgType.VAR) && (movinst.fromType == MovArgType.REG)){
				Instruction inst2 = insts.get(i+1);

				if(!(inst2 instanceof MovOp)){ //if not movinst, move on
					toRet.add(inst);	//can skip inst2 as well
					toRet.add(inst2);	//can skip inst2 as well
					i++;
					continue;
				}

				MovOp movinst2 = (MovOp) inst2;
				//if second mov instruction is from var to reg
				if((movinst2.toType == MovArgType.REG) && (movinst2.fromType == MovArgType.VAR)){
					//check operand are same
					if((movinst2.RS1 == movinst.RS2) && (movinst2.RS2 == movinst.RS1) && (movinst2.fromIntOffset == movinst.toIntOffset)){
						toRet.add(inst);
						i++;			//skip second inst of loading back into REG
						continue;
					}
				}
			}

			toRet.add(inst);	//add normal
			continue;
		}

		return toRet;
	}
}
