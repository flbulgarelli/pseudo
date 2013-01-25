import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

public class AtributoSpec {

  @Test
  public void el_valor_default_de_un_atributo_es_null() throws Exception {
    assertNull(new Atributos().atributo_sin_inicializador());
  }

  @Test
  public void el_valor_inicial_de_un_atributo_es_su_inicializador() throws Exception {
    assertEquals(BigDecimal.valueOf(10), new Atributos().atributo_con_inicializador());
  }

  @Test
  public void el_tipo_del_atributo_es_object() throws Exception {
    assertEquals(Object.class, Atributos.class.getDeclaredField("atributo_con_inicializador").getType());
  }
  
  @Test
  public void todo_atributo_tiene_accesores() throws Exception {
    Atributos.class.getMethod("fijar_atributo_con_inicializador", Map.class);
  }

  @Test
  public void todo_atributo_tiene_accesores_java_beans() throws Exception {
    Atributos.class.getMethod("getAtributoConInicializador");
    Atributos.class.getMethod("setAtributoConInicializador", Object.class);
  }

  @Test
  public void todo_atributo_tiene_acumuladores() throws Exception {
    Atributos.class.getMethod("incrementar_atributo_con_inicializador", Object.class);
    Atributos.class.getMethod("decrementar_atributo_con_inicializador", Object.class);
  }
}
