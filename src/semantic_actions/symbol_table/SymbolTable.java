package semantic_actions.symbol_table;

import semantic_actions.visitor.*;
import semantic_actions.syntax_tree.*;
import semantic_actions.symbol_table.*;

public class SymbolTable implements Visitor{
    private Method method;
    private MainContext mainContext;
    private ClassContext myClass;
    private ErroContext error;

    public SymbolTable(){
        this.method = null;
        this.mainContext = new MainContext();
        this.error = new ErroContext();
        this.myClass = null;
    }

    public MainContext getTable(){ return this.mainContext; }

    @Override
    public void visit(MainClass n){
        n.i1.accept(this);

        ClassContext classAux = new ClassContext(n.i1.toString(), null);

        if(!mainContext.addClass(classAux, Symbol.symbol(n.i1.toString())))
            error.complain("Class " +n.i1.toString() +" already defined!");
        else
            myClass = classAux;

        Method methodAux = new Method("main", null, null);

        if(!myClass.addMethod(methodAux, Symbol.symbol("main")))
            error.complain("Method main in class " +n.i1.toString() +" already defined!");
        else{
            method = methodAux;
            method.addParam(null, Symbol.symbol(n.i2.toString() ));
        }

        n.i2.accept(this);
        n.s.accept(this);
    }

    @Override
    public void visit(And n){
        n.e1.accept(this);
        n.e2.accept(this);
    }

	@Override
    public void visit(ArrayAssign n){
        n.e1.accept(this);
        n.e2.accept(this);
        n.i.accept(this);
    }

	@Override
    public void visit(Assign n){
        n.e.accept(this);
        n.i.accept(this);
    }

	@Override
    public void visit(ArrayLookup n){
        n.e1.accept(this);
        n.e2.accept(this);
    }

	@Override
    public void visit(ArrayLength n){n.e.accept(this);}
	@Override
    public void visit(BooleanType n){}
	@Override
    public void visit(Block n){}

	@Override
    public void visit(Call n){
        n.e.accept(this);
        n.i.accept(this);
    }
	@Override
    public void visit(ClassDeclSimple n){}

	@Override
    public void visit(ClassDeclExtends n){
        n.i.accept(this);
        n.j.accept(this);

        String iString = n.i.toString();

        ClassContext classAux = new ClassContext(iString, n.j.toString());

        if(!mainContext.addClass(classAux, Symbol.symbol(iString))){
            error.complain("Class " +iString +" already defined!");
        }else{
            myClass = classAux;
            this.method = null;
        }

        for(int i=0; i<n.vl.size(); i++){
            n.vl.elementAt(i).accept(this);
        }

        for(int i=0; i<n.ml.size(); i++){
            n.ml.elementAt(i).accept(this);
        }
    }
	@Override
    public void visit(Formal n){
        n.t.accept(this);
        n.i.accept(this);

        if(!method.addParam(n.t, Symbol.symbol(n.i.toString()))){
            error.complain("Param " +n.i.toString() +" is defined in method " +method.toString() +"!");
        }
    }
	@Override
    public void visit(False n){}
	@Override
    public void visit(IntArrayType n){}
	@Override
    public void visit(IntegerType n){}
	@Override
    public void visit(IdentifierType n){}
	@Override
    public void visit(IdentifierExp n){}

	@Override
    public void visit(If n){
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

	@Override
    public void visit(IntegerLiteral n){}
	@Override
    public void visit(Identifier n){}
	@Override
    public void visit(LessThan n){
        n.e1.accept(this);
        n.e2.accept(this);
    }
	@Override
    public void visit(Minus n){
        n.e1.accept(this);
        n.e2.accept(this);
    }
	@Override
    public void visit(MethodDecl n){}
	@Override
    public void visit(NewArray n){}
	@Override
    public void visit(NewObject n){}
	@Override
    public void visit(Not n){n.e.accept(this);}
	@Override
    public void visit(Program n){
        n.m.accept(this);

        for(int i=0; i<n.cl.size(); i++){
            n.cl.elementAt(i).accept(this);
        }
    }
	@Override
    public void visit(Print n){}
	@Override
    public void visit(Plus n){
        n.e1.accept(this);
        n.e2.accept(this);
    }
	@Override
    public void visit(Times n){
        n.e1.accept(this);
        n.e2.accept(this);
    }
	@Override
    public void visit(True n){}
	@Override
    public void visit(This n){}
	@Override
    public void visit(VarDecl n){}
	@Override
    public void visit(While n){
        n.e.accept(this);
        n.s.accept(this);
    }

}