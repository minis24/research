package stream.usage.reducing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class min {

	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		//---------------------------------------------------------
		// 리듀싱 ==> 모든 스트림 요소를 처리해서 값으로 도출
		//---------------------------------------------------------
		// 리듀스 연산을 이용해서 ‘메뉴의 모든 칼로리의 합계를 구하시오’，
		// ‘메뉴에서 칼로리가 가장 높은 요리는?’ 같은 
		// 스트림 요소를 조합해서 더 복잡한 질의를 표현하는 방법을 설명한다.
		// 이러한 질의를 수행하려면 Integer 같은 결과가 나올 때까지 스트림의 모든 요소를
		// 반복적으로 처리해야 한다
			
		//---------------------------------------------------------
		// 최대값과 최소값을 찾을 떄도 reduce를 활용 할 수 있다.
		//---------------------------------------------------------	
		// reduce는 두개의 인수를 갖는다.
		// (1) 초기값:0
		// (2) 스트림의 두 요소를 합쳐서 하나의 값으로 만드는 데 사용할 람다
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		
		Optional<Integer> max =
				someNumbers
					.stream()
					.reduce(Integer::max);
		
		
		
		//Integer.max 대신 Integer.min을 reduce로 넘겨주면 최솟값을 찾을 수 있다
	
		
		
		//----------------------------------------------------------
		// 초기값 없음 : 초기값을 받지 않도록 오버로드된 reduce 도 있다.
		// 이때 reduce는 Optional 객체를 반환한다.
		//----------------------------------------------------------	
		// ==> 왜 Optional〈Integer〉를 반환하는 걸까? 
		//   스트림에 아무 요소도 없는 상황을 생각해보자
		//   이런 상황이라면 초깃값이 없으므로 reduce는 합계를 반환할 수 없다
		//   따라서 합계가 없음을 가리킬 수 있도록 Optional 객체로 감싼 결과를 반환한다
		Optional<Integer> sum2 = 
				someNumbers
					.stream()
					.reduce(Integer::sum);
		
		sum2.ifPresent(System.out::println);
		//출력 결과 : 15 
	}
	
	
		
}
