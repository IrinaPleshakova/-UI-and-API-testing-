[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/ArpVjtXn)
# Курсове завдання

## Мета

Розробити автоматизаційне тестове рішення для сайту [AutomationExercise](https://automationexercise.com/), яке включає
UI та API тести, з використанням сучасних інструментів для тестування.

## Технічні вимоги

- **Мова програмування**: Java 11
- **Проєкт**: Maven
- **Фреймворк для тестування**: TestNG
- **BDD фреймворк**: Cucumber
- **Паттерн проектування для WebUI**: Page Object Model (POM)
- **Репорти**: Allure
- **API тести**: Cucumber/TestNG/RestAssured/Gson/Lombok
- **Інтеграція з інструментами**: Allure для створення детальних звітів про тести, `log4j` для логування
- **IDE**: IntelliJ IDEA
- **CI**: GitHub Actions

## Структура проєкту та branching-strategy

0. Перед початком виконання завдання рекомендується уважно ознайомитися з усим завданням, це дозволить вам сформувати
   для себе приблизну послідовність дій та мати на увазі рекомендації та обмеження
1. Базову структуру проєкту вам надано, ви можете самі вирішувати як структурувати ваш код, конфігураційні та
   feature-файли
2. Перед початком виконання завдання відбранчуйтеся з `main`, створіть бранчу назва якої починається на `qa_` (
   напр. `qa_final_project_rodion_baronov`)
3. Після завершення проєкту - створіть пул-реквест в напрямку `main` вашого репозиторію, додайте `rodikno` рев'ювером
4. Після створення фінального пул-реквесту відмітьте відповідне домашнє завдання в Google Classroom виконаним, додайте
   посилання на Pull Request

## Загальні кроки

1. **Налаштування базового проекту**:
    - Використайте початкову структуру Maven проекту, яку вам надано в цьому репозиторії
    - Підключіть необхідні залежності: TestNG, Cucumber, Selenium, Allure, Gson, Lombok, log4j

2. **UI тестування (через Selenium)**:
    - **Page Object Model**: Реалізуйте моделі для усіх необхідних сторінок сайту
    - **Сценарії WebUI тестування**: Імплементуйте в коді кілька тестових сценаріїв на основі функціональності сайту,
      самостійно
      оберіть як розподілити сценарії по feature-файлам:
        - Реєстрація користувача
        - Вхід в систему з коректними та некоректними даними
        - Вихід з системи
        - Перегляд всіх продуктів та фільтрація по категорії (Women -> Dress)
        - Перевірка інформації на сторінці продукту (View Product Page)
        - Додавання продукту(ів) в корзину та видалення з корзини
        - Оформлення замовлення при непустій корзині (Place Order)
    - Використовуйте **Cucumber** для опису тестових сценаріїв у форматі BDD (Given/When/Then).

3. **API тестування (через RestAssured)**:
    - [Документація Automation Excercise API](https://automationexercise.com/api_list)
    - Напишіть Cucumber-сценарії для перевірки функціоналу REST API ресурсів/ендпоінтів:
        - POST https://automationexercise.com/api/createAccount
        - POST https://automationexercise.com/api/verifyLogin
        - GET https://automationexercise.com/api/getUserDetailByEmail
        - DELETE https://automationexercise.com/api/deleteAccount
        - GET https://automationexercise.com/api/productsList
        - POST https://automationexercise.com/api/searchProduct
    - Використовуйте бібліотеку на зразок **Gson** для десеріалізації відповідей з API або вбудовані методи RestAssured
      для роботи з DTO/POJO, уникайте використання RestAssured безпосередньо в Step Definitions, створіть класи-клієнти
      на зразок "ProductsAPIClient" з методами, з яких вже будуть відправлятися запити з допомогою RestAssured
    - Використовуйте `Lombok` для автоматичної генерації геттер/сеттер методів та конструктора в DTO/POJO класах

4. **Інтеграція з Allure**:
    - Налаштуйте Allure для генерації тестових звітів.
    - Додайте кроки у ваші тести для більш детальної інформації в звітах.

5. **Оформлення фінального проєкту**:
    - Використовуйте Cucumber Scenario Outlines та Data Tables там, де це може бути корисним
    - Додайте файл `README_TEST.md` і вкажіть опис проєкту і інструкції по запуску, додайте приклад виводу Maven при
      виконанні `mvn test`
    - Додайте в `README_TEST.md` інструкції по генерації тестового репорту `Allure`

## Покрокові завдання

1. **Структура проєкту**:
    - Базова структура Maven-проєкту вже доступна у вашому репозиторії, розширюйте її під ваші задачі відповідно до
      рекомендацій розробників інструментів, що використовуються
    - Налаштуйте залежності в `pom.xml` для усіх інструментів та бібліотек, які ви будете використовувати

2. **Створення POM/Page Object Classes для веб-сторінок**:
    - Створіть класи для основних сторінок сайту за паттерном Page Object (
      наприклад, `LoginPage`, `ProductPage`, `RegistrationPage`).
    - Реалізуйте основні методи для роботи з елементами сторінок.

3. **BDD сценарії (Cucumber)**:
    - Створіть `.feature` файли з тестовими сценаріями, розділіть фічі/сценарії, що перевіряють WebUI і API
    - Напишіть `step definitions`, які зв'язуватимуть сценарії з методами POM.

4. **API тести**:
    - Напишіть тести для API з використанням RestAssured для вказаних ендпоінтів/ресурсів
    - Для кожного ендпоінту додайте один позитивний і один негативний сценарій, негативний сценарій провокуватиме
      повернення `4хх` або `5хх` статус коду
    - Використайте Gson/RestAssured для перетворення JSON відповіді у Java об'єкти

5. **Репорти та Allure**:
    - Налаштуйте Allure для збору детальних звітів після запуску тестів.
    - Переконайтеся, що кожен тест генерує репорт з усіма необхідними кроками та результатами.
    - Ваш фреймворк має бути здатним запускати UI і API тести та генерувати Allure-звіти після кожного запуску.

6. **Конфігурування та CI**:
    - Користувач фреймворку має мати змогу запустити UI і API тести окремо або запустити усі наявні тести разом
    - Додайте можливість запуску UI тестів з використанням
      Headless-browser (https://www.selenium.dev/blog/2023/headless-is-going-away/), а також з можливістю вибора
      браузера через вказання відповідних Environment-variables, додайте інструкції в `README_TEST.md` - як запускати
      тести в різних режимах і з різними браузерами
    - Налаштуйте GitHub Actions Workflow для запуску тестів через мануальний тригер, а також по пушу
      в `main` або гілки, назва яких починається на `qa_`, додайте архівацію тестових звітів та можливість перегляду
      спрощеного Junit-звіту після виконання тестів в
      CI-системі (для запуску Selenium тестів в Github Actionc CI доведеться використовувати тільки headless-mode,
      оскільки CI-воркери не мають встановленої UI-оболонки)

## Додаткові поради

- Не беріться за все одразу, почніть розвивати ваш фреймворк з WebUI частини, додаючи спочатку Cucumber-сценарії, потім
  Step Definitions для них, потім відповідні POM-класи та DTO-класи, попіклуйтеся про запуск ваших тестів через Maven та
  створення тестового звіту і запуск в CI вже після того, як будете впевнені, що тести є працездатними
- Важливо дотримуватися принципів чистого коду [Java Clean Code](https://www.baeldung.com/java-clean-code)
- Уникайте `Thread.sleep()`, використовуйте підхід з Implicit/Explicit Waits з
  допомогою [Selenium Waiting strategies](https://www.selenium.dev/documentation/webdriver/waits/)
- Пишіть тести таким чином, щоб їх можна було легко підтримувати та масштабувати.
- Для глобальної конфігурації створіть конфігураційний файли/файли, де користувач зможе вказувати дані на
  зразок `BASE_URI`, данних для автентифікації і т.п. і створіть `ConfigProvider` клас, з якого через статичні методи
  можна буде отримувати значення конфігураційних параметрів у вашому коді, не забувайте надати значення за замовчуванням
  для усих параметрів - далі користувач вже сам матиме змогу вказати щось специфічне перед запуском тестів
- Використовуйте коментарі для пояснення складних частин коду.
- Використовуйте бібліотеку для логування на зразок `log4j`
- Ви можете використовувати додаткові бібліотеки та інструменти за власним бажанням (наприклад `faker` для генерації
  даних)
- Ви можете користуватися допомогою AI-інструменту (напр. ChatGPT), але виконати проєкт повноцінно не вийде без повного
  осмислення власних дій :)

## Критерії оцінювання

Оцінювання проходить по 100-бальній системі, декомпозиція задач для оцінювання є наступною:

| Приймальний критерій                                                                                                                  | Балів до нарахування |
|---------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| [ОБОВ'ЯЗКОВО] Усі UI сценарії реалізовано в повному обсязі з використанням вказаних інструментів та підходів                          | 30                   |
| [ОБОВ'ЯЗКОВО] Усі API сценарії реалізовано в повному обсязі з використанням вказаних інструментів та підходів                         | 20                   |
| Фреймворк інтегрований з Allure, надано інструкції для створення та перегляду Allure репорту                                          | 20                   |
| Для UI тестів - додано можливість обирати браузер а також виконувати UI тести в headless режимі                                       | 10                   |
| Додано інтеграцію з GitHub Actions згідно завдання + є мінімум один успішний білд з пройденими тестами                                | 10                   |
| У Step Definitions для UI тестів - не викликаються методи Selenium безпосередньо, для API тестів - не викликаються методи RestAssured | 10                   |
| У проєкті не використовуються `System.out.println()` та `Thread.sleep()`                                                              | 10                   |
| Додано `README_TEST.md` згідно з завданням                                                                                            | 10                   |

**Курсова вважається виконаною успішно, якщо сумарний набраний бал >= 70**

