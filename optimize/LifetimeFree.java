package optimize;
import arch.*;
import java.util.*;


public class LifetimeFree implements Optimizer{
	public List<Instruction> Optimize(List<Instruction> insts){
		List<Instruction> toRet = new ArrayList<Instruction>();

		for(int i = 0; i < insts.size(); i++){		
			Instruction inst = insts.get(i);

			//if inst is a JumpOp (to malloc)
			if(!(inst instanceof JumpOp)){
				toRet.add(inst);
				continue;
			}

			//check call to malloc
			JumpOp jop = (JumpOp) inst;
			if(!jop.label.labelName.equals("malloc")){
				toRet.add(inst);
				continue;
			}
			Tree varTree = new Tree();
			varTree.line = i;
			

		}

		return toRet;
	}
}

class Tree{
	public int line;
	public List<Tree> children;

	public Tree(int line){ 
		this.line = line; 
		this.children = new ArrayList<Tree>();
	}
}
