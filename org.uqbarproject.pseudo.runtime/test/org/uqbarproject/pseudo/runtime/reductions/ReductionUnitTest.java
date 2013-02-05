package org.uqbarproject.pseudo.runtime.reductions;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;
import org.uqbarproject.pseudo.runtime.IdentityFunction;
import org.uqbarproject.pseudo.runtime.Iterables;
import org.uqbarproject.pseudo.runtime.MessageSend;

/**
 * Tests for functions in the reduction package
 * 
 * @author flbulgarelli
 */
public class ReductionUnitTest {

  @Test
  public void maxReductionCalculatesMax() throws Throwable {
    assertEquals(50, Iterables.reduce(Arrays.asList(10, 50, 3), new MaxReduction(new IdentityFunction())));
  }

  @Test
  public void maxOnReductionCalculatesMaxUsingACriteria() throws Throwable {
    assertEquals(
      "abcdefgh",
      Iterables.reduce(Arrays.asList("foo", "hello", "abcdefgh", "world"), new MaxReduction(new MessageSend("length"))));
  }

  @Test
  public void minReductionCalculatesMin() throws Throwable {
    assertEquals(3, Iterables.reduce(Arrays.asList(10, 50, 3), new MinReduction(new IdentityFunction())));
  }

  @Test
  public void minOnReductionCalculatesMinUsingACriteria() throws Throwable {
    assertEquals(
      "baz",
      Iterables.reduce(Arrays.asList("hello", "abcdeg", "baz"), new MinReduction(new MessageSend("length"))));
  }

  @Test
  public void sumReductionCalculatesSum() throws Throwable {
    assertEquals(BigDecimal.valueOf(63), //
      Iterables.reduce(
        Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(50), BigDecimal.valueOf(3)),
        new SumReduction()));

  }

  @Test
  public void averageReductionCalculatesAverage() throws Throwable {
    assertEquals(
      new BigDecimal("0.6666666667"),
      Iterables.reduce(Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE), new AverageReduction()));
  }

  @Test
  public void first() throws Throwable {
    assertEquals("foo", Iterables.reduce(Arrays.asList("foo", "", "bar"), new FirstReduction()));
  }
  
  @Test
  public void take() throws Throwable {
    assertEquals(Arrays.asList("foo", ""), Iterables.reduce(Arrays.asList("foo", "", "bar"), new TakeReduction(2)));
    assertEquals(Arrays.asList("foo"), Iterables.reduce(Arrays.asList("foo", "", "bar"), new TakeReduction(1)));
    assertEquals(Arrays.asList(), Iterables.reduce(Arrays.asList("foo", "", "bar"), new TakeReduction(0)));
  }

  @Test
  public void any() throws Throwable {
    Reduction anyIsEmpty = new AnyReduction(new MessageSend("isEmpty"));
    assertTrue((Boolean) Iterables.reduce(Arrays.asList("foo", "", "bar"), anyIsEmpty));
    assertFalse((Boolean) Iterables.reduce(Arrays.asList("foo", "baz", "bar"), anyIsEmpty));
  }

  @Test
  public void all() throws Throwable {
    Reduction allAreEmpty = new AllReduction(new MessageSend("isEmpty"));
    assertTrue((Boolean) Iterables.reduce(Arrays.asList("", "", ""), allAreEmpty));
    assertFalse((Boolean) Iterables.reduce(Arrays.asList("", "baz", "bar"), allAreEmpty));
  }

}
