package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private By categoryMenu = By.id("category-menu");
    private By subcategoryMenu = By.id("subcategory-menu");
    private By addToCartButton = By.cssSelector(".add-to-cart");
    private By cartPopup = By.id("cart-popup");
    private By totalAmount = By.id("total-amount");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCategory(String category, String subcategory) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(categoryMenu)).click();
        driver.findElement(By.linkText(category)).click();
        wait.until(ExpectedConditions.elementToBeClickable(subcategoryMenu)).click();
        driver.findElement(By.linkText(subcategory)).click();
    }

    public void addToCart(int quantity) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (int i = 0; i < quantity; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        }
    }

    public boolean validateCartPopup() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartPopup)).isDisplayed();
    }

    public String getTotalAmount() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount)).getText();
    }
}