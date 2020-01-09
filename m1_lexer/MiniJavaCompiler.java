import java.util.ArrayList;

public class MiniJavaCompiler {

	public static void main(String[] args) {
		String filepath = args[0];
		Lexer lexer = new Lexer(filepath);
		ArrayList<String[]> results = lexer.analyse();
		for(int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i)[0] + ", " + results.get(i)[1]);
		}
		
	}

}
