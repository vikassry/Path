import java.util.*;
import java.io.*;

public class Paths {
	public static void main(String[] args)throws IOException {

		if(args.length>=1 && !args[0].equals("-f")){
			System.out.println("Invalid option "+args[0]+"  Try `-f'"); return;
		}
		if(args.length>=3 && !args[2].equals("-c")){
			System.out.println("Invalid option "+args[2]+"  Try `-c'"); return;
		}

		if(args.length == 6){
			MyReader r = new MyReader(args[1]);
			MyReader r2 = new MyReader(args[3]);
			String pathContent = r.getContent();
			String cityContent = r2.getContent();

			if(pathContent.substring(0,6).equals("Error:")){
				System.out.println("No database named `"+args[1]+"' found."); return;
			}
			if(cityContent.substring(0,6).equals("Error:")){
				System.out.println("No database named `"+args[3]+"' found."); return;
			}	
			String[] routes[] = r.getDirectPaths(pathContent);
			Map<String,String> country = r2.getCitiesWithCountries(cityContent);
			PathManager p = new PathManager(routes,country);
			System.out.println(p.givePathResult(args[4], args[5]));
		}
		else {
			String error = " Not enough information.\r\n Please enter the source and destination "+
							"and \r\n pathFile source with proper option.";
			System.out.println(error);
		}
	}

}