package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class GooglePage extends  Page{
    public GooglePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "q")

    public WebElement searchBox;

    public  void open(){
        driver.navigate().to("https://www.google.ru/");
        pageLoaded("Google");
    }

    //поиск в поле вариантов запроса
    public void searchResultField(String request){
        searchBox.sendKeys(request);
        wait.until(d -> {
            List<WebElement> elements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant ::div[@class='sbl1']"));
            for (int i=0; i<elements.size(); i++)
            {
                String listitem = elements.get(i).getText();
                if(listitem.contains("tinkoff mobile тарифы"))
                {
                    elements.get(i).click();
                    break;
                }}
            return d.getTitle().equals("tinkoff mobile тарифы - Поиск в Google");
        });
        logger.info("Найден вариант запроса " + request);
    }
}
