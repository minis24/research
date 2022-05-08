package java8.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class String_collect {

	/**
	 * [ Lambda 의 기본 틀 ]
	 * Predicate    : (T -> boolean)    -> 주로 필터에 사용
	 * Supplier     : (() -> T)         -> 만드는놈(객체 생성)
	 * Consumer     : (T -> void)       -> 쓰는놈(실행에 사용)
	 * Function     : (T -> R)          -> From 에서 뭔가를 To 로 만들어 넘김
	 */

 
	
	public static void main(String[] args) {

		
		final List<Person> people = Arrays.asList(
				new Person("Jhon",20),
				new Person("Sara",21),
				new Person("Jane",21),
				new Person("Greg",35)
				);
		

		// 명령적 
		List<Person> olderThan20 = new ArrayList<Person>();
		people.stream()
		.filter(p -> p.getAge() > 20)
		.forEach(p -> olderThan20.add(p));
		System.out.println("people Older than 20 : " + olderThan20);
	
		
		//서술적 (1)
		printPeople("collect (1) : ",
		people.stream()
		.filter(p -> p.getAge() > 20)
		/*****************************************************************************************************/
		//collect() 메서드가 이터레이션의 타깃 멤버를 원하는 타입의 포맷으로 변환하는 리듀서의 역할 
		//collect() 메서드는 엘리먼트들에 대한 스트림을 가지며, 결과 컨테이너로 스트림을 모은다.
		//collect() 메서드는 첫 번째 파라미터로 팩토리나 서플라이어를 갖는다. 그 다음 파라미터는 엘리먼트들을 컬렉션으로 모으는 오퍼레이션.
		// * 결과컨테이너를 만드는 방법 - ArrayList::new
		// * 하나의 엘리먼트를 결과 컨테이너에 추가하는 방법 - ArrayList::add 
		// * 하나의 결과 컨테이너를 다른 것과 합치는 방법 - ArrayList::addAll
		/*****************************************************************************************************/
		.collect(ArrayList::new , ArrayList::add , ArrayList::addAll));
		
		
		
		//서술적 (2)
		printPeople("collect (2) : ",
		people.stream()
		.filter(p -> p.getAge() > 20)
		/*****************************************************************************************************/
		// toList() 자리에는 아래와 같은 오퍼레이션이 가능함.
		// * toSet()
		// * toMap()
		// * joinning() : 엘리먼트를 스트링으로 합치기
		// * minBy()
		// * maxBy()
		// * groupingBy()
		/*****************************************************************************************************/
		.collect(Collectors.toList())
		);
		
		
		
		
		
		
		
		//서술적 (3) : 나이로 그룹핑 
		Map<Integer, List<Person>> peopleByAge = people.stream()
												.filter(p -> p.getAge() > 20)
		/*****************************************************************************************************/
		// * groupingBy() 는 람다 표현식이나 메서드 레퍼런스를 갖는다. 이를 분류함수라고 한다.
		// 분류함수는 그룹핑 하려고 하는 엘리먼트의 속성값을 리턴하고, 이 결과에 따라 컨텍스트에 있는 엘리먼트를 그룹에 추가한다.
		/*****************************************************************************************************/
												.collect(Collectors.groupingBy(Person::getAge));
		System.out.println("서술적 (3)");
		System.out.println(peopleByAge);
		
		
		
		
		
		
		
		//서술적 (4) : 이름 리스트를 나이로 그룹핑 
		Map<Integer, List<String>> nameOfpeopleByAge = 
				people.stream()
					  .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName,Collectors.toList())));
		System.out.println("서술적 (4)");
		System.out.println(nameOfpeopleByAge);
		
		
		
		
		
		
		//서술적 (5) : 첫번째 문자로 이름을 그룹핑하고, 가그룹에서 가장 나이가 많은 사람을 얻는다.
		Comparator<Person> byAge = Comparator.comparing(Person::getAge);
		Map<Character,Optional<Person>> oldestPersonOfEachLetter = 
				people.stream()
					  .collect(Collectors.groupingBy(p -> p.getName().charAt(0), 
							  			 			 Collectors.reducing(BinaryOperator.maxBy(byAge))
							  ));
		System.out.println("서술적 (5) : Oldest Person of each Letter");
		System.out.println(oldestPersonOfEachLetter);
		
	}
	
	
	
	
		
	
	
	
	public static void printPeople(final String message, final List<Person> people) {
		System.out.println(message);
		people.forEach(System.out::println);
	}
}
