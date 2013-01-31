import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

public class JavaIntegrationSpec {

  @Test
  public void traduce_snake_a_camel() throws Throwable {
    assertEquals(Arrays.asList(BigDecimal.valueOf(4), BigDecimal.valueOf(9)), new Integraciones().removerTodo());
  }

  @Test
  public void traduce_numeros_a_big_decimal() throws Throwable {
    assertEquals(BigDecimal.ZERO, new Integraciones().largoLista());
  }

}
