import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class EmptyFieldError extends BaseRunner {
    @Test
    public void testEmptyFieldError() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'ui-input__column')]")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_checkbox _2NQKP')]//*[contains(@class, '_5tJV0')]")).click(); // чек-бокс
        driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_tel')]")).click(); // телефон

        driver.findElement(By.xpath("//*[contains(@class, 'ui-dropdown-select ui-dropdown-select_mobile_native')]")).click(); // ниспадающий спсиок

            //дописать гражданство ui-form-field-error-message ui-form-field-error-message_ui-form

        driver.findElement(By.xpath("//*[contains(@name, 'email')]")).click();

        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Оставить заявку'])[1]/following::div[2]")).click();
        assertEquals("Укажите ваше ФИО", driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'error-message')]")).getText());

        assertEquals("Необходимо указать номер телефона", driver.findElement(By.xpath("//*[contains(@class, 'ui-form__fieldset ui-form__fieldset_inline ui-form__fieldset_column-mob')]//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]")).getText());
//*[contains(@class, 'ui-form__row ui-form__row_tel')]
        driver.findElement(By.xpath("//*[contains(@name, 'email')]")).click();
        assertEquals("Для продолжения нужно согласие с условиями",
                driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_checkbox _2NQKP ui-form__row_default-error-view-visible')]//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]")).getText()); // Для продолжения нужно согласие с условиями chek-box

        driver.findElement(By.xpath("//*[contains(@name, 'email')]")).click();
//доделать
        assertEquals("Поле обязательное", driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_dropdownSuggest ui-form__row_default-error-view-visible')]//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]")).getText());

    }
    @Test
    public void testNonValidValue(){
        driver.get(baseUrl);

        driver.findElement(By.cssSelector("input[name='fio']")).click();
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys("Марлен");
        driver.findElement(By.cssSelector("input[name='email']")).click();
        assertEquals("Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)",
                driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());

        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);

        StringSelection selection = new StringSelection("Selenium IDE");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "v");
        driver.findElement(By.cssSelector("input[name='email']")).click();
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис",
                driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());

        // Поле Контактный телефон

        //driver.findElement(By.cssSelector("input[name='phone_mobile']")).click();
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).sendKeys("(999) 999-99-0");
        driver.findElement(By.cssSelector("input[name='email']")).click();
       // driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).click();
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_tel.ui-form__row_default-error-view-visible > div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());


        driver.findElement(By.name("phone_mobile")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);

       // driver.findElement(By.cssSelector("input[name='phone_mobile']")).click();
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).clear();
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).sendKeys("(777) 777-77-77");
        driver.findElement(By.cssSelector("input[name='email']")).click();
        //driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).click();
        assertEquals("Код оператора должен начинаться с цифры 9", driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_tel.ui-form__row_default-error-view-visible > div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

        // Поле e-mail
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("@yandex.ru");
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).click();
      //  driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[3]")).click();
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_dropdownSuggest.ui-form__row_default-error-view-visible > " +
                        "div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

        // Поле Укажите страну
        driver.findElement(By.cssSelector("span.ui-select__value")).click();
        driver.findElement(By.cssSelector("div.ui-dropdown-field-list__item-event-handler div.ui-dropdown-field-list__item-view > span.ui-dropdown-field-list__item-text")).click();
        driver.findElement(By.name("temp_non_resident_nationality")).click();
        driver.findElement(By.name("temp_non_resident_nationality")).clear();
        driver.findElement(By.name("temp_non_resident_nationality")).sendKeys("China");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]")).click();
        assertEquals("Выберите страну из выпадающего списка", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]")).getText());
    }
    @Test
    public void testSwithcTabs () throws InterruptedException {
        driver.get("https://www.google.ru/");

        driver.findElement(By.name("q")).sendKeys("мобайл тинькофф");
                //  driver.findElements(By.xpath("//ul[@role='listbox']/li"));
        List<WebElement> elements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant ::div[@class='sbl1']"));
        Thread.sleep(1000); /// исправить на wait

        for (int i=0; i<elements.size(); i++)
        {
            String listitem = elements.get(i).getText();
            if(listitem.contains("мобайл тинькофф тарифы"))
            {
                elements.get(i).click();
                break;
            }
        }
        driver.getTitle().equals("тинькофф мобайл тарифы - Поиск в Google");
        String tabGoogle = driver.getWindowHandle();
        System.out.println(tabGoogle);



        List<WebElement> resultElements = driver.findElements(By.xpath("//div[@class='rc']//a[@href]//cite[@class='iUh30']"));

        Thread.sleep(1000); /// исправить на wait

        for(int i=0; i<resultElements.size(); i++){
//            System.out.println(resultElements.getAttribute("href"));
//            System.out.println(element.getText());
             String listitem = resultElements.get(i).getText();

            if (listitem.contains("https://www.tinkoff.ru/mobile-operator/tariffs/"))
              resultElements.get(i).click();
            break;
        }
        Thread.sleep(1000); /// исправить на wait

        driver.getTitle().equals("Тарифы Тинькофф Мобайла");

        driver.switchTo().window(tabGoogle).close();
        Thread.sleep(100000);
        String urlMobile = driver.getCurrentUrl();

        assertEquals(urlMobile, "https://www.tinkoff.ru/mobile-operator/tariffs/");
        Thread.sleep(1000);
    }

    @Test
    public void inactiveButton() {
        driver.get(baseUrl);

        driver.findElement(By.xpath("//span[contains(text(),'Нет, изменить') and @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Москва и Московская обл.')]")).click();
        wait.until(d-> d.findElement(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']")));

        driver.findElement(By.xpath("//span[@class='ui-select__value']")).click();

        driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();

        driver.findElement(By.name("temp_non_resident_nationality")).sendKeys("Украина");
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).sendKeys("(999) 999-99-00");
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("my@yandex.ru");
        driver.findElement(By.cssSelector("input[name='fio']")).click();
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys("Иванов Иван");

        driver.findElement(By.xpath("//div[@class='BlockingButton__blockingButton_N-UUk']")).click();

        driver.findElement(By.xpath("//div[@class='UIAppointmentForm__button_xyTKE UIAppointmentForm__buttonPrimary_P00g7']//button[@type='button']")).isEnabled();


    }

    @Test
    public void changeRegion() throws InterruptedException {
        driver.get(baseUrl);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'Нет, изменить') and @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Москва и Московская обл.')]")).click();
        wait.until(d-> d.findElement(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']")));  //ghjdthbnm
        driver.navigate().refresh();
        driver.getCurrentUrl().contains(baseUrl);
        assertNotEquals(By.xpath("//h3[contains(text(),'Общая цена')]").toString(),"296");





        driver.findElement(By.xpath("//div[@class='ui-form__fieldset ui-form__fieldset_inline ui-form__fieldset_column-mob']/div[2]/div[@class='ui-form__field']")).click();
        List<WebElement> resultCalls = driver.findElements(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']/" +
                "div[@class='ui-form']/div[@class='ui-form__row']/div[@class='ui-form__fieldset ui-form__fieldset_focused ui-form__fieldset_inline " +
                "ui-form__fieldset_column-mob']/div[2]/div[1]//span[@class='ui-dropdown-field-list__item-text']"));

        System.out.println(resultCalls.size());
        //span[@class='ui-dropdown-field-list__item-text']"
        Thread.sleep(1000); /// исправить на wait

        for (int i=0; i<resultCalls.size(); i++)
        {
            String listitem = resultCalls.get(i).getAttribute("innerHTML");

            System.out.println(listitem);
            if(listitem.contains("Безлимитные минуты"))
            {
                resultCalls.get(i).click();
                break;
            }
        }

        driver.findElement(By.xpath("//body/div[@class='application']/div[@data-qa-file='UIFormAppPopup']/div[@class='PlatformLayout__layout_1an9n ui-layout']/div[@class='PlatformLayout__layoutPage_1e7Pm']/div[@class='UILayoutPage__page_VOKnn']/div[@class='UILayoutWrapper__wrapper_2RHiZ']/div[@class='PortalContainer__container_1xEgI']/div[@class='form-app-personalized-landing-page']/div[@id='x9c051']/div[@data-qa-file='UniversalErrorFallback']/div[@data-qa-file='BackgroundForBlock']/div[@class='ResponsiveContainer__section_2LKAl']/div[@data-qa-file='ResponsiveContainer']/div[@class='ResponsiveContainer__slimLayout_sUwff ResponsiveContainer__fullWidth_3jp4R']/div[@id='form']/div[@class='js-page-form']/div[@data-qa-file='FormStepsController']/div[@data-qa-file='aboutInfoPopup']/div[@data-qa-file='FormStepComponent']/div[@data-qa-file='UIFormWrapper']/div[@class='UIFormWrapper__container_1TIK8']/div[@data-qa-file='UIFormWrapper']/div[@id='form-application']/div[@data-qa-file='UIFormWrapper']/div[@class='ui-form__wrapper']/div[@class='FormStepComponent__formStepWrapper_1PTAs']/form[@class='ui-form']/div[@class='MobileOperatorProductCalculator__root_3WX9U']/div[@class='ui-form']/div[@class='ui-form__row']/div[@class='ui-form__fieldset ui-form__fieldset_inline ui-form__fieldset_column-mob']/div[1]/div[1]/div[1]/div[1]")).click();
        //Thread.sleep(1000);


        List<WebElement> resultElements = driver.findElements(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']/div[@class='ui-form']/div[@class='ui-form__row']//div[@class='ui-dropdown-field-list ui-dropdown-field-list__opened']//div[@data-qa-file='UIDropdownList']"));

        Thread.sleep(1000); /// исправить на wait
        System.out.println(resultElements.size());
        //span[@class='ui-dropdown-field-list__item-text']"
        Thread.sleep(1000); /// исправить на wait

        for (int i=0; i<resultElements.size(); i++)
        {
            String listitem = resultElements.get(i).getAttribute("innerHTML");

            System.out.println(listitem);
            if(listitem.contains("Безлимитный"))
            {
                resultElements.get(i).click();
                break;
            }
        }

        Thread.sleep(100); /// исправить на wait

        driver.findElement(By.xpath("//label[contains(text(),'Безлимитные СМС')]")).click();
        driver.findElement(By.xpath("//label[contains(text(),'Режим модема')]")).click();

        Thread.sleep(100); /// исправить на wait



        assertEquals("2546",driver.findElement(By.xpath("//h3[contains(text(),'Общая цена')]")).getText().replaceAll("[^0-9]", ""));


        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys("Иванов Иван");

        



    }
}



/* wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("yt")
                .pollingEvery(Duration.ofMillis(500))
                .until( d-> {
            By listItems = By.xpath("//ul[@role='listbox']//li/descendant ::div[@class='sbl1']");
            List<WebElement> elements = driver.findElements(listItems);
            for (WebElement el: elements){
                System.out.println(el.getText());

                if(el.getText().equals(("мобайл тинькофф тарифы").toLowerCase()))
                    el.click();
                break;
            }
        return d.getTitle().equals("тинькофф мобайл тарифы - Поиск в Google");
        });*/

/*List<WebElement> resultElements = driver.findElements(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']/div[@class='ui-form']/div[@class='ui-form__row']/div[@class='ui-form__fieldset ui-form__fieldset_inline ui-form__fieldset_column-mob']/div[1]/div[1]//div[@class='ui-dropdown-field-list__item-view ui-select__option_with-subtext_right-side']/span[@class='ui-dropdown-field-list__item-text']"));
                //span[@class='ui-dropdown-field-list__item-text']"
        Thread.sleep(1000); /// исправить на wait

        for(int i=0; i<resultElements.size(); i++){
//
            String listitem = resultElements.get(i).getAttribute("innerHTML");

            System.out.println(resultElements);

            if (listitem.contains("Безлимитный интернет"))
                resultElements.get(i).click();
            break;
        }*/