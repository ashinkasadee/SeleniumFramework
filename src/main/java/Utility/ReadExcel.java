package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public String readExcel(int row, int col, String filePath, String sheetName) throws IOException {
	File file = new File(filePath);
	FileInputStream inputStream = new FileInputStream(file);
	XSSFWorkbook excelWBook = new XSSFWorkbook(inputStream);
	XSSFSheet excelSheet = excelWBook.getSheet(sheetName);
	return excelSheet.getRow(row).getCell(col).getStringCellValue().toString();
	
	
	}
}
