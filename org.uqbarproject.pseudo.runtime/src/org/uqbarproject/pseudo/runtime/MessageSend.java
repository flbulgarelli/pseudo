package org.uqbarproject.pseudo.runtime;

import java.lang.reflect.Method;

public class MessageSend extends AbstractApplicable {
  private final String selector;
  private final Object[] arguments;

  public MessageSend(String selector, Object... arguments) {
    this.selector = selector;
    this.arguments = arguments;
  }

  public Object apply(Object receptor) throws Throwable {
    Class<?>[] types = new Class[arguments.length];
    java.util.Arrays.fill(types, Object.class);
    try {
      return resolveMethod(receptor, types).invoke(receptor, arguments);
    } catch (java.lang.reflect.InvocationTargetException e) {
      throw e.getCause();
    }
  }

  private Method resolveMethod(Object receptor, Class<?>[] types) throws NoSuchMethodException {
    // TODO dar sugerencias
    return receptor.getClass().getMethod(selector, types);
  }

}
