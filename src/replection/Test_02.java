package replection;

import java.lang.reflect.Method;
import java.util.logging.Logger;


public class Test_02 {

	public int add(int a, int b) {
		return a + b;
	}

	public static Logger loggerMain = Logger.getLogger("logging_van");

	public static void main(String args[]) {

		
		loggerMain.info("dddddddddddddddddddddddddddddd");
		try {

			/**********************************************************************************************/
			// 클래스 획득
			/**********************************************************************************************/
			Class cls = Class.forName("com.initech.icsl.test.Test_02");
			// Class cls = new Test_01().getClass();

			/**********************************************************************************************/
			// 클래스에서 메서드 획득
			// 파라미터 ( 메서드 명 , 파라미터 타입 배열 )
			/**********************************************************************************************/
			Class argType[] = new Class[2];
			argType[0] = Integer.TYPE; // int
			argType[1] = Integer.TYPE; // int
			Method method = cls.getMethod("add", argType);

			/**********************************************************************************************/
			// 실행할 인스턴스 생성
			/**********************************************************************************************/
			Test_02 instance = new Test_02();

			/**********************************************************************************************/
			// 파라미터 생성
			/**********************************************************************************************/
			Object arglist[] = new Object[2];
			arglist[0] = new Integer(37);
			arglist[1] = new Integer(47);

			/**********************************************************************************************/
			// 호출
			/**********************************************************************************************/
			Object retobj = method.invoke(instance, arglist);
			Integer retval = (Integer) retobj;
			System.out.println(retval.intValue());
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

}
