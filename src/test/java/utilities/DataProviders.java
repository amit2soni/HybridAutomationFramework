package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    // login data provider
    @DataProvider(name="loginData")
    public Object[][] getData() throws IOException {
        String path = "./testData//LoginData.xlsx";
        String sheetName = "Sheet1";
        ExcelUtils xlu = new ExcelUtils(path,sheetName);
        int totalRows = xlu.getRowCount();
        int totalCols = xlu.getCellCount(1);
        Object[][] loginData = new Object[totalRows][totalCols];
        for(int i=1;i<=totalRows;i++){
            for(int j=0;j<totalCols;j++){
                loginData[i-1][j] = xlu.getCellData(i,j);
            }
        }
    return loginData;
    }
}
