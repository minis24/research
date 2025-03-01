package stream.collect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import stream.usage.stream_slicing.Dish;

public class counting {

	public static void main(String[] args) {
		List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("french fries", true, 530, Dish.Type.OTHER));

		// --------------------------------------------------
		// 첫 번째 예제로 counting()이라는 팩토리 메서드가 반환하는 컬렉터로 메뉴에서 요리 수를 계산한다.
		// --------------------------------------------------
		long howManyDishes = specialMenu.stream().collect(Collectors.counting());

		// 다음처럼 불필요한 과정을 생략할 수 있다.
		long howManyDishes2 = specialMenu.stream().count();
		
	}
}
