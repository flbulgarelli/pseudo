package org.uqbarproject.pseudo.runtime.reductions;

import org.uqbarproject.pseudo.runtime.AbstractApplicable;
import org.uqbarproject.pseudo.runtime.Applicable;
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
    // TODO Auto-generated method stub
    return null;
  }

}
