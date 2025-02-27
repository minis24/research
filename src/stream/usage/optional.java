package stream.usage;

import java.util.Arrays;
import java.util.List;

import stream.usage.stream_slicing.Dish;

public class optional {

	
	public static void main(String[] args) {
		
		
		List<Dish> menu = Arrays.asList(
				new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER));

		
		
		//---------------------------------------------
		// Optional<T> 클래스(java.util.Optional) :: 값의 존재나 부재 여부를 표현하는 컨테이너 클래스다
		//---------------------------------------------
		// (1) isPresent()는 Optional이 값을 포함하면 참(true)을 반환하고，값을 포함하지 않으면 거짓 (false) 을 반환한다
		// (2) ifPresent(Consumer<T> block) 은 값이 있으면 주어진 블록을 실행한다
		// (3) T get ()은 값이 존재하면 값을 반환하고，값이 없으면 NoSuchElementException을 일으킨다
		// (4) T get ()은 값이 존재하면 값을 반환하고，값이 없으면 NoSuchElementException을 일으킨다
		
		
		
		/*
		 * 값이 있으면 출력되고
		 * 값이 없으면 아무 일도 일어나지 않는다
		 */
		menu.stream()
		.filter(Dish::isVegetarian)
		.findAny()  // <-- Optiona<Dish> 반환
		.ifPresent(dish -> System.out.println(dish.getName()));
		
		
		//-------------------------------------------
		//findAny()
		//-------------------------------------------
		//findAny 메서드는 현재 스트림에서 임의의 요소를 반환한다
		//스트림 파이프라인은 내부적으로 단일 과정으로 실행할 수 있도록 최적화된다.
		//즉，쇼트서킷을 이용해서 결과를 찾는 즉시 실행을 종료한다
		
		
		//컬렉션의 첫번째 값을 추출하며, Optional 타입객체를 리턴함.
        // --> 결과가 없는 경우 유용
		  //isPresent() 메서드 : 객체가 존재하는지 알아보고
        // get()메서드로 현재값을 얻어온다.
		  // orElse()메서드롤 놓친인스턴스에 대해 대신할 값을 제안할 수 있다.
	}
}
