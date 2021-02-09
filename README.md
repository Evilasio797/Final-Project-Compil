# Final-Project-Compil

MiniJava Language JavaCC Compiler
Federal University of Ceara, Department of Computing, Course of Compiler Construction.

Projeto final de Compiladores
   
   1. Lexical Analyser and Parser Phase
   Instructions:
 
 1- cd src/lexical_analyser
 2- javacc Parser.jj
 3- cd ..
 4- javac lexical_analyser/*.java
 5- javac semantic_actions/*/*.java
 6- javac *.java
 7- java Main test_files/minijava.java 

2. Semantic Actions Phase
   -- need to fix some bugs in semantic_actions
   
   Instructions:
First, compile the nodes of the abstract tree. After you compile the visitor.
 1- cd src/
 2- javac semantic_actions/syntax_tree/*.java
 3- javac semantic_actions/visitor/*.java

Generate the parser. It is like the previous one.
 1- cd lexical_analyser
 2- javacc Parser.jj
 3- cd ..
 4- javac lexical_analyser/*.java

Run the compiler.
 java lexical_analyser.Parser test_files/minijava.java
