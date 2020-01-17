package compiler;
import java.util.*;
import lexer.Lexer;
import parser.Parser;
import codegen.Program;

public class MiniJavaCompiler {

	public static void main(String[] args) {
		String filepath = args[0];
		Lexer lexer = new Lexer(filepath);
		ArrayList<String[]> results = lexer.analyse();

		//for(int i = 0; i < results.size(); i++) {
		//	System.out.println(results.get(i)[0] + ", " + results.get(i)[1]);
		//}

		Parser parser = new Parser(results);
		if(!parser.parseProgram()){
			System.out.println("Parsing Failed");
			return;
		}
		//System.out.println(parser.parseTree.toString(0));
		Program syntaxTree = new Program(parser.parseTree.getChild(0));
		String asm = syntaxTree.CodeGen();
		System.out.println(asm);
		
	}
}
