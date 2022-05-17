package com.github;

import com.github.domain.MenuItem;
import config.BaseSetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pages.SearchPage;
import pages.StartPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SearchTest extends BaseSetup {
    /**Pages**/
    StartPage startPage = new StartPage();
    SearchPage searchPage = new SearchPage();

    @BeforeEach
    void openPage() {
        System.out.println("BEFORE EACH");
        startPage.openPage();
    }

    @AfterEach
    void afterEach () {
        System.out.println("AFTER EACH");
    }

    @ValueSource(strings =  {
            "selenide",
            "abstracta"
    })
    @ParameterizedTest(name = "Check search by word {0} on GitHub")
    void gitSearchDataTest(String testData) {
        startPage.searchValue(testData);
        searchPage.checkUrlContainsValue(testData);
        searchPage.checkSearchResultsContainValue(testData);
    }

    @CsvSource({
            "selenide, Selenide + TestContainers (Docker) sample project",
    "abstracta, IBL course materials for an undergraduate first-semester abstract algebra course that emphasizes visualization.",
            "jmeter, The JMeter Maven Plugin",
            "appium, AppiumLibrary is an appium testing library for RobotFramework"
    })
    @ParameterizedTest(name = "search by word {0}, expect description: {1}")
    void gitSearchCsvSourceTest (String testData, String ExpectedResult) {
        startPage.searchValue(testData);
        searchPage.checkUrlContainsValue(testData);
        searchPage.checkResultsContainDescription(ExpectedResult);
    }

    @CsvSource(value = {
            "appium | Appium Server in Desktop GUIs for Mac, Windows, and Linux",
            "dbeaver | Free universal database tool and SQL client"
    },
    delimiter = '|')
    @ParameterizedTest(name = "search by word {0}, expect description: {1}")
    void gitSearchCsvSourceDelimiterTest(String testData, String ExpectedResult){
        startPage.searchValue(testData);
        searchPage.checkUrlContainsValue(testData);
        searchPage.checkResultsContainDescription(ExpectedResult);
    }

    static Stream<Arguments> gitSearchMethodSourceTest() {
        List<String> listForDbeaver = new ArrayList<>();
        listForDbeaver.add("java");
        listForDbeaver.add("mysql");
        listForDbeaver.add("database");
        listForDbeaver.add("nosql");
        listForDbeaver.add("sqlserver");
        listForDbeaver.add("oracle");
        List<String> listForAppium = new ArrayList<>();
        listForAppium.add("appium");
        listForAppium.add("android");
        listForAppium.add("automation");
        listForAppium.add("webdriver");
        listForAppium.add("ios");
        listForAppium.add("windows");
        return Stream.of(
                Arguments.of("dbeaver", listForDbeaver),
                Arguments.of("appium", listForAppium)
        );
    }

    @MethodSource("gitSearchMethodSourceTest")
    @ParameterizedTest (name = "search by word {0}, expect List topics")
    void gitSearchMethodSourceTest(String testData, List<String> expectedTopics) {
        startPage.searchValue(testData);
        searchPage.checkUrlContainsValue(testData);
        searchPage.checkResultsContainListTopics(expectedTopics);
    }

    @EnumSource(MenuItem.class)
    @ParameterizedTest(name = "search menu item {0}")
    void githubEnumSearchTest(MenuItem testData) {
        startPage.searchValue("apppium");
        searchPage.checkMenuItem(
                testData.engName
        );
    }


}
