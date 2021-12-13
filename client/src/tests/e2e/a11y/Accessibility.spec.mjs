import {test} from '@playwright/test';
import {testA11y} from "../Steps.mjs";

test.describe("A11y", () => {
    test('Home accessibility', async ({page}) => {
        await testA11y(page, '/')
    })

    test('Sign in accessibility', async ({page}) => {
        await testA11y(page, '/sign-in')
    })

    test('Sign up accessibility', async ({page}) => {
        await testA11y(page, '/sign-up')
    })
})