javac semantic_actions/syntax_tree/*.java
javac semantic_actions/visitor/*.java
cd lexical_analyser
javacc Parser.jj
cd ..
javac lexical_analyser/*.java