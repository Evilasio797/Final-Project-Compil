package traducao_intermediario.mips;

import traducao_intermediario.tree.*;
import traducao_intermediario.temp.*;
import traducao_intermediario.frame.*;
public class InReg extends Frame.Access {
    Temp temp;
    InReg(Temp t) {
	temp = t;
    }

    public Tree.Exp exp(Tree.Exp fp) {
        return new Tree.TEMP(temp);
    }

    public String toString() {
        return temp.toString();
    }
}