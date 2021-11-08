import {test, expect} from '@playwright/test';
import clearDb from "./Utils.mjs";


test.beforeAll(async () => {
    await clearDb()
})

test.afterEach(async () => {
    await clearDb()
})

test.describe('Sign up', () => {
    test('Sing up, sign out, sign in and check session', async ({page}) => {
        await page.goto('http://localhost:3000/sign-up')

        await page.fill('input[name=\'login\']', 'user')
        await page.fill('input[name=\'password\']', 'password')
        const signUpButton = await page.locator('input[type=\'submit\']')
        await expect(signUpButton).toHaveValue("Sign up")
        await signUpButton.click()

        await expect(await page.locator('h1')).toHaveText('Hello, user! This is Places App.')

        const signOutButton = await page.locator('text=Sign out (user)')
        await signOutButton.click()

        await expect(await page.locator('h1')).toHaveText('Hello! This is Places App.')
        await page.click('text=Sign in')

        await page.fill('input[name=\'login\']', 'user')
        await page.fill('input[name=\'password\']', 'password')
        const signInButton = await page.locator('input[type=\'submit\']')
        await expect(signInButton).toHaveValue("Sign in")
        await signInButton.click()

        await expect(await page.locator('h1')).toHaveText('Hello, user! This is Places App.')
    })
})