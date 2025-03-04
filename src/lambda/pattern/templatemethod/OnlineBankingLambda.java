package lambda.pattern.templatemethod;

import java.util.function.Consumer;

//다음은 온라인 뱅킹 애플리케이션의 동작을 정의하는 추상 클래스다.
//processcustomer 메서드는 온라인 뱅킹 알고리즘이 해야 할 일을 보여준다.
// (1) 우선 주어진 고객 ID를 이용해서 고객을 만족시켜야 한다.
// (2) 각각의 지점은 OnlineBanking 클래스를 상속받아 makeCustomerHappy 메서드가 원하는 동작을 수행하도록 구현할 수 있다.
public  class OnlineBankingLambda {

	//----------------------------------
	//원래 코드 
	//----------------------------------
//	public void processCustomer(int id ) {
//		Customer c = Database.getCustomerWithId(id);
//		makeCustomerHappy(c);
//	}
	
	//----------------------------------
	//람다 표현식 사용
	//----------------------------------
	//이전에 정의한 makeCustomerHappy의 메서드 시그니처와 일치하도록 Consumer〈Customer> 형
	//식을 갖는 두 번째 인수를 processcustomer에 추가한다.
	public void processCustomer(int id ,Consumer<Customer> makeCustomerHappy) {
		Customer c = Database.getCustomerWithId(id);
		makeCustomerHappy.accept(c);
	}



	
	public static void main(String[] args) {
		new OnlineBankingLambda()
				.processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName()));
	}
}

