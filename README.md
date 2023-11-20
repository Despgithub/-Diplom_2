# Diplom_2

<hr>
Дипломный проект по тестированию API приложения по заказу бургеров Stellar Burgers.

## Описание

<hr>
На проекте используется Java 11 и Maven и следующие библиотеки:
<ul>
<li>Junit 4.13.2</li>
<li>Rest-assured 5.3.2</li>
<li>Gson 2.10.1</li>
<li>Allure 2.24.0</li>
<li>Lombok 1.18.30</li>
<li>Javafaker 1.0.2</li>
<li>Aspectj 1.9.20.1</li>
</ul>

## Запуск тестов

<hr>

```
mvn clean test
```

## Генерация отчета Allure

<hr>

```
mvn allure:serve
```

## Запуск тестов и генерация отчета Allure

<hr>

```
mvn clean test allure:serve
```