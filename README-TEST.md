# AutomationExercise Project - Test Instructions

### Overview
This project includes both UI and API tests for AutomationExercise. The tests are structured to allow running UI and API tests separately or together. The UI tests support running in headless mode, with the ability to choose the browser through environment variables.

#### Requirements
- **Java 11** (or later)
- **Maven**
- **Selenium WebDriver**
- **Cucumber**
- **TestNG**
- **RestAssured**
- **Allure** for test reporting

## Getting started

## 1. Cloning a repository
First, clone the repository with this project to your local machine:
https://github.com/JAVA-QA-AUTO-UA-RB/autoqa-final-IrinaPleshakova

## 2. Opening in IntelliJ IDEA
Open the project in IntelliJ IDEA to work with code and tests.

---

#### Requirements
Before running the tests, make sure you have installed:

- **Java JDK 11** or higher
- **Apache Maven** 3.6 or higher
- **Browser** (Chrome or Firefox)

#### Dependencies

The project uses the following dependencies:
- **Selenium WebDriver**
- **Cucumber**
- **TestNG**
- **RestAssured**
- **Allure** for test reporting
These dependencies are specified in pom.xml and will be automatically loaded when the project is built using Maven.

#### Project Structure
```shell
autoqa-final-IrinaPleshakova/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       ├── java/
│       │   ├── api/
│       │   │   ├── clients/
│       │   │   ├── models/
│       │   │   ├── runners/
│       │   │   └── stepsdef/
│       │   ├── ui/
│       │   │   ├── pages/
│       │   │   ├── runners/
│       │   │   └── stepsdef/
│       │   └── utils/
│       │       ├── ConfigProvider.java
│       │       ├── DriverManager.java
│       │       ├── Hooks.java
│       │       ├── ScreenshotUtil.java
│       │       ├── TestDataGenerator.java
│       │       └── UserApiHelper.java
│   └── resources/
│       └── features/
│           ├── api/
│           └── ui/
├── pom.xml
├── README_TEST.md
└── testng.xml
```

### Summary of Folders:
- **`api/clients/`**: Contains classes for making API requests. These handle interaction with various API endpoints such as account creation, deletion, and retrieving user details.
- **`api/models/`**: Holds POJO (Plain Old Java Object) classes that represent the data models used in API testing.
- **`api/runners/`**: Contains classes responsible for executing API tests, typically configured to run with TestNG or another framework.
- **`api/stepsdef/`**: Step definitions for API tests, written in Cucumber format, linking feature file steps to their corresponding Java methods.
- **`ui/pages/`**: Holds Page Object Model (POM) classes for UI tests. Each class corresponds to a webpage and contains methods for interacting with page elements.
- **`ui/runners/`**: Classes for executing UI tests. These are responsible for organizing test runs and supporting browser configuration.
- **`ui/stepsdef/`**: Step definitions for UI tests, written in Cucumber format, linking feature file steps to POM methods.
- **`utils/`**: Utility classes used in both API and UI tests. Includes:
  - `ConfigProvider`: Manages configuration properties for the tests.
  - `DriverManager`: Manages WebDriver instances for browser interactions, including headless mode.
  - `Hooks`: Contains `@Before` and `@After` methods to set up and tear down the test environment.
  - `ScreenshotUtil`: Captures screenshots during tests.
  - `TestDataGenerator`: Generates random data for tests, using tools like Faker.
  - `UserApiHelper`: Contains utility methods for working with API user-related actions.
- **`resources/features/`**: Contains Cucumber feature files, separated into two categories:
  - **API**: Feature files for API tests, detailing scenarios such as account creation, deletion, product search, etc.
  - **UI**: Feature files for UI tests, describing scenarios like login, registration, adding/removing products from the cart, and filtering products.
- **`pom.xml`**: Project Object Model file for managing dependencies and project configuration using Maven.
- **`README_TEST.md`**: Documentation for tests, explaining how to run them, tools used, and configuration options for different testing modes.
- **`testng.xml`**: Configuration file for TestNG, specifying which tests to run, how to group them, and parallel execution settings.

