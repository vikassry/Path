import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class PathsTest{

	@Test
	public void addCities_adds_all_cities_to_paths_hashMap(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		for(String city: cities) {
			assertEquals(p.path.containsKey(city), true);
			assertEquals(p.path.get(city), new ArrayList<String>());
		}
	}
	
	@Test
	public void setPath_adds_all_direct_paths_to_map(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		String [] routes = {"Bangalore","Singapore"};
		Path p = new Path(cities);
		p.setPath(routes[0],routes[1]);
		p.setPath(routes[1],routes[0]);

		assertEquals(p.path.get("Bangalore").get(0), "Singapore");
		assertEquals(p.path.get("Singapore").get(0), "Bangalore");
	}

	@Test
	public void addPaths_adds_all_paths_to_pathMap_from_with_both_side_flights(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		for (String city : p.path.keySet()) {
			System.out.println("=  src "+city + " destination-> " + p.path.get(city));
		}

		Map<String,List<String>> path = p.path;
		assertEquals(path.get("Bangalore").get(0),"Singapore");
		assertEquals(path.get("Singapore").get(0),"Bangalore");
		assertEquals(path.get("Singapore").get(1),"Seoul");
		assertEquals(path.get("Singapore").get(2),"Dubai");
		assertEquals(path.get("Seoul").get(0),"Singapore");
		assertEquals(path.get("Seoul").get(1),"Beijing");
	}

	@Test
	public void areCitiesValid_returns_true_when_both_cities_exists_in_database(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		Map<String,List<String>> path = p.path;
		assertEquals(p.areCitiesValid("Singapore","Bangalore"),1);
	}

	@Test
	public void areCitiesValid_returns_false_when_one_city_doesnt_exist_in_database(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		Map<String,List<String>> path = p.path;
		assertEquals(p.areCitiesValid("Singapore","Stockholm"),3);
		assertEquals(p.areCitiesValid("Chennai","Singapore"),2);
	}

	@Test
	public void isDirectPath_returns_true_when_direct_path_between_BLR_SGP_(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertTrue(p.isDirectPath("Bangalore","Singapore"));
	}

	@Test
	public void isDirectPath_returns_true_when_direct_path_between_BLR_TKY(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertFalse(p.isDirectPath("Bangalore","Tokyo"));
	}

	
	// @Test
	// public void givePathStatus_gives_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Chennai","Singapore");
	// 	assertEquals(p.isPath(), 2);
	// 	assertEquals(p.givePathStatus(),"No city named Chennai in database");
	// }

	// @Test
	// public void givePathStatus_gives_No_city_named_Stockholm_in_database_when_no_flight_to_Stockholm_available(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Bangalore","Stockholm");
	// 	assertEquals(p.isPath(), 3);
	// 	assertEquals(p.givePathStatus(), "No city named Stockholm in database");

	// }
	// @Test
	// public void isPath_returns_true_for_direct_flight_between_Singapore_Bangalore(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Bangalore","Singapore");
	// 	assertEquals(p.isPath(), 1);
	// 	assertEquals(p.givePathStatus(), "true");
	// }

	// @Test
	// public void isPath_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	path.put("Beijing","Tokyo");
	// 	Path p = new Path(path,"Bangalore","Tokyo");
	// 	assertEquals(p.isPath(), 0);
	// 	assertEquals(p.givePathStatus(), "false");
	// }
}