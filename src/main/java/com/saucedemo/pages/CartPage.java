package com.saucedemo.pages;

import com.saucedemo.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{
    private By continueShoppingButton= By.id("continue-shopping");
    private By checkOutButton= By.xpath("//*[@id=\"checkout\"]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

//         * Dynamically removes any item from the cart based on its name
//         * @param productName e.g., "Sauce Labs Backpack"
//         */
        public void removeItemFromCart(String productName) {
            // Convert "Sauce Labs Backpack" to "sauce-labs-backpack"
            String formattedName = productName.toLowerCase().replace(" ", "-");

            // Dynamic XPath using the ID pattern
            By removeBtn = By.id("remove-" + formattedName);

            click(removeBtn);
            LogUtils.info("Removed " + productName + " from cart.");
        }

        public void clickCheckout() {
            click(checkOutButton);
        }

        public void clickContinueShoppingButton(){
        click(continueShoppingButton);
        }
    }