### Test description: click details
<details>

### 1. Create Account API
#### Positive Scenario: Create account with valid data
- **Description**: This test verifies the creation of a new account with valid data.
- **Goal**: Ensure that account creation works correctly with valid user data.

#### Negative Scenario: Create account with existing email
- **Description**: This test checks for error handling when attempting to create an account with an already registered email.
- **Goal**: Verify that the server correctly handles duplicate emails and returns an appropriate error message.

---

### 2. Delete Account API
#### Positive Scenario: Delete account with valid credentials
- **Description**: This test verifies the successful deletion of an account with valid credentials.
- **Goal**: Ensure that an account can be deleted successfully with the correct credentials.

#### Negative Scenario: Delete account with invalid credentials
- **Description**: This test checks for error handling when attempting to delete an account with invalid credentials.
- **Goal**: Verify error handling when attempting to delete a non-existent account.

---

### 3. Products List API
#### Positive Scenario: Get All Products List
- **Description**: This test verifies fetching a complete list of products.
- **Goal**: Ensure that the products list is retrieved correctly with all required fields and valid data.

#### Negative Scenario: Get products list with invalid endpoint
- **Description**: This test checks the API behavior when making a request to an invalid endpoint.
- **Goal**: Verify that the server returns an error for incorrect requests.

---

### 4. Get User Detail By Email API
#### Positive Scenario: Get user detail with valid email
- **Description**: This test verifies the successful retrieval of user details using a valid email.
- **Goal**: Ensure that user details can be fetched with a valid email.

#### Negative Scenario: Get user detail with invalid email
- **Description**: This test checks for error handling when requesting user details with a non-existent email.
- **Goal**: Verify that the server returns an appropriate error for invalid email requests.

---

### 5. Search Product API
#### Positive Scenario: POST to Search product with valid name
- **Description**: This test verifies successful product search with a valid product name.
- **Goal**: Ensure that product search works correctly with a valid name.

#### Negative Scenario: POST to Search product without search_product parameter
- **Description**: This test checks for error handling when attempting to search for a product without providing the `search_product` parameter.
- **Goal**: Verify that the server handles requests without required parameters correctly.

---

### 6. Verify Login API
#### Positive Scenario: Verify login with valid credentials
- **Description**: This test verifies successful login verification with valid credentials.
- **Goal**: Ensure that login verification works correctly with valid credentials.

#### Negative Scenario: Verify login with invalid credentials
- **Description**: This test checks for error handling when attempting to verify login with incorrect credentials.
- **Goal**: Verify that the server returns an appropriate error for invalid login attempts.

---

### 7. Adding and Removing Products from Cart (UI)
#### Positive Scenario: Add random products to cart
- **Description**: This test verifies adding random products to the cart.
- **Goal**: Ensure that products are successfully added to the cart and displayed correctly.

#### Positive Scenario: Remove a product from the cart
- **Description**: This test verifies the removal of a product from the cart.
- **Goal**: Ensure that a product is successfully removed from the cart, and the remaining items are displayed correctly.

---

### 8. User Login (UI)
#### Positive Scenario: Successful login with valid credentials
- **Description**: This test verifies successful login with valid credentials.
- **Goal**: Ensure that a user can successfully log in with valid credentials.

#### Negative Scenario: Unsuccessful login with invalid credentials
- **Description**: This test checks for error handling when attempting to log in with incorrect credentials.
- **Goal**: Verify that an appropriate error message is displayed for invalid login attempts.

---

### 9. User Logout (UI)
#### Positive Scenario: Successful logout
- **Description**: This test verifies the successful logout of a user.
- **Goal**: Ensure that a user can log out successfully and is redirected to the homepage.

---

### 10. Placing an Order (UI)
#### Positive Scenario: Place Order - Register while Checkout
- **Description**: This test verifies successful order placement with user registration during checkout.
- **Goal**: Ensure that a new user can register and complete an order.

