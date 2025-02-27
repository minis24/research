package stream.usage.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class flatMap {

	public static void main(String[] args) {
		


		List<String> words = Arrays.asList("Hello","World");


		// ------------------------------------
		// flapMap :: 스트림 평면화
		// ------------------------------------
		// 메서드 map을 이용해서 리스트의 각 단어의 길이를 반환하는 방법을 확인했다.
		// 이를 응용해서 리스트에서 고유 문자로 이루어진 리스트를 반환해보자.
		// 예를들어 ["Hello", "World"] 라는 리스트가 있다면 
		// 결과로 ["H", "e", T, "o", "W", "r", "d"】를 포함하는 리스트가 반환되어야 한다.
		//리스트에 있는 각 단어를 문자로 매핑한 다음에 
		//distinct로 중복된 문자를 필터링해서 쉽게 문제를 해결할 수 있을 것이라고 추측한 독자도 있을 것이다.
		// 즉, 다음처럼 문제를 해결할 수 있다



		words.stream()
				.map(word -> word.split(""))
				.distinct()
				.collect(Collectors.toList());



		// ------------------------------------
		// 위 코드에서 map으로 전달한 람다는 각 단어의 String[](문자열 배열)을 반환한다는 점이 문제다.
		// 다행히 flatMap이라는 메서드를 이용해서 이 문제를 해결할 수 있다
		// ------------------------------------
		// 따라서 map 메소드가 반환한 스트림의 형식은 Stream<String[]〉이다.
		// 우리가 원하는 것은 문자열의 스트림을 표현할 Stream〈String>이다. 
		


				
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		
		//명령형 코딩 방식 
		final List<String> uppercaseNames = new ArrayList<String>();
		for(String name : friends) {
			uppercaseNames.add(name.toUpperCase());
		}
		
		
		//내부 이터레이터로 전환
		friends.forEach(name -> uppercaseNames.add(name.toUpperCase()));
		
		
		//스트림의 map()메서드는 각 엘리먼트의 내용을 처리한다.
		// * 람다표현식의 실행결과를 취합하여 결과 컬렉션으로 리턴함.
		// * 입력컬렉션을 출력컬렉션으로 매핑하거나 변경하는데 유용함.
		friends.stream()
			.map(name -> name.toUpperCase())
			.forEach(name -> System.out.println(name + " "));
		
		
		
		//map()메서드는 입력컬렉션의 수는 출력컬렉션의 수와 같음을 보장한다.
		// 타입은 다를 수 있다.
		friends.stream()
			.map(item -> item.length())
			.forEach(item -> System.out.println("length : "+item));
	
		
		
		//람다표현식에서 파라미터가 없는 경우 메서드 레퍼런스를 사용하여 간결하게 할 수 있다.
		// String 클래스의 toUpperCase 메서드를 호출하는 경우.
		friends.stream()
			.map(String :: toUpperCase)
			.forEach(name -> System.out.println(name));
	
	}
}
