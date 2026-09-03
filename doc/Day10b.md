# Día 10b - Factory
Ahora los botones ya no togglean luces: cada pulsación **suma 1** a cada contador que el botón afecta. Hay que llevar todos los contadores exactamente a los valores de joltage indicados, con el mínimo número total de pulsaciones. En el ejemplo del enunciado, las tres máquinas requieren `10`, `12` y `11` pulsaciones respectivamente, sumando `33` en total.

## Modelo conceptual en UML
<div style="text-align: center;">
  <img src="../images/Day10b.png"/>
</div>

## El problema como sistema de ecuaciones lineales
Cada botón se modela como una columna de una matriz (con un `1` en cada contador que afecta), cada fila representa un contador, y el vector de joltage objetivo es el término independiente. Modelarlo así hace que la complejidad dependa del **tamaño de la matriz** (número de botones y contadores), no del **valor** de los objetivos de joltage — una diferencia clave frente a cualquier enfoque que explorara estados uno a uno.

## Patrones de diseño
### Reducción de Gauss-Jordan (RREF) con pivoteo parcial
`JoltageSolver.reduceToRref` lleva la matriz aumentada a su forma escalonada reducida por filas. En cada columna se elige como pivote la fila con mayor valor absoluto (`selectPivotRow`), una técnica estándar de pivoteo parcial que mejora la estabilidad numérica del cálculo en coma flotante. El resultado se envuelve en `ReducedSystem`, un value object que agrupa la matriz reducida junto con las columnas pivote y las dimensiones del sistema, evitando pasar esos cuatro datos sueltos de un método a otro.

### Búsqueda acotada solo sobre los grados de libertad reales
Tras la reducción, las columnas se separan en pivote (determinadas por el sistema) y libres (grados de libertad). Solo esas columnas libres se recorren con búsqueda exhaustiva (`searchFreeVariables`), reconstruyendo el resto de variables a partir de cada asignación y descartando las combinaciones que no den como resultado enteros no negativos. La cota de búsqueda no es un valor fijo arbitrario: `upperBoundOnPresses` la calcula como la suma de todos los objetivos de joltage, ya que ninguna solución óptima necesitaría que una variable libre supere ese límite.

### Poda de la búsqueda (branch and bound)
`searchFreeVariables` corta una rama en cuanto la suma parcial de las variables libres ya asignadas iguala o supera el mejor resultado encontrado hasta el momento (`partialSum >= bestSoFar`), sin seguir explorando esa rama. Esto evita recorrer combinaciones que, aunque válidas, nunca podrían mejorar la mejor solución ya conocida.

### Comprobación de consistencia adelantada
`ReducedSystem.hasInconsistentRow()` se evalúa **una sola vez**, antes de iniciar la búsqueda de variables libres, en vez de repetirse en cada combinación explorada. Una fila sin columna pivote y con residuo distinto de cero en la matriz reducida indica que el sistema no tiene solución, independientemente de qué valores tomen las variables libres — comprobarlo por adelantado evita explorar un espacio de búsqueda que ya se sabe inútil.

## Clean Code
- **SRP**: [`Button`](../src/main/java/software/aoc/day10/b/Button.java) solo sabe qué contadores afecta (`affects`); [`JoltageRequirement`](../src/main/java/software/aoc/day10/b/JoltageRequirement.java) solo modela el vector objetivo; [`Machine`](../src/main/java/software/aoc/day10/b/Machine.java) solo agrega requisito y botones, y sabe parsearse desde texto; [`ReducedSystem`](../src/main/java/software/aoc/day10/b/ReducedSystem.java) solo empaqueta el resultado de la reducción y sabe evaluarse a sí mismo (`hasInconsistentRow`); [`JoltageSolver`](../src/main/java/software/aoc/day10/b/JoltageSolver.java) es la única clase responsable de resolver el sistema completo.
- **Sin variables de salida mutables escondidas en parámetros**: `searchFreeVariables` y `evaluateSolution` devuelven directamente el resultado (`long`), combinado con `Math.min` en cada nivel de la recursión, en vez de mutar un array pasado como "referencia de salida".
- **Value object**: `ReducedSystem` agrupa `matrix`, `pivotColumns`, `numRows` y `numCols`, que de otro modo viajarían como cuatro parámetros sueltos entre varios métodos.
- **Sin números mágicos**: La cota de la búsqueda de variables libres se calcula a partir de los propios datos del problema (`upperBoundOnPresses`), en vez de ser una constante arbitraria sin relación con el dominio.
- **Nombres que revelan intención**: `reduceToRref`, `selectPivotRow`, `eliminateColumn`, `freeColumns`, `sumIfValidNonNegativeIntegers` — cada método describe exactamente su paso dentro del algoritmo de álgebra lineal, sin necesitar comentarios.
- **Fail-fast**: `parseRequirement` lanza excepción si no encuentra el bloque de joltage en la línea; `solveMinimumPresses` lanza excepción tanto si el sistema es inconsistente como si ninguna combinación de variables libres produce una solución entera válida, en ambos casos con un mensaje que referencia la máquina afectada.
- **Inmutabilidad en el modelo del dominio**: `Button`, `JoltageRequirement`, `Machine` y [`FactoryManual`](../src/main/java/software/aoc/day10/b/FactoryManual.java) son records inmutables con `List.copyOf` en sus constructores compactos. La mutabilidad se confina deliberadamente a la matriz dentro de `JoltageSolver`, donde el propio algoritmo de eliminación gaussiana exige modificarla en el sitio por eficiencia — mutabilidad controlada y aislada, sin propagarse al resto del dominio.
- **Constante de tolerancia con nombre y alcance visible**: `EPSILON` vive en `JoltageSolver` con visibilidad de paquete, compartida explícitamente con `ReducedSystem` para la comprobación de consistencia, en vez de duplicar el valor de tolerancia en dos sitios distintos.

## Test
[`FactoryManualTest`](../src/test/java/software/aoc/day10/b/FactoryManualTest.java) comprueba, con el ejemplo del enunciado, el mínimo de pulsaciones de cada máquina por separado (`10`, `12`, `11`), la suma total (`33`), y un tercer test valida el resultado con el input real del puzzle.