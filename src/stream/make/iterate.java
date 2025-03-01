package stream.make;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import stream.usage.stream_slicing.Dish;

public class iterate {

	public static void main(String[] args) {
		//---------------------------------------------
		//함수로 무한 스트림 만들기
		//---------------------------------------------
		// 스트림 API는 함수에서 스트림을 만들 수 있는 두 정적 메서드 
		//Stream.iterate와 Stream.generate를 제공한다.
		//두 연산을 이용해서 무한 스트림，즉 고정된 컬렉션에서 고정된 크기로 스트림을 만들었던 것과는 달리 
		//크기가 고정되지 않은 스트림을 만들 수 있다.
		//iterate와 generate에서 만든 스트림은 요청할 때마다 주어진 함수를 이용해서 값을 만든다.
		//따라서 무제한으로 값을 계산할 수 있다.
		//하지만 보통 무한한 값을 출력하지 않도록 limit(n) 함수를함께 연결해서 사용한다.
		
		//---------------------------------------------
		// iterate 메서드 
		//---------------------------------------------
		// iterate 메서드는 초깃값(예제에서는 0)과 
		// 람다(예제에서는 UnaryOperator<T> 사용)
		// 를 인수로 받아서 새로운 값을 끊임없이 생산할 수 있다.
		//예제에서는 람다 n -> n+2, 즉 이전 결과에 2를 더한 값을 반환한다.
		Stream.iterate(0, n -> n + 2)
			.limit(10)
			.forEach(System.out::println);
		
		
		//---------------------------------------------
		//자바 9의 iterate 메소드는 프레디케이트를 지원한다.
		//---------------------------------------------
		//예를 들어 0에서 시작해서 100보다 크면 숫자 생성을 중단하는 코드를 디음처럼 구현할 수 있다.
		//iterate 메소드는 두 번째 인수로 프레디케이트를 받아 언제까지 작업을 수행할 것인지의 기준으로 사용한다.
		IntStream
			.iterate(0, n -> n < 100, n -> n + 4)
			.forEach(System.out::println);
		
		
		//스트림 쇼트서킷을 지원하는 takeWhile을 이용할 수도 있다.
		IntStream
			.iterate(0, n -> n + 4)
			.takeWhile(n -> n < 100)
			.forEach(System.out::println);
		
		
		
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));



        //-------------------------------------------
        // takeWhile 연산 적용
        //-------------------------------------------
        // takeWhile연산을 이용하면 무한 스트림을 포함한 모든 스트림에 
        // 프레디케이트를 적용해 스트림을 슬라이스 할 수 있다.
        List<Dish> slicedMenul = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        
        
		//우리는 무한한 크기를 가진 스트림을 처리하고 있으므로 limit를 이용해서 명시적으로 스트림의 크기를 제한해야 한다.
		//그렇지 않으면 최종 연산（예제에서는 forEach）을 수행했을 때 아무 결과도 계산되지 않는다.
		//마찬가지로 무한 스트림의 요소는 무한적으로 계산이 반복되므로 정렬하거나 리듀스할 수 없다.
	}
}
