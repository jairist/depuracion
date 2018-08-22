package com.depuracion.ejecucion;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.depuracion.login.AOL;
import com.depuracion.util.ExcelUtils;
import com.depuracion.util.Log;
import com.depuracion.util.Util;

public class Ejecucion {

	private static WebDriver driver;
	private static AOL aol;
	private static AOL yahoo;
	private static boolean status;

	@BeforeMethod
	public void beforeMethod() throws Exception {
		System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER);
		// System.out.println(new File("").getAbsolutePath().toString()+
		// "\\tools\\chromedriver.exe");
		Log.startTestCase("Login");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@DataProvider(name = "Authentication")
	public static Object[][] credentials() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE, Util.EXCEL_SHEET);
		return (testObjArray);
	}

	@Test(dataProvider = "Authentication")
	public void ejecucion(String sUserName, String sPassword) throws Exception {
		String[] tMail = sUserName.split("@");
		String mail = tMail[tMail.length - 1].replace(".", ":");
		String ttMail[] = mail.split(":");
		mail = ttMail[0].toLowerCase();

		System.out.println(mail);

		switch (mail) {
		case Util.MAIL_AOL:
			driver.get(Util.AOL_URL);
			driver.findElement(By.id("login-username")).sendKeys(sUserName);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();
			Thread.sleep(3000);
			if (driver.findElement(By.id("username-error")).isDisplayed()) {
				// scribir el excel con el usuario invalido.

			} else {
				driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
				driver.findElement(By.id("login-signin")).click();
				// Si logro entrar encribir en el archivo que el usuario es válido.
				System.out.println("El usuario :"+sUserName
						+ " es Válido");

			}

			break;
		case Util.MAIL_YAHOO:
			driver.get(Util.YAHOO_URL);

			driver.findElement(By.id("login-username")).sendKeys(sUserName);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();
			Thread.sleep(3000);
			if (driver.findElement(By.id("username-error")).isDisplayed()) {
				// scribir el excel con el usuario invalido.

			} else {
				driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
				driver.findElement(By.id("login-signin")).click();
				// si logro entrar encribir en el archivo que el usuario es válido.

			}

			break;

		default:
			break;
		}

	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}