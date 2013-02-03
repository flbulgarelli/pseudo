package org.uqbarproject.pseudo.runtime;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.uqbarproject.pseudo.runtime.reductions.Reduction;

/**
 * Eager transformations on Iterable-like object
 * 
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
  @SuppressWarnings("unchecked")
  public static Iterable<Object> toIterable(Object source) {
    if (source instanceof Iterable)
      return (Iterable<Object>) source;
    if (source.getClass().isArray())
      return Arrays.asList((Object[]) source);
    throw new IllegalArgumentException("generator must be iterable");
  }

  /**eager map*/
  public static Object map(Object generator, Applicable mapping) throws Throwable {
    List<Object> results = new LinkedList<Object>();
    for (Object element : Iterables.toIterable(generator))
      results.add(mapping.apply(element));
    return results;
  }

  /**eager filter*/
  public static Object filter(Object generator, Applicable criteria) throws Throwable {
    List<Object> results = new LinkedList<Object>();
    for (Object element : Iterables.toIterable(generator))
      if (criteria.applyForBoolean(element))
        results.add(element);
    return results;
  }

  /**eager for each*/
  public static Object each(Object generator, Applicable action) throws Throwable {
    Object lastResult = null;
    for (Object element : Iterables.toIterable(generator))
      lastResult = action.apply(element);
    return lastResult;
  }
  
  /**eager for each*/
  public static Object reduce(Object generator, Reduction reduction) throws Throwable {
    Iterator<Object> iter = Iterables.toIterable(generator).iterator();
    Object lastResult = reduction.initial(iter);
    while(iter.hasNext()) 
      lastResult = reduction.reduce(lastResult, iter.next());
    return reduction.reduceLast(lastResult);
  }

}
