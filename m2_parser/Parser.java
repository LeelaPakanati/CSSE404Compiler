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

public class Parser {

	ArrayList<String[]> toParse;
	int parseIndex;

	public Parser(ArrayList<String[]> toParse) {
		this.toParse = toParse;
		this.parseIndex = 0;
	}

	private String[] nextWord(){
		try{
			return toParse.get(parseIndex++);
		} catch(Exception e) {
			String[] EOF = {"", ""};
			return EOF;
		}
	}

	private Boolean fail(int resetIndex){
		this.parseIndex = resetIndex;
		return false;
	}

	public Boolean parseProgram(){
		int resetIndex = this.parseIndex;
		System.out.println("Program (");

		if(!parseMainClassDecl()) { 
			return fail(resetIndex);
		}

		if(!parseClassDeclList()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != ""){
			return fail(resetIndex);
		}

		System.out.println(")");
		return true;
	}

	public Boolean parseMainClassDecl(){
		int resetIndex = this.parseIndex;
		System.out.println("MainClassDecl (");

		if(nextWord()[1] != "class"){
			return fail(resetIndex);
		}

		if(!parseID()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "{"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "public"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "static"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "void"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "main"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "("){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "String"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "["){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "]"){
			return fail(resetIndex);
		}

		if(!parseID()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != ")"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "{"){
			return fail(resetIndex);
		}

		if(!parseStmtList()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "}"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "}"){
			return fail(resetIndex);
		}

		System.out.println(")");
		return true;
	}

	public Boolean parseClassDeclList(){
		int resetIndex = this.parseIndex;
		System.out.println("ClassDeclList");

		if(!parseClassDecl()){
			System.out.println(")");
			return true;
		}

		if(!parseClassDeclList()){
			return fail(resetIndex);
		}
		
		System.out.println(")");
		return true;
	}

	public Boolean parseClassDecl(){
		int resetIndex = this.parseIndex;
		System.out.println("ClassDecl (");

		if(nextWord()[1] != "class"){
			return fail(resetIndex);
		}

		if(!parseID()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "["){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "extends"){
			return fail(resetIndex);
		}

		if(!parseID()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "]"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "{"){
			return fail(resetIndex);
		}

		if(!parseClassVarDeclList()){
			return fail(resetIndex);
		}

		if(!parseMethodVarDeclList()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "}"){
			return fail(resetIndex);
		}

		
		System.out.println(")");
		return true;
	}

	public Boolean parseClassVarDeclList(){
		int resetIndex = this.parseIndex;
		System.out.println("ClassVarDeclList (");

		if(!parseClassVarDecl()){
			System.out.println(")");
			return true;
		}

		if(!parseClassVarDeclList()){
			return fail(resetIndex);
		}
		
		System.out.println(")");
		return true;
	}

	public Boolean parseClassVarDecl(){
		int resetIndex = this.parseIndex;
		System.out.println("ClassVarDecl (");

		if(!parseType()){
			return fail(resetIndex);
		}
		
		if(!parseID()){
			return fail(resetIndex);
		}

		System.out.println(")");
		return true;
	}

	public Boolean parseMethodDeclList(){
		int resetIndex = this.parseIndex;
		System.out.println("ClassMethodDeclList (");

		if(!parseClassMethodDecl()){
			System.out.println(")");
			return true;
		}

		if(!parseClassMethodDeclList()){
			return fail(resetIndex);
		}
		
		System.out.println(")");
		return true;
	}
	
	public Boolean parseMethodDecl(){
		int resetIndex = this.parseIndex;
		System.out.println(" (");

		if(nextWord()[1] != "public"){
			return fail(resetIndex);
		}

		if(!parseType()){
			return fail(resetIndex);
		}
		
		if(!parseID()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "("){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "["){
			return fail(resetIndex);
		}

		if(!parseFormal()){
			return fail(resetIndex);
		}

		if(!parseFormalList()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "]"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != ")"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "{"){
			return fail(resetIndex);
		}

		if(!parseStmtList()){
			return fail(resetIndex);
		}


		if(nextWord()[1] != "return"){
			return fail(resetIndex);
		}

		if(!parseExpr()){
			return fail(resetIndex);
		}

		if(nextWord()[1] != ";"){
			return fail(resetIndex);
		}

		if(nextWord()[1] != "}"){
			return fail(resetIndex);
		}


		System.out.println(")");
		return true;
	}

