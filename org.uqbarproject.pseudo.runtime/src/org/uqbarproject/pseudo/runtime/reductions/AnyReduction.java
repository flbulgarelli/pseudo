package org.uqbarproject.pseudo.runtime.reductions;

import java.util.Iterator;

import org.uqbarproject.pseudo.runtime.Applicable;

/**
 * @author flbulgarelli
 */
public class AnyReduction extends AbstractReduction {

  private final Applicable criteria;

  public AnyReduction(Applicable criteria) {
    this.criteria = criteria;
  }

  @Override
  public Object initial(Iterator<Object> iter) throws Throwable {
    return false;
  }

  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    return ((Boolean) lastResult) || criteria.applyForBoolean(next);
  }

}
