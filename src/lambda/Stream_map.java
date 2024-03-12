package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stream_map {

	public static void main(String[] args) {
		
		
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
