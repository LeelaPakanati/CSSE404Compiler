#! /bin/sh
java MiniJavaCompiler SourceFiles/$1.java > compiled/$1.asm
nasm -f elf32 compiled/$1.asm -o compiled/$1.o
gcc -m32 compiled/$1.o -o compiled/$1
