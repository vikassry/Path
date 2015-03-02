import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class PathsTest{

	@Test
	public void isPath_returns_true_for_direct_flight_between_Singapore_Bangalore(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		Path p = new Path(path,"Bangalore","Singapore");
		assertEquals(p.isPath(), 1);
	}

	@Test
	public void isPath_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		path.put("Beijing","Tokyo");
		Path p = new Path(path,"Bangalore","Tokyo");
		assertEquals(p.isPath(), 0);
	}

	@Test
	public void isPath_returns_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		Path p = new Path(path,"Chennai","Singapore");
		assertEquals(p.isPath(), 2);
	}

	@Test
	public void isPath_returns_No_city_named_Chennai_in_database_when_no_flight_to_Stockholm_available(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		Path p = new Path(path,"Bangalore","Stockholm");
		assertEquals(p.isPath(), 3);
	}

	@Test
	public void isPath_returns_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		Path p = new Path(path,"Chennai","Singapore");
		assertEquals(p.isPath(), 2);
	}

	@Test
	public void isPath_returns_No_city_named_Chennai_in_database_when_no_flight_to_Stockholm_available(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		Path p = new Path(path,"Bangalore","Stockholm");
		assertEquals(p.isPath(), 3);
	}

}