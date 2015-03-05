import java.io.*;

public class MyReader{
    String file;
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
        }
        BufferedReader br = new BufferedReader(fr);
        int length = (int)thisFile.length();
        char cbuf[] = new char[length];
        br.read(cbuf,0,length);
        return new String(cbuf);
    }   
    public String[][] getPaths(String content){
        String lines[] = content.split("\r\n");
        int len = lines.length;
        String allCity[][] = new String[len][lines.length];
        for(int i=0; i<len; i++)
                allCity[i] = lines[i].split(",");
        return allCity;
    }
}