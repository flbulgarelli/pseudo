package org.uqbarproject.pseudo.runtime;

public class IdentityFunction extends AbstractApplicable {
  @Override
  public Object apply(Object receptor) throws Throwable {
    return receptor;
  }
}
