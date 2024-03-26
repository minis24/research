package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Stream_reduce {

	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		/**********************************************************************************************************/
		// * map 오퍼레이션 
		//	--> mapToInt , mapToDouble 등이 있으며 IntStream,DoubleStream 같은 특정 타입 스트림을 생성한다.
		/**********************************************************************************************************/
		// * reduce 오퍼레이션의 특정케이스들.sum() 외..
		// - 최대길이 max() 오퍼레이션 
		// - 최소길이 min() 오퍼레이션 
		// - 길이정렬 sorted() 오퍼레이션
		// - 길이의 평균 average() 오퍼레이션 등.
		/**********************************************************************************************************/
		int num = friends.stream()
				.mapToInt(name -> name.length())
				.sum();
		System.out.println(num);
		
		
		
		

		/**********************************************************************************************************/
		// * reduce 오퍼레이션 [리턴값은 Optional이며,결과가 비워져있을 수도 있기 때문.]
		//	--> reduce()메서드는 컬렉션을 이터레이션 하면
		//  --> 첫번째 호출에서 리스트에 있는 처음 두개 엘리먼트를 사용하여 람다표현식을 호출한다.
		//  --> 람다표현식의 결과는 다음 호출에서 사용된다.
		//  --> 두번째 호출에서 name1 은 이전 호출의 결과이며, name2는 3번째 엘리먼트다.
		/**********************************************************************************************************/
		final Optional<String> aLongName = 
				friends.stream()
						.reduce((name1,name2) -> name1.length() >= name2.length() ? name1 : name2);
				
		aLongName.ifPresent(name -> System.out.println(name));
		
		
		//기준값이 있는경우 Optional로 리턴되지 않는다.
		final String steveOrLonger = 
				friends.stream()
				.reduce("steave",(name1,name2) ->
				                   name1.length() >= name2.length() ? name1 : name2);
		
		System.out.println(steveOrLonger);
	}
	
	
		
}
