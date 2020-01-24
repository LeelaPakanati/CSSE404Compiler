package symbol;
import symbol.Symbol;
import arch.Label;

public class MethodSymbol extends Symbol{
	public String retType;

	public MethodSymbol(String name, String retType){
		super(name);
		this.retType = retType;
	}

}

