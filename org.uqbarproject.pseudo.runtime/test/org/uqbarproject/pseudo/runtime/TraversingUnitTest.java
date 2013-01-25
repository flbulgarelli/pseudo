package org.uqbarproject.pseudo.runtime;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author flbulgarelli
 */
public class TraversingUnitTest {

  List<Cell> cells = Arrays.asList(new Cell(100), new Cell(80), new Cell(50));

  @Test
  public void traversingWithtTrueFilterExecutesTheStatementForEachElement() throws Throwable {
    new Traversing(setTime(10), new ConstantFunction(true)).apply(cells);
    Assert.assertEquals(Arrays.asList(new Cell(10), new Cell(10), new Cell(10)), cells);
  }

  @Test
  public void traversingExecutesTheStatementForEachElementThatMatchesCondition() throws Throwable {
    new Traversing(setTime(10), new AbstractApplicable() {
      @Override
      public Object apply(Object argument) throws Throwable {
        return ((Integer) ((Cell) argument).getValue()) > 70;
      }
    }).apply(cells);
    Assert.assertEquals(Arrays.asList(new Cell(10), new Cell(10), new Cell(50)), cells);
  }

  @Test
  public void traversingAnswersTheLastResult() throws Throwable {

    Object result = new Traversing(new MessageSend("getValue"), new ConstantFunction(true)).apply(cells);

    Assert.assertEquals(50, result);
  }

  private AbstractApplicable setTime(final Object value) {
    return new AbstractApplicable() {
      public Object apply(Object argument) throws Throwable {
        ((Cell) argument).setValue(value);
        return null;
      }
    };
  }

}
