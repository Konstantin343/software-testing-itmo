import {test, expect} from '@playwright/test';
import clearDb from "./Utils.mjs";

test.afterEach(async () => {
    await clearDb()
})

test.beforeAll(async () => {
    await clearDb()
})

test.beforeEach(async ({page}) => {
    await page.goto('http://localhost:3000/sign-up')
    await page.fill('input[name=\'login\']', 'test')
    await page.fill('input[name=\'password\']', 'test')
    const signUpButton = await page.locator('input[type=\'submit\']')
    await signUpButton.click()
})

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

    test('Add new places list with place', async ({page}) => {
        await page.goto('http://localhost:3000/sign-in')
        await page.fill('input[name=\'login\']', 'test')
        await page.fill('input[name=\'password\']', 'test')
        const signInButton = await page.locator('input[type=\'submit\']')
        await signInButton.click()

        await page.click("text=Add New List")
        await page.fill('input[name=\'name\']', 'testName')
        await page.fill('textarea[name=\'description\']', 'testDescription')
        let addButton = await page.locator('button')
        await addButton.click()

        await page.click('text=testName')
        await expect(await page.locator('h3')).toHaveText('Add Place')
        await page.fill('input[name=\'name\']', 'placeTestName')
        await page.fill('textarea[name=\'description\']', 'placeTestDescription')
        await page.fill('input[name=\'type\']', 'placeTestType')
        await page.fill('input[name=\'city\']', 'placeTestCity')
        await page.fill('input[name=\'street\']', 'placeTestStreet')
        await page.fill('input[name=\'number\']', 'placeTestNumber')
        addButton = await page.locator('button')
        await addButton.click()

        await expect(await page.locator('text=placeTestName')).toHaveText('placeTestName')
        await expect(await page.locator('text=placeTestDescription')).toHaveText('placeTestDescription')
        await expect(await page.locator('text=placeTestType')).toHaveText('placeTestType')
        await expect(await page.locator('text=placeTestCity, placeTestStreet, placeTestNumber'))
            .toHaveText('placeTestCity, placeTestStreet, placeTestNumber')
    })
})