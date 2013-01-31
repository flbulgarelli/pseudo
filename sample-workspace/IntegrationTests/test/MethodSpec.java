import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

public class MethodSpec {

  @Test
  public void soporta_metodos_sin_parametros() throws Exception {
    Metodos.class.getMethod("metodoSinParametros");
  }

  @Test
  public void soporta_metodos_con_parametros() throws Exception {
    Metodos.class.getMethod("metodoCon1Parametro", Object.class);
    Metodos.class.getMethod("metodoCon2Parametros", Object.class, Object.class);
  }
  
  @Test
  public void soporta_referenciar_parametros() throws Throwable {
    assertEquals(10, new Metodos().metodoQueDevuelveUnParametro(10));
  }

  @Test
  public void soporta_self_y_devolucion_explicita() throws Throwable {
    Metodos receptor = new Metodos();
    assertSame(receptor, receptor.metodoQueDevuelveSelfExplicitamente());
  }

  @Test
  public void soporta_self_y_devolucion_implicita() throws Throwable {
    Metodos receptor = new Metodos();
    assertSame(receptor, receptor.metodoQueDevuelveSelfImplicitamente());
  }

  @Test
  public void soporta_declarar_variables_locales() throws Throwable {
    assertEquals(BigDecimal.valueOf(5L), new Metodos().metodoConVariablesLocales());
  }
  
  @Test
  public void soporta_incrementar_variables_locales() throws Throwable {
    assertEquals(BigDecimal.ZERO, new Metodos().metodoQueDecrementaVariablesLocales());
  }
  
  @Test
  public void soporta_decrementar_variables_locales() throws Throwable {
    assertEquals(BigDecimal.ONE, new Metodos().metodoQueIncrementaVariablesLocales());
  }
  
  @Test
  public void soporta_actualizar_variables_locales() throws Throwable {
    assertEquals(new BigDecimal("6"), new Metodos().metodoQueActualizaVariablesLocales());
  }

  @Test
  public void soporta_when() throws Throwable {
    Metodos receptor = new Metodos();
    assertEquals("ok", receptor.metodoConWhenVerdadero());
    assertEquals("error", receptor.metodoConWhenFalso());
  }
  
  @Test
  public void soporta_when_sobre_variables() throws Throwable {
    Metodos receptor = new Metodos();
    assertEquals("ok", receptor.metodoConWhenConVariable());
  }

  @Test
  public void soporta_when_con_multiples_condiciones() throws Throwable {
    assertEquals("ok", new Metodos().metodoConWhenMultiple());
  }

  @Test
  public void soporta_when_sin_default() throws Throwable {
    assertEquals(null, new Metodos().metodoSinDefault());
  }

  @Test
  public void soporta_when_que_compila_a_sentencia() throws Throwable {
    assertEquals(BigDecimal.valueOf(5), new Metodos().metodoConSentenciaWhen());
  }
  
  @Test
  public void soporta_declarar_metodos_de_clase() throws Throwable {
    assertEquals(BigDecimal.valueOf(5), Metodos.metodoQueEsDeClase());
  }
  
  @Test
  public void soporta_uso_de_self_en_metodos_de_clase() throws Throwable {
    fail();
  }
}
