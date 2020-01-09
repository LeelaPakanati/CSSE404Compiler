import java.io.Reader;

public class LexStruct {
	String word;
	String type;
	int nextIdx;
	Boolean eof;
	
	public LexStruct(String word, String type, int nextIdx) {
		this.word = word;
		this.type = type;
		this.nextIdx = nextIdx;
	}
}
