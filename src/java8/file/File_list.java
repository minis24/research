package java8.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * [ Lambda 의 기본 틀 ]
 * Predicate    : (T -> boolean)    -> 주로 필터에 사용
 * Supplier     : (() -> T)         -> 만드는놈(객체 생성)
 * Consumer     : (T -> void)       -> 쓰는놈(실행에 사용)
 * Function     : (T -> R)          -> From 에서 뭔가를 To 로 만들어 넘김
 */
public class File_list {
	public static void main(String[] args) throws IOException, InterruptedException {

		
		//디렉토리에 있는 모든 파일명출력 
		System.out.println("-----------------------1");
		Files.list(Paths.get("."))
				.forEach(System.out::println);
		
		
		
		//디렉토리에 있는 서브디렉토리만 필터링 하여 출력 
		System.out.println("-----------------------2");
		Files.list(Paths.get(".")) 
				.filter(Files::isDirectory)
				.forEach(System.out::println);
		
		
		
		
		
		//자바는 파일이름을 선별하기 위해 다양한 list()메서드를 제공.
		// list()메서드는 파라미터로 FileNameFilter를 갖는다.
		// accept()메서드에서 true를 리턴하면 리스트에 주어진 파일 이름을 포함한다.
		System.out.println("-----------------------3");
		final String[] files = new File("/Users/jangkwankim/01_java_project/project_005_mbpcen/workspace/01_research/bin/java8/file")
				.list(new FilenameFilter() {
			
							@Override
							public boolean accept(File dir, String name) {
				//				return false;
								return name.endsWith(".class");
							}
		});
		for(String name : files) {
			System.out.println(name);
		}
		
		
		
		
		
		
		System.out.println("-----------------------4");
		// 새로운 DirectoryStream 기능으로 고도화.
		// 파일이름으로 필터링 (path -> path.toString().endsWith(".class"))
		Files.newDirectoryStream(Paths.get("/Users/jangkwankim/01_java_project/project_005_mbpcen/workspace/01_research/bin/java8/file") 
				, path -> path.toString().endsWith(".class"))
		.forEach(System.out::println);
		
		
		
		
		
		
		System.out.println("-----------------------5");
		// 파일속성 으로 필터링 (file -> file.isHidden())
		final File[] hiddenfiles = new File("/Users/jangkwankim/01_java_project/project_005_mbpcen/workspace/01_research/bin/java8/file")
				.listFiles(file -> file.isHidden());
		
		
		
		
		//대규모 디렉토리면 File 메서드를 사용하는 대신 directoryStream을 사용한다.
		System.out.println("-----------------------6");
		Files.newDirectoryStream(Paths.get("/Users/jangkwankim/01_java_project/project_005_mbpcen/workspace/01_research/bin/java8") 
				, path -> Files.readAttributes(path,BasicFileAttributes.class).isDirectory())
		.forEach(System.out::println);
		
		
		
		System.out.println("-----------------------7");
		List<File> files1 = new ArrayList<File>();
		File[] filesInCurrentDir = new File(".").listFiles();
		for(File file : filesInCurrentDir) {
			File[] filesInSubDir = file.listFiles();
			if(filesInSubDir != null) {
				files1.addAll(Arrays.asList(filesInSubDir));
			}else {
				files1.add(file);
			}
		}
		System.out.println(files1.size());
		
		
		
		
		
		
		
		System.out.println("-----------------------8");
		//flatMap으로 고도화.
		//flatMap()메서드 : 매핑후에 플랫하게 한다.
		//람다표현식에서 엘리먼트를 리턴하는 부분에서 람다 표현식이 아닌 스트림을 리턴한다.
		//각 엘리먼트를 매핑해서 얻은 다중 스트림을 하나의 플랫 스트림으로 플랫하게 한다.
		List<File> files2 = 
				Stream.of(new File(".").listFiles()) //현재 디렉토리에 대한 스트림 획득.
				.flatMap(file -> file.listFiles() == null ? Stream.of(file) : Stream.of(file.listFiles()))
				.collect(Collectors.toList());
		
		System.out.println(files2.size());
		
		
		
		
		
		
		System.out.println("-----------------------9");
		//파일변경 살펴보기 
		final Path path = Paths.get(".");
		final WatchService watchService = path.getFileSystem().newWatchService();
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY); //watchService 등록 , 파일변화를 감지해 watchKey를 통해 알려준다.
		
		System.out.println("Report any File changed within next 1 minute");
		
		System.out.println("watchService.poll111111");
		 final WatchKey wkey = watchService.poll(1,TimeUnit.MINUTES);
		System.out.println("watchService.poll222222");
		 if(wkey != null) {
			 wkey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
		 }
	}
	
}
