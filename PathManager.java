import java.util.*;
import java.io.*;

public class PathManager{
	Map<String,List<List<String>>> pathMap = new HashMap<String, List<List<String>>>();
	private Map<String,String> cityCountryList = new HashMap<String,String>();
	
	public PathManager(String[][] routes,  Map<String,String> countries){
		this.cityCountryList = countries;
		for (String[] route : routes) {
			this.setPath(route[0], route[1]);
			this.setPath(route[1], route[0]);
		}
	}

	public void setPath(String src, String dst){
		if(!pathMap.containsKey(src)){
			List<List<String>> list = new ArrayList<List<String>>();
			List<String> l = new ArrayList<String>();
			l.add(dst); list.add(l);
			pathMap.put(src,list);
		}
		else 
			pathMap.get(src).get(0).add(dst);
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

	public void makePath(String src, String dst, List<List<String>> paths, ArrayList<String> path) {
		path.add(src);
        if (src.equals(dst)) {
            paths.add(new ArrayList<String>(path));
            path.remove(src);
            return;
        }
        List<String> edges  = pathMap.get(src).get(0);
        for (String t : edges) {
            if (!path.contains(t)) {
                makePath(t, dst, paths, path);
            }
        }
        path.remove(src);
	}

	public String getPath(String src, String dst){
		List<List<String>> allPaths = new ArrayList<List<String>>();
		this.makePath(src, dst, allPaths, new ArrayList<String>());
		String route = BuildString.joinCountryNames(allPaths, cityCountryList);
		return route;	
	}

	public String givePathResult(String src, String dst){
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
