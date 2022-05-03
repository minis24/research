package nio2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class Sample_tempDirAndFile {

	
	
	public static void main(String[] args) throws IOException  {
	
		/**************************************************************************************************/
		// 1. 임시디렉토리
		//  임시파일을 저장하는 디렉토리이다. 운영체제에 따라서 다르다.
		//   - 윈도우 : TEMP 환경변수로 설정 , 보통은 C:\TEMP나 %Windows%\Temp 
		//   - 리눅스/유닉스 : 전역 임시디렉토리 /tmp 와 /var/tmp 이다.
		/**************************************************************************************************/
		
		
		
		/**************************************************************************************************/
		// 2. 임시디렉토리 생성
		//  - createTempDirectory() 메서드를 호출하여 생성
		//  - 파라미터 : [생성하려는 디렉터리명에 사용할 접두 문자열(null가능), 원자적으로 설정할 파일속성의 옵션 목록]
		/**************************************************************************************************/
		String tmp_dir_prefix = "nio";
		
		Path tmp_1 = Files.createTempDirectory(null);
		System.out.println("TMP : " + tmp_1.toString());
		
		Path tmp_2 = Files.createTempDirectory(tmp_dir_prefix);
		System.out.println("TMP : " + tmp_2.toString());
		/*
		 TMP : C:\Users\xxxxxxx\AppData\Local\Temp\7770766701413534922
		 TMP : C:\Users\xxxxxxx\AppData\Local\Temp\nio197579538045644636
		 */
		
		Path baseDir = FileSystems.getDefault().getPath("C:/QryDevSpace/workspace_java_001/NIO.2/resource/tempDir/");
		String tmp_dir_prefix_1 = "temp_";
		Path tmp = Files.createTempDirectory(baseDir, tmp_dir_prefix_1);
		System.out.println("TMP : " + tmp.toString());
		//TMP : C:\QryDevSpace\workspace_java_001\NIO.2\resource\tempDir\temp_5941145026985033166
		
		
		
		
		/**************************************************************************************************/
		// 3. 임시디렉토리 위치 확인
		//  - System.getProperty("java.io.tmpdir");
		/**************************************************************************************************/
		System.out.println("Default Temp Dir : "+ System.getProperty("java.io.tmpdir"));
		//Default Temp Dir : C:\Users\minis24\AppData\Local\Temp\
	
		
		
		
	}
}
