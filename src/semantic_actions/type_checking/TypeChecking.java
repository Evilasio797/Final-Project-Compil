package semantic_actions.syntax_tree;

import javax.sound.sampled.EnumControl.Type;

import jdk.nashorn.internal.codegen.types.BooleanType;
import semantic_actions.visitor.*;
import semantic_actions.symbol_table.*;
//import visitor.TypeVisitor;

public class TypeChecking extends TypeVisitor {
	private MainContext programTable;
	private ClassContext currClass;
	private Method currMethod;
	private ErroContext erroContext;

	public TypeChecking(MainContext programTable) {
		this.programTable = programTable;
		erroContext = new ErroContext();
	}

	public Type visit(And n) {
		if (!(n.e1.accept(this) instanceof BooleanType)) {
			erroContext.complain("Lado esquerdo do operador '&&' deve ser do tipo boolean.");
		}

		if (!(n.e2.accept(this) instanceof BooleanType)) {
			erroContext.complain("Lado direito do operador '&& deve ser do tipo boolean.");
		}

		return new BooleanType();
	}

	public Type visit(ArrayAssign n) {
		Type type = null;

		if (this.currMethod != null) {
			if (this.currMethod.getVars(Symbol.symbol(n.i.toString()) != null)) {
				type = this.currMethod.getVars(Symbol.symbol(n.i.toString()));
			}
			if (this.currMethod.getParams(Symbol.symbol(n.i.toString)) != null) {
				type = this.currMethod.getParams(Symbol.symbol(n.i.toString()));
			}
		}
		if ((this.currClass != null) && (this.currClass.getVars(Symbol.symbol(n.i.toString())) != null)) {
			type = this.currClass.getVars(Symbol.symbol(n.i.toString()));
		}
		if (this.programTable.getClasses(Symbol.symbol(n.i.toString())) != null) {
			type = new IdentifierType(n.toString());
		}

		if (type == null) {
			erroContext.complain("Array não declarado");
		}

		if (!(type instanceof IntegerType)) {
			erroContext.complain("Array precisa ser inteiro");
		}

		if (!(n.e1.accept(this) instanceof IntegerType)) {
			erro.complain("A chave do array precisa ser inteiro");
		}

		if (!(n.e2.accept(this) instanceof IntegerType)) {
			erro.complain("O lado direito da atribuição precisa ser inteiro");
		}

		return null;
	}

	public Type visit(Assign n) {
		Type type = null;
		Type exp = n.e.accept(this);

		if(this.currMethod != null) {
			if(this.currMethod.getVars(Symbol.symbol(n.i.toString())) != null) {
				type = this.currMethod.getVars(Symbol.symbol(n.i.toString()));
				if(exp == null || !type.toString().equals(exp.toString())) {
					erroContext.complain("Os tipos do Assign não são compatíveis");
				}
			}
			if(this.currMethod.getParams(Symbol.symbol(n.i.toString())) != null) {
				type = this.currMethod.getParams(Symbol.symbol(n.i.toString()));
				if(exp == null || !type.toString().equals(exp.toString)) {
					erroContext.complain("Os tipos do Assign não são compatíveis");
				}
			}
		}

		if((this.currClass != null) && (this.currClass.getVars(Symbol.symbol(n.i.toString())) != null)) {
			type = this.currClass.getVars(Symbol.symbol(n.i.toString()));
			if((exp == null) || (!type.toString().equals(exp.toString()))) {
				erroContext.complain("Os tipos do Assign não são compatíveis");
			}
		}

		if((this.currClass != null) && (this.currClass.getClasses(Symbol.symbol(n.i.toString())) != null)) {
			type = this.currClass.getClasses(Symbol.symbol(n.i.toString()));
			if((exp == null) || (!type.toString().equals(exp.toString()))) {
				erroContext.complain("Os tipos do Assign não são compatíveis");
			}
		}

		return null;
	}

