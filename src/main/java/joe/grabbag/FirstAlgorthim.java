package joe.grabbag;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

public class FirstAlgorthim {
	

	/**
	* @param participants list of individuals participating in the draw.
	* @return the list of individuals paired with the participants where participants[0] is matched
	* with assignments[0], participants[1] is matched with assignments[1], and so on.
	*/
	public static String[] generateAssignments(final String[] participants) {
		Function<Integer,Map<Integer,Integer>> mapGenerator=FirstAlgorthim::giftMap;
		return Common.generateAssignments(participants, mapGenerator);
	
	}
	
	private static Map<Integer,Integer> giftMap(int numberOfParticipants){
		Map<Integer,Integer> result=Maps.newHashMap();
		final Random random=new Random();
		List<Integer> remainingList = IntStream.range(0, numberOfParticipants).boxed().collect(Collectors.toList());
		Selection getsLastGiftSelection=getNextSelectionAndList(remainingList,random);
		int getsLastGift=getsLastGiftSelection.getNextPerson();
		
		int giver=getsLastGift;
		remainingList=getsLastGiftSelection.getRemainingList();
		while(remainingList.size()>0){
			Selection selection=getNextSelectionAndList(remainingList,random);
			int getter=selection.getNextPerson();
			remainingList=selection.getRemainingList();
			result.put(giver, getter);
			giver=getter;
		}
		result.put(giver, getsLastGift);
		return result;
	}
	
	private static Selection getNextSelectionAndList(List<Integer> seedList,Random random){
		final int nextPersonIndex=random.nextInt(seedList.size());
		final int nextPerson=seedList.get(nextPersonIndex);
		List<Integer> remainingList=seedList.stream().filter(person->person!=nextPerson).collect(Collectors.toList());
		return new Selection(nextPerson,remainingList);	
		
	}
	
	public static class Selection{
		private final int nextPerson;
		private final List<Integer> remainingList;
		private Selection(int nextPerson, List<Integer> remainingList) {
			this.nextPerson = nextPerson;
			this.remainingList = remainingList;
		}
		public int getNextPerson() {
			return nextPerson;
		}
		public List<Integer> getRemainingList() {
			return remainingList;
		}	
	}
	
	
}
