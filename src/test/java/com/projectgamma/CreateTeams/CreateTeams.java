package com.projectgamma.CreateTeams;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateTeams {

    protected static WebDriver driver = null;
    protected String test_name = null;
    protected String main_username = null;

    @Given("User is on Pokemon Showdown as {string} with password as {string}")
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

    @Given("Test name is {string}")
    public void setTestName(String arg1) throws Throwable {
        test_name = arg1;
    }

    @Given("^User opens Teambuilder$")
    public void openTeambuilder() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement teambuilder_button = driver.findElement(By.xpath("//button[@value='teambuilder']"));
        teambuilder_button.click();
    }

    @Given("^User clicks add a new teams$")
    public void addNewTeam() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement new_team_button = driver.findElement(By.name("newTop"));
        new_team_button.click();
    }

    @When("User sets format to {string}")
    public void setFormat(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement format_drop_down = driver.findElement(By.className("format-select"))
                .findElement(By.name("format"));
        format_drop_down.click();

        WebElement format_option = driver.findElement(By.xpath("//button[@value='" + arg1 + "']"));
        format_option.click();
    }

    @When("User imports a team using")
    public void importTeam(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement import_button = driver.findElement(By.name("import"));
        import_button.click();

        WebElement import_box = driver.findElement(By.className("teamedit"))
                .findElement(By.className("textbox"));
        import_box.sendKeys(arg1);

        WebElement save_import_button = driver.findElement(By.name("saveImport"));
        save_import_button.click();
    }

    @When("^User clicks validate$")
    public void validate() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement format_drop_down = driver.findElement(By.name("validate"));
        format_drop_down.click();
    }

    @When("^User adds a new Pokemon to the team$")
    public void addPokemon() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement add_pokemon_button = driver.findElement(By.name("addPokemon"));
        add_pokemon_button.click();
    }

    @When("User searches for and selects {string}")
    public void setPokemonSpecies(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.name("pokemon")).sendKeys(arg1);

        WebElement pokemon_entry = driver.findElement(By.xpath("//a[@data-entry='pokemon|" + arg1 + "']"));
        pokemon_entry.click();
    }

    @When("User sets move {int} as {string}")
    public void setPokemonMove(int arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.name("move" + arg1)).sendKeys(arg2);
    }

    @When("User sets {string} as {int}")
    public void setPokemonEV(String arg1, int arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.name("stats")).click();
        driver.findElement(By.name(arg1)).sendKeys("" + arg2);
    }

    @When("^User returns to team$")
    public void returnToTeam() throws Throwable {
        TimeUnit.SECONDS.sleep(1);
        WebElement return_to_team_button = driver.findElement(By.name("back"));
        return_to_team_button.click();
    }

    @When("User sets the following Pokemon to a team")
    public void setTeam(DataTable table) throws Throwable {
        List<Map<String, String>> mons = table.asMaps(String.class, String.class);

        for (Map<String, String> mon : mons) {
            driver.findElement(By.name("addPokemon")).click();

            driver.findElement(By.name("pokemon")).sendKeys(mon.get("Species"));

            driver.findElement(By.xpath("//a[@data-entry='pokemon|" + mon.get("Species") + "']")).click();

            driver.findElement(By.name("nickname")).sendKeys(mon.get("Nickname"));

            driver.findElement(By.name("item")).sendKeys(mon.get("Item"));

            driver.findElement(By.name("ability")).clear();
            driver.findElement(By.name("ability")).sendKeys(mon.get("Ability"));

            driver.findElement(By.name("details")).click();
            Select select_tera_type = new Select(driver.findElement(By.name("teratype")));
            select_tera_type.selectByVisibleText(mon.get("TeraType"));

            driver.findElement(By.name("stats")).click();

            Select select_nature = new Select(driver.findElement(By.name("nature")));
            select_nature.selectByValue(mon.get("Nature"));

            String[] evs = mon.get("EVs").split("/");

            WebElement evcol = driver.findElement(By.className("evcol"));

            int index = 0;

            for (WebElement ev : evcol.findElements(By.className("numform"))) {
                ev.sendKeys(evs[index]);
                index++;
            }

            String[] ivs = mon.get("IVs").split("/");

            WebElement ivcol = driver.findElement(By.className("ivcol"));

            index = 0;

            for (WebElement iv : ivcol.findElements(By.className("numform"))) {
                iv.sendKeys(ivs[index]);
                index++;
            }

            driver.findElement(By.name("move1")).sendKeys(mon.get("Move1"));
            driver.findElement(By.name("move2")).sendKeys(mon.get("Move2"));
            driver.findElement(By.name("move3")).sendKeys(mon.get("Move3"));
            driver.findElement(By.name("move4")).sendKeys(mon.get("Move4"));

            driver.findElement(By.name("back")).click();
        }

    }

    @Then("Successful validation message should show for {string}")
    public void checkSuccessfulValidation(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(2);

        WebElement text_message = driver.findElement(By.className("ps-popup"))
                .findElement(By.xpath(".//form/p"));

        if (text_message.getText().contains("Your team is valid for " + arg1 + ".")) {
            System.out.println("Test: " + test_name + ", Team is valid for " + arg1);
        } else {
            throw new NotFoundException("Test: " + test_name + ", Unexpected message: " + text_message.getText());
        }

        WebElement close_button = driver.findElement(By.name("close"));
        close_button.click();
    }

    @Then("Rejection message should show {string} can not learn {string}")
    public void checkIllegalMoveRejection(String arg1, String arg2) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        String text_message = driver.findElement(By.className("ps-popup"))
                .findElement(By.xpath(".//form/p"))
                .getText();

        if (text_message.matches("Your team was rejected for the following reasons:(.|\n)*" + arg1 + "(.|\n)* can't learn " + arg2 + "(.|\n)*")) {
            System.out.println("Test: " + test_name + ", Team successfully rejected for illegal move " + arg2 + " on " + arg1);
        } else {
            throw new NotFoundException("Test: " + test_name + ", Team not rejected as expected, found text: " + text_message);
        }

        WebElement close_button = driver.findElement(By.name("close"));
        close_button.click();
    }

    @Then("Rejection message should show {string} is banned")
    public void checkBannedPokemonRejection(String arg1) throws Throwable {
        TimeUnit.SECONDS.sleep(1);

        String text_message = driver.findElement(By.className("ps-popup"))
                .findElement(By.xpath(".//form/p"))
                .getText();

        if (text_message.matches("Your team was rejected for the following reasons:(.|\n)*" + arg1 + "(.|\n)* is tagged .*, which is banned by (.|\n)*")) {
            System.out.println("Test: " + test_name + ", Team successfully rejected for banned Pokemon " + arg1);
        } else {
            throw new NotFoundException("Test: " + test_name + ", Team not rejected as expected, found text: " + text_message);
        }

        WebElement close_button = driver.findElement(By.name("close"));
        close_button.click();
    }

    @After
    public void closeDriver() {
        driver.close();
    }
}
