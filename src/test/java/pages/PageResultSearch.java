package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PageResultSearch extends Page{
    public PageResultSearch(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public void foundLink(String link){
        List<WebElement> resultElements = driver.findElements(By.xpath("//div[@class='rc']//a[@href]//cite[@class='iUh30']"));
        for(int i=0; i<resultElements.size(); i++)
        {
            String listitem = resultElements.get(i).getAttribute("innerHTML");
            System.out.println(listitem);
            if (listitem.equals(link))
            {
                resultElements.get(i).click();
                break;
            }
        }

    }
}
