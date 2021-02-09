package semantic_actions.symbol_table;

import semantic_actions.visitor.*;
import semantic_actions.syntax_tree.*;
import semantic_actions.symbol_table.*;

import java.util.Map;
import java.util.HashMap;

public class ClassContext{
    String name;
    String father;
    Map<Symbol, Type> local;
    Map<Symbol, Method> methods;

    public ClassContext(String name, String father){
        this.name = name;
        this.father = father;
        this.local = new HashMap<Symbol, Type>();
        this.methods = new HashMap<Symbol,Method>();
    }

    public boolean addMethod(Method method, Symbol symbol){
        if(this.methods.get(symbol) == null){
            this.methods.put(symbol, method);
            return true;
        }
        return false;
    }
    public boolean addVariable(Type type, Symbol symbol){
        if(this.local.get(symbol) == null){
            this.local.put(symbol, type);
            return true;
        }
        return false;
    }

    public Map<Symbol, Method> getMethods(){ return this.methods; }
    public Map<Symbol, Type> getVars(){ return this.local; }
    public Method getMethods(Symbol symbol){ return this.methods.get(symbol); }
    public Type getVars(Symbol symbol){ return this.local.get(symbol); }
    public int getMethodSize(){ return this.methods.size(); }
    public int getVarSize(){ return this.local.size(); }
    public String toString(){ return this.name; }
    public String getFather(){ return this.father; }
    
    public boolean removeVar(Symbol symbol){
        if(this.local.get(symbol) != null){
            this.local.remove(symbol);
            return true;
        }
        return false;
    }

    public boolean removeMethod(Symbol symbol){
        if(this.methods.get(symbol) != null){
            this.methods.remove(symbol);
            return true;
        }
        return false;
    }
}