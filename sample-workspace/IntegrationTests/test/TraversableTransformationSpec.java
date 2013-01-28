import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TraversableTransformationSpec {

  private List<Persona> personas;
  {
    personas = Arrays.asList(new Persona() {
      {
        setEdad(BigDecimal.valueOf(15));
        setNombre("pepe");
      }
    }, new Persona() {
      {
        setEdad(BigDecimal.valueOf(23));
        setNombre("maria");
      }
    }, new Persona() {
      {
        setNombre("toto");
        setEdad(BigDecimal.valueOf(44));
      }
    });
  }

  @Test
  public void soporta_map() throws Throwable {
    assertEquals(Arrays.asList("pepe", "maria", "toto"), new Comprensiones().nombres_de_personas(personas));
  }

  @Test
  public void soporta_filter() throws Throwable {
    fail();
  }

  @Test
  public void soporta_filter_con_map() throws Throwable {
    assertEquals(Arrays.asList("pepe", "maria", "toto"), new Comprensiones().nombres_de_personas_mayores(personas));
  }

  @Test
  public void soporta_sumatoria() throws Exception {
    fail();
  }

  @Test
  public void soporta_promedio() throws Exception {
    fail();
  }

  @Test
  public void soporta_maximo() throws Exception {
    fail();
  }

  @Test
  public void soporta_minimo() throws Exception {
    fail();
  }

  @Test
  public void soporta_minimo_segun_criteio() throws Throwable {
    assertEquals(personas.get(0), new Comprensiones().persona_mas_joven(personas));
  }

  @Test
  public void soporta_maximo_segun_criterio() throws Throwable {
    assertEquals(personas.get(2), new Comprensiones().persona_mas_adulta(personas));
  }

  @Test
  public void soporta_buscar() throws Exception {
    fail();
  }

}
