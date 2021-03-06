package translation.frame;
import translation.tree.*;
import translation.temp.*;
import semantic_actions.*;
import java.util.List;

public abstract class Frame implements TempMap {
    public Label name;
    public List<Access> formals;
    public abstract Frame newFrame(Symbol.Symbol name, List<Boolean> formals);
    public abstract Access allocLocal(boolean escape);
    public abstract Temp FP();
    public abstract int wordSize();
    public abstract Expr externalCall(String func, List<Tree.Exp> args);
    public abstract Temp RV();
    public abstract String string(Label label, String value);
    public abstract Label badPtr();
    public abstract Label badSub();
    public abstract String tempMap(Temp.Temp temp);
    public abstract List<Assem.Instr> codegen(List<Tree.Stm> stms);
    public abstract void procEntryExit1(List<Stm> body);
    public abstract void procEntryExit2(List<Assem.Instr> body);
    public abstract void procEntryExit3(List<Assem.Instr> body);
    public abstract Temp[] registers();
    public abstract void spill(List<Assem.Instr> insns, Temp[] spills);
    public abstract String programTail(); //append to end of target code
}