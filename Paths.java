import java.util.*;
// import java.util.Arraylist;
// import java.util.List;

// class City{
// 	String city;
// 	List<String> directGoesTo = new ArrayList<String>();
	
// 	public City(String city){
// 		this.city = city;
// 	}
// 	// public void addDestination(String dst){
// 	// 	directGoesTo.add(dst);
// 	// }
// }

class Path{
	Map<String,List<String>> pathMap = new HashMap<String, List<String>>();
	String src, dst, status;
	int isPath;
	public Path(String[] cities) {
		for (String city: cities) {
			List<String> list = new ArrayList<String>();
			pathMap.put(city, list);
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
			if(pathFinder.contains(city)==false) {
				return checkForAnyPath(city, dst, pathFinder);
			}
		}
		return 0;
	}
	public int isPath(String src, String dst){
		List<String> pathFinder = new ArrayList<String>();
		isPath = (this.areCitiesValid(src, dst)==1) ? this.checkForAnyPath(src,dst,pathFinder) : this.areCitiesValid(src, dst);
		return isPath;
	}

//-----------------------------------------------------------

	// public Boolean isPath(String from, String to) {
	// 	List<String> pathMap = new ArrayList<String>();
	// 	return this.checkForAnyPath(from, to, pathMap);
	// }
//-------------------------------------------------------------

	public String givePathStatus(){
		switch(isPath){
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

		// if(args.length == 2){
		// 	Path p = new Path();
		// 	p.addPaths(routes);
		// 	System.out.println(p.isPath());
		// 	System.out.println(p.givePathStatus());
		// }
		// else 
		// 	System.out.println("The source and destination are mandatory to mention");
	}
}