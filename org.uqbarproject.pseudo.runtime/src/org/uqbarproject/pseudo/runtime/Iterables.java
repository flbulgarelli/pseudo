package org.uqbarproject.pseudo.runtime;

import java.util.Arrays;

/**
 * @author flbulgarelli
 */
public class Iterables {

  /**
   * Converts a source into an iterable.
   * 
   * <ul>
   * <li>When the source is iterable, returns it</li>
   * <li>When the source is an array, wraps it into a List</li>
   * <li>Otherwise, fails with an exception</li>
   * </ul>
   */
  public static Iterable<?> toIterable(Object source) {
    if (source instanceof Iterable)
      return (Iterable<?>) source;
    if (source.getClass().isArray())
      return Arrays.asList((Object[]) source);
    throw new IllegalArgumentException("generator must be iterable");
  }

}
