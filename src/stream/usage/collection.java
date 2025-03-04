package stream.usage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import stream.usage.stream_slicing.Dish;
import stream.usage.stream_slicing.Trader;
import stream.usage.stream_slicing.Transaction;

public class collection {

	
	public static void main(String[] args) {
		
		//고정크기 리스트 생성 (요소 갱신 가능, 요소추가/삭제 불가)
		List<String> numStrList = Arrays.asList("1","2","3","4","5","6","7","8","9");
		
		//갱신 가능 
		numStrList.set(0, "AAA");
		System.out.println(numStrList);
		/*
		 * 출력결과 : [AAA, 2, 3, 4, 5, 6, 7, 8, 9]
		 */
		
		//추가 또는 삭제 불가능
		//==> Exception in thread "main" java.lang.UnsupportedOperationException
		//numStrList.add("10");
		
		
		
		
		//--------------------------------------------------
		// (1) 리스트 팩토리(추가,삭제,갱신 불가 ,null 요소 금지)
		//--------------------------------------------------
		//List.of 팩토리 메소드를 이용해서 간단하게 리스트를 만들 수 있다.
		//==> 고정인수 방식으로 생성 
		List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
		System.out.println(friends);
		/*
		 * 출력결과 : [Raphael, Olivia, Thibaut]
		 */

		//Exception in thread "main" java.lang.UnsupportedOperationException
//		friends.set(0, "AAA");
		//==> 인수가 10개 이상이거나, 아래와 같이 배열을 인수로 설정하면 가변인수 방식으로 생성됨.
		String[] arr = {"Raphael", "Olivia", "Thibaut"};
		List<String> friends1 = List.of(arr);
		System.out.println(friends1);
		/*
		 * 출력결과 : [Raphael, Olivia, Thibaut]
		 */
		
		
		
		//--------------------------------------------------
		// (2) 집합 팩토리 (Set.of)
		//--------------------------------------------------
		//중복된 요소를 제공해 집합을 만들려고 하면 Olivia라는 요소가 중복되어 있다는 설명과 함께
		//IllegalArgumentException이 발생한다.
//		Set<String> friends3 = Set.of("Raphael","Olivia","Olivia");
		//Exception in thread "main" java.lang.IllegalArgumentException: duplicate element: Olivia
//		System.out.println(friends3);
		
		
		
		
		//--------------------------------------------------
		// (3) 맵 팩토리 (toSet)
		//--------------------------------------------------
		//두가지 방법이 있다.
		//첫번째: Map.of 팩토리 메서드에 키와 값을 번갈아 제공. (10개 이하 키쌍을 가진 작은 맵을 만들때 유용)
		Map<String, Integer> ageOfFriends = Map.of("Raphael", 30,"Olivia", 25,"Thibaut", 26);
		System. out. println(ageOfFriends);
		/*
		 * 출력결과 : {Olivia=25, Thibaut=26, Raphael=30}
		 */
		
		//두번째 : 10개 이상의 맵에서는 Map.Entry〈K, V>객체를 인수로 받으며 가변 인수로 구현된 
		//Map.ofEntries 팩토리 메서드를 이용하는 것이 좋다.
		Map<String, Integer> ageOfFriends1 
				= Map.ofEntries(Map.entry("Raphael", 30),
						Map.entry("Olivia", 25),
						Map.entry("Thibaut", 26),
						Map.entry("Thibaut1", 26),
						Map.entry("Thibaut2", 26),
						Map.entry("Thibaut3", 26),
						Map.entry("Thibaut4", 26),
						Map.entry("Thibaut5", 26),
						Map.entry("Thibaut6", 26),
						Map.entry("Thibaut7", 26));
		
		System.out.println(ageOfFriends1);
		/*
		 * 출력결과 : {Thibaut1=26, Olivia=25, Thibaut=26, Thibaut7=26, Thibaut6=26, Thibaut5=26, Thibaut4=26, Raphael=30, Thibaut3=26, Thibaut2=26}

		 */
		
		
		
		
		
		
		
		//--------------------------------------------------
		// (4) removeIf 메서드
		//--------------------------------------------------
		
//		List<Dish> specialMenu = Arrays.asList(
//				new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
//				new Dish("prawns", false, 300, Dish.Type.FISH),
//				new Dish("rice", true, 350, Dish.Type.OTHER),
//				new Dish("chicken", false, 400, Dish.Type.MEAT),
//				new Dish("french fries", true, 530, Dish.Type.OTHER));
		
		List<Dish> specialMenu = new ArrayList<>();
		specialMenu.add(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER));
		specialMenu.add(new Dish("prawns", false, 300, Dish.Type.FISH));
		specialMenu.add(new Dish("rice", true, 350, Dish.Type.OTHER));
		specialMenu.add(new Dish("chicken", false, 400, Dish.Type.MEAT));
		specialMenu.add(new Dish("french fries", true, 530, Dish.Type.OTHER));
		
