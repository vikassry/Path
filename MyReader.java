import java.io.*;
import java.util.*;

public class MyReader{
    private String file;
    public MyReader(String fileName){
        this.file = fileName;
    }
    
    public String getContent() throws IOException {
        File thisFile = new File(this.file);
        FileReader fr = null;
        try {
            fr = new FileReader(thisFile);
        }catch(IOException e) {
            return "Error:No database "+file+" found.";
            // throw new Exception("Error:No database "+file+" found.")
        }
        BufferedReader br = new BufferedReader(fr);
        int length = (int)thisFile.length();
        char cbuf[] = new char[length];
        br.read(cbuf,0,length);
        return new String(cbuf);
    }   

    public String[][] getDirectPaths(String content){
        String lines[] = content.split("\r\n");
        int len = lines.length;
        String allCity[][] = new String[len][lines.length];
        for(int i=0; i<len; i++)
                allCity[i] = lines[i].split(",");
        return allCity;
    }

    public Map<String,String> getCitiesWithCountries(String content){
        Map<String,String> cityWithCountryMap = new HashMap<String,String>();
        String lines[] = content.split("\r\n");
        for (String line : lines) {
            String[] splitted_array = line.split(",");
            cityWithCountryMap.put(splitted_array[0],splitted_array[1]);
        }
        return cityWithCountryMap;
    }

}