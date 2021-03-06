package jni;




public class JNI {

	
	
	/*************************************************************************************************************************************/
	// JNI 구현을 살펴보면 메서드 선언은 자바코드에서 , 선언된 메서드의 실제 구현은 C/C++ 코드에서 이뤄진다.
	//
	// * 네이티브 메서드
	//    --> 자바쪽에서 선언된 메서드를 네이티브 메서드라 정의한다.
	//
	// * 네이티브 함수
	//    --> C/C++ 코드로 실제 구현된 함수를 JNI 네이티브 함수라고 정의한다.
	/*************************************************************************************************************************************/
	
	
	/*************************************************************************************************************************************/
	// 1. 네이티브 메서드 선언
	//    자바 클래스에서 C/C++ 로 작성된 JNI 네이티브 함수와 연결할 메서드를 native 키워드를 이용해서 선언한다.
	//    native 키워드는 자바 컴파일러에게 이 키워드가 선언된 메서드는 실제 자바가 아닌 외부의 다른 언어(예를들면 C/C++ ) 로 
	//    구현되어 있음을 알려주는 역할을 한다.
	//    따라서 HelloJNI 클래스에는 native 로 선언된 printHello,printString 메서드는 자바로 작성된 구현부가 없다.
	/*************************************************************************************************************************************/
	native void random();
	
	
	
	
	/*************************************************************************************************************************************/
	// 2. 네이티브 메서드가 실제로 구현되어 있는 C라이브러리를 System.loadLibrary() 메서드를 호출해서 로딩한다.
	// System.loadLibrary() 메서드는 인자로 넘긴 문자열에 해당하는 네이티브 라이브러리를 로딩한다.
	//  전달 인자 : hellojni   
	//  로드되는 라이브러리 : [윈도우 : hellojni.dll , 리눅스 : hellojni.so]
	/*************************************************************************************************************************************/
	static{
		System.loadLibrary("ISoT_JNI");
	}
	
	public static void main(String[] args) {

		JNI jni = new JNI();
		
		/*************************************************************************************************************************************/
		// 3. 네이티브 메서드 호출
		//   네이티브 메서드를 호출하여 실제 JNI 네이티브 함수를 호출한다.
		/*************************************************************************************************************************************/

		jni.random();
	}


}
