package java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Stream_stream2 {

	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		
		
		
		/*==================================================================*/		
		//java 8에서 Iterable 인터페이스에 foreach() 메서드 제공 
		// - Consumer 타입의 파라미터를 받는다.
		// - Consumer의 인스턴스는 accept() 메서드를 통해 얻은 자원을 소비하는 기능을 한다.
		//   (1) friends 컬렉션에서 foreach() 메서드를 호출하고, 
		//   (2) Consumer의 어나니머스 인스턴스를 파라미터로 전달한다.
		//   (3) foreach() 메서드는 컬렉션에서 각 엘리먼트에 대해 주어진 Consumer의 accept() 메서드를 호출한다. 
		/*==================================================================*/		
		friends.forEach(new Consumer<String>() {
			@Override
			public void accept(final String name) {
				System.out.println(name);
			}
		});
		
		
		
		System.out.println("=============================");
		System.out.println("람다형식으로 전환.");
		System.out.println("=============================");
		//위와 같은 기능을 수행하는 코드 
		friends.forEach((final String name) -> System.out.println(name));
		
		
		
		
		
		// 타입정보 추론이 가능할 경우 생략 가능 
		System.out.println("=============================");
		System.out.println("타입 생략 함.");
		System.out.println("=============================");
		friends.forEach((name) -> System.out.println(name));
		
		
		
		
		// 싱글 파라미터 표현식에서 파라미터 추론이 가능한 경우 괄호 생략 가능  
		System.out.println("=============================");
		System.out.println("괄호 생략 함.");
		System.out.println("=============================");
		friends.forEach(name -> System.out.println(name));
		
		
		
		
		
		// 메서드 레퍼런스 사용
		// java에서는 코드의 본문을 우리가 선택한 메서드 이름으로 변경할 수 있다.
		System.out.println("=============================");
		System.out.println("메서드 레퍼런스 사용.");
		System.out.println("=============================");
		friends.forEach(System.out::println);
	}

}
