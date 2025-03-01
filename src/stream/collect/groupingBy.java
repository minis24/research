package stream.collect;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import lambda.string.comparator.Person;
import stream.usage.stream_slicing.Dish;

public class groupingBy {

	
	public enum CaloricLevel { DIET, NORMAL, FAT }
	
	public static void main(String[] args) {
		List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER));

		
		//--------------------------------------------------------
		//그룹화
		//--------------------------------------------------------
		//데이터 집합을 하나 이상의 특성으로 분류해서 그룹화하는 연산도 데이터베이스에서 많이 수행되는 작업이다.
		//트랜잭션 통화 그룹화 예제에서 확인했듯이 명령형으로 그룹화를 구현하려면 까다롭고，할일이 많으며，에러도 많이 발생한다.
		//하지만 자바 8의 함수형을 이용하면 가독성 있는 한 줄의 코드로 그룹화를 구현할 수 있다.
		
		// 이번에는 메뉴를 그룹화한다고 가정하자.
		//스트림의 각 요리에서 Dish .Type 과 일치하는 모든 요리를 추출하는 함수를 groupingBy 메서드로 전달했다.
		//이 함수를 기준으로 스트림이 그룹화되므로 이를 분류 함수라고 한다.
		Map<Dish.Type, List<Dish>> dishesByType =
				specialMenu.stream()
							.collect(Collectors.groupingBy(Dish::getType));
		
		
		System.out.println("dishesByType :: "+dishesByType);
		/*
		 * 출력결과 
		 * dishesByType :: {OTHER=[seasonal fruit, rice, french fries], FISH=[prawns], MEAT=[chicken]}
		 */
		
		
		
		//단순한 속성 접근자 대신 더 복잡한 분류 기준이 필요한 상황에서는 메서드 참조를 분류 함수로 사용할 수 없다.
		//예를 들어 400칼로리 이하를 ‘diet’로，400〜700칼로리를 ‘normal’로，700 칼로리 초과를 ‘fat* 요리로 분류한다고 가정하자.
		//Dish 클래스에는 이러한 연산에 필요한 메서드가 없으므로 메서드 참조를 분류 함수로 사용할 수 없다.
		//따라서 다음 예제에서 보여주는 것처럼 메서드 참조 대신 람다 표현식으로 필요한 로직을 구현할 수 있다.
		Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = 
				specialMenu.stream()
						   .collect(Collectors
								   		.groupingBy(dish -> {
								   				if (dish.getCalories() <= 400) return CaloricLevel.DIET;
								   				else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
								   				else return CaloricLevel.FAT;
		
								   		}));
		
		System.out.println("dishesByCaloricLevel :: "+dishesByCaloricLevel);
		/*
		 * 출력결과 
		 * dishesByCaloricLevel :: {NORMAL=[french fries], DIET=[seasonal fruit, prawns, rice, chicken]}
		 */
		
		
		
		
		
		
		
		
		final List<Person> people = Arrays.asList(
				new Person("Jhon",20),
				new Person("Sara",21),
				new Person("Jane",21),
				new Person("Greg",35)
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
		
		/*
		 * 출력 결과
		 * 서술적 (3)
			{35=[Greg - 35], 21=[Sara - 21, Jane - 21]}
		 */
		
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
}
