import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class MensajesSpec {
  @Test
  public void soporta_delegacion_en_self() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals(receptor.metodo_que_delega_en_si_mismo(), receptor.metodo_delegado());
  }
  
  @Test
  public void soporta_pasar_argumentos() throws Exception {
    fail();
  }

  @Test
  public void soporta_delegacion_en_general() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals("5", receptor.metodo_que_delega_en_otro_objeto());
  }

  @Test
  public void composicion() throws Exception {
    fail();
  }

  @Test
  public void encadenamiento() throws Exception {
    fail();
  }
}
