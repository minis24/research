package java8.string;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;

public class strComparator implements Comparator<String> {

	@Override
	public int compare(String firstParam, String secondParam) {
		return Integer.compare(firstParam.length(),secondParam.length());
	}

	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		

		
		String a = new String("aaaaaa".getBytes("EUC-KR"));
		System.out.println(a);
		System.out.println(new String(a.getBytes("UTF-8"),"EUC-KR"));
		
		
		
		String[] strArr = {"a","b","cc","dddd","b","gggggggggggg","s"};
		
		Arrays.sort(strArr,(String first,String second) -> Integer.compare(first.length(), second.length()));
//		Arrays.sort(strArr,String::compareToIgnoreCase);
//		"a".compareToIgnoreCase("b");
//		
		Comparator<String> comp0 = (String first,String second) -> first.compareToIgnoreCase(second);
		Arrays.sort(strArr,comp0);
		
		
		
		
		for(int i = 0 ; i < strArr.length ; i++){
			System.out.println(strArr[i]);
		}
		
		
		System.out.println("before sort");
		for(int i = 0 ; i < strArr.length ; i++){
			System.out.println(strArr[i]);
		}
		
		System.out.println();
		System.out.println();
		
		Arrays.sort(strArr, new strComparator());
		System.out.println("after sort");
		for(int i = 0 ; i < strArr.length ; i++){
			System.out.println(strArr[i]);
		}
		
		
		
		//단순한 코드블록 
		//변수들의 명세와 함께 코드를 전달
		Comparator< String> comp1 = (String first, String second) -> Integer.compare(first.length(),second.length());
		Arrays.sort(strArr, comp1);
		
		//표현식 하나로는 표현할 수 없는 계산을 수행한다면 중괄호를 사용하여 블록화 한다.
		Comparator< String> comp2 =  (String first, String second) -> {
			if(first.length() < second.length()){ 
				
				return -1;
			}
			else if(first.length() > second.length()){
				return 1;
			}
			else {
				return 0;
			}
		};
		
		
		//람다 표현식이 파라미터를 받지 않으면 빈괄호를 사용한다.
		Runnable r = () -> {
			for(int i = 0 ; i < 1000 ; i++){
				System.out.println(i);
			}
		};
		
		
		Thread t1 = new Thread( () -> {
			for(int i = 0 ; i < 1000 ; i++){
				System.out.println(i);
			}
		});
		t1.start();
		
		
		Thread t2 = new Thread(r);
		t2.start();
		
		
		
		
		//람다 표현식의 파라미터 타입을 추정할 수 있다면 타입을 생략 할 수 있다.
		Comparator< String> comp3 = (String first, String second) -> Integer.compare(first.length(),second.length());
		Comparator< String> comp4 = (first, second) -> Integer.compare(first.length(),second.length());
	
	}
}
