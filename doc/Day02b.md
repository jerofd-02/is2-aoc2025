# Día 2b - Gift Shop

## Descripción
El reto cambia respecto al apartado a: ya no basta con que un ID tenga un bloque de dígitos repetido **exactamente dos veces** (`"1212"`); ahora es inválido si ese bloque se repite **dos o más veces seguidas** (`"1212"`, `"121212"`, ...).

## Modelado conceptual en UML
<div align="center">
  <img src="../images/Day02.png"/>
</div>

## Qué cambia respecto a la parte A
`Range` e `InvalidIdPattern` se reutilizan sin tocar una línea: ambas clases viven en el paquete compartido `software.aoc.day02` (no en `day02.a` ni `day02.b`), así que la parte B las importa directamente. Como `InvalidIdPattern` encapsula la regla de validación detrás de `matches(long id)`, lo único que cambia es qué regex se le pasa al construirla:
```java
// day02.b.GiftShop
private static final String REPEATED_BLOCK = "^(\\d+)\\1+$";
public static GiftShop create() {
    return new GiftShop(InvalidIdPattern.of(REPEATED_BLOCK));
}
```
frente a la regex de la parte A (`^(\d+)\1$`, exactamente dos veces).

**Test**
[`GiftShopTest`](../src/test/java/software/aoc/day02/b/GiftShopTest.java) tiene exactamente la misma estructura que el de la parte A (mismos casos, mismo input real), cambiando únicamente los resultados esperados.