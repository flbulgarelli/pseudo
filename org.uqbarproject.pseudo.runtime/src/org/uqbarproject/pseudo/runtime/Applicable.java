package org.uqbarproject.pseudo.runtime;

/**
 * Interface for objects that model a computation that may be applied to a
 * single object, and that may return a result, produce and effect, or both.
 * 
 * Examples of such objects are functions, partial functions, messages, an so
 * on.
 * 
 * @author flbulgarelli
 * 
 */
public interface Applicable {

  Object apply(Object argument) throws Throwable;

  boolean applyForBoolean(Object argument) throws Throwable;

  Applicable andThen(Applicable other);

  Applicable compose(Applicable other);

}