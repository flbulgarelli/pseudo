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
		observable.notificarATodos();
		
		assertTrue((Boolean)observador1.notificado());
		assertTrue((Boolean)observador2.notificado());
	}
	
	@Test
  public void soporta_acciones_con_argumentos() throws Throwable {
    observable.notificarATodosDe("un evento");
    assertEquals("un evento", observador2.ultimoEvento());
  }

	@Test
	public void la_iteracion_es_una_expresion() throws Throwable {
		assertSame(true, observable.notificarATodos());
	}

}