#### Positive Scenario: Place Order - Login before Checkout
- **Description**: This test verifies successful order placement after logging in.
- **Goal**: Ensure that an authenticated user can complete an order successfully.

---

### 11. Viewing Product Information (UI)
#### Positive Scenario: View product details
- **Description**: This test verifies successful viewing of product information.
- **Goal**: Ensure that product details such as name, price, availability, and brand are displayed correctly.

---

### 12. User Registration (UI)
#### Positive Scenario: Successful user registration
- **Description**: This test verifies successful user registration.
- **Goal**: Ensure that a new user can register with valid data and log in successfully.

---

### 13. Viewing and Filtering Products by Category (UI)
#### Positive Scenario: View all products
- **Description**: This test verifies viewing all products on the page.
- **Goal**: Ensure that all products are displayed correctly.

#### Positive Scenario: Filter products by category "Women > Dress"
- **Description**: This test verifies successful filtering of products by category "Women > Dress."
- **Goal**: Ensure that product filtering by category works as expected.
</details>
---

### Test Configuration Using the Configuration File

To select a browser and enable headless mode, open the `config.properties` file and modify the following fields:

- **BROWSER**: 'chrome' or 'firefox'
- **HEADLESS**: 'true' for headless mode or 'false' for regular mode

Example:
properties
BROWSER=chrome
HEADLESS=tru

#### Selecting a Browser

To choose a browser, use the environment variable `BROWSER`. Supported values are:

- `chrome` - Google Chrome
- `firefox` - Mozilla Firefox

#### Enabling/Disabling Headless Mode

To run the browser in headless mode, use the environment variable `HEADLESS`. Accepted values:

- `true` - run in headless mode
- `false` - run with the browser interface

### Priority of Settings

1. **Environment variables** take precedence over values from the configuration file `config.properties`.
2. **Configuration files** take precedence over default values.

---

## 3. Running Tests

### Running All Tests
To **simply run** the tests without additional parameters, use the following command:

```bash
mvn clean test
```
This command will execute all the tests defined in the `.feature` files.

### Running API Tests
To **run the API tests** only, use the following command:
```shell
mvn clean test -DtestType=api
```

### Running UI Tests
To **run the UI tests**, use the following command:
```shell
mvn clean test -DtestType=ui
```

### Using Environment Variables

#### Running with Chrome in headless mode
```bash
export BROWSER=chrome
export HEADLESS=true
mvn test -DtestType=ui
```
#### Running with Firefox in regular mode
```bash
export BROWSER=firefox
export HEADLESS=false
mvn test -DtestType=ui
```

### Passing Parameters via Maven Command Line

#### Running with Chrome in headless mode
```bash
mvn clean test -DtestType=ui -DBROWSER=chrome -DHEADLESS=true
```

#### Running with Firefox in regular mode
```bash
mvn clean test -DtestType=ui -DBROWSER=firefox -DHEADLESS=false
```

### Additional Examples

#### Running with Chrome in regular mode
```bash
mvn clean test -DtestType=ui -DBROWSER=chrome -DHEADLESS=false
```

#### Running with Firefox in headless mode
```bash
mvn clean test -DtestType=ui -DBROWSER=firefox -DHEADLESS=true
```

#### Console Output Example
If the tests pass successfully, you will see the following output in the console:

```shell
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 325.1 s -- in TestSuite
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  05:29 min
[INFO] Finished at: 2024-10-11T20:00:56+02:00
[INFO] ------------------------------------------------------------------------
```
---

## 4. Generate reports with Allure

To generate reports after running tests using Maven, execute the following or another command:

```bash
mvn clean test
```

After the tests are completed, generate the Allure report with this command:

```bash
mvn allure:serve
```

This command will start a local server with the report, which you can view in your browser.

### Generating a Static Report

If you want to generate a static report without starting a server, use the following command:

```bash
mvn allure:report
```

The report will be saved in the `target/site/allure-maven-plugin` folder.










