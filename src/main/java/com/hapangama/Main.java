package com.hapangama;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.rules.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Period;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {

        // [0] Setup

        System.out.println("Test started");

        String driverPath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\hapangama\\drivers\\msedgedriver.exe";

        if(Files.exists(new File(driverPath).toPath())) {
            System.out.println("Driver exists");
        }

        // Set the property for webdriver.chrome.driver to be the location to your local              download of chromedriver
        System.setProperty("webdriver.edge.driver", driverPath);

        // Create new instance of ChromeDriver
        WebDriver driver = new EdgeDriver();



        // And now use this to visit Google
        driver.get("https://formy-project.herokuapp.com/keypress");

        // [1] Typing

        // Find the text input element by its name
        WebElement element = driver.findElement(By.id("name"));
        element.click(); //click
        element.sendKeys("Cheese!"); //type

        WebElement button = driver.findElement(By.id("button"));
        button.click();

        //if there's autocomplete suggestion just type until 1 suggestion is left and click on it

        // [2] Scrolling

        driver.get("https://formy-project.herokuapp.com/scroll");

        //getting a element
        WebElement name = driver.findElement(By.id("name"));

        Actions action = new Actions(driver);
        action.moveToElement(name);

        // [3] Switching to window

        driver.get("https://formy-project.herokuapp.com/switch-window");

        //clicking the button to create a new window
        WebElement firstbut = driver.findElement(By.id("new-tab-button"));
        firstbut.click();

        //window handle of original window
        String originalHandle = driver.getWindowHandle();

        System.out.println(originalHandle);

        for (String handle : driver.getWindowHandles()){
            driver.switchTo().window(handle); //switching to next window
        }

        driver.switchTo().window(originalHandle);

        // [4] Switching to alert
        WebElement alertbtn = driver.findElement(By.id("alert-button"));
        alertbtn.click();

        //Alert class to define alert object
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // [5] Running JS commands

        driver.get("https://formy-project.herokuapp.com/modal");

        WebElement mdlbtn =  driver.findElement(By.id("modal-button"));
        mdlbtn.click();

        WebElement closebtn = driver.findElement(By.id("close-button"));

        JavascriptExecutor js = (JavascriptExecutor) driver; //creating js executer object

        js.executeScript("arguments[0].click();", closebtn); //simple script for press close button

        // [6] Drag and Drop

        driver.get("https://formy-project.herokuapp.com/dragdrop");

        WebElement img = driver.findElement(By.id("image"));
        WebElement drop = driver.findElement(By.id("box"));

        //action to drag and drop
        Actions actions = new Actions(driver);
        actions.dragAndDrop(img, drop).build().perform();

        // [7] Using Css Selector

        driver.findElement(By.cssSelector(".classname"));
        driver.findElement(By.cssSelector("#id"));
        driver.findElement(By.cssSelector("tagname"));

        driver.findElement(By.cssSelector("tagname.classname"));
        driver.findElement(By.cssSelector("tagname#classname"));
        driver.findElement(By.cssSelector("input[type='radio']")); //using attributes
        driver.findElement(By.cssSelector(".class1.class2.class3")); //multiple classes

        // [8] Matching text

        //matching types
        driver.findElement(By.cssSelector("a[id^='first']")); // prefix
        driver.findElement(By.cssSelector("a[id$='last']")); // sufix
        driver.findElement(By.cssSelector("a[id*='middle']")); //substring
        driver.findElement(By.cssSelector("a[id='exact']")); //exact match

        // [9] Finding a clild item

        driver.findElement(By.cssSelector("div#perent a")); //aherf inside a div called parent
        driver.findElement(By.cssSelector("#list li:nth-child(2)")); //finding the second list item

        // [10] radio buttons

        driver.get("https://formy-project.herokuapp.com/radiobutton");

        WebElement r1 = driver.findElement(By.cssSelector("input[value='option1']"));
        r1.click();

        WebElement r2 = driver.findElement(By.cssSelector("input[value='option2']"));
        r2.click();

        WebElement r3 = driver.findElement(By.cssSelector("input[value='option3']"));
        r3.click();

        // [11] Datepicker

        driver.get("https://formy-project.herokuapp.com/datepicker");

        WebElement date = driver.findElement(By.id("datepicker"));
        date.click();

        date.sendKeys("02/03/2028");
        date.sendKeys(Keys.RETURN); //pressing enter key

        // [12] Dropdown

        driver.get("https://formy-project.herokuapp.com/dropdown");

        WebElement dd = driver.findElement(By.id("dropdownMenuButton"));
        dd.click();

        WebElement im = driver.findElement(By.id("autocomplete"));
        im.click();


        // [13] FileUpload

        driver.get("https://formy-project.herokuapp.com/fileupload");

        WebElement fu = driver.findElement(By.id("file-upload-field"));

        fu.sendKeys("your-file-name.png");



       // [14] Waits - slow down the tests

        // 2 types of waits

        // implicit waits - waits specified amount of time (default is 0)
        // explicit waits - waits specified amount of time for a certain condition to be true else throws exception

        //implicit
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

        //explicit
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));
        //waits 10 secounds untill elemnt apper else throws exceotrion
        //condition checks every 500ms




        //

        // [15] Forms and confirmation

        driver.get("https://formy-project.herokuapp.com/form");

        //filling the form
        driver.findElement(By.id("first-name")).sendKeys("kasun");
        driver.findElement(By.id("last-name")).sendKeys("hapangama");
        driver.findElement(By.id("job-title")).sendKeys("Jr Dev");
        driver.findElement(By.id("radio-button-1")).click();
        driver.findElement(By.id("checkbox-1")).click();
        driver.findElement(By.cssSelector("option[value='1']")).click();
        driver.findElement(By.id("datepicker")).sendKeys("02/01/2028");

        //subiting the form by btn
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary")).click();

        //waiting for the responce
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement alert1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert")));

        //Assertion using Junit
        String alt = alert1.getText();

        //Junit Assertions
        Assert.assertEquals("The form was successfully submitted!", alt);



        // [16] selenium grid
        // hub -  "C:\Program Files\Java\jdk-22\bin\java" -jar selenium-server-4.21.0.jar hub
        // node - "C:\Program Files\Java\jdk-22\bin\java" -jar selenium-server-4.21.0.jar node --hub http://192.168.42.242:4444 --selenium-manager true

        //Continuous Integration
        // Provides continuous feedback that test are working and validates the functionality of the application

        // circleci
        // jenkins
        // teamcity
        // travis CI

        //cloud based test tools

        // sauselabs



        driver.close();
        System.out.println("Test completed successfully");


    }
}