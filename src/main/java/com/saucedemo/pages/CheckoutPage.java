package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
   private By firstNameTextField= By.id("first-name");
   private By lastNameTextField= By.id("last-name");
   private By zipOrPostalCodeField= By.id("postal-code");
   private By cancelButton= By.id("cancel");
   private By continueButton= By.id("continue");
   private By checkOutCancelButton= By.id("cancel");
   private By checkOutFinishButton= By.id("finish");
   private By successMessage = By.xpath("//*[@id=\"checkout_complete_container\"]/h2");
   private By backHomneButton= By.id("back-to-products");

    private By errorContainer = By.cssSelector("h3[data-test='error']");

    public String getErrorMessage() {
        return getText(errorContainer); // Reuses your BasePage's wait logic
    }
   public CheckoutPage(WebDriver driver) {
        super(driver);
    }


    // 1. Individual "Atomic" Actions (Reusable for any scenario)
    public void enterFirstName(String fName) {
        type(firstNameTextField, fName);
    }

    public void enterLastName(String lName) {
        type(lastNameTextField, lName);
    }

    public void enterZipCode(String zip) {
        type(zipOrPostalCodeField, zip);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickFinish() {
        click(checkOutFinishButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public void clickBackHome(){
       click(backHomneButton);
    }

    // 2. High-Level Helper (Best for your Smoke Test)
    public void completeCheckoutDetails(String fName, String lName, String zip) {
        enterFirstName(fName);
        enterLastName(lName);
        enterZipCode(zip);
        clickContinue();
    }

    public String getConfirmationMessage() {
        return getText(successMessage);
    }
}


