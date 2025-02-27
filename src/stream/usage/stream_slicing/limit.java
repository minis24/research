package stream.usage.stream_slicing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class limit {
    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));




        //-------------------------------------------
        // 스트림 축소 :: limit 적용 
        //-------------------------------------------
        //스트림은 주어진 값 이하의 크기를 갖는 새로운 스트림을 반환하는 limit(n) 메서드를 지원한.
        //스트림이 정렬되어 있으면 최대 요소 n개를 반환할 수 있다.
        //예를 들어 다음처럼 300칼로리 이상의 세 요리를 선택해서 리스트를 만들 수 있다.

        //filter와 limit를 조합한 모습을 보여준다.
        // 프레디케이트와 일치하는 요소를 선택한 다음에 즉시 결과를 반환한다.
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());


    }
}
