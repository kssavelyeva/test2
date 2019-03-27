import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        Thread.sleep(10000);
        for (int i=0; i<elements.size(); i++)
        {
            String listitem = elements.get(i).getText();
            if(listitem.contains("мобайл тинькофф тарифы"))
            {
                elements.get(i).click();
                break;
            }
        }



        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Подборки'])[1]/following::b[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Все результаты'])[3]/following::h3[1]")).click();
        // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_1 | ]]
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Нижегородская область'])[1]/following::p[1]")).click();
        assertEquals("Тарифы Тинькофф Мобайла", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Нижегородская область'])[1]/following::p[1]")).getText());
        // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_local | ]]
        driver.close();
        // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_1 | ]]

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