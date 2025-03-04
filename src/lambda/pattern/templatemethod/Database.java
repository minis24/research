package lambda.pattern.templatemethod;

public class Database {

	//DB에서 id 로 고객정보를 조회하는 걸로~
	public static Customer getCustomerWithId(int id) {
		return new Customer(id);
	}
}
