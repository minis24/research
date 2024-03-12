package lambda;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Stream_stream1 {

	public static void main(String[] args) {
		

		
		
		
		
		List<String> list = Arrays.asList("Larry", "Moe", "Curly","4423422","sdsfsdfsdfs");
		System.out.println(list);   //[Larry, Moe, Curly]
		
		
		//기존 방식 코딩
		int count = 0;
		for (String w : list){
			if(w.length() > 2){
				count ++;
			}
		}
		System.out.println(count);
	 
		
		//스트림 연산
		long cnt = list.stream().filter( w -> w.length() > 2).count();
		System.out.println("CNT :"+ cnt);
		
		
		//스트림은 '어떻게가 아니라 무엇을' 원칙을 따른다. 위의 예제에서는 무엇을 해야하는지 기술한다
		// (긴 단어를 골라내고 갯수를 카운트 한다.)
		
		//스트림을 이용하여 작업할때는 3단계로 처리한다.
		//1. 스트림 생성
		//2 초기 스트림을 다른 스트림으로 변환하는 중간연산을 하나 이상 설정
		//3. 결과를 산출하기 위한 최종연산
		//   --> 최종 연산은 앞선 지연 연산들의 실행을 강제한다. 이후에는 스트림을 더이상 사용 할 수 없다. 
	
		
		
		/**********************************************************************************************************/
		//1. 스트림 생성
		/**********************************************************************************************************/
		// 자바 8에서 Collection 인터페이스에 추가된 stream() 메서드를 이용해 컬렉션을 스트림으로 변환할 수 있다.
		// 배열이 있다면 stream()메서드 대신 정적 메서드 Stream.of 메서드를 사용한다.
		// of 메서드는 가변인자를 받기 때문에 인자 갯수가 몇개든 스트림을 생성할 수 있다.
		
		//(ex :배열)
		String[] str_arr = {"asdfasdf","gggggggg","werrrrrrrr"};
		Stream arr_stream = Stream.of(str_arr);
		
		//(ex : 가변인자)
		Stream<String> str_stream = Stream.of("gennsdf","ssdfas","d","aq","weqqtrrrrwqweq");
		
		//(ex : 배열의 일부에서 스트림 생성)
		int from = 0;  //시작위치
		int to   =2;   //종료위치
		Stream arr_stream1 = Stream.of(str_arr,from,to);
		
		//(ex : 요소가 없는 스트림 생성)
		Stream<String> stream1 = Stream.empty();		
		Stream<String> stream2 = Stream.<String>empty();  //제네릭 타입<String> 과 같다.
		
		
		//(ex : 무한 스트림 생성)
		// generate() 메서드는 인자없는 함수를 받는다.
		//  ==> Supplier<T>의 인터페이스의 인스턴스
		// 스트림의 값이 필요할 때는 이 함수를 호출해서 값을 생산한다.
//        long ccnt = echos.filter(w -> w.length() > 1).count();  

        //(ex : 상수값들의 스트림)
		Stream<String> echos = Stream.generate(() -> "Echo");	
		//echos.forEach(System.out::println);
		
		
		//(ex : 난수값들의 스트림)
        Stream<Double> random =  Stream.generate(Math::random);
        //random.forEach(System.out::println);
        //0,1,2,3,4....와 같은 무한 수열을 만들어 낼려면 iterate()메서드를 사용한다.
        //iterate()메서드는 시드(seed) 값과 함수를 받는다.(함수는 UnarayOperator<T>) 
        //이 받은 함수를 이전 결과에 반복적으로 적용한다.
        //(ex : 등차수열)
        Stream<BigInteger> itegers = Stream.iterate(BigInteger.ZERO, n->n.add(BigInteger.ONE));
        //첫번째 요소는 시드값인(0) 이다. 두번째 요소는 f(seed) 1이다
        
        System.out.println(BigInteger.ZERO.add(BigInteger.ONE));  //1
        System.out.println(BigInteger.ONE.add(BigInteger.ONE));   //2
        System.out.println(BigInteger.TEN.add(BigInteger.ONE));   //11
        
        
        
        //자바8에서는 스트림을 리턴하는 다수의 메서드를 추가했다.
        //Pattern 클래스는 정규표현식을 이용해 CharSequence를 분리하는 splitAsStream 메서드를 포함한다.
        //(ex : 문자열을 단어로 분리)
        String contents = "abc def aaaee sfsdf";
        Stream<String> words = Pattern.compile("[\\P{L}]+").splitAsStream(contents);
        long cccnt = words.filter(w->w.length() >4).count();
        System.out.println(cccnt);
        
        
        //Files.lines 메서드는 파일에 있는 모든 행의 스트림을 리턴한다.
        //Stream 인터페이스는 AutoCloseable을 슈퍼인터페이스로 둔다.
        //따라서 스트림에 close 메서드를 호출할때  하부파일또한 닫힌다.
        //이를 확인하려면 자바 7부터 제공하는 try with resources 문을 사용하는 것이 가장 좋다.
        
//        try(Stream<String> lines = Files.lines(path)){
        	//lines를 이용해 작업을 수행한다.
//        }
        
        
		/**********************************************************************************************************/
		//2. 스트림 변환
		/**********************************************************************************************************/
        
        
        
        
        
        
	}
	
}
