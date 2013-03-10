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

La sintaxis es además flexible en el sentido de que virtualmente cualquier regla sintáctica puede ser quebrada si al hacerlo no se introducen ambiguedades para un humano. Por lo cual debe entenderse a Pseudo como una guía orientativa y no como una especificación rígida. 

En este documento también se desarrollan las excepciones sintáticas más comunes. Las mismas puede que sean introducidas a la sintaxis formal de Pseudo en futuras versiones.

No obstante, sólo las especificaciones que cumplan estrictamente con la sintaxis podrán ser traducidas a código mediante herramientas automatizadas. 

## Definicion de clase

Pseudo especifica un pseudocódigo OO basado en clases.

```
clase <NombreClase> [hereda <NombreSuperclase>]  
  <CuerpoClase>
fin
```

### Ejemplos: 

#### Sintaxis estricta

```
clase Persona
fin
```

#### Sintaxis flexible

En general, se puede omitir el terminador ```fin``` sin introducir ambiguedades
```
clase Persona
```

## Mixins/trait/categorias/extensiones

La noción de componente que no tiene entidad propia pero existe para proveer comportamiento extra a otros objetos o clases es común en la mayoría de los lenguajes modernos. Sin embargo, no es estándar ni tiene una forma estricta de modelado y es al día de hoy un tipo de componente cuya implementación varía mucho entre tecnologías. Pseudo no ofrece una sintaxis estricta para tal, pero si algunas sintaxis flexibles para mixins stateless: 

#### Declaración

```
modulo <NombreModulo>
   provee
   <Definiciones de Métodos que provee>
   
   requiere
   <Declaraciones de métodos que requiere>
```

##### Inclusión

```
clase <NombreClase> [hereda <Padre>] incluye <NombreModulo>
   <CuerpoClase>
fin
```

## Definición de atributos

Dentro del cuerpo de una clase, se pueden definir cero o más atributos de la siguiente forma:

```
  atributo <NombreAtributo> [: <TipoAtributo>]
```

Donde expresar el tipo es opcional. Dado que Pseudo es un pseudocódigo y no código ejecutable, usar tipado implícito vs explícito no significa ninguna diferencia semántica en el modelo ni presume ninguna restricción respecto del sistema de tipos, que podría ser estático o dinámico, inferido, opcional, etc. 

En otras palabras, debe interpretarse el uso de anotaciones de tipos como mera documentación.

### Ejemplos

##### Sintaxis estricta

```
clase Persona
   atributo nombre
   atributo edad : Numero
fin   
```
      
##### Sintaxis flexible

Tipicamente los atributos son definidos todos juntos, entonces se puede definir los mismos de forma mas sucinta

```
clase Persona
   atributos    
   nombre
   edad : Numero
```

Otra forma compacta de especificar los atributos es entre paréntesis al lado del nombre de la clase:

```
clase Persona(nombre, edad : Numero)
```

## Declaración de métodos

Dentro del cuerpo de la clase de la clase:

```
  metodo <NombreMetodo1> [( NombreParametro1  [: TipoParametro1], ..., NombreParametroN  [: TipoParametroN])
     [<CuerpoDelMetodo>]
  fin  
```

Los métodos no necesitan tener cuerpo, ya se porque están implementado para no hacer nada, o porque la implementación no es de interés. (Esto puede introducir en algunos casos ambiguedades, se resolverá en futuras versiones)

### Ejemplos

#### Sintaxis estricta

```
clase Persona
   atributo nombre
   atributo edad : Numero
   
   metodo mayor_edad
       edad > 18
   fin
   
   metodo validar_consistencia
   fin
fin   
```

#### Sintaxis flexible

Nuevamente, el terminado ```fin``` puede ser removido sin introducir ambiguedades. También varios metodos puede ser definidos de forma conjunta

```
clase Persona

   atributos 
   
   nombre
   edad : Numero
   
   metodos
   
   mayor_edad
     edad > 18   
       
   validar_consistencia       
```

Si se usa la sintaxis de atributos entre paréntesis, se puede eliminar el delimitar ```metodos``` sin introducir ambiguedades:

```
clase Persona(nombre, edad : Numero)
   mayor_edad
     edad > 18   
       
   validar_consistencia       
```


## Definición de métodos

## Sintaxis condicional (if/case)

Pseudo no diferencia entre estructuras condicionales estilo if y case.

### Ejemplo

#### Sintaxis estricta

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

#### Sintaxis flexible

Nuevamente, los terminadores ```fin``` a veces pueden ser eliminados sin introducir ambigueadad. Además, si bien Pseudo no especifica ninguna sintaxis de pattern matching, bien puede usarse una expreción ```cuando``` como tal, por ejemplo:

```
fibonacci(n) 
  machear n
  cuando 0 entonces 1 
  cuando 1 entonces 1
  otro caso fibonacci(n-1) + fibonacci(n-2)      
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

Pseudo define sintaxis para transformar colecciones. No especifica, sin embargo, ningún detalle de implementación de la mismas.

### Mapeo

Una estructura ```generar``` sirve para aplicar una transformación a cada elemento de una colección (collect/map). 
Esta estructura espera el mensaje que le será enviado a cada elemento de la colección. 

```
generar <MensajeProyeccion> de <ColeccionOrigen>
```
   
#### Ejemplo

```
generar nombre de personas
```


```
generar distancia_a(buenos_aires) de destinos_turisticos
```
   
   
### Filtrado

```<ColleccionOrigen> tal que <MensajeBooleano>```

#### Ejemplo 

```
personas tal que es_mayor
```
   
   
### Sumatoria/Promedio (sum/avg)

```
sumatoria de edad entre personas
```

   
```
promedio de edad entre personas
```
   
### Primero (head)

```
primero entre personas
```
    
### Primeros N (take)

```
primeros 2 entre personas tal que es_mayor
```

### Busqueda (find)

```
primero entre personas tal que es_mayor
```
   
### Maximo/Minimo (max/min)

```
maximo segun edad entre personas
```

   
```
minimo segun edad entre personas
```

#### Todos/Alguno (all/any)


```
todo satisface es_mayor entre personas
```
   
   
```
alguno satisface es_mayor entre personas
```


#### Combinación 

La sintaxis de filtrado puede ser combinada con cualquiera de las otras transformaciones. 

##### Ejemplos

```
generar nombre de personas tal que es_mayor
```
    
    
```
promedio de distancia_a(buenos_aires)  entre destinos_turisticos tal que tiene_disponibilidad
```

## Orden superior

Pseudo no especifica ninguna sintaxis para bloques. Sin embargo, varias estructuras de control (como las transformaciones de colecciones antes desarrolladas) reciben mensajes.




