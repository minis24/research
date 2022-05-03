package nio2;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.LinkPermission;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Date;
import java.util.Set;


public class Sample_Metadata {

	
	/*
	 * 파일이나 디렉토리가 숨김(hidden) 인지 디렉토리인지, 크기가 어떻게 되는지 , 소유자는 누구인지 등에 대한  정보
	 * NIO.2에서는 메타데이터의 개념(notion)과 속성(attribute) 을 연결지어 java.nio.file.attribute 패키지에서 접근
	 * 다른 파일시스템에서는 개념이 달라서 추적해야 하는 속성도 달라지므로 NIO.2 는 속성을 뷰로 묶어서 처리하며 
	 * 각 뷰는 특정 파일시스템 구현체와 매핑되어 있다. 
	 * 
	 *  일반적으로 readAttributes()를 통해 속성을 통째로 제공한다.
	 *  getAttribute(),setAttribute() 메서드로 개별 속성을 처리할 수 있다.
	 *  이들 메서드는 java.nio.file.Files 클래스에서 이용할 수 있다.
	 */
	private final static String FILE_PATH = "C:/QryDevSpace/workspace_java_001/NIO.2/resource/KakaoTalk_20150727_215337240.jpg"; 
	
	public static void main(String[] args) throws IOException {
	
		

		/**************************************************************************************************/
		// NIO.2에서 지원 하는 뷰
		/**************************************************************************************************/
		//  - BasicFileAttributeView  
		//    : 모든 파일시스템 구현물에서 지원해야하는 기본 속성에 대한 뷰를 제공한다. 속성뷰의 이름은 basic 이다.
		//  - DosFileAttributeView
		//    : 도스(DOS) 속성을 지원하는 파일시스템에서 지원하는 네 가지 표준 속성에 대한 뷰를 제공한다.속성뷰의 이름은 dos 이다.
		//  - PosixFileAttributeView
		//    : basic 속성뷰를 확장한 뷰로 유닉스 같은 POSIX 표준을 지원하는  파일시스템의 속성을 보여준다. 속성뷰의 이름은 posix
		//  - FileOwnerAttributeView
		//    : 파일 소유자 개념을 지원하는 파일시스템 구현물에서 지원하는 뷰다. 속성뷰의 이름은 owner 이다.
		//  - AclFileAttributeView
		//    : 파일의ACL 읽기는 업데이트를 지원하는 뷰다. NFSv4 ACL 모델을 지원한다. 속성뷰의 이름은 acl 이다.
		//  - UserDefinedFileAttributeView
		//    : 사용자가 정의 한 메타데이터를 지원하는 뷰다.
		/**************************************************************************************************/
		
		// --> 파일저장소는 하나이상의 FileStoreAttributeView 클래스를 지원한다.
		// --> 이 클래스는 다음과 같이 저장소 속성의 세트에 대해 읽기 전용이나 업데이트 가능한 뷰를 제공한다.
		// --> FileStoreAttributeView view = store.getFileStoreAttributeView(FileStoreAttributeView.class);
		
		
		/**************************************************************************************************/
		// 보충내용..
		/**************************************************************************************************/
		/*
		 2.6 POSIX 뷰
			PosixFileAttributes : 유닉스 계열에서 지원하는 속성을 basic 뷰에 추가함. 추가된 속성은 파일 소유자,그룹소유자,아홉가지 접근 권한등
			관련 속성 이름들은
			group : 관련 메소드는 PosixFileAttributes.group()
			permissions : 관련 메소드는 PosixFileAttributes.permissions()
			2.6.1 POSIX 권한
			PosixFileAttributes.permissions() : 권한 헬퍼 객인 PosixFilePermissions 객체의 컬레션을 반환
			PosixFilePermissions.asFileAttribute() : 파일 권한 Set를 받아서 FileAttribute를 생성한다. 그리고 Path.createFile()이나 Path.createDirectory()에 속성을 전달한다.
			PosixFilePermissions.fromString() : 코드에 직접 쓴 문자열(“rw-r--r--”)로 파일의 권한을 설정할 수 있다.
			2.6.2 POSIX 그룹 소유자
			PosixFileAttributeView.setGroup() : 주어진 그룹으로 변경함.
			
			
			2.7 ACL 뷰
			접근제어 목록은 파일시스템의 객체 권한에 엄격한 규칙을 적용하는데 쓰이는 권한의 컬렉션이다.
			ACL에서는 각 객체의 소유자,권한을 비롯해 다양한 플래그를 제어한다. AclFileAttributeView 인터페이스로 표현됨.
			acl 속성 이름 목록
			acl
			owner
			ACL 읽는 방법 
			Files.getFileAttributeView() : ACL 읽기 List<AclEntry> 형태로 추출함.
			Files.getAttribute() : ACL 읽기 List<AclEntry> 형태로 추출함. 속성 이름 문자열은 acl:acl 임. 
			
			
			AclEntry 클래스의 하위 네가지 컴포넌트 (acl 항목이 매핑되어 있음)
			타입 : 항목이 권한을 획득했는지, 거부됐는지를 결정한다. 타입은 ALARM,ALLOW,AUDIT,DENY
			principal : 어떤 항목이 권한을 획득했는지, 거부 됐는지에 대한 아이덴티티, 아이덴티디는 UserPrincipal로 매핑됨.
			권한 : 권한으로 된 세트 Set<AclEntryPermission>로 매핑
			플래그 : 항목이 어떻게 상속되고 전파되는지를 가리키는 플래그로 된 세트, Set<AclEntryFlag>로 매핑 
		 
		 */
		/**************************************************************************************************/
		
		
		FileSystem fileSystem = FileSystems.getDefault();

		for (FileStore store : fileSystem.getFileStores()) {
			FileStoreAttributeView fsav = store.getFileStoreAttributeView(FileStoreAttributeView.class);
			System.out.println(fsav);
		}
		
		
		
		Path file_path = Paths.get(FILE_PATH) ;
		FileStore fileStore = Files.getFileStore(file_path);
		System.out.println("fileStore.name : " +fileStore.name());
		System.out.println("fileStore.type : " +fileStore.type());
		System.out.println("isExist : " + Files.exists(file_path, LinkOption.NOFOLLOW_LINKS));
		FileStoreAttributeView fileStoreAttributeView = fileStore.getFileStoreAttributeView(FileStoreAttributeView.class);
		System.out.println("fileStoreAttributeView: " +fileStoreAttributeView);
//		fileStoreAttributeView.name();
		
		
		
		
		/**************************************************************************************************/
		// 특정파일시스템에서 지원하는 뷰 결정
		//  - NIO.2에서는 이름으로 지원하는 뷰의 전체목록을 조회하거나 파일 저장소가 특정 뷰를 지원하는지 검사할 수 있다.
		//    (파일 저장소는 파티션, 디바이스, 볼륨등의 저장소 타입을 매핑한 FileStore 클래스로 표현한다.)
		//  - 기본 파일시스템에 대한 인스턴스 획득  : FileSystems.getDefault()
		//  - 지원하는 뷰에 대한 목록 인스턴스 획득 : FileSystems.getDefault().supportedFileAttributeViews()
		/**************************************************************************************************/
//		FileSystem fileSystem = FileSystems.getDefault();
		Set<String> views = fileSystem.supportedFileAttributeViews();

		int i = 0;
		for(String view : views){
			System.out.println("view ["+i+"]: " + view);
			i++;
			
			/* windows 7에서 실행시 결과
			view [0]: owner
			view [1]: dos
			view [2]: acl
			view [3]: basic
			view [4]: user
			*/
		}
		
		
		/**************************************************************************************************/
		// FileSotre.supportsFileAttributeView() 메서드를 호출하는 방법으로 파일 저장소가 특정 뷰를 지원하는지 확인
		//  - 원하는 속성뷰 클래스나 속성뷰명을 문자열로 전달한다.
		/**************************************************************************************************/
		
		Iterable<FileStore> iter = fileSystem.getFileStores(); //파일시스템의 파일저장소(드라이브 객체) 목록 리턴
		for(FileStore fStore : iter){
//			boolean supported = fStore.supportsFileAttributeView(BasicFileAttributeView.class);
			boolean supported = fStore.supportsFileAttributeView("basic");
//			boolean supported = fStore.supportsFileAttributeView("posix");
//			boolean supported = fStore.supportsFileAttributeView("dos");
			System.out.println(fStore.name() +" : " + supported);
			System.out.println(fStore.type() );	             //NTFS  등
			System.out.println(fStore.getUsableSpace());	 // 여유공간
			
			/* 파일저장소 : 결국 HDD 또는 SSD 
			 	C드라이브 : true
				NTFS  
				29351907328
				
				D드라이브 : true
				NTFS 
				149856960512 
			 */
			
		}
		
		/**************************************************************************************************/
		// 특정 파일이 저장된 파일저장소에서 원하는 뷰를 지원하는지 검사할 수 있다.
		/**************************************************************************************************/
		String path_str = "C:/smcoreDevFrame_v_001/workspace/NIO.2/resource/KakaoTalk_20150727_215337240.jpg";
		Path path = Paths.get(path_str);
		
		FileStore fStore= Files.getFileStore(path);
		boolean supported = fStore.supportsFileAttributeView("basic");
		System.out.println(fStore.name() +" : " + supported);
		System.out.println(fStore.type() );	             //NTFS  등
		System.out.println(fStore.getUsableSpace());	 // 여유공간
		
		/* 
		 	C드라이브 : true
			NTFS  
			29351907328
		*/
		
		
		
		
		

		/**************************************************************************************************/
		// basic 뷰
		//  - 대다수 파일은 크기, 생성일,접근일,수정일 같은 공통 속성을 지원한다.
		//  - BasicFileAttributeView 에서 속성을 추출하거나 설정 할 수 있다.
		/**************************************************************************************************/
		// 1. 전체 속성 가져오기
		//   - readAttributes() 메서드 사용
		BasicFileAttributes attr = Files.readAttributes(path,BasicFileAttributes.class);
		System.out.println("\r\n\r\n\r\n");
		System.out.println("size             : " + attr.size());
		System.out.println("creationTime     : " + attr.creationTime());
		System.out.println("lastAccessTime   : " + attr.lastAccessTime());
		System.out.println("lastModifiedTime : " + attr.lastModifiedTime());
		
		System.out.println("isDirectory      : " + attr.isDirectory());
		System.out.println("isRegularFile    : " + attr.isRegularFile());
		System.out.println("isSymbolicLink   : " + attr.isSymbolicLink());
		System.out.println("IS other         : " + attr.isOther());
		
		/*
		    size             : 33768
			creationTime     : 2016-04-07T04:44:38.834324Z
			lastAccessTime   : 2016-04-07T04:44:38.834324Z
			lastModifiedTime : 2016-04-07T04:44:38.834324Z
			isDirectory      : false
			isRegularFile    : true
			isSymbolicLink   : false
			IS other         : false
		 */
		
		
		
		
		
		// 2. 단일 속성 가져오기
		//  - getAttribute() 메서드 사용
		//  - LinkOption.NOFOLLOW_LINKS : 현재 심볼링크를 해석하지 않음.
		//  - Object 타입으로 리턴하므로 명시적으로 캐스팅 시켜줘야 함.
		//  - [view-name:]attribute-name 형식을 사용한다. 여기서 view-name은 basic 이다.
		//  - 사용할 수 있는 속성 목록
		//     1) lastModifiedTime
		//     2) lastAccessTime
		//     3) creationTime
		//     4) size
		//     5) isRegularfile
		//     6) isDirectory
		//     7) isSymbolicLink
		//     8) isOther
		//     9) fileKey
		long size =(Long) Files.getAttribute(path, "basic:size", LinkOption.NOFOLLOW_LINKS);
		FileTime lastModifiedTime =(FileTime) Files.getAttribute(path, "lastModifiedTime", LinkOption.NOFOLLOW_LINKS);
		FileTime lastAccessTime =(FileTime) Files.getAttribute(path, "lastAccessTime", LinkOption.NOFOLLOW_LINKS);
		FileTime creationTime =(FileTime) Files.getAttribute(path, "creationTime", LinkOption.NOFOLLOW_LINKS);
		boolean isRegularFile =(boolean) Files.getAttribute(path, "isRegularFile", LinkOption.NOFOLLOW_LINKS);
		boolean isSymbolicLink =(boolean) Files.getAttribute(path, "isSymbolicLink", LinkOption.NOFOLLOW_LINKS);
		boolean isOther =(boolean) Files.getAttribute(path, "isOther", LinkOption.NOFOLLOW_LINKS);
//		boolean fileKey =(boolean) Files.getAttribute(path, "fileKey", LinkOption.NOFOLLOW_LINKS);
		
		System.out.println("size : " + size);
		System.out.println("lastModifiedTime : " + lastModifiedTime);
		System.out.println("lastAccessTime : " + lastAccessTime);
		System.out.println("creationTime : " + creationTime);
		System.out.println("isRegularFile : " + isRegularFile);
		System.out.println("isSymbolicLink : " + isSymbolicLink);
		System.out.println("isOther : " + isOther);
		System.out.println("fileKey : " + Files.getAttribute(path, "fileKey", LinkOption.NOFOLLOW_LINKS));
		
		/*
		 	lastModifiedTime : 2016-04-07T04:44:38.834324Z
			lastAccessTime : 2016-04-07T04:44:38.834324Z
			creationTime : 2016-04-07T04:44:38.834324Z
			isRegularFile : true
			isSymbolicLink : false
			isOther : false
			fileKey : null
		 */
		
		
		
		
		
	}
}
