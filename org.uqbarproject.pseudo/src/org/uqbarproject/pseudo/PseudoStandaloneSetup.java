
package org.uqbarproject.pseudo;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class PseudoStandaloneSetup extends PseudoStandaloneSetupGenerated{

	public static void doSetup() {
		new PseudoStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

