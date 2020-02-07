package symbol;
import java.util.*;
import symbol.Symbol;


public class SymbolTable{
	private static SymbolTable instance = null;

	public Map<String, Symbol> classTable;
	public List<Symbol> scope;
	public int IfWhileID;

	public static int localIdx = 1;

	private SymbolTable(){
		this.classTable = new HashMap<String, Symbol>();
		this.scope = new ArrayList<Symbol>();
		this.IfWhileID = 0;
	}

	public static SymbolTable getInstance(){
		if (instance == null){
			instance = new SymbolTable();
		}
		return instance;
	}

	public String toString(){
		String toRet = "";
		for(String key : this.classTable.keySet()){
			toRet += this.classTable.get(key).toString() + "\n";
		}
		return toRet;
	}

	public static Symbol getSymbol(List<Symbol> scope, String symbolName){
		if(scope.size() == 0){
			if(instance.classTable.keySet().contains(symbolName)){
				return instance.classTable.get(symbolName);
			}
		} else{
			List<Symbol> scopeRev = new ArrayList<Symbol>(scope);
			Collections.reverse(scopeRev);
			for(Symbol symbol : scopeRev){

				if(symbol instanceof ClassSymbol){ //if its a class symbol, have to look at superclasses
					ClassSymbol classSymbol = (ClassSymbol) symbol;

					while(true){
						if(classSymbol.symbols.keySet().contains(symbolName)){
							return classSymbol.symbols.get(symbolName);

						} else if(classSymbol.superclass != null){
							classSymbol = classSymbol.superclass;

						} else{
							break;
						}
					}

				} else{
					if(symbol.symbols.keySet().contains(symbolName)){
						return symbol.symbols.get(symbolName);
					}
				}
			}
		}

		System.out.println("Symbol " + symbolName + " not found in scope: " + getScopeStr(scope) + "\n");
		return null;
	}

	public static Symbol getSymbol(String symbolName){
		return getSymbol(instance.scope, symbolName);
	}

	public static int getIfWhileID(){
		return instance.IfWhileID++;
	}

	public static void scopeAdd(Symbol symbol){
		instance.scope.add(symbol);
	}

	public static void scopePop(){
		instance.scope.remove(instance.scope.size()-1);
	}

	public static void addSymbol(List<Symbol> scope, Symbol symbol){
		if(scope.size() == 0){
			instance.classTable.put(symbol.name, symbol);
		} else{
			Symbol lastSymbol = scope.get(scope.size() - 1);
			lastSymbol.symbols.put(symbol.name, symbol);
		}
	}

	public static void addSymbol(Symbol symbol){
		addSymbol(instance.scope, symbol);
	}

	public static void addClass(ClassSymbol classSymbol){
		instance.classTable.put(classSymbol.name, classSymbol);
	}

	public static ClassSymbol getClassSymbol(String className){
		ClassSymbol toRet = (ClassSymbol) instance.classTable.get(className);
		if(toRet != null){
			return toRet;
		} else{
			System.out.println("Class " + className + " not found.");
			return null;
		}
	}

	public static List<Symbol> getScope(){
		return instance.scope;
	}

	public static void setScope(List<Symbol> newScope){
		instance.scope = newScope;
	}

	public static String getScopeStr(List<Symbol> scope){
		String toRet = "";
		for(Symbol symbol : scope){
			toRet += symbol.name + " -> ";
		}
		return toRet;
	}
}
