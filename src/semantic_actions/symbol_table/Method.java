package semantic_actions.symbol_table;

import semantic_actions.symbol_table.Symbol;
import semantic_actions.syntax_tree.Type;

import java.util.HashMap;
import java.util.Map;

public class Method{
    String name;
    Type type;
    String father;

    Map<Symbol, Type> params;
    Map<Symbol, Type> local;

    public Method(String name, String father, Type type){
        this.name = name;
        this.father = father;
        this.type = type;

        this.params = new HashMap<Symbol, Type>();
        this.local = new HashMap<Symbol, Type>();
    }

    public boolean addParam(Type type, Symbol symbol){
        if(this.params.get(symbol) == null){
            this.params.put(symbol, type);
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

    public Map<Symbol, Type> getParams(){
        return this.params;
    }
    public Map<Symbol, Type> getVars(){
        return this.local;
    }

    public Type getParams(Symbol symbol){
        return this.params.get(symbol);
    }
    public Type getVars(Symbol symbol){
        return this.local.get(symbol);
    }

    public int getParamsSize(){
        return this.params.size();
    }
    public int getVarsSize(){
        return this.local.size();
    }

    public Type getType(){
        return this.type;
    }

    public String toString(){
        return this.name;
    }

    public boolean removeVariable(Symbol symbol){
        if(this.local.get(symbol) == null){
            return false;
        }
        this.local.remove(symbol);
        return true;
    }

    public boolean removeParam(Symbol symbol){
        if(this.params.get(symbol) == null ){
            return false;
        }
        this.params.remove(symbol);
        return true;
    }

}