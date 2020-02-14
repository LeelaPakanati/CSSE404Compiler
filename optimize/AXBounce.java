package optimize;
import arch.*;
import java.util.*;


public class AXBounce implements Optimizer{
	public AXBounce(){

	}

	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		// this is an optimization for instance of: Var/Arr <- RegAx; RegAx <- Var/Arr
		//										  : RegAX <- REG/IMM?VAR/ARR; RegCX <- RegAx
		for(int i = 0; i < insts.size(); i++){		
			Instruction inst = insts.get(i);

			//if inst is a MovOp
			if(!(inst instanceof MovOp)){
				toRet.add(inst);
				continue;
			}

			MovOp movinst = (MovOp) inst;

			//if moving to Var from Reg
			if(((movinst.toType == ArgType.VAR) || (movinst.toType == ArgType.ARR)) && (movinst.fromType == ArgType.REG)){
				Instruction inst2 = insts.get(i+1);

				if(!(inst2 instanceof MovOp)){ //if not movinst, move on
					toRet.add(inst);
					continue;
				}

				MovOp movinst2 = (MovOp) inst2;
				//if second mov instruction is from var to reg
				if((movinst2.toType == ArgType.REG) && (movinst2.fromType == movinst.toType)){
					//check operand are same
					if((movinst2.RS1 == movinst.RS2) && (movinst2.varSymbol.name.equals(movinst.varSymbol.name))){

						if(movinst.toType == ArgType.ARR){	//extra checks for arrs
							if(movinst.arrRefByReg){
								if(movinst.regArrRef != movinst2.regArrRef){
									toRet.add(inst);
									continue;
								}
							} else{
								if(movinst.intArrRef != movinst2.intArrRef){
									toRet.add(inst);
									continue;
								}
							}
						}

						//System.out.println("AXBOUNCE: 1");
						toRet.add(inst);
						i++;			//skip second inst of loading back into REG
						continue;
					}
				}
			}

			//if moving to Reg
			if(movinst.toType == ArgType.REG){
				Instruction inst2 = insts.get(i+1);

				if(!(inst2 instanceof MovOp)){ //if not movinst, move on
					toRet.add(inst);
					continue;
				}

				MovOp movinst2 = (MovOp) inst2;
				//if second mov instruction is from Reg to REG
				if((movinst2.toType == ArgType.REG) && (movinst2.fromType == ArgType.REG)){
					//check operand are same
					if(movinst.RS1 == movinst2.RS2){
						//System.out.println("AXBOUNCE: 2");
						switch(movinst.fromType){
							case REG:
								toRet.add(new MovOp(movinst2.RS1, movinst.RS1));
								break;
							case IMM:
								toRet.add(new MovOp(movinst2.RS1, movinst.imm));
								break;
							case VAR:
								toRet.add(new MovOp(movinst2.RS1, movinst.varSymbol));
								break;
							case ARR:
								if(movinst.arrRefByReg){
									toRet.add(new MovOp(movinst2.RS1, movinst.varSymbol, movinst.regArrRef));
								} else{
									toRet.add(new MovOp(movinst2.RS1, movinst.varSymbol, movinst.intArrRef));
								}
								break;
							default:
								toRet.add(inst);
								continue;

						}
						i++;			//skip second inst 
						continue;
					}
				}
			}

			toRet.add(inst);			//add normal
			continue;
		}

		return toRet;
	}
}
