package stream.usage.stream_slicing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class skip
 {
    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));




        //-------------------------------------------
        // 스트림 요소 건너뛰기 :: skip 적용 
        //-------------------------------------------
        //스트림은 처음 n개 요소를 제외한 스트림을 반환하는 skip(n) 메서드를 지원한다
        //n개 이하의 요소를 포함하는 스트림에 skip(n) 을 호출하면 빈 스트림이 반환된다
        // limut(n)과 skip(n) 은 상호 보완적인 연산을 수행한다
        // 예를 들어 다음 코드는 300칼로리 이상의 처음 두 요리를 건너뛴 다음에 300칼로리가 넘는 나머지 요리를 반환한다.
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());


    }
}
