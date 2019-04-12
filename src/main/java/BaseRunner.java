import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    public WebDriverWait wait;
    public String browserName = System.getProperty("browser");
    String baseUrl;
    File folder;

    @Before
    public void setUp() {
        baseUrl = "https://www.tinkoff.ru/mobile-operator/tariffs/";

        folder = new File(UUID.randomUUID().toString());
        folder.mkdir();
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", folder.getAbsolutePath());
        prefs.put("plugins.always_open_pdf_externally", true);


        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver,20);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.manage().window().maximize();
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
