import java.util.*;

class PathManager{
	Map<String,List<String>> pathMap = new HashMap<String, List<String>>();
	String status;

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

	public void addPaths(String [][] routes){
		for (String[] route : routes) {
			this.setPath(route[0], route[1]);
			this.setPath(route[1], route[0]);
		}
	}

	public int areCitiesValid(String src, String dst){
		if(pathMap.get(src)==null) return 2;
		if(pathMap.get(dst)==null) return 3;
		return 1;
	}

	public boolean isDirectPathBetween(String src, String dst){
		return (this.areCitiesValid(src,dst)==1 && pathMap.get(src).contains(dst));
	}

	public int checkForAnyPath(String src, String dst, List<String> pathFinder) {
		pathFinder.add(src);
		if(this.isDirectPathBetween(src, dst)) return 1;
		for (String city: pathMap.get(src)) {
			if(pathFinder.contains(city)!=true) {
				return checkForAnyPath(city, dst, pathFinder);
			}
		}
		return 0;
	}

	public int isPath(String src, String dst){
		List<String> pathFinder = new ArrayList<String>();
		return (this.areCitiesValid(src, dst)==1) ? this.checkForAnyPath(src,dst,pathFinder) 
		: this.areCitiesValid(src, dst);
	}

	public String givePathStatus(String src, String dst){
		switch(isPath(src, dst)){
			case 0 : status = "false"; break;
			case 1 : status = "true"; break;
			case 2 : status = "No city named "+src+" in database"; break;
			case 3 : status = "No city named "+dst+" in database"; break;	
		}
		return status;
	}
}

public class Paths {
	public static void main(String[] args) {
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"},
							{"Singapore","Dubai"},
							{"Seoul","Beijing"},
							{"Beijing","Tokyo"}};

		if(args.length == 2){
			PathManager p = new PathManager();
			p.addPaths(routes);
			System.out.println(p.givePathStatus(args[0], args[1]));
		}
		else 
			System.out.println("The source and destination are mandatory to mention");
	}
}