package org.example

import com.microsoft.playwright.*
import com.microsoft.playwright.options.HttpCredentials

class PlaywrightEdgeGroovyBasicAuthNullDemo {
    static void main(String[] args) {
        Playwright playwright = Playwright.create()
        try {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setChannel("msedge")
                    .setHeadless(false)
            )
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setHttpCredentials(new HttpCredentials("user", "pass")))
            Page page = context.newPage()

            // Navigate to a page that requires basic authentication
            // Example: https://httpbin.org/basic-auth/user/passwd
            page.navigate("https://httpbin.org/basic-auth/user/pass")
            // Extract JSON from the page and parse it as a Map
            def jsonText = page.textContent("pre")
            def jsonSlurper = new groovy.json.JsonSlurper()
            def jsonMap = jsonSlurper.parseText(jsonText)
            println jsonMap
            // Wait for the page to load and observe the result
            page.waitForTimeout(3000)
            
            browser.close()
        } finally {
            playwright.close()
        }
    }
}