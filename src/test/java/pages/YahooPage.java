package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import config.BaseSetupForSimple;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class YahooPage extends BaseSetupForSimple {
    SelenideElement searchInputField =$("input#ybar-sbq._yb_1q4yo");

    public YahooPage openPage () {
        open("/");
        getWebDriver().manage().window().maximize();
        return this;
    }

    public void searchValue (String value) {
        searchInputField.setValue(value).pressEnter();
    }

    public void checkUrlContainsValue (String value) {
        String expectedQueryParams = value;
        Assertions.assertTrue(getWebDriver().getCurrentUrl().contains(expectedQueryParams), "Url doesn't contain value " + value );
    }

    public void checkSearchResultsContainValue (String value) {
        $$x("//h3[@class='title']/a")
                .find(Condition.text(value))
                .shouldBe(Condition.visible, Duration.ofMillis(WAITING_TIMEOUT));
    }

}
