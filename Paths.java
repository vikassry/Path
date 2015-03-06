import java.util.*;
import java.io.*;

class PathManager{
	Map<String,List<List<String>>> pathMap = new HashMap<String, List<List<String>>>();
	Map<String,String> cityCountryList = new HashMap<String,String>();
	public PathManager(String[][] routes,  Map<String,String> countries){
		this.cityCountryList = countries;
		for (String[] route : routes) {
			this.setPath(route[0], route[1]);
			this.setPath(route[1], route[0]);
		}
	}

	public void setPath(String src, String dst){
		if(pathMap.containsKey(src)){
			pathMap.get(src).get(0).add(dst);
		}
		else{
			List<List<String>> list = new ArrayList<List<String>>();
			List<String> l = new ArrayList<String>();
			l.add(dst); list.add(l);
			pathMap.put(src,list);
		}
	}

	public int areCitiesValid(String src, String dst){
		if(pathMap.get(src)==null) return 2;
		if(pathMap.get(dst)==null) return 3;
		return 1;
	}

	public boolean isDirectPathBetween(String src, String dst){
		return (this.areCitiesValid(src,dst)==1 && pathMap.get(src).get(0).indexOf(dst)> -1);
	}

	public int checkForAnyPath(String src, String dst, List<String> pathFinder) {
		pathFinder.add(src);
		if(src.equals(dst)) return 1;
		if(this.isDirectPathBetween(src, dst)) return 1;
		for (String city: pathMap.get(src).get(0)) {
			if(!pathFinder.contains(city) && checkForAnyPath(city, dst, pathFinder)==1)
				return 1;
		}
		return 0;
	}

	public int isPath(String src, String dst){
		List<String> pathFinder = new ArrayList<String>();
		return (this.areCitiesValid(src, dst)==1) ? this.checkForAnyPath(src,dst,pathFinder) 
		: this.areCitiesValid(src, dst);
	}

	public String getPath(String src, String dst) {
		String route = "";
		List<String> pathList = new ArrayList<String>();
		if(this.checkForAnyPath(src, dst, pathList)==0) return "false";
		for (String city: pathList)
			route = route + city +"["+ cityCountryList.get(city) +"]-> ";
		return route += dst +"["+ cityCountryList.get(dst) +"]";
	}

	public String givePathStatus(String src, String dst){
		String status = new String();
		switch(isPath(src, dst)){
			case 0 : status = "false"; break;
			case 1 : status = this.getPath(src,dst); break;
			case 2 : status = "No city named "+src+" in database"; break;
			case 3 : status = "No city named "+dst+" in database"; break;	
		}
		return status;
	}
}

public class Paths {
	public static void main(String[] args)throws IOException {
		if(args.length>=1 && !args[0].equals("-f")){
			System.out.println("Invalid option "+args[0]+". Try `-f'"); return;
		}
		if(args.length == 4){
			MyReader r = new MyReader(args[1]);
			MyReader r2 = new MyReader("cities.txt");
			Map<String,String> country = r2.getCitiesWithCountries(r2.getContent());
			String pathContent = r.getContent();
			for (String key : country.keySet()) {
				System.out.println(key+"-> "+country.get(key));
			}

			if(pathContent.substring(0,6).equals("Error:")){
				System.out.println("No database named `"+args[1]+"' found."); return;
			}	
			String[] routes[] = r.getPaths(pathContent);
			PathManager p = new PathManager(routes,country);
			System.out.println(p.givePathStatus(args[2], args[3]));
		}
		else {
			String error = " Not enough information.\r\n Please enter the source and destination "+
							"and \r\n pathFile source with proper option.";
			System.out.println(error);
		}
	}
}