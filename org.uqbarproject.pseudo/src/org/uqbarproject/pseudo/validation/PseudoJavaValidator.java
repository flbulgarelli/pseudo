package org.uqbarproject.pseudo.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.uqbarproject.pseudo.pseudo.Declaration;
import org.uqbarproject.pseudo.pseudo.Let;
import org.uqbarproject.pseudo.pseudo.Method;
import org.uqbarproject.pseudo.pseudo.Parameter;
import org.uqbarproject.pseudo.pseudo.PseudoPackage;
import org.uqbarproject.pseudo.pseudo.Statement;
import org.uqbarproject.pseudo.pseudo.Type;

public class PseudoJavaValidator extends AbstractPseudoJavaValidator {

  /* Severe errors */

  abstract class DuplicateChecker<P, C extends EObject> {
    private Map<String, C> members = new HashMap<String, C>();
    private Set<C> duplicateMembers = new HashSet<C>();

    public void checkDuplicates(P parentObject) {
      checkChilds(parentObject);
      for (C member : duplicateMembers) {
        error("First defined here", member, feature(), INSIGNIFICANT_INDEX);
      }
    }

    protected void updateIndexes(C child, String memberName) {
      if (members.containsKey(memberName)) {
        duplicateMembers.add(members.get(memberName));
        error(duplicationMessage(), child, feature(), INSIGNIFICANT_INDEX);
      } else {
        members.put(memberName, child);
      }
    }

    protected abstract void checkChilds(P parentObject);

    protected abstract EAttribute feature();

    protected abstract String duplicationMessage();
  }

  @Check
  public void checkDuplicateMembers(Type type) {
    new DuplicateChecker<Type, Declaration>() {
      protected void checkChilds(Type parentObject) {
        for (Declaration child : parentObject.getDeclarations()) {
          updateIndexes(child, child.getName());
        }
      }

      protected EAttribute feature() {
        return PseudoPackage.Literals.DECLARATION__NAME;
      }

      protected String duplicationMessage() {
        return "Duplicate member";
      }
    }.checkDuplicates(type);
  }

  @Check
  public void checkDuplicateLocals(Method method) {
    new DuplicateChecker<Method, Let>() {
      protected void checkChilds(Method parentObject) {
        for (Statement statement : parentObject.getStatements()) {
          if (statement instanceof Let) {
            Let child = (Let) statement;
            updateIndexes(child, child.getName());
          }
        }
      }

      protected EAttribute feature() {
        return PseudoPackage.Literals.LET__NAME;
      }

      protected String duplicationMessage() {
        return "Duplicate let";
      }
    }.checkDuplicates(method);
  }

  // TODO rename declaration to member

  @Check
  public void checkDuplicateParameters(Method method) {
    Set<String> parameterNames = new HashSet<String>();
    for(Parameter parameter : method.getParameters()) {
      parameterNames.add(parameter.getName());
    }
    if (parameterNames.size() != method.getParameters().size()) {
      error("Duplicate parameters", method, PseudoPackage.Literals.METHOD__PARAMETERS, INSIGNIFICANT_INDEX);
    }
  }

  /* Conventions */

  @Check
  public void checkTypeIsUppercase(Type type) {
    if (!Character.isUpperCase(type.getName().charAt(0))) {
      warning(
        "Classes should start with an uppercase letter",
        type,
        PseudoPackage.Literals.TYPE__NAME,
        INSIGNIFICANT_INDEX);
    }
  }

  @Check
  public void checkMethodIsLowercase(Method method) {
    if (!Character.isLowerCase(method.getName().charAt(0))) {
      warning(
        "Methods should start with a lower case letter",
        method,
        PseudoPackage.Literals.METHOD__CLASS_METHOD,
        INSIGNIFICANT_INDEX);
    }
  }

  /* Type inference warnings */

}
