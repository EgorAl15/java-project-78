# Валидатор данных (Java)

[![hexlet-check](https://github.com/EgorAl15/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/EgorAl15/java-project-78/actions/workflows/hexlet-check.yml)
[![build](https://github.com/EgorAl15/java-project-78/actions/workflows/build.yml/badge.svg)](https://github.com/EgorAl15/java-project-78/actions/workflows/build.yml)

Библиотека для проверки корректности данных на Java.

Проект реализует валидатор, позволяющий создавать схемы проверки строк, чисел и объектов `Map`. Ограничения можно объединять с помощью fluent-интерфейса и использовать для вложенной валидации данных.

Учебный проект выполнен в рамках курса [Хекслета](https://ru.hexlet.io/programs/java).

## Возможности

Валидатор поддерживает:

- валидацию строк;
- проверку обязательности значения;
- проверку минимальной длины строки;
- проверку наличия подстроки;
- валидацию чисел;
- проверку положительных чисел;
- проверку попадания числа в диапазон;
- валидацию объектов `Map`;
- проверку размера `Map`;
- вложенную валидацию значений `Map` с помощью `shape()`;
- объединение нескольких ограничений в одной схеме.

## Стек

- Java 21
- Gradle
- JUnit 5
- GitHub Actions

## Установка

Клонируйте репозиторий:

```bash
git clone https://github.com/EgorAl15/java-project-78.git
cd java-project-78/app
```

Соберите проект:

### Linux / macOS

```bash
./gradlew build
```

### Windows

```powershell
.\gradlew.bat build
```

## Использование

### Валидация строк

```java
var validator = new Validator();

var schema = validator.string()
        .required()
        .minLength(5)
        .contains("hex");

schema.isValid("hexlet"); // true
schema.isValid("hello");  // false
schema.isValid(null);     // false
```

### Валидация чисел

```java
var validator = new Validator();

var schema = validator.number()
        .required()
        .positive()
        .range(5, 10);

schema.isValid(7);    // true
schema.isValid(5);    // true
schema.isValid(10);   // true
schema.isValid(-5);   // false
schema.isValid(11);   // false
schema.isValid(null); // false
```

### Валидация Map

```java
var validator = new Validator();

var schema = validator.map()
        .required()
        .sizeof(2);

var data = new HashMap<String, String>();
data.put("firstName", "John");
data.put("lastName", "Smith");

schema.isValid(data); // true
```

### Вложенная валидация

Метод `shape()` позволяет определить отдельную схему проверки для каждого значения объекта `Map`.

```java
var validator = new Validator();
var schema = validator.map();

Map<String, BaseSchema<String>> schemas = new HashMap<>();

schemas.put(
        "firstName",
        validator.string().required()
);

schemas.put(
        "lastName",
        validator.string().required().minLength(2)
);

schema.shape(schemas);

Map<String, String> human = new HashMap<>();
human.put("firstName", "John");
human.put("lastName", "Smith");

schema.isValid(human); // true
```

Если значение не соответствует своей схеме:

```java
Map<String, String> human = new HashMap<>();
human.put("firstName", "John");
human.put("lastName", "B");

schema.isValid(human); // false
```

## Тестирование

Для запуска тестов:

### Linux / macOS

```bash
./gradlew test
```

### Windows

```powershell
.\gradlew.bat test
```

Для полной проверки проекта:

```powershell
.\gradlew.bat clean build
```

Тесты также автоматически запускаются в GitHub Actions при отправке изменений в репозиторий.

## Демонстрация

[Пример работы валидатора на asciinema](https://asciinema.org/a/NtQ6xBownxYFN2H8WEffvtcS1)

## О Хекслете

[Хекслет](https://ru.hexlet.io/) — школа программирования с практическими проектами.

Этот проект выполнен в рамках обучения Java-разработке.