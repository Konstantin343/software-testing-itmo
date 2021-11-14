import {test, expect} from '@playwright/test';
import {addNewList, addNewPlace, submitCredentials} from "./Steps.mjs";

let suffix
let login
let password

test.describe('Places lists', () => {
    test.beforeEach(async ({page}) => {
        if (!login && !password) {
            login = 'login' + Math.random()
            password = 'password' + Math.random()
            await page.goto('http://localhost:3000/sign-up')
        } else {
            await page.goto('http://localhost:3000/sign-in')
        }
        suffix = Math.random()
        await submitCredentials(page, login, password)
    })

    test('Add new places list', async ({page}) => {
        await addNewList(page, 'testName' + suffix, 'testDescription' + suffix)

        await expect(await page.locator('text=testName' + suffix))
            .toHaveText('testName' + suffix)
        await expect(await page.locator('text=testDescription' + suffix))
            .toHaveText('testDescription' + suffix)
    })

    test('Add new places list with place', async ({page}) => {
        suffix = Math.random()
        await page.goto('http://localhost:3000/sign-in')
        await submitCredentials(page, 'login' + suffix, 'password' + suffix)

        await addNewList(page, 'testName' + suffix, 'testDescription' + suffix)

        await page.click('text=testName' + suffix)
        await expect(await page.locator('h3')).toHaveText('Add Place')

        await addNewPlace(
            page,
            'placeTestName' + suffix,
            'placeTestDescription' + suffix,
            'placeTestType' + suffix,
            'placeTestCity' + suffix,
            'placeTestStreet' + suffix,
            'placeTestNumber' + suffix
        )

        await expect(await page.locator('text=placeTestName' + suffix))
            .toHaveText('placeTestName' + suffix)
        await expect(await page.locator('text=placeTestDescription' + suffix))
            .toHaveText('placeTestDescription' + suffix)
        await expect(await page.locator('text=placeTestType' + suffix))
            .toHaveText('placeTestType' + suffix)
        await expect(await page.locator('text=placeTestCity' + suffix + ', placeTestStreet' + suffix + ', placeTestNumber' + suffix))
            .toHaveText('placeTestCity' + suffix + ', placeTestStreet' + suffix + ', placeTestNumber' + suffix)
    })
})