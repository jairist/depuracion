package com.depuracion.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.depuracion.util.Log;

public class Yahoo {

	public static boolean Login(String sUserName, String sPassword, WebDriver driver) throws Exception {

		driver.findElement(By.id("login-username")).sendKeys(sUserName);
		Thread.sleep(2000);
		driver.findElement(By.id("login-signin")).click();
		Thread.sleep(3000);
		if (driver.findElement(By.id("username-error")).isDisplayed()) {
			return false;

		} else {
			driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
			driver.findElement(By.id("login-signin")).click();

			return true;

		}
	}

}
