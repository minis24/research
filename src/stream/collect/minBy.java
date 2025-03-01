package stream.collect;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class minBy {

	public static void main(String[] args) {
		List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER));

		// --------------------------------------------------
		//스트림값에서 최댓값과 최솟값 검색
		// --------------------------------------------------
		// 메뉴에서 칼로리가 가장 높은 요리를 찾는다고 가정하자.
		//Collectors.maxBy, Collectors.minBy 두 개의 메서드를 이용해서 스트림의 최댓값과 최솟값을 계산할 수 있다
		//두 컬렉터는 스트림의 요소를 비교하는 데 사용할 Comparator를 인수로 받는다
		//다음은 칼로리로 요리를 비교하는 Comparator 를 구현한 다음에 Col lectors. maxBy 로 전달하는 코드다.
	
		Comparator<Dish> dishCaloriesComparator =
				Comparator.comparingInt(Dish::getCalories);
		
		Optional<Dish> mostCalorieDish =
				specialMenu.stream()
				.collect(Collectors.maxBy(dishCaloriesComparator));
		
	}
}
