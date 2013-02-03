package org.uqbarproject.pseudo.runtime.reductions;

import org.uqbarproject.pseudo.runtime.Applicable;

/**
 * @author flbulgarelli
 */
public abstract class MaxMinFunction extends AbstractReduction {

  private final Applicable criteria;

  public MaxMinFunction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    if (shouldUpdate(applyCriteria(lastResult), (applyCriteria(next))))
      return next;
    return lastResult;
  }

  @SuppressWarnings("unchecked")
  private Comparable<Object> applyCriteria(Object max) throws Throwable {
    return (Comparable<Object>) criteria.apply(max);
  }

  protected abstract boolean shouldUpdate(Comparable<Object> current, Comparable<Object> next);
}
