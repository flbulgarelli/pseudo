package org.uqbarproject.pseudo.runtime;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author flbulgarelli
 */
public class MessageSendUnitTest {

  @Test(expected = NoSuchMethodException.class)
  public void failsWhenMethodIsNotFound() throws Throwable {
    new MessageSend("foo").apply(new Cell(10));
  }

  @Test
  public void canSendUnaryMessages() throws Throwable {
    Assert.assertEquals(10, new MessageSend("getValue").apply(new Cell(10)));
  }

  @Test
  public void canSendNAriyMessages() throws Throwable {
    Cell aCell = new Cell(10);
    new MessageSend("setValue", 15).apply(aCell);
    Assert.assertEquals(15, aCell.getValue());
  }
}
