package lambda;

public class numCompareTo {

	
	public static void main(String[] args) {
		
		//=========================================================
		// compareTo() : 파라미터로 전달된 객체를 비교하여
		//  - 같으면 0 을 리턴한다.
		//  - 파라미터가 더 크면 -1 을 리턴한다.
		//  - 파라미터가 더 작으면 1 을 리턴한다.
		//=========================================================
		Integer i1 = new Integer(1);
		Integer i2 = new Integer(2);
		Integer i3 = new Integer(3);
		Integer i4 = new Integer(4);
		Integer i5 = new Integer(5);
		
		System.out.println(i1.compareTo(i5));
		System.out.println(i1.compareTo(i4));
		System.out.println(i1.compareTo(i3));
		System.out.println(i1.compareTo(i2));
		
		System.out.println("================");
		System.out.println(i1.compareTo(i1));
		System.out.println("================");
		
		
		System.out.println(i5.compareTo(i1));
		System.out.println(i4.compareTo(i1));
		System.out.println(i3.compareTo(i1));
		System.out.println(i2.compareTo(i1));
	}
}
