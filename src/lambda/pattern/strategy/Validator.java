package lambda.pattern.strategy;

/**
 * 람다식으로 스트레티지 패턴 구현.
 * @author jangkwankim
 *
 */
public class Validator {

	private final ValidationStrategy strategy;
	
	public Validator(ValidationStrategy v) {
		this.strategy = v;
	}
	
	public boolean validate(String s) {
		return strategy.execute(s);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		//----------------------------------------------
		// Validationstrategy는 함수형 인터 페이스며 Predicate〈String〉과 같은 함수 디스크립터를 갖고 있음을 파악했을 것이다.
		// 따라서 다양한 전락을 구현하는 새로운 클래스를 구현할 필요 없이 람다 표현식을 직접 전달하면 코드가 간결해진다.
		
		Validator numValidator = new Validator( (String s) ->  s.matches("[a-z]+")); // 람다를 직접 전달 
		boolean b1 = numValidator.validate("ssssss");
		
		System.out.println("b1 :: "+ b1);  //b1 :: true
		
		
		Validator lowerCaseValidator = new Validator( (String s) ->  s.matches("\\d+")); // 람다를 직접 전달 
		boolean b2 = lowerCaseValidator.validate("aaaaa");
		
		System.out.println("b2 :: "+ b2);  //b2 :: false
	}
	
}
