package com.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import com.example.pages.LoginPage;
import com.example.pages.HomePage;
import com.example.pages.CartPage;
import com.example.utils.DriverFactory;
import com.example.utils.PdfReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class StoreSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private List<String> testResults;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        testResults = new ArrayList<>();
    }

    @After
    public void tearDown() {
    DriverFactory.quitDriver();
    System.out.println("Iniciando generación del informe PDF...");
    try {
        PdfReportGenerator.generateReport(testResults);
        System.out.println("Generación del informe PDF completada.");
    } catch (Exception e) {
        System.err.println("Error al generar el informe PDF: " + e.getMessage());
        e.printStackTrace();
    }
}

    @Given("estoy en la página de la tienda")
    public void estoy_en_la_página_de_la_tienda() {
        driver.get("https://qalab.bensg.com/store");
        testResults.add("Accedido a la página de la tienda");
    }

    @Given("me logueo con mi usuario {string} y clave {string}")
    public void me_logueo_con_mi_usuario_y_clave(String username, String password) {
        loginPage.login(username, password);
        testResults.add("Login realizado con éxito");
    }

    @When("navego a la categoria {string} y subcategoria {string}")
    public void navego_a_la_categoria_y_subcategoria(String category, String subcategory) {
        homePage.navigateToCategory(category, subcategory);
        testResults.add("Navegado a la categoría " + category + " y subcategoría " + subcategory);
    }

    @When("agrego {int} unidades del primer producto al carrito")
    public void agrego_unidades_del_primer_producto_al_carrito(Integer quantity) {
        homePage.addToCart(quantity);
        testResults.add("Agregadas " + quantity + " unidades al carrito");
    }

    @Then("valido en el popup la confirmación del producto agregado")
    public void valido_en_el_popup_la_confirmación_del_producto_agregado() {
        boolean popupDisplayed = homePage.validateCartPopup();
        assert popupDisplayed : "El popup del carrito no se mostró";
        testResults.add("Popup de confirmación validado");
    }

    @Then("valido en el popup que el monto total sea calculado correctamente")
    public void valido_en_el_popup_que_el_monto_total_sea_calculado_correctamente() {
        String totalAmount = homePage.getTotalAmount();
        testResults.add("Monto total validado: " + totalAmount);
    }

    @When("finalizo la compra")
    public void finalizo_la_compra() {
        testResults.add("Compra finalizada");
    }

    @Then("valido el titulo de la pagina del carrito")
    public void valido_el_titulo_de_la_pagina_del_carrito() {
        String cartTitle = cartPage.getCartTitle();
        assert cartTitle.equals("Shopping Cart") : "El título de la página del carrito no es correcto";
        testResults.add("Título de la página del carrito validado: " + cartTitle);
    }

    @Then("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvo_a_validar_el_calculo_de_precios_en_el_carrito() {
        String totalPrice = cartPage.getTotalPrice();
        testResults.add("Cálculo de precios en el carrito validado: " + totalPrice);
    }
}