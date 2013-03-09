Pseudo
======

Pseudo es una especificación de un pseudocódigo:
  * orientado a objetos y con características funcionales  
  * diseñado con fines educativos y de modelado a alto nivel
  * en español
 

Pseudo no constituye un lenguaje de programación completo. No especifica, entre otras cosas:
  * Una estrategia de evaluación
  * Un sistema de validación de tipos
  * Un plataforma de ejecución
  * Bibliotecas estándares
  * Sistema de espacion de nombres (paquetes)
 

Si embargo, para poder validar los diseños, se ofrecen herramientas de generación de código, capaces de procesar un subconjunto del lenguaje y generar código un lenguaje de programación. Actualmente sólo Java está soportado, aunque es parte de la hoja de ruta extenderlo a Scala y Groovy.

# Convenciones del documento

 * una expresión entre corchetes ([]) signfica que es opcional
 * una expresion entre mayores y menores significa que allí va otra subexpresión

# Especificación

A diferencia de la mayoría de los pseudocódigos imperativos, Pseudo está diseñado para soportar el paridgama de objetos.

## Definicion de clase

Pseudo especifica un pseudocódigo OO basado en clases.

```
clase <NombreClase> [hereda <NombreSuperclase>]
  atributo <NombreAtributo1> [: <TipoAtributo1>]
  ...
  atributo <NombreAtributoN> [: <TipoAtributoN>]
  
  metodo <NombreMetodo1> [( NombreParametro1  [: TipoParametro1], ..., NombreParametroN  [: TipoParametroN])
     [<CuerpoDelMetodo>]
  [fin]
  ...
  metodo <NombreMetodoM> [( NombreParametro1  [: TipoParametro1], ..., NombreParametroN  [: TipoParametroN])]
     [<CuerpoDelMetodo>]
  [fin]


[fin]
```

Ejemplos: 
```
clase Persona
   atributo nombre
   atributo edad : Numero
   
   metodo mayor_de_edad : Booleano   
```

## Definición de atributos
## Declaración de métodos
## Definición de métodos
## Sintaxis condicional (if/case)

Pseudo no diferencia entre estructuras condicionales estilo if y case:

```
metodo fibonacci(n) 
  cuando n = 0 entonces
    1 
  cuando n = 1 entonces
    1
  otro caso
    fibonacci(n-1) + fibonacci(n-2)      
  fin
fin
```

## Literales
### Colecciones

 * listas: 
   * ```lista()```
   * ```lista(1, 2, 3)```
 * conjuntos: 
   *  ```conjunto()```
   *  ```conjunto("hola", "hello", "hallo")```
 
### Rangos

 * ```1 hasta 10``` 
 
## Operaciones sobre colecciones
### Mapeo
### Filtrado
### Sumatoria
### Busqueda





## Semánticas


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



