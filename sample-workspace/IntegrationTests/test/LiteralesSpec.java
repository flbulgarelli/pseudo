import static org.junit.Assert.*;

import java.math.BigDecimal;

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
	public void soporta_listas_literales() throws Exception {
		fail();
	}

	@Test
	public void soporta_listas_vacias_literales() throws Exception {
		fail();
	}

	@Test
	public void soporta_conjuntos_literales() throws Exception {
		fail();
	}

	@Test
	public void soporta_conjuntos_vacios_literales() throws Exception {
		fail();
	}

	@Test
	public void soporta_diccionarios_literales() throws Exception {
		fail();
	}
	
	  @Test
	  public void soportaBooleanos() throws Throwable {
	    assertFalse((Boolean)new Literales().devuelve_falso());
	    assertFalse((Boolean)new Literales().devuelve_no());
       assertTrue((Boolean)new Literales().devuelve_verdadero());
	    assertTrue((Boolean)new Literales().devuelve_si());
	  }
	  
	  @Test
	  public void soportaNumerosEnteros() throws Throwable {
	     assertEquals(BigDecimal.valueOf(5), new Literales().devuelve_cinco());
	  } 
	  
	  @Test
	  public void soportaNumerosFlotantes() throws Throwable {
	    assertEquals(new BigDecimal("5.5"), new Literales().devuelve_cinco_y_medio());
	  }
}
