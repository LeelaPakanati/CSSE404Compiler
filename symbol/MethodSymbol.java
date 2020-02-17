package symbol;
import symbol.Symbol;
import arch.Label;
import codegen.Formal;
import java.util.List;

public class MethodSymbol extends Symbol{
	public String retType;
	public List<Formal> argList;

	public MethodSymbol(String name, String retType){
		super(name);
		this.retType = retType;
	}

	public String getProfile(){
		String toRet = this.name + "(";
		for(Formal arg : this.argList){
			toRet += arg.getType() + ", ";
		}
		toRet = toRet.substring(0, toRet.length()-2) + ")";
		return toRet;
	}
}

