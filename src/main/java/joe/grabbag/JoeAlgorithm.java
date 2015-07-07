package joe.grabbag;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

public class JoeAlgorithm {
	

	/**
	* Simple algorthim of my own devising.
	* Picks a person to receive the last gift and give the first gift.  
	* Picks someone to receive the first gift and give the second.
	* The person that receives that gift gets the third, etc.
	* Everyone gives and receives one gift.
	* No one gives to themselves.
	* There order is almost random, although there will never be any "loops".
	* So if there are more than two people and Alice gives Bob a gift, Bob will not give Alice the gift.
	* Likewise, if there are more than three people and Alice gives to Bob and Bob gives to Craig, Craig will not give to Alice.
	* 
	*/
	public static String[] generateAssignments(final String[] participants) {
		Function<Integer,Map<Integer,Integer>> mapGenerator=JoeAlgorithm::giftMap;
		return Common.generateAssignments(participants, mapGenerator);
	
	}
	
	static Map<Integer,Integer> giftMap(int numberOfParticipants){
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
	
	static Selection getNextSelectionAndList(List<Integer> seedList,Random random){
		final int nextPersonIndex=random.nextInt(seedList.size());
		final int nextPerson=seedList.get(nextPersonIndex);
		List<Integer> remainingList=seedList.stream().filter(person->person!=nextPerson).collect(Collectors.toList());
		return new Selection(nextPerson,remainingList);	
		
	}
	
	/**
	 * Utility class.  Holds the result of getNextSelectionAndList, so one function can get neatly get two things.
	 */
	private static class Selection{
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
