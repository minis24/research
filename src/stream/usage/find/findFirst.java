package stream.usage.find;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class findFirst {

	public static void main(String[] args) {
		//----------------------------------------
		// 첫 번째 요소 찾기 (findFirst())
		//----------------------------------------
		//숫자 리스트에서 3으로 나누어떨어지는 첫 번째 제곱값을 반환
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

		Optional<Integer> firstSquareDivisibleByThree = 
				someNumbers
					.stream().map(n -> n * n)
					.filter(n -> n % 3 == 0)
					.findFirst(); // 9
		
		//----------------------------------------
		// findFirst와 findAny는 언제 사용하나?
		//----------------------------------------
		// 그런데 왜 findFirst와 findAny 메서드가 모두 필요할까? 바로 병렬성 때문이다.
		// 병렬 실행에서는 첫 번째 요소를 찾기 어렵다.
		// 따라서 요소의 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.
	
		

		
	}
}
