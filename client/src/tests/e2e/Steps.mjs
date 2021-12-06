import {expect} from '@playwright/test';
import {checkA11y, injectAxe} from "axe-playwright";

export async function submitCredentials(page, login, password, buttonText = null) {
    await page.fill('input[name=\'login\']', login)
    await page.fill('input[name=\'password\']', password)
    const submitButton = await page.locator('input[type=\'submit\']')
    if (buttonText)
        await expect(submitButton).toHaveValue(buttonText)
    await submitButton.click()
}

export async function addNewList(page, name, description) {
    await page.click("text=Add New List")
    await page.fill('input[name=\'name\']', name)
    await page.fill('textarea[name=\'description\']', description)
    const addButton = await page.locator('button')
    await addButton.click()
}

export async function addNewPlace(page, name, description, type, city, street, number) {
    await page.fill('input[name=\'name\']', name)
    await page.fill('textarea[name=\'description\']', description)
    await page.fill('input[name=\'type\']', type)
    await page.fill('input[name=\'city\']', city)
    await page.fill('input[name=\'street\']', street)
    await page.fill('input[name=\'number\']', number)
    const addButton = await page.locator('button')
    await addButton.click()
}

export async function testA11y(page, url) {
    await page.goto(url)
    await injectAxe(page)
    await checkA11y(page, null, {
        detailedReport: true
    })
}