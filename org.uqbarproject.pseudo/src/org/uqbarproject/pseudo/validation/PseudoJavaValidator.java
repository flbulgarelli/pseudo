package org.uqbarproject.pseudo.validation;

import static org.uqbarproject.pseudo.pseudo.PseudoPackage.Literals.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.validation.Check;
import org.uqbarproject.pseudo.pseudo.Attribute;
import org.uqbarproject.pseudo.pseudo.CollectionLiteralExpression;
import org.uqbarproject.pseudo.pseudo.ConstructionExpression;
import org.uqbarproject.pseudo.pseudo.DecrementExpression;
import org.uqbarproject.pseudo.pseudo.Expression;
import org.uqbarproject.pseudo.pseudo.FalseExpression;
import org.uqbarproject.pseudo.pseudo.IdExpression;
import org.uqbarproject.pseudo.pseudo.IncrementExpression;
import org.uqbarproject.pseudo.pseudo.LocalVariable;
import org.uqbarproject.pseudo.pseudo.Member;
import org.uqbarproject.pseudo.pseudo.Method;
import org.uqbarproject.pseudo.pseudo.Parameter;
import org.uqbarproject.pseudo.pseudo.Statement;
import org.uqbarproject.pseudo.pseudo.StringExpression;
import org.uqbarproject.pseudo.pseudo.TrueExpression;
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
    new DuplicateChecker<Type, Member>() {
      protected void checkChilds(Type parentObject) {
        for (Member child : parentObject.getMembers()) {
          updateIndexes(child, child.getName());
        }
      }

      protected EAttribute feature() {
        return MEMBER__NAME;
      }

      protected String duplicationMessage() {
        return "Duplicate member";
      }
    }.checkDuplicates(type);
  }

  @Check
  public void checkDuplicateLocals(Method method) {
    new DuplicateChecker<Method, LocalVariable>() {
      protected void checkChilds(Method parentObject) {
        for (Statement statement : parentObject.getStatements()) {
          if (statement instanceof LocalVariable) {
            LocalVariable child = (LocalVariable) statement;
            updateIndexes(child, child.getName());
          }
        }
      }

      protected EAttribute feature() {
        return LOCAL_VARIABLE__NAME;
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
    for (Parameter parameter : method.getParameters()) {
      parameterNames.add(parameter.getName());
    }
    if (parameterNames.size() != method.getParameters().size()) {
      error("Duplicate parameters", method, METHOD__PARAMETERS, INSIGNIFICANT_INDEX);
    }
  }

  /* Conventions */

  @Check
  public void checkTypeIsUppercase(Type type) {
    if (!Character.isUpperCase(type.getName().charAt(0))) {
      warning("Classes should start with an uppercase letter", type, TYPE__NAME, INSIGNIFICANT_INDEX);
    }
  }

  @Check
  public void checkMethodIsLowercase(Method method) {
    if (!Character.isLowerCase(method.getName().charAt(0))) {
      warning("Methods should start with a lower case letter", method, METHOD__CLASS_METHOD, INSIGNIFICANT_INDEX);
    }
  }

  /* Type guess - not really inference - warnings */

  abstract class NumericUpdateChecker<T extends EObject> {

    void check(T update) {
      checkUpdatedVariableMayBeNumeric(update, getTarget(update), targetFeature());
      if (getValue(update) != null)
        checkUpdatingExpressionMayBeNumeric(update, getValue(update), valueFeature());
    }

    protected abstract EReference valueFeature();

    protected abstract EReference targetFeature();

    protected abstract Expression getValue(T update);

    protected abstract EObject getTarget(T update);

    private void checkUpdatedVariableMayBeNumeric(EObject update, EObject target, EReference feature) {
      if (target instanceof Attribute)
        checkMayBeNumeric(update, ((Attribute) target).getInitialValue(), feature);
      else if (target instanceof LocalVariable)
        checkMayBeNumeric(update, ((LocalVariable) target).getValue(), feature);
    }
    
    private void checkUpdatingExpressionMayBeNumeric(EObject update, Expression target, EReference feature) {
      if (target instanceof IdExpression)
        checkUpdatedVariableMayBeNumeric(update, ((IdExpression) target).getValue(), feature);
      else 
        checkMayBeNumeric(update, (Expression) target, feature);
    }

    private void checkMayBeNumeric(EObject update, Expression target, EReference feature) {
      if (target instanceof ConstructionExpression //
        || target instanceof CollectionLiteralExpression //
        || target instanceof StringExpression //
        || target instanceof TrueExpression //
        || target instanceof FalseExpression) {
        warning("Expression does not seem to be numeric", update, feature, INSIGNIFICANT_INDEX);
      }
    }

  }

  @Check
  public void checkIncrementedVariableIsNumber(IncrementExpression increment) {
    new NumericUpdateChecker<IncrementExpression>() {
      protected EReference valueFeature() {
        return INCREMENT_EXPRESSION__VALUE;
      }

      protected EReference targetFeature() {
        return INCREMENT_EXPRESSION__TARGET;
      }

      protected Expression getValue(IncrementExpression update) {
        return update.getValue();
      }

      protected EObject getTarget(IncrementExpression update) {
        return update.getTarget();
      }
    }.check(increment);
  }

  @Check
  public void checkDecrementedVariableIsNumber(DecrementExpression decrement) {
    new NumericUpdateChecker<DecrementExpression>() {
      protected EReference valueFeature() {
        return DECREMENT_EXPRESSION__VALUE;
      }

      protected EReference targetFeature() {
        return DECREMENT_EXPRESSION__TARGET;
      }

      protected Expression getValue(DecrementExpression update) {
        return update.getValue();
      }

      protected EObject getTarget(DecrementExpression update) {
        return update.getTarget();
      }
    }.check(decrement);
  }
}
