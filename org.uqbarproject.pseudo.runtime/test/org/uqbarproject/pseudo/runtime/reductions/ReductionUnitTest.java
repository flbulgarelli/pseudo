package org.uqbarproject.pseudo.runtime.reductions;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.uqbarproject.pseudo.runtime.MessageSend;
import org.uqbarproject.pseudo.runtime.reductions.MaxFunction;
import org.uqbarproject.pseudo.runtime.reductions.MinFunction;
import org.uqbarproject.pseudo.runtime.reductions.SumFunction;

public class ReductionUnitTest {

  @Test
  public void maxReductionCalculatesMax() throws Throwable {
    assertEquals(50, new MaxFunction().apply(Arrays.asList(10, 50, 3)));
  }

  @Test
  public void maxOnReductionCalculatesMaxUsingACriteria() throws Throwable {
    assertEquals(
      "abcdefgh",
      new MaxFunction(new MessageSend("length")).apply(Arrays.asList("foo", "hello", "abcdefgh", "world")));
  }

  @Test
  public void minReductionCalculatesMin() throws Throwable {
    assertEquals(3, new MinFunction().apply(Arrays.asList(10, 50, 3)));
  }

  @Test
  public void minOnReductionCalculatesMinUsingACriteria() throws Throwable {
    assertEquals("baz", new MinFunction(new MessageSend("length")).apply(Arrays.asList("hello", "abcdeg", "baz")));
  }

  @Test
  public void sumReductionCalculatesMax() throws Throwable {
    assertEquals(63, new SumFunction().apply(Arrays.asList(10, 50, 3)));
  }

}
