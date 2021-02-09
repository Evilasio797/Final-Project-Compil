package translation.visitor;
import semantic_actions.*;
import tanslation.*;



public class VisitIR {
	public Exp visit(And n){
		Exp exp1 = n.exp1.accept(this);
		Exp exp2 = n.exp2.accept(this);
    	BINOP binop = new BINOP(BINOP.PLUS, e1.unEx(), e2.unEx());
        return new Exp(binop);
	}
	public Exp visit(ArrayAssign n){
		Exp e1 = n.e1.accept(this);
        Exp e2 = n.e2.accept(this);

        // TEMP temp = new TEMP(n.id.toString()); // 
        TEMP temp = new TEMP(new Temp()); // 
        MEM mem = new MEM(temp);
        BINOP binop = new BINOP(BINOP.PLUS, mem, e1.unEx());
        MOVE move = new MOVE(binop, e2.unEx());

        return new Exp(new ESEQ(move, null)); // converter pra Exp
	}
	public Exp visit(Assign n) {
		Exp e = n.e.accept(this);
        TEMP temp = new TEMP(new Temp()); //Temp ou TEMP ? 
        MOVE move = new MOVE(temp, e.unEx());

        return new Exp(new ESEQ(move, null));
	}
	public Exp visit(ArrayLookup n) {
		//
	}
	public Exp visit(ArrayLength n) {
		
	}
	public Exp visit(BooleanType n){
		
	}
	public Exp visit(Block n){
		
	}
	public Exp visit(Call n){
		
	}
	public Exp visit(ClassDeclSimple n){
		
	}
	public Exp visit(ClassDeclExtends n){
		
	}
	public Exp visit(False n){
		
	}
	public Exp visit(Formal n){
		
	}
	
	public Exp visit(IntArrayType n){
		
	}
	public Exp visit(IntegerType n){
		
	}
	public Exp visit(IdentifierType n){
		
	}
	public Exp visit(IdentifierExp n){
		
	}
	public Exp visit(If n){
		
	}
	public Exp visit(IntegerLiteral n){
		
	}
	public Exp visit(Identifier n){
		
	}
	public Exp visit(LessThan n){
		
	}
	public Exp visit(MainClass n){
		
	}
	public Exp visit(Minus n){
		
	}
	public Exp visit(MethodDecl n){
		
	}
	public Exp visit(NewArray n){
		
	}
	
	public Exp visit(NewObject n){
		
	}
	public Exp visit(Not n){
		
	}
	public Exp visit(Program n){
		
	}
	public Exp visit(Print n){
		
	}
	public Exp visit(Plus n){
		
	}
	public Exp visit(Times n){
		
	}
	public Exp visit(True n){
		
	}
	public Exp visit(This n){
		
	}
	public Exp visit(VarDecl n){
		
	}
	public Exp visit(While n){
		
	}

}
