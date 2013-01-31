package org.uqbarproject.pseudo.runtime;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author flbulgarelli
 * */
// TODO rename to Message
public class MessageSend extends AbstractApplicable {
  private final String selector;
  private final Object[] arguments;

  public MessageSend(String selector, Object... arguments) {
    this.selector = selector;
    this.arguments = arguments;
  }

  // TODO use invokeDynamic
  public Object apply(Object receptor) throws Throwable {
    return sendTo(receptor);
  }

  /** Sends this message to the given object */
  private Object sendTo(Object receptor) throws IllegalAccessException, NoSuchMethodException, Throwable {
    try {
      return adaptResult(resolveMethod(receptor, arguments.length).invoke(receptor, arguments));
    } catch (java.lang.reflect.InvocationTargetException e) {
      throw e.getCause();
    }
  }

  private Object adaptResult(Object value) {
    if(value instanceof Number)
      return BigDecimals.toBigDecimal(value);
    return value;
  }

  /** Performs a naiv method lookup */
  private Method resolveMethod(Object receptor, int argumentsCount) throws NoSuchMethodException {
    Collection<Method> similarMethods = new LinkedList<Method>();

    for (Method method : getCandidateMethods(receptor)) {
      if (method.getName().equals(selector) && method.getParameterTypes().length == argumentsCount)
        return method;

      if (method.getName().toLowerCase().contains(selector.toLowerCase()))
        similarMethods.add(method);
    }

    throw new NoSuchMethodException(String.format(
      "No such method %s with %s parameters. Similar methods are: %s",
      selector,
      argumentsCount,
      similarMethods));
  }

  /**
   * Answers a collection of methods that may match the this message for the
   * given receptor
   */
  private Collection<Method> getCandidateMethods(Object receptor) {
    Collection<Method> methods = new ArrayList<Method>();
    Collections.addAll(methods, receptor.getClass().getMethods());
    if (receptor instanceof Class)
      for (Method method : ((Class<?>) receptor).getMethods())
        if (Modifier.isStatic(((Method) method).getModifiers()))
          methods.add(method);
    return methods;
  }
}
