package lambda.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 컬렉션을 사용해서 다른 결과물, 즉 다른 형태의 컬렉션을 만들어 낸다. 
 */
public class ListTransform {

	/**
	 * [ Lambda 의 기본 틀 ]
	 * Predicate    : (T -> boolean)    -> 주로 필터에 사용
	 * Supplier     : (() -> T)         -> 만드는놈(객체 생성)
	 * Consumer     : (T -> void)       -> 쓰는놈(실행에 사용)
	 * Function     : (T -> R)          -> From 에서 뭔가를 To 로 만들어 넘김
	 */

 
	
	public static void main(String[] args) {
		
		final List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		
	
		
		// 컬렉션의 모든 엘리먼트의 내용을 모두 대문자로 변경하는 경우 
		//  - Java 의 String은 불변성(immutable) 이며, 스트링의 인스턴스 자체는 수정될 수 없다.
		//  - 모두 대문자로 된 새로운 String 을 만든다.
		//  - 컬렉션의 적절한 엘리먼트를 이 String으로 변경한다.
		//  - 원본 컬렉션을 잃어 버릴수 있다.
		//  - 원본 리스트가 Arrays.asList() 를 사용하여 생성하는 것과 같이 불변이라면, 그 리스트는 변경할 수 없다.
		//  - 원본 리스트를 사용하는 경우 병렬화 하기가 어렵다.
		//*****************************************************
		// 그래서 --> 모두 대문자로 된 새로운 리스트를 생성하는 것이 더 좋다.
		//*****************************************************
		
		
		
		System.out.println("------------------------------");
		System.out.println("1단계");
		System.out.println("------------------------------");
		//1단계 새로운 컬렉션을 생성하는 것부터 하자.
		final List<String> upperCaseNames = new ArrayList<String>();
		
		for(String name : friends) {
			upperCaseNames.add(name.toUpperCase());
		}

		upperCaseNames.forEach(System.out::println);
		
		
		
		
		
		System.out.println("------------------------------");
		System.out.println("2단계");
		System.out.println("------------------------------");
		//2단계 for문을 내부 이터레이터로 변경하자.
		final List<String> upperCaseNames1 = new ArrayList<String>();
		friends.forEach(name-> upperCaseNames1.add(name.toUpperCase()));
		upperCaseNames1.forEach(System.out::println);
		
		
		
		System.out.println("------------------------------");
		System.out.println("3단계");
		System.out.println("------------------------------");
		//람다표현식을 사용한다.
		// 새로운 스트림 인터페이스의 map()메서드를 사용하면 가변성이 발생하지 않도록 코드를 간결하게 작성 할 수 있다.
		// map()메서드는 입력 순서를 출력 순서로 매핑하거나 입력 순서를 다른 순서의 출력으로 변형한다.
		friends.stream()
			.map(name -> name.toUpperCase())
			.forEach(name -> System.out.print(name + " "));
		
		
		
		
		
	}
}
