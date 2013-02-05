package org.uqbarproject.pseudo.runtime.reductions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TakeReduction extends AbstractReduction {

  private int count;

  public TakeReduction(Object count) {
    this.count = ((Number) count).intValue();
  }

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    return new ArrayList<Object>();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    if (count > 0) {
      ((List<Object>) lastResult).add(next);
      count--;
    }
    return lastResult;
  }

}
