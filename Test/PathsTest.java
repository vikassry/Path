
import com.paths.BuildString;
import com.paths.MyReader;
import com.paths.PathManager;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.io.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PathsTest{
    Map<String,List<List<String>>> db = new HashMap<String,List<List<String>>>();
    List<String> path = new ArrayList<String>();
    List<String> cost2 = new ArrayList<String>();
    List<String> cost1 = new ArrayList<String>();
    List<List<String>> route1 = new ArrayList<List<String>>();
    List<List<String>> route2 = new ArrayList<List<String>>();
    List<String> dst1 = new ArrayList<String>();
    List<String> dst2 = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        path.add("Bangalore"); path.add("Seoul"); path.add("Singapore");
        cost1.add("9000"); cost2.add("10000");
        dst1.add("Seoul"); dst2.add("Singapore");
        route1.add(dst1); route1.add(cost1);
        route2.add(dst2); route2.add(cost2);
        db.put("Bangalore",route1); db.put("Seoul",route2);
    }

    @Test
    public void areCitiesValid_returns_1_when_both_cities_exists_in_database(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Singapore","Seoul","9000"}, {"Singapore","Dubai","12000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.areCitiesValid("Singapore","Bangalore"),1);
    }

    @Test
    public void areCitiesValid_returns_2_when_source_city_doesnt_exist_in_database(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.areCitiesValid("Chennai","Singapore"),2);
    }

    @Test
    public void areCitiesValid_returns_3_when_destination_city_doesnt_exist_in_database(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.areCitiesValid("Bangalore","Stockholm"),3);
    }

    @Test
    public void isDirectPathBetween_returns_true_when_direct_path_between_BLR_SNGPR_(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertTrue(p.isDirectPathBetween("Bangalore","Singapore"));
    }

    @Test
    public void isDirectPathBetween_returns_false_when_direct_path_between_BLR_TKY(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},
                {"Seoul","Beijing","3000"}, {"Beijing","Tokyo","5000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertFalse(p.isDirectPathBetween("Bangalore","Tokyo"));
    }

    @Test
    public void isDirectPathBetween_returns_false_when_direct_path_between_any_invalid_city(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertFalse(p.isDirectPathBetween("Bangalore","Chennai"));
    }

    @Test
    public void isAnyPathAvailable_returns_1_for_direct_path_between_2_cities(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        PathManager p = new PathManager(routes,countryMap);
        List<String> pathFinder = new ArrayList<String>();

        assertEquals(p.isAnyPathAvailable("Bangalore","Singapore",pathFinder),1);
    }

    @Test
    public void isAnyPathAvailable_returns_1_for_indirect_path_between_BLR_and_TKY(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},
                {"Singapore","Seoul","10000"}, {"Singapore","Dubai","12000"},
                {"Seoul","Beijing","3000"}, {"Beijing","Tokyo","5000"}};
        PathManager p = new PathManager(routes,countryMap);
        List<String> pathFinder = new ArrayList<String>();

        assertEquals(p.isAnyPathAvailable("Bangalore","Tokyo",pathFinder),1);
    }

    @Test
    public void isAnyPathAvailable_returns_3_for_path_between_BLR_and_STKHM(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},
                {"Singapore","Seoul","10000"}, {"Singapore","Dubai","12000"}};
        PathManager p = new PathManager(routes,countryMap);
        List<String> pathFinder = new ArrayList<String>();

        assertEquals(p.isAnyPathAvailable("Bangalore","Stockholm",pathFinder),0);
    }

    @Test
    public void isAnyPathAvailable_returns_1_for_indirect_path_between_TKY_and_BLR(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},
                {"Singapore","Seoul","10000"}, {"Singapore","Dubai","12000"},
                {"Seoul","Beijing","3000"}, {"Beijing","Tokyo","5000"}};
        PathManager p = new PathManager(routes,countryMap);
        List<String> pathFinder = new ArrayList<String>();

        assertEquals(p.isAnyPathAvailable("Tokyo","Bangalore",pathFinder),1);
    }

    @Test
    public void isAnyPathAvailable_returns_0_for_path_between_BLR_and_BJNG(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Beijing","Tokyo","5000"}};
        PathManager p = new PathManager(routes,countryMap);
        List<String> pathFinder = new ArrayList<String>();

        assertEquals(p.isAnyPathAvailable("Bangalore","Beijing",pathFinder),0);
        assertEquals(p.isAnyPathAvailable("Beijing","Bangalore",pathFinder),0);
    }

    @Test
    public void isPath_returns_1_for_any_path_between_2_cities(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Singapore","Tokyo","8000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.isPath("Bangalore","Tokyo"),1);
    }

    @Test
    public void isPath_returns_0_when_no_path_between_2_cities(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Tokyo","Beijing","5000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.isPath("Bangalore","Beijing"),0);
    }

    @Test
    public void isPath_returns_2_when_source_city_is_not_available_in_database(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Tokyo","Beijing","5000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.isPath("Chennai","Beijing"),2);
    }

    @Test
    public void isPath_returns_3_when_destination_city_is_not_available_in_database(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Tokyo","Beijing","5000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.isPath("Beijing","Delhi"),3);
    }

    @Test
    public void givePathMapStatus_gives_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Singapore","Seoul","10000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.givePathResult("Chennai","Singapore"),"No city named Chennai in database");
    }

    @Test
    public void givePathResult_gives_No_city_named_Stockholm_in_database_when_no_flight_to_Stockholm_available(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Singapore","Seoul","10000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.givePathResult("Bangalore","Stockholm"),"No city named Stockholm in database");
    }
    @Test
    public void givePathResult_returns_true_for_direct_flight_between_Singapore_Bangalore(){
        Map<String,String> countryMap = new HashMap<String,String>();
        countryMap.put("Bangalore","India");
        countryMap.put("Tokyo","Japan");
        countryMap.put("Singapore","Singapore");
        countryMap.put("Seoul","South Korea");
        countryMap.put("Dubai","UAE");
        countryMap.put("Beijing","China");

        String [][] routes = {{"Bangalore","Singapore","7000"},{"Singapore","Seoul","10000"},
                {"Singapore","Dubai","12000"},{"Seoul","Beijing","3000"}, {"Beijing","Tokyo","5000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.givePathResult("Bangalore","Singapore"), "Bangalore[India]->Singapore[Singapore]\r\nTotal cost: 7000");
        assertEquals(p.givePathResult("Tokyo","Bangalore"), "Tokyo[Japan]->Beijing[China]->Seoul[South Korea]->Singapore[Singapore]->Bangalore[India]\r\nTotal cost: 25000");
        assertEquals(p.givePathResult("Tokyo","Seoul"), "Tokyo[Japan]->Beijing[China]->Seoul[South Korea]\r\nTotal cost: 8000");
    }

    @Test
    public void givePathResult_returns_path_when_both_source_and_destination_are_same(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"}};
        countryMap.put("Bangalore","India");
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.givePathResult("Bangalore","Bangalore"), "false");
    }

    @Test
    public void givePathResult_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
        Map<String,String> countryMap = new HashMap<String,String>();
        String [][] routes = {{"Bangalore","Singapore","7000"},{"Tokyo","Seoul","8000"}};
        PathManager p = new PathManager(routes,countryMap);

        assertEquals(p.givePathResult("Singapore","Tokyo"), "false");
    }

    @Test
    public void getContent_of_MyReader_reads_the_given_file_and_returns_the_text_from_the_file() throws IOException {
        Map<String,String> countryMap = new HashMap<String,String>();
        String content = "Bangalore,Singapore,7000"+"\r\n"+"Singapore,Seoul,10000"+"\r\n"+"Singapore,Dubai,12000"+
                "\r\n"+"Seoul,Beijing,3000"+"\r\n"+"Beijing,Tokyo,5000"+"\r\nDubai,Seoul,9000";
        MyReader mr = new MyReader("./data/Paths.txt");

        assertEquals(mr.getContent(), content);
    }

    @Test
    public void getDirectPaths_returns_array_of_string_arrays_with_source_and_their_corresponding_destination_city() throws IOException {
        Map<String,String> countryMap = new HashMap<String,String>();
        String[][] routes = {{"Bangalore","Singapore","7000"}, {"Singapore", "Seoul","10000"},{"Singapore", "Dubai","12000"},
                {"Seoul", "Beijing","3000"}, {"Beijing", "Tokyo","5000"},{"Dubai","Seoul","9000"}};
        MyReader mr = new MyReader("./data/Paths.txt");
        String content = mr.getContent();
        String[][] expected = mr.getDirectPaths(content);

        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i][0], routes[i][0]);
            assertEquals(expected[i][1], routes[i][1]);
        }
    }

    @Test
    public void getCitiesWithCountries_returns_a_map_with_cities_with_their_correspondind_country_name()throws IOException{
        Map<String,String> countryMap = new HashMap<String,String>();
        countryMap.put("Bangalore","India");
        countryMap.put("Tokyo","Japan");
        countryMap.put("Singapore","Singapore");
        countryMap.put("Seoul","South Korea");
        countryMap.put("Dubai","UAE");
        countryMap.put("Beijing","China");

        MyReader r = new MyReader("./data/cities.txt");
        String content = r.getContent();
        Map<String,String> expected = r.getCitiesWithCountries(content);

        assertEquals(countryMap, expected);
    }

    @Test
    public void calculateCostOfJourney_returns_the_total_cost_of_the_given_path(){
        assertEquals(BuildString.calculateCostOfJourney(path, db), 19000);
    }

    @Test
    public void joinCountryNames_gives_paths_along_with_countries(){
        Map<String,String> countryMap = new HashMap<String,String>();
        countryMap.put("Bangalore","India");
        countryMap.put("Singapore","Singapore");
        countryMap.put("Seoul","South Korea");
        List<List<String>> allPaths = new ArrayList<List<String>>();
        allPaths.add(path);
        String expected = "Bangalore[India]->Seoul[South Korea]->Singapore[Singapore]\r\nTotal cost: 19000";

        assertEquals(expected, BuildString.joinCountryNames(allPaths, countryMap, db));
    }
}

