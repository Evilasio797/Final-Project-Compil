package translation.frame;
import translation.tree.*;

public abstract class Access {
  public abstract String toString();
  public abstract tree.EXP exp(tree.EXP e);
}
