package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MobilePage extends  Page{
    public MobilePage(WebDriver driver) {
        super(driver);
}
    public void open()  {
        driver.navigate().to("https://www.tinkoff.ru/mobile-operator/tariffs/");
        pageLoaded("Тарифы Тинькофф Мобайла");
        logger.info("Открыта страница https://www.tinkoff.ru/mobile-operator/tariffs/" );
    }

   // методы для клика полей

    public void clickName(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'Фамилия')]/../../../div/input[last()]")).click();
            logger.info("Курсор в поле ФИО" );
            return true;
        });
    }
    public void clickPhone(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'телефон')]/../../../div/input[last()]")).click();
            logger.info("Курсор в поле Контактный телефон" );
            return true;
        });
    }
    public void clickEmail(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'почта')]/../../../div/input[last()]")).click();
            logger.info("Курсор в поле Email" );
            return true;
        });

    }
    public void clickCountry(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[@class='ui-select__value']")).click(); // гражданство
            driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();
            driver.findElement(By.xpath("//input[@name='temp_non_resident_nationality']")).click();
            driver.findElement(By.cssSelector("[name='temp_non_resident_nationality']")).click();
            logger.info("Курсор в поле Выберите страну" );
            return true;
        });
    }

// проверка ошибок на пустые поля

    public void errorEmptyName(){
        assertEquals("Укажите ваше ФИО",
                driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'error-message')]"))
                        .getText());
        logger.info("Проверка наличия ошибки при пустом поле ФИО " );
    }
    public void errorEmptyPhone(){
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_tel')]//*/div[contains(text(), 'указать номер')]"))
                        .getText());
        logger.info("Проверка наличия ошибки при пустом поле Котнактный телефон " );
    }
    public void errorEmptyCountry(){
        assertEquals("Поле обязательное",
                driver.findElement(By
                        .xpath("//*[contains(@class, 'ui-form__row ui-form__row_dropdownSuggest ui-form__row_default-error-view-visible')]" +
                                "//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]"))
                        .getText());
        logger.info("Проверка наличия ошибки при пустом поле Выберать страну " );
    }
    public void errorEmptyCondition(){
        assertEquals("Для продолжения нужно согласие с условиями",
                driver.findElement(By.
                        xpath("//label[@for='agreement']/..//../../div[contains(text(),'Для продолжения')]"))
                        .getText());
        logger.info("Проверка наличия ошибки при пустом чек-боксе Условия опльзовательского соглашения " );
    }

    // проверка ошибок на некорректные вводимые значения

    public void errorEnglishName(){
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис",
               driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());
        logger.info("Проверка наличия ошибки при вводе латинских букв в поле ФИО" );
    }
    public void errorShortName(){
        assertEquals("Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)",
               driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());
        logger.info("Проверка наличия ошибки при коротком имени в поле ФИО " );
    }
    public void errorShortPhone(){
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]"))
                        .getText());
        logger.info("Проверка наличия ошибки при коротком номере в поле Котнактный телефон " );
    }
    public void errorCodePhone(){
        assertEquals("Код оператора должен начинаться с цифры 9",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]"))
                        .getText());
        logger.info("Проверка наличия ошибки при неправильном коде телефона в поле Котнактный телефон " );
    }
    public void errorIncorrectCountry(){
        assertEquals("Выберите страну из выпадающего списка",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]"))
                        .getText());
        logger.info("Проверка наличия ошибки при неправильно введеном названии страны в поле Выберите страну" );
    }
    public void errorIncorrectEmail(){
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[3]"))
                        .getText());
        logger.info("Проверка наличия ошибки при неправильно введеном e-mail в поле E-mail" );

    }

        // методы для заполнения полей
    public void textInputName(String nameValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'Фамилия')]/../../../div/input[last()]")).sendKeys(nameValue);
            return true;
        });
        logger.info("В поле ФИО введено: " + nameValue);
    }
    public void textInputPhone( String phoneValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'телефон')]/../../../div/input[last()]")).sendKeys(phoneValue);
            return true;
        });
        logger.info("В поле Контактный телефон введено: " + phoneValue);
    }
    public void textInputEmail( String emailValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'почта')]/../../../div/input[last()]")).sendKeys(emailValue);
            return true;
        });
        logger.info("В поле E-mail введено: " + emailValue);
        return ;
    }
    public void textInputCountry( String countryValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[@class='ui-select__value']")).click(); // гражданство
            driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();
            driver.findElement(By.xpath("//input[@name='temp_non_resident_nationality']")).click();
            driver.findElement(By.cssSelector("[name='temp_non_resident_nationality']")).sendKeys(countryValue);
            return true;
        });
        logger.info("В поле Выберите страну введено: " + countryValue);
        return ;
    }

    // Методы для удаления значений из полей

    public void deleteValueName(){
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);
        logger.info("Из поля ФИО удалено значение");
    }
    public void deleteValuePhone(){
        driver.findElement(By.name("phone_mobile")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);
        logger.info("Из поля Контактный телефон удалено значение");
    }

    //Работа с кнопками
    public void buttonOrderClick(){
        wait.until(d -> {d.findElement(By.xpath("//div[contains(text(),'Заказать')]/../../../button")).click();
        return true;});
        logger.info("Кнопка Заказасть сим-карту нажата");
        return;
    }
    public void buttonRequestClick(){
        wait.until(d -> {d.findElement(By.xpath("//div[contains(text(),'Оставить заявку')]/../../../button")).click();
            return true;});
        logger.info("Кнопка Оставить заявку нажата");
    }
    public void  buttonOrderShipping(){
        wait.until(d ->{
            d.findElement(By
                    .xpath("//div[@class='UIAppointmentForm__button_xyTKE UIAppointmentForm__buttonPrimary_P00g7']//button[@type='button']"))
                    .isEnabled();
            return true;
        });
        logger.info("Кнопка Заказасть доставку существует");
    }
    // метод для копирвоания

    public void CopyValueName(String value){
        StringSelection selection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "v");
        logger.info(value + "скопирвоано в поле ФИО");
    }

    // изменение региона
    public void changeCity(String city){
       wait.until(d -> {
           d.findElement(By. xpath("//span[contains(text(),'Нет, изменить') and @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']")).click();
           boolean townCheck = false;
           List<WebElement> result = driver.findElements(By.xpath("//div[@class='MobileOperatorRegionsPopup__listParts_16aoL']/div/div"));

           System.out.println(result.size());
           for (int i=0; i<result.size(); i++)
           {
               String listitem = result.get(i).getAttribute("innerHTML");
               System.out.println(listitem);
               if(listitem.contains(city))
               {
                   result.get(i).click();
                   townCheck = true;
                   break;
               }
           }
          return townCheck;
       });
        logger.info("Регион изменен на " + city);
    }

    // методы для работы с селекторами Интернет и Звонки

    public boolean selectInterntet(String value){
        wait.until(d -> {d.findElement(By
                .xpath("//span[contains(text(), 'Интернет')]/ancestor::div[@data-qa-file='UIDropdownField']"))
                .click();
            return this;
        });
        boolean selectChek = false;
        List<WebElement> result = driver.findElements
                (By.xpath("//div[1]/span[contains(text(), 'Интернет')]" +
                        "/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div"));

        System.out.println(result.size());
        for (int i=0; i<result.size(); i++)
        {
            String listitem = result.get(i).getAttribute("innerText");
            if(listitem.contains(value))
            {
                result.get(i).click();
                selectChek = true;
                break;
            }
        }
        logger.info("Тариф в поле Интернет выбран " + value);
        return  selectChek;
    }
    public boolean selectPhoneCalls(String value){
        wait.until(d -> {d.findElement(By
                .xpath("//span[contains(text(), 'Звонки')]/ancestor::div[@data-qa-file='UIDropdownField']"))
                .click();
            return this;
        });

            List<WebElement> result = driver.findElements
                    (By.xpath("//div[1]/span[contains(text(), 'Звонки')]" +
                            "/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div"));
            System.out.println(result.size());
            wait.until(d ->{
                boolean selectChek = false;
                for (int i=0; i<result.size(); i++)
                {
                    String listitem = result.get(i).getAttribute("innerText");
                    if(listitem.contains(value))
                    {
                        result.get(i).click();
                        selectChek = true;
                        break;
                    }
                }
                return  selectChek;
            });
        logger.info("Тариф в поле Звонки выбран " + value);
        return true;
    }

    // чек боксы

    public MobilePage chekBoxConditions(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[@for='agreement']/..//../div[@tabindex]"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Условия передачи информации выбран");
        return this;
    }
    public MobilePage chekBoxModem(boolean value){
        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Режим модема')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Режим модема выбран");
        return this;
    }
    public MobilePage chekBoxSMS(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Безлимитные СМС')]/../div"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Безлимтиные СМС выбран выбран");
        return this;
    }
    public MobilePage chekBoxMessenger(boolean value){
        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Мессенджеры')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Мессенджеры выбран");
        return this;
    }
    public MobilePage chekBoxSocialNetwork(boolean value){
        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Социальные сети')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Социальные сети выбран");
        return this;
    }
    public MobilePage chekBoxMusic(boolean value){
        WebElement chekbox= driver.findElement(By
                .xpath("//label[contains(text(),'Музыка')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Музыка выбран");
        return this;
    }
    public MobilePage chekBoxVideo(boolean value){
        WebElement chekbox= driver.findElement(By
                .xpath("//label[contains(text(),'Видео')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        logger.info("Чек-бокс Видео выбран");
        return this;
    }

    /// что делать с селктом со страной

    public void refresh(){
        wait.until(d -> {
            d.navigate().refresh();
        return this;
        }
        );
        logger.info("СТраница обновлена");
    }

    // сравнение текста, сумм
    public void equalsDefolt(String value){
        wait.until(d ->{
            assertNotEquals(value,driver.findElement(By
                    .xpath("//h3[contains(text(),'Общая цена')]"))
                    .getText().replaceAll("[^0-9]", ""));
            return true;
        });
        logger.info("Суммы тарифов, установенных по дефолту, разные");
    }
    public void equalsMaxSum(String value){
        wait.until(d ->{
            assertEquals(value,driver.findElement(By
                    .xpath("//h3[contains(text(),'Общая цена')]"))
                    .getText().replaceAll("[^0-9]", ""));
            return  true;
        });
        logger.info("Суммы с максимально выбранными тарифами одинаковые");
    }
    public void equalsCity(String cityName){
       wait.until(d -> {assertEquals(cityName,
               d.findElement(By.xpath("//div[@class='MvnoRegionConfirmation__title_DOqnW']")).getText());
           return true; });
        logger.info("Значение города сответсует выбранному "+ cityName);
    }
}


