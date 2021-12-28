# Домашние задания

## ДЗ 1 (Фронтенд + тесты)

### Требования

#### Main часть:

- [x] ~~Создать репозиторий на GitHub, в котором создать папку client~~
- [x] ~~В папке client создать приложение на React / Angular / Vue фреймворке на ваш выбор.~~
- [x] ~~Требования к приложению: должно содержать несколько страниц с роутингом, обязательно содержать сервис, который общается с node.js бекендом.~~
- [x] ~~Присутствуют unit, component и e2e тесты в минимальном количестве 1шт каждый.~~

#### Advanced часть:

- [x] ~~Приложение содержит авторизацию и не собрано из генераторов вида JHipster.~~
- [x] ~~Написаны тесты для проверки авторизации~~
- [x] ~~Существует нескольно наборов тестов (несколько suites)~~

#### Bonus часть:

- [x] ~~Вы рассматриваете два любых фреймворка на выбор. Например: Mocha и Jest или Playwright и Cypress.~~
- [x] ~~Пишете примеры с каждым из фреймовокров для своего приложения.~~
- [x] ~~Короткая статья или заметки в чем именно для вас кажутся основные отличия.~~
- [x] ~~Делаете в своем репозитории на GitHub активным GitHub pages и размещаете эту статью там.~~

---

### Приложение

Приложение, которое позволяет хранить подборки различных заведений, делиться ими и выбирать места, куда можно сходить

---

### Структура

- [**client**](client) - React App frontend. Run: `cd client && npm start`

  `http://localhost:3000` by default
- [**server**](server) - Express Node.js backend. Run: `cd server && npm start`

  `SERVER_PORT = 5000` by default (change in [**.env**](server/.env))

  **Database**: `sqlite`

  **Test**: `cd client && npm test`

- [**e2e**](client/src/tests/e2e) - End-to-End tests. 
Run: `npx playwright test src/tests/e2e/**/*.spec.*js --workers=1 --config=src/tests/e2e/playwright.config.ts`

---

### GitHub Pages

