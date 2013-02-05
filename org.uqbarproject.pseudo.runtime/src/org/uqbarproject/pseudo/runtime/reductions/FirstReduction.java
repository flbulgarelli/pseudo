package org.uqbarproject.pseudo.runtime.reductions;


public class FirstReduction extends AbstractReduction {
  
  @Override
  public Object reduce(Object lastResult, Object next) throws Throwable {
    return lastResult;
  }

}
