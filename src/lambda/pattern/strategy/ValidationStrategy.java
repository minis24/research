package lambda.pattern.strategy;

/**
 * 람다식으로 스트레티지 패턴 구현. 
 * 함수형 인터페이스 
 * @author jangkwankim
 *
 */
@FunctionalInterface
public interface ValidationStrategy {
	
	boolean execute (String s);
	
}
