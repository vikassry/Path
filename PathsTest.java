import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class PathsTest{

	@Test
	public void setPath_adds_all_direct_paths_to_map(){
		String [][] routes = {{"Beijing","Seoul"}};
		PathManager p = new PathManager(routes);
		String [] route = {"Bangalore","Singapore"};
		p.setPath(route[0],route[1]);

		assertEquals(p.pathMap.get("Bangalore").get(0), "Singapore");
	}

	@Test
	public void addPaths_adds_all_paths_to_pathMap_from_with_both_side_flights(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}, 
								{"Singapore","Dubai"},{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes);

		Map<String,List<String>> pathMap = p.pathMap;
		assertEquals(pathMap.get("Singapore").get(0),"Bangalore");
		assertEquals(pathMap.get("Singapore").get(1),"Seoul");
		assertEquals(pathMap.get("Singapore").get(2),"Dubai");
		assertEquals(pathMap.get("Seoul").get(0),"Singapore");
		assertEquals(pathMap.get("Seoul").get(1),"Beijing");
	}

	@Test
	public void areCitiesValid_returns_1_when_both_cities_exists_in_database(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}, {"Singapore","Dubai"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.areCitiesValid("Singapore","Bangalore"),1);
	}

	@Test
	public void areCitiesValid_returns_2_when_source_city_doesnt_exist_in_database(){
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.areCitiesValid("Chennai","Singapore"),2);
	}

	@Test
	public void areCitiesValid_returns_3_when_destination_city_doesnt_exist_in_database(){
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.areCitiesValid("Bangalore","Stockholm"),3);
	}

	@Test
	public void isDirectPathBetween_returns_true_when_direct_path_between_BLR_SNGPR_(){
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes);
		
		assertTrue(p.isDirectPathBetween("Bangalore","Singapore"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_BLR_TKY(){
		String [][] routes = {{"Bangalore","Singapore"},
								{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes);
		assertFalse(p.isDirectPathBetween("Bangalore","Tokyo"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_any_invalid_city(){
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes);
		
		assertFalse(p.isDirectPathBetween("Bangalore","Chennai"));
	}

	@Test
	public void checkForAnyPath_returns_1_for_direct_path_between_2_cities(){
		String [][] routes = {{"Bangalore","Singapore"}};
		PathManager p = new PathManager(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Singapore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_BLR_and_TKY(){
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"}, {"Singapore","Dubai"},
							{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Tokyo",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_3_for_path_between_BLR_and_STKHM(){
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"}, {"Singapore","Dubai"}};
		PathManager p = new PathManager(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Stockholm",pathFinder),0);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_TKY_and_BLR(){
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes);
		for (String city : p.pathMap.keySet()) {
			System.out.println(city+"->  "+ p.pathMap.get(city));
		}
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Tokyo","Bangalore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_0_for_path_between_BLR_and_BJNG(){
		String [][] routes = {{"Bangalore","Singapore"},{"Beijing","Tokyo"}};
		PathManager p = new PathManager(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Beijing",pathFinder),0);
		assertEquals(p.checkForAnyPath("Beijing","Bangalore",pathFinder),0);
	}

	@Test
	public void isPath_returns_1_for_any_path_between_2_cities(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Tokyo"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.isPath("Bangalore","Tokyo"),1);
	}

	@Test
	public void isPath_returns_0_when_no_path_between_2_cities(){
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.isPath("Bangalore","Beijing"),0);
	}

	@Test
	public void isPath_returns_2_when_source_city_is_not_available_in_database(){
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.isPath("Chennai","Beijing"),2);
	}

	@Test
	public void isPath_returns_3_when_destination_city_is_not_available_in_database(){
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Beijing"}};
		PathManager p = new PathManager(routes);
		
		assertEquals(p.isPath("Beijing","Delhi"),3);
	}

	@Test
	public void givePathMapStatus_gives_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}};
		PathManager p = new PathManager(routes);

		assertEquals(p.givePathStatus("Chennai","Singapore"),"No city named Chennai in database");
	}

	@Test
	public void givePathStatus_gives_No_city_named_Stockholm_in_database_when_no_flight_to_Stockholm_available(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}};
		PathManager p = new PathManager(routes);

		assertEquals(p.givePathStatus("Bangalore","Stockholm"),"No city named Stockholm in database");
	}
	@Test
	public void givePathStatus_returns_true_for_direct_flight_between_Singapore_Bangalore(){
		String [][] routes = {{"Bangalore","Singapore"},{"Singapore","Seoul"}};
		PathManager p = new PathManager(routes);

		assertEquals(p.givePathStatus("Bangalore","Singapore"), "Bangalore-> Singapore");
		assertEquals(p.givePathStatus("Seoul","Bangalore"), "Seoul-> Singapore-> Bangalore");
	}

	@Test
	public void givePathStatus_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
		String [][] routes = {{"Bangalore","Singapore"},{"Tokyo","Seoul"}};
		PathManager p = new PathManager(routes);

		assertEquals(p.givePathStatus("Singapore","Tokyo"), "false");
	}

}