package crypto;

/**
 * SMCORE CRYPTO 패키지
 * RSA 키쌍관리
 * RSA 공개키 암/복호화,개인키 암/복호화 (공개키,개인키의 키쌍이 맞아야 한다)
 * AES 암/복호화
 * 테스트 지원
 * @author (주)스마트코어 기술연구소 
 * @since 2015.04.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일            수정자           수정내용
 *  ------------    --------    ---------------------------
 *   2015.04.14      김장관          최초 생성
 *
 * </pre>
 */


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;


public class SECURE_RANDOM_PAC {
	

	
	
	/**
	 * 보안 랜덤 바이트를 추출한다.
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createRandomStr(int len) throws UnsupportedEncodingException{
		
		SecureRandom sramdom = new SecureRandom();
		byte[] rByte = new byte[len];
		for(int i = 0 ; i < 1000 ; i ++){
			sramdom.nextBytes(rByte);
		}
		return  new String(rByte,"UTF-8");
	}
	

	
	/**
	 * 보안 랜덤 바이트를 추출한다.
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] createRandomBytes(int len) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		
		SecureRandom sramdom = SecureRandom.getInstance("SHA1PRNG");
		byte[] rByte = new byte[len];
		for(int i = 0 ; i < 1000 ; i ++){
			sramdom.nextBytes(rByte);
		}
		return  rByte;
	}
	
	
	/**
	 * 보안 랜덤 바이트를 추출한다.
	 * 추출한 랜덤 바이트는 Base64로 인코딩한 문자열로 변환하여 리턴한다.
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createRandomBase64Str(int len) throws UnsupportedEncodingException{
		
		SecureRandom sramdom = new SecureRandom();
		byte[] rByte = new byte[len];
		for(int i = 0 ; i < 1000 ; i ++){
			sramdom.nextBytes(rByte);
		}
		return  new String(Base64.encodeBase64(rByte),"UTF-8");
	}
	
	/**
	 * 보안 랜덤 수를 추출한다.
	 * 추출한 랜덤 바이트는 Base64로 인코딩한 문자열로 변환하여 리턴한다.
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createRandomInt(int min,int max) {
		
		SecureRandom sramdom = new SecureRandom();
		int r = 0;
		for(int i = 0 ; i < 10 ; i ++){
			r= sramdom.nextInt(max);
		}
		
		return r < min ? createRandomInt(min,max) :String.valueOf(r);
	}
	
	

	
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception  {

	
//		001+1432012944004+랜덤 4자리
		long cTime = System.currentTimeMillis();
		String i = createRandomInt(1000,10000);
		long lTime = System.currentTimeMillis();
		System.out.println(lTime - cTime );
		System.out.println(i);
		
		
		
		
		
//		rtnVal = hu.hashEncode(Integer.toString(rRandom)).substring(0, 32);
		
//		System.out.println(rRandom);
		
		
		
		
//		SecureRandom r = new SecureRandom();
//		Integer i = Math.abs(r.nextInt());
//		System.out.println(i);
	}





}
