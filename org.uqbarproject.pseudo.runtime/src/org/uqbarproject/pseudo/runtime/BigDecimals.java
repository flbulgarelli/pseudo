package org.uqbarproject.pseudo.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigDecimals {

  public static BigDecimal divide(BigDecimal x, BigDecimal y) {
    return x.divide(y, 10, RoundingMode.HALF_UP);
  }

  public static Object toBigDecimal(Object value) {
    if (value instanceof BigDecimal)
      return value;
    else if (value instanceof BigInteger)
      return new BigDecimal((BigInteger) value);
    else if (value instanceof Number)
      return new BigDecimal(value.toString());
    else
      return value;
  }

}
