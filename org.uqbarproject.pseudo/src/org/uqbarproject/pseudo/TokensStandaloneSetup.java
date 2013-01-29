
package org.uqbarproject.pseudo;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TokensStandaloneSetup extends TokensStandaloneSetupGenerated{

	public static void doSetup() {
		new TokensStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

