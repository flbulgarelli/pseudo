package org.uqbarproject.pseudo.jvmmodel

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.uqbarproject.pseudo.pseudo.All
import org.uqbarproject.pseudo.pseudo.Any
import org.uqbarproject.pseudo.pseudo.Applicable
import org.uqbarproject.pseudo.pseudo.AssignExpression
import org.uqbarproject.pseudo.pseudo.Attribute
import org.uqbarproject.pseudo.pseudo.Average
import org.uqbarproject.pseudo.pseudo.CatchedExceptionExpression
import org.uqbarproject.pseudo.pseudo.ClassType
import org.uqbarproject.pseudo.pseudo.ComparisonExpression
import org.uqbarproject.pseudo.pseudo.ComparisonOperation
import org.uqbarproject.pseudo.pseudo.DecrementExpression
import org.uqbarproject.pseudo.pseudo.ExceptionThrowExpression
import org.uqbarproject.pseudo.pseudo.ExceptionType
import org.uqbarproject.pseudo.pseudo.Expression
import org.uqbarproject.pseudo.pseudo.FalseExpression
import org.uqbarproject.pseudo.pseudo.FilteredExpression
import org.uqbarproject.pseudo.pseudo.First
import org.uqbarproject.pseudo.pseudo.ForAllExpression
import org.uqbarproject.pseudo.pseudo.IdExpression
import org.uqbarproject.pseudo.pseudo.IncrementExpression
import org.uqbarproject.pseudo.pseudo.InitExpression
import org.uqbarproject.pseudo.pseudo.ListLiteralExpression
import org.uqbarproject.pseudo.pseudo.LocalVariable
import org.uqbarproject.pseudo.pseudo.Max
import org.uqbarproject.pseudo.pseudo.Message
import org.uqbarproject.pseudo.pseudo.MessageSendExpression
import org.uqbarproject.pseudo.pseudo.MessageThrowExpression
import org.uqbarproject.pseudo.pseudo.Method
import org.uqbarproject.pseudo.pseudo.Min
import org.uqbarproject.pseudo.pseudo.Model
import org.uqbarproject.pseudo.pseudo.NewExpression
import org.uqbarproject.pseudo.pseudo.NullExpression
import org.uqbarproject.pseudo.pseudo.NumberExpression
import org.uqbarproject.pseudo.pseudo.Parameter
import org.uqbarproject.pseudo.pseudo.Reduction
import org.uqbarproject.pseudo.pseudo.ReductionExpression
import org.uqbarproject.pseudo.pseudo.Return
import org.uqbarproject.pseudo.pseudo.SelectExpression
import org.uqbarproject.pseudo.pseudo.SelfExpression
import org.uqbarproject.pseudo.pseudo.SetLiteralExpression
import org.uqbarproject.pseudo.pseudo.SetupExpression
import org.uqbarproject.pseudo.pseudo.StringExpression
import org.uqbarproject.pseudo.pseudo.Sum
import org.uqbarproject.pseudo.pseudo.SuperSend
import org.uqbarproject.pseudo.pseudo.Take
import org.uqbarproject.pseudo.pseudo.Member
import org.uqbarproject.pseudo.pseudo.ThrowExpression
import org.uqbarproject.pseudo.pseudo.TrueExpression
import org.uqbarproject.pseudo.pseudo.TryCatchExpression
import org.uqbarproject.pseudo.pseudo.WhenExpression
import org.eclipse.xtext.common.types.JvmMember
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.common.types.JvmTypeReference
import org.uqbarproject.pseudo.pseudo.Type

import static extension org.uqbarproject.pseudo.SelectorExtensions.*
import static extension org.uqbarproject.pseudo.util.EObjectExtensions.*

class PseudoJvmModelInferrer extends AbstractModelInferrer {
  
  static val MESSAGE = "org.uqbarproject.pseudo.runtime.MessageSend"
  static val ITERABLES = "org.uqbarproject.pseudo.runtime.Iterables"

	@Inject extension JvmTypesBuilder

   	def dispatch void infer(Model element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		for(type : element.types) {
   		  acceptor.accept(type.toClass(type.name)).initializeLater([
   		    superTypes += type.buildParentType
          for (member : type.members) {
            members.addAll(member.buildMember)
          }
          
          members += it.toMethod("toString", it.newTypeRef(typeof(String))) [
            body = [
              append('''return "«type.name»[«type.members.filter(typeof(Attribute)).map[it.name + '="+'+it.name.toJavaId+'+"'].join(", ")»]";''')
            ]
          ]
          
          members += it.toConstructor [
            body = [
              append('super();')
            ]
          ]
          members.addAll(type.buildConstructors)
        ])
   		}
   	}
   	
