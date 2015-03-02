import java.util.*;

class Path{
	Map<String,String> path;
	String src, dst, status;
	int isPath;
	public Path(Map<String,String> path, String src, String dst){
		this.path = path;
		this.src = src;
		this.dst = dst;
	}
	public int isPath(){
		isPath = (path.containsKey(src)==false) ? 2 : 
		(path.containsValue(dst)==false) ? 3 : path.get(src).equals(dst) ? 1 : 0;
		return isPath;
	}
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
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		path.put("Singapore","Seoul");
		path.put("Singapore","Dubai");
		path.put("Seoul","Beijing");
		path.put("Beijing","Tokyo");

		if(args.length == 2){
			Path p = new Path(path, args[0], args[1]);
			System.out.println(p.isPath());
			System.out.println(p.givePathStatus());
		}
		else 
			System.out.println("The source and destination are mandatory to mention");
	}
}