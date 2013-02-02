import static org.junit.Assert.*;

import org.junit.Test;

public class ToStringSpec {

  @Test
  public void genera_to_string() throws Exception {
    assertEquals("Perro[nombre=fido, raza=terrier]", new Perro("fido", "terrier").toString());
  }

}
