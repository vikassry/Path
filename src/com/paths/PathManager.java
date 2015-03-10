package com.paths;

import java.util.*;
import java.io.*;

public class PathManager{
    private Map<String,List<List<String>>> pathMap = new HashMap<String, List<List<String>>>();
    private Map<String,String> cityCountryList = new HashMap<String,String>();

    public PathManager(String[][] routes,  Map<String,String> countries){
        this.cityCountryList = countries;
        for (String[] route : routes) {
            this.setPath(route[0], route[1], route[2]);
            this.setPath(route[1], route[0], route[2]);
        }
    }

    public void setPath(String src, String dst, String fare){
        if(!pathMap.containsKey(src)){
            List<List<String>> list = new ArrayList<List<String>>();
            List<String> d = new ArrayList<String>();
            List<String> cost = new ArrayList<String>();
            d.add(dst); cost.add(fare); list.add(d); list.add(cost);
            pathMap.put(src,list);  return;
        }
        pathMap.get(src).get(0).add(dst);
        pathMap.get(src).get(1).add(fare);
    }

    public int areCitiesValid(String src, String dst){
        if(pathMap.get(src)==null) return 2;
        if(pathMap.get(dst)==null) return 3;
        return 1;
    }

    public boolean isDirectPathBetween(String src, String dst){
        return (this.areCitiesValid(src,dst)==1 && pathMap.get(src).get(0).indexOf(dst)> -1);
    }

    public int isAnyPathAvailable(String src, String dst, List<String> pathFinder) {
        pathFinder.add(src);
        if(src.equals(dst)) return 0;
        if(this.isDirectPathBetween(src, dst)) return 1;
        for (String city: pathMap.get(src).get(0)) {
            if(!pathFinder.contains(city) && isAnyPathAvailable(city, dst, pathFinder)==1)
                return 1;
        }
        return 0;
    }

    public int isPath(String src, String dst){
        List<String> pathFinder = new ArrayList<String>();
        return (this.areCitiesValid(src, dst)==1) ? this.isAnyPathAvailable(src,dst,pathFinder)
                : this.areCitiesValid(src, dst);
    }

    public void findPath(String src, String dst, List<List<String>> paths, ArrayList<String> path) {
        path.add(src);
        if (src.equals(dst)) {
            paths.add(new ArrayList<String>(path));
            path.remove(src);
            return;
        }
        List<String> destinations  = pathMap.get(src).get(0);
        for (String eachDest : destinations) {
            if (!path.contains(eachDest)) {
                findPath(eachDest, dst, paths, path);
            }
        }
        path.remove(src);
    }

    public String getPath(String src, String dst){
        List<List<String>> allPaths = new ArrayList<List<String>>();
        this.findPath(src, dst, allPaths, new ArrayList<String>());
        String route = BuildString.joinCountryNames(allPaths, cityCountryList, pathMap);
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
