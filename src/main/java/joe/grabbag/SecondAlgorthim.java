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
public class SecondAlgorthim {
	

	public static String[] generateAssignments(final String[] participants) {
		Function<Integer,Map<Integer,Integer>> mapGenerator=SecondAlgorthim::giftMap;
		return Common.generateAssignments(participants, mapGenerator);
	
	}
	
	static Map<Integer,Integer> giftMap(int numberOfParticipants){
		List<Integer> randomDerangement=randomDerangement(numberOfParticipants);
		Map<Integer,Integer> resultAsMap=Maps.newHashMap();
		for (int i=0;i<numberOfParticipants;i++){
			resultAsMap.put(i, randomDerangement.get(i));
		}
		return resultAsMap;
		
	}
	
	static List<Integer> randomDerangement(int numberOfParticipants){
		List<Double> d=derangementRecurrancces(numberOfParticipants+1);
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
				if (p< ((u-1)*d.get(u-2)/d.get(u))){
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
	
	static int getNextSelection(List<Integer> list,Random random,int skip){
		List<Integer> remainingList=list.stream().filter(number->number!=skip).collect(Collectors.toList());
		final int nextIndex=random.nextInt(remainingList.size());
		return remainingList.get(nextIndex);
	}
	
	
	static List<Double> derangementRecurrancces(int size){
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
