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
      new Literales().devuelve_lista_llena());
  }

  @Test
  public void soporta_listas_vacias_literales() throws Throwable {
    assertEquals(Arrays.asList(), new Literales().devuelve_lista_vacia());
  }

  @Test
  public void soporta_conjuntos_literales() throws Throwable {
    assertEquals(
      new HashSet<Object>(Arrays.<Object> asList(BigDecimal.ONE, new BigDecimal("2"), true, "hola")),
      new Literales().devuelve_conjunto_lleno());
  }

  @Test
  public void soporta_conjuntos_vacios_literales() throws Throwable {
    assertEquals(new HashSet<Object>(Arrays.asList()), new Literales().devuelve_conjunto_vacio());
  }

  @Test
  public void soporta_diccionarios_literales() throws Exception {
    fail();
  }

  @Test
  public void soportaBooleanos() throws Throwable {
    assertFalse((Boolean) new Literales().devuelve_falso());
    assertFalse((Boolean) new Literales().devuelve_no());
    assertTrue((Boolean) new Literales().devuelve_verdadero());
    assertTrue((Boolean) new Literales().devuelve_si());
  }

  @Test
  public void soportaNumerosEnteros() throws Throwable {
    assertEquals(BigDecimal.valueOf(5), new Literales().devuelve_cinco());
  }

  @Test
  public void soportaNumerosFlotantes() throws Throwable {
    assertEquals(new BigDecimal("5.5"), new Literales().devuelve_cinco_y_medio());
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
