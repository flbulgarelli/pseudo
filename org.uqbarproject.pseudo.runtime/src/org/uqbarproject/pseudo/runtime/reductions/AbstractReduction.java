package org.uqbarproject.pseudo.runtime.reductions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractReduction implements Reduction {

  @Override
  public Object reduceLast(Object lastResult) throws Throwable {
    return lastResult;
  }

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    if (!iter.hasNext())
      throw new NoSuchElementException("Iterable object is empty");
    return iter.next();
  }

}
