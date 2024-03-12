package lambda.delegate;

public class Student {
	private String name;
	private int number;
	private int engScore;
	private int mathScore;
	
	public Student(String name, int engScore, int mathScore) {
		this.name = name;
		this.engScore = engScore;
		this.mathScore = mathScore;
	}
	
	
	public Student(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public int getNumber() {
		return number;
	}
	public int getEngScore() {
		return engScore;
	}
	public void setEngScore(int engScore) {
		this.engScore = engScore;
	}
	public int getMathScore() {
		return mathScore;
	}
	public void setMathScore(int mathScore) {
		this.mathScore = mathScore;
	}
	public int getAvg() {
		// TODO Auto-generated method stub
		return getEngScore() + getMathScore();
	}
}