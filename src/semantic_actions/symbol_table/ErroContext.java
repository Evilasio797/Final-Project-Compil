package semantic_actions.symbol_table;

public class ErroContext{
    boolean anyErrors;

    public void complain(String msg){
        anyErrors = true;
        System.out.println(msg);
    }
}