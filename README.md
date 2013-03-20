Pseudo
======

# ¿Qué es Pseudo?

Pseudo es una especificación de un pseudocódigo:
  * orientado a objetos y con características funcionales  
  * diseñado con fines educativos y de modelado a alto nivel
  * en español
 
# ¿Porqué Pseudo es un pseudocódigo y no un lenguaje de programación?

Porque Pseudo no constituye un lenguaje de programación completo. No especifica, entre otras cosas:
  * Una estrategia de evaluación
  * Un sistema de validación de tipos
  * Un plataforma de ejecución
  * Bibliotecas estándares
  * Sistema de espacion de nombres (paquetes)
 
Es decir, un pseudocódigo Pseudo, aún cumpliendo una sintaxis válida, no define de forma completa un programa, sino que solo describe a grandes rasgos un diseño de solución. Aún es necesario bajarlo a un lenguaje de programación para definir los detalles de implementación.

Por otro lado, La sintaxis es flexible en el sentido de que virtualmente cualquier regla sintáctica puede ser quebrada si al hacerlo no se introducen ambiguedades __para un humano__. Por lo cual debe entenderse a la sintaxis de Pseudo como una guía orientativa y no como una especificación rígida. 
 
Moraleja: ¡Pseudo admite ambiguedades! Por eso decimos que Pseudo es una especificación de una sintaxis y semántica __abstracta__ (mas adelante explicaremos porqué) 

# ¿Qué ofrece Pseudo entonces?
  * Una especificación abstracta de una semántica y sintaxis. 
  * Algunas herramientas. Aquí encontrarás un plugin para Eclipse que permite:
    * el coloreo de la sintaxis
    * Navegación
    * Generación de código Java (experimiental), útil para validar un diseño. Es parte de la hoja de ruta extender la generación a Scala y Groovy.

# ¿Si Pseudo admite ambigüedades, como se las bancan las herramientas? 

Las herramientas claramente son menos inteligentes que un humano, por lo que lo que no es ambiguo para un humano, quizás si lo es para la máquina. Entonces, estas herramientas trabajan con un subconjunto de la especificación. Este subconjunto será llamado de ahora en más __sintaxis estricta__, en oposición al superconjunto de __sintaxis flexible__.

En este documento se desarrolla la sintaxis estricta, y algunos ejemplos de sintaxis flexibles que muestra las excepciones sintáticas más comunes. Las mismas puede que sean introducidas a la sintaxis formal de Pseudo en futuras versiones.

# ¿Y para qué quiero Pseudo, cuando ya tengo pseudocódigo tradicional? 
 
Porque la mayoría de los pseudocódigos que se usan son imperativos, mientras que Pseudo está diseñado para soportar el paridgama de objetos, con algunos aditamentos funcionales. 

Por otro lado, el pseodcódigo tradicional imita a lenguajes como Pascal, Basic o C. Pseudo en cambio es un pseudcódigo actualizado a al desarrollo de software en la decáda del 2010, y toma ideas de lenguajes mas modernos, como Ruby, Groovy o Scala. 

# ¿Y para qué formalizar algo que es por definición flexible?

Porque así es más facil comunicar nuestros diseños. Si todos hablamos un mismo idioma, será mas facil que otras personas nos entiendan. 


# Ahora sí, ¿como se escribe?

## Convenciones del documento

 * una expresión entre corchetes ([]) signfica que es opcional
 * una expresion entre mayores y menores significa que allí va otra subexpresión

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


## Constructores

En Pseudo se asume que los constructores ya existen, por lo que no necesitan ser codificados. 

En cuando a su uso, las clases son instanciadas mediante el operador ```nuevo```, pasando argumentos posicionales (en el orden en que fueron definidos los atributos) o etiquetados

### Ejemplo

#### Sintaxis estricta
Suponiendo la siguiente clase:
```
clase Perro
 atributo nombre
 atributo edad
 metodo ladrar
fin
```

Sintaxis posicional:

```
nuevo Perro("Fido", 4)
```

Sintaxis etiquetada:

```
nuevo Perro(nombre = "Fido", edad = 4)
nuevo Perro(edad = 4, nombre = "Fido")
```

## Exepciones

Es posible lanzar excpeciones mediante los operadores ```lanzar``` (que toma una excepción por parámetro),  y ```error``` (que toma un mensaje por parámetro).

### Ejemplo

```
lanzar nueva MiExcepcion

error "ups!"
```




