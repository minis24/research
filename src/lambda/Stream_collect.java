package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Stream_collect {

	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		/**********************************************************************************************************/
		// * collect 
		/**********************************************************************************************************/
		System.out.println(
				friends.stream().map(String::toUpperCase)
				.collect(Collectors.joining(", "))
				);
	}
	
	
		
}
