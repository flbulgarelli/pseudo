package org.uqbarproject.pseudo.runtime;

public class ComposedApplicable extends AbstractApplicable {

  private final Applicable first;
  private final Applicable second;

  public ComposedApplicable(Applicable first, Applicable second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public Object apply(Object receptor) throws Throwable {
    return second.apply(first.apply(receptor));
  }

}
