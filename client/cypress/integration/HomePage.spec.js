describe('Home Page', () => {
    it('User should be able to sign up', () => {
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