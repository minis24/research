package java8.string;

public class String_iterate {

	
	public static void main(String[] args) {
		final String str = "abc00c111";
		
		
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
		
		
		//int를 문자로 변환
		//mapToObj: 이 스트림의 요소에 주어진 함수를 적용한 결과로 구성된 객체 값 Stream을 반환합니다.
		//이것은 중간 작업입니다.
		str.chars()
		.mapToObj(ch -> Character.valueOf((char)ch))
		.forEach(System.out::println);
		
		
		//필터링
		//메서드 레퍼런스 : 
		//  - 자바 컴파일러는 메서드가 인스턴스 메서드인지 static 메서드인지 체크한다.
		//  - 체크해서 인스턴스 메서드 이면 합성된 메서드의 파라미터는 호출하는 타깃이 된다.(ex: param.toUppercase())
		//  - 체크해서 static 메서드 이면 합성된 메서드의 파라미터는 이 메서드의 인수로 라우팅 된다.(ex: Character::isDigit)
		str.chars()
		.filter(Character::isDigit)
		.forEach(System.out::println);
		
		
	
	}
}
