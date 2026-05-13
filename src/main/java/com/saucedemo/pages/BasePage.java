package com.saucedemo.pages;

import com.saucedemo.utils.LogUtils;
import com.saucedemo.utils.WaitUtils; // Import your utility
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    private final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // A "Smart" click that always waits first
    protected void click(By locator) {
        WaitUtils.waitForElementClickable(driver, locator, DEFAULT_TIMEOUT).click();
    }

    // A "Smart" type that always waits for visibility
    protected void type(By locator, String text) {
        WebElement element = WaitUtils.waitForElementVisible(driver, locator, DEFAULT_TIMEOUT);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.waitForElementVisible(driver, locator, DEFAULT_TIMEOUT).getText();
    }

    public void handleBrowserAlert() {
        try {
            // Switch to the alert and dismiss it (clicks Cancel/No/Close)
            driver.switchTo().alert().dismiss();
            LogUtils.info("Browser alert detected and dismissed.");
        } catch (Exception e) {
            // No alert was present; continue normally
        }
    }
    }