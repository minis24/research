package stream.collect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class joining {

	
	public static void main(String[] args) {

		
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		/**********************************************************************************************************/
		// * 내부적으로 join()메서드는 StringJoiner를 호출하여 
		//   두번째 매개변수인 가변인자에 있는 값들을 첫번째 매겨변수인 콤마로 구분된 하나의 큰 스트링으로 합친다. 
		/**********************************************************************************************************/
		System.out.println(String.join(",", friends));
		
		
		
		//----------------------------------------------------------
		//문자열 연결 (joining)
		//----------------------------------------------------------
		//컬렉터에 joining 팩토리 메서드를 이용하면 스트림의 각 객체에 toString 메서드를 호출해서
		//추출한 모든 문자열을 하나의 문자열로 연결해서 반환한다.
		//joining 메서드는 내부적으로 StringBuilder를 이용해서 문자열을 하나로 만든다.
		
		//다음은 메뉴의 모든 요리명을 연결하는 코드다.
		List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER));

		String shortMenu = 
				specialMenu.stream()
						   .map(Dish::getName)
						   .collect(Collectors.joining());
		
		System.out.println("shortMenu :: "+shortMenu);
		/*
		 * 출력결과 
		 * shortMenu :: seasonal fruitprawnsricechickenfrench fries
		 */
		
		//연결된 두 요소 사이에 구분 문자열을 넣을 수 있도록 오버로드된 joining 팩토리 메서드도 있다.	
		String shortMenu2 = 
				specialMenu.stream()
						   .map(Dish::getName)
						   .collect(Collectors.joining(","));
		
		System.out.println("shortMenu2 :: "+shortMenu2);
		/*
		 * 출력결과 
		 * shortMenu2 :: seasonal fruit,prawns,rice,chicken,french fries
		 */
	}
	
	
		
}
