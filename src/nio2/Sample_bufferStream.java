package nio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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


public class Sample_bufferStream {

	
	
	public static void main(String[] args) throws IOException  {
	

		
		/**************************************************************************************************/
		// 1. 스트림 [버퍼스트림 / 비버퍼스트림]
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
		
		
		// 2. 표준 열기옵션
		//  - NIO.2부터 생성,읽기,쓰기(그외 파일열기와 관련된 모든것)와 관련된 메서드는 모두 파일 생성이나 여는 방법을 지정하는 
		//    옵션 매개변수 OpenOption을 지원한다.
		//  - 실제로 OpenOption은 Java.nio.file 패키지의 인터페이스이며 , 두 가지 구현체를 가지고 있다.
		//    1) LinkOption클래스[NOFOLLOW_LINK enum 상수정의]
		//    2) StandardOpenOption 클래스에서 다음의 enum 상수 정의
		//       * READ             : 읽기용으로 파일을 연다
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
		// 3. 새파일 생성
		//  - Files.createFile() 메서드를 호출하여 수행
		//  - 파라미터로 생성할 파일의 Path 와 파일속성의 옵션목록(FileAttribute<?>)를 받는다.
		//  - 생성된 파일을 리턴한다.
		//  - 파일이 존재하면 FileAlreadyExistException을 던진다.
		/**************************************************************************************************/
		Path newFile = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		
		try{
			Files.createFile(newFile);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		
		
		/**************************************************************************************************/
		// 4. POSIX 파일시스템에서 새파일을 특정권한으로 생성
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
		// 5. 작은파일 쓰기
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
		
		try{
			Files.write(small_file, small_bytes);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		/**************************************************************************************************/
		// 6. line 쓰기 (문자열 시퀀스 쓰기)
		//  - Files.write() 메서드로 수행
		//  - 각 라인의 뒤에 플랫폼에서 사용하는 라인구분자(line.separator 시스템속성)
		//  - 파라미터 : 파일의 Path, 문자시퀀스에 대한 이터레이션 가능한 객체, 문자셋에 사용할 인코딩, 파일열기를 지정하는 옵션
		/**************************************************************************************************/
		Path write_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		Charset charset = Charset.forName("UTF-8");
		ArrayList<String> lines = new ArrayList<>();
		lines.add("\n");
		lines.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		lines.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		lines.add("ccccccccccccccccccccccccccccccccc");
		
		try{
			Files.write(write_file_path,lines, charset,StandardOpenOption.APPEND);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
	
		
		/**************************************************************************************************/
		// 7. 작은파일 읽기 (바이너리)
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
			Files.write(test_file_path, byteArray);
			
			//파일이 텍스트 문자열이라면 적절하게 캐릭터셋을 지정하여 String으로 변환 할 수 있다.
			String test_str = new String(byteArray,"UTF-8");
					
			
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		
		
		
		/**************************************************************************************************/
		// 8. 작은파일 읽기(라인문자열)
		//  - 전체 파일을 읽어서 문자열의 리스트로 변환한다.
		//  - 루프 처리가 효율적이다.
		//  - 다음과 같은 라인 종결문자를 인식한다.
		//    * \u000D\u000A  // 캐리지리턴 + 라인피드
		//    * \u000D        // 캐리지리턴
		//    * \u000A        // 라인피드
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
		
		
		
		
		
		/**************************************************************************************************/
		// 9. newBufferedWriter()
		//  - Path,캐릭터셋,파일열기 옵션 을 파라미터로 받는다.
		//  - 쓰기를 위한 파일을 오픈하거나(없다면 생성),기존 일반 파일을 0바이트 크기로 잘라낸다.
		//  - CREATE,TRUNCATE_EXSISTING,WRITE 옵션을 준것처럼 동작한다.
		//  - 기본 버퍼라이터를 리턴한다.
		/**************************************************************************************************/
		Path buffered_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/create_new_file.txt");
		Charset charset_1 = Charset.forName("UTF-8");
		
		try(BufferedWriter writer = Files.newBufferedWriter(buffered_file_path, charset_1, StandardOpenOption.APPEND)){
			
			
			String text_str = "\nasdfasdfwerqwaersdfsdf";
			writer.write(text_str);
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		/**************************************************************************************************/
		// 10. newBufferedReader() 
		//  - 버퍼를 통해 파일을 읽기위해 사용
		//  - Path,캐릭터셋을 파라미터로 받는다.
		//  - 버퍼리더를 리턴한다.
		/**************************************************************************************************/
		try(BufferedReader reader = Files.newBufferedReader(buffered_file_path, charset_1)){
			String line = null;
			while((line = reader.readLine()) != null){
				System.out.println("BufferedReader :"+  line);
			}
		}catch (IOException e){
//			e.printStackTrace();
			System.err.println(e);
		}
		
		
		/**************************************************************************************************/
		// 11. newOutputStream() 
		//  - 그대로 사용할 수도 있으며 래핑하여 버퍼스트림으로 변환해서 사용할 수도 있다.
		//  - 쓰기를 위한 파일을 오픈하거나(없다면 생성),기존 일반 파일을 0바이트 크기로 잘라낸다.
		//  - CREATE,TRUNCATE_EXSISTING,WRITE 옵션을 준것처럼 동작한다.
		//  - 파라미터로 Path, 파일열기 옵션을 받는다.
		//  - 쓰레드안전한 비버퍼 스트림을(OutputStream) 리턴한다.
		/**************************************************************************************************/
		Path unBuffered_stream_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/test_new_file.txt");
		
		
		try {
			OutputStream outputStream_1 = Files.newOutputStream(unBuffered_stream_file_path);
			
			String w_str = "asdgadsfsgawerwe1qadasd";
			byte[] w_str_bytes = w_str.getBytes();
			outputStream_1.write(w_str_bytes);
			
			
			
			OutputStream outputStream_2 = Files.newOutputStream(unBuffered_stream_file_path,StandardOpenOption.APPEND);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream_2));
			writer.write(w_str);
			writer.flush();
			writer.close(); // 			
		}catch (IOException e){
			e.printStackTrace();
//			System.err.println(e);
		}
		
		
		/**************************************************************************************************/
		// 12. newInputStream() 
		//  - 그대로 사용할 수도 있으며 래핑하여 버퍼스트림으로 변환해서 사용할 수도 있다.
		//  - 디폴트로는 (옵션을 지정하지 않으면) READ 옵션으로 여는것과 동일함.
		//  - 파라미터로 Path, 파일열기 옵션을 받는다.
		//  - 쓰레드안전한 비버퍼 스트림을(InputStream) 리턴한다.
		/**************************************************************************************************/
		Path in_file_path = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/newDir/test_new_file.txt");
		int n;
		try {
			InputStream in = Files.newInputStream(unBuffered_stream_file_path);
			while((n=in.read()) != -1){
				System.out.println((char) n);
			}
		
			//바이트 형태로 버퍼 배열을 채우는 방법[여전히 비버퍼 스트림메서드이다.]
			//read(buf,0,buf.length) 와 같다.
			InputStream in_1 = Files.newInputStream(unBuffered_stream_file_path);
			byte[] buf = new byte[1024];
			while((n=in_1.read(buf)) != -1){
				System.out.println(new String(buf).trim() );
			}
			
			//버퍼스트림으로 변환하여 사용. 효율성이 더 높다.
			InputStream in_2 = Files.newInputStream(unBuffered_stream_file_path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in_2));
			String line = null;
			while((line= reader.readLine()) != null){
				System.out.println("buffer변환 : " + line);
			}
			
		}catch (IOException e){
			e.printStackTrace();
//			System.err.println(e);
		}
	
		
	}
}
