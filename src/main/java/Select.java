import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;


import java.util.List;

public class Select {
    String internetSelect = "//div[1]/span[contains(text(), '%s')]/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div";
    String selectCurrentValue = "//span[contains(text(), '%s')]/../div[@class='ui-select__title-flex']";

    String selectClick = "//span[contains(text(), '%s')]/ancestor::div[@data-qa-file='UIDropdownField']";
    protected WebDriver driver;

    public Select(WebDriver driver) {
        this.driver = driver;
    }

    public Select selectClick(String name){
        driver.findElement(By.xpath(format(selectClick,name))).click();
        return this;
    }
    public Select selectValue(String name, String value){

        List<WebElement> result = driver.findElements(By.xpath(format(internetSelect, name)));

        System.out.println(result.size());
        for (int i=0; i<result.size(); i++)
        {
            String listitem = result.get(i).getAttribute("innerHTML");
            System.out.println(listitem);
            if(listitem.contains(value))
            {
                result.get(i).click();
                break;
            }
        }
        return  this;

    }

    public Select currentValue(String name){
        System.out.println(driver.findElement(By.xpath(format(selectCurrentValue, name))).getText());
        return this;
    }
}
