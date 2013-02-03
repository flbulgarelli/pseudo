package org.uqbarproject.pseudo.runtime.reductions;

import java.math.BigDecimal;
import java.util.Iterator;

import org.uqbarproject.pseudo.runtime.Applicable;

/**
 * @author flbulgarelli
 */
public class SumFunction extends AbstractReduction {

  private final Applicable criteria;

  public SumFunction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    return BigDecimal.ZERO;
  }

  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    return ((BigDecimal) lastResult).add((BigDecimal) criteria.apply(next));
  }

}
