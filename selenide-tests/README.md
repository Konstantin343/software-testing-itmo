# Настройка тестов с Selenide.

## Проект с тестами

1. Создать **Gradle**-проект
2. Изменить `build.gradle`:
   - Добавить секцию **allure**-плагина:
     ```kotlin
      plugins {
          id 'io.qameta.allure' version '2.6.0'
      }
      ```
   - Добавить в `dependencies` зависимость `com.codeborne:selenide:5.22.1`
   - Добавить в `dependencies` все необходимые зависимости `io.qameta.allure:*`
   - Добавить секцию **allure**:
     ```kotlin
     allure {
         autoconfigure = true
         version = '2.17.2'
         reportDir = file("$projectDir/build/allure-report")
         resultsDir = file("$projectDir/build/allure-results")
         downloadLinkFormat = 'https://github.com/allure-framework/allure2/releases/download/%s/allure-%<s.zip'
     }
     ```
   - В раздел `test` добавить необходимые параметры:
     ```kotlin
        'selenide.<param>' : System.getProperty("selenide.<param>") ?: '<default>'
     ```
     
   Пример: [**build.gradle**](build.gradle)


3. Написать любой тест с использованием **Selenide**

   Пример: [**AuthorizationTest**](src/test/kotlin/com/itmo/kkrukhmalev/places/tests/AuthorizationTest.kt)

## Allure
1. При заданной конфигурации в папке `$projectDir/build/allure-results` будут находиться результаты тестов
2. Используйте `./gradlew allureServe` для просмотра отчета

## Selenoid

1. Добавить системные параметры при запуске тестов:
   - `selenide.driverManagerEnabled = "false"`
   - `selenide.remote = "http://localhost:4444/wd/hub"` (или другой **Selenoid**-сервер)
2. Поднять **Selenoid** стандартным образом (см. [**инструкция**](https://aerokube.com/selenoid/latest/))
3. При запуске тестов они будут работать на заданном **Selenoid**-сервере

## GitHub Actions

---

- _В данном репозитории реализован вариант, при котором на **GitHub Actions** каждый раз поднимается приложение, после чего стартует **Selenoid**-сервер, напротив которого запускются тесты._  
- _В общем случае такой подход не является верным, поэтому в инструкции описан другой вариант._  
- _В задании используется такой вариант в связи с отсутствием возможности поднять отдельно все необходимое на длительный срок._  

---

1. Поднять **Selenoid**-сервер на `<selenoid-host>`
2. Поднять приложение на `<app-host>`
3. Настроить **workflow**:
   ```yaml
   Selenide-Tests:
     name: Selenide Tests - ${{ matrix.browser }}
     runs-on: ubuntu-latest

     strategy:
       fail-fast: false
     matrix:
       browser: [ chrome, firefox ]

     steps:
       - name: Checkout
         uses: actions/checkout@v2

       - name: Run Selenide Tests
         working-directory: selenide-tests
         run: ./gradlew test -Dselenide.browser=${{ matrix.browser }} -Dselenide.remote=<selenoid-host> -Dselenide.baseUrl=<app-host>

       - name: Upload Allure Reports Artifact
         if: always()
         uses: actions/upload-artifact@v2
         with:
           name: selenide-allure-results-${{ matrix.browser }}
           path: selenide-tests/build/allure-results
   ```
   Отчеты по отдельным запускам на разных браузерах будут собраны в отдельные артефакты
4. Можно добавить **job** в **workflow** для сборки артефактов в один отчет:  
   Пример: [build-and-test.yml](../.github/workflows/build-and-test.yml)