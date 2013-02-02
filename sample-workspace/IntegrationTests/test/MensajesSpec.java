import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

public class MensajesSpec {
  @Test
  public void soporta_delegacion_en_self() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals(receptor.metodoQueDelegaEnSiMismo(), receptor.metodoDelegado());
  }
  
  @Test
  public void soporta_pasar_argumentos() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals(BigDecimal.valueOf(9), receptor.metodoQuePasaParametros(BigDecimal.valueOf(4), BigDecimal.valueOf(5)));
  }

  @Test
  public void soporta_delegacion_en_general() throws Throwable {
    Mensajes receptor = new Mensajes();
    assertEquals("5", receptor.metodoQueDelegaEnOtroObjeto());
  }

  @Test
  public void composicion() throws Throwable {
    assertEquals(BigDecimal.valueOf(4), new Mensajes().metodoQueCompone());
  }

  @Test
  public void encadenamiento() throws Throwable {
    assertEquals(BigDecimal.valueOf(3), new Mensajes().metodoQueEncadena());
  }
  
  @Test
  public void comparacion_es_null_safe() throws Throwable {
    assertFalse((Boolean)new Persona().argentina());
  }
  
  @Test
  public void disyuncion() throws Throwable {
    Persona persona = new Persona() {{
      setEmancipada(true);
      setEdad(BigDecimal.valueOf(15));
    }};
    assertTrue((Boolean)new Mensajes().mayorOEmancipada(persona));
  }
  
  @Test
  public void conjuncion() throws Throwable {
    Persona persona = new Persona() {{
      setNacionalidad("argentina");
      setEdad(BigDecimal.valueOf(30));
    }};
    assertTrue((Boolean)new Mensajes().mayorYArgentina(persona));
  }
  
  @Test
  public void soporta_enviar_mensajes_a_la_clase() throws Throwable {
    assertEquals(BigDecimal.valueOf(4), new Mensajes().metodoQueEnviaMensajeALaClase());
  }
 
  /**Esto es medio discutible. Conceptualmente no es correcto,
   * sin embargo, creo que podría ser aceptable es tanto es "pseudo"codigo
   */
  @Test
  public void soporta_enviar_mensajes_de_clase_a_la_instancia() throws Throwable {
    assertEquals(BigDecimal.valueOf(4), new Mensajes().metodoQueEnviaMensajeDeClaseALaInstancia());

  }
}
