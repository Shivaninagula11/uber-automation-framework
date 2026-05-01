# 🚗 Uber Clone Test Automation Framework

> A production-level test automation framework built with **Java**, **Selenium WebDriver**, **TestNG** — featuring real Uber website testing, cookie-based authentication, cross-browser execution, Docker integration, and CI/CD pipeline via GitHub Actions.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Cookie-Based Authentication](#cookie-based-authentication)
- [Test Reports](#test-reports)
- [Test Scenarios](#test-scenarios)
- [Docker Integration](#docker-integration)
- [CI/CD Pipeline](#cicd-pipeline)
- [Author](#author)

---

## Overview

This framework provides a robust, scalable foundation for end-to-end UI test automation against the **real Uber website**. It follows industry best practices including the **Page Object Model (POM)** design pattern, **cookie-based authentication** to handle OTP login flows, **ThreadLocal WebDriver** for parallel execution, and **Docker** for containerized test execution.

The framework handles one of the most challenging aspects of modern web automation — **OTP-based authentication** — using a professional cookie injection approach used by top QA teams in the industry.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Selenium WebDriver 4.x | Browser automation |
| TestNG | Test execution & management |
| Maven | Build & dependency management |
| WebDriverManager | Automatic driver management |
| ExtentReports | HTML test reporting |
| Allure Reports | Advanced interactive reporting |
| Apache POI | Excel data-driven testing |
| Docker | Containerized test execution |
| GitHub Actions | CI/CD pipeline |
| Log4j | Logging framework |

---

## 📁 Project Structure

```
uber-automation-framework/
├── .github/
│   └── workflows/
│       └── run-tests.yml        # GitHub Actions CI/CD
├── docker/
│   └── Dockerfile               # Docker configuration
├── src/
│   ├── main/java/com/uberframework/
│   │   ├── pages/               # Page Object classes
│   │   │   ├── LoginPage.java
│   │   │   ├── HomePage.java
│   │   │   ├── BookRidePage.java
│   │   │   └── PaymentPage.java
│   │   ├── utils/               # Utility classes
│   │   │   ├── DriverFactory.java
│   │   │   ├── CookieUtils.java
│   │   │   └── ScreenshotUtils.java
│   │   └── config/
│   │       └── ConfigReader.java
│   └── test/java/com/uberframework/
│       ├── base/
│       │   └── BaseTest.java
│       └── tests/
│           ├── LoginTest.java
│           ├── BookRideTest.java
│           └── PaymentTest.java
├── testdata/                    # Excel test data
├── screenshots/                 # Failure screenshots
├── logs/                        # Test logs
├── docker/                      # Docker files
├── testng.xml                   # Test suite config
└── pom.xml                      # Maven dependencies
```

---

## ✨ Key Features

### 🔐 Cookie-Based Authentication
Uber uses OTP-based authentication which cannot be automated traditionally. This framework implements **professional cookie injection** — the same approach used by enterprise QA teams — to bypass OTP and test authenticated flows.

### 🌐 Real Uber Website Testing
Locators are extracted directly from **Uber's live HTML** using Chrome DevTools, ensuring tests run against the actual production website with real elements.

### ⚡ Parallel Cross-Browser Execution
Using **ThreadLocal WebDriver**, tests run simultaneously across Chrome, Firefox, and Edge without interference.

### 📸 Auto Screenshot on Failure
Every test failure automatically captures a screenshot saved to the `screenshots/` folder and attached to the Extent Report.

### 🐳 Docker Integration
Run the entire test suite in a containerized environment — no local setup required.

### ⚙️ CI/CD Pipeline
Tests trigger automatically on every GitHub push via **GitHub Actions**.

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17+
- Maven 3.6+
- Google Chrome browser
- Git

### Installation

```bash
# Clone the repository
git clone https://github.com/Shivaninagula11/uber-automation-framework.git

# Navigate to project
cd uber-automation-framework

# Install dependencies
mvn clean install -DskipTests
```

### Configuration

Update `src/main/resources/config.properties`:

```properties
browser=chrome
url=https://auth.uber.com/v2/
email=your@email.com
headless=false
```

---

## ▶️ Running Tests

### Run Login Tests
```bash
mvn clean test -Dtest=LoginTest
```

### Run specific test
```bash
mvn clean test -Dtest=LoginTest#testCookieBasedLogin
```

### Run all tests
```bash
mvn clean test
```

### Run in headless mode
```bash
mvn clean test -Dheadless=true
```

---

## 🍪 Cookie-Based Authentication

### Why Cookie Authentication?

Uber uses OTP (One-Time Password) authentication which makes traditional automation impossible. This framework solves this using **session cookie injection**:

```java
// Inject session cookies to bypass OTP
CookieUtils.injectUberCookies(driver);

// Navigate to authenticated page
driver.get("https://www.uber.com");
```

### How to Get Fresh Cookies

1. Open Chrome and login to `https://www.uber.com`
2. Press **F12** → Application → Cookies → uber.com
3. Copy values of `sid` and `csid` cookies
4. Update `CookieUtils.java` with fresh values
5. Run tests immediately (cookies expire after a few hours)

> **Note:** Never commit cookie values to GitHub. Add `CookieUtils.java` to `.gitignore` in production.

---

## 📊 Test Reports

After execution, reports generate automatically:

| Report | Location | Description |
|--------|----------|-------------|
| Extent Report | `test-output/ExtentReport.html` | Visual HTML dashboard |
| Allure Report | `allure-results/` | Interactive report |
| TestNG Report | `test-output/index.html` | Default TestNG report |
| Screenshots | `screenshots/` | Auto-captured on failure |

### Generate Allure Report
```bash
mvn allure:serve
```

---

## 🧪 Test Scenarios

### 🔐 Login Tests
- ✅ Verify login page loads correctly
- ✅ Verify Continue button is visible
- ✅ Verify valid email is accepted
- ✅ Verify invalid email handling
- ✅ Verify empty field validation
- ✅ Verify cookie-based authentication

### 🚗 Book Ride Tests
- ✅ Verify home page loads
- ✅ Search for a ride
- ✅ Select ride type (UberGo, Premier, Auto)
- ✅ Verify fare estimate display
- ✅ Confirm ride booking
- ✅ Cancel a ride
- ✅ Schedule a ride

### 💳 Payment Tests
- ✅ Add credit/debit card
- ✅ Add UPI payment
- ✅ Apply promo code
- ✅ Verify fare breakdown
- ✅ Check wallet balance

---

## 🐳 Docker Integration

### Build Docker image
```bash
docker build -f docker/Dockerfile -t uber-automation .
```

### Run tests in Docker
```bash
docker run uber-automation
```

---

## ⚙️ CI/CD Pipeline

Tests run automatically on every push to `main` branch via **GitHub Actions**:

```yaml
on:
  push:
    branches: [ main ]
```

**Pipeline Steps:**
1. ✅ Checkout code
2. ✅ Setup Java 17
3. ✅ Setup Chrome
4. ✅ Run tests headless
5. ✅ Upload Extent Report
6. ✅ Upload screenshots on failure

---

## 👩‍💻 Author

**Shivani Nagula**
📧 [GitHub Profile](https://github.com/Shivaninagula11)

---

> ⭐ If you find this project useful, consider giving it a star on GitHub!
