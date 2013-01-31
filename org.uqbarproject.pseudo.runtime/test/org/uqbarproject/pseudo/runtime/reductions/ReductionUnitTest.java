package org.uqbarproject.pseudo.runtime.reductions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;
import org.uqbarproject.pseudo.runtime.IdentityFunction;
import org.uqbarproject.pseudo.runtime.MessageSend;

/**
 * Tests for functions in the reduction package
 * 
 * @author flbulgarelli
 */
public class ReductionUnitTest {

  @Test
  public void maxReductionCalculatesMax() throws Throwable {
    assertEquals(50, new MaxFunction(new IdentityFunction()).apply(Arrays.asList(10, 50, 3)));
  }

  @Test
  public void maxOnReductionCalculatesMaxUsingACriteria() throws Throwable {
    assertEquals(
      "abcdefgh",
      new MaxFunction(new MessageSend("length")).apply(Arrays.asList("foo", "hello", "abcdefgh", "world")));
  }

  @Test
  public void minReductionCalculatesMin() throws Throwable {
    assertEquals(3, new MinFunction(new IdentityFunction()).apply(Arrays.asList(10, 50, 3)));
  }

  @Test
  public void minOnReductionCalculatesMinUsingACriteria() throws Throwable {
    assertEquals("baz", new MinFunction(new MessageSend("length")).apply(Arrays.asList("hello", "abcdeg", "baz")));
  }

  @Test
  public void sumReductionCalculatesSum() throws Throwable {
    assertEquals(BigDecimal.valueOf(63), //
      new SumFunction(new IdentityFunction()).apply( //
        Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(50), BigDecimal.valueOf(3))));
  }

  @Test
  public void averageReductionCalculatesAverage() throws Throwable {
    assertEquals(
      new BigDecimal("0.6666666667"),
      new AverageFunction(new IdentityFunction()).apply(Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE)));
  }

  @Test
  public void find() throws Exception {
    fail();
  }

  @Test
  public void any() throws Exception {
    fail();
  }

  @Test
  public void all() throws Exception {
    fail();
  }

}
