package joe.grabbag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class SecondAlgorthimTest {
	
	@Test
	 public void firstTest(){
		for (int i=0;i<20;i++){
			String[] input={"Joe","Macaire"};
			String[] output=SecondAlgorthim.generateAssignments(input);
			assertEquals(output.length,2);
			assertEquals(output[0],"Macaire");
			assertEquals(output[1],"Joe");
		}
		
	}
	
	
	@Test
	 public void secondTest(){
		for (int i=0;i<20;i++){
			String[] input={"Joe","Macaire","Isabelle"};
			String[] output=SecondAlgorthim.generateAssignments(input);
			assertEquals(output.length,3);
			assertNotEquals(output[0],"Joe");
			assertNotEquals(output[1],"Macaire");
			assertNotEquals(output[2],"Isabelle");
			Set<String> set=ImmutableSet.copyOf(output);
			assertTrue(set.contains("Joe"));
			assertTrue(set.contains("Macaire"));
			assertTrue(set.contains("Isabelle"));
		}

		
	}
	
	@Test
	public void testgiftMap(){
		for (int i=0;i<20;i++){
			int size=100;
			Map<Integer,Integer> map=SecondAlgorthim.giftMap(size);
			Set<Integer> keys=map.keySet();
			Set<Integer> values=ImmutableSet.copyOf(map.values());
			assertEquals(map.size(),size);
			assertEquals(keys.size(),size);
			assertEquals(values.size(),size);
			for(int j=0;j<size;j++){
				assertTrue(keys.contains(j));
				assertTrue(values.contains(j));
			}
			for (Map.Entry<Integer, Integer> entry:map.entrySet()){
				assertNotEquals(entry.getKey(),entry.getValue());
			}
		}
	}

}
