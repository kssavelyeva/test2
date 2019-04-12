import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import static java.lang.String.format;

public class CheckBox {
    WebDriver driver ;
    String checkBox = "//label[contains(text(),'%s')]/../div";

   public CheckBox(WebDriver driver){
        this.driver = driver;
        return;
    }

    public CheckBox checkboxValue(String name, boolean value){

        WebElement checkbox= driver.findElement(By.xpath(format(checkBox,name)));

        if(!(checkbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            checkbox.click();
        }
        return this;
    }

    public CheckBox getCheckboxLabel(String name){
        System.out.println(driver.findElement(By.xpath(format(checkBox +"/../label", name))).getText());
        return this;
    }

    public boolean  getCheckboxStatus(String name){
        WebElement checked = driver.findElement(By.xpath(format(checkBox,name)));
        if(!(checked.getAttribute("innerHTML").contains("checked")))
        {
            System.out.println("Чек-бокс не активен "+ name );
            return false;
        }
        else  {System.out.println("Чек-бокс активен "+ name );
               return true;
        }
    }
}
