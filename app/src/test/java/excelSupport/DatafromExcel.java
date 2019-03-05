package excelSupport;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class DatafromExcel {

    XSSFWorkbook wb;
    XSSFSheet sheet;
    FileInputStream fis;

    public DatafromExcel(String path) {

        try{

            File src= new File(path);
            fis= new FileInputStream(src);
            wb= new XSSFWorkbook(fis);

        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }

    public String GetData(int sheetNumber, int row, int cell)
    {
        sheet=wb.getSheetAt(sheetNumber);
        String data=sheet.getRow(row).getCell(cell).getStringCellValue();
        return data;
    }

    public void closeFile() throws Exception
    {
        wb.close();

    }
}