    def dispatch buildConstructors(Type t) {
      t.subclassResponsibility("constructors") as List<JvmMember>
    }
    def dispatch buildConstructors(ClassType t) {
      val attributesNames = t.members.filter(typeof(Attribute))
      if(!attributesNames.empty)
        <JvmMember>newArrayList(t.toConstructor[ c |
          attributesNames.forEach [
            c.parameters += it.toParameter(it.name.toJavaId, newTypeRef(typeof(Object))) 
          ]
          c.body = [ b |
            attributesNames.forEach [
              c.parameters += it.toParameter(it.name.toJavaId, newTypeRef(typeof(Object)))
              b.append('''this.«it.name.toJavaId» = «it.name.toJavaId»;''') 
            ]            
          ]
        ])
      else 
        <JvmMember>newArrayList()
    }
    def dispatch buildConstructors(ExceptionType t) {
      newArrayList(
        t.toConstructor[
          parameters += t.toParameter("message", newTypeRef(typeof(String)))
          body = [
            append("super(message);")
          ]
        ] 
//        t.toConstructor[
//          parameters += t.toParameter("throwable", newTypeRef(typeof(Throwable)))
//          body = [
//            append("super(throwable);")
//          ]
//        ],
//        t.toConstructor[
//          parameters += t.toParameter("message", newTypeRef(typeof(String)))
//          parameters += t.toParameter("throwable", newTypeRef(typeof(Throwable)))
//          body = [
//            append("super(message, throwable);")
//          ]
//        ]
      )
    }
   	
   	def dispatch buildParentType(Type t) {
   	  t.subclassResponsibility("parentType") as JvmTypeReference
   	}
   	
   	def dispatch buildParentType(ClassType t) {
   	  t.newTypeRef(if (t.superType != null) t.superType.name else 'java.lang.Object') 
   	}
   	
   	def dispatch buildParentType(ExceptionType t) {
   	  t.newTypeRef('org.uqbarproject.pseudo.runtime.GenericException')
   	}
   	
   	def dispatch buildMember(Member m) {
   	  m.subclassResponsibility("buildMember") as List<JvmMember>
   	}
   	def dispatch buildMember(Method m){
   	  val name = m.name.toJavaId
   	  newArrayList(m.toMethod(name, m.newTypeRef(typeof(Object))) [ op |
   	    op.setStatic(m.isClassMethod)
   	    op.exceptions += m.newTypeRef(typeof(Throwable))
   	    m.parameters.forEach [
   	      op.parameters += it.toParameter(it.name.toJavaId, m.newTypeRef(typeof(Object)))
 	      ]
        op.body = [
          append('''
        «IF m.statements.empty» 
        return null;
        «ELSE»
        «FOR statement : m.statements.take(m.statements.size-1)»
          «statement.compile»   
        «ENDFOR»
        «IF m.statements.last instanceof ThrowExpression»
        «m.statements.last.compile»
        «ELSEIF m.statements.last instanceof Expression»
        return « (m.statements.last as Expression).compileForResult»;
        «ELSEIF m.statements.last instanceof Return»
        «m.statements.last.compile»
        «ELSE»
        «m.statements.last.compile»
        return null;
        «ENDIF»
        «ENDIF»
        ''')
        ]
      ]) 
   	}
   	def dispatch buildMember(Attribute a) {
   	  val name = a.name.toJavaId   
   	  val attributeAndSimpleGetter = newArrayList(
   	    a.toField(name, a.newTypeRef(typeof(Object)))[
   	      setStatic(a.classAttribute)
   	      if(a.initialValue != null)
   	      setInitializer([
            append(a.initialValue.compileForResult) 
   	      ])
   	    ],        
        a.toMethod(name, a.newTypeRef(typeof(Object))) [
          body = [append('''return this.«name»;''')]
          setStatic(a.classAttribute)
        ])
        
      if(!a.classAttribute) 
        attributeAndSimpleGetter + newArrayList(
          a.toGetter(name, a.newTypeRef(typeof(Object))),
          a.toSetter(name, a.newTypeRef(typeof(Object))))
      else
        attributeAndSimpleGetter
   	}
   	
    def dispatch compile(EObject declaration) { 
      declaration.subclassResponsibility('compile') as String
    }

