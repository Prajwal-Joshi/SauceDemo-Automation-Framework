package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SmokeTests extends BaseTest {

    @Test(priority = 1)
    public void testSmokeLogin(){
        LogUtils.info("Starting Smoke Test");

        LoginPage loginpage= new LoginPage(driver);

                loginpage.enterUsername("standard_user");
                loginpage.enterPassword();
                loginpage.clickLogin("standard_user");
        LogUtils.info("Login Succussful");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        LogUtils.info("Landed on Product Inventory page");

    }
    @Test(priority = 2,dependsOnMethods = "testSmokeLogin")
    public void testAllProductsDisplayed() {

        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> allProducts = inventoryPage.getAllProductNames();

        // Verify the count (SauceDemo always has 6 items)
        Assert.assertEquals(allProducts.size(), 6, "Product count mismatch!");
        // Verify a specific item exists in the list
        Assert.assertTrue(allProducts.contains("Sauce Labs Backpack"), "Backpack not found in list!");
        LogUtils.info("All the products fetched and listed");
        //return allProducts;
    }

    //Add product to the cart
    @Test(priority = 3,dependsOnMethods = "testSmokeLogin")
    public void addProductToCart(){
        InventoryPage inventoryPage= new InventoryPage(driver);
      //  inventoryPage.addProductToCartByName("Sauce Labs Fleece Jacket");
        inventoryPage.addBackpackToCart();
       // Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");

        inventoryPage.goToCart();
        LogUtils.info("Products added to the cart");
    }
    @Test(priority = 4, dependsOnMethods = "addProductToCart")
    public void verifyCheckoutAndOrderCompletion() {
        LogUtils.info("Starting Checkout Process");

        // 1. Navigate from Cart
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // 2. Enter Information
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckoutDetails("Prajwal", "QA", "560001");

        // 3. Finish and Verify
        checkoutPage.clickFinish();
        String actualMessage = checkoutPage.getConfirmationMessage();

        Assert.assertEquals(actualMessage, "Thank you for your order!", "Order failed!");
        LogUtils.info("Smoke Test: Order completed successfully.");
    }

}
