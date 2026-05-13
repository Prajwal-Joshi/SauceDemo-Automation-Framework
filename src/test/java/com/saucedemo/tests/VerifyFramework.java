package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;

public class VerifyFramework extends BaseTest {

    @Test(description = "Verify browser opens and URL is loaded")
    public void testFrameworkSetup() {
        // Verify the title to ensure the page loaded
        String title = driver.getTitle();
        System.out.println("Page Title is: " + title);
        Assert.assertEquals(title, "Swag Labs");
    }

    @Test(description = "Intentional failure to test Screenshot Listener")
    public void testListenerFeature() {
        // This will fail to trigger your TestListener
        Assert.fail("Checking if the listener captures a screenshot on failure.");
    }
}