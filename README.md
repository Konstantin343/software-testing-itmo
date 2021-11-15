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

  Database: `sqlite`

- [**e2e**](client/src/tests/e2e) - End-to-End tests. Run: `npx playwright test src/tests/e2e/*.spec.*js --workers=1`

---

### GitHub Pages

[**Playwright vs Cypress**](https://konstantin343.github.io/software-testing-itmo/)

---

## ДЗ 2 (Бекенд + тесты)

### Требования

#### Main часть: 

- [X] Сделать сервис на Java+Spring+любая DB (или NodeJS + DI tool), который имеет как мин 1 Controller.
- [ ] Написать Unit и Component тесты для этого сервиса. 
- [ ] Использовать TestContainers для одного теста с DB. 
- [ ] Использовать Mockito для мокирования тестов с внешним сервисом. 
- [ ] Написать документацию(README) какие тесты еще необходимо написать, но вы не успели.

#### Advanced часть: 

- [X] Сделать взаимодействие сервиса и вашего Frontend приложения.
- [ ] Сделать тесты на авторизацию. 
- [ ] Создать отдельные Spring Test Configuration, которые можно переключать с помощью флага при запуске тестов. 
- [ ] Сделать генерацию тестовой документации через Asci Doctor(Spring Rest Docs).

#### Bonus часть:

- [ ] Придумать функциональность, с которой можно использовать очереди/стримы вида RabbitMQ/Kafka streams. 
- [ ] Написать компонентные тесты на эту функциональность(используя TestContainers).

---

### Структура

- [**client**](client) - React App frontend. Run: `cd client && npm start`

  `http://localhost:3000` by default
- [**spring-backend**](spring-backend) - Spring backend. Run: main from `PlacesBackendApplication.kt` file

  `server.port=5000` by default (change in [**application.properties**](spring-backend/src/main/resources/application.properties))

  Database: `postgresql`
  
  Change properties and start postgres on this port (`5432` by default)
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
  spring.datasource.username=
  spring.datasource.password=
  ```


---

## ДЗ 3 (CI/CD (GtiHub Actions))

### Требования

#### Main часть:

- [ ] Добавить GitHub action для запуска тестов на UI и Backend по пушу из в master ветку.

#### Advanced часть:

- [ ] Добавить GitHub action для деплоя приложения UI+BE на Azure/Vercel/Яндекс Облако.

#### Bonus часть:

- [ ] Использовать Kubernetes в Azure/Яндекс Облаке для разворачивания среды.

---

## ДЗ 4 (Репортинг Allure)

### Требования

#### Bonus часть:

- [ ] Использовать Allure reporting для написанных тестов.

---

## ДЗ 5 (Pact-тесты)

### Требования

#### Bonus часть:

- [ ] Написать Pact-тесты к своему сервису.

---

## ДЗ 6 (A11Y)

### Требования

#### Bonus часть:

- [ ] Протестировать свой веб-сайт на а11y с помощью инструментов от Mozilla и Lighthouse. 
- [ ] Пофиксить проблемы.
- [ ] Сделать автоматический тест с axe.

---

## ДЗ 7 (Selenium)

### Требования

#### Main часть:

- [ ] Написать e2e тесты, используя Selenide.

#### Advanced часть:

- [ ] Настроить запуск тестов с Selenoid.

#### Bonus часть:

- [ ] Настроить генерацию отчетов с Allure report, сделав полный сетап в GitHub: e2e тесты с selenide запускаются с использованием Selenoid на разных окружениях(браузерах) параллельно и собирают отчет с помощью Allure Report.
- [ ] Написать статью как собрать такой сетап.

---

## ДЗ 8 (Performance)

### Требования

#### Main часть:

- [ ] Пройти воркшоп и выложить результат в гитхаб репозиторий.

#### Advanced часть:

- [ ] Настроить CI с Github actions.

#### Bonus часть:

- [ ] Настроить генерацию отчетов с Allure report.

---
