package joe.grabbag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class SecondAlgorthimTest {
	
	@Test
	 public void firstTest(){
		String[] input={"Joe","Macaire"};
		String[] output=SecondAlgorthim.generateAssignments(input);
		assertEquals(output.length,2);
		assertEquals(output[0],"Macaire");
		assertEquals(output[1],"Joe");
		
	}
	
	
	@Test
	 public void secondTest(){
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
