import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;


import java.util.List;

public class Select {
    String select = "//div[1]/span[contains(text(), '%s')]/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div";
    String selectCurrentValue = "//span[contains(text(), '%s')]/../div[@class='ui-select__title-flex']";

    String openSelect = "//span[contains(text(), '%s')]/ancestor::div[@data-qa-file='UIDropdownField']";
    protected WebDriver driver;

    public Select(WebDriver driver) {
        this.driver = driver;
    }

    public Select openSelect(String name){
        driver.findElement(By.xpath(format(openSelect,name))).click();
        return this;
    }
    public Select selectValue(String name, String value){

        List<WebElement> result = driver.findElements(By.xpath(format(select, name)));

        for (int i=0; i<result.size(); i++)
        {
            String listItem = result.get(i).getAttribute("innerHTML");
            if(listItem.contains(value))
            {
                result.get(i).click();
                break;
            }
        }
        return  this;

    }

    public Select getCurrentValue(String name){
        System.out.println(driver.findElement(By.xpath(format(selectCurrentValue, name))).getText());
        return this;
    }
}
