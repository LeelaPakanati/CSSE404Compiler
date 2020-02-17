package lexer;
import java.io.Reader;

public class LexStruct {
	String word;
	String type;
	int nextIdx;
	int lineCount;
	Boolean eof;
	
	public LexStruct(String word, String type, int nextIdx, int lineCount) {
		this.word = word;
		this.type = type;
		this.nextIdx = nextIdx;
		this.lineCount = lineCount;
	}
}
