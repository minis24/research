package lambda.string.comparator;

public class Person {

	private final String name;
	private final int age;
	
	
	public Person(final String name , final int age) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age = age;
	}


	public String getName() {
		return name;
	}


	public int getAge() {
		return age;
	}
	
	
	public int ageDifference(final Person other) {
		return this.age - other.age;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s - %d" , name,age);
	}



	
	
}
