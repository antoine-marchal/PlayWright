package org.example

import com.microsoft.playwright.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class PlaywrightEdgeGroovyEvalCountDemo {
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

            // Navigate to a blank page
            page.navigate("about:blank")

            // Evaluate JS: count to 10 with 1s delay, return array
            def values = (1..10).toList()

            // Write values to CSV file
            def dataDir = Paths.get("data")
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir)
            }
            def csvPath = dataDir.resolve("values.csv")
            def writer = Files.newBufferedWriter(csvPath)
            def csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
            values.each { csvPrinter.printRecord(it) }
            csvPrinter.close()
            writer.close()

            // Read CSV in browser context, log and collect values
            def result = page.evaluate("""
                async () => {
                    const lines = ${groovy.json.JsonOutput.toJson(csvPath.toFile().readLines())};
                    const arr = [];
                    for (const line of lines) {
                        const val = parseInt(line, 10);
                        console.log(val);
                        arr.push(val);
                        await new Promise(resolve => setTimeout(resolve, 1000));
                    }
                    return arr;
                }
            """)

            // Convert result to Groovy List and print
            List<Integer> resultList = result as List<Integer>
            println "Read values from CSV: $resultList"

            page.waitForTimeout(2000)
            browser.close()
        } finally {
            playwright.close()
        }
    }
}
