package semantic_actions.syntax_tree;

import semantic_actions.visitor.*;
//import visitor.TypeVisitor;

public class Not extends Exp {
  public Exp e;
  
  public Not(Exp ae) {
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
