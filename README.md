# Tarea 12: Implementación de un Árbol AVL en Java

## 1. Información Académica
* **Institución:** Escuela Politécnica Nacional (EPN)
* **Asignatura:** Estructura de Datos y Algoritmos 1
* **Integrantes:** Adrián Mauricio Trujillo Salazar y Mauro Alexander Valencia Monteros
* **Cédula de prueba utilizada:** 1751416718 (Adrián Trujillo)

---

## 2. Contexto y Relación con la Tarea Anterior

Este proyecto en Java implementa las operaciones fundamentales de un Árbol AVL (Adelson-Velsky y Landis). El objetivo principal del programa es extender la lógica del Árbol Binario de Búsqueda (BST) desarrollado en la tarea anterior, incorporando la capacidad de auto-balanceo estructural mediante rotaciones.

El sistema reutiliza exactamente la misma cédula declarada y el mismo orden de inserción de los valores generados (11, 72, 53, 14, 45, 16, 67, 78, 19, 90). Todas las operaciones ejecutadas por el programa han sido diseñadas para demostrar empíricamente la optimización de las alturas y la reducción del número de comparaciones en operaciones de búsqueda frente a la estructura BST original.

---

## 3. Explicación de Operaciones y Casos Cubiertos

El programa cumple con los siguientes requisitos funcionales sin el uso de librerías externas para la estructura de datos (como `TreeMap` o `TreeSet`):

1. **Construcción y Rebalanceo:** Genera y enlaza los nodos secuencialmente. Tras cada inserción, el sistema evalúa el factor de equilibrio y aplica rotaciones simples (LL, RR) o dobles (LR, RL) de manera automática para corregir cualquier desbalance detectado.
2. **Búsquedas Analíticas:** Rastrea valores específicos, documentando la ruta de nodos visitados y el total de comparaciones lógicas realizadas para evidenciar la mejora en eficiencia de tiempo de ejecución.
3. **Eliminación por Casos con Auto-balanceo:** Cubre los tres escenarios clásicos: nodo hoja, un solo hijo y dos hijos (utilizando el sucesor en inorden). Posterior a la eliminación, el algoritmo recalcula alturas en reversa y aplica las rotaciones de rebalanceo necesarias para devolverle al árbol su propiedad AVL.
4. **Análisis Comparativo:** Genera automáticamente un reporte final que contrasta la altura total, las comparaciones de búsqueda y el número total de rotaciones del antiguo BST frente a la nueva estructura AVL.

---

## 4. Pseudocódigo de las Operaciones Centrales

El siguiente pseudocódigo corresponde de manera exacta a la implementación en Java entregada. Se detalla la lógica recursiva implementada para la gestión estricta del equilibrio del árbol.

### A. Operación: Obtener Factor de Equilibrio
```text
FUNCION obtenerFactorEquilibrio(nodo)
    SI nodo ES NULO ENTONCES
        RETORNAR 0
    FIN SI
    RETORNAR Altura(nodo.izquierdo) - Altura(nodo.derecho)
FIN FUNCION

```

### B. Operación: Rotaciones (Simples y Dobles)

```text
FUNCION rotacionDerecha(y)
    x = y.izquierdo
    T2 = x.derecho
    
    x.derecho = y
    y.izquierdo = T2
    
    Actualizar Altura(y)
    Actualizar Altura(x)
    
    RETORNAR x
FIN FUNCION

FUNCION rotacionIzquierda(x)
    y = x.derecho
    T2 = y.izquierdo
    
    y.izquierdo = x
    x.derecho = T2
    
    Actualizar Altura(x)
    Actualizar Altura(y)
    
    RETORNAR y
FIN FUNCION

```

### C. Operación: Insertar con Rebalanceo Automático

```text
FUNCION insertar(nodo, valor)
    SI nodo ES NULO ENTONCES
        RETORNAR NuevoNodo(valor)
    FIN SI
    
    SI valor < nodo.valor ENTONCES
        nodo.izquierdo = insertar(nodo.izquierdo, valor)
    SINO SI valor > nodo.valor ENTONCES
        nodo.derecho = insertar(nodo.derecho, valor)
    SINO
        RETORNAR nodo
    FIN SI
    
    Actualizar Altura(nodo)
    balance = obtenerFactorEquilibrio(nodo)
    
    SI balance > 1 Y valor < nodo.izquierdo.valor ENTONCES
        RETORNAR rotacionDerecha(nodo) // Caso LL
    FIN SI
    SI balance < -1 Y valor > nodo.derecho.valor ENTONCES
        RETORNAR rotacionIzquierda(nodo) // Caso RR
    FIN SI
    SI balance > 1 Y valor > nodo.izquierdo.valor ENTONCES
        nodo.izquierdo = rotacionIzquierda(nodo.izquierdo)
        RETORNAR rotacionDerecha(nodo) // Caso LR
    FIN SI
    SI balance < -1 Y valor < nodo.derecho.valor ENTONCES
        nodo.derecho = rotacionDerecha(nodo.derecho)
        RETORNAR rotacionIzquierda(nodo) // Caso RL
    FIN SI
    
    RETORNAR nodo
FIN FUNCION

```

