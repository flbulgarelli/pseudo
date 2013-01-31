package org.uqbarproject.pseudo.runtime;

import static org.junit.Assert.*;

import java.math.BigDecimal;

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
    assertEquals(BigDecimal.valueOf(15), setAndGet.apply(new Cell(10)));
  }
  
  @Test
  public void orPerformsDisjunction() throws Throwable {
    Applicable is15Or10 = new MessageSend("equals", 15).or(new MessageSend("equals", 10));
    assertEquals(true, is15Or10.apply(10));
    
    Applicable is15Or9 = new MessageSend("equals", 15).or(new MessageSend("equals", 9));
    assertEquals(false, is15Or9.apply(10));
  }
  
}
