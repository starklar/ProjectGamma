Feature: Users should be able to find and battle other players

Background:
    Main and second users navigates to Pokemon Showdown in seperate windows
    Given Main user is on Pokemon Showdown as "one_star_man" with password as "zeta_slow"

Scenario: Second user rejects main user's challenge
    Given Test name is "SECOND USER REJECTS MAIN USER'S CHALLENGE"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    When Main user searches for "two_star_man" and sends a "gen9randombattle" battle request
    And Second user rejects challenge
    Then Rejection message should show for both users from "two_star_man"

Scenario: Main user chats with second user during a Gen 9 random battle
    Given Test name is "MAIN USER CHATS WITH SECOND USER DURING A GEN 9 RANDOM BATTLE"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    When Main user searches for "two_star_man" and sends a "gen9randombattle" battle request
    And Second user accepts challenge
    And Main user sends "Hello" in battle chat
    And Second user sends "You are going down" in battle chat
    Then Second user should see "Hello" from "one_star_man"
    And Main user should see "You are going down" from "two_star_man"
    And Second user should see "You are going down" from "two_star_man"

Scenario: Main user challenges second user to a Gen 9 random battle and forfeits
    Given Test name is "MAIN USER CHALLENGES SECOND USER TO A GEN 9 RANDOM BATTLE AND FORFEITS"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    When Main user searches for "two_star_man" and sends a "gen9randombattle" battle request
    And Second user accepts challenge
    And Main user forfeits and exits the battle
    Then Second user should win battle
    And Main user's battle tab should be closed

Scenario: Main user challenges second user to an Gen 9 OU battle and wins
    Given Test name is "MAIN USER CHALLENGES SECOND USER TO A GEN 9 OU BATTLE AND WINS"
    Given Test name is "MAIN USER BATTLES SECOND USER TO A GEN 9 RANDOM BATTLE"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    And Main user imports a team for "gen9ou" using
        """
        Odin Dark (Ceruledge)  
        Ability: Flash Fire  
        Tera Type: Fire  
        EVs: 252 HP  
        IVs: 0 Atk  
        - Bulk Up  

        Mii (Mimikyu)  
        Ability: Disguise  
        Tera Type: Ghost  
        EVs: 252 HP  
        IVs: 0 Atk  
        - Draining Kiss   
        """
    And Second user imports a team for "gen9ou" using
        """
        Boom (Forretress)  
        Ability: Sturdy  
        Tera Type: Bug  
        EVs: 252 Atk  
        - Explosion  

        EXPLOSION (Coalossal)  
        Ability: Steam Engine  
        Tera Type: Rock  
        EVs: 252 HP / 252 Spe  
        - Explosion  
        """
    When Main user searches for "two_star_man" and sends a "gen9ou" battle request
    And Second user accepts challenge
    And Main user chooses a random lead Pokemon
    And Second user chooses a random lead Pokemon
    And Battle occurs until a winner is decided
    Then Main user should win battle

Scenario: Main user battles second user in a Gen 9 random battle
    Given Test name is "MAIN USER BATTLES SECOND USER TO A GEN 9 RANDOM BATTLE"
    And Second user is on Pokemon Showdown as "two_star_man" with password as "so_zeta_slow"
    When Main user searches for "two_star_man" and sends a "gen9randombattle" battle request
    And Second user accepts challenge
    And Battle occurs until a winner is decided
    Then A winner should be decided

Scenario: Main user participates in a Gen 9 random battle and exits
    Given Test name is "MAIN USER PARTICIPATES IN A GEN 9 RANDOM BATTLE AND FORFEITS"
    When Main user battles with a random opponent in the "gen9randombattle" format
    And Main user forfeits and exits the battle
    Then Main user's battle tab should be closed
