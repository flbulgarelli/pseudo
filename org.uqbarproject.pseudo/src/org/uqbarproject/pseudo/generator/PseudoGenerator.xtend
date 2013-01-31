/*
 * generated by Xtext
 */
package org.uqbarproject.pseudo.generator

import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.uqbarproject.pseudo.pseudo.AssignmentExpression
import org.uqbarproject.pseudo.pseudo.Attribute
import org.uqbarproject.pseudo.pseudo.ComprehensionExpression
import org.uqbarproject.pseudo.pseudo.FalseExpression
import org.uqbarproject.pseudo.pseudo.ForEachExpression
import org.uqbarproject.pseudo.pseudo.IdExpression
import org.uqbarproject.pseudo.pseudo.WhenExpression
import org.uqbarproject.pseudo.pseudo.Message
import org.uqbarproject.pseudo.pseudo.Expression
import org.uqbarproject.pseudo.pseudo.Method
import org.uqbarproject.pseudo.pseudo.Return
import org.uqbarproject.pseudo.pseudo.NullExpression
import org.uqbarproject.pseudo.pseudo.NumberExpression
import org.uqbarproject.pseudo.pseudo.SelfExpression
import org.uqbarproject.pseudo.pseudo.MessageSend
import org.uqbarproject.pseudo.pseudo.StringExpression
import org.uqbarproject.pseudo.pseudo.TrueExpression
import org.uqbarproject.pseudo.pseudo.Type
import org.uqbarproject.pseudo.pseudo.LocalVariable
import org.uqbarproject.pseudo.pseudo.IncrementExpression
import org.uqbarproject.pseudo.pseudo.DecrementExpression
import org.uqbarproject.pseudo.pseudo.ListLiteralExpression
import org.uqbarproject.pseudo.pseudo.SetLiteralExpression
import org.uqbarproject.pseudo.pseudo.MaxExpression
import org.uqbarproject.pseudo.pseudo.MinExpression
import org.uqbarproject.pseudo.pseudo.SumExpression
import org.uqbarproject.pseudo.pseudo.EmbeddableExpression
import org.uqbarproject.pseudo.pseudo.AverageExpression
import org.uqbarproject.pseudo.pseudo.ConstructionExpression
import org.uqbarproject.pseudo.pseudo.Parameter
import org.uqbarproject.pseudo.pseudo.SuperSend
import org.uqbarproject.pseudo.pseudo.Applicable
import org.uqbarproject.pseudo.pseudo.ApplicablePipeline
import org.uqbarproject.pseudo.pseudo.ApplicableComposition
import org.uqbarproject.pseudo.pseudo.ApplicableDisyuntion
import org.uqbarproject.pseudo.pseudo.ApplicableConjuntion

import static extension org.uqbarproject.pseudo.util.EObjectExtensions.*
import static extension org.uqbarproject.pseudo.SelectorExtensions.* 

/**
 * @author flbulgareli
 */
class PseudoGenerator implements IGenerator {
	
