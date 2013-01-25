package org.uqbarproject.pseudo.runtime;

public class Traversing extends AbstractApplicable {

  private final Applicable action;
  private final Applicable filter;

  public Traversing(Applicable action, Applicable filter) {
    this.action = action;
    this.filter = filter;
  }

  public Object apply(Object generator) throws Throwable {
    Object lastResult = null;
    for (Object element : (Iterable<?>) generator) {
      if (filter.applyForBoolean(element)) {
        lastResult = action.apply(element);
      }
    }
    return lastResult;
  }

}
