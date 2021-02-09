# Final-Project-Compil

MiniJava Language JavaCC Compiler
Federal University of Ceara, Department of Computing, Course of Compiler Construction.

Projeto final de Compiladores
   
   1. Lexical Analyser and Parser Phase
   Instructions:
$ cd src/lexical_analyser
$ javacc Parser.jj
$ cd ..
$ javac lexical_analyser/*.java
$ javac semantic_actions/*/*.java
$ javac *.java
$ java Main test_files/minijava.java 

2. Semantic Actions Phase
   -- need to fix some bugs in semantic_actions
   
   Instructions:
First, compile the nodes of the abstract tree. After you compile the visitor.
$ cd src/
$ javac semantic_actions/syntax_tree/*.java
$ javac semantic_actions/visitor/*.java

Generate the parser. It is like the previous one.
$ cd lexical_analyser
$ javacc Parser.jj
$ cd ..
$ javac lexical_analyser/*.java

Run the compiler.
$ java lexical_analyser.Parser test_files/minijava.java
