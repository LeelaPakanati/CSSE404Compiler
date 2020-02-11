
build: *.java **/*.java
	javac MiniJavaCompiler.java

compile_run: build
	> compiledouts
	for file in tests/*.java ; do \
		echo `echo $$file | sed 's/tests\///g'` | tee -a compiledouts ; \
		./cr.sh `echo $$file | sed 's/.java//g' | sed 's/tests\///g'` | tee -a compiledouts ; \
		done

.ONESHELL:
compile_run_java:
	> actualouts
	cd tests
	for file in *.java; do \
		echo $$file | tee -a ../actualouts ; \
		javac $$file ; \
		java `echo $$file | sed 's/.java//g'` | tee -a ../actualouts ; \
		done
	cd ..

clean:
	find . -name "*.class" -type f -delete
	rm -f compiled/*
