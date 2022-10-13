/*------------------------------------------------------------------------------
 * 파일명 : ByteUtils
 * 설  명 : Byte에 대한 유틸 성 클래스
 * 버  전 : v1.0
 * 프로젝트 : 스마트 마케팅 플랫폼 구축 - 자바라
 *------------------------------------------------------------------------------
 *                  변         경         사         항
 *------------------------------------------------------------------------------
 *    날짜             작성자                      변경내용
 * ----------  ------  ---------------------------------------------------------
 * 2010.02.01  조민훈D  최초 프로그램 작성
 * 2012.11.01  조민훈D  toBytesFromHexString 메소드 추가
 *------------------------------------------------------------------------------
*/
package crypto;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.xml.bind.DatatypeConverter;

/**
 * 클래스명 : ByteUtils
 * 설명 : Byte에 대한 유틸 성 클래스
 * 주의사항 :
 */
public class ByteUtils {

	
	
	
	/**
	 * 두개의 바이트 배열을 덧붙인다.
	 * <br> 파라미터로 넘어온 바이트 배열이 null 인 경우 null 리턴
	 * <br> 앞의 배열에 뒤의 배열을 이어 붙인다.
	 */
	public static byte[] binaryCat(byte[] arr1 , byte[] arr2){
		if(arr1 == null || arr2 == null){
			System.out.println("\r\nparameter is null\r\n\r\n");
			return null;
		}
		
		
		int len1 = arr1.length;
		int len2 = arr2.length;
		
		byte[] result = new byte[len1+len2];
		System.arraycopy(arr1, 0, result, 0, len1);
		System.arraycopy(arr2,0, result, len1, len2);
		
		
		return result;
		
	}
	
	

 
	/**
	 * int형을 byte배열로 바꿈<br>
	 * @param integer
	 * @param order
	 * @return
	 */
	public static byte[] intTobyte(int integer, ByteOrder order) {
 
		ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE/8);
		buff.order(order);
 
		// 인수로 넘어온 integer을 putInt로설정
		buff.putInt(integer);
 
