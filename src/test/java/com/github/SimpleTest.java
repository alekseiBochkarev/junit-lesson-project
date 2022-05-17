package com.github;

import config.BaseSetupForSimple;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.YahooPage;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SimpleTest extends BaseSetupForSimple {
    /**Pages**/
    YahooPage yahooPage = new YahooPage();

    @BeforeEach
    void openPage() {
        System.out.println("BEFORE EACH");
        yahooPage.openPage();
    }

    @AfterEach
    void afterEach () {
        System.out.println("AFTER EACH");
        getWebDriver().close();
    }

    @Disabled("Lesson6")
    @DisplayName("Demo disabled test")
    @Test
    void junitTest () {
        Assertions.assertTrue(3 > 2, "assert true 3 > 2 - failed");
        Assertions.assertFalse(3 < 2, "assert false 3 < 2 - failed");
    }

    @DisplayName("Демонстрационный тест № 2")
    @Test
    void secondTest() {
        System.out.println("TEST secondTest");
        Assertions.assertTrue(3 > 2, "assert true 3 < 2 - failed");
    }

    @DisplayName("Demo test with gitHub")
    @Test
    void thirdTest() {
        System.out.println("TEST yahoo");
        yahooPage.searchValue("junit");
        yahooPage.checkSearchResultsContainValue("junit");
        yahooPage.checkUrlContainsValue("junit");
    }
}
