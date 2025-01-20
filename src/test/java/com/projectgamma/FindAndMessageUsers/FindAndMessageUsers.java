package com.projectgamma.FindAndMessageUsers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FindAndMessageUsers {

    protected static WebDriver driver = null;
    private static WebDriver second_driver = null;
    protected String test_name = null;
    protected String main_username = null;
    private String second_username = null;

    @Given("Test name is {string}")
    public void setTestName(String arg1) throws Throwable {
        test_name = arg1;
    }

    @Given("Main user is on Pokemon Showdown as {string} with password as {string}")
    public void mainUserGoesToShowdown(String arg1, String arg2) throws Throwable {
        driver = new FirefoxDriver();
        driver.navigate().to("https://play.pokemonshowdown.com/");

        TimeUnit.SECONDS.sleep(2);

        main_username = arg1;
        WebElement choose_name_button = driver.findElement(By.name("login"));
        choose_name_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("username")).sendKeys(arg1);

        TimeUnit.SECONDS.sleep(1);

        WebElement second_choose_name_button = driver.findElement(By.xpath("//button/strong[text()='Choose name']"));
        second_choose_name_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("password")).sendKeys(arg2);
        WebElement login_button = driver.findElement(By.xpath("//button/strong[text()='Log in']"));
        login_button.click();
    }

    @Given("Second user is on Pokemon Showdown as {string} with password as {string}")
    public void secondUserGoesToShowdown(String arg1, String arg2) throws Throwable {
        FirefoxOptions opts = new FirefoxOptions();
        opts.addArguments("-private");
        second_driver = new FirefoxDriver(opts);
        second_driver.navigate().to("https://play.pokemonshowdown.com/");

        TimeUnit.SECONDS.sleep(2);

        second_username = arg1;
        WebElement choose_name_button = second_driver.findElement(By.name("login"));
        choose_name_button.click();

        TimeUnit.SECONDS.sleep(1);

        second_driver.findElement(By.name("username")).sendKeys(arg1);

        TimeUnit.SECONDS.sleep(1);

        WebElement second_choose_name_button = second_driver.findElement(By.xpath("//button/strong[text()='Choose name']"));
        second_choose_name_button.click();

        TimeUnit.SECONDS.sleep(1);

        second_driver.findElement(By.name("password")).sendKeys(arg2);
        WebElement login_button = second_driver.findElement(By.xpath("//button/strong[text()='Log in']"));
        login_button.click();
    }

    @Given("Main user is ignoring {string}")
    public void checkSecondUserIsIgnored(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement find_user_button = driver.findElement(By.name("finduser"));
        find_user_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("data")).sendKeys(arg1);
        WebElement open_button = driver.findElement(By.xpath("//button/strong[text()='Open']"));
        open_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement more_options_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("userOptions"));
        more_options_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement ignore_button = driver.findElement(By.name("toggleIgnoreUser"));

        if (ignore_button.getText().equals("Ignore")) {
            ignore_button.click();
        }

        driver.navigate().refresh();
    }

    @Given("Main user is not ignoring {string}")
    public void checkSecondUserIsNotIgnored(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement find_user_button = driver.findElement(By.name("finduser"));
        find_user_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("data")).sendKeys(arg1);
        WebElement open_button = driver.findElement(By.xpath("//button/strong[text()='Open']"));
        open_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement more_options_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("userOptions"));
        more_options_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement ignore_button = driver.findElement(By.name("toggleIgnoreUser"));

        if (ignore_button.getText().equals("Unignore")) {
            ignore_button.click();
        }

        driver.navigate().refresh();
    }

    @When("Main user searches for {string}")
    public void mainUserSearchesForUser(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement find_user_button = driver.findElement(By.name("finduser"));
        find_user_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("data")).sendKeys(arg1);
        WebElement open_button = driver.findElement(By.xpath("//button/strong[text()='Open']"));
        open_button.click();
    }

    @When("Main user chooses to chat")
    public void mainUserChoosesToChat() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement chat_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("pm"));
        chat_button.click();
    }

    @When("Main user toggles ignore")
    public void mainUserChoosesIgnore() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement more_options_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("userOptions"));
        more_options_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement ignore_button = driver.findElement(By.name("toggleIgnoreUser"));
        ignore_button.click();

        TimeUnit.SECONDS.sleep(1);

        try {
            WebElement close_button = driver.findElement(By.name("close"));
            close_button.click();
        } catch (NotFoundException e) {
            //Close button only exists if chat with the blocked user is not open
            System.out.println("Test: " + test_name + ", Is okay that close button doesn't exist currently.");
        }

        driver.findElement(By.className("ps-overlay")).click();
    }

    @When("Main user chooses to send friend request")
    public void mainUserChoosesFriend() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement more_options_button = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("userOptions"));
        more_options_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement friend_button = driver.findElement(By.name("toggleFriend"));
        friend_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.className("ps-overlay")).click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.className("ps-overlay")).click();
    }

    @When("Second user chats with {string}")
    public void secondUserChoosesToChat(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement find_user_button = second_driver.findElement(By.name("finduser"));
        find_user_button.click();

        TimeUnit.SECONDS.sleep(1);

        second_driver.findElement(By.name("data")).sendKeys(arg1);
        WebElement open_button = second_driver.findElement(By.xpath("//button/strong[text()='Open']"));
        open_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement chat_button = second_driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"))
                .findElement(By.name("pm"));
        chat_button.click();
    }

    @When("Main user sends {string} in chat")
    public void mainUserSendsMessage(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement message_box = driver.findElement(By.name("message"));
        message_box.sendKeys(arg1);
        message_box.sendKeys(Keys.ENTER);
    }

    @When("Second user sends {string} in chat")
    public void secondUserSendsMessage(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement message_box = second_driver.findElement(By.name("message"));
        message_box.sendKeys(arg1);
        message_box.sendKeys(Keys.ENTER);
    }

    @Then("^Main user should see options to interact with second user$")
    public void mainUserSeesOptionsToInteract() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement text_message = driver.findElement(By.className("userdetails"))
                .findElement(By.xpath(".//strong"));

        WebElement button_bar = driver.findElement(By.className("ps-overlay"))
                .findElement(By.className("buttonbar"));

        button_bar.findElement(By.name("challenge"));
        button_bar.findElement(By.name("pm"));
        button_bar.findElement(By.name("userOptions"));

        if (text_message.getText().contains(second_username)) {
            System.out.println("Test: " + test_name + ", Successfully found options to interact");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Expected second user's username, instead found: " + text_message.getText());
        }
    }

    @Then("^Main user should see options to interact with self$")
    public void mainUserSeesOptionsToInteractWithSelf() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement text_message = driver.findElement(By.className("userdetails"))
                .findElement(By.xpath(".//strong"));

        WebElement overlay = driver.findElement(By.className("ps-overlay"));

        overlay.findElement(By.name("pm"));
        overlay.findElement(By.name("login"));
        overlay.findElement(By.name("logout"));

        if (text_message.getText().contains(main_username)) {
            System.out.println("Test: " + test_name + ", Successfully found options to interact with self");
        } else {
            throw new NotFoundException("Test: " + test_name + ", Expected main user's username, instead found: " + text_message.getText());
        }
    }

    @Then("Main user should see {string} in chat from {string}")
    public void checkMainUserChatForMessage(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement pm_box = driver.findElement(By.className("pm-log"));

        String chat_message_class_name = ".chat";
        if (arg2.equals(main_username)) {
            chat_message_class_name += ".mine";
        }

        List<WebElement> messages = pm_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1)) {
                System.out.println("Test: " + test_name + ", Correct message found: " + arg1);
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Message not found: " + arg1);
    }

    @Then("Main user should not see {string} in chat from {string}")
    public void checkMainUserChatForNoSuchMessage(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement pm_box = driver.findElement(By.className("pm-log"));

        String chat_message_class_name = ".chat";
        if (arg2.equals(main_username)) {
            chat_message_class_name += ".mine";
        }

        List<WebElement> messages = pm_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1)) {
                throw new RuntimeException("Test: " + test_name + ", Message found not as intended: " + arg1);
            }
        }

        System.out.println("Test: " + test_name + ", Message not found as intended: " + arg1);
    }

    @Then("Second user should see {string} in chat from {string}")
    public void checkSecondUserChatForMessage(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement pm_box = second_driver.findElement(By.className("pm-log"));

        String chat_message_class_name = ".chat";
        if (arg2.equals(second_username)) {
            chat_message_class_name += ".mine";
        }

        List<WebElement> messages = pm_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1)) {
                System.out.println("Test: " + test_name + ", Correct message found: " + arg1);
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Message not found: " + arg1);
    }

    @Then("Second user should see friend request from {string}")
    public void checkSecondUserSeesFriendRequest(String arg1) throws Throwable {
        WebElement pm_box = second_driver.findElement(By.className("pm-log"));

        String chat_message_class_name = ".chat";

        List<WebElement> messages = pm_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1) && text.contains("sent you a friend request!")) {
                System.out.println("Test: " + test_name + ", Friend request found from: " + arg1);
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Friend request not found from: " + arg1);
    }

    @After
    public void closeDriver() {
        driver.close();
    }

    @After
    public void closeSecondDriver() {
        if (second_driver != null) {
            second_driver.close();
            second_driver = null;
        }
    }
}
