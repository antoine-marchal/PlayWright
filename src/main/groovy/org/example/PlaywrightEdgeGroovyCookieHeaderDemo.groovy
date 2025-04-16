package org.example

import com.microsoft.playwright.*
import com.microsoft.playwright.options.*
import groovy.json.JsonOutput

class PlaywrightEdgeGroovyCookieHeaderDemo {
    static void main(String[] args) {
        Playwright playwright = Playwright.create()
        try {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setChannel("msedge")
                    .setHeadless(false)
            )
            BrowserContext context = browser.newContext()
            Page page = context.newPage()

            // Set a cookie
            context.addCookies([
                [
                    name: "myCookie",
                    value: "cookieValue123",
                    domain: "webhook.site",
                    path: "/"
                ]
            ])

            // Perform a GET request with headers
            def getHeaders = [
                "Authorization": "Bearer mytoken123",
                "Custom-Header": "customValue"
            ]

            page.navigate("https://webhook.site/3bd7194e-3495-4925-981d-858724c41177")
            println "GET - Page title: ${page.title()}"
            page.waitForTimeout(2000)

            // Prepare POST body as JSON
            Map<String, Object> postBody = [
                username: "user1",
                password: "pass123",
                details: [role: "admin", active: true]
            ]
            String jsonBody = JsonOutput.toJson(postBody)

            // Perform a POST request with headers and JSON body
            def postHeaders = [
                "Authorization": "Bearer mytoken123",
                "Content-Type": "application/json",
                "Custom-Header": "customValue"
            ]
            page.evaluate("""
                fetch('https://webhook.site/3bd7194e-3495-4925-981d-858724c41177', {
                    method: 'POST',
                    headers: ${JsonOutput.toJson(postHeaders)},
                    body: ${JsonOutput.toJson(jsonBody)}
                })
            """)
            println "POST - Sent JSON body: $jsonBody"
            page.waitForTimeout(3000)

            browser.close()
        } finally {
            playwright.close()
        }
    }
}