package stream.usage.filtering;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class findFirst {

	
	public static void pickName (final List<String> names, final String startingLetter ) {
		final Optional<String> foundName = 
				names.stream()
					.filter(name -> name.startsWith(startingLetter))
					.findFirst(); //컬렉션의 첫번째 값을 추출하며, Optional 타입객체를 리턴함.
		                          // --> 결과가 없는 경우 유용
								  //isPresent() 메서드 : 객체가 존재하는지 알아보고
		                          // get()메서드로 현재값을 얻어온다.
		 						  // orElse()메서드롤 놓친인스턴스에 대해 대신할 값을 제안할 수 있다.
		
		System.out.println("startingLetter : " + startingLetter +"," + foundName.isPresent() + "," + foundName.get());
		System.out.println("startingLetter : " + startingLetter +"," + foundName.orElse("No name Found"));
	}
	
	
	
	
	public static void main(String[] args) {
		List<String> friends = Arrays.asList("Brian","Nate","Neal","Raju","sara","Scott");
		List<String> editors = Arrays.asList("Briane","Natee","Neale","Rajue","sarae","Scotte");
		List<String> comrades = Arrays.asList("Brianc","Natec","Nealc","Rajuc","sarac","Scottc");
		
		
		
		pickName(friends,"N");
	

	}
}
