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

public class SecondAlgorthim {
	

	/**
	* @param participants list of individuals participating in the draw.
	* @return the list of individuals paired with the participants where participants[0] is matched
	* with assignments[0], participants[1] is matched with assignments[1], and so on.
	*/
	public static String[] generateAssignments(final String[] participants) {
		Function<Integer,Map<Integer,Integer>> mapGenerator=SecondAlgorthim::giftMap;
		return Common.generateAssignments(participants, mapGenerator);
	
	}
	
	private static Map<Integer,Integer> giftMap(int numberOfParticipants){
		List<Integer> randomDerangement=randomDerangement(numberOfParticipants);
		Map<Integer,Integer> resultAsMap=Maps.newHashMap();
		for (int i=0;i<numberOfParticipants;i++){
			resultAsMap.put(i, randomDerangement.get(i));
		}
		return resultAsMap;
		
	}
	
	private static List<Integer> randomDerangement(int numberOfParticipants){
		List<Long> d=derangementRecurrancces(numberOfParticipants);
		final Random random=new Random();
		List<Integer> swapList = IntStream.range(0, numberOfParticipants).boxed().collect(Collectors.toList());
		List<Integer> markList=Lists.newArrayList(swapList);
		final int n=numberOfParticipants;
		int i=n;
		int u=n;
		while (u>=2){
			if (markList.contains(i)){
				int j=getNextSelection(markList,random);
				Collections.swap(swapList, i, j);
				//While this part is non-deterministic, the only random thing is how much things get swapped.
				//The only thing left to chance is if something needs multiple swaps rather than one.
				double p=random.nextDouble();
				if (p< ((u-1)*d.get(u-2)/d.get(u))){
					//Be sure to remove the item that equals j, not the jth item in the list by casting to Integer
					markList.remove(new Integer(j));
					u--;
				}	
				u--;
			}
			i--;
		}
		return swapList;
	}
	
	private static int getNextSelection(List<Integer> list,Random random){
		final int nextIndex=random.nextInt(list.size());
		return list.get(nextIndex);

		
	}
	
	public static List<Long> derangementRecurrancces(int size){
		List<Long> builder=Lists.newArrayListWithExpectedSize(size);
		builder.add(1L);
		builder.add(0L);
		for (int i=2;i<=size;i++){
			long d=(i-1)*(builder.get(i-1)+builder.get(i-2));
			builder.add(d);
		}
		return ImmutableList.copyOf(builder);
	}
	
	
	
}
