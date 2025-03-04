package stream.usage.stream_slicing;

public class Transaction {
	private final Trader trader;
	private final int year;
	private final int value;
	private final String referenceCode;

	public Transaction(Trader trader, int year, int value,String referenceCode) {
		this.trader = trader;
		this.year = year;
		this.value = value;
		this.referenceCode = referenceCode;
	}

	public Trader getTrader() {
		return trader;
	}

	public int getYear() {
		return year;
	}

	public int getValue() {
		return value;
	}
	
	
	public String getReferenceCode() {
		return referenceCode;
	}

	public String toString(){
		return "{" + this.trader + "z " +
		"year: "+this.year+", " +
		"value:" + this.value +"}";
		}
		

}
