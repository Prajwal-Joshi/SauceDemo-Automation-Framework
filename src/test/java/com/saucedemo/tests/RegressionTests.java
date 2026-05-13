package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegressionTests extends BaseTest {

    @DataProvider(name="loginData")
    public Object[][] getLoginData(){
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"invalid_user", "invalid_pass", "Epic sadface: Username and password do not match any user in this service"}
        };

    }

    @Test(dataProvider = "loginData", priority = 1, description = "REG_01-05: Login Negative Scenarios")
    public void testLoginValidation(String user, String pass, String expectedError){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user,pass);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }

    // --- DATA PROVIDER FOR CHECKOUT FAILURES ---
    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return new Object[][]{
                {"", "Joshi", "560001", "Error: First Name is required"},
                {"Prajwal", "", "560001", "Error: Last Name is required"},
                {"Prajwal", "Joshi", "", "Error: Postal Code is required"}
        };
    }

    @Test(dataProvider = "checkoutData", priority = 2, description = "REG_06-08: Checkout Form Validations")
    public void testCheckoutFields(String fname, String lname, String zip, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getProperty("standard_user"), ConfigReader.getProperty("password"));

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckoutDetails(fname, lname, zip);
        checkoutPage.clickContinue(); // Trigger validation

        Assert.assertEquals(checkoutPage.getErrorMessage(), expectedError);
    }

    // --- DATA PROVIDER MATCHED TO YOUR TEXT-BASED METHOD ---
    @DataProvider(name = "sortData")
    public Object[][] getSortData() {
        return new Object[][]{
                {"Name (Z to A)", "Test.allTheThings() T-Shirt (Red)"},
                {"Name (A to Z)", "Sauce Labs Backpack"},
                {"Price (low to high)", "$7.99"},
                {"Price (high to low)", "$49.99"}
        };
    }

    @Test(dataProvider = "sortData", priority = 3, description = "REG_09-12: Inventory Sorting Validation")
    public void testProductSorting(String sortText, String expectedFirstItem) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getProperty("standard_user"));

        InventoryPage inventoryPage = new InventoryPage(driver);
        // Calls your existing method with the full text
        inventoryPage.selectSortOption(sortText);

        if (sortText.contains("Price")) {
            Assert.assertEquals(inventoryPage.getFirstItemPrice(), expectedFirstItem);
        } else {
            Assert.assertEquals(inventoryPage.getFirstItemName(), expectedFirstItem);
        }
    }
}
