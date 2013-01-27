import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class CuentaTest {

	CuentaBancaria cuentaBancaria = new CuentaBancaria();
	@Test
	public void asignacionBasica() throws Throwable {
		cuentaBancaria.setMonto(BigDecimal.valueOf(5));
		assertEquals(BigDecimal.valueOf(5), cuentaBancaria.getMonto());
	}

	@Test
	public void inicializacionDeAtributos() throws Exception {
		assertEquals(BigDecimal.valueOf(10), cuentaBancaria.getMonto());
	}

	@Test
	public void parametrizacionBasica() throws Throwable {
		cuentaBancaria.depositar(BigDecimal.valueOf(5));
		assertEquals(BigDecimal.valueOf(15), cuentaBancaria.getMonto());
	}

}
