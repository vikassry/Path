import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

public class PathsTest{
	
	@Test
	public void setPath_adds_all_direct_paths_to_map(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Beijing","Seoul"}};
		PathManager p = new PathManager(routes, countryMap);
		String [] route = {"Bangalore","Singapore"};
		p.setPath(route[0],route[1]);

		assertEquals(p.pathMap.get("Bangalore").get(0).get(0), "Singapore");
	}

	@Test
	public void addPaths_adds_all_paths_to_pathMap_from_with_both_side_flights(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}, 
								{"Singapore","Dubai"},{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);

		Map<String,List<List<String>>> pathMap = p.pathMap;
		assertEquals(pathMap.get("Singapore").get(0).get(0),"Bangalore");
		assertEquals(pathMap.get("Singapore").get(0).get(1),"Seoul");
		assertEquals(pathMap.get("Singapore").get(0).get(2),"Dubai");
		assertEquals(pathMap.get("Seoul").get(0).get(0),"Singapore");
		assertEquals(pathMap.get("Seoul").get(0).get(1),"Beijing");
	}

	@Test
	public void areCitiesValid_returns_1_when_both_cities_exists_in_database(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}, {"Singapore","Dubai"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.areCitiesValid("Singapore","Bangalore"),1);
	}

	@Test
	public void areCitiesValid_returns_2_when_source_city_doesnt_exist_in_database(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.areCitiesValid("Chennai","Singapore"),2);
	}

	@Test
	public void areCitiesValid_returns_3_when_destination_city_doesnt_exist_in_database(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.areCitiesValid("Bangalore","Stockholm"),3);
	}

	@Test
	public void isDirectPathBetween_returns_true_when_direct_path_between_BLR_SNGPR_(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertTrue(p.isDirectPathBetween("Bangalore","Singapore"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_BLR_TKY(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},
								{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);
		assertFalse(p.isDirectPathBetween("Bangalore","Tokyo"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_any_invalid_city(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertFalse(p.isDirectPathBetween("Bangalore","Chennai"));
	}

	@Test
	public void checkForAnyPath_returns_1_for_direct_path_between_2_cities(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes,countryMap);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Singapore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_BLR_and_TKY(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"}, {"Singapore","Dubai"},
							{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Tokyo",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_3_for_path_between_BLR_and_STKHM(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"}, {"Singapore","Dubai"}};
		PathManager p = new PathManager(routes,countryMap);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Stockholm",pathFinder),0);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_TKY_and_BLR(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Tokyo","Bangalore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_0_for_path_between_BLR_and_BJNG(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Beijing",pathFinder),0);
		assertEquals(p.checkForAnyPath("Beijing","Bangalore",pathFinder),0);
	}

	@Test
	public void isPath_returns_1_for_any_path_between_2_cities(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.isPath("Bangalore","Tokyo"),1);
	}

	@Test
	public void isPath_returns_0_when_no_path_between_2_cities(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.isPath("Bangalore","Beijing"),0);
	}

	@Test
	public void isPath_returns_2_when_source_city_is_not_available_in_database(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.isPath("Chennai","Beijing"),2);
	}

	@Test
	public void isPath_returns_3_when_destination_city_is_not_available_in_database(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes,countryMap);
		
		assertEquals(p.isPath("Beijing","Delhi"),3);
	}

	@Test
	public void givePathMapStatus_gives_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}};
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.givePathStatus("Chennai","Singapore"),"No city named Chennai in database");
	}

	@Test
	public void givePathStatus_gives_No_city_named_Stockholm_in_database_when_no_flight_to_Stockholm_available(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}};
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.givePathStatus("Bangalore","Stockholm"),"No city named Stockholm in database");
	}
	@Test
	public void givePathStatus_returns_true_for_direct_flight_between_Singapore_Bangalore(){
		Map<String,String> countryMap = new HashMap<String,String>();
		countryMap.put("Bangalore","India");
		countryMap.put("Tokyo","Japan");
		countryMap.put("Singapore","Singapore");
		countryMap.put("Seoul","South Korea");
		countryMap.put("Dubai","UAE");
		countryMap.put("Beijing","China");
		
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}, 
							{"Singapore","Dubai"},{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.givePathStatus("Bangalore","Singapore"), "Bangalore[India]-> Singapore[Singapore]");
		assertEquals(p.givePathStatus("Tokyo","Bangalore"), "Tokyo[Japan]-> Beijing[China]-> Seoul[South Korea]-> Singapore[Singapore]-> Bangalore[India]");
		assertEquals(p.givePathStatus("Tokyo","Seoul"), "Tokyo[Japan]-> Beijing[China]-> Seoul[South Korea]");
	}

	@Test
	public void givePathStatus_returns_path_when_both_source_and_destination_are_same(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"}};
		countryMap.put("Bangalore","India");
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.givePathStatus("Bangalore","Bangalore"), "Bangalore[India]-> Bangalore[India]");
	}

	@Test
	public void givePathStatus_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
		Map<String,String> countryMap = new HashMap<String,String>();
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Seoul"}};
		PathManager p = new PathManager(routes,countryMap);

		assertEquals(p.givePathStatus("Singapore","Tokyo"), "false");
	}

	@Test
	public void getContent_of_MyReader_reads_the_given_file_and_returns_the_text_from_the_file() throws IOException {
		Map<String,String> countryMap = new HashMap<String,String>();
		String content = "Bangalore,Singapore"+"\r\n"+"Singapore,Seoul"+"\r\n"+
						"Singapore,Dubai"+"\r\n"+"Seoul,Beijing"+"\r\n"+"Beijing,Tokyo";
		MyReader mr = new MyReader("Paths.txt");
		assertEquals(mr.getContent(), content);
	}

	@Test
	public void getPaths_returns_array_of_string_arrays_with_source_and_their_corresponding_destination_city() throws IOException {
		Map<String,String> countryMap = new HashMap<String,String>();
        String[][] routes = {{"Bangalore", "Singapore"}, {"Singapore", "Seoul"},
                {"Singapore", "Dubai"}, {"Seoul", "Beijing"}, {"Beijing", "Tokyo"}};
        MyReader mr = new MyReader("Paths.txt");
        String content = mr.getContent();
        String[][] expected = mr.getPaths(content);
        int i;
        for (i = 0; i < expected.length; i++) {
            assertEquals(expected[i][0], routes[i][0]);
            assertEquals(expected[i][1], routes[i][1]);
        }
    }

}