package config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDataSet {
    @DataProvider(name = "RegisterData")
    public Object[][] getData(){
        return new Object[][]{
                {"John","Doe","john8.doe101@gmail.com","Password123","0182837773767","Dhaka"},
                {"Jane","Smith","jane8.smith101@gmail.com","Password123","0186847773767","Mirpur"},
                {"Bob","Brown","bob8.brown101@gmail.com","Password123","0182897773777","Banani"}
        };
    }
    @DataProvider(name = "RegisterCSVData")
    public Object[][] getCSVData() throws IOException {
        String csvFilePath="./src/test/resources/registerdata.csv";
        List<Object[]> data=new ArrayList<>();
        CSVParser csvParser= new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        for(CSVRecord csvRecord: csvParser){
            String firstname=csvRecord.get("firstname");
            String lastname=csvRecord.get("lastname");
            String email =csvRecord.get("email");
            String password =csvRecord.get("password");
            String phoneNumber =csvRecord.get("phoneNumber");
            String address =csvRecord.get("address");
            data.add(new Object[]{firstname,lastname,email,password,phoneNumber,address});
        }
        return data.toArray(new Object[0][]);
    }

}
