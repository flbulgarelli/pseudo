/*
 * generated by Xtext
 */
package org.uqbarproject.pseudo.generator

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.uqbarproject.pseudo.pseudo.Attribute
import org.uqbarproject.pseudo.pseudo.ComprehensionExpression
import org.uqbarproject.pseudo.pseudo.Expression
import org.uqbarproject.pseudo.pseudo.ForEachExpression
import org.uqbarproject.pseudo.pseudo.IdExpression
import org.uqbarproject.pseudo.pseudo.IfExpression
import org.uqbarproject.pseudo.pseudo.Method
import org.uqbarproject.pseudo.pseudo.NullExpression
import org.uqbarproject.pseudo.pseudo.NumberExpression
import org.uqbarproject.pseudo.pseudo.StringExpression
import org.uqbarproject.pseudo.pseudo.Type
import org.uqbarproject.pseudo.pseudo.Message
import org.uqbarproject.pseudo.pseudo.SimpleMessageSend
import org.uqbarproject.pseudo.pseudo.SelfUnaryMessageShortcutExpression
import org.uqbarproject.pseudo.pseudo.UnaryMessage
import org.uqbarproject.pseudo.pseudo.AssignmentExpression


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
  		subclassResponsibility as String
  	}
	def dispatch compile(Type type) '''
		import org.uqbarproject.pseudo.runtime.*;
		public class «type.name» extends «type.effectiveSuperType» {
		  	«FOR declaration : type.declarations»
		  	  «declaration.compile»		
		  	«ENDFOR»
		}
  	'''
  	def dispatch compile(Method method) '''
	  public Object «method.name»(«method.parameters.map [ "Object " + it ].join(", ")») throws Throwable {
	  	«FOR statement : method.statements»
	  		«statement.compile»		
	  	«ENDFOR»
	  	return null;
	  }
	'''
	def dispatch compile(Attribute declaration) '''
	  private Object «declaration.name» = «declaration.effectiveInitialValue»;
	  public void set«declaration.name.toFirstUpper»(Object value) {
	  	this.«declaration.name» = value;
	  }
	  public Object get«declaration.name.toFirstUpper»() {
	  	return this.«declaration.name»;
	  }
	  public Object «declaration.name»() {
	  	return this.«declaration.name»;
	  }
	'''
	//TODO Not an expression, yet
	def dispatch compile(UnaryMessage message) '''
		new MessageSend("«message.selector»")
	'''
	
	//Expressions
	def dispatch compile(Expression expression) '''
		«expression.compileForResult»;
	'''
	def dispatch compile(IfExpression expression) '''
		if («expression.condition.compileForResult») {
			«expression.trueExpression.compile»;
		} else {
			«expression.trueExpression.compile»;
		}		
	'''
	
	def dispatch compileForResult(IfExpression expression) '''
		((«expression.condition.compileForResult») 
			? («expression.trueExpression.compileForResult»)
			: («expression.trueExpression.compileForResult»)) 
	'''
	def dispatch compileForResult(AssignmentExpression expression) '''
		«expression.assigned» = («expression.value.compileForResult»)
	'''
    def dispatch compileForResult(NumberExpression expression) '''
    	(new java.math.BigDecimal(«expression.value»))    
    '''
    def dispatch compileForResult(IdExpression expression) {
    	expression.value    
    }
    def dispatch compileForResult(StringExpression expression) '''
    	"«expression.value»"    
    '''
    def dispatch compileForResult(NullExpression expression) {
    	'(null)'
    }   
     
    def dispatch compileForResult(SimpleMessageSend expression) '''
    	«expression.message.compile».apply(«expression.receptor»)
    '''
    def dispatch compileForResult(SelfUnaryMessageShortcutExpression expression) '''
    	«expression.message.compile».apply(this)
    '''
	def dispatch compileForResult(ForEachExpression expression) '''
	    new Traversing(
	       «expression.action.compile»,
	       «filterForSelector(expression.condition)»
	       ).apply(«expression.target.compileForResult»)
	'''
	def dispatch compileForResult(ComprehensionExpression expression) '''
	    new TraversingTransformation(
	       «mapperForSelector(expression.mapping)»,
	       «filterForSelector(expression.mapping)»,
	       new IdentityFunction()
	       ).apply(«expression.target.compileForResult»)
	'''

	def effectiveInitialValue(Attribute attribute) {
		if (attribute.initialValue != null) attribute.initialValue.compileForResult else "null"  
	}
	
	def effectiveSuperType(Type type) { 
		if (type.superType != null) type.superType.name else 'java.lang.Object'
	}
	
	def Object subclassResponsibility() {
		throw new UnsupportedOperationException()
	}
	
	//TODO pass arguments
	def filterForSelector(Message message)'''
		«IF message == null»
			new ConstantFunction(true)
		«ELSE»
		    «message.compile»
		«ENDIF»
	'''

	//TODO pass arguments	
	def mapperForSelector(Message message)'''
		«IF message == null»
			new IdentityFunction()
		«ELSE»
			«message.compile»
		«ENDIF»
	'''
}
