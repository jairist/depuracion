package com.depuracion.ejecucion;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.depuracion.util.ExcelUtils;
import com.depuracion.util.Log;
import com.depuracion.util.Util;

public class Ejecucion {

	private static WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws Exception {
		ExcelUtils.CrearExcelConUsuario();
		
		

	}

	@Test
	public void ejecucion() throws Exception {

		for (int i = 0; i <= ExcelUtils.getLastRowNum(); i++) {

			try {

				String sUserName = ExcelUtils.getCellData(i, 0);
				String sPassword = ExcelUtils.getCellData(i, 1);

				String[] tMail = sUserName.split("@");
				String mail = tMail[tMail.length - 1].replace(".", ":");
				String ttMail[] = mail.split(":");
				mail = ttMail[0].toLowerCase();

				System.out.println(mail);

				switch (mail) {
				case Util.MAIL_AOL:
					System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER);
					// System.out.println(new File("").getAbsolutePath().toString()+
					// "\\tools\\chromedriver.exe");
					Log.startTestCase("Login");
					driver = new ChromeDriver();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					driver.get(Util.AOL_URL);
					driver.findElement(By.id("login-username")).sendKeys(sUserName);
					Thread.sleep(2000);
					driver.findElement(By.id("login-signin")).click();
					Thread.sleep(3000);
					if (driver.findElements(By.id("username-error")).size() != 0) {
						// scribir el excel con el usuario invalido .
						ExcelUtils.setCellData("Invalido", i, 2);

					} else {
						driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
						driver.findElement(By.id("login-signin")).click();
						// Si logro entrar encribir en el archivo que el usuario es válido.
						ExcelUtils.setCellData("Valido", i, 2);
					}
					driver.close();
					break;
				case Util.MAIL_YAHOO:
					System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER);
					// System.out.println(new File("").getAbsolutePath().toString()+
					// "\\tools\\chromedriver.exe");
					Log.startTestCase("Login");
					driver = new ChromeDriver();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					driver.get(Util.YAHOO_URL);

					driver.findElement(By.id("login-username")).sendKeys(sUserName);
					Thread.sleep(2000);
					driver.findElement(By.id("login-signin")).click();
					Thread.sleep(3000);
					if (!Util.verifyObjectPresent(driver, "login-passwd")) {
						// scribir el excel con el usuario invalido.
						ExcelUtils.setCellData("Invalido", i, 2);

					} else if (!Util.verifyObjectPresent(driver, "recaptchaForm")) {
						ExcelUtils.setCellData("Invalido", i, 2);

					} else {
						driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
						driver.findElement(By.id("login-signin")).click();
						// si logro entrar encribir en el archivo que el usuario es válido.
						ExcelUtils.setCellData("Valido", i, 2);

						System.out.println("El usuario :" + sUserName + " es Válido");

					}
					driver.close();
					break;
				case Util.MAIL_GMAIL:
					ExcelUtils.setCellData("Invalido", i, 2);

					break;

				default:

					break;
				}

			} catch (NoSuchElementException nsee) {
				System.out.println("no se encontro el elemento" + nsee.getMessage());
				throw (nsee);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.out.println("Cantidad de Usuarios trabajados: " + i);
		}
	}

}