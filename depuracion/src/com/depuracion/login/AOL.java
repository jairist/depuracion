package com.depuracion.login;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.depuracion.util.ExcelUtils;
import com.depuracion.util.Log;
import com.depuracion.util.Util;

public class AOL {

	private WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws Exception {
		System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER);
		// System.out.println(new File("").getAbsolutePath().toString()+
		// "\\tools\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Util.AOL_URL);

	}

	@DataProvider(name = "Authentication")
	public static Object[][] credentials() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(new File("").getAbsolutePath() + "//testData//TestData.xlsx",
				"Sheet1");
		return (testObjArray);
	}

	@Test(dataProvider = "Authentication")
	public void Login(String sUserName, String sPassword) throws Exception {
		// driver.findElement(By.xpath(".//*[@id='account']/a")).click();
		driver.findElement(By.id("login-username")).sendKeys(sUserName);
		Thread.sleep(2000);
		driver.findElement(By.id("login-signin")).click();
		Thread.sleep(3000);
		if (driver.findElement(By.id("username-error")).isDisplayed()) {
			Log.info("Usuario invalido");
			
			driver.close();

		} else {
			driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
			driver.findElement(By.id("login-signin")).click();
			//si logro entrar encribir en el archivo que el usuario es válido.
			System.out.println("Usuario Válido");
			
		}
	}
	@AfterMethod
	public void afterMethod() {
		//driver.close();
	}

}
