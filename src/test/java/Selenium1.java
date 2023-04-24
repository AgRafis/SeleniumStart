import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Selenium1 {
    static ChromeDriver driver;

    @BeforeAll
    public static void setBeforeAll() {

        System.setProperty("webdriver.http.factory", "jdk-http-client");
    }

    @BeforeEach
    public void openBeforeEach() {
        driver = new ChromeDriver();
        driver.get("https://www.tkani-feya.ru/");
    }

    @AfterEach
    public void closeAfterEach() {
        driver.close();
    }

    @Test
    @DisplayName("Поиск товара")
    @Description("Ищет конкретный товар, в результате поиска должен быть только один товар")
    public void shouldSearchByTextile(){
        final String itemName = "Шифон Хамелеон";

        step("Ввод конкретного товара в строку поиска", () -> {
            driver.findElement(By.name("find")).sendKeys(itemName + Keys.ENTER);
        });

        step("Основная проверка страницы", () -> {
            assertEquals(driver.getCurrentUrl(), "https://www.tkani-feya.ru/fabrics/?find=%D0%A8%D0%B8%D1%84%D0%BE%D0%BD+%D0%A5%D0%B0%D0%BC%D0%B5%D0%BB%D0%B5%D0%BE%D0%BD");
            assertTrue(driver.findElement(By.tagName("h1")).isDisplayed()); //isDisplayed() - значит отображается

            assertTrue(driver.findElement(By.cssSelector(".text-block .name")).getText().contains(itemName));
            assertEquals("Каталог тканей",
                    driver.findElement(By.tagName("h1")).getText());
        });
        // проверяет единственный элемент

//        assertTrue(driver.findElements(By.cssSelector(".text-block .name")).get(0).getText().contains(itemName));
        // Без цикла проверяет только первый элемент

//        for ( WebElement el : driver.findElements(By.cssSelector(".text-block .name"))) {
//              assertTrue(el.getText().contains(itemName));
//        }
        // В цикле проверяются все элементы содержащие .text-block .name.
    }

    @Test
    @DisplayName("Вызываем заглушку")
    @Description("Ввод в строке поиска рандомное название и тем самым вызываем заглушку")
    public void shouldNegativeSearchByTextile() {
        final String itemName1 = "башкир";

        step("Ввод рандомного слова в строку поиска", () -> {
            driver.findElement(By.name("find")).sendKeys(itemName1 + Keys.ENTER);
        });

        step("Основная проверка страницы", () -> {
            assertEquals(driver.getCurrentUrl(), "https://www.tkani-feya.ru/fabrics/?find=%D0%B1%D0%B0%D1%88%D0%BA%D0%B8%D1%80");
            assertTrue(driver.findElement(By.className("hidden-xs")).isDisplayed());
            //cssSelector(".alert alert-warning") не находит
            assertEquals("По вашему запросу ничего не найдено",
                    driver.findElement(By.cssSelector(".alert-warning")).getText());
        });
    }

}
