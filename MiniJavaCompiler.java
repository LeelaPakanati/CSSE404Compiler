import java.util.*;
import lexer.Lexer;
import parser.Parser;
import codegen.Program;
import symbol.*;
import arch.*;
//import optimize.*;

public class MiniJavaCompiler {

	public static void main(String[] args) {
		String filepath = args[0];
		Lexer lexer = new Lexer(filepath);
		ArrayList<String[]> results = lexer.analyse();

		//for(int i = 0; i < results.size(); i++) {
		//	System.out.println(results.get(i)[0] + ", " + results.get(i)[1]);
		//}
		SymbolTable symbols = SymbolTable.getInstance();

		Parser parser = new Parser(results);
		if(!parser.parseProgram()){
			System.err.println("Parsing Failed");
			return;
		}
		//System.out.println(parser.parseTree.toString(0));
		Program syntaxTree = new Program(parser.parseTree.getChild(0));

		//System.out.println("\nSymbol Table After Construction\n");
		//System.out.println(symbols.toString());

		List<Instruction> asm = syntaxTree.CodeGen();

		//List<Optimizer> opts = new ArrayList<Optimizer>();
		//opts.add(new AXBounce());
		//opts.add(new ComSub());
		//opts.add(new PushMem());

		//for(Optimizer opt : opts){
		//	asm = opt.Optimize(asm);
		//}
		
		for(Instruction ins : asm){
			System.out.println(ins.toX86());
		}
		
	}
}
