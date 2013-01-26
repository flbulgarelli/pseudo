package org.uqbarproject.pseudo.runtime;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

public class MessageSend extends AbstractApplicable {
  private final String selector;
  private final Object[] arguments;

  public MessageSend(String selector, Object... arguments) {
    this.selector = selector;
    this.arguments = arguments;
  }

  // TODO use invokeDynamic
  public Object apply(Object receptor) throws Throwable {
    try {
      return resolveMethod(receptor, arguments.length).invoke(receptor, arguments);
    } catch (java.lang.reflect.InvocationTargetException e) {
      throw e.getCause();
    }
  }

  private Method resolveMethod(Object receptor, int argumentsCount) throws NoSuchMethodException {
    Method[] methods = receptor.getClass().getMethods();
    Collection<Method> similarMethods = new LinkedList<Method>();

    for (Method method : methods) {
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

}
