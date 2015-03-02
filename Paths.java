import java.util.*;


class Path{
	public static int isPath(Map<String,String> path, String src, String dst){
		return (path.containsKey(src)==false) ? 2 : 
		(path.containsValue(dst)) ?  path.get(src).equals(dst) ? 1 : 0 : 3 ;
	}
	public static String givePathStatus(Map<String,String> path, String src, String dst){
		String status="";
		switch(Path.isPath(path,src,dst)){
			case 0 : status = "false"; break;
			case 1 : status = "true"; break;
			case 2 : status = "No city named "+src+"in database"; break;
			case 3 : status = "No city named "+dst+"in database"; break;	
		}
		return status;
	}
}

public class Paths {
	public static void main(String[] args) {
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		path.put("Singapore","Seoul");
		path.put("Singapore","Dubai");
		path.put("Seoul","Beijing");
		path.put("Beijing","Tokyo");
		System.out.println(Path.isPath(path, args[0],args[1]));
		System.out.println(Path.givePathStatus(path, args[0],args[1]));
	}
}