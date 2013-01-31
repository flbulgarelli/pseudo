import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

/**
 * @author flbulgarelli
 */
public class LiteralesSpec {

  @Test
  public void soporta_rangos_literales() {
    fail();
  }

  @Test
  public void soporta_listas_literales() throws Throwable {
    assertEquals(
      Arrays.<Object> asList(BigDecimal.ONE, new BigDecimal("2"), true, "hola"),
      new Literales().devuelveListaLlena());
  }

  @Test
  public void soporta_listas_vacias_literales() throws Throwable {
    assertEquals(Arrays.asList(), new Literales().devuelveListaVacia());
  }

  @Test
  public void soporta_conjuntos_literales() throws Throwable {
    assertEquals(
      new HashSet<Object>(Arrays.<Object> asList(BigDecimal.ONE, new BigDecimal("2"), true, "hola")),
      new Literales().devuelveConjuntoLleno());
  }

  @Test
  public void soporta_conjuntos_vacios_literales() throws Throwable {
    assertEquals(new HashSet<Object>(Arrays.asList()), new Literales().devuelveConjuntoVacio());
  }

  @Test
  public void soporta_diccionarios_literales() throws Exception {
    fail();
  }

  @Test
  public void soportaBooleanos() throws Throwable {
    assertFalse((Boolean) new Literales().devuelveFalso());
    assertFalse((Boolean) new Literales().devuelveNo());
    assertTrue((Boolean) new Literales().devuelveVerdadero());
    assertTrue((Boolean) new Literales().devuelveSi());
  }

  @Test
  public void soportaNumerosEnteros() throws Throwable {
    assertEquals(BigDecimal.valueOf(5), new Literales().devuelveCinco());
  }

  @Test
  public void soportaNumerosFlotantes() throws Throwable {
    assertEquals(new BigDecimal("5.5"), new Literales().devuelveCincoYMedio());
  }
  
  @Test
  public void soporta_literales_de_clase() throws Exception {
    fail();
  }
  
  @Test
  public void soporta_literales_de_mensajes() throws Exception {
    fail();
  }
}
