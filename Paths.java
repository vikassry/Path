import java.util.*;
import java.io.*;

class PathManager{
	Map<String,List<String>> pathMap = new HashMap<String, List<String>>();

	public PathManager(String[][] routes){
		for (String[] route : routes) {
			this.setPath(route[0], route[1]);
			this.setPath(route[1], route[0]);
		}
	}

	public void setPath(String src, String dst){
		if(pathMap.containsKey(src)){
			pathMap.get(src).add(dst);
		}
		else{
			List<String> list = new ArrayList<String>();
			list.add(dst);
			pathMap.put(src, list);
		}
	}

	public int areCitiesValid(String src, String dst){
		if(pathMap.get(src)==null) return 2;
		if(pathMap.get(dst)==null) return 3;
		return 1;
	}

	public boolean isDirectPathBetween(String src, String dst){
		return (this.areCitiesValid(src,dst)==1 && pathMap.get(src).indexOf(dst)> -1);
	}

	public int checkForAnyPath(String src, String dst, List<String> pathFinder) {
		pathFinder.add(src);
		if(this.isDirectPathBetween(src, dst)) return 1;
		for (String city: pathMap.get(src)) {
			if(!pathFinder.contains(city)) {
				if(checkForAnyPath(city, dst, pathFinder)==1)
					return 1;
			}
		}
		return 0;
	}

	public int isPath(String src, String dst){
		List<String> pathFinder = new ArrayList<String>();
		return (this.areCitiesValid(src, dst)==1) ? this.checkForAnyPath(src,dst,pathFinder) 
		: this.areCitiesValid(src, dst);
	}

	public String getPath(String src, String dst, String route) {
		List<String> pathList = new ArrayList<String>();
		if(this.checkForAnyPath(src, dst, pathList)==0) return "false";
		for (String city: pathList) {
			route = route + city + "-> ";
		}
		route += dst;
		return route;
	}

	public String givePathStatus(String src, String dst){
		String status="", route="";
		switch(isPath(src, dst)){
			case 0 : status = "false"; break;
			case 1 : status = this.getPath(src,dst,route); break;
			case 2 : status = "No city named "+src+" in database"; break;
			case 3 : status = "No city named "+dst+" in database"; break;	
		}
		return status;
	}
}

public class Paths {
	public static void main(String[] args)throws IOException {
		if(!args[0].equals("-f")){
			System.out.println("Invalid option "+args[0]+". Try `-f'");
			return;
		}
		MyReader r = new MyReader(args[1]);
		String pathContent = r.readFile();
		String[] routes[] = r.getPaths(pathContent);
		if(args.length == 4){
			PathManager p = new PathManager(routes);
			System.out.println(p.givePathStatus(args[2], args[3]));
		}
		else 
			System.out.println("The source and destination are mandatory to mention");
	}
}