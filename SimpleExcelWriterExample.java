package komalhealthcare;

import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelWriterExample {
 
    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");
         
        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);

        Row row1 = sheet.createRow(0);
        Cell cellTitle = row1.createCell(1);

        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("Title");

        Cell cellAuthor = row1.createCell(2);
        cellAuthor.setCellStyle(cellStyle);
        cellAuthor.setCellValue("Author");

        Cell cellPrice = row1.createCell(3);
        cellPrice.setCellStyle(cellStyle);
        cellPrice.setCellValue("Price");
                
        int rowCount = 0;
         
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
             
            int columnCount = 0;
             
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
             
        }
        try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
            workbook.write(outputStream);
        }
    }
 
}