	public Type visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);

		return null;
	}

	public Type visit(ArrayLength n) {
		n.e.accept(this);

		return null;
	}

	public Type visit(BooleanType n) {
		return new BooleanType();
	}

	public Type visit(Block n) {
		for (int i = 0; i < n.s1.size(); i++) {
			n.s1.elementAt(i).accept(this);
		}

		return null;
	}

	public Type visit(Call n) {
		Type exp = n.e.accept(this);
        IdentifierType exp1 = (IdentifierType) exp;
        ClassContext classeExp = programTable.getClasses(Symbol.symbol(exp1.toString()));
		Method metodoId = classeExp.getMethods(Symbol.symbol(n.i.toString()));
        Set chavesParam = metodoId.getParams().entrySet();
        Symbol s;
        Map.Entry<Symbol, Type> me;
        Iterator it = chavesParam.iterator();
		
        if(exp == null ||  !(exp instanceof IdentifierType) ) {
            erroContext.complain ("Classe do objeto é inexistente.");
        }
        if(metodoId == null) {
            erroContext.complain ("Método não encontrado.");
        }
        if(n.el.size() != metodoId.getParamsSize()) {
            erroContext.complain ("Quantidade de parametros inconsistente.");
        }

        for(int i = 0; i < n.el.size(); i++) {
            Type tipoChamado = n.el.elementAt(i).accept(this);
            me = (Map.Entry<Symbol, Type>) it.next();
            s = (Symbol) me.getKey();
            Type tipoParam = metodoId.getParams(s);

            if(tipoChamado instanceof IdentifierType) {
                IdentifierType tipo1 = (IdentifierType) tipoChamado;
                ClassContext classeChamada = programTable.getClasses(Symbol.symbol(tipo1.toString()));
                if(tipoParam.toString().equals("IdentifierType")) {
                    if(classeChamada == null || !classeChamada.toString().equals( ((IdentifierType) tipoParam).toString())) {
                        erroContext.complain ("Tipo do argumento passado:" + classeChamada.toString() + "não compatível com o tipo esperado:" + tipoParam.toString());
                    }
                }
                else {
                    erroContext.complain ("Tipo do argumento passado:" + classeChamada.toString() + "não compatível com o tipo esperado:" + tipoParam.toString());
                }
            }
            else if (tipoChamado == null || !tipoChamado.toString().equals(tipoParam.toString()) ) {
                erroContext.complain ("Tipo do argumento passado:" + tipoChamado.toString() + "não compatível com o tipo esperado:" + tipoParam.toString() );
            }
        }

        return metodoId.getType();
	}

	public Type visit(ClassDeclSimple n) {
		return null;
	}

	public Type visit(ClassDeclExtends n) {
		n.i.accept(this);
		currClass = programTable.getClasses(Symbol.symbol(n.i.toString()));
		n.j.accept(this);
		currClass = null;

		for(int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}

		for(int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}

		return null;
	}

	public Type visit(False n) {
		return null;
	}

	public Type visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);

		return null;
	}

	public Type visit(IntArrayType n) {
		return new IntArrayType();
	}

	public Type visit(IntegerType n) {
		return new IntegerType();
	}

	public Type visit(IdentifierType n) {
		return n;
	}

	public Type visit(IdentifierExp n) {
		Type type = null;
		if (this.currMethod != null) {
			if (this.currMethod.getVars(Symbol.symbol(n.toString()) != null)) {
				type = this.currMethod.getVars(Symbol.symbol(n.toString()));
			}
			if (this.currMethod.getParams(Symbol.symbol(n.toString)) != null) {
				type = this.currMethod.getParams(Symbol.symbol(n.toString()));
			}
		}
		if ((this.currClass != null) && (this.currClass.getVars(Symbol.symbol(n.toString())) != null)) {
			type = this.currClass.getVars(Symbol.symbol(n.toString()));
		}
		if (this.programTable.getClasses(Symbol.symbol(n.toString())) != null) {
			type = new IdentifierType(n.toString());
		}

		if (type == null) {
			erroContext.complain("Identificador não encontrado");
		}

		return type;
	}

	public Type visit(If n) {
		if (!(n.e.accept(this) instanceof BooleanType)) {
			erroContext.complain("A condição do if deve ser do tipo boolean.");
		}

		n.s1.accept(this);
		n.s2.accept(this);

		return null;
	}

	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(Identifier n) {
		if (this.currMethod != null) {
			if (this.currMethod.getVars(Symbol.symbol(n.toString()) != null)) {
				return this.currMethod.getVars(Symbol.symbol(n.toString()));
			}
			if (this.currMethod.getParams(Symbol.symbol(n.toString)) != null) {
				return this.currMethod.getParams(Symbol.symbol(n.toString()));
			}
		}
		if ((this.currClass != null) && (this.currClass.getVars(Symbol.symbol(n.toString())) != null)) {
			return this.currClass.getVars(Symbol.symbol(n.toString()));
		}
		if (this.programTable.getClasses(Symbol.symbol(n.toString())) != null) {
			return new IdentifierType(n.toString());
		}

		return null;
	}

	public Type visit(LessThan n) {
		if (!(n.e1.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado esquerdo do operador '<' deve ser do tipo IntegerType.");
		}
		if (!(n.e2.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado direito do operador '<' deve ser do tipo IntegerType.");
		}

		return new BooleanType();
	}

	public Type visit(MainClass n) {
		n.i1.accept(this);
		this.currClass = programTable.getClasses(Symbol.symbol(n.i1.toString()));
		n.e2.accept(this);
		this.currMethod = programTable.getMethods(Symbol.symbol(n.i2.toString()));
		n.s.accept(this);

		return null;
	}

	public Type visit(Minus n) {
		if (!(n.e1.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado esquerdo do operador '-' deve ser do tipo IntegerType.");
		}
		if (!(n.e2.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado direito do operador '-' deve ser do tipo IntegerType.");
		}

		return new IntegerType();
	}

	public Type visit(MethodDecl n) {
		return null;
	}

	public Type visit(NewArray n) {
		n.e.accept(this);

		return null;
	}

	public Type visit(NewObject n) {
		currClass = programTable.getClasses(Symbol.symbol(n.i.toString()));
		if(currClass == null) {
			erroContext.complain("Classe usada no objeto não existe");
		}

		return new IdentifierType(currClass.toString());
	}

	public Type visit(Not n) {
		if (!(n.e.accept(this) instanceof BooleanType)) {
			erroContext.complain("Lado direito do operador deve ser do tipo Boolean");
		}

		return new BooleanType();
	}

	public Type visit(Program n) {
		n.m.accept(this);

		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}

		return null;
	}

	public Type visit(Print n) {
		n.e.accept(this);

		return null;
	}

	public Type visit(Plus n) {
		if (!(n.e1.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado esquerdo do operador '+' deve ser do tipo IntegerType.");
		}
		if (!(n.e2.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado direito do operador '+' deve ser do tipo IntegerType.");
		}

		return new IntegerType();
	}

	public Type visit(Times n);

	{
		if (!(n.e1.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado esquerdo do operador '*' deve ser do tipo IntegerType.");
		}
		if (!(n.e2.accept(this) instanceof IntegerType)) {
			erroContext.complain("O lado direito do operador '*' deve ser do tipo IntegerType.");
		}

		return new IntegerType();
	}

	public Type visit(True n) {
		return BooleanExpression();
	}

	public Type visit(This n) {
		if (this.currClass == null) {
			erroContext.complain("Classe não encontrada");
		}

		return new IdentifierType(this.currClass.toString());
	}

	public Type visit(VarDecl n) {
		return null;
	}

	public Type visit(While n) {
		if (!(n.e.accept(this) instanceof BooleanType)) {
			erroContext.complain("A condição dentro do while deve ser to tipo BooleanType.");
		}

		n.s.accept(this);

		return null;
	}

}
