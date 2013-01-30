package org.uqbarproject.pseudo.runtime;

public abstract class AbstractApplicable implements Applicable {

  public Applicable andThen(Applicable other) {
    return new ChainedApplicable(this, other);
  }

  public Applicable compose(Applicable other) {
    return new ComposedApplicable(other, this);
  }
  
  public Applicable or(Applicable other) {
    throw new UnsupportedOperationException("unimplemented");
  }
  
  public Applicable and(Applicable other) {
    throw new UnsupportedOperationException("unimplemented");
  }

  @Override
  public boolean applyForBoolean(Object argument) throws Throwable {
    return (Boolean) apply(argument);
  }

}