import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    public void testSwithcTabs ()  {
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

        driver.findElement(By.xpath("//span[contains(text(),'Нет, изменить') and" +
                " @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Москва и Московская обл.')]")).click();

        wait.until(d-> d.findElement(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']")));

        TextInput feild = new TextInput(driver);
        feild.fillField("Фамилия","Иван Иванов");
        feild.fillField("телефон", "999 999-99-00");
        feild.fillField("Электронная почта","my@yandex.ru");

        CheckBox checkBox = new CheckBox(driver);
        checkBox.chekBoxActive("Музыка", false);
        checkBox.chekBoxActive("Мессенджеры", false);
        checkBox.chekBoxActive("Социальные сети", false);
        checkBox.chekBoxActive("Видео", false);

        Select select = new Select(driver);
        select.selectClick("Звонки");
        wait.until(d -> select.selectValue("Звонки", "0 минут"));
        select.selectClick("Интернет");
        wait.until(d -> select.selectValue("Интернет", "0 ГБ"));

        driver.findElement(By.xpath("//div[@class='BlockingButton__blockingButton_N-UUk']")).click();

        driver.findElement(By
                .xpath("//div[@class='UIAppointmentForm__button_xyTKE UIAppointmentForm__buttonPrimary_P00g7']//button[@type='button']"))
                .isEnabled();
    }

    @Test
    public void changeRegion() throws InterruptedException {
        driver.get(baseUrl);
        Thread.sleep(1000);
        driver.findElement(By.
                xpath("//span[contains(text(),'Нет, изменить') " +
                        "and @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']"))
                .click();
        driver.findElement(By.xpath("//div[contains(text(),'Москва и Московская обл.')]")).click();
        wait.until(d-> d.findElement(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']")));

        driver.navigate().refresh();
        assertEquals("Москва и Московская область",
                driver.findElement(By.xpath("//div[contains(text(),'Москва и Московская область')]")).getText());

        wait.until(d-> d.findElement(By.xpath("//div[@class='MobileOperatorProductCalculator__root_3WX9U']")));
        String moscovDeafultTotalPrice = driver.findElement(By.xpath("//h3[contains(text(),'Общая цена')]")).getText().replaceAll("[^0-9]", "");
        assertNotEquals("296",driver.findElement(By.xpath("//h3[contains(text(),'Общая цена')]")).getText().replaceAll("[^0-9]", ""));

        Select select = new Select(driver);
        select.selectClick("Звонки");
        select.selectValue("Звонки", "Безлимитные минуты");
        select.selectClick("Интернет");
        select.selectValue("Интернет","Безлимитный");

        CheckBox chekbox = new CheckBox(driver);
        chekbox.chekBoxActive("СМС",true );
        chekbox.chekBoxStatus("СМС");
        chekbox.chekBoxStatus("Режим");
        chekbox.chekBoxActive("Режим модема", true);

        assertEquals("2546",driver.findElement(By.xpath("//h3[contains(text(),'Общая цена')]")).getText().replaceAll("[^0-9]", ""));

    }
}