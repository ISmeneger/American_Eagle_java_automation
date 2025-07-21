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
Проект направлен на обеспечение качества пользовательского интерфейса и бизнес-логики.

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
  <a href="https://www.jetbrains.com/idea/" rel="nofollow"><img width="10%" title="IntelliJ IDEA" src="images/logo/Intelij_IDEA.svg" alt="Intellij_IDEA" style="max-width: 100%;"></a>
  <a href="https://www.java.com/" rel="nofollow"><img width="10%" title="Java" src="images/logo/Java.png" alt="Java" style="max-width: 100%;"></a>
  <a href="https://rest-assured.io/" rel="nofollow"><img width="10%" title="Rest Assured" src="images/logo/RestAssured.png" alt="RestAssured" style="max-width: 100%;"></a>
  <a href="https://gradle.org/" rel="nofollow"><img width="10%" title="Gradle" src="images/logo/Gradle.png" alt="Gradle"></a>
  <a href="https://junit.org/junit5/" rel="nofollow"><img width="10%" title="JUnit5" src="images/logo/JUnit5.png" alt="JUnit5" style="max-width: 100%;"></a>
  <a href="https://www.selenium.dev/" rel="nofollow"><img width="10%" title="Selenium" src="images/logo/selenium.png" alt="Selenium" style="max-width: 100%;"></a>
  <a href="https://github.com/" rel="nofollow"><img width="10%" title="GitHub" src="images/logo/GitHub.png" alt="GitHub" style="max-width: 100%;"></a>
  <a href="https://github.com/features/actions" rel="nofollow"><img width="10%" title="Github Actions" src="images/logo/Github_Actions.png" alt="Github Actions" style="max-width: 100%;"></a>
<a href="https://allurereport.org/" rel="nofollow"><img width="10%" title="Allure Report" src="images/logo/Allure_Report.jpg" alt="Allure" style="max-width: 100%;"></a>
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

## 🚀 Запуск тестов
📌 **Перед запуском** локально заполните `email` и `password` в файле `default.properties`:
#### Пример использования:

- email=your_email@example.com
- password=your_password

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
7. 
```bash
   ./gradlew allExceptDefectRemote -Denv=default -Demail=EMAIL_INPUT -Dpassword=PASSWORD_INPUT
```

- ✅ Все команды запускаются из корня проекта.
- 📦 Используется Gradle Wrapper (./gradlew), глобальный Gradle не требуется.

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

## 🧩 Дополнительно
✅ Реализовано логическое разделение по типам тестов (UI, API, smoke, defect)

✅ Используется шаблон Page Object Model (POM)

✅ Настроены теги @Tag для гибкой фильтрации тестов

✅ Поддержка CI/CD и Allure отчётности