		//Exception in thread "main" java.util.ConcurrentModificationException
//		for (Dish s : specialMenu) {
//			if(s.getName().startsWith("r")) {
//				specialMenu.remove(s);
//			}
//		}
		
		
		specialMenu.removeIf(dish -> dish.getName().startsWith("r"));

		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(brian, 2011, 300, "AsfasDfasdf"));
		transactions.add(new Transaction(raoul, 2012, 1000, "1asdafas"));
		transactions.add(new Transaction(raoul, 2011, 400, "sadfasdf"));
		transactions.add(new Transaction(mario, 2012, 710, "2sfsdf"));
		transactions.add(new Transaction(mario, 2012, 700, "dsdgsg"));
		transactions.add(new Transaction(alan, 2012, 950, "55sdfasdf"));

		transactions.removeIf(transaction ->
		Character.isDigit(transaction.getReferenceCode().charAt(0)));
		
		System.out.println(transactions);
		/*
		 * 출력결과 : [{Trader:Brian in Cambridgez year: 2011, value:300}
		 * , {Trader:Raoul in Cambridgez year: 2011, value:400}
		 * , {Trader:Mario in Milanz year: 2012, value:700}]
		 */
		
		//--------------------------------------------------
		// (5) replaceAll 메서드
		//--------------------------------------------------
		//List 인터페이스의 replaceAll 메서드를 이용해 리스트의 각 요소를 새로운 요소로 바꿀 수 있다.
		
		
		
		//--------------------------------------------------
		// (6) Map의 정렬기능 
		//--------------------------------------------------
		// 다음 두 개의 새로운 유틸리티를 이용하면 맵의 항목을 값 또는 키를 기준으로 정렬할 수 있다.
		// • Entry.comparingByValue
		// • Entry.comparingByKey
		
		Map<String, Integer> ageOfFriends2 = Map.ofEntries(Map.entry("Raphael", 30),
				Map.entry("Olivia", 25),
				Map.entry("Fhibaut5", 10),
				Map.entry("Ghibaut6", 16),
				Map.entry("Ahibaut", 33),
				Map.entry("Bhibaut1", 54),
				Map.entry("Dhibaut3", 44),
				Map.entry("Chibaut2", 48),
				Map.entry("Ehibaut4", 14),
				Map.entry("Hhibaut7", 26));
		
		
		ageOfFriends2
		.entrySet()
		.stream()
		.sorted(Entry.comparingByKey())  //키로 정렬 
		.forEachOrdered( System.out::println);
		
		/*
		 * 결과 : Ahibaut=33
Bhibaut1=54
Chibaut2=48
Dhibaut3=39
Ehibaut4=14
Fhibaut5=10
Ghibaut6=16
Hhibaut7=26
Olivia=25
Raphael=30
		 */
		
		
		
		ageOfFriends2
		.entrySet()
		.stream()
		.sorted(Entry.comparingByValue())  //값으로 정렬 
		.forEachOrdered( System.out::println);
		/*
		 *결과 : 
		 *Fhibaut5=10
Ehibaut4=14
Ghibaut6=16
Olivia=25
Hhibaut7=26
Raphael=30
Ahibaut=33
Dhibaut3=39
Chibaut2=48
Bhibaut1=54
		 */
		
		
		//--------------------------------------------------
		// (7) getOrDefault 메서드
		//--------------------------------------------------
		//이 메서드는 첫 번째 인수로 키를，
		//두 번째 인수로 기본값을 받으며 맵에 키가 존재하지 않으면 두 번째 인수로 받은 기본값을 반환한다.
		//키가 존재하더라도 값이 널인 상황에서는 getOrDefault가 널을 반환할 수 있다.
		System.out.println(ageOfFriends2.getOrDefault("Ghibaut6", 0));
		/*
		 * 출력결과 : 16
		 */
		
		System.out.println(ageOfFriends2.getOrDefault("AAAAA", 0));
		/*
		 * 출력결과 : 0
		 */
		
		
		Map<String, String> testMap = new HashMap<>();
		testMap.put("AAAA", null);
		
		System.out.println(testMap.getOrDefault("BBBB", "QQQQ"));
		/*
		 * 출력결과 : QQQQ
		 */

		//키가 존재하더라도 값이 널인 상황에서는 getOrDefault가 널을 반환할 수 있다.
		System.out.println(testMap.getOrDefault("AAAA", "QQQQ"));
		/*
		 * 출력결과 : null
		 */
		
		
		//--------------------------------------------------
		// (8) replaceAll 메서드
		//--------------------------------------------------
		//BiFunction을 적용한 결과로 각 항목의 값을 교체한다.
		Map<String, String> favouriteMovies = new HashMap<>();
		favouriteMovies.put("Raphael", "Star Wars");
		favouriteMovies.put("Olivia" ,"james bond");
		favouriteMovies.replaceAll((key, value) -> key + value.toUpperCase());
		System. out. println (favouriteMovies);
		/*
		 * 출력 결과 : {Olivia=OliviaJAMES BOND, Raphael=RaphaelSTAR WARS}
		 */
		
		
		//--------------------------------------------------
		// (9) replace 메서드
		//--------------------------------------------------
		
		//--------------------------------------------------
		// (10) remove 메서드
		//--------------------------------------------------
		//제공된 키에 해당하는 맵 항목을 제거하는 remove 메서드는 이미 알고 있다.
		//자바 8에서는 키가 특정한 값과 연관되었을 때만 항목을 제거하는 오버로드 버전 메서드를 제공한다.
		Map<String, String> favouriteMovies1 = new HashMap<>();
		favouriteMovies1.put("Raphael", "Star Wars");
		favouriteMovies1.put("Olivia" ,"james bond");
		
		favouriteMovies1.remove("Raphael", "Star Wars");
		System.out.println ("remove :: " + favouriteMovies1);
		/*
		 * 출력 결과 : remove :: {Olivia=james bond}
		 */
		//--------------------------------------------------
		// (11) merge 메서드
		//--------------------------------------------------
		
		
		
		
	
		 
	}
}
