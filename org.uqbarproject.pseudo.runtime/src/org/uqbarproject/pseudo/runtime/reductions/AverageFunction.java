package org.uqbarproject.pseudo.runtime.reductions;

import java.math.BigDecimal;

import org.uqbarproject.pseudo.runtime.AbstractApplicable;
import org.uqbarproject.pseudo.runtime.Applicable;
import org.uqbarproject.pseudo.runtime.BigDecimals;
import org.uqbarproject.pseudo.runtime.Iterables;

/**
 * @author flbulgarelli
 */
public class AverageFunction extends AbstractApplicable {

  private final Applicable criteria;

  public AverageFunction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object apply(Object argument) throws Throwable {
    BigDecimal sum = BigDecimal.ZERO;
    BigDecimal count = BigDecimal.ZERO;
    for (Object element : Iterables.toIterable(argument)) {
      sum = sum.add((BigDecimal) criteria.apply(element));
      count = count.add(BigDecimal.ONE);
    }
    return BigDecimals.divide(sum, count);
  }

}
