pseudo
======

Un lenguaje de objetos y funcional que imita al pseudocódigo

# Características

## Semánticas

 * Tipado dinámico, implícito
   * Motivación: TODO
 * Paradigma: 
    * Objetos, en tanto soporta el concepto de objetos polimórficos y envío de mensajes
      * Motivación: TODO
    * Funcional, en tanto los mensajes son valores y ciudadanos de primer orden (!)
      * Motivación: TODO
  * Basado en clases
     * Motivación: TODO
  * Con efectos y valores mutables
  * __A definir__ _Con mecanismos de extensión: mixins, métodos de extensión o clases abiertas_
     * Motivación: TODO
  * Con colecciones por comprensión y primitivas básicas de transformación de colecciones
     * Motivación: TODO
  * Con primitivas básicas de combinación de mensajes: composición, encadenamiento, disyunción, conjunción
  * Sin tipos primitivos
  * Pocos "objetos básicos":
     * Colecciones: Listas, Conjuntos, Diccionarios
     * Booleanos, Strings, Numeros enteros y numeros decimales 
     * Mensaje
   * No ofrece 
      * clases anonimas
      * enumeraciones     
      * __A definir__ _espacios de nombres_
      * Modulos
      * lambdas (pero los mensajes son valores)
    * __A definir__ _aplicacion parcial_
      * Ojo: la sintaxis de envio de mensajes impactará sobre esta característica

## Sintácticas

  * __A definir__ _en español_
  * Preferir palabras por sobre símbolos, y favorecer construcciones cercanas al lenguaje natural
     * Motivación: sintaxis de SQL como caso de éxito
  * Colecciones literales
  * __A definir__. _Variables automáticas_
  * __A definir__ sintaxis de envío de mensajaes. TODO: elegir entre
    * Java-like (con receptor parentesis opcionales)
    * Erlang-Like: objeto ! { selector, argumento0,argumento1..argumentoN  }    
    * Smalltalk-like  
    * Haskell-Like prefija o infija: 
       * receptor selector argumento0 argumento1 ... argumentoN
       * selector argumento0 argumento1 ... argumentoN receptor
    * Alguna sintaxis híbrida, basada en etiquetas no posicionales, Groovy-Like:  
        * ```receptor : selector etiqueta0 argumento 0 etiqueta1 argumento1 ... etiquetaN argumentoN```
          * Ejemplo: ```pastel : cocinar a (150: grados) durante (30: minutos)```
        * Se podria incluir algun atajo para los mensajes unarios:
          * Ejemplo: ```pastel : cocinar a 150'grados durante 30'minutos```  
          

## Plataforma
  
  * JVM
  * __A definir__: compilación a código Java o a bytecode

## En general
  
 * No es un lenguaje de proposito general, sino orientado al modelado a alto nivel de soluciones de software
 * No pretende ser un lenguaje que aporte productividad. 
    * Intenta aproximarse tanto como sea posible al lenguaje natural. 
    * Eventualmente es más verborrágico que Ruby o Groovy    * 
    * No ofrece características de metaprogramación.    
 * TODO
 
