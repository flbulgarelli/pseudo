package org.uqbarproject.pseudo.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public class IntegralRange implements Iterable<BigInteger> {

  private final BigInteger start, stop;

  public IntegralRange(Object start, Object stop) {
    this.start = toBigInteger(start);
    this.stop = toBigInteger(stop);
  }

  private BigInteger toBigInteger(Object value) {
    if (value instanceof BigInteger)
      return (BigInteger) value;
    else if (value instanceof BigDecimal)
      return ((BigDecimal) value).toBigInteger();
    else if (value instanceof Number)
      return BigInteger.valueOf(((Number) value).longValue());
    else
      throw new IllegalArgumentException(value + "is not a number");
  }

  @Override
  public Iterator<BigInteger> iterator() {
    return new Iterator<BigInteger>() {

      BigInteger current = start;

      @Override
      public boolean hasNext() {
        return current.compareTo(stop) <= 0;
      }

      @Override
      public BigInteger next() {
        BigInteger next = current;
        current = current.add(BigInteger.ONE);
        return next;
      }
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
