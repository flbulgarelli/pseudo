import static org.junit.Assert.*;

import org.junit.Test;

public class ClaseSpec {

  @Test
  public void puedeDeclararClaseVacia() throws Exception {
    new ClaseVacia();
  }

  @Test
  public void puedeDeclararClaseConTagsExtendidos() throws Exception {
    new ClaseConTagsExtendidos();
  }

  @Test
  public void puedeDeclararClaseConHerencia() throws Exception {
    new ClaseConPadre();
    assertEquals(ClaseVacia.class, ClaseConPadre.class.getSuperclass());
  }

  @Test
  public void puedeDeclararClaseConAtributosYMetodos() throws Exception {
    new ClaseSimpleConAtributosYMetodos();
    ClaseSimpleConAtributosYMetodos.class.getDeclaredMethod("un_metodo");
    ClaseSimpleConAtributosYMetodos.class.getDeclaredField("un_atributo");
  }

}