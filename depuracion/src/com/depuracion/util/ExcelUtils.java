package com.depuracion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.depuracion.util.EnumWordDictionary.enumNombreColumna;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	private static MissingCellPolicy xRow;

	public static Map<String, String> dataFileData;
	public static ArrayList<Object> dataFileDataInformation;
	public static ArrayList<String> testCaseStatus;
	public static String dataFilePathAndName;
	public final static List<String> dataFileHeader = Arrays.asList(enumNombreColumna.TestCaseName.valor,
			enumNombreColumna.Usuario.valor, enumNombreColumna.Clave.valor, enumNombreColumna.Resultado.valor

	);

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			// you can write a function as well to get Column count
			int totalCols = 2;
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
					System.out.println(tabArray[ci][cj]);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = Cell.getCellType();
			if (dataType == 3) {
				return "";
			} else {
				String CellData = Cell.getStringCellValue();
				return CellData;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}

	}

	public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {

		try {
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, xRow.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {

			throw (e);

		}

	}

	public static void readAndWriteExcelFile() {

		// write the excel to the File system
		// The try/catch clause is to manage any error while create/save the excel file

		try {

			FileInputStream excelfile = new FileInputStream(new File(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE));

			XSSFWorkbook workbook = new XSSFWorkbook(excelfile);

			XSSFSheet sheet = workbook.getSheet(Util.EXCEL_SHEET);

			// Get Row at index 1
			XSSFRow row = sheet.createRow((dataFileDataInformation.size() - 1));

			for (int rowHeader = 0; rowHeader < dataFileHeader.size(); rowHeader++) {

				// Get the Cell at index 2 from the above row
				XSSFCell cell = row.createCell(rowHeader);

				if (dataFileData.containsKey(dataFileHeader.get(rowHeader))) {

					cell.setCellValue(dataFileData.get(dataFileHeader.get(rowHeader)));

				} else {

					cell.setCellValue("Inconcluso");
				}
			}

			excelfile.close();

			// Create the file into the File System
			FileOutputStream fileOutputStreamVariable = new FileOutputStream(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE);
			// Write the information into the file
			workbook.write(fileOutputStreamVariable);
			// close the file and its input streaming data
			fileOutputStreamVariable.close();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	public static int getLastRowNum() {
		int totalRows = 0;

		try {
			FileInputStream ExcelFile = new FileInputStream(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(Util.EXCEL_SHEET);

			totalRows = ExcelWSheet.getLastRowNum();

		} catch (Exception e) {

		}
		return totalRows;

	}
}
