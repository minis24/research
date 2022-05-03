package jni.hellojni;

public class JNI {

	
	native void printHello();
	native void printString(String str);
	
	
	static{
		System.loadLibrary("ISoT_JNI");
	}
	
	public static void main(String[] args) {

		HelloJNI myJNY = new HelloJNI();
		
		/*************************************************************************************************************************************/
		// 3. 네이티브 메서드 호출
		//   네이티브 메서드를 호출하여 실제 JNI 네이티브 함수를 호출한다.
		/*************************************************************************************************************************************/
		myJNY.printHello();
		myJNY.printString("HELLO World from printString fun");
	}
	
}
