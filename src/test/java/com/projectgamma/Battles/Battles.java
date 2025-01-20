package com.projectgamma.Battles;

import java.util.List;
import java.util.Random;
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

public class Battles {

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

        String zoomOut = Keys.chord(Keys.CONTROL, "-");
        driver.findElement(By.tagName("html")).sendKeys(zoomOut);
        driver.findElement(By.tagName("html")).sendKeys(zoomOut);

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

        String zoomOut = Keys.chord(Keys.CONTROL, "-");
        second_driver.findElement(By.tagName("html")).sendKeys(zoomOut);
        second_driver.findElement(By.tagName("html")).sendKeys(zoomOut);

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

    @When("Main user searches for {string} and sends a {string} battle request")
    public void mainUserSendsBattleRequest(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement find_user_button = driver.findElement(By.name("finduser"));
        find_user_button.click();

        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("data")).sendKeys(arg1);
        WebElement open_button = driver.findElement(By.xpath("//button/strong[text()='Open']"));
        open_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement challenge_button = driver.findElement(By.name("challenge"));
        challenge_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement format_drop_down = driver.findElement(By.name("format"));
        format_drop_down.click();

        WebElement format_option = driver.findElement(By.name("formats"))
                .findElement(By.xpath(".//button[@value='" + arg2 + "']"));
        format_option.click();

