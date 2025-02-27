package stream.usage.reducing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class sum {

	
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
		// 요소의 합 
		//---------------------------------------------------------	
		// reduce를 이용해서 다음처럼 스트림의 모든 요소를 더할 수 있다.
		// reduce는 두개의 인수를 갖는다.
		// (1) 초기값:0
		// (2) 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>.
		//   예제에서는 람다 표현식 (a, b) -〉 a + b를 사용했다.
		
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		int sum = someNumbers.stream()
				.reduce(0, (a, b) -> a + b);
		// 스트림이 하나의 값으로 줄어들 때까지 람다는 각 요소를 반복해서 조합한다
		// 초기값 0과 첫번째 스트림요소 1을 더한다. 결과 : 1
		// 결과 1과 두번째 스트림 요소 2를 더한다 . 결과 : 3
		// 결과 3과 세번쨰 스트림 요소 3을 더한다. 결과 : 6
		// 결과 6과 네번쨰 스트림 요소 4를 더한다 . 결과 : 10
		// 결과 10과 다섯번째 스트림 요소 5를 더한다. 결과 : 15 
		
		
		//----------------------------------------------------------
		// 메서드 참조를 이용해서 이 코드를 좀 더 간결하게 만들 수 있다.
		// 자바 8에서는 Integer 클래스에 두 숫자를 더하는 정적 sum 메서드를 제공한다
		//----------------------------------------------------------
		int sum1 = someNumbers.stream().reduce(0,Integer::sum);
		
		
		
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
		
		
		
		
		
		/**********************************************************************************************************/
		// * map 오퍼레이션 
		//	--> mapToInt , mapToDouble 등이 있으며 IntStream,DoubleStream 같은 
		//      특정 타입 스트림을 생성한다.
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
