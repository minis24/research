package stream.usage.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class map {

	public static void main(String[] args) {
		


		List<Dish> specialMenu = Arrays.asList(
			new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
			new Dish("prawns", false, 300, Dish.Type.FISH),
			new Dish("rice", true, 350, Dish.Type.OTHER),
			new Dish("chicken", false, 400, Dish.Type.MEAT),
			new Dish("french fries", true, 530, Dish.Type.OTHER));


		// ------------------------------------
		// map :: 스트림의 각 요소에 함수 적용하기
		// ------------------------------------
		// 스트림은 함수를 인수로 받는 map 메서드를 지원한다.
		// 인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다
		// (이 과정은 기존의 값을 ‘고친다’라는 개념보다는 '새로운 버전을 만든다’라는 개념에 가까우므로 ‘변환에 가까운
		// ‘매핑：napping’이라는 단어를 사용한다).
		// 예를 들어 다음은 Dish::getName을 map 메서드로 전딜해서 스트림의 요리 명을 추출하는 코드다.

		List<String> dishNames = specialMenu.stream()
				.map(Dish::getName)
				.collect(Collectors.toList());

		







				
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