	public Boolean parseFormalList(){
		int resetIndex = this.parseIndex;
		System.out.println("FormalList (");

		if(!parseFormal()){
			System.out.println(")");
			return true;
		}

		if(!parseFormalList()){
			return fail(resetIndex);
		}

		System.out.println(")");
		return true;
	}

	public Boolean parseFormal(){
		int resetIndex = this.parseIndex;
		System.out.println("Formal (");

		if(!parseType()){
			return fail(resetIndex);
		}
		
		if(!parseID()){
			return fail(resetIndex);
		}

		System.out.println(")");
		return true;
	}

	public Boolean parseType(){
		int resetIndex = this.parseIndex;
		System.out.println("Type (");

		if(parseID()){
			System.out.println(")");
			return true;
		}

		if(nextWord()[1] = "int"){
			System.out.println(")");
			return true;
		}

		return fail(resetIndex);
	}

	public Boolean parseStmtList(){
		int resetIndex = this.parseIndex;
		System.out.println("StmtList (");

		if(!parseStmt()){
			System.out.println(")");
			return true;
		}

		if(!parseStmtList()){
			return fail(resetIndex);
		}
		
		System.out.println(")");
		return true;
	}

	public Boolean parseStmt(){
		int resetIndex = this.parseIndex;
		System.out.println("Stmt (");

		System.out.println(")");
		return true;
	}

	public Boolean parseBracketStmt(){
		int resetIndex = this.parseIndex;
		System.out.println("BracketStmt (");

		System.out.println(")");
		return true;
	}

	public Boolean parseAtomicStmtList(){
		int resetIndex = this.parseIndex;
		System.out.println("AtomicStmtList (");

		System.out.println(")");
		return true;
	}

	public Boolean parseAtomicStmt(){
		int resetIndex = this.parseIndex;
		System.out.println("AtomicStmt (");


		System.out.println(")");
		return true;
	}

	public Boolean parseExprList(){
		int resetIndex = this.parseIndex;
		System.out.println("ExprList (");

		System.out.println(")");
		return true;
	}

	public Boolean parseExpr(){
		int resetIndex = this.parseIndex;
		System.out.println("Expr (");


		System.out.println(")");
		return true;
	}

	public Boolean parseExprP(){
		int resetIndex = this.parseIndex;
		System.out.println("ExprP (");


		System.out.println(")");
		return true;
	}

	public Boolean parseOrOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("OrOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseOrOperandP(){
		int resetIndex = this.parseIndex;
		System.out.println("OrOperandP (");


		System.out.println(")");
		return true;
	}

	public Boolean parseAndOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("AndOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseAndOperandP(){
		int resetIndex = this.parseIndex;
		System.out.println("AndOperandP (");


		System.out.println(")");
		return true;
	}

	public Boolean parseEqualityOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("EqualityOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseEqualityOperandP(){
		int resetIndex = this.parseIndex;
		System.out.println("EqualityOperandP (");


		System.out.println(")");
		return true;
	}

	public Boolean parseRelOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("RelOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseAddOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("AddOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseAddOperandP(){
		int resetIndex = this.parseIndex;
		System.out.println("AddOperandP (");


		System.out.println(")");
		return true;
	}

	public Boolean parseMulOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("MulOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseUnaryOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("UnaryOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseParenOperand(){
		int resetIndex = this.parseIndex;
		System.out.println("ParenOperand (");


		System.out.println(")");
		return true;
	}

	public Boolean parseValue(){
		int resetIndex = this.parseIndex;
		System.out.println("Value (");


		System.out.println(")");
		return true;
	}

	public Boolean parseID(){
		int resetIndex = this.parseIndex;
		System.out.println("ID (");

		
		System.out.println(")");
		return true;
	}
}
