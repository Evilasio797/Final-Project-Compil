package semantic_actions.symbol_table;

import java.util.HashMap;
import java.util.Map;

public class Symbol{
    private String name;
    private static Map<String, Symbol> dict = new HashMap<String, Symbol>();
    
    private Symbol(String name){
        this.name = name;
    }

    public static Symbol symbol(String key){
        Symbol s = dict.get(key);
        if(s == null){
            s = new Symbol(key);
            dict.put(key, s);
        }

        return s;
    }

    public static Symbol getSymbol(String key){
        return dict.get(key);
    }
}