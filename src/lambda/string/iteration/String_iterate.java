package lambda.string.iteration;

public class String_iterate {

	
	public static void main(String[] args) {
		final String str = "abc00c111";
		
		//-----------------------------------------
		//chars() :: 스트링을 다양한 방법으로 이터레이션 하는 경우에 사용 
		//   => 컨비니언트 인터널 이터레이터 
		//   => 스트링을 구성하는 개별적 문자들에 대해 오퍼레이션을 적용할 수 있다.
		//-----------------------------------------
		str.chars()
			.forEach(ch -> System.out.println(ch));
		
		
		/*  출력 결과.
		    97
			98
			99
			48
			48
			99
			49
			49
			49
		*/
		
		
		
		//메서드 레퍼런스 사용.
		str.chars()
			.forEach(System.out::println);
		
		
		
		
		//int를 문자로 변환 ==> chars() 가 문자를 표현하는 int의 스트림을 리턴함 
		//mapToObj: 이 스트림의 요소에 주어진 함수를 적용한 결과로 구성된 객체 값 Stream을 반환합니다.
		//이것은 중간 작업입니다.
		str.chars() 
			.mapToObj(ch -> Character.valueOf((char)ch))
			.forEach(System.out::println);
		
		/*
		 	a
			b
			c
			0
			0
			c
			1
			1
			1
	 
		 */
		
		
		
		
		
		
		//필터링
		//메서드 레퍼런스 : 
		//  - 자바 컴파일러는 메서드가 인스턴스 메서드인지 static 메서드인지 체크한다.
		//  - 체크해서 인스턴스 메서드 이면 합성된 메서드의 파라미터는 호출하는 타깃이 된다.(ex: param.toUppercase()) 
		//     ==> 예외는 System.out::println 처럼 이미 설정되어 있는 경우이다.
		//  - 체크해서 static 메서드 이면 합성된 메서드의 파라미터는 이 메서드의 인수로 라우팅 된다.(ex: Character.isDigit(param)
		
		System.out.println("-------------------------");
		str.chars()
		.filter(Character::isDigit)
		.mapToObj(ch -> Character.valueOf((char)ch))
		.forEach(System.out::println);
		/*
		 	0
			0
			1
			1
			1
		*/
		System.out.println();
	
		
		
		
	}
}
