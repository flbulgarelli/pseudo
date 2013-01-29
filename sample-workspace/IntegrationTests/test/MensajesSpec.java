import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

public class MensajesSpec {
  @Test
  public void soporta_delegacion_en_self() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals(receptor.metodo_que_delega_en_si_mismo(), receptor.metodo_delegado());
  }
  
  @Test
  public void soporta_pasar_argumentos() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals(BigDecimal.valueOf(9), receptor.metodo_que_pasa_parametros(BigDecimal.valueOf(4), BigDecimal.valueOf(5)));
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
  
  @Test
  public void soporta_enviar_mensajes_a_la_clase() throws Throwable {
    assertEquals(BigDecimal.valueOf(4), new Mensajes().metodo_que_envia_mensaje_a_la_clase());
  }
 
  /**Esto es medio discutible. Conceptualmente no es correcto,
   * sin embargo, creo que podría ser aceptable es tanto es "pseudo"codigo
   */
  @Test
  public void soporta_enviar_mensajes_de_clase_a_la_instancia() throws Throwable {
    assertEquals(BigDecimal.valueOf(4), new Mensajes().metodo_que_envia_mensaje_de_clase_a_la_instancia());

  }
}
