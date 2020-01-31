package arch;
import arch.Operation;
import arch.Register;

public class UnaryOp extends Instruction {

	public UnaryOp(String operation, Register RS1){
		this.op = this.getOp(operation);
		this.RS1 = RS1;
	}

	private Operation getOp(String operation){
		switch(operation){
			case "!":
				return Operation.NOT;
			case "-":
				return Operation.NEGQ;
			default:
				//fail
				System.out.println("Unary Op not recognized");
				break;
		}
		return null;
	}

	public String toX86(){
		return super.toX86() + " " + this.RS1.label;
	}
}

