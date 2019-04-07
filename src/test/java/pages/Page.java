//import app.Application;
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Page {
    Logger logger = LoggerFactory.getLogger(Page.class);

    protected  WebDriver driver;
    protected  WebDriverWait wait;

    public Page(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }
    public void getPage(String url) {
        driver.navigate().to(url);
    }
    public  boolean pageLoaded(String titlePage){
        wait.until(d -> d.getTitle().contains(titlePage));
        return true;
    }

    public void switchTab(String nameTab){
        wait.until(d -> {
            boolean switchTab = false;
            for(String pageTitle : driver.getWindowHandles()){
                driver.switchTo().window(pageTitle);
                switchTab = d.getTitle().equals(nameTab);
            }
            return switchTab;
        });
    }

    public void switchToWindow(String windowName){
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                System.out.println(d.getTitle());
                check = d.getTitle().equals(windowName);
            }
            return check;
        });
    }

// для поиска нет изменить
    public List<WebElement> xpathSearcherByText(String searchText) {
        String xpath = String.format("//*[contains(text(),'%s')]", searchText);
        return driver.findElements(By.xpath(xpath));
    }

    public  void openPage(String url){
        driver.navigate().to(url);
    }

    public void closeCurrentTab(){
        driver.close();
        logger.info("Закрыта вкладка");
    }

    public void switchToMainTab(){
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
    }
}
