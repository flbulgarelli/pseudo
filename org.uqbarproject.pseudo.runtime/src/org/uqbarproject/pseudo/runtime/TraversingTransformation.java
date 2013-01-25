package org.uqbarproject.pseudo.runtime;

import java.util.LinkedList;
import java.util.List;

public class TraversingTransformation extends AbstractApplicable {

  private final Applicable mapper;
  private final Applicable filter;
  private final Applicable reducer;

  public TraversingTransformation(Applicable mapper, Applicable filter, Applicable reducer) {
    this.mapper = mapper;
    this.filter = filter;
    this.reducer = reducer;
  }

  public Object eval(Object argument) throws Throwable {
    final List<Object> result = new LinkedList<Object>();
    new Traversing(new AbstractApplicable() {
      public Object apply(Object argument) throws Throwable {
        result.add(mapper.apply(argument));
        return null;
      }
    }, filter).apply(argument);
    return reducer.apply(result);
  }

  @Override
  public Object apply(Object receptor) throws Throwable {
    // TODO Auto-generated method stub
    return null;
  }

}
