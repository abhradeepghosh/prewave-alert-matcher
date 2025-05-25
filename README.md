# 🚨 Prewave Alert Matcher

A Java Maven application to match alerts against query terms using either live API calls or local mock data. Built to be clean, testable, and easy to understand.

---

## 🧠 Problem Overview

Given a list of `alerts` and a list of `query terms`, match an alert if:
- The alert text **matches** the query term
- Both are in the **same language**
- If `keepOrder = true`, the query must appear as an exact phrase
- If `keepOrder = false`, all query words must be present, order doesn't matter

---

## 🏗️ Project Structure

```
src/
├── main/
│   └── java/
│       ├── model/              # Alert and QueryTerm definitions
│       ├── service/            # Main logic (Main, ApiService, MatcherService)
│       └── util/               # TextMatcher (core match logic)
│
├── test/
│   └── java/
│       ├── unit/               # Unit tests for matcher and API service
│       ├── integration/        # Mock and stress tests
│       └── util/               # TestDataLoader.java
│
└── resources/
    ├── mock-alerts.json
    └── mock-query-terms.json
```

---

## 🚀 How to Run

### 1️⃣ Build the project

```bash
mvn clean install
```

### 2️⃣ Run the live matcher

```bash
mvn exec:java -Dexec.mainClass="service.Main"
```

### 3️⃣ Run all tests

```bash
mvn test
```

### 4️⃣ Run individual test classes

```bash
# Run mock data integration test
mvn test -Dtest=MockDataTest

# Run stress test (200 live API runs)
mvn test -Dtest=StressLoadTest
```

---

## 🧪 Tests Included

| Test Class            | Type           | Description                              |
|------------------------|----------------|------------------------------------------|
| `MatcherServiceTest`  | Unit Test      | 8 case-based tests (ordered, unordered, edge cases) |
| `ApiServiceTest`      | Unit Test      | Live API fetch tests                     |
| `MockDataTest`        | Integration    | Runs matcher against local mock JSON     |
| `StressLoadTest`      | Stress Test    | Runs 200x live API fetch + match cycles  |

> Logs and console output are included to help reviewers understand test behavior.

---

## 📂 Test Data

Stored under `src/test/resources/`

```text
mock-alerts.json        # Sample alerts in different languages
mock-query-terms.json   # Query terms with keepOrder true/false
```

Test data covers:
- Single and multiple content per alert
- Ordered/unordered term configurations
- Case-insensitive and multilingual scenarios

---

## 🔐 API Key

For demo and assignment purposes:

```text
abhradeep:d90ee0f6f7e33455329d00919c362ecfc5d2e92e0679be4265f9b07029ae3e30
```

> 🛡 In production, this should be stored securely (e.g. environment variable).  
> For assignment simplicity, it's hardcoded to avoid setup issues.

---

## 📌 Key Highlights

- ✅ Clean code structure with clear separation of responsibilities
- ✅ Strong unit test coverage with edge cases
- ✅ Integration tests validate end-to-end behavior with mock data
- ✅ Stress test simulates high-volume usage and logs failures gracefully
- ✅ Rich, structured console logs in all test outputs

---

## 🛠 Tech Stack

- Java 21
- Apache HttpClient 5
- org.json for JSON handling
- JUnit 5 for testing
- Maven for build and dependency management

---

## 🔧 Logging

For simplicity, this project uses `System.out.println` for regular logs and `System.err.println` for error messages.

> 🛠 In a production environment, it's recommended to use a structured logging framework like **SLF4J** with **Logback** for better control, filtering, and configurability.

---

<sub>
📌 This project was committed in a single push for assignment clarity.  
In a production setting, the implementation would follow incremental commits and CI-based version control best practices.
</sub>

---

## 🙌 Thanks

This project is designed with clarity, maintainability, and testability in mind.
The logs, test cases, and structure aim to make behavior easy to understand and extend.

Happy reviewing! 😊
