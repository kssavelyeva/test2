import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    public WebDriverWait wait;
    public String browserName = System.getProperty("browser");
    String baseUrl;

    @Before
    public void setUp() {
        driver = BrowsersFactory.chrome.create();
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriver getDriver(){
        BrowsersFactory.valueOf(System.getProperty("browser"));
        return BrowsersFactory.valueOf(browserName).create();

    }

}
