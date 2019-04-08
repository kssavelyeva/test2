package pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page {
    Logger logger = LoggerFactory.getLogger(Page.class);

    protected  WebDriver driver;
    protected  WebDriverWait wait;

    public Page(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

   public void closeCurrentTab(){
       driver.close();
       logger.info("Закрыта активная вкладка");
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
        logger.info("Окно переклчено " + windowName);
    }

    public void switchbetweenWindows(int tabIndex) {
        String handle = this.driver.getWindowHandles().toArray(new String[0])[tabIndex];
        this.driver.switchTo().window(handle);
        logger.info("Окно переклчено " );
    }

    public void getCurrentUrl(String url){
        wait.until(d -> {
            for(String title: driver.getWindowHandles()){
                driver.switchTo().window(title);
               d.getCurrentUrl().equals(url);
            }
            return this;
        });
        logger.info("Теккущее URL " + url );
    }

    public boolean pageLoaded(String substring) {
        wait.until(d -> d.getTitle().contains(substring));
        return true;
    }

}
