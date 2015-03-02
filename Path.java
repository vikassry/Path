import java.util.*;

public class Path {
	public static boolean isPath(Map<String,String> path, String src, String dst){
		return ((path.get(dst)!=null && path.get(dst).equals(src)) || (path.get(src)!=null && path.get(src).equals(dst)));
	}

	public static void main(String[] args) {
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		System.out.println(isPath(path, args[0], args[1]));
	}
}