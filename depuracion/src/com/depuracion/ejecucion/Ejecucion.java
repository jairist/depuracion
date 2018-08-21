package com.depuracion.ejecucion;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.depuracion.util.ExcelUtils;

public class Ejecucion {

	private static WebDriver driver;

	@DataProvider(name = "Authentication")
	public static Object[][] credentials() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(new File("").getAbsolutePath() + "//testData//TestData.xlsx",
				"Sheet1");
		return (testObjArray);
	}
	
	@Test(dataProvider="Authentication")
    public void Registration_data(String sUserName,String sPassword, WebDriver driver)throws  Exception{
        //driver.findElement(By.xpath(".//*[@id='account']/a")).click();
        driver.findElement(By.id("login-username")).sendKeys(sUserName);
        Thread.sleep(2000);
        driver.findElement(By.id("signin")).click();
        
        driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
		System.out.println(sPassword);
        
        System.out.println(" Login Successfully, now it is the time to Log Off buddy.");
        driver.findElement(By.xpath(".//*[@id='account_logout']/a")).click();

		}
	
	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}