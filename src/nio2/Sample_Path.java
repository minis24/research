package nio2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sample_Path {

	
	/*
	 * 경로(path)는 파일시스템에 있다.
	 * 파일시스템은 java.nio.file.FileSyatems 파이널클래스를 통해서 접근할 수 있으며
	 * java.nio.file.FileSystem의 인스턴스를 가져와 작업할 수 있다.
	 * 
	 * FileSystems 에는 새파일시스템을 생성하는 newFileSystem() 메서드가 있다.
	 * 
	 * getDefault()  : JVM에 기본 FileSystem(주로 운영체제의 기본 파일 시스템)을 반환하는 static 메서드.
	 * getFileSystem(URI uri) 인자의 URI스키마와 일치하는 파일시스템제공자 목록에서 파일시스템을 반환하는 static 메서드
	 */
	
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		
		
		/**************************************************************************************************/
		// 파일시스템(FileSystem) 속성 
		//  - FileSystem fileSystem    = FileSystems.getDefault() 메서드를 호출하여 기본 파일시스템에 대한 인스턴스 획득
		//  - fileSystem.getSeparator() : 파일시스템의 구분자 획득
		//  - Iterable<Path> iter1      = fileSystem.getRootDirectories() : 
		//  - Iterable<FileStore> iter2 = fileSystem.getFileStores() 메서드를 호출하여 파일 저장소 목록 인스턴스 획득
		/**************************************************************************************************/
		FileSystem fileSystem = FileSystems.getDefault();
		System.out.println("fileSystem.getSeparator() : " + fileSystem.getSeparator()); // \
		System.out.println("fileSystem.getFileStores() : " + fileSystem.getFileStores());
		System.out.println("fileSystem.getRootDirectories() : " + fileSystem.getRootDirectories()); // [C:\, D:\]
//		System.out.println("fileSystem.getPath() : " + fileSystem.getPath(first, more));
		
		
		
		/**************************************************************************************************/
		// 파일저장소(FileStore) 인스턴스 획득
		//  - Iterable<FileStore> iter = fileSystem.getFileStores() 메서드를 호출하여 파일 저장소 목록 인스턴스 획득
		//  - Files.getFileStore(Path);
		/**************************************************************************************************/
		
		// 첫번째 방법
		System.out.println("******* 첫번째 방법 *******");
		Iterable<FileStore> fileStores = fileSystem.getFileStores();
		for(FileStore store : fileStores){
			String storeName = store.name();
			String sotreType = store.type();
			System.out.println("storeName : "+ storeName +"        |        "+"sotreType : " +sotreType);
			
			/*
			 storeName :         |        sotreType : NTFS
			 storeName : 새 볼륨        |        sotreType : NTFS
			 storeName :         |        sotreType : NTFS
			 storeName : D드라이브        |        sotreType : NTFS
			 */
		
		}
		
		
		// 두번째 방법
		System.out.println("******* 두번째 방법 *******");
		Path storePath = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
		FileStore fileStore = Files.getFileStore(storePath);
		System.out.println("storeName : "+ fileStore.name() +"        |        "+"sotreType : " +fileStore.type()+"        |        "+"unallocated_space : " +fileStore.getUnallocatedSpace()+"        |        "+"available_space : " +fileStore.getUsableSpace());
		
		
		
		
		/**************************************************************************************************/
		// 파일저장소(FileStore) 속성
		//  - Iterable<FileStore> iter = fileSystem.getFileStores() 메서드를 호출하여 파일 저장소 목록 인스턴스 획득
		//    --> iterate 한 각 인스턴스는 이에 해당하는 name(),type(),getTotalSpace()의 메서드를 호출할 수 있다.
		//    --> 디스크공간은 바이트로 리턴되므로 MB/GB 등으로 환산해주면 보기 편하다.
		/**************************************************************************************************/
		Iterable<FileStore> iter = fileSystem.getFileStores();
		for(FileStore store : iter){
			long total_space = store.getTotalSpace()/1024/1024/1024;
			long used_space =  total_space - store.getUnallocatedSpace()/1024/1024/1024;
			long available_space =  store.getUsableSpace() /1024/1024/1024;
			boolean isReadOnly   = store.isReadOnly();
			String storeName = store.name();
			String sotreType = store.type();
			System.out.println("\r\n");
			System.out.println("STORE NAME : ["+storeName+"]");
			System.out.println("STORE TYPE : ["+sotreType+"]");
			System.out.println("total_space: " + total_space + " GB");
			System.out.println("used_space : " + used_space + " GB");
			System.out.println("available_space: " + available_space + " GB");
			System.out.println("isReadOnly: " + isReadOnly);
			
		}
		
		
		
		
		
		/**************************************************************************************************/
		// Path 설정하는 법
		//  - get()메서드는 경로를 몇개의 덩어리로 나눌 수 있다.
		//  - NIO가 경로를 재구성 하므로 덩어리가 몇개인지는 문제가 되지 않는다.
		//  - 각 요소를 덩어리로 구성할 경우에는 파일 구분자를 생략 할 수 있다.
		/**************************************************************************************************/

		
		
		
		/**************************************************************************************************/
		//절대 경로 정의하기
		/**************************************************************************************************/
		String path_str = "C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg";

		Path path1 = Paths.get(path_str);
		Path path2 = Paths.get("C:","QryDevSpace","workspace_java_001/NIO.2/resource","KakaoTalk_20150727_215337240.jpg");
		
		FileStore fileStore1 = Files.getFileStore(path1);
		FileStore fileStore2 = Files.getFileStore(path2);
		System.out.println("fileStore1 : "+ fileStore1.name());
		System.out.println("fileStore2 : "+ fileStore2.name());
		
		
		
		/**************************************************************************************************/
		// 파일저장소 루트에서의 상대경로 지정하기
		/**************************************************************************************************/
		Path path3 = Paths.get("/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
		FileStore fileStore3 = Files.getFileStore(path3);
		System.out.println("fileStore3 : "+ fileStore3.name());
		
		
		/**************************************************************************************************/
		//작업폴더에서 상대경로 정의하기
		//  - 현재 작업폴더에서 상대경로를 정의할때는 파일구분자를 경로시작에 쓰면 안된다.
		/**************************************************************************************************/
		Path path4 = Paths.get("resource/newDir/ball.png");
		FileStore fileStore4 = Files.getFileStore(path4);
		System.out.println("fileStore4 : "+ fileStore4.name());
		
		
		

		
		/**************************************************************************************************/
		//URI 에서 경로 정의하기
		//  - URI.create() 메서드를 사용하여 문자열에서 URI를 생성하고 Path.get()메서드에 URI 객처를 전달한다.
		/**************************************************************************************************/
		URI uri = URI.create("file:///"+path_str);
		System.out.println(uri);  //출력결과 -->  file:///C:/smcoreDevFrame_v_001/workspace/NIO.2/resource/KakaoTalk_20150727_215337240.jpg
		
		Path path_uri = Paths.get(uri);
		System.out.println(" path_uri : "+ path_uri);//출력결과 -->C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		FileStore fileStore_uri = Files.getFileStore(path_uri);
		System.out.println(fileStore_uri.name());
		
		/**************************************************************************************************/
		// 홈디렉토리를 이용하여 경로 정의
		//  - 홈디렉토리는 사용자 환경에 따라 다르다.
		/**************************************************************************************************/
		System.out.println(System.getProperty("user.home")); 
		Path path_5 = Paths.get(System.getProperty("user.home"), "dir1" , "dir2","file_name");
		
		
				
		
		/**************************************************************************************************/
		// FileSystems 클래스 사용하여 경로 정의 
		//  - getDefault() 호출하여 기본 FileSystem 을 얻는다.
		//  - NIO.2 에서는 기본 파일시스템에 접근 할 수 있는 일반 객체를 제공할 것이다.
		//  - getPath() 를 호출한다.
		//  - Paths.get() 이 방법의 단축된 표현이다.
		/**************************************************************************************************/
		Path path6 = FileSystems.getDefault().getPath("C:","QryDevSpace","workspace_java_001/NIO.2/resource","KakaoTalk_20150727_215337240.jpg"); 
		System.out.println(FileSystems.getDefault().toString());
		 
		

		
		
		
		
		
		
		
		
		
		
		
		/**************************************************************************************************/
		// 경로에 관한 정보 가져오기
		//  - NIO.2 가 경로 문자열을 요소들의 집합으로 자르고 가장 높은 요소에 0 을 할당하고 가장 낮은 요소에 n-1을 할당한다.
		//  - 가장 높은 요소는 root 가 되고 가장 낮은 요소는 파일이 된다.
		/**************************************************************************************************/
		
		/**************************************************************************************************/
		// 파일/디렉토리 이름 가져오기
		//  - getFileName() 메서드는 파일/디렉토리를 반환하며 루트에서 가장 멀리 떨어진 요소를 반환한다.
		/**************************************************************************************************/
		System.out.println("path.getFileName()   : "+ path6.getFileName());   //출력결과 --> KakaoTalk_20150727_215337240.jpg
		
		
		/**************************************************************************************************/
		// 경로 루트 가져오기
		//  - getFileName() 메서드는 파일/디렉토리를 반환하며 루트에서 가장 멀리 떨어진 요소를 반환한다.
		/**************************************************************************************************/
		System.out.println("path.getRoot()   : "+ path6.getRoot());   //출력결과 --> C:\
		
		
		/**************************************************************************************************/
		// 경로 부모 가져오기
		//  - getParent() 메서드는 현재 경로의 부모를 반환한다.
		/**************************************************************************************************/
		System.out.println("path.getParent()   : "+ path6.getParent());   //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource
		
		
		/**************************************************************************************************/
		// 경로의 이름요소 가져오기
		//  - getNameCount() 메서드로 경로에 있는 요소 갯수를 구할 수 있고, 각 요소의 이름은 getName()으로 알 수 있다.
		/**************************************************************************************************/
		System.out.println("path.getNameCount()   : "+ path1.getNameCount());   //출력결과 --> 5
		
		int nameCnt = path1.getNameCount();
		for(int i = 0 ; i <nameCnt ; i++){
			System.out.println("path.getName("+i+")   : "+ path1.getName(i));   
			/*출력결과 -->
			 *  path.getName(0)   : smcoreDevFrame_v_001
				path.getName(1)   : workspace
				path.getName(2)   : NIO.2
				path.getName(3)   : resource
				path.getName(4)   : KakaoTalk_20150727_215337240.jpg 
			 */
			
		}
		
		
		
		/**************************************************************************************************/
		// 경로의 서브경로 가져오기
		//  - subpath() 메서드로 상대경로를 추출할 수 있다.
		//  - 메서드에 시작 인덱스와 끝 인덱스를 전달하면 해당 범위의 요소를 반환한다.
		/**************************************************************************************************/
		System.out.println("path.subpath()   : "+ path1.subpath(0,3));   //출력결과 --> smcoreDevFrame_v_001\workspace\NIO.2
		System.out.println("path.subpath()   : "+ path1.subpath(1,4));   //출력결과 --> workspace\NIO.2\resource
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**************************************************************************************************/
		// 경로를 변환하기
		/**************************************************************************************************/
		// - 문자열로 변환
		String path_to_string = path1.toString();
		System.out.println("path_to_string   : "+ path_to_string);   //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		
		// - URI로 변환
		URI path_to_uri = path1.toUri();
		System.out.println("path_to_uri   : "+ path_to_uri);   //출력결과 --> file:///C:/smcoreDevFrame_v_001/workspace/NIO.2/resource/KakaoTalk_20150727_215337240.jpg
				
		
		/**************************************************************************************************/
		// 상대경로를 절대 경로로 변환
		// - toAbsolutePath() 메서드
		// - 절대경로에 대해 이 메서드를 호출하면 같은 결과를 반환한다.
		// - 파일이 실재로 존재하지 않아도 된다.
		/**************************************************************************************************/
		Path path_to_absolute = path1.toAbsolutePath();
		System.out.println("path_to_absolute   : "+ path_to_absolute);   //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
				
		
		/**************************************************************************************************/
		// 경로를 실재 경로로 변환
		// - toRealPath() 메서드
		// - 실재파일의 실제 경로를 반환한다.
		// - 심볼링크를 지원하는 파일시스템에서 toRealPath()에 어떤 인자도 전달하지 않으면 심볼링크를 실제경로로 풀이한다.
		// - 변환과정에서 심볼링크를 제외하고 싶다면 LinkOption.NOFOLLOW_LINKS  enum type 상수를 전달해야 한다.
		// - 상대경로를 전달하면 절대경로로 반환한다.
		// - 경로에 중복 요소가 있다면 이를 제거한 경로를 반환한다.
		// - 파일이 없거나 접근할 수 없다면 IOException 을 던진다.
		/**************************************************************************************************/
		Path real_path = path1.toRealPath();
		System.out.println("real_path :" + real_path );  //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		
		
		
		/**************************************************************************************************/
		// 경로를 File객체로 변환
		// - toFile() 메서드
		// - File 클래스에는 toPath 메서드가 있으므로 Path 객체로 변환할 수 있다.
		/**************************************************************************************************/
		File path_to_file = path1.toFile();
		Path file_to_path = path_to_file.toPath();
		
		System.out.println("path_to_file :" + path_to_file );  //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		System.out.println("file_to_path :" + file_to_path );  //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		
		
		
		/**************************************************************************************************/
		// 두 경로를 조합하기
		// - 고정된 root 경로에 부분 경로를 추가하는 방법이다.
		// - 공통부분을 두고 경로를 정의할때 유용하다.
		// - NIO.2 에서 resolve() 메서드로 이런 작업을 수행한다.
		/**************************************************************************************************/
		// - 고정경로 정의
		Path path_fix = Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/");
		
		//park.jpg 추가
		Path path_park = path_fix.resolve("park.jpg");
		System.out.println("path_park : " + path_park);
		 //출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\park.jpg
		
		//KakaoTalk_20150727_215337240.jpg 추가
		Path path_kakao = path_fix.resolve("KakaoTalk_20150727_215337240.jpg");
		System.out.println("path_kakao : " + path_kakao);
		
		
		// - 형제경로만 처리하는 메서드
		// - resolveSibling() 메서드
		// - 현재경로의 부모 경로를 기준으로 전달된 경로를 해석한다.
		// - 현재경로의 파일 이름을 주어진 경로의 파일이름으로 대체하는 작업을 한다.
		Path path_sibling = path_kakao.resolveSibling("test.jpg");
		System.out.println("path_sibling : " + path_sibling);
		System.out.println("path_kakao : " + path_kakao);
		//출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\test.jpg
		//출력결과 --> C:\smcoreDevFrame_v_001\workspace\NIO.2\resource\KakaoTalk_20150727_215337240.jpg
		
		
		
		/**************************************************************************************************/
		// 두 위치 사이의 경로 생성하기.
		// - relativize() 메서드
		// - 두경로 사이의 상대경로를 생성한다,
		// - 원본 경로에서 인자로 전달된 위치로 끝나는 경로를 생성한다.
		// - 새로운 경로는 원본경로의 상대경로가 된다.
		/**************************************************************************************************/
		// 형제경로일경우
		Path path01 = Paths.get("BNP.txt");
		Path path02 = Paths.get("AEGON.txt");
		
		Path path01_to_path02 = path01.relativize(path02);
		System.out.println("path01_to_path02 : " + path01_to_path02);
		//출력결과 --> ..\AEGON.txt
		
		Path path02_to_path01 = path02.relativize(path01);
		System.out.println("path02_to_path01 : " + path02_to_path01);
		//출력결과 --> ..\BNP.txt
		
		
		
		//루트 요소를 포함한 두경로가 있을경우
		Path path03 = Paths.get("/abc/2009/BNP.txt");
		Path path04 = Paths.get("/abc/2011");
		
		Path path03_to_path04 = path03.relativize(path04);
		System.out.println("path03_to_path04 : " + path03_to_path04);
		//출력결과 -->  ..\..\2011
		
		Path path04_to_path03 = path04.relativize(path03);
		System.out.println("path04_to_path03 : " + path04_to_path03);
		//출력결과 -->  ..\2009\BNP.txt
		
		
		
		
		/**************************************************************************************************/
		// 두 경로 비교하기
		// - Path.equals() 메서드는 파일시스템에 접근하지 않으며
		//    , 비교하려는 경로가 실재하지 않아도 되며
		//    , 경로가 같은 파일인지 검사하지 않는다.
		//    , 운영체제에 따라 대소문자를 고려하여 비교하거나 무시한다.
		//    , 대소문자 구분을 지정하는 구현체도 있다.
		// - isSameFile() 메서드는 같은 파일 또는 폴더인지 검사한다.
		//    ,내부적으로 equals 메서드를 사용하며 Path.equals()가 true 를 반환하면 동등하므로 더이상 비교하지 않으며
		//    ,false를 반환하면 이중 체크 작업을 진행한다.
		//    ,파일시스템에 실재 해야한다.
		/**************************************************************************************************/
		Path path_c01 =  Paths.get("C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
		Path path_c02 =  Paths.get("/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg");
		
		if(path_c01.equals(path_c02)){
			System.out.println("path_c01 == path_c02");
		}else{
			System.out.println("path_c01 != path_c02");
		}
		//출력결과 --> path_c01 != path_c02
		
		
		boolean check =  Files.isSameFile(path_c01, path_c02);
		System.out.println("check : " + check);
		//출력결과 --> check : true
		
		
		/**************************************************************************************************/
		//부분비교
		// Path.startsWith90, Path.endsWith() 메서드를 사용하면 주어진 경로로 시작하는지 끝나는지 알 수 있다.
		/**************************************************************************************************/
		boolean startsWith_check =path_c01.startsWith("C:/smcoreDevFrame_v_001");
		System.out.println("startsWith_check.startsWith : " + startsWith_check);
		
		
		
		/**************************************************************************************************/
		// 경로의 이름요소 반복하기
		// - Path 클래스는 Iterable 인터페이스를 구현하고 있으므로 경로에 있는 요소에 대해 이터레이트 할 수 있는 
		//   객체를 가져올 수 있다.
		// - 명시적 이터레이터를 사용하거나 각 이터레이션에 대해 path 객체를 반환하는 foreach 루프를 사용할 수 있다.
		/**************************************************************************************************/
		for(Path name : path6){
			System.out.println(name);
			
			/*
			 *  smcoreDevFrame_v_001
				workspace
				NIO.2
				resource
				KakaoTalk_20150727_215337240.jpg
			 */
		}
		
		
		
	}
}
