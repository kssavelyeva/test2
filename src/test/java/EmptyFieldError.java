import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class EmptyFieldError extends BaseRunner {
    @Test
    public void testEmptyFieldError() {
        driver.get(baseUrl);
        driver.findElement(By.name("fio")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Гражданство'])[1]/following::span[1]")).click();
        driver.findElement(By.xpath("(//div[@id='form-application']/div[2]/div/div/form/div[3]/div/div/div/div[2]/div[2]/div/div)")).click();
        driver.findElement(By.name("phone_mobile")).click();
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Поле обязательное'])[1]/following::div[8]")).click();


        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Оставить заявку'])[1]/following::div[2]")).click();
        assertEquals("Укажите ваше ФИО", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия, имя и отчество'])[1]/following::div[3]")).getText());
        assertEquals("Необходимо указать номер телефона", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).getText());
        assertEquals("Для продолжения нужно согласие с условиями", driver.findElement(By.cssSelector("#form-application > div:nth-child(2) > div > div > form > div.ui-form__row.app-form-action-buttons > div > div.ui-form__row.ui-form__row_checkbox._2NQKP.ui-form__row_default-error-view-visible > div > div.ui-form-field-error-message.ui-form-field-error-message_ui-form")).getText()); // Для продолжения нужно согласие с условиями chek-box
        assertEquals("Поле обязательное", driver.findElement(By.xpath("(//*[@id=\"form-application\"]/div[2]/div/div/form/div[4]/div/div[2])")).getText());

    }
    @Test
    public void testNonValidValue(){
        driver.get(baseUrl);

        driver.findElement(By.name("fio")).click();
        driver.findElement(By.name("fio")).sendKeys("Марлен");
        driver.findElement(By.name("email")).click();
        assertEquals("Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)", driver.findElement(By.xpath("(.//*[@id=\"form-application\"]/div[2]/div/div/form/div[1]/div/div[2])")).getText());

        driver.findElement(By.name("fio")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);

        StringSelection selection = new StringSelection("Selenium IDE");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        driver.findElement(By.name("fio")).sendKeys(Keys.CONTROL, "v");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия, имя и отчество'])[1]/following::div[3]")).click();
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия, имя и отчество'])[1]/following::div[3]")).getText());

        // Поле Контактный телефон

        driver.findElement(By.name("phone_mobile")).click();
        driver.findElement(By.name("phone_mobile")).sendKeys("(999) 999-99-0");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).click();
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).getText());


        driver.findElement(By.name("phone_mobile")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);


        driver.findElement(By.name("phone_mobile")).click();
        driver.findElement(By.name("phone_mobile")).clear();
        driver.findElement(By.name("phone_mobile")).sendKeys("(777) 777-77-77");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).click();
        assertEquals("Код оператора должен начинаться с цифры 9", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]")).getText());

        // Поле e-mail
        driver.findElement(By.name("email")).sendKeys("@yandex.ru");
        driver.findElement(By.name("phone_mobile")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[3]")).click();
        assertEquals("Введите корректный адрес эл. почты", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[3]")).getText());

        // Поле Укажите страну
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Гражданство'])[1]/following::span[1]")).click();
        driver.findElement(By.xpath("(//div[@id='form-application']/div[2]/div/div/form/div[3]/div/div/div/div[2]/div[2]/div/div)")).click();
        driver.findElement(By.name("temp_non_resident_nationality")).click();
        driver.findElement(By.name("temp_non_resident_nationality")).clear();
        driver.findElement(By.name("temp_non_resident_nationality")).sendKeys("China");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]")).click();
        assertEquals("Выберите страну из выпадающего списка", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]")).getText());
    }
}