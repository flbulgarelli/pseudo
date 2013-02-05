package org.uqbarproject.pseudo.runtime.reductions;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * @author flbulgarelli
 */
public class SumReduction extends AbstractReduction {

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    return BigDecimal.ZERO;
  }

  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    return ((BigDecimal) lastResult).add((BigDecimal) next);
  }

}
