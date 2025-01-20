package com.projectgamma.UsernamesAndLogin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UsernamesAndLogin {

    protected static WebDriver driver = null;
    protected String test_name = null;
    protected String main_username = null;

    @Given("^User is on Pokemon Showdow$")
    public void goToShowdown() throws Throwable {
        driver = new FirefoxDriver();
        driver.navigate().to("https://play.pokemonshowdown.com/");

        TimeUnit.SECONDS.sleep(2);
    }

    @Given("Test name is {string}")
    public void setTestName(String arg1) throws Throwable {
        test_name = arg1;
    }

    @When("User tries to login with username as {string}")
    public void enterUsername(String arg1) throws Throwable {
        main_username = arg1;
        WebElement choose_name_button = driver.findElement(By.name("login"));
        choose_name_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("username")).sendKeys(arg1);

        TimeUnit.SECONDS.sleep(1);

        WebElement second_choose_name_button = driver.findElement(By.xpath("//button/strong[text()='Choose name']"));
        second_choose_name_button.click();
    }

    @When("User enters password as {string}")
    public void enterPassword(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.name("password")).sendKeys(arg1);
        WebElement login_button = driver.findElement(By.xpath("//button/strong[text()='Log in']"));
        login_button.click();
    }

    @When("^User opens the options menu$")
    public void openOptions() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement options_button = driver.findElement(By.name("openOptions"));
        options_button.click();
    }

    @When("^User clicks the logout button$")
    public void clickLogout() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement logout_button = driver.findElement(By.name("logout"));
        logout_button.click();
    }

    @When("^User clicks the register button$")
    public void clickRegister() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement register_button = driver.findElement(By.name("register"));
        register_button.click();
    }

    @When("User tries to login with the registration info with {string} as a password and {string} as the Pokemon displayed")
    public void enterRegisterationInfo(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.name("password")).sendKeys(arg1);
        driver.findElement(By.name("cpassword")).sendKeys(arg1);
        driver.findElement(By.name("captcha")).sendKeys(arg2);

        WebElement register_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.xpath(".//button[contains(., 'Register')]"));
        register_button.click();
    }

    @Then("^Login should succeed$")
    public void checkLoginSuccess() throws Throwable {
        TimeUnit.SECONDS.sleep(2);

        WebElement username_text = driver.findElement(By.className("usernametext"));

        if (username_text.getText().equals(main_username)) {
            System.out.println("Test: " + test_name + ", Login Suceeded");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Login Failed, message: " + username_text.getText());
        }
    }

    @Then("^User should be prompted for a password$")
    public void checkPromptedForPassword() throws Throwable {
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.name("password"));
        System.out.println("Test: " + test_name + ", Prompted for password Succeeded");
    }

    @Then("^Login should fail$")
    public void checkLoginFail() throws Throwable {
        TimeUnit.SECONDS.sleep(2);

        WebElement error_message = driver.findElement(By.className("error"));

        if (error_message.getText().equals("Wrong password.")) {
            System.out.println("Test: " + test_name + ", Login rejection Succeeded");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Unexpected login rejection message: " + error_message.getText());
        }
    }

    @Then("^User should be notified of being successfully logged out$")
    public void checkLogoutMessage() throws Throwable {
        TimeUnit.SECONDS.sleep(2);

        WebElement text_message = driver.findElement(By.className("ps-popup"))
                .findElement(By.xpath(".//form/p"));

        if (text_message.getText().contains("You have been logged out and disconnected.")) {
            System.out.println("Test: " + test_name + ", Logout Succeeded");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Unexpected logout message: " + text_message.getText());
        }
    }

    @Then("^User should be notified of being successfully registered$")
    public void checkRegisteredMessage() throws Throwable {
        TimeUnit.SECONDS.sleep(2);

        WebElement text_message = driver.findElement(By.className("ps-popup"))
                .findElement(By.xpath("//form/p"));

        if (text_message.getText().contains("You have been successfully registered.")) {
            System.out.println("Test: " + test_name + ", Registration Succeeded");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Unexpected registration message: " + text_message.getText());
        }

        WebElement close_button = driver.findElement(By.name("close"));
        close_button.click();
    }

    @After
    public void closeDriver() {
        driver.close();
    }
}
