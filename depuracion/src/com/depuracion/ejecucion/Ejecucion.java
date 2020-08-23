package com.depuracion.ejecucion;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

	public void beforeMethod() throws Exception {
		ExcelUtils.CrearExcelConUsuario();
		System.setProperty("webdriver.chrome.driver", Util.CHROME_DRIVER); 
		//driver = new ChromeDriver();
//		System.setProperty("webdriver.gecko.driver", Util.FIREFOX_PATH);
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", true);
//		driver = new FirefoxDriver(capabilities);

	}
	public void affertMethod() throws Exception {
		
		driver.quit();
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
				System.out.println("no se encontro el elemento" + nsee.getMessage());
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
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get(Util.AOL_URL);
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
				System.out.println("El usuario :" + sUserName + " es Válido");
				ExcelUtils.setCellData("Valido", i, 2);
			}
		} else {
			// scribir el excel con el usuario invalido .
			System.out.println("El usuario :" + sUserName + " es Inválido");
			ExcelUtils.setCellData("Invalido", i, 2);

		}
		driver.close();
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
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get(Util.YAHOO_URL);

		driver.findElement(By.id("login-username")).sendKeys(sUserName);
		Thread.sleep(2000);
		driver.findElement(By.id("login-signin")).click();
		Thread.sleep(3000);

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
				ExcelUtils.setCellData("Valido", i, 2);
				System.out.println("El usuario :" + sUserName + " es Válido");
			}

		} else {
			ExcelUtils.setCellData("Invalido", i, 2);
			System.out.println("El usuario :" + sUserName + " es Inválido");
		}
		driver.close();
		
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
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
		driver.close();
		
	}

}