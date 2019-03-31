import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import org.openqa.selenium.*;

import static  org.openqa.selenium.By.xpath;
import static java.lang.String.format;

import java.util.List;

public class CheckBox {
    WebDriver driver ;
    String name = "//label[contains(text(),'%s')]";
    String activeChekBox = "//label[contains(text(),'%s')]/../div";

    public CheckBox(WebDriver driver){
        this.driver = driver;
        return;
    }


    String chekBox = "//label[contains(text(),'%s')]";
    public CheckBox chekBoxActive(boolean value, String name){
        WebElement chek = driver.findElement(By.xpath(format(activeChekBox,name)));
        if(chek.getText().contains("cheked")) System.out.println("Активен");
        return this;
    }
    public CheckBox chekBoxDeactive(boolean value, String name){
        WebElement chek = driver.findElement(By.xpath(format(activeChekBox,name)));
        if(chek.getText().contains("cheked")) System.out.println("Активен");
        else chek.click();
        return this;
    }
}
