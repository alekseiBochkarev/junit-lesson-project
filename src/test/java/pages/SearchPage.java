package pages;

import com.codeborne.selenide.Condition;
import config.BaseSetup;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SearchPage extends BaseSetup {

    public void checkUrlContainsValue (String value) {
        String expectedQueryParams = value;
        Assertions.assertTrue(getWebDriver().getCurrentUrl().contains(expectedQueryParams), "Url doesn't contain value " + value );
    }

    public void checkSearchResultsContainValue (String value) {
        $$(".repo-list a[href].v-align-middle")
                .find(Condition.text(value))
                .shouldBe(Condition.visible);
    }

    public void checkResultsContainDescription (String value) {
        $$("p.mb-1")
                .find(Condition.text(value))
                .shouldBe(Condition.visible);
    }

    public void checkResultsContainListTopics (List<String> topics) {
        for (String topic: topics) {
            $$("[data-ga-click='Topic, search results']")
                    .find(Condition.text(topic))
                    .shouldBe(Condition.visible);
        }
    }

    public void checkMenuItem (String value) {
        $$(".menu-item")
                .find(Condition.text(value))
                .shouldBe(Condition.visible, Duration.ofMillis(WAITING_TIMEOUT))
                .click();
        $(".menu-item.selected").shouldHave(Condition.text(value), Duration.ofMillis(WAITING_TIMEOUT));
        String expectedQueryParams = value;
        Assertions.assertTrue(getWebDriver().getCurrentUrl().contains(expectedQueryParams.toLowerCase()), "Url doesn't contain value " + value );
    }
}
