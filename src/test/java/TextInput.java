import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import org.openqa.selenium.*;

import static  org.openqa.selenium.By.xpath;
import static java.lang.String.format;

public class TextInput {
    WebDriver driver ;
    String name = "//span[contains(text(),'%s')]/../../../div/input[last()]";

    public TextInput(WebDriver driver){
        this.driver = driver;
        return;
    }

    public TextInput fillField(String name, String value){
        driver.findElement(By.xpath(format(name))).sendKeys(value);
        return this;
    }

    public TextInput currentValue(String name){
        System.out.println(driver.findElement(By.xpath(format(name))).getAttribute("value"));
        return this;

    }


}