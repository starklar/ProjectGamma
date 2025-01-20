$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri('Annotation\Annotation.feature');
formatter.feature({
  "line": 1,
  "name": "annotation",
  "description": "",
  "id": "annotation",
  "keyword": "Feature"
});
formatter.scenario({
  "comments": [
    {
      "line": 2,
      "value": "#This is how background can be used to eliminate duplicate steps"
    },
    {
      "line": 4,
      "value": "#Background:"
    },
    {
      "line": 5,
      "value": "#   User navigates to Facebook"
    },
    {
      "line": 6,
      "value": "#   Given I am on Facebook login page"
    },
    {
      "line": 8,
      "value": "#Scenario with AND"
    }
  ],
  "line": 9,
  "name": "",
  "description": "",
  "id": "annotation;",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 10,
  "name": "I am on Facebook login page",
  "keyword": "Given "
});
formatter.step({
  "line": 11,
  "name": "I enter username as \"TOM\"",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I enter password as \"JERRY\"",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "Login should fail",
  "keyword": "Then "
});
formatter.match({
  "location": "Annotation.goToFacebook()"
});
formatter.result({
  "duration": 231316300,
  "error_message": "java.lang.IllegalStateException: The path to the driver executable must be set by the webdriver.chrome.driver system property; for more information, see https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver. The latest version can be downloaded from http://chromedriver.storage.googleapis.com/index.html\r\n\tat com.google.common.base.Preconditions.checkState(Preconditions.java:197)\r\n\tat org.openqa.selenium.remote.service.DriverService.findExecutable(DriverService.java:109)\r\n\tat org.openqa.selenium.chrome.ChromeDriverService.access$000(ChromeDriverService.java:32)\r\n\tat org.openqa.selenium.chrome.ChromeDriverService$Builder.findDefaultExecutable(ChromeDriverService.java:137)\r\n\tat org.openqa.selenium.remote.service.DriverService$Builder.build(DriverService.java:290)\r\n\tat org.openqa.selenium.chrome.ChromeDriverService.createDefaultService(ChromeDriverService.java:88)\r\n\tat org.openqa.selenium.chrome.ChromeDriver.\u003cinit\u003e(ChromeDriver.java:116)\r\n\tat Annotation.Annotation.goToFacebook(Annotation.java:17)\r\n\tat ✽.Given I am on Facebook login page(Annotation\\Annotation.feature:10)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "TOM",
      "offset": 21
    }
  ],
  "location": "Annotation.enterUsername(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "JERRY",
      "offset": 21
    }
  ],
  "location": "Annotation.enterPassword(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "Annotation.checkFail()"
});
formatter.result({
  "status": "skipped"
});
});