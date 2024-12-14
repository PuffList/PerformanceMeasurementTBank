# JMH Benchmark Results

## Описание

Проект демонстрирует сравнение производительности 4 методов вызова:
- Прямой вызов (`directAccess`).
- LambdaMetafactory (`lambdaMetaFactory`).
- MethodHandles (`methodHandles`).
- Рефлексия (`reflection`).

Тесты выполнены с использованием JMH.

## Результаты

| Benchmark                              | Mode  | Cnt | Score   | Error | Units  |
|----------------------------------------|-------|-----|---------|-------|--------|
| `ReflectionBenchmark.directAccess`     | avgt  |     | 0,771   |       | ns/op  |
| `ReflectionBenchmark.lambdaMetaFactory`| avgt  |     | 1,228   |       | ns/op  |
| `ReflectionBenchmark.methodHandles`    | avgt  |     | 5,805   |       | ns/op  |
| `ReflectionBenchmark.reflection`       | avgt  |     | 10,266  |       | ns/op  |

## Выводы

1. Прямой вызов (`directAccess`) самый быстрый.
2. LambdaMetafactory подходит для высокопроизводительных динамических вызовов.
3. MethodHandles имеет приемлемую производительность для задач со сложной обработкой.
4. Рефлексия — самая медленная.

## Как запустить

Выполните следующую команду, а затем запустите BenchmarkRunner.java:
```
mvn clean package

