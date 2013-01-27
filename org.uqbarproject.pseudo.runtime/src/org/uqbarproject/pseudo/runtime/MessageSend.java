package org.uqbarproject.pseudo.runtime;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    Collection<Method> similarMethods = new LinkedList<Method>();

    Method method = findMethod(argumentsCount, getCandidateMethods(receptor), similarMethods);

    if (method != null)
      return method;

    throw new NoSuchMethodException(String.format(
      "No such method %s with %s parameters. Similar methods are: %s",
      selector,
      argumentsCount,
      similarMethods));
  }

  private Collection<Method> getCandidateMethods(Object receptor) {
    Collection<Method> methods = new ArrayList<Method>();
    Collections.addAll(methods, receptor.getClass().getMethods());
    if (receptor instanceof Class)
      for (Method method : ((Class<?>) receptor).getMethods())
        if (Modifier.isStatic(((Method) method).getModifiers()))
          methods.add(method);
    return methods;
  }

  private Method findMethod(int argumentsCount, Iterable<Method> methods, Collection<Method> similarMethods) {
    for (Method method : methods) {
      if (method.getName().equals(selector) && method.getParameterTypes().length == argumentsCount)
        return method;

      if (method.getName().toLowerCase().contains(selector.toLowerCase()))
        similarMethods.add(method);
    }
    return null;
  }

}
