import {test, expect} from '@playwright/test';
import {submitCredentials} from "./Steps.mjs";

test.describe('Sign up', () => {
    test('Sing up, sign out, sign in and check session', async ({page}) => {
        const suffix = Math.random()
        const login = 'user' + suffix
        const password = 'password' + suffix

        await page.goto(`/sign-up`)
        await submitCredentials(page, login, password, 'Sign up')
        await expect(await page.locator('h1')).toHaveText('Hello, user' + suffix + '! This is Places App.')

        const signOutButton = await page.locator('text=Sign out (user' + suffix + ')')
        await signOutButton.click()
        await expect(await page.locator('h1')).toHaveText('Hello! This is Places App.')

        await page.click('text=Sign in')
        await submitCredentials(page, login, password, 'Sign in')
        await expect(await page.locator('h1')).toHaveText('Hello, user' + suffix + '! This is Places App.')
    })
})