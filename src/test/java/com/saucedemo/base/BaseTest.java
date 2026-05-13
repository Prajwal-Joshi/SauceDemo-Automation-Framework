package com.saucedemo.base;

import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    private final String url = ConfigReader.getProperty("url");
    private final String headless = ConfigReader.getProperty("headless");

    public WebDriver getDriver() {
        return driver;
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, ITestContext context) {
        // Use the Factory to get the driver
        driver = DriverFactory.initDriver(browser, headless);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);

        // Share the driver with the context for your TestListener
        context.setAttribute("WebDriver", driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}