		System.out.println("intTobyte : " + buff);
		return buff.array();
	}
 
	/**
	 * byte배열을 int형로 바꿈<br>
	 * @param bytes
	 * @param order
	 * @return
	 */
	public static int byteToInt(byte[] bytes, ByteOrder order) {
 
		ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE/8);
		buff.order(order);
 
		// buff사이즈는 4인 상태임
		// bytes를 put하면 position과 limit는 같은 위치가 됨.
		buff.put(bytes);
		// flip()가 실행 되면 position은 0에 위치 하게 됨.
		buff.flip();
 
		System.out.println("byteToInt : " + buff);
 
		return buff.getInt(); // position위치(0)에서 부터 4바이트를 int로 변경하여 반환
	}
	
	
	
	/**
	 * int형을 byte배열로 바꿈<br>
	 * @param integer
	 * @param order
	 * @return
	 */
	public static byte[] shortTobyte(short shortnum, ByteOrder order) {
		
		ByteBuffer buff = ByteBuffer.allocate(Short.SIZE/8);
		buff.order(order);
 
		// 인수로 넘어온 integer을 putInt로설정
		buff.putShort(shortnum);
 
		return buff.array();
	}
 
	/**
	 * byte배열을 int형로 바꿈<br>
	 * @param bytes
	 * @param order
	 * @return
	 */
	public static short byteToShort(byte[] bytes, ByteOrder order) {
 
		ByteBuffer buff = ByteBuffer.allocate(Short.SIZE/8);
		buff.order(order);
 
		// buff사이즈는 4인 상태임
		// bytes를 put하면 position과 limit는 같은 위치가 됨.
		buff.put(bytes);
		// flip()가 실행 되면 position은 0에 위치 하게 됨.
		buff.flip();
 
		System.out.println("byteToShort : " + buff);
 
		return buff.getShort(); // position위치(0)에서 부터 4바이트를 int로 변경하여 반환
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 스트림을 인자로 받아서 바이트배열로 리턴함.
	 * @param f
	 * @return
	 */
	public static byte[] writeBinaryFromStream(InputStream in) {
		byte[] resultBynary = null;

		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int readCnt = 0;

			byte[] buf = new byte[1024];
			readCnt = in.read(buf);
			System.out.println("readCnt : "+readCnt);
			
			baos.write(buf, 0, readCnt);
			baos.flush();
			resultBynary = baos.toByteArray();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultBynary;
	}
	
	
	
	
	public static String readFile(File f)  {
		
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			
			
			String readStr = null;
			while((readStr = br.readLine())!= null){
				sb.append(readStr).append("\r\n");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return sb.toString();
		
	}
	
	
	/**
	 * 파일을 인자로 받아서 바이트배열로 리턴함.
	 * @param f
	 * @return
	 */
	public static byte[] readByteFromFile(File f) {
		byte[] resultBynary = null;

		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		try {
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(f);
			int readCnt = 0;

			byte[] buf = new byte[1024];
			while ((readCnt = fis.read(buf)) != -1) {
				baos.write(buf, 0, readCnt);
			}
			baos.flush();
			resultBynary = baos.toByteArray();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultBynary;
	}
	
	
	
	

	/**
	 * 바이트배열을 받아서 파일로 저장함.
	 * @param f
	 * @return
	 */
	public static void saveFileFromByte(File f ,byte[] b) {
		
		FileOutputStream fos = null ; 
		ByteArrayInputStream bis = null;

		
		try {

			bis = new ByteArrayInputStream(b);
			fos = new FileOutputStream(f);

			int readCnt = 0;

			byte[] buf = new byte[1024];
			while ((readCnt = bis.read(buf)) != -1) {
				fos.write(buf, 0, readCnt);
			}
			fos.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 블록사이즈에 맞도록 패딩을 덧붙여서 리턴한다.(패딩은 스페이스)
	 * @param b
	 * @param blockSize
	 * @return
	 */
	public static byte[] createPaddingBytes(byte[] b, int blockSize) {
		int bLen = b.length;
		
		int dNum = 16;
		int rNum = (int) Math.ceil(bLen / (double)dNum);
		
		int addBytesLen = dNum*rNum - bLen;
		byte[] addBytes = new byte[addBytesLen];
		for(byte addByte : addBytes){
			addByte=32;
		}
		
		return addBytes;
		
	}
	
	
	
	/**
	 * 소켓 InputStream 을 인자로 주어진 사이즈 만큼  바이트배열로 리턴함.
	 * @param input           : 소켓 InputStream
	 * @param dataLength      : 읽을 크기
	 * @param readBufferSize  : 읽기 버퍼 크기
	 * @return
	 * @throws Exception
	 */
	public static byte[] readByteFromSocketInputStream(InputStream input, int dataLength ,int readBufferSize) throws Exception  {
		
		byte[] buff = new byte[readBufferSize];
		//바이트 버퍼로 미리 메모리 할당해놓는것보다 읽어온 만큼 메모리에 쓰는것이 메모리 효율적일듯 하여 변경하였음.
//		ByteBuffer inBuffer = ByteBuffer.allocate(dataLength);  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		
		
		int n = 0;             //read 함수의 리턴값 (정수이면 읽어온 크기, -1 이면 읽어온게 없음.)
		int readedLength = 0;  //read된 크기
		
		
		while (true) {
			
			
			// 1. 읽을 크기(dataLength) 가 (읽은 크기와 블록사이즈) 보다 크거나 같으면 블록사이즈 만큼 더 읽는다.
			// 2. 읽을 크기(dataLength) 가 (읽은 크기와 블록사이즈) 보다 작으면 읽을 크기에서 읽은 크기만큼 빼고 읽는다.
			// ex) dataLength = 100byte  ,  READ_BLOCK_SIZE = 32byte  , readedLength = 96byte
			//     --> 100 - 96  = 4byte 만큼만 읽어 들인다.
			
			n = input.read(buff, 0,	((dataLength >= (readedLength + readBufferSize)) ? readBufferSize	: (dataLength - readedLength)));
			
			
			
			if (n < 0) {  // -1 이 리턴되면 마지막까지 다 읽은 것임.
				if (dataLength > readedLength) { // 읽을 크기보다 읽어온크기가 작으면 에러.
					throw new Exception("failed to read data from socket");
				}
			}
			
			else if (n == 0) {
				Thread.sleep(100);
				continue;
			}
			
			else{
				
				readedLength += n;
				baos.write(buff, 0, n);
//				inBuffer.put(buff, 0, n); //바이트버퍼에 put 읽어온 버퍼를 put 해줌.
				if (readedLength >= dataLength) {
					break;
				}
			}
			

		}
		
		baos.flush();
		return baos.toByteArray();
//		return inBuffer.array();
	}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		String hex = "3000";
		byte[] s  = DatatypeConverter.parseHexBinary(hex);
		
		short sn = ByteUtils.byteToShort(s, ByteOrder.LITTLE_ENDIAN);
		
		System.out.println(sn);
		
		
		
		int testInt = 1025;
 
		System.out.println("===== ByteOrder.LITTLE_ENDIAN  ===");
	    byte[] bytes = intTobyte(testInt, ByteOrder.LITTLE_ENDIAN);
 
	    for (int i = 0; i < bytes.length; i++) {
			System.out.printf("[%02X]", bytes[i]);
		}
		System.out.println();
		System.out.println(byteToInt(bytes, ByteOrder.LITTLE_ENDIAN));
 
		System.out.println();
		System.out.println("===== ByteOrder.BIG_ENDIAN  ===");
	    bytes = intTobyte(testInt, ByteOrder.BIG_ENDIAN);
 
	    for (int i = 0; i < bytes.length; i++) {
			System.out.printf("[%02X]", bytes[i]);
		}
		System.out.println();
		System.out.println(byteToInt(bytes, ByteOrder.BIG_ENDIAN));
	}

}
