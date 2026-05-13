package com.saucedemo.pages;

import com.saucedemo.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // 1. Private Locators - Keep these hidden from the tests
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private final By errorContainer = By.cssSelector("h3[data-test='error']");
    // 2. Constructor - Just pass the driver to the parent (BasePage)
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 3. Action Methods - These do the work!
    public void enterUsername(String username) {
        type(usernameField, username); // Uses BasePage's wait-and-type logic
    }

    public void enterPassword() {
        type(passwordField, ConfigReader.getProperty("password"));
    }

    public void clickLogin(String standardUser) {
        click(loginButton); // Uses BasePage's wait-and-click logic
    }

    public void login(String username) {
        login(username, ConfigReader.getProperty("password"));
    }
    // 4. Combined Helper (Great for multiple scripts)
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
        handleBrowserAlert();
    }

    // NEW: Overloaded method for Regression (allows invalid passwords)
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public String getErrorMessage() {
        return getText(errorContainer);
    }
}