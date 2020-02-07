package arch;

public class Label extends Instruction {
	String labelName;

	public Label(String labelName){
		this.labelName = labelName;
	}

	public String toX86(){
		return this.labelName + ":";
	}

	public String toX86(boolean colon){
		if(colon){
			return this.labelName + ":";
		} else{
			return this.labelName;
		}
	}
}

