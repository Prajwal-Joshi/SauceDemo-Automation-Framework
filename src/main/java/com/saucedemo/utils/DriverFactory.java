package com.saucedemo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver initDriver(String browser, String headless) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--incognito", "--disable-notifications");

                if (headless.equalsIgnoreCase("true")) {
                    chromeOptions.addArguments("--headless", "--window-size=1920,1080", "--no-sandbox");
                }
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                // Firefox: Disable Password Manager and Enable Private Mode
                ffOptions.addPreference("signon.rememberSignons", false);
                ffOptions.addArguments("-private");

                if (headless.equalsIgnoreCase("true")) {
                    ffOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(ffOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);
                edgeOptions.addArguments("-inprivate");

                if (headless.equalsIgnoreCase("true")) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        return driver;
    }
}