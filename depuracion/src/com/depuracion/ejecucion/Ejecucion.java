package com.depuracion.ejecucion;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.depuracion.util.ExcelUtils;
import com.depuracion.util.Util;

public class Ejecucion {

	public static void main(String[] args) {
		Ejecucion ejecucion = new Ejecucion();
		try {
			ejecucion.beforeMethod();
			ejecucion.ejecucion();
			
			// ejecucion.yahooMailLogin(1, Util.USER_NAME, Util.PASSWD);
			// ejecucion.oalMailLogin(1, Util.USER_NAME, Util.PASSWD);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private static WebDriver driver;
	private static DesiredCapabilities capabilities;

	public void beforeMethod() throws Exception {
		ExcelUtils.CrearExcelConUsuario();
		ChromeOptions options = new ChromeOptions();
		options.setBinary(Util.CHROME_PORTABLE_PATH);
		
		
//		options.addArguments("enable-automation");
//		options.addArguments("disable-infobars");
//        options.addArguments("start-maximized");
		//options.AddArgument("--user-data-dir=" + chromeUserData);
        
		
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("applicationCacheEnabled", false);
		capabilities.setCapability("marionette", true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		
		System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER); 
		System.out.println("Cargando Chrome Portable de ruta : " + Util.CHROME_PORTABLE_PATH);
		//driver = new ChromeDriver(options);
//		System.setProperty("webdriver.gecko.driver", Util.FIREFOX_PATH);
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", true);
//		driver = new FirefoxDriver(capabilities);

	}
	public void affertMethod() throws Exception {
		
		System.exit(0);

	}

	public void ejecucion() throws Exception {

		for (int i = 0; i <= ExcelUtils.getLastRowNum(); i++) {
			try {

				String sUserName = ExcelUtils.getCellData(i, 0);
				String sPassword = ExcelUtils.getCellData(i, 1);

				String[] tMail = sUserName.split("@");
				String mail = tMail[tMail.length - 1].replace(".", ":");
				String ttMail[] = mail.split(":");
				mail = ttMail[0].toLowerCase();

				switch (mail) {
				case Util.MAIL_AOL:
					oalMailLogin(i, sUserName, sPassword);
					break;
				case Util.MAIL_YAHOO:
					yahooMailLogin(i, sUserName, sPassword);
					break;
				case Util.MAIL_YMAIL:
					yahooMailLogin(i, sUserName, sPassword);
					break;
				case Util.MAIL_VERIZON:
					verizonoMailLogin(i, sUserName, sPassword);
					break;
				case Util.MAIL_GMAIL:
					ExcelUtils.setCellData("Invalido", i, 2);
					break;
				default:
					break;
				}

			} catch (NoSuchElementException nsee) {
				System.out.println(" no se encontro el elemento" + nsee.getMessage());
				driver.quit();
				affertMethod();
			
				throw (nsee);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	
			
			System.out.println("Cantidad de Usuarios trabajados: " + i +" de "+ ExcelUtils.getLastRowNum());
			if(i == ExcelUtils.getLastRowNum()) {
				affertMethod();		
			}
	
		}
	}

	/**
	 * @param i
	 * @param sUserName
	 * @param sPassword
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void oalMailLogin(int i, String sUserName, String sPassword) throws InterruptedException, Exception {
		
		// System.out.println(new File("").getAbsolutePath().toString()+
		// "\\tools\\chromedriver.exe");
		//Log.startTestCase("Login");
		driver = new ChromeDriver(capabilities);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get(Util.AOL_URL);
		
		if (Util.verifyObjectPresentByClass(driver, "logout-button")) {
			driver.findElement(By.className("logout-button")).click();
			Thread.sleep(2000);
			driver.findElement(By. cssSelector("input.puree-button-secondary")).click();
			
			Thread.sleep(2000);
			
			driver.close();
			Thread.sleep(2000);
			driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
			driver.get(Util.AOL_URL);
			
		}
			driver.findElement(By.id("login-username")).sendKeys(sUserName);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();
			Thread.sleep(2000);
			
		
		if (Util.verifyObjectPresent(driver, "login-passwd")) {
			driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();

			// Verificar si la contrase;a es valida
			if (Util.verifyObjectPresentByClass(driver, "error-msg")) {
				System.out.println("El usuario :" + sUserName + " tiene contraseña Inválida");
				ExcelUtils.setCellData("Invalido", i, 2);
			} else {
				// Si logro entrar encribir en el archivo que el usuario es válido.
				Thread.sleep(2000);
				System.out.println("El usuario :" + sUserName + " es Válido");
				ExcelUtils.setCellData("Valido", i, 2);
				driver.findElement(By.className("logout-button")).click();
				
			}
		} else {
			// scribir el excel con el usuario invalido .
			System.out.println("El usuario :" + sUserName + " es Inválido");
			ExcelUtils.setCellData("Invalido", i, 2);

		}
		driver.quit();
	}
	/**
	 * @param i
	 * @param sUserName
	 * @param sPassword
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void yahooMailLogin(int i, String sUserName, String sPassword) throws InterruptedException, Exception {
		
		//Log.startTestCase("Login");
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage(). deleteAllCookies();
		
		Thread.sleep(7000); 

		driver.get(Util.YAHOO_URL);
		
		Thread.sleep(2000);
		
		if(Util.verifyObjectPresent(driver, "account-switcher")) {
			System.out.println("Entre aqui ");
			driver.findElement(By. cssSelector("a[role=button]")).click();
			Thread.sleep(2000);
		}
		
		if (Util.verifyObjectPresent(driver, "header-profile-menu")) {
			driver.findElement(By.id("header-profile-menu")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("profile-signout-link")).click();
			Thread.sleep(2000);
			driver.findElement(By. cssSelector("input.puree-button-secondary")).click();
			
			Thread.sleep(2000);
			driver.close();
			Thread.sleep(2000);
			driver.quit();
			Thread.sleep(2000);
			driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
			Thread.sleep(7000);
			driver.get(Util.YAHOO_URL);
			
		}
		Thread.sleep(2000);
		
		//clearCache();
		//driver.get(Util.YAHOO_URL);
		if (Util.verifyObjectPresent(driver, "login-username")) {
			driver.findElement(By.id("login-username")).sendKeys(sUserName);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();
			Thread.sleep(2000);
			
		}
		
		if (Util.verifyObjectPresent(driver, "login-passwd")) {
			// scribir el excel con el usuario invalido.
			// ExcelUtils.setCellData("Invalido", i, 2);
			driver.findElement(By.id("login-passwd")).sendKeys(sPassword);
			Thread.sleep(2000);
			driver.findElement(By.id("login-signin")).click();
			// si logro entrar encribir en el archivo que el usuario es válido.
			Thread.sleep(2000);

			// Verificar si la contrase;a es valida
			if (Util.verifyObjectPresentByClass(driver, "error-msg")) {
				System.out.println("El usuario :" + sUserName + " tiene contraseña Inválida");
				ExcelUtils.setCellData("Invalido", i, 2);
			} else {
				// Si logro entrar encribir en el archivo que el usuario es válido.
				// ExcelUtils.setCellData("Valido", i, 2);
				driver.findElement(By.id("header-profile-menu")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("profile-signout-link")).click();
				Thread.sleep(2000);
				driver.findElement(By. cssSelector("input.puree-button-secondary")).click();
				ExcelUtils.setCellData("Valido", i, 2);
				System.out.println("El usuario :" + sUserName + " es Válido");
				
			}

		} else {
			ExcelUtils.setCellData("Invalido", i, 2);
			System.out.println("El usuario :" + sUserName + " es Inválido");
		}
		driver.quit();
		
	}
	
	/**
	 * @param i
	 * @param sUserName
	 * @param sPassword
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void verizonoMailLogin(int i, String sUserName, String sPassword) throws InterruptedException, Exception {
		
		//Log.startTestCase("Login");
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

		driver.get(Util.MAIL_VERIZON);

		driver.findElement(By.id("IDToken1")).sendKeys(sUserName);
		Thread.sleep(2000);
		driver.findElement(By.id("IDToken2")).sendKeys(sPassword);
		Thread.sleep(2000);
		driver.findElement(By.id("login-submit")).click();
		Thread.sleep(3000);

		if (!Util.verifyObjectPresent(driver, "bannererror")) {
			System.out.println("El usuario :" + sUserName + " tiene contraseña Inválida");
			ExcelUtils.setCellData("Invalido", i, 2);

		} else {
			ExcelUtils.setCellData("Invalido", i, 2);
			System.out.println("El usuario :" + sUserName + " es Inválido");
		}
		driver.quit();
		
	}

}