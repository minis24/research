package lambda.string.comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class String_comparator {

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
		


		
		
		/*****************************************************************************************************/
		// * sorted()메서드는 파라미터로 Comparator를 갖는다.
		// * Comparator는 함수형 인터페이스라서 람다 표현식을 인수로 쉽게 넘길 수 있다.
		// --> Comparator의 compareTo() 추상 메서드는 두개의 파라미터를 가지며 int 타입의 리턴값을 갖는다.
		// --> 이 파라미터를 사용하여 람다표현식은 두개의 파라미터를 갖게 되며, 두개의 객체가 같은지 다른지를 알려주는 int값을 리턴한다.
		// --> 같으면 0 ,음수면 첫번째가 작고, 양수면 첫번째가 크다.
		//* collect() :: 결과를 하나의 리스트로 만든다.
		// --> 메서드가 이터레이션의 타깃 멤버를 원하는 타입의 포맷으로 변환하는 리듀서의 역할 
		//  -->Collectors.toList() : 컨비니언스 클래스의 정적 메소드
		/*****************************************************************************************************/
		List<Person> ascAge = people.stream()
									.sorted((p1,p2) -> p1.ageDifference(p2))		
									.collect(Collectors.toList());
	
//		public interface Comparator<T> {
//			public int compare(T o1, T o2);
//		}
		
		
		
		
		
		
		
		//메서드 레퍼런스 적용
		List<Person> ascAge2 = people.stream()
				/*****************************************************************************************************/
				//두개의 파라미터를 라우팅하는 대신 메서드 레퍼런스를 사용하여 컴파일러가 라우팅하도록 하자.
				//두개의 파라미터가 타깃 또는 인수로 사용되는데, 컴파일러가 알아서 해준다.
				/*****************************************************************************************************/
				.sorted(Person::ageDifference)
				.collect(Collectors.toList());
		
		
		printPeople("Sorted in ascending order by age : ", ascAge2);
		
		/*
		Sorted in ascending order by age : 
		Jhon - 20
		Sara - 21
		Jane - 21
		Greg - 35	
		*/
		
		
		
		
		
		
		//Comparator 재사용 
		Comparator<Person> compareAscending = (p1,p2) -> p1.ageDifference(p2); //오름차순 
		/*****************************************************************************************************/
		// 내림차순: 중복작업으로 람다표현식을 구현하는 대신 첫번째 Comparator에서 reserve()를 호출하면 
		// 비교결과가 역순으로된 다른 Comparator를 획득한다.
		/*****************************************************************************************************/
		Comparator<Person> compareDescending = compareAscending.reversed(); 
		
		printPeople("Sorted in ascending order by age : ",
					people.stream().sorted(compareAscending).collect(Collectors.toList())
				);
		
		printPeople("Sorted in descending order by age : ",
				people.stream().sorted(compareDescending).collect(Collectors.toList())
			);
		
		
		
		
		
		//이름으로 정렬..
		printPeople("Sorted in ascending order by name : ",
				people.stream()
				.sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
				.collect(Collectors.toList())
			);
		
		Comparator<Person> compareAscendingName = (p1,p2) -> p1.getName().compareTo(p2.getName()); //오름차순 
		Comparator<Person> compareDescendingName = compareAscendingName.reversed(); //오름차순 
		
		printPeople("Sorted in descending order by name : ",
				people.stream()
				.sorted(compareDescendingName)
				.collect(Collectors.toList())
			);
		
		
		
		
		/*****************************************************************************************************/
		//리스트에서 가장 어린 사람. 
		// --> 명령형 코드 : 리스트 정렬 후 맨첫번째 아이템 
		// --> 스트림을 사용하면 min() 호출 ,  min()메서드는 Comparator를 파라미터로 받아서 가작적은 객체를 리턴함.
		/*****************************************************************************************************/
		people.stream()
		.min(compareAscending) //Optional을 리턴, 이유는 리스트가 비어있을 수 있기 떄문.
		.ifPresent(youngest -> System.out.println("youngest : " + youngest));
		
		
		/*****************************************************************************************************/
		//리스트에서 가장 나이 많은 사람  
		/*****************************************************************************************************/
		people.stream()
		.max(compareAscending) //Optional을 리턴, 이유는 리스트가 비어있을 수 있기 떄문.
		.ifPresent(eldest -> System.out.println("eldest : " + eldest));
		
		
		
		/*****************************************************************************************************/
		//Function<T, R>
		//<T> the type of the input to the function
		//<R> the type of the result of the function
		/*****************************************************************************************************/
		
		
		/*****************************************************************************************************/
		//복합 비교 연산 
		/*****************************************************************************************************/
		final Function<Person, String> byName = p1 -> p1.getName();
		final Function<Person, Integer> byAge = p1 -> p1.getAge();
		
		
		
		//오름차순 
		printPeople("Sorted in ascending order by name and age : ", 
				people.stream()
				// Comparator.comparing()메서드는 Function타입을 파라미터로 받아서 Comparator를 리턴함.
				// Comparator.comparing()메서드는 Comparator를 생성한 후 나이를 기준으로 정렬하기 위해 리턴하고,
				// 리턴된 Comparator는 thenComparing()메서드를 호출하여 나이와 이름 두값에 따라 비교하는 복합 Comparator를
				// 생성하게 된다.
				.sorted(Comparator.comparing(byAge).thenComparing(byName))
				.collect(Collectors.toList()));

		//내림차순
		printPeople("Sorted in descending order by name and age : ", 
				people.stream()
				.sorted(Comparator.comparing(byAge).reversed().thenComparing(byName).reversed())
				.collect(Collectors.toList()));
		
		
		
		
	}
	
	
	
	
		
	
	
	
	public static void printPeople(final String message, final List<Person> people) {
		System.out.println(message);
		people.forEach(System.out::println);
	}
}
