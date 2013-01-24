package org.uqbarproject.pseudo.runtime;

public class ConstantFunction extends AbstractApplicable {

	private final Object constant;

	public ConstantFunction(Object constant) {
		this.constant = constant;
	}

	@Override
	public Object apply(Object receptor) throws Throwable {
		return constant;
	}

}
