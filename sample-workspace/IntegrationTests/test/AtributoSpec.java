import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

public class AtributoSpec {

  @Test
  public void el_valor_default_de_un_atributo_es_null() throws Exception {
    assertNull(new Attributes().attrWithoutInitializer());
  }

  @Test
  public void el_valor_inicial_de_un_atributo_es_su_inicializador() throws Exception {
    assertEquals(BigDecimal.valueOf(10), new Attributes().attrWithInitializer());
  }

  @Test
  public void el_tipo_del_atributo_es_object() throws Exception {
    assertEquals(Object.class, Attributes.class.getDeclaredField("atributoConInicializador").getType());
  }
  
  @Test
  public void todo_atributo_tiene_accesores_java_beans() throws Exception {
    Attributes.class.getMethod("getAtributoConInicializador");
  }
  
  @Test
  public void soporta_incrementar_atributos() throws Throwable {
    assertEquals(BigDecimal.valueOf(11), new Attributes().metodoQueIncrementaAtributos());
  }
  
  @Test
  public void soporta_acumular_atributos() throws Throwable {
    assertEquals(BigDecimal.valueOf(20), new Attributes().metodoQueAcumulaAtributos());
  }
  
  @Test
  public void soporta_decrementar_atributos() throws Throwable {
    assertEquals(BigDecimal.valueOf(9), new Attributes().metodoQueDecrementaAtributos());
  }
  
  @Test
  public void soporta_actualizar_atributos() throws Throwable {
    assertEquals(new BigDecimal("6"), new Attributes().metodoQueActualizaAtributos());
  }
}
