package symbol;
import symbol.Symbol;
import arch.Label;

public class VarSymbol extends Symbol{
	public String type;
	
	public VarSymbol(String name, String type){
		super(name);
		this.type = type;
	}

}
