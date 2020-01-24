
build: *.java **/*.java
	javac MiniJavaCompiler.java

clean:
	find . -name "*.class" -type f -delete
