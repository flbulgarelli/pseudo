package org.uqbarproject.pseudo.runtime.reductions;

import java.util.Iterator;

public interface Reduction {

  Object initial(Iterator<Object> iter) throws Throwable;

  Object reduce(Object lastResult, Object next) throws Throwable;

  Object reduceLast(Object lastResult) throws Throwable;

}
