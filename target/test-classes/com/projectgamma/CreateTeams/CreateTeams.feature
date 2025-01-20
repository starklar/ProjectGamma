Feature: Users should be able to set teams to use for battles

Background:
    User navigates to Pokemon Showdown
    Given User is on Pokemon Showdown as "one_star_man" with password as "zeta_slow"
    And User opens Teambuilder
    And User clicks add a new teams

Scenario: User successfully creates a simple AG team through copy and paste
    Given Test name is "USER SUCCESSFULLY CREATES A SIMPLE AG TEAM THROUGH COPY AND PASTE"
    When User sets format to "gen9anythinggoes" 
    And User imports a team using
        """
        Mewbers #1 (Mew)  
        Ability: Synchronize  
        Tera Type: Psychic  
        EVs: 8 HP  
        - Metronome  
        """
    And User clicks validate
    Then Successful validation message should show for "[Gen 9] Anything Goes"

Scenario: User successfully creates a simple AG team through the UI
    Given Test name is "USER SUCCESSFULLY CREATES A SIMPLE AG TEAM THROUGH THE UI"
    When User sets format to "gen9anythinggoes" 
    And User adds a new Pokemon to the team
    And User searches for and selects "Mew"
    And User sets move 1 as "Metronome"
    And User sets "stat-hp" as 8
    And User returns to team
    And User clicks validate
    Then Successful validation message should show for "[Gen 9] Anything Goes"

Scenario: User successfully creates a complex OU team through copy and paste
    Given Test name is "USER SUCCESSFULLY CREATES A COMPLEX OU TEAM THROUGH COPY AND PASTE"
    When User sets format to "gen9ou" 
    And User imports a team using
        """
        Table Salt (Garganacl) @ Leftovers  
        Ability: Purifying Salt  
        Tera Type: Flying  
        EVs: 248 HP / 28 Def / 228 SpD / 4 Spe  
        Careful Nature  
        - Salt Cure  
        - Body Press  
        - Iron Defense  
        - Recover  

        Kris (Primarina) @ Assault Vest  
        Ability: Torrent  
        Tera Type: Steel  
        EVs: 80 HP / 252 SpA / 176 Spe  
        Modest Nature  
        - Sparkling Aria  
        - Moonblast  
        - Ice Beam  
        - Aqua Jet  

        Cincinnati (Cinccino) @ Protective Pads  
        Ability: Skill Link  
        Tera Type: Ghost  
        EVs: 252 Atk / 4 Def / 252 Spe  
        Jolly Nature  
        - Tail Slap  
        - Rock Blast  
        - Knock Off  
        - Tidy Up  

        Knightfall (Corviknight) @ Rocky Helmet  
        Ability: Mirror Armor  
        Tera Type: Ground  
        EVs: 248 HP / 252 Def / 8 SpD  
        Careful Nature  
        - Iron Head  
        - U-turn  
        - Roost  
        - Defog  

        Donut (Iron Treads) @ Focus Sash  
        Ability: Quark Drive  
        Tera Type: Ground  
        EVs: 252 Atk / 4 Def / 252 Spe  
        Jolly Nature  
        - Earthquake  
        - Ice Spinner  
        - Rapid Spin  
        - Stealth Rock  

        Flower (Comfey) @ Life Orb  
        Ability: Triage  
        Tera Type: Ground  
        EVs: 252 SpA / 4 SpD / 252 Spe  
        Timid Nature  
        IVs: 0 Atk  
        - Draining Kiss  
        - Stored Power  
        - Substitute  
        - Calm Mind  
        """
    And User clicks validate
    Then Successful validation message should show for "[Gen 9] OU"

Scenario: User successfully creates a complex OU team through UI
    Given Test name is "USER SUCCESSFULLY CREATES A COMPLEX OU TEAM THROUGH UI"
    When User sets format to "gen9ou" 
    And User sets the following Pokemon to a team
        | Species   | Nickname      | Item          | Ability           | TeraType  | Nature    | IVs               | EVs               | Move1             | Move2         | Move3         | Move4         |
        | Primarina | Kris          | Assault Vest  | Torrent           | Steel     | Modest    | 31/31/31/31/31/31 | 80/0/0/252/0/176  | Sparkling Aria    | Moonblast     | Ice Beam      | Aqua Jet      |
        | Garganacl | Table Salt    | Leftovers     | Purifying Salt    | Flying    | Careful   | 31/31/31/31/31/31 | 248/0/28/0/228/4  | Salt Cure         | Body Press    | Recover       | Iron Defense  |
        | Comfey    | Flower        | Life Orb      | Triage            | Ground    | Timid     | 31/0/31/31/31/31  | 0/0/0/252/4/252   | Draining Kiss     | Stored Power  | Substitute    | Calm Mind     |
    And User clicks validate
    Then Successful validation message should show for "[Gen 9] OU"

Scenario: User fails to validate a team with a banned Pokemon in NU
    Given Test name is "USER FAILS TO VALIDATE A TEAM WITH A BANNED POKEMON IN NU"
    When User sets format to "gen9nu" 
    And User imports a team using
        """
        Lord Almighty (Arceus)  
        Ability: Multitype  
        Tera Type: Normal  
        EVs: 252 Atk / 252 Spe  
        - Judgment  
        """
    And User clicks validate
    Then Rejection message should show "Arceus" is banned

Scenario: User fails to validate a team with a Pokemon with an illegal move
    Given Test name is "USER FAILS TO VALIDATE A TEAM WITH A POKEMON WITH AN ILLEGAL MOVE"
    When User sets format to "gen9ou" 
    And User imports a team using
        """
        Freddy Fast Bear (Ursaluna) @ Choice Scarf  
        Ability: Unnerve  
        Tera Type: Ground  
        EVs: 252 Spe  
        Jolly Nature  
        IVs: 0 Atk  
        - Agility  
        """
    And User clicks validate
    Then Rejection message should show "Ursaluna" can not learn "Agility"