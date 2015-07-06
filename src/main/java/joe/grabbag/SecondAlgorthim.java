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
		List<Long> d=derangementRecurrancces(numberOfParticipants+1);
		final Random random=new Random();
		List<Integer> swapList = IntStream.range(0, numberOfParticipants).boxed().collect(Collectors.toList());
		List<Integer> markList=Lists.newArrayList(swapList);
		final int n=numberOfParticipants;
		int i=n-1;
		int u=n;
		while (u>=2){
			if (markList.contains(i)){
				int j=getNextSelection(markList,random,i);
				Collections.swap(swapList, i, j);
				double p=random.nextDouble();
				//if (p< ((u)*d.get(u-1)/d.get(u+1))){
				if(false){
					//Be sure to remove the item that equals j, not the jth item in the list by casting to Integer
					markList.remove(new Integer(j));
					u--;
				}	
				markList.remove(new Integer(i));
				u--;
			}
			i--;
		}
		return swapList;
	}
	
	private static int getNextSelection(List<Integer> list,Random random,int skip){
		List<Integer> remainingList=list.stream().filter(number->number!=skip).collect(Collectors.toList());
		final int nextIndex=random.nextInt(remainingList.size());
		return remainingList.get(nextIndex);

		
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
