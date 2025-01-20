Feature: Users should be able to find and interact with specific users through the main page

Background:
    Main and second users navigates to Pokemon Showdown in seperate windows
    Given Main user is on Pokemon Showdown as "one_star_man" with password as "zeta_slow"

Scenario: Main user searches for second user
    Given Test name is "MAIN USER SEARCHES FOR SECOND USER"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user is not ignoring "two_star_man"
    When Main user searches for "two_star_man"
    Then Main user should see options to interact with second user

Scenario Outline: Main user sends message to second user who responds
    Given Test name is 'MAIN USER CHATS WITH SECOND USER STARTING WITH <msg1>'
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user is not ignoring "two_star_man"
    When Main user searches for "two_star_man"
    And Main user chooses to chat
    And Main user sends <msg1> in chat
    And Second user sends <msg2> in chat
    And Main user sends <msg3> in chat
    Then Main user should see <msg2> in chat from "two_star_man"
    And Second user should see <msg1> in chat from "one_star_man"
    And Main user should see <msg1> in chat from "one_star_man"
    And Second user should see <msg3> in chat from "one_star_man"

Examples:
    | msg1 | msg2 | msg3|
    | "Hee ho" | "Ho Hee" | "Eeh oh" |
    | "1" | "56709" | "777" |
    | "He was #1!" | "???" | "$9.00" |
    | "잘 자주세요" | "J'espère que je peux" | "Lütfen ve teşekkür ederim" |

Scenario: Main user ignores second user
    Given Test name is "MAIN USER IGNORES SECOND USER"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user is not ignoring "two_star_man"
    When Second user chats with "one_star_man"
    And Second user sends "Hello" in chat
    And Main user searches for "two_star_man"
    And Main user toggles ignore
    And Second user sends "Do you still hear me?" in chat
    Then Main user should see "Hello" in chat from "two_star_man"
    But Main user should not see "Do you still hear me?" in chat from "two_star_man"

Scenario: Main user unignores second user
    Given Test name is "MAIN USER UNIGNORES SECOND USER"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user is ignoring "two_star_man"
    When Second user chats with "one_star_man"
    And Second user sends "Hello" in chat
    And Main user searches for "two_star_man"
    And Main user toggles ignore
    And Second user sends "Do you still hear me?" in chat
    Then Main user should see "Do you still hear me?" in chat from "two_star_man"
    But Main user should not see "Hello" in chat from "two_star_man"

Scenario: Main user friends second user
    Given Test name is "MAIN USER FRIENDS FOR SECOND USER"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user is not ignoring "two_star_man"
    When Main user searches for "two_star_man"
    And Main user chooses to send friend request
    Then Second user should see friend request from "one_star_man"

Scenario: Main user searches self
    Given Test name is "MAIN USER SEARCHES FOR SELF"
    #When Main user searches for "one_star_man"
    When Main user searches for "one_star_man"
    Then Main user should see options to interact with self

Scenario: Main user chats to self
    Given Test name is "MAIN USER CHATS TO SELF"
    #When Main user searches for "one_star_man"
    When Main user searches for "one_star_man"
    And Main user chooses to chat
    And Main user sends "Hello Charlotte" in chat
    Then Main user should see "Hello Charlotte" in chat from "one_star_man"