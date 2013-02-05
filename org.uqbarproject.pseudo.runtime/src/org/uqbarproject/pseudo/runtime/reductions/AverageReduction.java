package org.uqbarproject.pseudo.runtime.reductions;

import java.math.BigDecimal;
import java.util.Iterator;

import org.uqbarproject.pseudo.runtime.BigDecimals;

/**
 * @author flbulgarelli
 */
public class AverageReduction extends AbstractReduction {

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    return new AverageMutablePartialResult((BigDecimal) super.initial(iter));
  }

  @Override
  public Object reduceLast(Object lastResult) throws Throwable {
    return ((AverageMutablePartialResult) lastResult).average();
  }

  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    ((AverageMutablePartialResult) lastResult).update(next);
    return lastResult;
  }

  class AverageMutablePartialResult {
    BigDecimal count = BigDecimal.ONE;
    BigDecimal sum;

    public AverageMutablePartialResult(BigDecimal initialSum) {
      this.sum = initialSum;
    }

    BigDecimal average() {
      return BigDecimals.divide(sum, count);
    }

    void update(Object element) throws Throwable {
      sum = sum.add((BigDecimal) element);
      count = count.add(BigDecimal.ONE);
    }
  }

}
