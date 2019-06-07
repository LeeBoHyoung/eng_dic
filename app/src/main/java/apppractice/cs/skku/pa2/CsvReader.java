package apppractice.cs.skku.pa2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    InputStream inputStream;

    public CsvReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

        public List read(){
            List resultList = new ArrayList();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String csvLine;
                int i = 1;
                while ((csvLine = reader.readLine()) != null) {
                    csvLine = csvLine.replace("'", "`");
                    if(i % 2 != 0) {
                            String[] row = new String[3];
                            if(csvLine.toCharArray()[0] == '\"') {
                                row[0] = csvLine.substring(1, csvLine.indexOf("("));
                                row[1] = csvLine.substring(csvLine.indexOf("(") + 1, csvLine.indexOf(")"));
                                row[2] = csvLine.substring(csvLine.indexOf(")") + 1, csvLine.length()-1);
                            }
                            else{
                                row[0] = csvLine.substring(0, csvLine.indexOf("("));
                                row[1] = csvLine.substring(csvLine.indexOf("(") + 1, csvLine.indexOf(")"));
                                row[2] = csvLine.substring(csvLine.indexOf(")") + 1);
                            }
                            resultList.add(row);
                    }
                    i++;
                }
            }
            catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: "+ex);
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    throw new RuntimeException("Error while closing input stream: "+e);
                }
            }
            return resultList;
    }
}
