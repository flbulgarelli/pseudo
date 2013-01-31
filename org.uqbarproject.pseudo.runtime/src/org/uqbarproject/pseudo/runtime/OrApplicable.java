package org.uqbarproject.pseudo.runtime;

public class OrApplicable extends AbstractApplicable {

  private final Applicable first;
  private final Applicable second;

  public OrApplicable(Applicable first, Applicable second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public Object apply(Object receptor) throws Throwable {
    return first.applyForBoolean(receptor) || second.applyForBoolean(receptor);
  }

}