        WebElement make_challenge_button = driver.findElement(By.name("makeChallenge"));
        make_challenge_button.click();
    }

    @When("Main user battles with a random opponent in the {string} format")
    public void mainUserChallengesRandom(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement format_drop_down = driver.findElement(By.name("format"));
        format_drop_down.click();

        WebElement format_option = driver.findElement(By.name("formats"))
                .findElement(By.xpath(".//button[@value='" + arg1 + "']"));
        format_option.click();

        WebElement make_challenge_button = driver.findElement(By.name("search"));
        make_challenge_button.click();

        int total = 0;
        while (total < 30) {
            TimeUnit.SECONDS.sleep(2);
            total++;
            try {
                driver.findElement(By.name("closeRoom"));
                return;
            } catch (NotFoundException e) {
                //Just keep looking for battle room to form
            }
        }
    }

    @When("^Second user accepts challenge$")
    public void secondUserAcceptsChallenge() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement accept_button = second_driver.findElement(By.name("acceptChallenge"));
        accept_button.click();
    }

    @When("^Second user rejects challenge")
    public void secondUserRejectsChallenge() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement reject_button = second_driver.findElement(By.name("rejectChallenge"));
        reject_button.click();
    }

    @When("Main user sends {string} in battle chat")
    public void mainUserSendsMessage(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement message_box = driver.findElement(By.className("battle-log-add"))
                .findElement(By.xpath("./form/textarea[2]"));
        message_box.sendKeys(arg1);
        message_box.sendKeys(Keys.ENTER);
    }

    @When("Second user sends {string} in battle chat")
    public void secondUserSendsMessage(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement message_box = second_driver.findElement(By.className("battle-log-add"))
                .findElement(By.xpath("./form/textarea[2]"));
        message_box.sendKeys(arg1);
        message_box.sendKeys(Keys.ENTER);
    }

    @When("^Main user forfeits and exits the battle$")
    public void mainUserForfeits() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        driver.findElement(By.name("closeRoom")).click();
        driver.findElement(By.className("ps-popup")).findElement(By.xpath(".//button/strong[text()='Forfeit']")).click();
    }

    @When("^Main user chooses a random lead Pokemon$")
    public void mainUserChoosesRandomLead() throws Throwable {
        TimeUnit.SECONDS.sleep(5);

        Random rand = new Random();
        List<WebElement> switch_bar = driver.findElement(By.className("switchmenu"))
                .findElements(By.name("chooseTeamPreview"));
        int randSwitch = rand.nextInt(switch_bar.size());

        switch_bar.get(randSwitch).click();
    }

    @When("^Second user chooses a random lead Pokemon$")
    public void secondUserChoosesRandomLead() throws Throwable {
        TimeUnit.SECONDS.sleep(5);

        Random rand = new Random();
        List<WebElement> switch_bar = second_driver.findElement(By.className("switchmenu"))
                .findElements(By.name("chooseTeamPreview"));
        int randSwitch = rand.nextInt(switch_bar.size());

        switch_bar.get(randSwitch).click();
    }

    @When("Main user imports a team for {string} using")
    public void mainUserImportsTeam(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement teambuilder_button = driver.findElement(By.xpath("//button[@value='teambuilder']"));
        teambuilder_button.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement new_team_button = driver.findElement(By.name("newTop"));
        new_team_button.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement format_drop_down = driver.findElement(By.className("format-select"))
                .findElement(By.name("format"));
        format_drop_down.click();

        WebElement format_option = driver.findElement(By.xpath("//button[@value='" + arg1 + "']"));
        format_option.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement import_button = driver.findElement(By.name("import"));
        import_button.click();

        WebElement import_box = driver.findElement(By.className("teamedit"))
                .findElement(By.className("textbox"));
        import_box.sendKeys(arg2);

        TimeUnit.SECONDS.sleep(1);

        WebElement save_import_button = driver.findElement(By.name("saveImport"));
        save_import_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement close_room_button = driver.findElement(By.name("closeRoom"));
        close_room_button.click();
    }

    @When("Second user imports a team for {string} using")
    public void secondUserImportsTeam(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement teambuilder_button = second_driver.findElement(By.xpath("//button[@value='teambuilder']"));
        teambuilder_button.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement new_team_button = second_driver.findElement(By.name("newTop"));
        new_team_button.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement format_drop_down = second_driver.findElement(By.className("format-select"))
                .findElement(By.name("format"));
        format_drop_down.click();

        WebElement format_option = second_driver.findElement(By.xpath("//button[@value='" + arg1 + "']"));
        format_option.click();

        TimeUnit.SECONDS.sleep(1);
        WebElement import_button = second_driver.findElement(By.name("import"));
        import_button.click();

        WebElement import_box = second_driver.findElement(By.className("teamedit"))
                .findElement(By.className("textbox"));
        import_box.sendKeys(arg2);

        TimeUnit.SECONDS.sleep(1);

        WebElement save_import_button = second_driver.findElement(By.name("saveImport"));
        save_import_button.click();

        TimeUnit.SECONDS.sleep(1);

        WebElement close_room_button = second_driver.findElement(By.name("closeRoom"));
        close_room_button.click();
    }

    @When("^Battle occurs until a winner is decided$")
    public void battleHappens() throws Throwable {
        while (true) {
            TimeUnit.SECONDS.sleep(12);
            try {
                driver.findElement(By.name("closeAndMainMenu"));
                return;
            } catch (NotFoundException e) {
                //Nothing to do here
            }
            randomBattleAction(driver);
            randomBattleAction(second_driver);
        }
    }

    private void randomBattleAction(WebDriver d) throws Throwable {
        Random rand = new Random();

        List<WebElement> move_bar;
        List<WebElement> switch_bar;

        int justSwitch;
        int randMove;
        int randSwitch;

        justSwitch = rand.nextInt(10);

        move_bar = d.findElements(By.name("chooseMove"));
        switch_bar = d.findElements(By.name("chooseSwitch"));

        if (justSwitch > 7 && !switch_bar.isEmpty()) {
            try {
                d.findElement(By.xpath("//button[@text='Switch']")).click();
            } catch (NotFoundException e) {
                //Nothing to do here
            }

            randSwitch = rand.nextInt(switch_bar.size());

            switch_bar.get(randSwitch).click();
            return;
        }

        try {
            if (!move_bar.isEmpty()) {
                try {
                    d.findElement(By.xpath("//button[@text='Attack']")).click();
                } catch (NotFoundException e) {
                    //Nothing to do here
                }

                randMove = rand.nextInt(move_bar.size());

                move_bar.get(randMove).click();
                return;
            }
        } catch (NotFoundException e) {
            //Nothing to do here
        }

        if (!switch_bar.isEmpty()) {
            try {
                d.findElement(By.xpath("//button[@text='Switch']")).click();
            } catch (NotFoundException excp) {
                //Nothing to do here
            }
            try {
                switch_bar = d.findElements(By.name("chooseSwitch"));
                randSwitch = rand.nextInt(switch_bar.size());

                switch_bar.get(randSwitch).click();
            } catch (NotFoundException excp) {
                //Nothing to do here
            }
        }
    }

    @Then("Rejection message should show for both users from {string}")
    public void checkRejectionForBothUsers(String arg1) throws Throwable {
        WebElement main_pm_box = driver.findElement(By.className("pm-log"));

        List<WebElement> main_user_dms = main_pm_box.findElements(By.cssSelector(".chat"));

        boolean found_in_main = false;

        for (WebElement message : main_user_dms) {
            String text = message.getText();
            if (text.contains(arg1 + " rejected the challenge.")) {
                System.out.println("Test: " + test_name + ", main user sees rejection message.");
                found_in_main = true;
                break;
            }
        }

        if (found_in_main == false) {
            throw new NotFoundException("Test: " + test_name + ", Rejection message not found in main user's DMs");
        }

        WebElement secondary_pm_box = second_driver.findElement(By.className("pm-log"));

        List<WebElement> secondary_user_dms = secondary_pm_box.findElements(By.cssSelector(".chat"));

        for (WebElement message : secondary_user_dms) {
            String text = message.getText();
            if (text.contains(arg1 + " rejected the challenge.")) {
                System.out.println("Test: " + test_name + ", second user sees rejection message.");
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Rejection message not found in second user's DMs");
    }

    @Then("Main user should see {string} from {string}")
    public void checkMainUserChatForMessage(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement chat_box = driver.findElement(By.className("battle-log"));

        String chat_message_class_name = ".chat";
        if (arg2.equals(main_username)) {
            chat_message_class_name += ".mine";
        }

        List<WebElement> messages = chat_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1)) {
                System.out.println("Test: " + test_name + ", Correct message found: " + arg1);
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Message not found: " + arg1);
    }

    @Then("Second user should see {string} from {string}")
    public void checkSecondUserChatForMessage(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        WebElement chat_box = second_driver.findElement(By.className("battle-log"));

        String chat_message_class_name = ".chat";
        if (arg2.equals(second_username)) {
            chat_message_class_name += ".mine";
        }

        List<WebElement> messages = chat_box.findElements(By.cssSelector(chat_message_class_name));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(arg1)) {
                System.out.println("Test: " + test_name + ", Correct message found: " + arg1);
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Message not found: " + arg1);
    }

    @Then("^Main user should win battle$")
    public void mainUserShouldWinBattle() throws Throwable {
        TimeUnit.SECONDS.sleep(5);

        WebElement pm_box = driver.findElement(By.className("battle-log"));

        List<WebElement> messages = pm_box.findElements(By.className("battle-history"));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(main_username + " won the battle!")) {
                System.out.println("Test: " + test_name + ", Second user win message found");
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Second user win message not found");
    }

    @Then("^Second user should win battle$")
    public void secondUserShouldWinBattle() throws Throwable {
        TimeUnit.SECONDS.sleep(5);

        WebElement pm_box = second_driver.findElement(By.className("battle-log"));

        List<WebElement> messages = pm_box.findElements(By.className("battle-history"));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(second_username + " won the battle!")) {
                System.out.println("Test: " + test_name + ", Second user win message found");
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", Second user win message not found");
    }

    @Then("^A winner should be decided$")
    public void aUserShouldWinBattle() throws Throwable {
        TimeUnit.SECONDS.sleep(5);

        WebElement pm_box = driver.findElement(By.className("battle-log"));

        List<WebElement> messages = pm_box.findElements(By.className("battle-history"));

        for (WebElement message : messages) {
            String text = message.getText();
            if (text.contains(main_username + " won the battle!") || text.contains(second_username + " won the battle!")) {
                System.out.println("Test: " + test_name + ", User win message found");
                return;
            }
        }

        throw new NotFoundException("Test: " + test_name + ", User win message not found");
    }

    @Then("^Main user's battle tab should be closed$")
    public void mainUserBattleTabClosed() throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        List<WebElement> rooms = driver.findElements(By.cssSelector(".roomtab"));

        for (WebElement room : rooms) {
            try {
                String text = room.findElement(By.xpath(".//span")).getText();
                if (text.contains("vs.") && text.contains(main_username) && text.contains(second_username)) {
                    throw new Exception("Test: " + test_name + ", Main user battle tab still open.");
                }
            } catch (NotFoundException e) {
                System.out.println("Test: " + test_name + ", room is not the battle room.");
            }
        }
        System.out.println("Test: " + test_name + ", battle room is closed successfully.");
    }

    @After
    public void cleanup() {
        try {
            driver.findElement(By.name("closeRoom")).click();
            driver.findElement(By.className("ps-popup")).findElement(By.xpath(".//button/strong[text()='Forfeit']")).click();
        } catch (NotFoundException e) {
            //Nothing to do here, this is just to close old battles if they still exist
        }

        driver.close();
        driver = null;

        if (second_driver != null) {
            try {
                second_driver.findElement(By.name("closeRoom")).click();
                second_driver.findElement(By.className("ps-popup")).findElement(By.xpath(".//button/strong[text()='Forfeit']")).click();
            } catch (NotFoundException e) {
                //Nothing to do here, this is just to close old battles if they still exist
            }
            second_driver.close();
            second_driver = null;
        }

    }
}
