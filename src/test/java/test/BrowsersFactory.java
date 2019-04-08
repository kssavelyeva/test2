package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowsersFactory {
    public static class MyListener extends AbstractWebDriverEventListener {

        Logger logger = LoggerFactory.getLogger(BrowsersFactory.class);
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            logger.info("Найден элемент " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Найден элемент " + by);
        }
    }
        public static WebDriver buildDriver() {
            ChromeDriver driver = new ChromeDriver() ;
                    return  driver;

            }
    }


