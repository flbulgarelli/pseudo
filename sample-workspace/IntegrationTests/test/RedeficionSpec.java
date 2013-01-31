import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class RedeficionSpec {
  @Test
  public void soporta_redefinir_metodos() throws Throwable {
    assertEquals(BigDecimal.valueOf(6), new TerrenoDeLujo().costoPorMetroCuadrado());
  }

  @Test
  public void soporta_usar_super_sin_argumentos() throws Throwable {
    assertEquals(BigDecimal.valueOf(10), new TerrenoDeLaHostia().costoPorMetroCuadrado());
  }

  @Test
  public void soporta_usar_super_con_argumentos() throws Exception {
    fail();
  }

  @Test
  public void override_es_opcional() throws Throwable {
    assertEquals(BigDecimal.valueOf(2), new TerrenoBerreta().costoPorMetroCuadrado());
  }

  @Test
  public void redefinicion_funciona_con_metodos_de_clase() throws Exception {
    fail();
  }

  @Test
  public void redefinicion_funciona_con_metodos_de_clase_y_super() throws Exception {
    fail();
  }

}
