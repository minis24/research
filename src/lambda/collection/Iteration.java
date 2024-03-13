package lambda.collection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * 리스트를 사용한 이터레이션 
 */
public class Iteration {

	/**
	 * [ Lambda 의 기본 틀 ]
	 * Predicate    : (T -> boolean)    -> 주로 필터에 사용
	 * Supplier     : (() -> T)         -> 만드는놈(객체 생성)
	 * Consumer     : (T -> void)       -> 쓰는놈(실행에 사용)
	 * Function     : (T -> R)          -> From 에서 뭔가를 To 로 만들어 넘김
	 */

 
	
	public static void main(String[] args) {
		
		//리스트에 대한 불변 컬렉션 생성
		final List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		
		//1단계 :: 기존 명령형 프로그래밍
		for(int i = 0 ; i< friends.size();i++) {
			System.out.println(friends.get(i));
		}
		
		
		//2단계 :: 개선된 for 루프 사용
		// 내부적으로 Iterator 인터페이스를 사용하고, hasNext(), next()메서드를 호출한다.
		for(String name : friends) {
			System.out.println(name);
		}
		
		
		//위의 1단계,2단계 모두 외부 이터레이터를 사용한다.
		// for 루프는 본질적으로 순차적인 방식이라 병렬화가 어렵다.
		// 이러한 루프는 비다형적이다. 우리가 원하는 것만을 정확하게 얻는다.
		// 태스트를 수행하기 위해 컬렉션에서 메서드(다형성 오퍼레이션)을 호출하는 대신 
		// for문에 컬렉션을 넘긴다.
		// 원하는 인덱스만 정확하게 실행하는데 유용하다.
		
		System.out.println("------------------------------");
		System.out.println("함수형..1");
		System.out.println("------------------------------");
		//3단계 함수형 스타일로 변경
		//내부 이터레이터를 사용.
		
		// friends 컬렉션에서 forEach()를 호출하고, 이 메서드에 Consumer의 어노니머스 인스턴스를 파라미터로 넘긴다.
		// forEach()메서드는 컬렉션에서 주어진 각 엘리먼트에 대해 주어진 Consumer의 accept() 메서드를 호출하고 원하는 작업을 수행한다.
		friends.forEach(new Consumer<String>() {

			@Override
			public void accept(String name) {
				
				System.out.println(name);
				
			}
		});
		
		
		
		System.out.println("------------------------------");
		System.out.println("함수형..2");
		System.out.println("------------------------------");
		//4단계 함수형 스타일로 변경
		//forEach()메서드는 람다표현식 혹은 코드블록을 인수로 받는 고차함수이다.
		//내부 라이브러리는 람다 표현식의 동작에 대한 제어를 맡는다.
		//내부라이브러리는 호출하는 순서에 상관없이 lazy 하게 실행되므로 병렬화 가능하다.
		// --> 제약사항이 있다.
		// --> 한번 forEach()메서드를 실행하게 되면, 내부에서 이터레이션을 멈출 수 없다.
		// --> (이 제약은 해결하는 방법이 있긴하다.)
		// --> 그래서 컬렉션의 모든 엘리먼트를 처리해야 하는 경우에 유용하다. 
		friends.forEach((final String name) -> System.out.println(name));
		
		
		
		System.out.println("------------------------------");
		System.out.println("함수형..3 ");
		System.out.println("------------------------------");
		// 파라미터의 타입추론이 가능하다. 타입생략.
		// 이때, 한가지 문제는 타입추론한 파라미터는 final이 아니라는 점이다.
		// 파라미터를 final 로 마킹하면 람다표현식 내부에서 파라미터가 수정되는 것을 방지한다.
		// 타입을 설정하지 않고, 컴파일러가 타입을 추론하게 되면, 람다표현식 내부에서 파라미터를 수정하지 않도록 주의해야한다.
		friends.forEach((name) -> System.out.println(name));
		
		// 파라미터가 한개면 괄호생략이 가능하다. 괄호생략.
		friends.forEach(name -> System.out.println(name));
		
		
		
		System.out.println("------------------------------");
		System.out.println("함수형..4 - 메서드 레퍼런스");
		System.out.println("------------------------------");
		// 메서드 레퍼런스 사용
		friends.forEach(System.out::println);
		
	}

	
}
