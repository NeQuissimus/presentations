# [fit] Java 8
## [fit] Streams and functions

---

# Lambdas

- Adhoc definition of functions
- Single-use
- Intended to be short
- Basic building block for function programming
- Captured variables need to be final
- Required for lazy evaluation in Java

---

# Lambdas

```java
  // Function that can double an Integer
  Function<Integer, Integer> double = i -> i * 2;

  IntStream stream = IntStream.range(0, 200);
  IntStream doubleEvens = stream.filter(i -> i % 2 == 0).map(i -> i * 2);
```

---

# Exceptions

- No checked exceptions inside lambdas

```java
  i -> { throw new Exception("error"); } // will not compile
```
- RuntimeExceptions are permitted[^1]

```java
  i -> { throw new RuntimeException("works"); } // compiles
```

[^1]: There are usually better ways of error handling with functional programming. Throwing an Exception always breaks referential transparency.

---

# Optional

- Optional value wrapper
- No more NullPointerExceptions!

```java
  // From an "unknown" stream, grab the first number > 1000 if one exists
  IntStream stream = ...
  Optional<Integer> o = stream.filter(i -> i > 1000).findFirst();
  // o is either Optional[i] or Optional.empty at this point
  // Triple number or return 42
  Integer num = o.map(i -> i * 3).orElseGet(() -> 42);
```

---

# JDK
## java.util.function

- Predicate, Consumer, Supplier, Function, UnaryOperation, BinaryOperation
- Runnable (java.lang)
- Examples
- Optional and lambdas
- Debuggers and lambdas

---

# Streams and collections
## Concepts

- Not a data structure
- Designed with lambdas
- No indexed access
- Output into lists/sets
- lazy access
- parallelizable

--

# Streams and collections

- Get streams using Stream.of, Collection.stream(), Arrays.stream()
- Step by step toward functional programming

---

# Streams and collections

- Examples
- Native functions and streams

---

# Lazy evaluation

- Required for infinite collections
- Lambdas allow for this (know how to get element, not what it is)

---

# Lazy evaluation

```
  Get large list of numbers
  Filter out prime numbers // This will take a while
  Filter odd numbers
  Grab third element
```

Eager:
  - Will run all transformations
  - Then retrieve third element
Lazy:
  - Both filters will be combined
  - Will only compute until the third element is found


---

# Lazy evaluation

- Intermediate operations[^2]
  - Lazy, operations only applied as elements are read from Stream
- Terminal operations
  - Compute the entire stream and resolves all intermediate operations

[^2]: See <http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#StreamOps>

---

# Lazy evaluation

```java
IntStream stream = IntStream.range(0, 200);
OptionalInt oi = stream
    .filter(i -> i % 2 == 0) // Lazy
    .map(i -> i * 100) // Lazy
    .skip(12) // Lazy but stateful
    .findFirst(); // Terminal operation
// java.util.OptionalInt oi = OptionalInt[2400]
```

---

# Parallel stream

- Execute on the current thread pool
- Default ForkJoinPool if no thread pool is found
- Streams can be turned parallel / sequential

---

# [fit] Parallel streams
## [fit] Demo

---

# Parallel streams / mutability

- Operations need to be thread-safe
- Avoid mutable objects / mutable state

---

# Advantages of Streams

- Hides boilerplate code
- Focus more on "what" than "how"
- Allows parallelism

---

# Disadvantages

- No (yet) lambdas debugging support in the JDK
- Longer synthetic call stacks (if you run the JVM with -XX:+UnlockDiagnosticVMOptions -XX:+ShowHiddenFrames)
- A bit of learning curve
