package org.uqbarproject.pseudo.runtime.reductions;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.uqbarproject.pseudo.runtime.AbstractApplicable;
import org.uqbarproject.pseudo.runtime.Applicable;
import org.uqbarproject.pseudo.runtime.Iterables;

/**
 * @author flbulgarelli
 */
public abstract class MaxMinFunction extends AbstractApplicable {

  private final Applicable criteria;

  public MaxMinFunction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object apply(Object argument) throws Throwable {
    Iterator<?> iter = Iterables.toIterable(argument).iterator();
    if (!iter.hasNext())
      throw new NoSuchElementException("Iterable object " + argument + " is empty");
    Object max = iter.next();

    while (iter.hasNext()) {
      Object next = iter.next();
      if (shouldUpdate(applyCriteria(max), (applyCriteria(next))))
        max = next;
    }
    return max;
  }

  @SuppressWarnings("unchecked")
  private Comparable<Object> applyCriteria(Object max) throws Throwable {
    return (Comparable<Object>) criteria.apply(max);
  }

  protected abstract boolean shouldUpdate(Comparable<Object> current, Comparable<Object> next);
}
