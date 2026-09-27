# Advent of Code 2025 — Ingeniería del Software II

Soluciones a los problemas planteados por el [Advent of Code 2025](https://adventofcode.com/2025/) para la asignatura Ingeniería del Software II.

## Objetivo de la práctica
El objetivo no es solo resolver cada puzzle, sino hacerlo aplicando principios de **Clean Code** y **patrones de diseño** de forma consciente y justificada: nombres significativos, funciones pequeñas con una única responsabilidad, ausencia de duplicación, bajo acoplamiento, inmutabilidad donde corresponde, y elegir en cada caso el patrón (Strategy, Builder, Composite, Visitor, Factory Method, Union-Find, backtracking...) que mejor encaje con la naturaleza del problema, en vez de forzar una solución genérica. Cada día del calendario tiene, además de su código, una documentación (`doc/`) que explica el diseño elegido y por qué.

## Dependencias y versión de JDK
| | |
|---|---|
| **JDK** | 26 (`maven.compiler.source`/`target` = `26` en `pom.xml`) |
| **Build tool** | Maven |
| **Test framework** | JUnit 4.13.2 |
| **Aserciones** | AssertJ 3.27.7 |

Ambas dependencias tienen `scope=test`: el código de producción (`src/main/java`) no depende de ninguna librería externa, solo de la biblioteca estándar de Java.

## Características generales del código
Más allá del patrón concreto de cada día (ver la tabla de abajo), hay un conjunto de convenciones que se repiten en las 24 soluciones:
 
- **Value Objects inmutables con `record`**: prácticamente todo dato del dominio (`Position`, `Range`, `JunctionBox`, `Equation`, `Rectangle`...) se modela como `record` de Java. Los que envuelven colecciones copian defensivamente en su constructor compacto (`List.copyOf(...)`, `Map.copyOf(...)`, `Set.copyOf(...)`), así que ninguna instancia puede mutar después de construida ni compartir por referencia una colección con quien la construyó.
- **Static Factory Methods como puerta de entrada**: casi ninguna clase se instancia con `new` desde fuera de su propio paquete; en su lugar exponen constructores con nombre (`from(...)`, `of(...)`, `create(...)`, `between(...)`) que documentan, con el propio nombre, a partir de qué se construye el objeto.
- **Streams declarativos frente a bucles con acumuladores**: la inmensa mayoría del código de transformación de datos usa `stream()`/`mapToLong()`/`reduce()`/`filter()` en vez de bucles `for` con variables mutables reasignadas, salvo en los sitios donde el propio algoritmo exige mutación explícita y acotada (el backtracking del día 12, o la eliminación de Gauss-Jordan del día 10b).
- **Paquete compartido vs. paquete `a`/`b`**: cuando una clase de dominio no cambia de comportamiento entre partes (`Position`, `Bank`, `CircuitDiagram`, `JoltageCalculator`...) vive en el paquete raíz del día y ambas partes la reutilizan sin duplicarla; cuando sí cambia, cada parte mantiene su propia versión. Este criterio no siempre se aplicó con la misma disciplina en las 12 soluciones (algún día conserva una clase duplicada entre `a` y `b` a propósito, por autocontención), y queda anotado explícitamente en el `doc/` correspondiente cuando ocurre.
- **Fail-fast con mensajes descriptivos**: los puntos de parseo o de búsqueda de un elemento que podría no existir (un operador desconocido, una posición de inicio no encontrada, una red que nunca se conecta del todo) lanzan excepciones no comprobadas (`IllegalArgumentException`, `IllegalStateException`) con un mensaje que dice exactamente qué se esperaba encontrar, en vez de devolver `null`, `-1` o `0` y dejar que el fallo aparezca más tarde y más lejos de su causa.
- **Sin dependencias externas en producción**: `src/main/java` no importa ninguna librería de terceros — solo la biblioteca estándar de Java (records, streams, `java.util`). JUnit y AssertJ están declarados con `scope=test` y solo se usan para verificar el comportamiento, no para implementarlo.
- **Java moderno de forma consistente**: uso extensivo de `record`, *text blocks* (`"""..."""`) en los tests para los inputs de ejemplo, `switch` sobre patrones donde aplica, e inferencia de tipos — no hay código con estilo pre-Java 8 conviviendo con el resto.

## Estructura del código
El proyecto sigue un patrón consistente en los 12 días:
```
src/main/java/software/aoc/dayNN/
├── *.java          ← clases de dominio compartidas entre parte A y B
│                      (cuando no hay diferencia de comportamiento entre partes)
├── a/*.java         ← solución específica de la parte A
└── b/*.java         ← solución específica de la parte B

src/test/java/software/aoc/dayNN/
├── a/*Test.java     ← tests de la parte A
└── b/*Test.java     ← tests de la parte B

doc/DayNNa.md         ← diseño y razonamiento de la parte A
doc/DayNNb.md         ← diseño y razonamiento de la parte B (solo el delta respecto a A)
images/DayNNa.png      ← diagrama UML de la parte A
images/DayNNb.png      ← diagrama UML de la parte B
```
Cuando una clase de dominio (por ejemplo `Position`, `Bank`, `CircuitDiagram`) no cambia de comportamiento entre la parte A y la B, vive directamente en el paquete raíz del día (`dayNN/`) y ambas partes la importan sin duplicarla. Cuando sí cambia (el algoritmo, la estructura de datos, o la regla de negocio), cada parte tiene su propia versión en `dayNN/a/` o `dayNN/b/`.

## Índice de soluciones
| Día | Enunciado | Código | Test | Doc | Patrones / técnicas |
|---|---|---|---|---|---|
| 1a | Secret Entrance | [`Código`](src/main/java/software/aoc/day01) | [`Test`](src/test/java/software/aoc/day01/a/DialTest.java) | [Doc](doc/Day01a.md) | Static Factory Method, Fluent Interface, Immutable Value Object |
| 1b | Secret Entrance | [`Código`](src/main/java/software/aoc/day01) | [`Tests`](src/test/java/software/aoc/day01/b) | [Doc](doc/Day01b.md) | Extracción de Value Object (`Cycle`) para aritmética modular reutilizable |
| 2a | Gift Shop | [`Código`](src/main/java/software/aoc/day02) | [`Test`](src/test/java/software/aoc/day02/a/GiftShopTest.java) | [Doc](doc/Day02a.md) | Strategy, Static Factory Method, Fluent Interface, Immutable Value Object |
| 2b | Gift Shop | [`Código`](src/main/java/software/aoc/day02) | [`Test`](src/test/java/software/aoc/day02/b/GiftShopTest.java) | [Doc](doc/Day02b.md) | Reutilización del mismo Strategy con otra regex inyectada |
| 3a | Lobby | [`Código`](src/main/java/software/aoc/day03) | [`Test`](src/test/java/software/aoc/day03/a/EscalatorTest.java) | [Doc](doc/Day03a.md) | Strategy + Dependency Inversion, Factory Method, Fluent Interface, Inmutabilidad persistente |
| 3b | Lobby | [`Código`](src/main/java/software/aoc/day03) | [`Test`](src/test/java/software/aoc/day03/b/EscalatorTest.java) | [Doc](doc/Day03b.md) | Generalización del algoritmo (pila monótona) tras el mismo Strategy |
| 4a | Printing Department | [`Código`](src/main/java/software/aoc/day04) | [`Test`](src/test/java/software/aoc/day04/a/GridTest.java) | [Doc](doc/Day04a.md) | Iterator (`Neighbors`) |
| 4b | Printing Department | [`Código`](src/main/java/software/aoc/day04) | [`Tests`](src/test/java/software/aoc/day04/b/) | [Doc](doc/Day04b.md) | SRP entre estado (`Grid`) y proceso iterativo (`Forklifts`), inmutabilidad persistente |
| 5a | Cafeteria | [`Código`](src/main/java/software/aoc/day05) | [`Test`](src/test/java/software/aoc/day05/a/InventoryTest.java) | [Doc](doc/Day05a.md) | Composite, Factory Method |
| 5b | Cafeteria | [`Código`](src/main/java/software/aoc/day05) | [`Test`](src/test/java/software/aoc/day05/b/InventoryTest.java) | [Doc](doc/Day05b.md) | Visitor sobre el mismo Composite |
| 6a | Trash Compactor | [`Código`](src/main/java/software/aoc/day06) | [`Test`](src/test/java/software/aoc/day06/a/WorksheetTest.java) | [Doc](doc/Day06a.md) | Builder, Strategy (enum con comportamiento), Factory Method |
| 6b | Trash Compactor | [`Código`](src/main/java/software/aoc/day06) | [`Test`](src/test/java/software/aoc/day06/b/WorksheetTest.java) | [Doc](doc/Day06b.md) | Extract Class (`WorksheetParser`), Value Object (`ColumnRange`) |
| 7a | Laboratories | [`Código`](src/main/java/software/aoc/day07) | [`Test`](src/test/java/software/aoc/day07/a/TachyonManifoldTest.java) | [Doc](doc/Day07a.md) | Factory Method, Immutable Value Object, Fold/Reduce |
| 7b | Laboratories | [`Código`](src/main/java/software/aoc/day07) | [`Test`](src/test/java/software/aoc/day07/b/QuantumManifoldTest.java) | [Doc](doc/Day07b.md) | Mismo Fold/Reduce sobre `Map` en vez de `Set` (conteo en vez de presencia) |
| 8a | Playground | [`Código`](src/main/java/software/aoc/day08) | [`Test`](src/test/java/software/aoc/day08/a/JunctionBoxNetworkTest.java) | [Doc](doc/Day08a.md) | Union-Find (Disjoint Set) persistente e inmutable |
| 8b | Playground | [`Código`](src/main/java/software/aoc/day08) | [`Test`](src/test/java/software/aoc/day08/b/JunctionBoxNetworkTest.java) | [Doc](doc/Day08b.md) | Mismo Union-Find con criterio de parada estilo Kruskal, Value Object (`LastConnection`) |
| 9a | Movie Theater | [`Código`](src/main/java/software/aoc/day09) | [`Test`](src/test/java/software/aoc/day09/a/MovieTheaterFloorTest.java) | [Doc](doc/Day09a.md) | Generar candidatos + seleccionar el mejor, Factory Method |
| 9b | Movie Theater | [`Código`](src/main/java/software/aoc/day09) | [`Test`](src/test/java/software/aoc/day09/b/MovieTheaterFloorTest.java) | [Doc](doc/Day09b.md) | Specification (`Polygon::contains` como filtro) |
| 10a | Factory | [`Código`](src/main/java/software/aoc/day10) | [`Test`](src/test/java/software/aoc/day10/a/FactoryManualTest.java) | [Doc](doc/Day10a.md) | BFS sobre grafo de estados, botones como Command, estado como bitmask |
| 10b | Factory | [`Código`](src/main/java/software/aoc/day10) | [`Test`](src/test/java/software/aoc/day10/b/FactoryManualTest.java) | [Doc](doc/Day10b.md) | Álgebra lineal (Gauss-Jordan), Value Object (`ReducedSystem`), Parameter Object (`SearchContext`) |
| 11a | Reactor | [`Código`](src/main/java/software/aoc/day11) | [`Test`](src/test/java/software/aoc/day11/a/ReactorNetworkTest.java) | [Doc](doc/Day11a.md) | Recursión con memoización sobre un DAG |
| 11b | Reactor | [`Código`](src/main/java/software/aoc/day11) | [`Test`](src/test/java/software/aoc/day11/b/ReactorNetworkTest.java) | [Doc](doc/Day11b.md) | Memoización con clave ampliada, Value Object (`VisitedRequiredNodes`) |
| 12a | Christmas Tree Farm | [`Código`](src/main/java/software/aoc/day12/a) | [`Test`](src/test/java/software/aoc/day12/a/ChristmasTreeFarmTest.java) | [Doc](doc/Day12a.md) | Backtracking (estilo exact-cover) con poda por presupuesto de área |