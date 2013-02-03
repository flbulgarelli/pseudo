package org.uqbarproject.pseudo.runtime;

import static junit.framework.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author flbulgarelli
 */
public class IterablesUnitTest {

  List<Cell> cells = Arrays.asList(new Cell(100), new Cell(80), new Cell(50));

  @Test
  public void traversingExecutesTheStatementForEachElement() throws Throwable {
    Iterables.each(cells, setTime(10));
    Assert.assertEquals(Arrays.asList(new Cell(10), new Cell(10), new Cell(10)), cells);
  }

  @Test
  public void traversingAnswersTheLastResult() throws Throwable {

    Object result = Iterables.each(cells, new MessageSend("getValue"));

    Assert.assertEquals(BigDecimal.valueOf(50), result);
  }

  private AbstractApplicable setTime(final Object value) {
    return new AbstractApplicable() {
      public Object apply(Object argument) throws Throwable {
        ((Cell) argument).setValue(value);
        return null;
      }
    };
  }

  @Test
  public void canMap() throws Throwable {
    assertEquals(
      Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(50)),
      Iterables.map(Arrays.asList(new Cell(10), new Cell(50)), new MessageSend("getValue")));
  }

}
