import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static java.lang.String.format;



public class Button {
    WebDriver driver ;
    String nameButton = "//div[contains(text(),'%s')]/../../../button";

    public Button(WebDriver driver){
        this.driver = driver;
        return;
    }

    public Button buttonClick(String name){
        driver.findElement(By.xpath(format(nameButton, name))).click();
        return this;
    }

    public Button buttonLabel(String name){
       System.out.println(driver.findElement(By.xpath(format(nameButton, name))).getText());
        return this;
    }
}
