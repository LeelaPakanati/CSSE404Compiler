package lexer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lexer {

	String filepath;
	String toLex;
	int currIdx = 0;

	public Lexer(String filepath) {
		this.filepath = filepath;

		BufferedReader reader;
		StringBuilder builder;
		try{
			reader = new BufferedReader(new FileReader(filepath));
			builder = new StringBuilder();
			String currentLine = reader.readLine();
			while (currentLine != null) {
				builder.append(currentLine);
				builder.append('\n');
				currentLine = reader.readLine();
			}
		}catch(IOException e){
			System.out.print("Invalid File");
			return;
		}

		this.toLex =  builder.toString();
		this.currIdx = 0;
	}

	public ArrayList<String[]> analyse(){
		return this.lexProgram();		
	}
	
	private ArrayList<String[]> lexProgram(){

		Boolean cont = true;
		ArrayList<String[]> results = new ArrayList<String[]>();

		while(currIdx < this.toLex.length()) {
			LexStruct ws = lexWhitespace();
			if(ws != null){
				currIdx = ws.nextIdx;
				continue;
			}

			LexStruct token = this.lexToken();
			if(token != null){
				String[] result = {token.type, token.word};
				results.add(result);
				currIdx = token.nextIdx;
				continue;
			}
			return results;
		}
		results.add(new String[]{"eof", "$"});
		return results;
	}

	private LexStruct lexToken(){
		LexStruct integer = this.lexInteger();
		if(integer != null)
			return integer;

		LexStruct operator = this.lexOperator();
		if(operator != null)
			return operator;

		LexStruct reservedWord = this.lexReservedWord();
		if(reservedWord != null)
			return reservedWord;

		LexStruct delimiter = this.lexDelimiter();
		if(delimiter != null)
			return delimiter;

		LexStruct id = lexId();
		if(id != null)
			return id;

		System.out.print("Invalid Program at char" + this.currIdx + "\n");
		return null;
	}

	private LexStruct lexInteger(){
		String word = "";
		int nextIdx = 0;
		char nextChar = 0;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);

		if(!Character.isDigit(nextChar))
			return null;

		nextIdx++;
		word += nextChar;

		if(nextChar == '0')
			return new LexStruct(word, "Integer", this.currIdx + nextIdx);

		while(true){
			nextChar = this.toLex.charAt(this.currIdx + nextIdx);
			if(!Character.isDigit(nextChar)){
				break;
			}
			nextIdx++;
			word += nextChar;
		}
		return new LexStruct(word, "Integer", this.currIdx + nextIdx);
	}

	private LexStruct lexId(){
		String word = "";
		int nextIdx = 0;
		char nextChar = 0;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		if(!Character.isLetter(nextChar)){
			return null;
		}

		nextIdx++;
		word += nextChar;
		while(true){
			nextChar = this.toLex.charAt(this.currIdx + nextIdx);
			if(!Character.isLetter(nextChar) && !Character.isDigit(nextChar) && nextChar != '_'){
				break;
			}
			nextIdx++;
			word += nextChar;
		}

		return new LexStruct(word, "ID", this.currIdx + nextIdx);
	}


	private LexStruct lexOperator() {
		String word = "";
		int nextIdx = 0;
		char nextChar = 0;
		String operatorSingle[] = {"+", "-", "*", "/", "<", "!", ">", "!"};
		String operatorDouble[] = {"<=", ">=","==", "!=", "&&", "||"};

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		nextIdx++;
		word += nextChar;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		word += nextChar;

		//Math with double char operators
		for(String op : operatorDouble) {
			if(op.equals(word)) {
				nextIdx++;
				return new LexStruct(op, "Operator", this.currIdx + nextIdx);
			}
		}

		//Math with single char operators
		for(String op : operatorSingle) {
			if(op.equals(String.valueOf(word.charAt(0)))) {
				return new LexStruct(op, "Operator", this.currIdx + nextIdx);
			}
		}

		return null;
	}

	private LexStruct lexDelimiter(){
		int nextIdx = 0;
		char nextChar = 0;
		String delimiter[] = {";", ".", ",", "=", "(", ")", "{", "}", "[", "]"};

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);

		for(String ch : delimiter) {
			if(ch.equals(String.valueOf(nextChar))) {
				nextIdx++;
				return new LexStruct(ch, "Delimiter", this.currIdx + nextIdx);
			}
		}
		return null;
	}

	private LexStruct lexReservedWord() {
		String reservedWord[] = {"class", "public", "static", "extends", "void", "int", "boolean", "if", "else", "while", "return", "null", "true", "false", "this", "new", "String", "main", "System.out.println", "length"};

		String word = "";
		int nextIdx = 0;
		char nextChar = 0;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		if(!Character.isLetter(nextChar)){
			return null;
		}

		nextIdx++;
		word += nextChar;
		while(true){
			nextChar = this.toLex.charAt(this.currIdx + nextIdx);
			if(!Character.isLetter(nextChar) && nextChar != '.' && nextChar != '_'){
				break;
			}
			nextIdx++;
			word += nextChar;
		}

		for(String rw : reservedWord) {
			if(rw.equals(word)) {
				return new LexStruct(word, "ReservedWord", this.currIdx + nextIdx);
			}
		}

		return null;
	}

	public LexStruct lexWhitespace(){
		String word = "";
		int nextIdx = 0;
		char nextChar = 0;

		LexStruct commentLex = this.lexComment();
		if(commentLex != null)
			return commentLex;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		if(nextChar == ' ' || nextChar == '\n' || nextChar == '\t') {
			nextIdx++;
			return new LexStruct("", "Whitespace", this.currIdx + nextIdx);
		}

		return null;
	}
	
	public LexStruct lexComment(){
		String word = "";
		int nextIdx = 0;
		char nextChar = 0;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		if(nextChar != '/')
			return null;
		nextIdx++;

		nextChar = this.toLex.charAt(this.currIdx + nextIdx);
		//line comment
		if(nextChar == '/'){
			nextIdx++;
			while(this.currIdx + nextIdx < this.toLex.length()){
				nextChar = this.toLex.charAt(this.currIdx + nextIdx);
				nextIdx++;
				if(nextChar == '\n'){
					break;
				}
				word += nextChar;
			}
			return new LexStruct(word, "Comment", this.currIdx + nextIdx);
		}
		
		//comment between /*  */
		if(nextChar == '*'){
			nextIdx++;
			while(this.currIdx + nextIdx < this.toLex.length()){
				nextChar = this.toLex.charAt(this.currIdx + nextIdx);
				nextIdx++;
				if(nextChar == '*'){
					nextChar = this.toLex.charAt(this.currIdx + nextIdx);
					nextIdx++;
					if(nextChar == '/'){
						nextIdx++;
						break;
					}
					word += '*' + nextChar;
					continue;
				}
				word += nextChar;
			}
			// allow multiline comment to be terminated by end of file as well as '*/'
			return new LexStruct(word, "Comment", this.currIdx + nextIdx);
		}

		// not actually a comment, just a '/'
		return null;
	}
}
