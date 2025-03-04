package stream.usage.find;

public class findAny {

	public static void main(String[] args) {
		//findAny 메서드는 현재 스트림에서 임의의 요소를 반환한다
		//스트림 파이프라인은 내부적으로 단일 과정으로 실행할 수 있도록 최적화된다.
		//즉，쇼트서킷을 이용해서 결과를 찾는 즉시 실행을 종료한다
		
		
		//----------------------------------------
		// findFirst와 findAny는 언제 사용하나?
		//----------------------------------------
		// 그런데 왜 findFirst와 findAny 메서드가 모두 필요할까? 바로 병렬성 때문이다.
		// 병렬 실행에서는 첫 번째 요소를 찾기 어렵다.
		// 따라서 요소의 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.
	
		
	}
}
