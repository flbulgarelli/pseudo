import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.uqbarproject.pseudo.runtime.GenericException;

public class ExcepcionesSpec {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void soporta_lanzar_mensajes() throws Throwable {
    thrown.expect(GenericException.class);
    thrown.expectMessage("ups");
    new Excepciones().metodoQueLanzaError();
  }

  @Test
  public void soporta_lanzar_excepciones() throws Throwable {
    thrown.expect(GenericException.class);
    thrown.expectMessage("foo");
    new Excepciones().metodoQueLanzaExcepcion();
  }

  @Test
  public void soporta_capturar_excepciones() throws Exception {
    fail();
  }

}
