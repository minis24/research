package nio2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class Sample_createFile {

	
	
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
		
		Path dirPath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource");
		boolean dirPath_exists = Files.exists(dirPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		boolean dirPath_notExists = Files.notExists(dirPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		
		System.out.println("dirPath_exists : " + dirPath_exists);
		System.out.println("dirPath_notExists : " + dirPath_notExists);
		
		
		Path jpgPath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
		boolean path_exists = Files.exists(jpgPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		boolean path_notExists = Files.notExists(jpgPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
		
		System.out.println("path_exists : " + path_exists);
		System.out.println("path_notExists : " + path_notExists);
		
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
			 C:₩
			 D:₩
			*/
			list.add(path);
		}
		
		Path[] pathArr = new Path[list.size()];
		
		
		list.toArray(pathArr);
		for(Path path : pathArr){
			System.out.println("arr : " + path);
			
			/*
			 * arr : C:₩
			 * arr : D:₩
			 */
		}
		
		
		Object[] objArr = list.toArray();
		for(Object path : objArr){
			System.out.println("obj : " + path);
			
			/*
			 * obj : C:₩
			 * obj : D:₩
			 */
		}
		
		
		/**************************************************************************************************/
		// 2. File배열로 루트 디렉토리 추출
		/**************************************************************************************************/
		File[] roots = File.listRoots();
		for(File root : roots){
			System.out.println(root);
			/*
			 * C:₩
			 * D:₩
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
		Path newDir = fileSystem.getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir");
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
		Path dir = Files.createDirectories(newDir);
		
		
		
		
		
		
		/**************************************************************************************************/
		// 4. 스트림 [버퍼스트림 / 비버퍼스트림]
		//  대다수 운영 체제 에서 I/O작업은 코스트가 비싸다. 네이티브 API를 호출하기전 운영체제와 애플리케이션 사이에 있는
		//  버퍼에서 데이터 입출력을 처리한다. 이렇게 함으로서 시스템 호출을 수행하는 횟수가 감소해  애플리케이션의 효율성을 증가한다.
		//  - 버퍼 : 운영체제와 버퍼기반 메서드 사이의 메모리공간
		//  - 스트림은 입력소스나 출력 목적지로 쓰인다.(디스크 파일에서 부터 메모리상의 배열까지 무엇이든 될수 있다.)
		//  - 스트림은 문자열,바이트,기본 데이터타입, 로컬화된 문자,객체처럼 다양한 종류의 데이터를 지원한다.
		//  - 비버퍼스트림(unbuffer stream)은 각각의 읽기 요청이나 쓰기요청은 기반 운영체제에서 바로 처리함
		//  - 버버스트림(buffer stream) 버퍼로 알려진 메모리영역에서 데이터를 읽어오며 버퍼가 비어 있을때만 네이티브 입력API를 호출한다.
		//  - 버퍼 출력스트림은 버퍼에 데이터를 쓰며, 버퍼가 꽉 찼을때만 네이티브 출력API를 호출한다.
		//  - 버퍼가 가득차는 것을 기다리지 않고 기록된 것을 비우면 버퍼가 비워졌다고 한다.
		//  - Files.newBufferedReader() 메서드  --> BufferedReader 인스턴스 리턴
		//  - Files.newBufferedWriter() 메서드  --> BufferedWriter 인스턴스 리턴
		
		
		// 5. 표준 열기옵션
		//  - NIO.2부터 생성,읽기,쓰기(그외 파일열기와 관련된 모든것)와 관련된 메서드는 모두 파일 생성이나 여는 방법을 지정하는 
		//    옵션 매개변수 OpenOption을 지원한다.
		//  - 실제로 OpenOption은 Java.nio.file 패키지의 인터페이스이며 , 두 가지 구현체를 가지고 있다.
		//    1) LinkOption클래스[NOFOLLOW_LINK enum 상수정의]
		//    2) StandardOpenOption 클래스에서 다음의 enum 상수 정의
		//       * READ               : 읽기용으로 파일을 연다
		//       * WRITE              : 쓰기용으로 파일을 연다.
		//       * CREATE             : 파일이 없다면 새 파일을 생성한다.
		//       * CREATE_NEW         : 새 파일을 만든다. 파일이 이미 있으면 예외와 함께 실패한다.
		//       * APPEND             : 파일끝에 데이터를 추가한다.(WRITE 나 CREATE 와 함께 사용)
		//       * DELETE_ON_CLOSE    : 스트림을 닫을 때 파일을 삭제한다.(임시파일을 삭제할때 사용)
		//       * TRUNCATE_EXISTING  : 파일을 0바이트로 잘라낸다(WRITE 옵션과 함께 사용)
		//       * SPARSE             : 새로 생성하는 파일이 희소 할 수 있다.
		//       * SYNC               : 파일 내용과 메타 데이터를 기반 저장소 디바이스와 동기화 한다.
		//       * DSYNC              : 파일 내용을 기반 저장소 디바이스와 동기화 한다.
		/**************************************************************************************************/
		
		
		
		
		
		/**************************************************************************************************/
		// 6. 새파일 생성
		//  - Files.createFile() 메서드를 호출하여 수행
		//  - 파라미터로 생성할 파일의 Path 와 파일속성의 옵션목록(FileAttribute<?>)를 받는다.
		//  - 생성된 파일을 리턴한다.
		//  - 파일이 존재하면 FileAlreadyExistException을 던진다.
		/**************************************************************************************************/
		long currentTimeMillis = System.currentTimeMillis();
		Path newFile = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/","create_new_file_" +currentTimeMillis+".txt");
		
		System.out.println("************ Files.createFile(newFile) ************");
		try{
			Files.createFile(newFile);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		
		
		/**************************************************************************************************/
		// 7. POSIX 파일시스템에서 새파일을 특정권한으로 생성
		/**************************************************************************************************/
//		Path posix_newFile = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
//		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
//		FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
		
//		try{
//			Files.createFile(posix_newFile,attr);
//		}catch (IOException e){
//			e.printStackTrace();
//			System.err.println(e);
//		}
		
		
		
		
		
		/**************************************************************************************************/
		// 8. 작은파일 쓰기
		//  - NIO.2 에는 작은 바이너리 파일이나 작은 텍스트 파일을 위한 Files.write()메서드를 제공
		//  - Files.write() 메서드 : 파일을 쓰기용도로 열거나 (파일이 없다면 생성한다.)
		//    기존에 있던 일반 파일을 0바이트 크기로 잘라내기 한다.
		//  - 바이트나 라인을 모두 썼다면 메서드는 파일을 닫는다.(IO에러나 예외가 발생해도 닫는다)
		//  - 이 메서드는 CREATE,TRUNCATE_EXISTING,WRITE 옵션이 있는것처럼 동작
		/**************************************************************************************************/
		Path small_file = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir","ball.png");
		byte[] small_bytes = new byte[]{
				(byte)0x89,(byte)0x50,(byte)0x4e,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x11,(byte)0x12,(byte)0x22,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x11,(byte)0x12,(byte)0x22,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x11,(byte)0x12,(byte)0x22,
				(byte)0x00,(byte)0x02,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x11,(byte)0x12,(byte)0x22,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x11,(byte)0x12,(byte)0x22};
		
		System.out.println("************ Files.write(small_file, small_bytes) ************");
		try{
			Files.write(small_file, small_bytes);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		/**************************************************************************************************/
		// 9. line 쓰기 (문자열 시퀀스 쓰기)
		//  - Files.write() 메서드로 수행
		//  - 각 라인의 뒤에 플랫폼에서 사용하는 라인구분자(line.separator 시스템속성)
		//  - 파라미터 : 파일의 Path, 문자시퀀스에 대한 이터레이션 가능한 객체, 문자셋에 사용할 인코딩, 파일열기를 지정하는 옵션
		/**************************************************************************************************/
		Path write_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		Charset charset = Charset.forName("UTF-8");
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("₩n");
		lines.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		lines.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		lines.add("ccccccccccccccccccccccccccccccccc");
		
		try{
//			Files.write(write_file_path,lines, charset,StandardOpenOption.CREATE);
			Files.write(write_file_path,lines, charset,StandardOpenOption.APPEND);
//			Files.write(write_file_path,lines, charset,StandardOpenOption.CREATE_NEW);
//			Files.write(write_file_path,lines, charset,StandardOpenOption.WRITE);
			
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
	
		
		/**************************************************************************************************/
		// 10. 작은파일 읽기 (바이너리)
		//  - NIO.2 에는 작은 바이너리 파일이나 작은 텍스트 파일을 한번에 읽을 수 있는 빠른 방법을 제공한다.
		//  - Files.readAllBytes(),Files.readAllLines() 메서드를 제공한다.
		//  - 전체파일의 바이트나 라인을 한번에 읽어온다.
		//  - 스트림을 열고 파일읽기가 끝나면 I/O에러나 예외가 발생할 경우 스트림을 닫기를 처리한다.
		//  - 파일은 반드시 있어야 한다.
		/**************************************************************************************************/
		Path read_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		
		try{
			byte[] byteArray = Files.readAllBytes(read_file_path);
			
			//같은디렉토리에 파일 생성
			Path test_file_path = read_file_path.resolveSibling("test_file_path.txt");
			
			System.out.println("************ Files.write(test_file_path, byteArray) ************");
			Files.write(test_file_path, byteArray,StandardOpenOption.APPEND);
			
			//파일이 텍스트 문자열이라면 적절하게 캐릭터셋을 지정하여 String으로 변환 할 수 있다.
			String test_str = new String(byteArray,"UTF-8");
			System.out.println(test_str);		
			
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		
		
		/**************************************************************************************************/
		// 11. 작은파일 읽기(라인문자열)
		//  - 전체 파일을 읽어서 문자열의 리스트로 변환한다.
		//  - 루프 처리가 효율적이다.
		//  - 다음과 같은 라인 종결문자를 인식한다.
		//    * ₩u000D₩u000A  // 캐리지리턴 + 라인피드
		//    * ₩u000D        // 캐리지리턴
		//    * ₩u000A        // 라인피드
		/**************************************************************************************************/
		Path text_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		Charset charset_utf8 = Charset.forName("UTF-8");
		try{
			
			List<String> lines_str = Files.readAllLines(text_file_path,charset_utf8);
			for(String str : lines_str){
				System.out.println("readAllLines : "+str);
			}
			
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		
		
		
		
		
		
		
	}
}
