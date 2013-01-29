package org.uqbarproject.pseudo.runtime.reductions;

import org.uqbarproject.pseudo.runtime.Applicable;

/**
 * Function that computes the min element an iterable source. Throws an
 * exception when source is empty
 * 
 * @author flbulgarelli
 */
public class MinFunction extends MaxMinFunction {

  public MinFunction(Applicable criteria) {
    super(criteria);
  }

  @Override
  protected boolean shouldUpdate(Comparable<Object> current, Comparable<Object> next) {
    return current.compareTo(next) > 0;
  }
}