  def dispatch compile(Return ret) '''
    return «ret.value.compileForResult»;
  '''
  def dispatch compile(LocalVariable declaration) '''
    Object «declaration.name.toJavaId» = «declaration.getValue.compileForResultOrNull»;
  '''
  def dispatch compile(Applicable pipeline) '''
    «pipeline.left.compile».«pipeline.compileOperation»(«pipeline.right.compile»)
  '''
  def compileOperation(Applicable operation) {
    if(operation.andThen) 'andThen'
    else if(operation.compose) 'compose'
    else if(operation.or) 'or'
    else if(operation.and) 'and'
  }
  def dispatch compile(Message message) '''
    new «MESSAGE»("«message.selector.toJavaId»"
    «IF message.arguments.empty»
     )
    «ELSE»
    , «joinCompileExpressions(message.arguments)»)
    «ENDIF»
  '''

  
  //Expressions
  def dispatch compile(Expression expression) '''
    «expression.compileForResult»;
  '''
  def dispatch compile(WhenExpression expression) '''
    if («expression.cases.get(0).compileForBooleanResult») {
      «expression.actions.get(0).compile»;
    }
    «IF expression.cases.size > 1»
    «FOR i : 1..expression.cases.size-1»
    else if («expression.cases.get(i).compileForBooleanResult») {
      «expression.actions.get(i).compile»;
    }
    «ENDFOR»
    «ENDIF»
    «IF expression.defaultAction != null»   
    else {
      «expression.defaultAction.compile»;
    }
    «ENDIF»   
  '''
  //TODO invallid syntax
  def dispatch compileForResult(TryCatchExpression expression) '''
    try {
       «expression.action.compile»
    } catch (Throwable $$e$$) {
       «expression.catchAction.compile»
    }
  '''
  def dispatch compile(TryCatchExpression expression) '''
    try {
      «expression.action.compile»
    } catch (Throwable $$e$$) {
      «expression.catchAction.compile»
    }
  '''
  //TODO validar que solo se pueda emplear en contextos adecuados
  def dispatch compileForResult(CatchedExceptionExpression expr) '''
     $$e$$
  '''
   
  def dispatch compileForResult(WhenExpression expression) '''
    ((«expression.cases.get(0).compileForBooleanResult») ? 
      («expression.actions.get(0).compileForResult»)
    «IF expression.cases.size > 1»      
    «FOR i : 1..expression.cases.size-1»
    : («expression.cases.get(i).compileForBooleanResult») ?
      («expression.actions.get(i).compileForResult»)        
    «ENDFOR»
    «ENDIF»
    «IF expression.defaultAction != null» 
    : («expression.defaultAction.compileForResult»)
    «ELSE»
    : null
    «ENDIF»
    )
  '''
  def dispatch compileForResult(ComparisonExpression expression) '''
    «IF expression.op == ComparisonOperation::EQ»
    «expression.left.compileForResult».equals(«expression.right.compileForResult»)
    «ELSEIF expression.op == ComparisonOperation::NEQ»
    !«expression.left.compileForResult».equals(«expression.right.compileForResult»)
    «ELSE»
    ((Comparable)(«expression.left.compileForResult»)).compareTo(«expression.right.compileForResult») «expression.op.build»
    «ENDIF»
  ''' 
  def build(ComparisonOperation op) {
    switch op {
      case ComparisonOperation::LT: '< 0'
      case ComparisonOperation::GT: '> 0'
      case ComparisonOperation::LTE: '<= 0'
      case ComparisonOperation::GTE: '>= 0'
    }
  }
  
