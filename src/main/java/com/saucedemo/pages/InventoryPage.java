package com.saucedemo.pages;

import com.saucedemo.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage{
    private By hamBurgerMenu= By.id("react-burger-menu-btn");
    private By logoutOption= By.id("logout_sidebar_link");
    private By shoppingCartIcon= By.id("shopping_cart_container");
    private By shoppingCartBadge= By.xpath("//span[@class='shopping_cart_badge']");
    //private By shoppingCartItemNumber = (By) shoppingCartBadge.findElement((SearchContext) By.linkText(""));

    private By productFilter= By.xpath("//select[@class='product_sort_container']");
    private By productNames= By.xpath("//div[@data-test='inventory-item-name']");
    private By itemPrices = By.className("inventory_item_price");

    private By addToCartSauceLabsBackpack = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");


    public InventoryPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getAllProductNames() {
        // Use findElements to get the list of WebElements
        List<WebElement> elements = driver.findElements(productNames);
        List<String> namesList = new ArrayList<>();

        // For-each loop to extract text from each element
        for (WebElement element : elements) {
            String text = element.getText();
            namesList.add(text);
            System.out.println("Found Product: " + text); // Helpful for debugging
        }

        return namesList;
    }
    public void openMenu() {
        click(hamBurgerMenu);
        LogUtils.info("Sidebar menu opened");
    }

    public void clickLogout() {
        openMenu(); // Ensures menu is open before clicking logout
        click(logoutOption);
        LogUtils.info("Logout button clicked");
    }

    public void goToCart() {
        click(shoppingCartIcon);
        LogUtils.info("Navigating to Shopping Cart");
    }

    public String getCartBadgeCount() {
        try {
            return getText(shoppingCartBadge);
        } catch (Exception e) {
            return "0"; // Return 0 if badge isn't present (cart is empty)
        }
    }

    public void selectSortOption(String optionText) {
        // Note: Since this is a <select> tag, you can use Selenium's Select class
        // or just click it if your BasePage 'click' handles it.
        click(productFilter);
        click(By.xpath("//option[text()='" + optionText + "']"));
        LogUtils.info("Sorted products by: " + optionText);
    }

    // Specific method for Smoke Test
    public void addBackpackToCart() {
        click(addToCartSauceLabsBackpack);
        LogUtils.info("Backpack added to cart");
    }

    // Dynamic method for any product (Crucial for your 25 scripts!)
    public void addProductToCartByName(String productName) {
        String formattedName = productName.toLowerCase().replace(" ", "-");
        By dynamicBtn = By.id("add-to-cart-" + formattedName);
        click(dynamicBtn);
        LogUtils.info("Added to cart: " + productName);
    }

  //Filter/Sorting  related methods:


    public String getFirstItemName() {
        return getText(productNames);
    }

    public String getFirstItemPrice() {
        return getText(itemPrices);
    }




}

