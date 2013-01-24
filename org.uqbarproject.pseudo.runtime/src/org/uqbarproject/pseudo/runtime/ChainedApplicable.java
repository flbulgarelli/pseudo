package org.uqbarproject.pseudo.runtime;

public class ChainedApplicable extends AbstractApplicable {

	private final Applicable first;
	private final Applicable second;

	public ChainedApplicable(Applicable first, Applicable second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Object apply(Object argument) throws Throwable {
		first.apply(argument);
		return second.apply(argument);
	}

}
