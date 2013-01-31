package org.uqbarproject.pseudo

class SelectorExtensions {
	
	def static toJavaIdPart(String selector) {
		var parts = selector.split("_")
		parts.map[it.toFirstUpper].join
	}
	
	def static toJavaId(String selector) {
		toJavaIdPart(selector).toFirstLower
	}
	
}