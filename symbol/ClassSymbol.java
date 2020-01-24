package symbol;
import symbol.Symbol;
import arch.Label;

public class ClassSymbol extends Symbol{
	public ClassSymbol superclass;

	public ClassSymbol(String name){
		super(name);
	}

	public ClassSymbol(String name, ClassSymbol superclass){
		super(name);
		this.superclass = superclass;
	}
}
