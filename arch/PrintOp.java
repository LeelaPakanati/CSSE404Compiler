package arch;
import arch.*;
import java.util.*;

public class PrintOp extends Instruction {

	public PrintOp(){
	}

	public String toX86(){
		String toRet = "\n";
		List<Instruction> ins = new ArrayList<Instruction>();
		ins.add(new Label("PrintNum"));
		ins.add(new PushOp(Register.BP));			//push frame pointer
		ins.add(new MovOp(Register.BP, Register.SP)); //save stack pointer as new frame pointer

		ins.add(new MovOp(Register.AX, Register.BP, 8));
		ins.add(new PushOp(Register.AX));

		ins.add(new PushOp(new Label("message")));

		ins.add(new JumpOp(new Label("printf"), true));
		ins.add(new MovOp(Register.SP, Register.BP)); //restore sp to bp
		ins.add(new PopOp(Register.BP)); //restore frame pointer
		ins.add(new JumpOp());			//return 

		for(Instruction in : ins){
			toRet += in.toX86() + "\n";
		}

		toRet += "message db \"%d\", 10, 0\n";
		return toRet;
	}
}
