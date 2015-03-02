import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;


public class PathTest{

	@Test
	public void isPath_returns_true_for_direct_flight_between_Singapore_Bangalore(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		assertEquals(Path.isPath(path,"Bangalore","Singapore"), true);
	}

	@Test
	public void isPath_returns_true_for_direct_flight_between_Bangalore_Singapore(){
		Map<String,String> path = new HashMap<String,String>();
		path.put("Bangalore","Singapore");
		assertEquals(Path.isPath(path,"Singapore","Bangalore"), true);
	}
}