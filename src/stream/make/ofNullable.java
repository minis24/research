package stream.make;

import java.util.stream.Stream;

public class ofNullable {

	public static void main(String[] args) {
		//---------------------------------------------
		//값으로 스트림만들기
		//---------------------------------------------
		//임의의 수를 인수로 받는 정적 메서드 Stream.of를 이용해서 스트림을 만들 수 있다.
		//예를 들어 다음 코드는 Stream.of로 문자열 스트림을 만드는 예제다.
		//스트림의 모든 문자열을 대문자로 변환한 후 문자열을 하나씩 출력한다.
		
		Stream<String> stream = 
				Stream.of("Modern ", "Java ", "In ", "Action");
		
		stream.map(String::toUpperCase)
			  .forEach(System.out::println);
		
		//출력 결과
		/*
		 * MODERN 
			JAVA 
			IN 
			ACTION
		 */
	}
}
