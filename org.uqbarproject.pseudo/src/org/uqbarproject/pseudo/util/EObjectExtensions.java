package org.uqbarproject.pseudo.util;

import org.eclipse.emf.ecore.EObject;

public class EObjectExtensions {

  public static <T> T parentOfType(EObject eObject, Class<T> type) {
    T parentOfType = parentOfTypeOrNull(eObject, type);
    if (parentOfType == null)
      throw new IllegalArgumentException("Has no parent of type" + type);
    return parentOfType;
  }

  @SuppressWarnings("unchecked")
  public static <T> T parentOfTypeOrNull(EObject eObject, Class<T> type) {
    if (eObject.eContainer() == null)
      return null;

    if (type.isAssignableFrom(eObject.eContainer().getClass()))
      return (T) eObject.eContainer();

    return parentOfTypeOrNull(eObject.eContainer(), type);
  }

  public static Object subclassResponsibility(EObject object, String message) {
    throw new UnsupportedOperationException(object + " should implement " + message);
  }

}
