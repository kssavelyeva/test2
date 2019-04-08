package app;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.MobilePage;
import pages.GooglePage;
import pages.PageResultSearch;
import test.BrowsersFactory;
import java.util.concurrent.TimeUnit;

public class Application {

    private WebDriverWait wait;
    private WebDriver driver;
    public GooglePage googlePage;
    public MobilePage tinkoffMobile;
    public PageResultSearch googleSearchResult;


    public Application() {
        driver = new EventFiringWebDriver(getDriver());
        ((EventFiringWebDriver) driver).register(new BrowsersFactory.MyListener());
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //page
        googlePage = new GooglePage(driver);
        googleSearchResult = new PageResultSearch(driver);
        tinkoffMobile = new MobilePage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

    private WebDriver getDriver() {
        return BrowsersFactory.buildDriver();
    }

}
