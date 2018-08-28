package com.depuracion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.depruacion.entidad.Usuario;

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

	public static void CrearExcelConUsuario() {
		ArrayList<Usuario> datos =  new ArrayList<>();
		// titulos
		String[] titulos = { "Usuario", "Clave", "Resultado" };
		datos = TxtUtil.obtenerUsuariosYClaves();

		File archivo = new File(Util.EXCEL_FILE_PATH + Util.EXCEL_FILE);

		// Creamos el libro de trabajo de Excel formato OOXML
		Workbook workbook = new XSSFWorkbook();

		// La hoja donde pondremos los datos
		XSSFSheet pagina = (XSSFSheet) workbook.createSheet(Util.EXCEL_SHEET);

		// Creamos el estilo paga las celdas del encabezado
		CellStyle style = workbook.createCellStyle();
		// Indicamos que tendra un fondo azul aqua
		// con patron solido del color indicado
		style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		// Creamos una fila en la hoja en la posicion 0
		XSSFRow fila = pagina.createRow(0);

		// Creamos el encabezado
		for (int i = 0; i < titulos.length; i++) {
			// Creamos una celda en esa fila, en la posicion
			// indicada por el contador del ciclo
			XSSFCell celda = fila.createCell(i);

			// Indicamos el estilo que deseamos
			// usar en la celda, en este caso el unico
			// que hemos creado
			celda.setCellStyle(style);
			celda.setCellValue(titulos[i]);
		}

		// Y colocamos los datos en esa fila
		for (int i = 1; i < datos.size(); i++) {
			// Ahora creamos una fila en la posicion 1
			fila = pagina.createRow(i);

			// Creamos una celda en esa fila, en la
			// posicion indicada por el contador del ciclo
			fila = pagina.getRow(i);

			XSSFCell celda = fila.getCell(0, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (celda == null) {
				celda = fila.createCell(0);
				celda.setCellValue(datos.get(i).getNombreUsuario());
				//System.out.println("guardando usuario :" + datos.get(i).getNombreUsuario());

			}
			celda = fila.getCell(1, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (celda == null) {
				celda = fila.createCell(1);
				celda.setCellValue(datos.get(i).getClave());

			}
		}
		// Ahora guardaremos el archivo
		try {
			// Creamos el flujo de salida de datos,
			// apuntando al archivo donde queremos
			// almacenar el libro de Excel
			FileOutputStream salida = new FileOutputStream(archivo);

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();
			
			System.out.println("Archivo creado existosamente en {0}" + archivo.getAbsolutePath());

		} catch (FileNotFoundException ex) {
			System.out.println("Creanado Archivo sin depurar: NO ENCONTRADO EN EL SISTEMA");

		} catch (IOException ex) {
			System.out.println("Error de entrada/salida" + ex.getMessage());
		}
	}

	public static Object[][] getTableArray() throws Exception {

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(Util.EXCEL_FILE_PATH);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(Util.EXCEL_SHEET);
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
