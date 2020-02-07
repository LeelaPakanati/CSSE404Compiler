package arch;

public class VarDeclOp extends Instruction {
	String varName;
	String type;

	public LabelOP(String varName, String type){
		this.labelName = labelName;
	}

	public String toX86(){
		return this.labelName;
	}
}

