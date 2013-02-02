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
    assertEquals(Arrays.asList("pepe", "maria", "toto"), new Comprensiones().nombresDePersonas(personas));
  }

  @Test
  public void soporta_filter() throws Throwable {
    assertEquals(personas.subList(1, 3), new Comprensiones().personasMayores(personas));
  }

  @Test
  public void soporta_filter_con_map() throws Throwable {
    assertEquals(Arrays.asList("pepe", "maria", "toto"), new Comprensiones().nombresDePersonasMayores(personas));
  }

  @Test
  public void soporta_sumatoria() throws Throwable {
    assertEquals(BigDecimal.valueOf(15 + 23 + 44), new Comprensiones().sumaEdades(personas));
  }

  @Test
  public void soporta_promedio() throws Throwable {
    assertEquals(new BigDecimal("27.3333333333"), new Comprensiones().promedioEdades(personas));
  }

  @Test
  public void soporta_maximo() throws Throwable {
    assertEquals(personas.get(2).getEdad(), new Comprensiones().edadMaxima(personas));
  }

  @Test
  public void soporta_minimo() throws Throwable {
    assertEquals(personas.get(0).getEdad(), new Comprensiones().edadMinima(personas));
  }

  @Test
  public void soporta_minimo_segun_criteio() throws Throwable {
    assertEquals(personas.get(0), new Comprensiones().personaMasJoven(personas));
  }

  @Test
  public void soporta_maximo_segun_criterio() throws Throwable {
    assertEquals(personas.get(2), new Comprensiones().personaMasAdulta(personas));
  }

  @Test
  public void soporta_buscar() throws Exception {
    fail();
  }

}
