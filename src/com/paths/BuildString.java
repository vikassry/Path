package com.paths;

import java.util.*;

public class BuildString{
    public static String joinCountryNames(List<List<String>> paths, Map<String,String> cityCountryMap, Map<String,List<List<String>>> pathMap){
        String finalRout=""; int cost=0;
        for (int i=0; i<paths.size(); i++) {
            List<String> list = paths.get(i);
            cost = calculateCostOfJourney(list, pathMap);
            for (int j=0; j<list.size(); j++) {
                String city = list.get(j);
                finalRout += (j==list.size()-1) ? (city + "["+ cityCountryMap.get(city) +"]")
                        : (city + "["+ cityCountryMap.get(city)+"]->") ;
            }
            finalRout += (i==paths.size()-1) ? ("Total cost: "+ cost) : ("Total cost: "+ cost +"\r\n");
        }
        return sortPathsByCost(finalRout);
    }

    public static int calculateCostOfJourney (List<String> path, Map<String,List<List<String>>> db){
        int cost = 0;
        for (int i=0; i < path.size()-1; i++) {
            String city = path.get(i);
            cost += Integer.parseInt(db.get(city).get(1).get(db.get(city).get(0).indexOf(path.get(i+1))));
        }
        return cost;
    }

    public static String sortPathsByCost (String strPath){
        String[] paths = strPath.split("\r\n");
        String temp = "", sortedPath="";
        for(int i=0; i<paths.length; i++){
            for(int j=1; j<paths.length-i; j++){
                if(Integer.parseInt(paths[j-1].split(": ")[1]) > Integer.parseInt(paths[j].split(": ")[1])){
                    temp = paths[j-1];
                    paths[j-1] = paths[j];
                    paths[j] = temp;
                }
            }
        }
        for (int i=0; i<paths.length; i++){
            sortedPath += (i == paths.length-1) ? paths[i] : paths[i]+"\r\n";
        }
        sortedPath = sortedPath.replace("Total","\r\nTotal");
        return sortedPath;
    }
}

