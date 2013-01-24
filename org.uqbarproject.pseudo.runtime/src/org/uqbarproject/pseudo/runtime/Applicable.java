package org.uqbarproject.pseudo.runtime;

public interface Applicable {

	Object apply(Object argument) throws Throwable;

	boolean applyForBoolean(Object argument) throws Throwable;

	Applicable andThen(Applicable other);

	Applicable compose(Applicable other);

}