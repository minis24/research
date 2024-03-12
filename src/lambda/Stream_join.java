package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Stream_join {

	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		/**********************************************************************************************************/
		// * 내부적으로 join()메서드는 StringJoiner를 호출하여 
		//   두번째 매개변수인 가변인자에 있는 값들을 첫번째 매겨변수인 콤마로 구분된 하나의 큰 스트링으로 합친다. 
		/**********************************************************************************************************/
		System.out.println(String.join(",", friends));
	}
	
	
		
}
