package symbol;
import symbol.Symbol;
import symbol.VarType;

public class VarSymbol extends Symbol{
	public String type;
	public int index;
	public VarType varType;
	public boolean isArr;
	
	public VarSymbol(String name, String type){
		super(name);
		this.type = type;
		this.varType = VarType.localVar;
	}
}

