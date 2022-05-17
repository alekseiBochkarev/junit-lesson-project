package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class StartPage {
    SelenideElement searchInputField =$("input[data-test-selector=nav-search-input]");

    public StartPage openPage () {
        open("/");
        getWebDriver().manage().window().maximize();
        return this;
    }

    public void searchValue (String value) {
        searchInputField.setValue(value).pressEnter();
    }

}