  def dispatch compileForResult(AssignExpression expression) '''
    «expression.target.compileForReference» = («expression.value.compileForResult»)
  '''
  def dispatch compileForResult(IncrementExpression expression) '''
    «expression.target.compileForReference» = new «MESSAGE»(
        "add",
        «expression.value.compileForResultOrOne»
        ).apply(«expression.target.compileForReference»)
  '''
  def dispatch compileForResult(DecrementExpression expression) '''
    «expression.target.compileForReference» = new «MESSAGE»(
        "subtract",
        «expression.value.compileForResultOrOne»
        ).apply(«expression.target.compileForReference»)
  '''
    def dispatch compileForResult(NumberExpression expression) '''
      (new java.math.BigDecimal("«expression.value»"))    
    '''
    def dispatch compileForResult(IdExpression expression) {
      expression.value.compileForReference   
    }
    def dispatch compileForResult(StringExpression expression) '''
      "«expression.value»"    
    '''
    def dispatch compileForResult(NullExpression expression) '''
      (null)
    '''
    def dispatch compile(NullExpression expression) '''
      ;
    '''
    def dispatch compileForResult(SelfExpression expression) '''
      this
    '''      
    def dispatch compileForResult(TrueExpression expression) '''
        true
    '''
    def dispatch compileForResult(FalseExpression expression) '''
        false
    '''
    def dispatch compileForResult(ListLiteralExpression expression) '''
      new java.util.LinkedList(java.util.Arrays.asList(«joinCompileExpressions(expression.elements)»))
    '''
    def dispatch compileForResult(SetLiteralExpression expression) '''
      new java.util.HashSet(java.util.Arrays.asList(«joinCompileExpressions(expression.elements)»))
    '''
    def dispatch compileForResult(MessageThrowExpression expression) '''
      throw new org.uqbarproject.pseudo.runtime.GenericException(«expression.throwable.compileForResult»)
    '''
    def dispatch compileForResult(ExceptionThrowExpression expression) '''
      throw «expression.throwable.compileForResult»
    '''
    def dispatch compileForResult(MessageSendExpression expression) '''
      «expression.getMessage.compile».apply(«expression.getReceptor.compileForResult»)
    '''
    def dispatch compileForResult(SuperSend expression) '''
      super.«expression.parentOfType(typeof(Method)).name.toJavaId»(«joinCompileExpressions(expression.arguments)»);
    '''
  def dispatch compileForResult(ForAllExpression expression) '''
    «ITERABLES».each(«expression.target.compileForResult», «expression.action.compile»)
  '''  
  def dispatch compileForResult(SelectExpression expression) {
    compileMapping(expression.target, expression.mapping)
  }
  def dispatch compileForResult(FilteredExpression expression) '''
    «ITERABLES».filter(«expression.target.compileForResult», «expression.condition.compile»)
  '''
  def dispatch compileForResult(ReductionExpression expression) '''
    «ITERABLES».reduce(
    «IF expression.reduction.mapping != null»
     «compileMapping(expression.target, expression.reduction.mapping)»
    «ELSE»
     «expression.target.compileForResult»
    «ENDIF»,
    new «expression.reduction.compile»)
  '''
  def dispatch compile(Reduction reduction) {
    reduction.subclassResponsibility("compile") as String
  }
  def dispatch compile(Sum reduction) {
    compileReduction('Sum')
  }
  def dispatch compile(Average reduction) {
    compileReduction('Average')
  }
  def dispatch compile(First reduction) {
    compileReduction('First')
  }
  def dispatch compile(Take reduction) {
    compileReductionWithArgument('Take', reduction.amount)
  }
  def dispatch compile(Min reduction) {
    compileReductionWithCriteria('Min', reduction.criteria)
  }
  def dispatch compile(Max reduction) {
    compileReductionWithCriteria('Max', reduction.criteria)
  }
  def dispatch compile(All reduction) {
    compileReductionWithCriteria('All', reduction.criteria)
  }
  def dispatch compile(Any reduction) {
    compileReductionWithCriteria('Any', reduction.criteria)
  }
  def dispatch compileForResult(NewExpression expression) '''
    new «expression.target.name»(«joinCompileExpressions(expression.arguments)»)
  '''
  def dispatch compileForResult(InitExpression expression) '''
    new «expression.target.name»() {{
      «FOR init : expression.initializations»
        set«init.attribute.toJavaIdPart»(«init.initialValue.compileForResult»);
      «ENDFOR»
    }}
  '''
  def dispatch compileForResult(SetupExpression expression) '''
    «««TODO
  ''' 
  
  def dispatch compileForBooleanResult(Expression expression) '''
    (Boolean) («expression.compileForResult»)
  '''
  def dispatch compileForBooleanResult(MessageSendExpression expression) '''
    «expression.message.compile».applyForBoolean(«expression.receptor.compileForResult»)
  '''
  
  def compileForResultOrNull(Expression expression) {
    if (expression != null) expression.compileForResult else "null"  
  }
  
  def compileForResultOrOne(Expression expression) {
    if(expression != null) expression.compileForResult else "java.math.BigDecimal.ONE" 
  }
  
  def compileForResultOrIdentityFunction(Applicable message)'''
    «IF message != null»
      «message.compile»
    «ELSE»
      new org.uqbarproject.pseudo.runtime.IdentityFunction()
    «ENDIF»
  '''
  
 
  def compileReductionWithCriteria(String reductionClass, Applicable criteria) '''
      org.uqbarproject.pseudo.runtime.reductions.«reductionClass»Reduction(«criteria.compileForResultOrIdentityFunction»)
  '''
  def compileReductionWithArgument(String reductionClass, Expression argument) '''
      org.uqbarproject.pseudo.runtime.reductions.«reductionClass»Reduction(«argument.compileForResult»)
  '''
  def compileReduction(String reductionClass) '''
      org.uqbarproject.pseudo.runtime.reductions.«reductionClass»Reduction()
  '''
  
  
  def dispatch compileForReference(EObject object) {
    object.subclassResponsibility('compileForReference') as String
  }
  def dispatch compileForReference(Attribute attribute) {
    'this.' + attribute.name.toJavaId
  } 
  def dispatch compileForReference(Parameter parameter) {
    parameter.name.toJavaId
  } 
  def dispatch compileForReference(LocalVariable localVariable) {
    localVariable.name.toJavaId
  }
  def joinCompileExpressions(EList<? extends Expression> expressions) {
      expressions.map[it.compileForResult].join(',')
    }
    def joinCompileParameters(Iterable<String> names) {
      names.map [ "Object " + it ].join(", ")
    } 
  
  def compileMapping(Expression target, Applicable mapping) '''
    «ITERABLES».map(«target.compileForResult», «mapping.compile»)
  '''       
   	
}

