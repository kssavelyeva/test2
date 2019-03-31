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
    String labelChekBox = "";

   public CheckBox(WebDriver driver){
        this.driver = driver;
        return;
    }

    public CheckBox chekBoxActive(boolean value, String name){
        WebElement chekboxInfo = driver.findElement(By.xpath(format(activeChekBox,name)+"/*/*/*"));
        WebElement chekbox= driver.findElement(By.xpath(format(activeChekBox,name)));
        System.out.println(driver.findElement(By.xpath(format(activeChekBox,name)+"/*/*/*")));
        if(!(chekboxInfo.getAttribute("innerHTML").contains("checked"))== true)  chekbox.click();
        else  chekbox.click();
        System.out.println(driver.findElement(By.xpath(format(activeChekBox,name))));

        return this;
    }

    public CheckBox chekBoxLabel(String name){
        System.out.println(driver.findElement(By.xpath(format(activeChekBox+"/../label", name))).getText());
        return this;
    }

    public void  chekBoxStatus(String name){
        WebElement chek = driver.findElement(By.xpath(format(activeChekBox,name)));
        if(chek.getAttribute("innerHTML").contains("cheked"))
        {
            System.out.println("Активен"+chek.getAttribute("innerHTML") );
        }
        else  {System.out.println("Не активен"+chek.getAttribute("innerHTML") );}

    }
}
