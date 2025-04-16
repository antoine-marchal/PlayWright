package org.example

import com.microsoft.playwright.*

class PlaywrightEdgeExample {
    static void main(String[] args) {
        println 'Hello'
        Playwright playwright = Playwright.create()
        try {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setChannel("msedge")
                    .setHeadless(false)
            )
            BrowserContext context = browser.newContext()
            Page page = context.newPage()

            // Perform a GET request
            page.navigate("https://webhook.site/3bd7194e-3495-4925-981d-858724c41177")
            println "GET - Titre de la page : ${page.title()}"

            // Perform a POST request via JavaScript
            page.evaluate("fetch('https://webhook.site/3bd7194e-3495-4925-981d-858724c41177', { method: 'POST', body: 'données de test' })")

            // Wait a few seconds to observe the results
            page.waitForTimeout(5000)

            browser.close()
        } finally {
            playwright.close()
        }
    }
}