    @Inject extension IQualifiedNameProvider
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		resource.allContents.filter(typeof(Type)).forEach [
			fsa.generateFile(
				it.fullyQualifiedName.toString("/") + ".java", 
				it.compile
			)
		]
	}
	
  	def dispatch compile(EObject declaration) { 
  		declaration.subclassResponsibility('compile') as String
  	}
	def dispatch compile(Type type) '''
		import org.uqbarproject.pseudo.runtime.*;
		import org.uqbarproject.pseudo.runtime.reductions.*;
		public class «type.name» extends «type.effectiveSuperType» {
		  	«FOR member : type.members»
		  	  «member.compile»		
		  	«ENDFOR»
		}
  	'''
  	def dispatch compile(Method method) '''
	  «IF method.overrideMethod»
	  @Override
	  «ENDIF»
	  public «method.classMethod.compileClassModifier» Object «method.name.toJavaId»(«method.parameters.map [ "Object " + it.name.toJavaId ].join(", ")») throws Throwable {
	  	«IF method.statements.empty»
	  	return null;
	  	«ELSE»
	  	«FOR statement : method.statements.take(method.statements.size-1)»
	  		«statement.compile»		
	  	«ENDFOR»
	  	«IF method.statements.last instanceof Expression»
	  	return « (method.statements.last as Expression).compileForResult»;
	  	«ELSEIF method.statements.last instanceof Return»
	  	«method.statements.last.compile»
	  	«ELSE»
	  	«method.statements.last.compile»
	  	return null;
	  	«ENDIF»
	  	«ENDIF»
	  }
	'''
	def dispatch compile(Attribute declaration) '''
	  private «declaration.classAttribute.compileClassModifier» Object «declaration.name.toJavaId» = «declaration.initialValue.compileForResultOrNull»;
«««	  TODO REMOVER
	  public void set«declaration.name.toJavaIdPart»(Object value) {
	  	this.«declaration.name.toJavaId» = value;
	  }
	  public Object get«declaration.name.toJavaIdPart»() {
	  	return this.«declaration.name.toJavaId»;
	  }
	  public Object «declaration.name.toJavaId»() {
	  	return this.«declaration.name.toJavaId»;
	  }
	'''
	def dispatch compile(Return ret) '''
		return «ret.value.compileForResult»;
	'''
	def dispatch compile(LocalVariable declaration) '''
		Object «declaration.name.toJavaId» = «declaration.getValue.compileForResultOrNull»;
	'''
	//TODO Not an expression, yet
	def dispatch compile(ApplicablePipeline pipeline) '''
		«joinMessages(pipeline.messages, "andThen")»
	'''
	def dispatch compile(ApplicableComposition pipeline) '''
		«joinMessages(pipeline.messages, "compose")»
	'''
	def dispatch compile(ApplicableDisyuntion pipeline) '''
		«joinMessages(pipeline.messages, "or")»
	'''
	def dispatch compile(ApplicableConjuntion pipeline) '''
		«joinMessages(pipeline.messages, "and")»
	'''
	def dispatch compile(Message message) '''
		new MessageSend("«message.selector.toJavaId»"
		«IF message.arguments.empty»
		 )
		«ELSE»
		, «joinCompileResults(message.arguments)»)
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
	
	def dispatch compileForResult(AssignmentExpression expression) '''
		«expression.target.compileForReference» = («expression.value.compileForResult»)
	'''
	def dispatch compileForResult(IncrementExpression expression) '''
		«expression.target.compileForReference» = new MessageSend(
				"add",
				«expression.value.compileForResultOrOne»
				).apply(«expression.target.compileForReference»)
	'''
	def dispatch compileForResult(DecrementExpression expression) '''
		«expression.target.compileForReference» = new MessageSend(
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
    	new java.util.LinkedList(java.util.Arrays.asList(«joinCompileResults(expression.elements)»))
    '''
    def dispatch compileForResult(SetLiteralExpression expression) '''
    	new java.util.HashSet(java.util.Arrays.asList(«joinCompileResults(expression.elements)»))
    '''

    def dispatch compileForResult(MessageSend expression) '''
    	«expression.message.compile».apply(«expression.getReceptor.compileForResult»)
    '''
    def dispatch compileForResult(SuperSend expression) '''
    	super.«expression.parentOfType(typeof(Method)).name.toJavaId»(«joinCompileResults(expression.arguments)»);
    '''
	def dispatch compileForResult(ForEachExpression expression) '''
	    new Traversing(
	       «expression.action.compile»,
	       «compileForResultOrTrueFunction(expression.condition)»
	       ).apply(«expression.target.compileForResult»)
	'''
	def dispatch compileForResult(ComprehensionExpression expression) '''
	    new TraversingTransformation(
	       «compileForResultOrIdentityFunction(expression.mapping)»,
	       «compileForResultOrTrueFunction(expression.condition)»,
	       new IdentityFunction()
	       ).apply(«expression.target.compileForResult»)
	'''
	def dispatch compileForResult(MaxExpression expression) {
	 	compileReductionWithCriteria('MaxFunction', expression.criteria, expression.target)
	}
	def dispatch compileForResult(MinExpression expression) {
		compileReductionWithCriteria('MinFunction', expression.criteria, expression.target)
	}
	def dispatch compileForResult(SumExpression expression) {
		compileReductionWithCriteria('SumFunction', expression.criteria, expression.target)
	}
	def dispatch compileForResult(AverageExpression expression) {
		compileReductionWithCriteria('AverageFunction', expression.criteria, expression.target)
	}
	def dispatch compileForResult(ConstructionExpression expression) '''
		new «expression.target.name»()
	'''
	
	def compileForBooleanResult(Expression expression) '''
		(Boolean) («expression.compileForResult»)
	'''
	
	def compileForResultOrNull(Expression expression) {
		if (expression != null) expression.compileForResult else "null"  
	}
	
	def compileForResultOrOne(Expression expression) {
		if(expression != null) expression.compileForResult else "java.math.BigDecimal.ONE" 
	}
	
	//TODO pass arguments
	def compileForResultOrTrueFunction(Applicable message)'''
		«IF message != null»
		    «message.compile»
		«ELSE»
			new ConstantFunction(true)
		«ENDIF»
	'''

	//TODO pass arguments	
	def compileForResultOrIdentityFunction(Applicable message)'''
		«IF message != null»
			«message.compile»
		«ELSE»
			new IdentityFunction()
		«ENDIF»
	'''
	
	def effectiveSuperType(Type type) { 
		if (type.superType != null) type.superType.name else 'java.lang.Object'
	}
	
	def compileClassModifier(boolean isClassModifier) {
		if (isClassModifier) "static" else ""
	}
	
	def compileReductionWithCriteria(String reductionClass, Applicable criteria, EmbeddableExpression target) '''
	    new TraversingTransformation(
	       new IdentityFunction(),
	       new ConstantFunction(true),
	       new «reductionClass»(«compileForResultOrIdentityFunction(criteria)»)
	       ).apply(«target.compileForResult»)
	'''
	
	def Object subclassResponsibility(EObject object, String message) {
		throw new UnsupportedOperationException(object + " should implement " + message)
	}
	
	def dispatch compileForReference(EObject object) {
		object.subclassResponsibility('compileForReference')
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
	def joinCompileResults(EList<? extends Expression> expressions) {
    	expressions.map[it.compileForResult].join(',')
    }
    def joinMessages(EList<Message> messages, String selector){
    	messages.map['(' + it.compile + ')'].join("." + selector)
    }
}
