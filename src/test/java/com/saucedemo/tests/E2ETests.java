package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class E2ETests extends BaseTest {

    @Test(priority = 1, description = "E2E_01: Verify standard user can complete a full purchase flow")
    public void testStandardUserE2E(){
        LogUtils.info("Starting E2E Test: Standard user Purchase");

        //LOGIN
        LoginPage loginPage= new LoginPage(driver);
        loginPage.login("standard_user");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),"Login failed!");

        //INVENTORY

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.addProductToCartByName("Sauce Labs Fleece Jacket");
        //Assert.assertEquals(inventoryPage.getCartBadgeCount(),"2","Cart count mismatch!");
        inventoryPage.goToCart();

        // CART: Verify items and proceed

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        //CHECKOUT: Checkout info

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckoutDetails("Prajwal", "Automation", "560001");

        //OVERVIEW: Final finish

        LogUtils.info("Confirming Order on Overview Page");
        checkoutPage.clickFinish();

        // CONFIRMATION

        String succussMsg = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(succussMsg,"Thank you for your order!", "E2E Order Completion Failed!");

        LogUtils.info("E2E Test Completed Successfully!");
    }


        @Test(priority = 2, description = "E2E_02: Verify problem_user journey and catch Last Name field bug")
        public void testProblemUserE2E() {
            LogUtils.info("Starting E2E Test: Problem User");

            // 1. LOGIN (Using the refactored method)
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("problem_user");
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Problem user login failed!");

            // 2. INVENTORY: Add item
            InventoryPage inventoryPage = new InventoryPage(driver);
            inventoryPage.addBackpackToCart();
            inventoryPage.goToCart();

            // 3. CART: Proceed to checkout
            CartPage cartPage = new CartPage(driver);
            cartPage.clickCheckout();

            // 4. CHECKOUT: The "Buggy" Step
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            LogUtils.info("Attempting to enter details for problem_user...");

            // The problem_user has a bug where Last Name is often blocked or ignored
            checkoutPage.completeCheckoutDetails("Prajwal", "Joshi", "560001");

            // 5. VALIDATION: Check if the error appears
            // Because we expect this to fail, we assert that an error message is displayed
            String error = checkoutPage.getErrorMessage();
            LogUtils.error("Detected Error: " + error);

            Assert.assertTrue(error.contains("Last Name is required"),
                    "Problem user bug NOT detected! Expected an error regarding Last Name.");
        }

    @Test(priority = 3, description = "E2E_03: Verify performance_glitch_user can complete flow despite delays")
    public void testPerformanceUserE2E() throws InterruptedException {
        LogUtils.info("Starting E2E Test: Performance Glitch User");

        // 1. LOGIN (This step will take 5 seconds)
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("performance_glitch_user");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"))));

        // Assert that we eventually land on the inventory page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Performance user login failed!");
        LogUtils.info("Successfully navigated past the performance glitch.");

        // 2. INVENTORY: Add multiple items
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.addProductToCartByName("Sauce Labs Onesie");
        inventoryPage.goToCart();

        // 3. CART: Proceed
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // 4. CHECKOUT: Enter Info
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckoutDetails("Prajwal", "Performance", "560001");

        // 5. FINISH: Complete the journey
        checkoutPage.clickFinish();

        // 6. VALIDATION
        String successMsg = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(successMsg, "Thank you for your order!", "Performance E2E failed!");

        LogUtils.info("Performance E2E Test Completed Successfully!");
    }

    @Test(priority = 4, description = "E2E_04: Verify cart persistence across sessions")
    public void testCartPersistenceE2E() {
        LogUtils.info("Starting E2E Test: Cart Persistence");

        // 1. First Login & Add Item
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");

        // 2. Logout
        // Note: You'll need a logout method in your InventoryPage/BasePage
        inventoryPage.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("index") || driver.getCurrentUrl().equals("https://www.saucedemo.com/"));

        driver.manage().deleteAllCookies();
        driver.navigate().to(ConfigReader.getProperty("url"));

        LogUtils.info("Attempting Second Login");
        // 3. Second Login
        loginPage.login("standard_user");

        // 4. Verify Persistence
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1", "Cart was cleared after logout!");
        inventoryPage.goToCart();
        LogUtils.info("Landed on Cart Page");

        // 5. Complete Flow
        new CartPage(driver).clickCheckout();
        LogUtils.info("Processing Checkout Step - 1");
        new CheckoutPage(driver).completeCheckoutDetails("Prajwal", "Persistence", "560001");

        CheckoutPage checkout = new CheckoutPage(driver);
        LogUtils.info("Processing Checkout Step -2");
        checkout.clickFinish();

        LogUtils.info("Checkout Processed");
        Assert.assertEquals(checkout.getConfirmationMessage(), "Thank you for your order!");
        LogUtils.info("Thank you for your order!");
    }

    }

