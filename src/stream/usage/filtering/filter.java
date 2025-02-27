package stream.usage.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class filter {
	
	
	// 
	//-----------------------------------------
	//렉시컬스코프 (javascript)
	//-----------------------------------------
	// 함수를 어디서 호출하는지가 아니라 어디에 선언하였는지에 따라 결정되는 것을 말한다.
    // 즉, 함수를 어디서 선언하였는지에 따라 상위 스코프를 결정한다는 뜻이며, 
	// 가장 중요한 점은 함수의 호출이 아니라 함수의 선언에 따라 결정된다는 점이다.
	// 다른 말로, 정적 스코프(Static scope)라 부르기도 하다.
	
	//-----------------------------------------
	// Predicate 
	//-----------------------------------------
	// 스트림 인터페이스는 filter 메서드를 지원한다.
	// filter 메서드는 프레디케이트（불리언을 반환하는 함수）를 인수로 받아서 
	// 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.
	// --> 컬렉션에 있는 컨텍스트 엘리먼트를 표현하기 위해 하나의 파라미터를 받는 함수를 수신해야 하며 
	// --> bolean 결과를 리턴한다.
	public static Predicate<String> checkIfStartsWith(final String letter) {
		return name -> name.startsWith(letter);
	}
	
	
	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		//명령형 코딩 방식 
		final List<String> startsWithN = new ArrayList<String>();
		
		for(String name : friends) {
			if(name.startsWith("N")) {
				startsWithN.add(name);
			}
		}
		
		
		
		//filter()메서드는 boolean 결과를 리턴하는 람다표현식이 필요하다.
		// true면 결과 컬렉션에 추가하고, false면 다음으로 넘어간다.
		// 결과 컬렉션의 스트림을 리턴한다.
		// 리턴된 스트림을 collect()메서드를 사용하여 리스트로 변경한다.
		final List<String> startsWithN_filter = 
				friends.stream()
				.filter(name -> name.startsWith("N"))
				.collect(Collectors.toList());
		

	
		//람다표현식 재사용1
		//filter()메서드는 java.util.function.Predicate 함수형 인터페이스에 대한 레퍼런스를 받는다.
		//자바컴파일러에서 람다표현식에서 Predicate의 test()메서드의 구현을 합성한다.
		Predicate<String> startsWithN_ref = name -> name.startsWith("N");
		
		final long countFriendsStartsN = friends.stream().filter(startsWithN_ref).count();
		final long countEditorsStartsN = editors.stream().filter(startsWithN_ref).count();
		final long countComradesStartsN = comrades.stream().filter(startsWithN_ref).count();
		
		
		
		
		//람다표현식 재사용2
		final long countFriendsStartsN1 = friends.stream().filter(checkIfStartsWith("N")).count();
		final long countFriendsStartsB1 = friends.stream().filter(checkIfStartsWith("B")).count();
		
		System.out.println("countFriendsStartsB1 ::"+countFriendsStartsB1);
		
		//람다표현식 재사용3
		final Function<String, Predicate<String>> startsWithLetter = 
				(String letter) -> {
					Predicate<String> checkStarts =
							(String name) -> name.startsWith(letter);
				
				return checkStarts;
				};
			
				
				
				
		//좀더 간결하게 정리하면..1
		final Function<String, Predicate<String>> startsWithLetter_2 = 
				(String letter) -> (String name) -> name.startsWith(letter);
		
				
		//좀더 간결하게 정리하면..2
		final Function<String, Predicate<String>> startsWithLetter_3 = 
				letter -> name -> name.startsWith(letter);
				
				
		final long countFriendsStartsN2 = friends.stream().filter(startsWithLetter_3.apply("N")).count();
		final long countFriendsStartsB2 = friends.stream().filter(startsWithLetter_3.apply("B")).count();
				
	}
}
