import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class ParaCadaSpec {
	
	Observador observador1 = new Observador();
	Observador observador2 = new Observador();
	Observable observable = new Observable();
	
	@Before
	public void setup() {
		observable.setObservadores(Arrays.asList(observador1, observador2));
	}

	@Test
	public void soporta_iteracion_basica() throws Throwable {
		observable.notificar_a_todos();
		
		assertTrue((Boolean)observador1.notificado());
		assertTrue((Boolean)observador2.notificado());
	}

	@Test
	public void soporta_iteracion_con_filtro() throws Exception {
		fail();
	}

	@Test
	public void la_iteracion_es_una_expresion() throws Throwable {
		assertSame(observador2, observable.notificar_a_todos());
	}

}
