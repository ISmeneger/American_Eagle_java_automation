#  American Eagle Java Automation Test Project

## 🧪 Дипломный проект: Автоматизация тестирования сайта  [American Eagle](https://www.ae.com/us/en)

Автоматизация UI и API тестирования сайта **American Eagle** с использованием **Java**, **Selenium**, **REST Assured**, **JUnit 5** и **GitHub Actions**.

[![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=openjdk)](https://www.java.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.1-%2302303A?logo=gradle)](https://gradle.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5-%23525F6D?logo=junit5)](https://junit.org/junit5/)
[![Selenium](https://img.shields.io/badge/Selenium-4.21-%2343B02A?logo=selenium)](https://www.selenium.dev/)
[![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI-%232671E5?logo=githubactions)](https://github.com/features/actions)
[![Allure](https://img.shields.io/badge/Allure-Report-%23FF6A00?logo=allure)](https://allurereport.org/)

## 📌 Описание проекта

Этот репозиторий содержит **автоматизированные UI и API тесты** для сайта [American Eagle](https://www.ae.com), разработанные в рамках дипломного проекта.  
#### **American Eagle Outfitters, Inc.**, также известный как **American Eagle** — американская розничная компания по продаже одежды и аксессуаров, со штаб-квартирой в Питтсбурге, штат Пенсильвания. 
Она была основана в 1977 году братьями Джерри и Марком Силверманами в качестве дочерней компании розничной торговли **Ventures, Inc.**, которая также владела брендом мужской одежды **Silverman.**
По состоянию на январь 2021 года работает 901 магазин **American Eagle Stores**, 175 магазинов **Aerie** и 2 магазина **Todd Snyder** в США, Канаде, Мексике и Гонконге.
Среди популярных продуктов бренда — джинсы, рубашки поло, футболки, верхняя одежда и купальники
### **Проект направлен на обеспечение качества пользовательского интерфейса и бизнес-логики.**

##  🗂  Проект включает:

- Автоматизированные сценарии пользовательского интерфейса (UI)

- API-тесты для проверки бизнес-логики и взаимодействия с backend

- Использование инструментов: Selenium, JUnit 5, Gradle, GitHub Actions


| Инструмент                                                                                       | Назначение                                |
| ------------------------------------------------------------------------------------------------ | ----------------------------------------- |
| ![Gradle](https://img.shields.io/badge/Gradle-8.1-%2302303A?logo=gradle)                         | Сборка проекта и управление зависимостями |
| ![JUnit](https://img.shields.io/badge/JUnit-5-%23525F6D?logo=junit5)                             | Фреймворк для написания и запуска тестов  |
| ![Selenium](https://img.shields.io/badge/Selenium-4.21-%2343B02A?logo=selenium)                  | UI-тестирование веб-интерфейса            |
| ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI-%232671E5?logo=githubactions) | Автоматический запуск тестов (CI/CD)      |
| **REST Assured**                                                                                 | Автоматизация API тестирования            |
| **Allure Reports**                                                                               | Формирование наглядных тест-отчётов       |

---

## 📚 Содержание
- [🧰 Технологический стек](#-технологический-стек)

- [🚀 Запуск тестов](#-запуск-тестов)

- [⚙️ Запуск в GitHub Actions](#-запуск-в-github-actions)

- [📊 Allure отчет в GitHub Actions](#-allure-отчет-в-github-actions)

---

## 🧰 Технологический стек

<p align="center">
  <a href="https://jetbrains.com/idea/" target="_blank"> <img src="images/logo/intellij.png" alt="Intelij_IDEA" width="60" height="60"/> </a> 
  <a href="https://www.oracle.com/java/" target="_blank"> <img src="images/logo/Java.png" alt="Java" width="60" height="60"/> </a> 
  <a href="https://rest-assured.io/" target="_blank"> <img src="images/logo/RestAssured.png" alt="RestAssured" width="60" height="60"/> </a>
  <a href="https://gradle.org/" target="_blank"> <img src="images/logo/Gradle.png" alt="Gradle" width="60" height="60"/> </a>
  <a href="https://junit.org/junit5/" target="_blank"> <img src="images/logo/JUnit5.png" alt="JUnit5" width="120" height="60"/> </a>
  <a href="https://www.selenium.dev/" target="_blank"> <img src="images/logo/selenium.png" alt="selenium" width="60" height="60"/> </a> 
  <a href="https://github.com/" target="_blank"> <img src="images/logo/GitHub.png" alt="GitHub" width="60" height="60"/> </a> 
  <a href="https://docs.github.com/en/actions" target="_blank"> <img src="images/logo/Github_Actions.png" alt="Github Actions" width="100" height="60"/> </a> 
  <a href="https://docs.qameta.io/allure/" target="_blank"> <img src="images/logo/Allure.png" alt="Allure Report" width="60" height="60"/> </a>
</p>

- Язык: Java 17

- Фреймворк тестирования: JUnit 5

- UI тесты: Selenium WebDriver

- API тесты: REST Assured

- Сборщик: Gradle

- Шаблон проектирования: Page Object Model (POM)

- Генерация моделей: Lombok

- Отчетность: Allure Report

- CI/CD: GitHub Actions + GitHub Pages

Allure отчет включает:

- Пошаговое выполнение тестов

- Скриншоты и исходный код страницы при падениях

- Отметки по severity, tags и owner

---

## 📝Тест план
- [Test Plan (PDF)](src/test/resources/TestPlan.pdf)

---

## 🚀 Запуск тестов
📌 **Перед запуском** локально заполните `email` и `password` в файле `default.properties`:
#### Пример использования:

- email=your_email@example.com
- password=your_password

### ❗❗❗ Важно!

Мы проводим тестирование на **реальном сайте действующего интернет-магазина,** который использует защиту от ботов (Akamai). 
В связи с этим некоторые **автоматические тесты могут быть заблокированы.**

### ⚠️ Ограничения:
- Тесты, связанные с **регистрацией и авторизацией**, не работают в обычном режиме из-за защиты.

- Эти тесты в проекте помечены **тегом** `(@Teg="defect")`.

- Для их локального выполнения используется **отдельная команда запуска** (`gradle defect`).

### 📌 Это временное решение, применяемое только в рамках локального тестирования.

## 🔧 Команды для запуска:

| Тип тестов                   | Команда                                                                                  |
|------------------------------|------------------------------------------------------------------------------------------|
| **1. Все (без дефектных)**   | `gradle allExceptDefect`                                                                 |
| **2. Только smoke-тесты**    | `gradle smoke`                                                                           |
| **3. Только API**            | `gradle apiTests`                                                                        |
| **4. Только UI**             | `gradle uiTests`                                                                         |
| **5. Только defect-тесты**   | `gradle defect`                                                                          |
| **6. Полный запуск**         | `gradle test`                                                                            |
| **7. Удалённый запуск (CI)** | `./gradlew allExceptDefectRemote -Denv=default -Demail=EMAIL_INPUT -Dpassword=PASSWORD_INPUT` |

### Локальный запуск (через IntelliJ или терминал)
1.
```bash
   gradle allExceptDefect
```
2. 
```bash
    gradle smoke
```
3. 
```bash
   gradle apiTests
```
4.
```bash
   gradle uiTests
```
5. 
```bash
   gradle defect
```
6. 
```bash
   gradle test
```

- ✅ Все команды запускаются из корня проекта.
- 📦 Используется Gradle Wrapper (./gradlew), глобальный Gradle не требуется.

7.  **Запуск через GitHub Actions**
```bash
   ./gradlew allExceptDefectRemote -Denv=default -Demail=EMAIL_INPUT -Dpassword=PASSWORD_INPUT
```



---

## ⚙️ Запуск в GitHub Actions
1. Перейдите в репозиторий **[American_Eagle_java_automation](https://github.com/ISmeneger/American_Eagle_java_automation/actions)**

2. Откройте вкладку `Actions`

<p align="center"> <img src="images/screenShots/Actions.png" alt="Press GitHub Actions" width="700"/> </p>

3. Выберите workflow `Api and UI Automation`

<p align="center"> <img src="images/screenShots/Workflow.png" alt="Inside workflow" width="700"/> </p>

4. Нажмите `Run workflow`

<p align="center"> <img src="images/screenShots/Run_workflow.png" alt="Run workflow button" width="700"/> </p>

5. Дождитесь завершения выполнения тестов

---

## 📊 Allure отчет в GitHub Actions
1. После завершения сборки перейдите снова во вкладку **[Actions](https://github.com/ISmeneger/American_Eagle_java_automation/actions)**

<p align="center"> <img src="images/screenShots/Actions_after_build.png" alt="Press GitHub Actions" width="700"/> </p>

2. Откройте раздел `pages build and deployment`

<p align="center"> <img src="images/screenShots/Press_pages_build_and_deployment.png" alt="Press pages build and deployment" width="700"/> </p>

3. Перейдите по ссылке на **[Allure](https://ismeneger.github.io/American_Eagle_java_automation/20/index.html#)** отчет

<p align="center"> <img src="images/screenShots/Allure_report_link.png" alt="Press to allure report link" width="700"/> </p>

4. Изучите отчет `Allure`

<p align="center"> <img src="images/screenShots/Allure_report_overview.png" alt="Allure report overview" width="700"/> </p>

<p align="center"> <img src="images/screenShots/Allure_report_behaviors.png" alt="Allure report behaviors" width="700"/> </p>

<p align="center"> <img src="images/screenShots/Packages_api_tests.png" alt="Packages api tests" width="700"/> </p>

<p align="center"> <img src="images/screenShots/Packages_ui_tests.png" alt="Packages ui tests" width="700"/> </p>

## 🧩 Дополнительно
✅ Реализовано логическое разделение по типам тестов (UI, API, smoke, defect)

✅ Используется шаблон Page Object Model (POM)

✅ Настроены теги @Tag для гибкой фильтрации тестов

✅ Поддержка CI/CD и Allure отчётности