### D. Operación: Eliminar con Rebalanceo

```text
FUNCION eliminar(nodo, valor)
    SI nodo ES NULO ENTONCES
        RETORNAR nodo
    FIN SI
    
    SI valor < nodo.valor ENTONCES
        nodo.izquierdo = eliminar(nodo.izquierdo, valor)
    SINO SI valor > nodo.valor ENTONCES
        nodo.derecho = eliminar(nodo.derecho, valor)
    SINO
        SI nodo.izquierdo ES NULO O nodo.derecho ES NULO ENTONCES
            temp = NULO
            SI nodo.izquierdo NO ES NULO ENTONCES temp = nodo.izquierdo
            SINO temp = nodo.derecho
            
            SI temp ES NULO ENTONCES // Caso 1: Hoja
                nodo = NULO
            SINO // Caso 2: Un hijo
                nodo = temp
            FIN SI
        SINO // Caso 3: Dos hijos
            temp = ObtenerMinimo(nodo.derecho)
            nodo.valor = temp.valor
            nodo.derecho = eliminar(nodo.derecho, temp.valor)
        FIN SI
    FIN SI
    
    SI nodo ES NULO ENTONCES
        RETORNAR nodo
    FIN SI
    
    Actualizar Altura(nodo)
    balance = obtenerFactorEquilibrio(nodo)
    
    SI balance > 1 Y obtenerFactorEquilibrio(nodo.izquierdo) >= 0 ENTONCES
        RETORNAR rotacionDerecha(nodo)
    FIN SI
    SI balance > 1 Y obtenerFactorEquilibrio(nodo.izquierdo) < 0 ENTONCES
        nodo.izquierdo = rotacionIzquierda(nodo.izquierdo)
        RETORNAR rotacionDerecha(nodo)
    FIN SI
    SI balance < -1 Y obtenerFactorEquilibrio(nodo.derecho) <= 0 ENTONCES
        RETORNAR rotacionIzquierda(nodo)
    FIN SI
    SI balance < -1 Y obtenerFactorEquilibrio(nodo.derecho) > 0 ENTONCES
        nodo.derecho = rotacionDerecha(nodo.derecho)
        RETORNAR rotacionIzquierda(nodo)
    FIN SI
    
    RETORNAR nodo
FIN FUNCION

```

### E. Operación: Recorridos del Árbol

```text
FUNCION imprimirInorden(nodo)
    SI nodo NO ES NULO ENTONCES
        imprimirInorden(nodo.izquierdo)
        IMPRIMIR nodo.valor
        imprimirInorden(nodo.derecho)
    FIN SI
FIN FUNCION

FUNCION imprimirPreorden(nodo)
    SI nodo NO ES NULO ENTONCES
        IMPRIMIR nodo.valor
        imprimirPreorden(nodo.izquierdo)
        imprimirPreorden(nodo.derecho)
    FIN SI
FIN FUNCION

FUNCION imprimirPostorden(nodo)
    SI nodo NO ES NULO ENTONCES
        imprimirPostorden(nodo.izquierdo)
        imprimirPostorden(nodo.derecho)
        IMPRIMIR nodo.valor
    FIN SI
FIN FUNCION

```

---

## 5. Instrucciones de Compilación y Ejecución

El proyecto está diseñado para compilarse sin dependencias de un entorno de desarrollo (IDE). Desde la consola de comandos o terminal, ubíquese en la raíz del proyecto (donde se encuentra la carpeta `src`) y ejecute los siguientes comandos:

**Para compilar los archivos:**

```bash
mkdir bin
javac src/*.java -d bin

```

**Para ejecutar el programa:**

```bash
java -cp bin Main

```

---

## 6. Resultados y Análisis Comparativo: BST vs AVL

Se procesó la misma secuencia de inserción utilizada en la tarea anterior, con los valores generados a partir de la cédula: 11, 72, 53, 14, 45, 16, 67, 78, 19, 90.

| Métrica Evaluada | Árbol BST (Tarea Anterior) | Árbol AVL (Tarea Actual) |
| --- | --- | --- |
| **Altura Final del Árbol** | 7 | 4 |
| **Comparaciones (Buscar V10 = 90)** | 4 | 3 |
| **Total de Rotaciones (Construcción)** | 0 | 5 (2 RR, 1 LR, 2 RL) |

**Análisis de Construcción y Eficiencia:**

Durante el proceso de inserción secuencial de los valores generados, se pudo observar el comportamiento degenerativo del Árbol Binario de Búsqueda (BST) tradicional. Dado que los datos ingresaron con un marcado sesgo hacia la derecha y luego en cascada hacia la izquierda, el BST careció de mecanismos para corregir la inclinación de las ramas, resultando en una estructura profunda con una altura total de 7 niveles. Esta profundidad excesiva penaliza el rendimiento y compromete su complejidad logarítmica esperada.

