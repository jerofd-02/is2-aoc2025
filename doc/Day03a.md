# Día 3a - Lobby
Solución al ejercicio "Lobby": dado un conjunto de bancos de baterías (cada uno una cadena de dígitos), encender exactamente **k** baterías por banco —preservando su orden— para maximizar el joltage que produce ese banco, y sumar el resultado de todos los bancos.

## Modelado conceptual en UML
<div style="text-align: center;">
  <img src="../images/Day03.png"/>
</div>

## Diseño y patrones aplicados
### Factory Method — `Bank.from(...)`, `Escalator.create()` / `create(JoltageCalculator)`
Se evita exponer `new` directamente al código cliente:
```java
public static Bank from(String line) { ... }
public static Escalator create() { ... }
public static Escalator create(JoltageCalculator joltage) { ... }
```

`create()` fija una configuración por defecto (la regla vigente, `k` dígitos) en un único sitio; `create(JoltageCalculator)` permite inyectar una regla distinta sin tocar [`Escalator`](../src/main/java/software/aoc/day03/a/Escalator.java) ni [`Bank`](../src/main/java/software/aoc/day03/a/Bank.java) — solo cambia el `k` que recibe [`JoltageCalculator`](../src/main/java/software/aoc/day03/a/JoltageCalculator.java).

### Parametrización por inyección — `Escalator.create(JoltageCalculator)`
`Escalator` no crea su `JoltageCalculator` internamente; lo recibe por constructor o por el *factory method* `create(JoltageCalculator joltage)`. Aunque en esta versión solo existe una implementación (la regla de "dos dígitos"), la clase ya está preparada para recibir otra sin que `Escalator` ni `Bank` necesiten cambiar - solo haría falta otra clase que se le pase en lugar de `JoltageCalculator`.

### Fluent Interface — `Escalator`
`add(...)` y `execute(...)` devuelven `Escalator`, permitiendo encadenar llamadas sin variables intermedias:
```java
Escalator.create().add("987654321111111").totalOutputJoltage();
Escalator.create(new JoltageCalculator()).execute(input).totalOutputJoltage();
```

### Inyección de dependencias por constructor
`Escalator` recibe su `JoltageCalculator` desde fuera en lugar de crearlo internamente, desacoplando la orquestación de la regla concreta de cálculo y haciendo la clase testeable con cualquier configuración de `k`.

## Clean Code
- **Single Responsibility Principle**: Cada clase cambia por un único motivo.
  - `JoltageCalculator`: Cambia si cambia el *algoritmo* para elegir los mejores dígitos.
  - `Bank`: Cambia si cambia cómo se *parsea* una línea de input.
  - `Escalator`: Cambia si cambia cómo se *orquesta* el input o se *agrega* el resultado total.
- **Un único nivel de abstracción por método**: `JoltageCalculator.compute(...)` no mezcla "cómo se precalcula el mejor dígito a la derecha de cada posición" con "cómo se combina esa información para obtener el mejor valor de dos dígitos" - cada paso vive en su propio método privado (`maxDigitAfterEachPosition`, `bestTwoDigitValue`, `digitAt`), cada uno con un nombre que explica su intención sin necesidad de comentarios.
- **Nombres que revelan intención**: `maxDigitAfterEachPosition`, `bestTwoDigitValue`, `digitAt`, `totalOutputJoltage` - se entiende qué hace cada pieza solo con leer su firma.
- **Inmutabilidad en el dominio**: `Bank` es un `record` — no puede cambiar de estado tras construirse, y no expone forma alguna de mutar sus datos.
- **Composición sobre condicionales**: No hay ningún `if (parte == "A")` en `Escalator` ni en `Bank`. La diferencia de comportamiento entre partes del ejercicio vive únicamente en qué `JoltageCalculator` se inyecta.
- **Algoritmo lineal y descompuesto en pasos con nombre**: En vez de comparar por fuerza bruta todos los pares de posiciones (`O(n²)`), se precalcula en una sola pasada, de derecha a izquierda, el mayor dígito que queda a la derecha de cada posición (`maxDigitAfterEachPosition`). Con eso, encontrar la mejor combinación de dos dígitos es una segunda pasada lineal (`bestTwoDigitValue`) — el algoritmo completo es `O(n)` y cada paso tiene su propio método con un nombre que explica qué calcula, sin necesidad de comentarios.
- **Construcción controlada**: El constructor de `Escalator` es `private`; la única forma de obtener una instancia desde fuera es `create()` o `create(JoltageCalculator)`, evitando estados inválidos o inconsistentes construidos a mano.
- **Copia defensiva**: El constructor guarda `List.copyOf(banks)` en vez de la referencia recibida tal cual. Así, aunque alguien conserve una referencia mutable a la lista original y la modifique después, el estado interno de `Escalator` permanece intacto.

## Tests
[`EscalatorTest`](../src/test/java/software/aoc/day03/a/EscalatorTest.java) cubre:
1. Bancos individuales con la regla vigente (`given_a_bank_should_find_max_joltage`).
2. El ejemplo completo del enunciado (`sum_total_output_joltage`).
3. El input real del ejercicio, leído como recurso (`reward`).