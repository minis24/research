package stream.usage.stream_slicing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class dropWhile {
    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));




        //-------------------------------------------
        // 320 칼로리 이하의 요리 선택
        //-------------------------------------------
        // ==> 위의 리스트에서 칼로리를 보면 이미 칼로리 순으로 정렬되어 있다.
        // ==> filter 연산을 이용하면, 전체스트림을 반복 하면서 각 요소에 프레디케이트를 적용하게 되는데
        // ==> 따라서 이미, 리스트가 정렬되어 있다는 사실을 이용해 320칼로리보다 크거나 같은 요리가 나왔을때
        // ==> 반복 작업을 중단 시킬 수 있다.        
        // ==> 이때, takeWhile연산을 이용해서 처리할 수 있다.
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());



        //-------------------------------------------
        // dropWhile 연산 적용
        //-------------------------------------------
        // dropWhile연산은 takeWhile 연산과 정반대의 작업을 수행한다. 
        // dropWhile 은 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
        // 프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.
        // 이때, dropWhile은 무한한 남은 요소를 가진 무한 스트림에서도 동작한다.
        List<Dish> slicedMenul = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());


                
    }
}
