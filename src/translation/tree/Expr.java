package translation.tree;
//era a Exp do framework, mas como j� tem EXP, adicionei o r
abstract public class Expr {
	abstract public ExpList kids();
	abstract public Expr build(ExpList kids);
}