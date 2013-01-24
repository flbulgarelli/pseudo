import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ZoologicoTest {

	Zoologico zoologico = new Zoologico();

	@Test
	public void listasLiterales() throws Exception {
		assertEquals(Arrays.asList(), zoologico.getAnimales());
	}

	@Test
	public void filtrado() throws Throwable {
		Animal animalNuevo = new Animal();
		zoologico.setAnimales(Arrays.asList(animalNuevo, new Animal() {{
			setNuevo(false);
		}}));
		assertEquals(Arrays.asList(animalNuevo), zoologico.animalesNuevos());
	}
	
	@Test
	public void elMetodoDevuelveLaUltimaExpresion() throws Throwable {
		assertEquals("Un Zoologico", zoologico.imprimir());
	}


}
