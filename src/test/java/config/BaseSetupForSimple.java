package config;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseSetupForSimple {
    public static final long WAITING_TIMEOUT = 10000;

    @BeforeAll
    static void setUp() {
        System.out.println("BEFORE ALL");
        Configuration.holdBrowserOpen = false;
        Configuration.baseUrl = "https://yahoo.com";
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AFTER ALL");
    }
}
