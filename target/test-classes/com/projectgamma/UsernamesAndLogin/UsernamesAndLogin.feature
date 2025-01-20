Feature: Users should be able to only be able to use usernames that are either not taken or belong to them

Background: 
   User navigates to Pokemon Showdown
   Given User is on Pokemon Showdow

Scenario: Use untaken username successfully
   Given Test name is "USE UNTAKEN USERNAME SUCCESSFULLY"
   When User tries to login with username as "three_star_man"
   Then Login should succeed

Scenario: Be prompted for password for taken username
   Given Test name is "BE PROMPTED FOR PASSWORD FOR TAKEN USERNAME"
   When User tries to login with username as "one_star_man"
   Then User should be prompted for a password

Scenario: Fail to use taken username with wrong password
   Given Test name is "FAIL TO USE TAKEN USERNAME WITH WRONG PASSWORD"
   When User tries to login with username as "one_star_man"
   And User enters password as "wrong password"
   Then Login should fail
   And User should be prompted for a password

Scenario: Login to own account successfully
   Given Test name is "LOGIN TO OWN ACCOUNT SUCCESSFULLY"
   When User tries to login with username as "one_star_man"
   And User enters password as "zeta_slow"
   Then Login should succeed

Scenario: Logout of account after successful login
   Given Test name is "LOGOUT OF ACCOUNT AFTER SUCCESSFUL LOGIN"
   When User tries to login with username as "one_star_man"
   And User enters password as "zeta_slow"
   And User opens the options menu
   And User clicks the logout button
   Then User should be notified of being successfully logged out

Scenario: Switch usernames after choosing one
   Given Test name is "SWITCH USERNAMES AFTER CHOOSING ONE"
   When User tries to login with username as "three_star_man"
   And User opens the options menu
   And User tries to login with username as "four_star_man"
   Then Login should succeed

#NOTE: Showdown does not allow users to delete user accounts, so to avoid flooding
#      their databases, this test will be commented out for now.
#Scenario: Register new account
#   Given Test name is "REGISTER NEW ACCOUNT"
#   When User tries to login with username as "two_star_man"
#   And User opens the options menu
#   And User clicks the register button
#   And User tries to login with the registration info with "so_zeta_slow" as a password and "Pikachu" as the Pokemon displayed
#   Then User should be notified of being successfully registered
