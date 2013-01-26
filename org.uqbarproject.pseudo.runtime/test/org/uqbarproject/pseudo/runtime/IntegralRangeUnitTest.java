package org.uqbarproject.pseudo.runtime;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class IntegralRangeUnitTest {

  @Test
  public void isAnEmptyRangeWhenStartAndStopAreTheSameValue() {
    assertInRange(Arrays.asList(bi(10)), new IntegralRange(10L, 10L));
  }

  @Test
  public void isANonEmptyRangeWhenStartIsGreaterThanStop() throws Exception {
    assertInRange(Arrays.asList(bi(0), bi(1), bi(2), bi(3)), new IntegralRange(0L, 3L));
  }

  @Test
  public void isAnEmptyListWhenStartIsLessThanStop() {
    assertInRange(Arrays.<BigInteger>asList(), new IntegralRange(10L, 8L));
  }

  private void assertInRange(Collection<BigInteger> elements, IntegralRange range) {
    assertEquals(elements, toList(range));
  }

  private BigInteger bi(long value) {
    return BigInteger.valueOf(value);
  }

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> list = new LinkedList<T>();
    for (T element : iterable)
      list.add(element);
    return list;
  }

}
