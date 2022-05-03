package nio2;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Sample_FileAndDirectory {

	
	
	public static void main(String[] args) throws IOException  {
	
		
		/**************************************************************************************************/
		// 파일이나 디렉토리 존재유무 검사
		/**************************************************************************************************/
		//  - Path 인스턴스는 매핑된 파일이나 디렉토리가 물리적으로 없더라도 유효하게 동작하기때문에 검사 필요
		//  - exists() : 파일 및 디렉터리가 있는지 여부 검사 [파일이 있으면 true , 없거나 검사를 수행할 수 없는 경우 false]
		//  - notExists() : 파일 및 디렉터리가 없는지 여부 검사[파일이 없으면 true , 있거나 검사를 수행할 수 없는 경우 false]
		//    --> 두 메서드에 같은 path 를 적용했는데 둘다 false가 떨어진다면 검사를 수행 할 수 없는 경우이다.
		//    --> 예를 들어 어플리케이션이 파일에 접근할 수 없다면 상태를 알 수 없으므로 두메서드는 false
		//    --> 파일이 검사 직후에 삭제될 수도 있기 때문에 파일이 있다고 가리켜도 그 이후의 접근이 성공한다고 보장하지는 않는다.
		//  - 파라미터는 두개 : 첫번째는 Path인스턴스 두번째는 심볼링크를 어떻게 처리할지 지시자
		/**************************************************************************************************/
		Path dirPath = FileSystems.getDefault().getPath("/QryDevSpace/workspace_java_001/NIO.2/resource");
//		Path dirPath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource");
		boolean dirPath_exists = Files.exists(dirPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS,LinkOption.NOFOLLOW_LINKS});
//		boolean dirPath_exists = Files.exists(dirPath,LinkOption.NOFOLLOW_LINKS,LinkOption.NOFOLLOW_LINKS,LinkOption.NOFOLLOW_LINKS);
		boolean dirPath_notExists = Files.notExists(dirPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		
		
		System.out.println("dirPath_exists : " + dirPath_exists);
		System.out.println("dirPath_notExists : " + dirPath_notExists);
		
		
		Path jpgPath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
//		boolean path_exists = Files.exists(jpgPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		
		System.out.println("************* LinkOption ***************");
		System.out.println(new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		System.out.println(LinkOption.NOFOLLOW_LINKS);
		System.out.println(new LinkOption[]{LinkOption.NOFOLLOW_LINKS}[0]);
		System.out.println(new LinkOption[]{LinkOption.NOFOLLOW_LINKS}.length);

		
		
		
		boolean jpg_exists = Files.exists(jpgPath, LinkOption.NOFOLLOW_LINKS);
		boolean jpg_notExists = Files.notExists(jpgPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		
		System.out.println("jpg_exists : " + jpg_exists);
		System.out.println("jpg_notExists : " + jpg_notExists);
		
		/*
		 path_exists : true
		 path_notExists : false
		 dirPath_exists : true
		 dirPath_notExists : false
		*/
		
		
		
		
		
		/**************************************************************************************************/
		// 파일이나 접근성 검사하기
		/**************************************************************************************************/
		//  - 파일에 접근하기전 isReadable(), isWritable(), isExecutable() 메서드를 사용해 파일의 접근레벨을 확인한다.
		//  - 각각 순서대로 읽기가능여부 ,쓰기가능여부, 실행가능여부 검사
		//  - 파일이 실제로 있어야 하면 JVM에서 이를 수행할 권한이 있어야 한다.
		//  - isRegularFile() 메서드를 호출해서 Path가 일반 파일을 가리키는지 검사
		//    : 일반파일이란 특별한 속성이 전혀없는 파일이다.즉, 심볼링크,디렉터리 등이 아닌...텍스트나 바이너리 파일처럼
		//      실제 데이터가 담긴 파일을 말한다.
		//  - 이들 메서드로 접근성을 확인하더라도 파일에 접근할 수 있다고 보장하지는 않는다.
		//    : 검사와 결과를 이용하는 시기의 차이를 뜻하는 time-of-check-to-time-of-use (TOCTTOU,TOCK too 라고 읽음) 
		//      라는 소프트웨어 버그때문.
		/**************************************************************************************************/
		boolean isReadable = Files.isReadable(jpgPath);
		boolean isWriteable = Files.isWritable(jpgPath);
		boolean isExecuteable = Files.isExecutable(jpgPath);
		boolean isRegularFile = Files.isRegularFile(jpgPath);
		
		boolean isdirPathReadable = Files.isReadable(dirPath);
		boolean isdirPathWriteable = Files.isWritable(dirPath);
		boolean isdirPathExecuteable = Files.isExecutable(dirPath);
		boolean isdirPathRegularFile = Files.isRegularFile(dirPath);
		
		System.out.println("isReadable: " + isReadable);
		System.out.println("isWriteable: " + isWriteable);
		System.out.println("isExecuteable: " + isExecuteable);
		System.out.println("isRegularFile: " + isRegularFile);
		
		System.out.println("isdirPathReadable: " + isdirPathReadable);
		System.out.println("isdirPathWriteable: " + isdirPathWriteable);
		System.out.println("isdirPathExecuteable: " + isdirPathExecuteable);
		System.out.println("isdirPathRegularFile: " + isdirPathRegularFile);
		
		
		
		
		
		
		
		/**************************************************************************************************/
		// 두 경로가 같은 파일을 가리키는지 검사하기
		/**************************************************************************************************/
//		Files.isSameFile(path, path2);
		
		
		
		
		
		
		
		/**************************************************************************************************/
		// 1. 루트 디렉토리 목록 나열하기,배열로 만들기
		/**************************************************************************************************/
		FileSystem fileSystem = FileSystems.getDefault(); 
		
		Iterable<Path> rootPaths = fileSystem.getRootDirectories();
		List<Path> list = new ArrayList<Path>();
		for(Path path : rootPaths){
			System.out.println(path);
			/*   
			 C:\
			 D:\
			*/
			list.add(path);
		}
		
		Path[] pathArr = new Path[list.size()];
		
		
//		Path[] pathArr1  = list.toArray(pathArr);
//		for(Path path : pathArr1){
//			System.out.println("arr1 : " + path);
//			
//			/*
//			 * arr : C:\
//			 * arr : D:\
//			 */
//		}
		for(Path path : pathArr){
			System.out.println("arr : " + path);
			
			/*
			 * arr : C:\
			 * arr : D:\
			 */
		}
		
		
		Object[] objArr = list.toArray();
		for(Object path : objArr){
			System.out.println("obj : " + path);
			
			/*
			 * obj : C:\
			 * obj : D:\
			 */
		}
		
		
		/**************************************************************************************************/
		// 2. File배열로 루트 디렉토리 추출
		/**************************************************************************************************/
		File[] roots = File.listRoots();
		for(File root : roots){
			System.out.println(root);
			/*
			 * C:\
			 * D:\
			 */
		}
		
		
		
		
		
		/**************************************************************************************************/
		// 3. 디렉토리 생성
		//  - Files.createDirectory(dir, attrs) 
		//  - Files.createDirectories(dir, attrs)
		//  - dir : path, attrs :  설정할 파일속성의 부가적인 목록(FileAttribute<?>)
		//  - attrs를 넣지 않으면 기본속성으로 생성된다.
		//  - 생성한 디렉토리를 반환한다.
		//  - 이미 같은이름으로 디렉토리가 있다면 예외 처리됨.
		/**************************************************************************************************/
		//Paths.get("") 메서드는 FileSystem.getPath("") 메서드의 축약형이다.
		Path newDirPath = fileSystem.getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir");
		//디렉토리 한개씩 생성하기
//		Path dir = Files.createDirectory(newDir);
		
		
		
		//한번에 모든 디렉토리 생성하기
		//File f = new File("");
		//f.mkdirs();
		
		/*
		 * 디렉토리 시퀀스의 디렉터리 중 하나 이상의 디렉토리가 이미 존재한다면 createDirectories() 메서드는 예외를 던지지 않고
		 * 해당 디렉토리를 건너뛰고 다음디렉토리로 이동한다.
		 * 이 메서드는 디렉토리시퀀스 전체가 아닌 일부 디렉토리만 생성을 실패해도 실패한다.
		 */
//		dir = Files.createDirectories(newDir);
		
		
		
		
		
		
		
		/**************************************************************************************************/
		// 디렉토리의 전체 내용 나열하기
		//  - NIO.2에서는 이터레이션 가능한 스트림,DirectoryStream 을 제공한다.
		//  - DirectoryStream은 Iterable 을 구현한 인터페이스다.
		//  - Files.newDirectoryStream() 메서드를 통해 스트림을 획득한다.
		//  - path 를 인자로 받고 스트림을 반환한다.
		/**************************************************************************************************/
		DirectoryStream<Path> stream = Files.newDirectoryStream(newDirPath);  //필터 적용 안함.
		
		System.out.println("******** 첫번째 ********");
		for(Path path : stream){
			System.out.println("fileName : " +path.getFileName());
			System.out.println("isReadable : " +Files.isReadable(path));
			System.out.println("isRegularFile : " + (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS) ? "REGULAR":"FALSE"));

		}

		
		
		/**************************************************************************************************/
		// 글롭패턴을 적용하여 내용 나열하기
		//  - 특정범주의 내용만 나열해야 할 경우 필터를 적용한다.
		//    (ex : 특정패턴과 이름이 같은 파일이나 디렉토리만 추출)
		//  - NIO.2 에서는 내장 글롭(glob) 필터로 정의한다.글롭패턴은 다른 문자열에 대해 매칭을 수행할 문자열일 뿐이다.
		//    여기서는 디렉토리나 파일의 이름
		/**************************************************************************************************/
		// *  : 0개 이상의 문자
		// ** : * 과 비슷하지만 디렉토리의 경계를 넘을 수 있음.
		// ?  : 정확히 한문자.
		// {} : 콤마로 나뉜 하위패턴의 컬렉션. ex. {A,B,C} 는 A,B,C와 일치
		// [] : 대체할 문자 집합 - 문자를 사용하면 문자의 범위를 표현한다.
		//  --> [0-9] : 숫자
		//  --> [A-Z] :대문자
		//  --> [a-z,A-Z] : 대소문자
		//  --> [12345] : 1,2,3,4,5 중에서 하나
		// []내부에서 "*","?","\" 는 문자 그대로 매칭
		// 이외의 문자는 모두 문자 그대로 매칭
		// "*","?" 를 비롯한 특수문자와 일치하는 것을 찾으려면 문자"\"를 사용해서 이스케이프 해야한다.
		// ex) "\\" 는 문자"\"과 매칭 하며 , "\?"는 문자"?" 과 매칭한다.
		/**************************************************************************************************/
		//glob 는 유닉스에서 유래한 용어로 초기 유닉스에서는 와일드카드문자를 제공하지 않았기 때문에 glob()함수를 사용하였다.
		// 현재는 쉘에서 와일드 카드 문자를 지원한다.
		// glob()는 유닉스 시스템에서 정규표현식으로 패턴과 일치하는 경로이름을 발견하는 함수로 남아있다.
		
		System.out.println("******** 두번째 ********");
		DirectoryStream<Path> filterStream = Files.newDirectoryStream(newDirPath,"*.{png,jpg,xml}");  //필터 적용.확장자가 png,jpg,xml인 파일만
		for(Path path : filterStream){
			System.out.println("fileName : " +path.getFileName());
			System.out.println("isReadable : " +Files.isReadable(path));
			System.out.println("isRegularFile : " + (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS) ? "REGULAR":"FALSE"));
		}
		
		
		
		/**************************************************************************************************/
		// 사용자정의 필터를 적용하여 내용 나열하기
		//  - 글롭패턴 이상의 것이 필요하다면 필터를 직접 작성하면 된다.
		//  - DirectoryStream.Filter<T> 인터페이스를 구현한다.
		//  - 이 인터페이스는  accept() 메서드 하나로 되어 있다.
		/**************************************************************************************************/
		// 1. 사용자정의 필터 구현

		
		
		/**************************************************************************************************/
		// 디렉토리 필터
		/**************************************************************************************************/
		DirectoryStream.Filter<Path> dir_filter = new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
			}
		};
		
		
		/**************************************************************************************************/
		// 파일사이즈 필터
		/**************************************************************************************************/
		DirectoryStream.Filter<Path> size_filter = new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				//1KB보다 큰 파일/디렉토리만 수락
				return Files.size(path) > 1*1024;
			}
		};
		
		
		
		
		
		
		//TimeUnit 샘플
		long millis_1 = 24 * 60 * 60 * 1000;
		long millis_2 = TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS);
		long hours_1 = TimeUnit.HOURS.convert(1, TimeUnit.DAYS);
		
		System.out.println("millis_1 :" + millis_1);
		System.out.println("millis_2 :" + millis_2);
		System.out.println("hours_1 :" + hours_1);
		
		/*
		 *  millis_1 :86400000
			millis_2 :86400000
			hours_1 :24
		 */
		
		
		
		System.out.println("************* FileTime 시간변환 *************");
		//FileTime 시간변환 샘플
		FileTime currFileTime = FileTime.fromMillis(System.currentTimeMillis());
		System.out.println(currFileTime);
		System.out.println("TimeUnit.DAYS : " + currFileTime.to(TimeUnit.DAYS));  //millis 를 days 로 변환 (1970년
		System.out.println("YEARS  : " + currFileTime.to(TimeUnit.DAYS)/365);
		long currentTimeDays = currFileTime.to(TimeUnit.DAYS);
		
		Path filePath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir","ball.png");
		FileTime modifiedFileTime = (FileTime)Files.getAttribute(filePath, "basic:lastModifiedTime", LinkOption.NOFOLLOW_LINKS);
		long modifiedTimeDays = modifiedFileTime.to(TimeUnit.DAYS);
		
		System.out.println("currentTimeDays : "+ currentTimeDays);
		System.out.println("modifiedTimeDays : "+ modifiedTimeDays);
		
		/*
		    currentTimeMillis : 16909
			modifiedTimeMillis : 16909
		 */
		
		
		
		
		
		/**************************************************************************************************/
		// 파일시간 필터
		/**************************************************************************************************/
		DirectoryStream.Filter<Path> time_filter = new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				FileTime currFileTime = FileTime.fromMillis(System.currentTimeMillis());
				long currentTimeDays = currFileTime.to(TimeUnit.DAYS);
				
				
				FileTime modifiedFileTime = (FileTime)Files.getAttribute(path, "basic:lastModifiedTime", LinkOption.NOFOLLOW_LINKS);
				long modifiedTimeDays = modifiedFileTime.to(TimeUnit.DAYS);
				
				//현재날짜로 수정된 파일만 수락하는 필터
				if(currentTimeDays == modifiedTimeDays){
					return true;
				}
				
				return false;
			}
		};
		
		
		
		
		
		

		
		
		
		// 2.생성된 필터 적용
		System.out.println("************ 필터적용 (디렉토리 필터) ************");
		DirectoryStream<Path> dir_filter_Strem = Files.newDirectoryStream(newDirPath, dir_filter) ;
		for(Path path : dir_filter_Strem){
			System.out.println("fileName : " +path.getFileName());

			/*
			 * fileName : f1_0047E7CD6C158AA3B39DBDF5EA3D196D.xml
			 */
		}
		
		System.out.println("************ 필터적용 (size 필터) ************");
		DirectoryStream<Path> size_filter_Strem = Files.newDirectoryStream(newDirPath, size_filter) ;
		for(Path path : size_filter_Strem){
			System.out.println("fileName : " +path.getFileName());

			/*
			 * fileName : f1_0047E7CD6C158AA3B39DBDF5EA3D196D.xml
			 */
		}
		
		
		
		
		
	}
}
