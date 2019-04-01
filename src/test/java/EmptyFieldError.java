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

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.openqa.selenium.interactions.Actions;


public class EmptyFieldError extends BaseRunner {


    @Test
    public void testEmptyFieldError() {
        driver.get(baseUrl);


        driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'ui-input__column')]")).click();
        driver.findElement(By.xpath("//label[@for='agreement']/..//../div[@tabindex]")).click(); // чек-бокс
        driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_tel')]")).click(); // телефон

        driver.findElement(By.xpath("//span[@class='ui-select__value']")).click(); // гражданство
        driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();
        driver.findElement(By.xpath("//input[@name='temp_non_resident_nationality']")).click();

        driver.findElement(By.xpath("//*[contains(@name, 'email')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Оставить заявку')]/../../../button")).click();

        assertEquals("Укажите ваше ФИО", driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'error-message')]")).getText());
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_tel')]//*/div[contains(text(), 'указать номер')]"))
                        .getText());
        assertEquals("Для продолжения нужно согласие с условиями",
                driver.findElement(By.xpath("//label[@for='agreement']/..//../../div[contains(text(),'Для продолжения')]")).getText());
        assertEquals("Поле обязательное", driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_dropdownSuggest ui-form__row_default-error-view-visible')]//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]")).getText());

    }
    @Test
    public void testNonValidValue(){
        driver.get(baseUrl);

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

        // Поле Укажите страну
        driver.findElement(By.cssSelector("div:nth-child(1) div.ui-select__column > span.ui-select__value")).click();
        driver.findElement(By.cssSelector("div.ui-form__field div.ui-dropdown-select.ui-dropdown-select_mobile_native div.ui-dropdown-field div.ui-dropdown-field-list:nth-child(2) div.ui-dropdown-field-list__item:nth-child(2) div.ui-dropdown-field-list__item-event-handler div.ui-dropdown-field-list__item-view > span.ui-dropdown-field-list__item-text")).click();
        driver.findElement(By.cssSelector("[name='temp_non_resident_nationality']")).sendKeys("China");
        driver.findElement(By.name("email")).click();

        assertEquals("Выберите страну из выпадающего списка",
                driver.findElement(By.cssSelector("form.ui-form div.ui-form__row.ui-form__row_dropdownSuggest" +
                        ".ui-form__row_default-error-view-visible:nth-child(4) div.ui-form__field " +
                        "> div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

        // Поле Контактный телефон

        driver.findElement(By.cssSelector("input[name='phone_mobile']")).sendKeys("(999) 999-99-0");
        driver.findElement(By.cssSelector("input[name='email']")).click();
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_tel.ui-form__row_default-error-view-visible > div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

        driver.findElement(By.name("phone_mobile")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);

        driver.findElement(By.cssSelector("input[name='phone_mobile']")).clear();
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).sendKeys("(777) 777-77-77");
        driver.findElement(By.cssSelector("input[name='email']")).click();

        assertEquals("Код оператора должен начинаться с цифры 9", driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_tel.ui-form__row_default-error-view-visible > div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

        // Поле e-mail
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("@yandex.ru");
        driver.findElement(By.cssSelector("input[name='phone_mobile']")).click();

        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.cssSelector("div.ui-form__row.ui-form__row_dropdownSuggest.ui-form__row_default-error-view-visible > " +
                        "div.ui-form__field > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText());

    }
    @Test
    public void testSwithcTabs () throws InterruptedException {
        driver.get("https://www.google.ru/");

        driver.findElement(By.name("q")).sendKeys("мобайл тинькофф");
        List<WebElement> elements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant ::div[@class='sbl1']"));
        wait.until(d -> {
        for (int i=0; i<elements.size(); i++)
        {
            String listitem = elements.get(i).getText();
            if(listitem.contains("мобайл тинькофф тарифы"))
            {
                elements.get(i).click();
                break;
            }}
            return d.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
        });
        String tabGoogle = driver.getWindowHandle();
        List<WebElement> resultElements = driver.findElements(By.xpath("//div[@class='rc']//a[@href]//cite[@class='iUh30']"));

        for(int i=0; i<resultElements.size(); i++)
        {
             String listitem = resultElements.get(i).getAttribute("innerHTML");
             System.out.println(listitem);
            if (listitem.equals("https://www.tinkoff.ru/mobile-operator/tariffs/"))
            {
              resultElements.get(i).click();
            break;
            }
        }
        wait.until(d -> {
            boolean tinkoffTitle = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                System.out.println(d.getTitle());
                tinkoffTitle = d.getTitle().equals("Тарифы Тинькофф Мобайла");
            }
            return tinkoffTitle;
        });
        driver.switchTo().window(tabGoogle).close();
       /* String urlMobile = driver.getCurrentUrl();
        assertEquals(urlMobile, "https://www.tinkoff.ru/mobile-operator/tariffs/");*/
        wait.until(d -> {
            boolean urlTinkoff = false;
            for(String title: driver.getWindowHandles()){
                driver.switchTo().window(title);
                urlTinkoff = d.getCurrentUrl().equals("https://www.tinkoff.ru/mobile-operator/tariffs/");
            }
            return urlTinkoff;
        });

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

        /*driver.navigate().refresh();
        driver.getCurrentUrl().contains(baseUrl);
        assertNotEquals(By.xpath("//h3[contains(text(),'Общая цена')]").toString(),"296");*/


       driver.findElement(By.xpath("//span[contains(text(), 'Звонки')]/ancestor::div[@data-qa-file='UIDropdownField']")).click();

        Selec s = new Selec(driver);
        String f = "Звонки";
        String d = "Безлимитные минуты";
       s.selectCalls(f,d);
        s.currentValue("Звонки");

        driver.findElement(By.xpath("//span[contains(text(), 'Интернет')]/ancestor::div[@data-qa-file='UIDropdownField']")).click();
        Thread.sleep(1000);


        List<WebElement> resultElements = driver.findElements(By.xpath("//span[contains(text(), 'Интернет')]/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div"));
//div[@class='MobileOperatorProductCalculator__root_3WX9U']/div[@class='ui-form']/div[@class='ui-form__row']//div[@class='ui-dropdown-field-list ui-dropdown-field-list__opened']//div[@data-qa-file='UIDropdownList']
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

        //driver.findElement(By.xpath("//label[contains(text(),'Безлимитные СМС')]")).click();
        //CheckBox ch = new CheckBox(driver);
       // driver.findElement(By.xpath("//label[contains(text(),'СМС')]/../div/*/*/input")).click();

        CheckBox chekbox = new CheckBox(driver);
        chekbox.chekBoxActive(true, "СМС");
        Thread.sleep(1000);
        chekbox.chekBoxActive(false, "СМС");
        chekbox.chekBoxLabel("СМС");
        chekbox.chekBoxStatus("СМС");

        TextInput input = new TextInput(driver);
        input.fillField("Фамилия", "ка");
        input.currentValue("Фамилия");

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