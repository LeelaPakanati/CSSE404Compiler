package symbol;
import java.util.*;
import arch.Label;

public abstract class Symbol{
	public String name;
	public Map<String, Symbol> symbols;
	public Label label;

	public Symbol(String name){
		this.name = name;
		this.symbols = new HashMap<String, Symbol>();
	}

	public String toString(){
		return this.toString(0);
	}

	public String toString(int indent){
		String toRet = "\t".repeat(indent) + this.name + "\n";
		for(String key : symbols.keySet()){
			toRet += symbols.get(key).toString(indent+1);
		}
		return toRet;
	}
}
