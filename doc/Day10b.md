# Día 10b - Factory
Cada botón, al pulsarse, incrementa en uno un subconjunto fijo de contadores (no luces). Hay que alcanzar exactamente el valor objetivo de **todos** los contadores a la vez, minimizando el número total de pulsaciones. A diferencia de un panel binario, aquí cada contador puede necesitar cualquier valor entero no negativo, y varios botones pueden pulsarse más de una vez.

## Modelo conceptual en UML
<div align="center">
  <img src="../images/Day10b.png"/>
</div>

## Diseño y patrones aplicados
### El problema es álgebra lineal, no un grafo — `JoltageSolver`
Cada botón aporta un vector de incrementos (1 en los contadores que afecta, 0 en el resto), y el objetivo es un vector de valores exactos. Encontrar cuántas veces pulsar cada botón para llegar exactamente al objetivo es, literalmente, resolver un sistema de ecuaciones lineales: `A·x = b`, donde `A` son los botones, `x` las pulsaciones de cada uno (las incógnitas) y `b` el objetivo. Eso descarta por completo un recorrido por estados: el espacio de posibles conteos de pulsaciones no está acotado a priori, así que no hay un grafo finito razonable que explorar.

[`JoltageSolver`](../src/main/java/software/aoc/day10/b/JoltageSolver.java) resuelve el sistema en dos fases:
1. **Eliminación de Gauss-Jordan** (`reduceToRref`) para llevar la matriz aumentada a su forma escalonada reducida, identificando qué columnas quedan como variables *pivote* (con solución determinada por las demás) y cuáles como variables *libres* (pueden tomar cualquier valor válido).
2. **Búsqueda acotada sobre las variables libres** (`searchFreeVariables`), porque el sistema puede tener infinitas soluciones reales pero el enunciado exige *pulsaciones enteras no negativas* — una restricción que el álgebra lineal por sí sola no impone. Para cada combinación de valores enteros de las variables libres, se despejan las pivote y se descarta la combinación si algún valor resultante no es entero o es negativo, quedándose con la de menor suma total.

### El resultado de la eliminación como Value Object — `ReducedSystem`
En vez de que `JoltageSolver` devuelva varios arrays sueltos (la matriz reducida, qué columnas son pivote, dimensiones) desde `reduceToRref`, esos datos viajan juntos en un único objeto inmutable, [`ReducedSystem`](../src/main/java/software/aoc/day10/b/ReducedSystem.java), que además expone su propia consulta de dominio (`hasInconsistentRow()`) en vez de que quien lo reciba tenga que inspeccionar la matriz a mano para saber si el sistema no tiene solución.

### Mutabilidad aislada al proceso de eliminación
Gauss-Jordan es, por naturaleza, un algoritmo que muta una matriz paso a paso (intercambiar filas, escalar, eliminar columnas). Esa mutabilidad se confina a `buildAugmentedMatrix`, `selectPivotRow`, `swapRows` y `eliminateColumn` — todos privados — y nunca sale de `reduceToRref`: lo único que entra al resto del código es el `ReducedSystem` ya inmutable que resulta al final. Ningún otro método de la clase, ni nada externo, ve la matriz a medio reducir.

### Parameter Object para la búsqueda recursiva — `SearchContext`
`freeColumns`, `system` y el límite superior de cada variable libre no cambian entre llamadas recursivas de `searchFreeVariables` — solo cambian `freeIndex`, `freeValues` y `bestSoFar`, que sí representan el progreso de la búsqueda. Esos tres datos invariantes viajan juntos en un único record privado:
```java
private record SearchContext(List<Integer> freeColumns, ReducedSystem system, long upperBound) {
}
```
`upperBoundOnPresses()` se calcula una única vez, al construir el `SearchContext` en `solveMinimumPresses()`, y ese valor —no un nuevo cálculo— es el que usa el bucle de `searchFreeVariables` en cada nivel de la recursión.

## Clean Code
- **Un algoritmo por fase, con nombre**: `buildAugmentedMatrix`, `selectPivotRow`, `swapRows`, `eliminateColumn` — cada paso clásico de Gauss-Jordan tiene su propio método en vez de aparecer todo entremezclado dentro de un único bucle largo.
- **Poda explícita en la búsqueda**: `searchFreeVariables` corta una rama en cuanto la suma parcial ya iguala o supera la mejor solución encontrada hasta el momento (`partialSum >= bestSoFar`), en vez de generar y descartar después todas las combinaciones completas.
- **Tolerancia explícita para comparar reales**: la constante `EPSILON` deja constancia, con nombre, de que comparar `double` contra cero de forma exacta no es seguro — evita que ese `1e-9` aparezca como un número mágico sin explicación en medio de `hasInconsistentRow()`.
- **Parámetros que viajan juntos, agrupados**: `searchFreeVariables` y `evaluateSolution` reciben `SearchContext` en vez de sus tres campos sueltos — la firma de cada método deja claro, de un vistazo, qué es "estado de la búsqueda" (`freeIndex`, `freeValues`, `bestSoFar`) y qué es "datos fijos del problema" (todo lo que vive dentro de `context`).

## Tests
[`FactoryManualTest`](../src/test/java/software/aoc/day10/b/FactoryManualTest.java) comprueba, con el ejemplo del enunciado, el mínimo de pulsaciones de cada máquina y la suma total; un tercer test valida el resultado con el input real del puzzle. Ningún test cubre `JoltageSolver` ni `ReducedSystem` de forma aislada — solo se ejercitan indirectamente a través de `Machine.minPresses()`.