import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import static java.lang.String.format;

public class CheckBox {
    WebDriver driver ;
    String activeChekBox = "//label[contains(text(),'%s')]/../div";

   public CheckBox(WebDriver driver){
        this.driver = driver;
        return;
    }

    public CheckBox chekBoxActive(String name, boolean value){

        WebElement chekbox= driver.findElement(By.xpath(format(activeChekBox,name)));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public CheckBox chekBoxLabel(String name){
        System.out.println(driver.findElement(By.xpath(format(activeChekBox+"/../label", name))).getText());
        return this;
    }

    public void  chekBoxStatus(String name){
        WebElement chek = driver.findElement(By.xpath(format(activeChekBox,name)));
        if(!(chek.getAttribute("innerHTML").contains("cheked")))
        {
            System.out.println("Не активен"+chek.getAttribute("innerHTML") );
        }
        else  {System.out.println("Активен"+chek.getAttribute("innerHTML") );}

    }
}
