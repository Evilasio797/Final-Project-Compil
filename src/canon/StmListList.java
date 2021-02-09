package canon;

import translation.temp.*;
import translation.tree.*;

public class StmListList {
  public StmList head;
  public StmListList tail;

  public StmListList(StmList h, StmListList t) {
    head = h;
    tail = t;
  }
}