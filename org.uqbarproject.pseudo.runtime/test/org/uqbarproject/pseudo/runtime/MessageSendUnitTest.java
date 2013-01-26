package org.uqbarproject.pseudo.runtime;

import static org.junit.Assert.*;

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
}
