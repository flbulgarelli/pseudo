package org.uqbarproject.pseudo.runtime;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TraversingTransformationUnitTest {

  @Test
  public void canMap() throws Throwable {
    TraversingTransformation mapValues = new TraversingTransformation(
      new MessageSend("getValue"),
      new ConstantFunction(true),
      new IdentityFunction());
    assertEquals(Arrays.asList(10, 50), mapValues.apply(Arrays.asList(new Cell(10), new Cell(50))));
  }

}