package org.uqbarproject.pseudo.runtime.reductions;

import java.math.BigDecimal;

import org.uqbarproject.pseudo.runtime.AbstractApplicable;
import org.uqbarproject.pseudo.runtime.Applicable;
import org.uqbarproject.pseudo.runtime.Iterables;

/**
 * @author flbulgarelli
 */
public class SumFunction extends AbstractApplicable {

  private final Applicable criteria;

  public SumFunction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object apply(Object argument) throws Throwable {
    BigDecimal sum = BigDecimal.ZERO;
    for (Object element : Iterables.toIterable(argument)) {
      sum = sum.add((BigDecimal) criteria.apply(element));
    }
    return sum;
  }

}
