package org.uqbarproject.pseudo.runtime;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

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

  @Test
  public void canSendMessagesForTypedMethods() throws Throwable {
    assertEquals(BigDecimal.valueOf(3), new MessageSend("add", java.math.BigDecimal.ONE).apply(BigDecimal.valueOf(2)));
  }

  @Test
  public void canSendClassMessagesToStaticMethods() throws Throwable {
    Assert.assertEquals(Cell.fromValue(10), new MessageSend("fromValue", 10).apply(Cell.class));
  }
  
  @Test(expected=NoSuchMethodException.class)
  public void failsWhenClassMethodIsNotFound() throws Throwable {
    new MessageSend("setValue", 10).apply(Cell.class);
  }
  
  @Test
  public void canSendClassMessagesToClassObjectMethods() throws Throwable {
    Assert.assertEquals("Cell", new MessageSend("getSimpleName").apply(Cell.class));
  }
}
