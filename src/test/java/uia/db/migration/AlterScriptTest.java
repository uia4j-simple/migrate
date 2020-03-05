package uia.db.migration;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import uia.dao.ColumnType;
import uia.dao.Database;
import uia.dao.TableType;
import uia.dao.pg.PostgreSQL;

public class AlterScriptTest {

    @Test
    public void test() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            try (Database db = new PostgreSQL("localhost", "5432", "pmsdb", "pms", "pms")) {
                Sheet sumSheet = workbook.createSheet("Summary");
                Row sumRowH = sumSheet.createRow(0);
                Cell sumCellH0 = sumRowH.createCell(0);
                sumCellH0.setCellStyle(headerCellStyle);
                sumCellH0.setCellValue("No.");
                Cell sumCellH1 = sumRowH.createCell(1);
                sumCellH1.setCellValue("Table Name");
                sumCellH1.setCellStyle(headerCellStyle);
                Cell sumCellH2 = sumRowH.createCell(2);
                sumCellH2.setCellValue("Description");
                sumCellH2.setCellStyle(headerCellStyle);
                sumSheet.setColumnWidth(0, 1500);
                sumSheet.setColumnWidth(1, 10000);
                sumSheet.setColumnWidth(2, 20000);

                int t = 0;
                for (String tableName : db.selectTableNames()) {
                    t++;
                    TableType table = db.selectTable(tableName, false);

                    Row sumRow = sumSheet.createRow(t);
                    Cell sumCell0 = sumRow.createCell(0);
                    sumCell0.setCellValue(t);
                    Cell sumCell1 = sumRow.createCell(1);
                    sumCell1.setCellValue(tableName);
                    Cell sumCell2 = sumRow.createCell(2);
                    sumCell2.setCellValue(table.getRemark());

                    Sheet sheet = workbook.createSheet(table.getTableName());
                    Row rowH = sheet.createRow(0);
                    Cell cellH0 = rowH.createCell(0);
                    cellH0.setCellValue("No.");
                    cellH0.setCellStyle(headerCellStyle);
                    Cell cellH1 = rowH.createCell(1);
                    cellH1.setCellValue("Column Name");
                    cellH1.setCellStyle(headerCellStyle);
                    Cell cellH2 = rowH.createCell(2);
                    cellH2.setCellValue("Data Type");
                    cellH2.setCellStyle(headerCellStyle);
                    Cell cellH3 = rowH.createCell(3);
                    cellH3.setCellValue("Description");
                    cellH3.setCellStyle(headerCellStyle);
                    sheet.setColumnWidth(0, 1500);
                    sheet.setColumnWidth(1, 10000);
                    sheet.setColumnWidth(2, 7000);
                    sheet.setColumnWidth(3, 20000);

                    int i = 0;
                    for (ColumnType column : table.getColumns()) {
                        i++;
                        Row row = sheet.createRow(i);
                        Cell c0 = row.createCell(0);
                        c0.setCellValue(i);
                        Cell c1 = row.createCell(1);
                        c1.setCellValue(column.getColumnName());
                        Cell c2 = row.createCell(2);
                        c2.setCellValue(column.getDataTypeName());
                        Cell c3 = row.createCell(3);
                        c3.setCellValue(column.getRemark());
                    }

                }
            }
            FileOutputStream fileOut = new FileOutputStream("d:/temp/pmsdbv2.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        }

    }
}
