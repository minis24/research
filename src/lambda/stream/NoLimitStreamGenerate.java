package lambda.stream;

import java.math.BigDecimal;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import crypto.ByteUtils;

public class NoLimitStreamGenerate {

	/**
	 * [ Lambda 의 기본 틀 ]
	 * Predicate    : (T -> boolean)    -> 주로 필터에 사용
	 * Supplier     : (() -> T)         -> 만드는놈(객체 생성)
	 * Consumer     : (T -> void)       -> 쓰는놈(실행에 사용)
	 * Function     : (T -> R)          -> From 에서 뭔가를 To 로 만들어 넘김
	 */
	public static void main(String[] args) {
		
		
		//-----------------------------------------------
		// 무한한 정수 스트림 생성 
		//-----------------------------------------------
		System.out.println("-----------------------------------------");
		System.out.println("1. 무한 정수 스트림 생성");
		System.out.println("-----------------------------------------");
		
		//IntStream 상호 작용.
		// * 파리미터 2개
		//   1) 초기값이라고도 하는 씨앗, 스트림의 첫 번째 요소가 되고 기능 새로운 요소를 생성하기 위해 이전 요소에 적용됩니다. 
		//즉, 스트림의 다음 요소 값은 해당 메서드를 스트림의 이전 요소에 적용한 결과에 따라 결정됩니다.
		//   2) 오퍼레이터 함수 
		IntStream.iterate(0,i -> i+1)
			.skip(0) // 처음 몇개의 스트림 요소를 버림 (인자값이  포함됨)
			.limit(11) //==> 스트림의 크기를 제한(인자값이 포함 안됨 )			
			.filter(i -> i%2 == 0)
			.forEach(System.out::println);
		
		int sum = IntStream.iterate(0,i -> i+1)
				.skip(0) // 처음 몇개의 스트림 요소를 버림 (인자값이  포함됨)
				.limit(11) //==> 스트림의 크기를 제한(인자값이 포함 안됨 )			
				.filter(i -> i%2 == 0)
				.sum();
		
		System.out.println(sum);
		
		
		// 출력결과 ::  0,1,2,3,4,5,6,7,....,100 ,.....-2147483648,-2147483647
		// ==> 100까지만 출력되길 기대했는데...오버플로우 난듯..
		// ==> limit(), skip() 함수 기능을 적용하여 오버플로우 해결 
		
		
		//-----------------------------------------------
		// 무한한 임의 난수 스트림 생성 
		//-----------------------------------------------
		System.out.println("-----------------------------------------");
		System.out.println("2. 무한 임의 난수 스트림 생성");
		System.out.println("-----------------------------------------");

		Random random = new Random();
		int limit = 5;
		int start = 0, end = 10;
		
		//0과 10사이의 임의의 정수의 무한 스트림을 생성
		// 첫번째 파라미터로 총 갯수(limit) 전달 
		random.ints(limit ,start,end+1)
			.forEach(System.out::println);
		
		

		
		
		
		
		
		//-----------------------------------------------
		// 무한한 정수 스트림 생성 - IntStream.generate() 방식
		//-----------------------------------------------
		System.out.println("-----------------------------------------");
		System.out.println("3. 무한 정수 스트림 생성 - IntStream.generate() 방식");
		System.out.println("-----------------------------------------");
		
		AtomicInteger counter = new AtomicInteger();
//		IntStream.generate(counter::incrementAndGet)
//			.forEach(System.out::println);
//		
		
		
//		Stream.generate(() -> "Echo")
//			.forEach(System.out::println);
		
		/*출력 결과 
		 	Echo
			Echo
			Echo
			Echo
			Echo
			...
		 */
		
		System.out.println("-----------------------------------------");
		System.out.println("가변 객체");
		System.out.println("-----------------------------------------");
		BigDecimal a = new BigDecimal(0);
		BigDecimal b = new BigDecimal(1);
		System.out.println("1 :: " + a);
		System.out.println("2 :: " + b);
		
		
		System.out.println("b :: "+ b);
		a = b;
		b = new BigDecimal(5) ;
		
		
		System.out.println("3 :: " + a);
		System.out.println("4 :: " + b);
		
		
		System.out.println("-----------------------------------------");
		System.out.println("불변 객체");
		System.out.println("-----------------------------------------");
		Integer a1 = 0;
		Integer b1 = 1;
		System.out.println("1 :: " + a1);
		System.out.println("2 :: " + b1);
		
		
		System.out.println("b1 :: "+ b1);
		a1 = b1;
		
		++ b1 ;
		
		System.out.println("3 :: " + a1);
		System.out.println("4 :: " + b1);
		
		
		
		System.out.println("b :: "+ b);
		System.out.println(System.identityHashCode(b));
		
		
	      Object obj = new Object();//참조변수
	        System.out.println(obj);// java.lang.Object@50f8360d (주소값)
	        System.out.println(obj.toString());
	        // Object 클래스는 모든 클래스의 최고 조상 클래스
	        // print ()메서드 안에 참조변수가 들어가면 주소값이 출력이 된다.
	        // 즉, toString()가 자동 호출이 된다.
		
		
		
		
	
		
		
	}
}
