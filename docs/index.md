# Playwright vs Cypress

Оба фреймворка позволяют выполнять **e2e** тестирование web-приложений

---

## Playwright

- Все действия выполняются асинхронно, необходимо использовать `async`/`await` в тестах
- Не требует запуска дополнительного приложения
- Тесты работают с переданным в функцию обектом page
- Тесты могут быть расположены в любом месте
- Тесты запускаются командой `npx playwright test path/to/tests/*.spec.js`
- Вывод тестовых результатов в консоль

---

### Cypress

- В коде не требуются `async`/`await`
- Перед запуском тестов необходимо запустить приложение Cypress
- Используется объект `cy` без явного импорта
- Тесты необходимо расположить в `<app>/cypress/integration/`
- Тесты запускются через приложение, можно наблюдать за шагами тестов в удобном формате
- Вывод тестовых результатов в приложении в удобном формте

---
## Примеры:

### [Cypress](https://github.com/Konstantin343/software-testing-itmo/tree/hw1/client/cypress/integration)
```javascript
describe('My First Test', () => {
    it('Does not do much!', () => {
        cy.visit('http://localhost:3000')

        cy.contains('Sign up').click()
        cy.get('[name=\"login\"]')
            .type('user')
        cy.get('[name=\"password\"]')
            .type('password')
        cy.contains('Sign up').click()

        cy.get('h1').should("have.text", "Hello, user! This is Places App.")
    })
})
```
### [Playwright](https://github.com/Konstantin343/software-testing-itmo/tree/hw1/client/src/tests/e2e)
```javascript
test.describe('Places lists', () => {
    test('Add new places list', async ({page}) => {
        await page.goto('http://localhost:3000/sign-in')
        await page.fill('input[name=\'login\']', 'test')
        await page.fill('input[name=\'password\']', 'test')
        const signInButton = await page.locator('input[type=\'submit\']')
        await signInButton.click()

        await page.click("text=Add New List")
        await page.fill('input[name=\'name\']', 'testName')
        await page.fill('textarea[name=\'description\']', 'testDescription')
        const addButton = await page.locator('button')
        await addButton.click()

        await expect(await page.locator('text=testName')).toHaveText('testName')
        await expect(await page.locator('text=testDescription')).toHaveText('testDescription')
    })
});
```