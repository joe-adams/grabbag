package joe.grabbag;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;

public class Common {
	
	/**
	* @param participants list of individuals participating in the draw.
	* @param mapGenerator function
	* @return the list of individuals paired with the participants where participants[0] is matched
	* with assignments[0], participants[1] is matched with assignments[1], and so on.
	*/
	public static String[] generateAssignments(final String[] participants, Function<Integer,Map<Integer,Integer>> mapGenerator) {
		checkInput(participants);
		Map<Integer,Integer> mapping=mapGenerator.apply(participants.length);
		return mapIntsToParticipants(mapping,participants);
	}
	
	static void checkInput(final String[] participants){
		if (participants.length<2){
			throw new RuntimeException("No data");
		}
		final Set<String> participantCounter=ImmutableSet.copyOf(participants);
		if (participantCounter.size()<participants.length){
			throw new RuntimeException("Not all elements unique");
		}		
	}
	
	static String[] mapIntsToParticipants(Map<Integer,Integer> mapping,String[] initialInput){
		final String[] result=new String[initialInput.length];
		for (Map.Entry<Integer, Integer> entry:mapping.entrySet()){
			result[entry.getKey()]=initialInput[entry.getValue()];
		}
		return result;
	}

}
