package joe.grabbag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;





import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * http://epubs.siam.org/doi/pdf/10.1137/1.9781611972986.7
 * I didn't invent this, but I pounded google until I found out I was looking for a "derangement"
 * https://en.wikipedia.org/wiki/Derangement
 * This is a random derangement generator.  Every derangement of the initial array is equally likely to appear as the output.
 * @author Joe
 *
 */
public class MartinezPanholzerProdingerAlgorithm {
	

	public static String[] generateAssignments(final String[] participants) {
		Function<Integer,Map<Integer,Integer>> mapGenerator=MartinezPanholzerProdingerAlgorithm::giftMap;
		return Common.generateAssignments(participants, mapGenerator);
	
	}
	
	/**
	 * Translate the result to the interface I made which represents the mapping of elements as a map.
	 * @param numberOfParticipants
	 * @return The result of the algorithm translated to the interface I made, which represents the mapping of elements as a map.
	 */
	static Map<Integer,Integer> giftMap(int numberOfParticipants){
		List<Integer> randomDerangement=randomDerangement(numberOfParticipants);
		Map<Integer,Integer> resultAsMap=Maps.newHashMap();
		for (int i=0;i<numberOfParticipants;i++){
			resultAsMap.put(i, randomDerangement.get(i));
		}
		return resultAsMap;
		
	}
	
	/**
	 * This is the algorithm from http://epubs.siam.org/doi/pdf/10.1137/1.9781611972986.7
	 * @param numberOfParticipants
	 * @return The random derangement from as a list.
	 */
	static List<Integer> randomDerangement(int numberOfParticipants){
		List<Double> subfactorial=subfactorials(numberOfParticipants+1);
		final Random random=new Random();
		List<Integer> swapList = IntStream.range(0, numberOfParticipants).boxed().collect(Collectors.toList());
		List<Integer> stillNeedsToBeSwapped=Lists.newArrayList(swapList);
		final int n=numberOfParticipants;
		for (int i=n-1;stillNeedsToBeSwapped.size()>=2;i--){
			if (stillNeedsToBeSwapped.contains(i)){
				int randomItemToSwap=getNextSelection(stillNeedsToBeSwapped,random,i);
				Collections.swap(swapList, i, randomItemToSwap);
				double p=random.nextDouble();
				//Note: Whether this loop is executed or not, the result will still be a proper derangement.
				//The random calculation calibrates things so each permutation gets an equal chance.
				if (p< ((stillNeedsToBeSwapped.size()-1)*subfactorial.get(stillNeedsToBeSwapped.size()-2)/subfactorial.get(stillNeedsToBeSwapped.size()))){
					//Be sure to remove the item that equals randomItemToSwap, not the randomItemToSwap-th item in the list by casting to Integer
					stillNeedsToBeSwapped.remove(new Integer(randomItemToSwap));
				}	
				//Remove the item that equals i, not the i-th item, by casting it to Integer.
				stillNeedsToBeSwapped.remove(new Integer(i));
			}
		}
		return swapList;
	}
	
	/**
	 * 
	 * @param list to pick from
	 * @param random generator
	 * @param skip- the item we are swapping with.  Don't pick this one.
	 * @return A random item to swap
	 */
	static int getNextSelection(List<Integer> list,Random random,int skip){
		List<Integer> remainingList=list.stream().filter(number->number!=skip).collect(Collectors.toList());
		final int nextIndex=random.nextInt(remainingList.size());
		return remainingList.get(nextIndex);
	}
	
	/**
	 * @param size of list
	 * @return A list of the values for D subscript n from the paper.  This is the formula on the second column on the second page.
	 */
	static List<Double> subfactorials(int size){
		List<Double> builder=Lists.newArrayListWithExpectedSize(size);
		builder.add(1.0);
		builder.add(0.0);
		for (int i=2;i<=size;i++){
			double d=(builder.get(i-1)+builder.get(i-2))*(i-1);
			builder.add(d);
		}
		return ImmutableList.copyOf(builder);
	}
	
}
