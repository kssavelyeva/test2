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

    public CheckBox chekBoxActive(String name, boolean value){
        WebElement chekboxInfo = driver.findElement(By.xpath(format(activeChekBox,name)+"/*/*/*"));
        WebElement chekbox= driver.findElement(By.xpath(format(activeChekBox,name)));
        WebElement c = driver.findElement(By.xpath(format(activeChekBox,name)));
        System.out.println(chekboxInfo);
       // System.out.println(driver.findElement(By.xpath(format(activeChekBox,name)+"/*/*/*")));
        if(!(c.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        //System.out.println(driver.findElement(By.xpath(format(activeChekBox,name))));
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
