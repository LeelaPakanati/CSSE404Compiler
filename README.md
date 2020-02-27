# Mini Java Compiler

Full documentation available in `Compiler_Documentation.pdf`

## Usage:
Running:
```
java MiniJavaCompiler <source_file>.java
```
outputs compiled asm. This is then assembled with nasm and linked with gcc. 

Use compile.sh to automate this:
```
./compile.sh <source_file>.sh
```

