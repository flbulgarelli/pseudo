package org.uqbarproject.pseudo.runtime;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractApplicableUnitTest {

  @Test
  public void composePipelinesMessagesRightToLeft() throws Throwable {
    Applicable toStringOfValue = new MessageSend("toString").compose(new MessageSend("getValue"));
    assertEquals("10", toStringOfValue.apply(new Cell(10)));
  }

  @Test
  public void andThenChainsMessages() throws Throwable {
    Applicable setAndGet = new MessageSend("setValue", 15).andThen(new MessageSend("getValue"));
    assertEquals(15, setAndGet.apply(new Cell(10)));
  }

}
