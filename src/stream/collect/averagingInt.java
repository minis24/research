package stream.collect;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class averagingInt {

	public static void main(String[] args) {
		List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER));

		// --------------------------------------------------
		//요약연산 
		// --------------------------------------------------
		// 트림에 있는 객체의 숫자 필드의 합계나 평균 등을 반환하는 연산에도 리듀싱 기능이 자주 사용된다.
		// 이러한 연산을 요약연산이라 부른다

		//Collectors 클래스는 Collectors.summinglnt라는 특별한 요약 팩토리 메서드를 제공한다
		// summinglnt는 객체를 int로 매핑하는 함수를 인수로 받는다.
		//summinglnt의 인수로 전달된 함수는 객체를 int로 매핑한 컬렉터를 반환한다.
		//그리고 summinglnt가 collect 메서드로 전달되면 요약 작업을 수행한다.
		
		//다음은 메뉴 리스트의 총 칼로리를 계산하는 코드다.
		int totalCalories = specialMenu.stream()
							.collect(Collectors.summingInt(Dish::getCalories));
		
		
		// --------------------------------------------------
		//이러한 단순 합계 외에 평균값 계산 등의 연산도 요약 기능으로 제공된다.
		// --------------------------------------------------
		//즉，Collectors.averaginglnt, averagingLong, averagingDouble 
		//등으로 다양한 형식으로 이루어진 숫자 집합의 평균을 계산할 수 있다.
		double avgCalories =
				specialMenu.stream()
					.collect(Collectors.averagingInt(Dish::getCalories));
		
		
		
		// --------------------------------------------------
		// summarizingInt
		// --------------------------------------------------
		
		//지금까지 컬렉터로 스트림의 요소 수를 계산하고，최댓값과 최솟값을 찾고,
		//합계와 평균을 계산하는 방법을 살펴봤다.
		//종종 이들 중 두 개 이상의 연산을 한 번에 수행해야 할 때도 있다.
		//런 상황에서는 팩토리 메서드 summarizinglnt가 반환하는 컬렉터를 사용할 수 있다.
		//예를 들어 다음은 하나의 요약 연산으로 메뉴에 있는 요소 수，요리의 칼로리 합계,평균，최댓값,최솟값 등을 계산하는 코드다
	
		//위 코드를 실행하면 IntSummaryStatistics 클래스로 모든 정보가 수집된다
		IntSummaryStatistics menustatistics = 
				specialMenu.stream()
						   .collect(Collectors.summarizingInt(Dish::getCalories));
		
		System.out.println(menustatistics);
		
		/*
		 * 출력결과 
		 * IntSummaryStatistics{count=5, sum=1700, min=120, average=340.000000, max=530
		 */
	}
}
