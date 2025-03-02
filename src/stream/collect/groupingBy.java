package stream.collect;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import lambda.string.comparator.Person;
import stream.usage.stream_slicing.Dish;

public class groupingBy {

	
	public enum CaloricLevel { DIET, NORMAL, FAT }
	
	public static void main(String[] args) {
		List<Dish> specialMenu = Arrays.asList(
				new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), 
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), 
				new Dish("french fries", true, 530, Dish.Type.OTHER));

		
		
		
		
		
		
		
		//--------------------------------------------------------
		//그룹화 (groupingBy)
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
		
		
		
		
		
		
		//--------------------------------------------------------
		//(1) groupingBy 연계 - 그룹화된 요소 조작 (filtering)
		//--------------------------------------------------------
		//그룹화 하기전 프레디케이트로 필터를 적용하면 결과 맵에서 해당 키 자체자 사라질 수 있다.
		// ==> 아래 예제에서는 OTHER 만 결과로 나왔다.
		Map<Dish.Type, List<Dish>> caloricDishesByType = 
				specialMenu.stream().filter(dish -> dish.getCalories() > 500)
				.collect(Collectors.groupingBy(Dish::getType));
		
		System.out.println("caloricDishesByType :: "+caloricDishesByType);
		/*
		 * 출력결과 
		 * caloricDishesByType :: {OTHER=[french fries]}
		 */	
		
		
		
		
		
		// Collectors 클래스는 일반적인 분류 함수에 Collector 형식의 두 번째 인수를 갖도록 
		// groupingBy 팩토리 메서드를 오버로드해 이 문제를 해결한다.
		// 다음 코드에서 보여주는 것처럼 두 번째 Collector 안으로 필터 프레디케이트를 이동함으로 이 문제를 해결할 수 있다.
		// filtering 메소드는 Collectors 클래스의 또 다른 정적 팩토리 메서드로 프레디케이트를 인수로 받는다.
		// 이 프레디케이트로 각 그룹의 요소와 필터링 된 요소를 재그룹화 한다.
		// 이렇게 해서 아래 결과 맵에서 볼 수 있는 것처럼 목록이 비어있는 FISH도 항목으로 추가된다.
		Map<Dish.Type, List<Dish>> caloricDishesByType1 = 
				specialMenu.stream()
						   .collect(Collectors.groupingBy(Dish::getType
								   ,Collectors.filtering(dish -> dish.getCalories() > 500,Collectors.toList())));
		
		System.out.println("caloricDishesByType1 :: "+caloricDishesByType1);
		/*
		 * 출력결과 
		 * caloricDishesByType1 :: {OTHER=[french fries], FISH=[], MEAT=[]}
		 * caloricDishesByType ::  {OTHER=[french fries]}
		 */		
		
		
		
		//--------------------------------------------------------
		//(2) groupingBy 연계 - 그룹화된 요소 조작 (mapping)
		//--------------------------------------------------------
		// 그룹화된 항목을 조작하는 다른 유용한 기능 중 또 다른 하나로 맵핑 함수를 이용해 요소를 변환하는 작업이 있다.
		// Collectors 클래스는 매핑 함수와 각 항목에 적용한 함수를 모으는 데 사용하는 
		// 또 다른 컬렉터를 인수로 받는 mapping 메서드를 제공한다.
		//예를 들어 이 함수를 이용해 그룹의 각 요리를 관련 이름 목록으로 변환할 수 있다.
		Map<Dish.Type, List<String>> dishNamesByType2 =
				specialMenu.stream()
							.collect(Collectors.groupingBy(Dish::getType
							       , Collectors.mapping(Dish::getName, Collectors.toList())));
		System.out.println("dishNamesByType2 :: "+dishNamesByType2);
		/*
		 * 출력결과 
		 * dishNamesByType2 :: {OTHER=[seasonal fruit, rice, french fries], FISH=[prawns], MEAT=[chicken]}
		 */		
		
		
		
		//--------------------------------------------------------
		//(3) groupingBy 연계 - 그룹화된 요소 조작 (flatMapping)
		//--------------------------------------------------------
		//groupingBy와 연계해 세 번째 컬렉터를 사용해서 일반 맵이 아닌 flatMap 변환을 수행할 수 있다
		//다음처럼 태그 목 록을 가진 각 요리로 구성된 맵이 있다고 가정하자
		//flatMapping 컬렉터를 이용하면 각 형식의 요리의 태그를 간편하게 추출할 수 있다.
		
		
		Map<String, List<String>> dishTags = new HashMap<>();
		dishTags.put("pork",         Arrays.asList("greasy", "salty"));
		dishTags.put("beef",         Arrays.asList("salty", "roasted"));
		dishTags.put("chicken",      Arrays.asList("fried", "crisp"));
		dishTags.put("french fries", Arrays.asList("greasy", "fried"));
		dishTags.put("rice",         Arrays.asList("light", "natural"));
		dishTags.put("seasonal fruit", Arrays.asList("fresh", "natural"));
		dishTags.put("pizza",        Arrays.asList("tasty", "salty"));
		dishTags.put("prawns",       Arrays.asList("tasty", "roasted"));
		dishTags.put("salmon",       Arrays.asList("delicious", "fresh"));
		
		//각 그룹에 수행한 flatMapping 연산 결과를 수집해서 리스트가 아니라 집합으로 그룹화해 중복 태그를 제거.
		Map<Dish.Type, Set<String>> dishNamesByType3 = 
				specialMenu.stream()
						   .collect(Collectors.groupingBy(Dish::getType
								   , Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream() , Collectors.toSet())));
		
		System.out.println("dishNamesByType3 :: "+dishNamesByType3);
		/*
		 * 출력결과 
		 * dishNamesByType3 :: {OTHER=[natural, greasy, light, fresh, fried], FISH=[roasted, tasty], MEAT=[fried, crisp]}
		 */	
		
		
		
		
		//--------------------------------------------------------
		//(4) groupingBy 연계 - 다수준 그룹화
		//--------------------------------------------------------
		//두 인수를 받는 팩토리 메서드 Collectors.groupingBy를 이용해서 항목을 다수준으로 그룹화할 수 있다.
		//Collectors.groupingBy는 일반적인 분류 함수와 컬렉터를 인수로 받는다.
		//즉，바깥쪽 groupingBy 메서드에 스트림의 항목을 분류할 두 번째 기준을 정의하는 내부 groupingBy를 전달해서 두 수준으로 스트림의 항목을 그룹화할 수 있다

		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = 
				specialMenu.stream()
				.collect(Collectors.groupingBy(Dish::getType, // < 첫 번째수준의분류함수
						 Collectors.groupingBy(dish -> {      // < 두번째수준의분류함수
							if (dish.getCalories() <= 400) {
								return CaloricLevel.DIET;
							} else if (dish.getCalories() <= 700) {
								return CaloricLevel.NORMAL;
							} else
								return CaloricLevel.FAT;
						})));
		
		
		System.out.println("dishesByTypeCaloricLevel :: "+dishesByTypeCaloricLevel);
		/*
		 * 출력결과 
		 * dishesByTypeCaloricLevel :: {OTHER={NORMAL=[french fries], DIET=[seasonal fruit, rice]}, FISH={DIET=[prawns]}, MEAT={DIET=[chicken]}}
		 */	
		
		
		//--------------------------------------------------------
		//(5) groupingBy 연계 - 서브그룹으로 데이터 수집 
		//--------------------------------------------------------
		//이전 예제에서 두 번째 groupingBy 컬렉터를 외부 컬렉터로 전달해서 다수준 그룹화 연산을 구현했다.
		//사실 첫 번째 groupingBy로 넘겨주는 컬렉터의 형식은 제한이 없다.
		//예를 들어 다음 코드처럼 groupingBy 컬렉터에 두 번째 인수로 counting 컬렉터를 전달해서 메뉴에서 요리의
		//수를 종류별로 계산할 수 있다.
		
		Map<Dish.Type, Long> typesCount =
				specialMenu.stream()
				           .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
		
		System.out.println("typesCount :: "+typesCount);
		/*
		 * 출력결과 
		 * typesCount :: {OTHER=3, FISH=1, MEAT=1}
		 */		
		
		//요리의 종류를 분류하는 컬렉터로 메뉴에서 가장 높은 칼로리를 가진 요리를 찾는 프로그램도 다시 구현할 수 있다.
		Map<Dish.Type, Optional<Dish>> mostCaloricByType = 
				specialMenu.stream()
						   .collect(Collectors.groupingBy(Dish::getType
								              ,Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		
		System.out.println("mostCaloricByType :: "+mostCaloricByType);
		/*
		 * 출력결과 
		 * mostCaloricByType :: {OTHER=Optional[french fries], FISH=Optional[prawns], MEAT=Optional[chicken]}
		 */		
		
		
		
		
		
		//--------------------------------------------------------
		//(6) groupingBy 연계 - 컬렉터 결과를 다른 형식에 적용하기
		//--------------------------------------------------------
		//마지막 그룹화 연산에서 맵의 모든 값을 Optional로 감쌀 필요가 없으므로 Optional을 삭제할수 있다.
		//즉，다음처럼 팩토리 메서드 Collators.collectingAndThen으로 컬렉터가 반환한 결과를 다른 형식으로 활용할 수 있다.
		//팩토리 메서드 collectingAndThen은 적용할 컬렉터와 변환 함수를 인수로 받아 다른 컬렉터를 반환한다.
		//반환되는 컬렉터는 기존 컬렉터의 래퍼 역할을 하며 collect의 마지막 과정에서 변환 함수로 자신이 반환하는 값을 매핑한다.
		Map<Dish.Type, Dish> mostCaloricByType1 = 
				specialMenu.stream()
				.collect (Collectors.groupingBy(Dish:: getType    // < 분류함수 
						      ,Collectors.collectingAndThen(
								      Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))  //<감싸인컬렉터 
								      ,Optional::get))); //변환함수 
		
		//이 예제에서는 maxBy로 만들어진 컬렉터가 감싸지는 컬렉터며 
		//변환 함수 Optional::get으로 반환된 Optional에 포함된 값을 추출한다.
		
		System.out.println("mostCaloricByType1 :: "+mostCaloricByType1);
		/*
		 * 출력결과 
		 * mostCaloricByType1 :: {OTHER=french fries, FISH=prawns, MEAT=chicken}
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
					  .collect(Collectors.groupingBy(Person::getAge
							  , Collectors.mapping(Person::getName
							  ,Collectors.toList())));
		System.out.println("서술적 (4)");
		System.out.println(nameOfpeopleByAge);
		
		/*
		 * 출력 결과
		 * 서술적 (4)
			{35=[Greg], 20=[Jhon], 21=[Sara, Jane]}
		 */	
		
		
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