Por el contrario, la implementación del Árbol AVL demostró empíricamente su capacidad de autoequilibrio. A medida que se insertaban los valores, el algoritmo detectaba desbalances mediante el cálculo constante del factor de equilibrio en cada nodo. Durante la construcción completa del árbol, se produjeron exactamente 5 rotaciones de rebalanceo (2 de tipo RR, 1 de tipo LR y 2 de tipo RL). Al aplicar estas rotaciones en tiempo de ejecución, el árbol logró reorganizar su topología dinámica, logrando comprimir su altura máxima a tan solo 4 niveles y manteniendo sus subárboles simétricos.

Esta sustancial reducción de altura tuvo un impacto directo y cuantificable en la eficiencia del proceso de búsqueda. Al buscar el valor V10 (90), el árbol BST requirió recorrer 4 nodos distintos secuencialmente para hallar el dato. En contraste, la estructura balanceada del AVL permitió encontrar el mismo valor realizando únicamente 3 comparaciones. Esto confirma prácticamente que el coste de procesamiento adicional exigido por las rotaciones de mantenimiento se compensa ampliamente al brindar tiempos de respuesta ágiles y consistentes.

---

## 7. Enlace al video de YouTube

A continuación se adjunta el enlace al video explicativo sobre la Tarea 12: Implementación de Árboles AVL. En esta grabación demostramos la ejecución en vivo del sistema, explicando a detalle la lógica detrás de las operaciones de inserción, la evaluación de desbalances, la aplicación de los cuatro tipos de rotaciones y la eliminación de la raíz con rebalanceo, cerrando con la comparativa final de alturas.

* **Sustentación del proyecto:** [https://youtu.be/9MSXXsGCYrI](https://youtu.be/9MSXXsGCYrI)

---

## 8. Enlace a Diagramas (Figma)

Para el diseño y la construcción visual comparativa de los árboles binarios generados en este taller, utilizamos la herramienta digital Figma.

En cumplimiento con las instrucciones de la guía de la asignatura, a continuación se adjunta el enlace fuente compartido con los permisos de edición vigentes para su respectiva revisión y verificación técnica:

* **Enlace editable del proyecto:** [https://www.figma.com/board/UjgTSyjlyBP3LSqcJt2eaj/%C3%81RBOLES-DE-LA-TAREA-EN-CLASE-11?node-id=0-1&t=2RfiAj0NpO5uZAux-1](https://www.figma.com/board/UjgTSyjlyBP3LSqcJt2eaj/%C3%81RBOLES-DE-LA-TAREA-EN-CLASE-11?node-id=0-1&t=2RfiAj0NpO5uZAux-1)

---

## 9. Declaración Obligatoria de Integridad Académica y Uso de IA

Nosotros, los integrantes del grupo, declaramos bajo compromiso de estricta integridad académica que el código fuente presentado en este repositorio es fruto de nuestro trabajo intelectual. Hemos estructurado, analizado y desarrollado algorítmicamente la lógica del rebalanceo sin emplear librerías predefinidas de Java como `TreeMap` o `TreeSet`.

Se utilizó Inteligencia Artificial (IA) exclusivamente como herramienta de soporte autorizado: para interpretar de manera ágil los mensajes de error arrojados por el compilador, para dar formato a este documento en lenguaje Markdown y para validar dudas teóricas específicas sobre la actualización de referencias de punteros durante escenarios complejos de eliminación con doble rotación. Ratificamos formalmente que no se empleó IA para generar el pseudocódigo, escribir la lógica principal de las clases ni redactar el guion del entregable audiovisual, y garantizamos que el video fue grabado en vivo por los integrantes evidenciando nuestro dominio técnico.

---

## 10. Enlace del Repositorio Oficial

Para revisar el código fuente en línea y el historial de versiones, puede acceder al repositorio público mediante el siguiente enlace:

* **Repositorio en GitHub:** [https://github.com/Mv05007/Tarea_S12-Arboles_AVL](https://github.com/Mv05007/Tarea_S12-Arboles_AVL)

---

## 11. Rúbrica de Evaluación

| Criterio de Evaluación | Puntos |
| --- | --- |
| Pseudocódigo (insertar, eliminar, rotaciones) entregado antes del código y consistente con la implementación | 15 |
| Reutilización correcta de la cédula y los valores generados en la tarea anterior | 5 |
| Inserción con rebalanceo automático correcto (los 4 casos: LL, RR, LR, RL) | 15 |
| Recorridos (inorden, preorden, postorden) correctos | 5 |
| Búsqueda con conteo de comparaciones | 10 |
| Eliminación correcta en los tres casos (hoja, un hijo, dos hijos) con rebalanceo posterior | 15 |
| Comparación explícita BST vs. AVL (altura, comparaciones, número de rotaciones) | 10 |
| Calidad del código (clases separadas, nombres claros, comentarios, compila con javac) | 10 |
| Video explicativo (requisitos de la sección 5) | 10 |
| Declaración de uso de IA y portada completa | 5 |
| **Total** | **100** |

```
