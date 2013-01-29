import static org.junit.Assert.*;

import org.junit.Test;

public class ConstructorSpec {

  @Test
  public void soporta_constructores_basicos() throws Throwable {
    assertTrue(Constructores.metodo_que_instancia() instanceof Perro);
  }

  @Test
  public void soporta_constructores_con_inicializadores() throws Throwable {
    Perro perro = (Perro) Constructores.metodo_que_inistancia_e_inicializa();
    assertEquals("terrier", perro.getRaza());
    assertEquals("fido", perro.getNombre());
  }

}
