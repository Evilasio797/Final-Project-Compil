package semantic_actions.symbol_table;

import semantic_actions.symbol_table.*;
import semantic_actions.syntax_tree.*;
import semantic_actions.visitor.*;

import java.util.Map;
import java.util.HashMap;

public class MainContext{
    String name;
    Map<Symbol, ClassContext> classes;

    public MainContext(String name){
        this.name = name;
        this.classes = new HashMap<Symbol, ClassContext>();
    }

    public MainContext(){
        this("");
    }

    public boolean addClass(ClassContext newClass, Symbol symbol){
        if(this.classes.get(symbol) == null){
            this.classes.put(symbol, newClass);
            return true;
        }
        return false;
    }

    public Map<Symbol, ClassContext> getClasses(){ return this.classes; }
    public ClassContext getClasses(Symbol symbol){return this.classes.get(symbol); }
    public String toString(){ return this.name; }

    public boolean removeClass(Symbol symbol){
        if(this.classes.get(symbol) == null)
            return false;
        this.classes.remove(symbol);
        return true;
    }
}