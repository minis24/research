package stream.make;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class empty {

	public static void main(String[] args) {
		//---------------------------------------------
		//null이 될 수도 있는 객체를 스트림화
		//---------------------------------------------
		// Java 9 이상에서 추가된 Stream.ofNullable 은 매개변수의 값이 null이 아니면 스트림을 생성한다.
		// null인 경우 Stream.empty 를 반환하여 빈 스트림을 생성한다.
		
		
		//예를 들어 System.getProperty는 제공된 키에 대응하는 속성이 없으면 null을 반환.
		//이런 메소드를 스트림에 활용하려면 다음처럼 null을 명시적으로 확인해야 했다
		String homeValue =  System.getProperty("home");
		Stream<String> homeValueStream1 = homeValue == null ? Stream.empty() : Stream.of(homeValue);
		
		//Stream.ofNullable을 이용해 다음처럼 코드를 구현할 수 있다.
		Stream<String> homeValueStream2 =
				Stream.ofNullable(System.getProperty("home"));
		
		
		
		
		
		
		Map<String, List<String>> map = new HashMap<>();
		map.put("metropolitan", Arrays.asList("Seoul", "Incheon"));
		map.put("Jeolla", Arrays.asList("Gwangju"));
		map.put("Gyeongsang", Arrays.asList("Ulsan", "Daegu", "Busan"));

		
		
		Stream<String> cityNamesStream =
		        Stream.ofNullable(map.get("metropolitan")
		        		.stream()
		        		.collect(Collectors.joining()));
		cityNamesStream.forEach(System.out::println); // SeoulIncheon

		//출력 결과
		/*
		 * SeoulIncheon
		 */
		
		cityNamesStream = Stream.ofNullable(map.get("Gangwon")
						.stream()
						.collect(Collectors.joining()));
		cityNamesStream.forEach(System.out::println); // 빈 스트림
		
		//출력 결과
		/*
		 * MODERN 
			JAVA 
			IN 
			ACTION
		 */
	}
}
