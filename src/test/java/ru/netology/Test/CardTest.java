package ru.netology.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CardTest {
    public WebDriver driver;

    @BeforeAll
    static void setAppAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setApp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void quit() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSuccessTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79219999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }


    @Test
    public void shouldFailEnglish() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vasileva Anna");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79219999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldFailNumbers() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("89219990000");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79219999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldFailPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79219990099");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldFailPhone2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+9219990099");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79219990009");
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void EmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79219990099");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void EmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильева Анна");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void EmptyNameAndPhone() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void AllIsEmpty() {
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }




}

