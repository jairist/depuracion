package com.depuracion.util;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Util {

	public static final int WAIT_TIME = 30; // Delay time to wait the website
	// launch completely

	public static final String AOL_URL = "https://login.aol.com";
	public static final String YAHOO_URL = "https://login.yahoo.com/";

	// Valid account for login
	public static final String USER_NAME = "jairistasdfasdf";
	public static final String PASSWD = "Monitorde15";

	public static final String CHROME_DRIVER = new File("").getAbsolutePath() + "\\tools\\chromedriver.exe";

	public static final String EXCEL_FILE_PATH = new File("").getAbsolutePath() + "//testData//";
	public static final String TXT_FILE_PATH = new File("").getAbsolutePath() + "//testData//";
	public static final String TXT_FILE_NAME = "archivo.txt";
	
	
	public static final String EXCEL_FILE = "Usuario.xlsx";
	public static final String EXCEL_SHEET = "Usuarios";

	public static final String MAIL_YAHOO = "yahoo";
	public static final String MAIL_AOL = "aol";
	public static final String MAIL_GMAIL = "gmail";

	public static final String FIREFOX_PATH = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

	public static final String CHROME_PATH = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";

	public static boolean verifyObjectPresent(WebDriver driver, String id) {

		try {

			driver.findElement(By.id(id));

			return true;

		} catch (Exception e) {

			return false;

		}

	}

}
