package java8.delegate;

import java.math.BigDecimal;
import java.util.function.Function;

public class CalulateNAV {

	private Function<String, BigDecimal> priceFinder;
	
	
	public BigDecimal computeStockWorth(final String ticker/*주식시세표*/, final int shares) {
		return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
	}
}
