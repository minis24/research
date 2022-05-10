package java8.delegate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * Function 
 * Function<Strudent, String> fc = t -> { return t.getName(); }
 *   -->  Student를 매개값으로 하여 String 타입을 리턴한다.
 *   -->  참조변수 t를 활용하여 값을 얻는다.

 * ToIntFunction<Strudent> fc = t -> { return t.getScore(); }
 *   -->  Student를 매개 값으로 하여 
 *   -->  인터페이스에 명시 된 것으로 int 타입을 리턴한다.
 */
//Function 의 apply() 함수형 인터페이스 : 매개값을 리턴값으로 매핑(타입변환)하는 특징을 갖고 있다.

public class Example_apply {

	private static List<Student> list 
	= Arrays.asList(new Student("피카츄", 90, 96), 
			        new Student("파이리", 95, 93));

	// Student를 매개 값으로 String을 리턴
	public static void printString(Function<Student, String> fc) {
		for (Student st : list) {
			// Student st = String으로 매핑 되어 리턴
			System.out.println(fc.apply(st) + " ");
		}
		System.out.println();
	}

	// 점수를 출력
//	public static void printInt(ToIntFunction<Student> function) {
//		for (Student st : list) {
//			System.out.println(function.applyAsInt(st) + " ");
//		}
//		System.out.println();
//	}
	// 위와 같은 결과
	public static void printInt(Function<Student, Integer> fc) {
		for (Student st : list) {
			System.out.println(fc.apply(st));
		}
	}

	// 평균
	public static void printdouble(ToDoubleFunction<Student> fc) {
		for (Student st : list) {
			System.out.println(fc.applyAsDouble(st) + " ");
		}
		System.out.println();
	}

	// 메인
	public static void main(String[] args) {

		/*****************************************************************/
		// apply() 메서드 예시
		/*****************************************************************/
		System.out.println("==================================================");
		System.out.println("(1) apply() 메서드 예시  ");
		System.out.println("==================================================");
		Function<Student, String> fc = t -> {
			return t.getName();
		};
		
		Function<Student, Integer> fc2 = t -> {
			return t.getNumber();
		};
		
		Student st = new Student("초록",1);
		System.out.println(fc.apply(st));    // 초록
		System.out.println(fc2.apply(st));   // 1
		
		
		
		
		System.out.println(" [ 학생 이름 ] ");
		// t = 매개변수
		// 괄호 생략 가능
		printString((t) -> { return t.getName(); });
		// = 학생 객체(st)를 매개 값으로 주면 학생의 이름을 리턴하겠다.

		System.out.println(" [ 영어 점수 ] ");
		printInt(t -> t.getEngScore());

		System.out.println(" [ 수학 점수 ] ");
		printInt(t -> t.getMathScore());

		System.out.println(" [ 평균 점수 ] ");
		printdouble(t -> t.getAvg() / list.size());

	}
}