[**Playwright vs Cypress**](https://konstantin343.github.io/software-testing-itmo/) _(disabled now)_

---

## ДЗ 2 (Бекенд + тесты)

### Требования

#### Main часть: 

- [X] ~~Сделать сервис на Java+Spring+любая DB (или NodeJS + DI tool), который имеет как мин 1 Controller.~~
- [X] ~~Написать Unit и Component тесты для этого сервиса.~~ 
- [X] ~~Использовать TestContainers для одного теста с DB.~~ 
- [X] ~~Использовать Mockito для мокирования тестов с внешним сервисом.~~ 
- [X] ~~Написать документацию(README) какие тесты еще необходимо написать, но вы не успели.~~

#### Advanced часть: 

- [X] ~~Сделать взаимодействие сервиса и вашего Frontend приложения.~~
- [X] ~~Сделать тесты на авторизацию.~~ 
- [X] ~~Создать отдельные Spring Test Configuration, которые можно переключать с помощью флага при запуске тестов.~~ 
- [X] ~~Сделать генерацию тестовой документации через Asci Doctor(Spring Rest Docs).~~

#### Bonus часть:

- [ ] Придумать функциональность, с которой можно использовать очереди/стримы вида RabbitMQ/Kafka streams. 
- [ ] Написать компонентные тесты на эту функциональность(используя TestContainers).

---

### Структура

- [**client**](client) - React App frontend. Run: `cd client && npm start`

  `http://localhost:3000` by default
- [**spring-backend**](spring-backend) - Spring backend. Run: `cd spring-backend && ./mvnw spring-boot:run`

  `server.port=5000` by default (change in [**application.properties**](spring-backend/src/main/resources/application.properties))

  **Database**: `postgresql`
  
  Change properties and start postgres on this port (`5432` by default)
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
  spring.datasource.username=
  spring.datasource.password=
  ```

  **Test**: `cd spring-backend && ./mvnw test`  
  You can specify `-Dtest.configuration=<value>` flag, where `<value>` in [`springMock`, `mockitoMock`].  
  - `springMock` _(default)_ - use `org.springframework.boot.test.mock.mockito.@MockBean` in `*ControllerTest`
  - `mockitoMock` - use `org.mockito.kotlin.mock()` in `*ControllerTest`

---

## ДЗ 3 (CI/CD (GtiHub Actions))

### Требования

#### Main часть:

- [X] ~~Добавить GitHub action для запуска тестов на UI и Backend по пушу из в master ветку.~~

#### Advanced часть:

- [ ] Добавить GitHub action для деплоя приложения UI+BE на Azure/Vercel/Яндекс Облако.

#### Bonus часть:

- [ ] Использовать Kubernetes в Azure/Яндекс Облаке для разворачивания среды.

### Структура 

- [**workflows**](.github/workflows) - `*.yml` files with GitHub workflows
  - [**Build & Test**](.github/workflows/build-and-test.yml) - workflow that build `spring-backend`/`client` and run tests

---

## ДЗ 4 (Репортинг Allure)

### Требования

#### Bonus часть:

- [X] ~~Использовать Allure reporting для написанных тестов.~~

### Allure Reports

[**Reports from `main`**](https://konstantin343.github.io/software-testing-itmo/)

---

## ДЗ 5 (Pact-тесты)

### Требования

#### Bonus часть:

- [ ] Написать Pact-тесты к своему сервису.

---

## ДЗ 6 (A11Y)

### Требования

#### Bonus часть:

- [X] ~~Протестировать свой веб-сайт на а11y с помощью инструментов от Mozilla и Lighthouse.~~ 
- [X] ~~Пофиксить проблемы.~~
- [X] ~~Сделать автоматический тест с axe.~~

### Проблемы

#### Mozilla 
- **Sign in**/**Sign up**: `Focusable element must have focus styling`
- **Navigation**: `Clickable elements must be focusable and should have interactive semantics`
- **Text fields**: `Form elements should have a visible text label`

#### Lighthouse

- **Home page**: `Heading elements are not in a sequentially-descending order`

### Тесты

- [**a11y**](client/src/tests/e2e/a11y) - A11y tests.
  Run: `npx playwright test src/tests/e2e/a11y/*.spec.*js --workers=1 --config=src/tests/e2e/playwright.config.ts`


---

## ДЗ 7 (Selenium)

### Требования

#### Main часть:

- [X] ~~Написать e2e тесты, используя Selenide.~~

#### Advanced часть:

- [X] ~~Настроить запуск тестов с Selenoid.~~

#### Bonus часть:

- [X] ~~Настроить генерацию отчетов с Allure report, сделав полный сетап в GitHub: e2e тесты с selenide запускаются с использованием Selenoid на разных окружениях(браузерах) параллельно и собирают отчет с помощью Allure Report.~~
- [X] ~~Написать статью как собрать такой сетап.~~

### Тесты

- [**Selenide**](selenide-tests) - Selenide tests.
  Run: `./gradlew test --Dselenide.browser="<value>"`

### Workflow

- [**Build & Test**](.github/workflows/build-and-test.yml) - workflow that build app and run tests

### Allure Reports

- [**Reports**](https://konstantin343.github.io/software-testing-itmo/) - отчеты о тестах

### Статья

- [**Сетап тестов**](selenide-tests/README.md) - статья по настройке подобных тестов

---

## ДЗ 8 (Performance)

### Требования

#### Main часть:

- [X] ~~Пройти воркшоп и выложить результат в гитхаб репозиторий.~~

#### Advanced часть:

- [ ] Настроить CI с Github actions.

#### Bonus часть:

- [ ] Настроить генерацию отчетов с Allure report.

### Результат

- [**perf-workshop**](perf-workshop)

### GitLab репозиторий

- **https://gitlab.com/kk-st/kk-st**

### Отчет о тестах

- **https://kk-st.gitlab.io/kk-st**

---
