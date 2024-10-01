package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private WebDriver driver;
    private By cartTitle = By.cssSelector("h1.cart-title");
    private By totalPrice = By.id("total-price");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCartTitle() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle)).getText();
    }

    public String getTotalPrice() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice)).getText();
    }
}