package stream.usage.filtering;

import java.util.Arrays;
import java.util.List;

public class distinct {
	
	/*
	 * 스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다
		(고유 여부는 스트림에서 만든 객체의 hashCode, equals로 결정된다).
		예를 들어 다음 코드는 리스트의 모든 짝수를 선택하고 중복을 필터링한다.
	 */
	

	 public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1 ,2, 1, 3, 3, 2, 4);
		numbers.stream()
			.filter(i -> i % 2 == 0)
			.distinct()
			.forEach(System.out::println);
	 }
}
