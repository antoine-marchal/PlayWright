# playwright-groovy-launcher

Author: ant1mcl

## Overview

This project provides a simple launcher to run Groovy scripts that utilize Microsoft Playwright for browser automation. It is designed to make it easy to execute Groovy-based Playwright scripts from the command line using a single JAR file.

## Features
- Launch and run Groovy scripts with Playwright support
- Example script included: `PlaywrightEdgeGroovyDemo.groovy`
- Supports running scripts in Microsoft Edge (Chromium)

## Requirements
- Java 8 or higher
- Maven
- Microsoft Edge (Chromium-based) installed (for Edge automation)

## Build Instructions

1. Clone the repository:
   ```sh
   git clone <repo-url>
   cd playwright-groovy-launcher
   ```
2. Build the project with Maven:
   ```sh
   mvn clean package
   ```
   This will generate a JAR file with dependencies in the `target` directory.

## Usage

To run a Groovy Playwright script (e.g., `PlaywrightEdgeGroovyDemo.groovy`):

```sh
java -jar .\target\playwright-demo-1.0-SNAPSHOT-jar-with-dependencies.jar .\src\main\groovy\org\example\PlaywrightEdgeGroovyCookieHeaderDemo.groovy
```

If no script is provided, you will see a usage message:

```
Usage: java -jar playwright-groovy-launcher.jar PlaywrightEdgeGroovyDemo.groovy
```

## Example Script

See `src/main/groovy/org/example/PlaywrightEdgeExample.groovy` for an example of launching Edge, performing GET and POST requests, and closing the browser.

## License

MIT License 

---

Maintained by ant1mcl.