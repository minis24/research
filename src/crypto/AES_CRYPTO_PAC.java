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


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;


public class AES_CRYPTO_PAC {
	
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String USER_DIR_SEPARATOR = File.separator;

	/**
	 * 알고리즘/모드/패딩방식 지정
	 */
//	private static final String transformation_ecb = "AES/ECB/PKCS5Padding";
//	private static final String TRANSFORMATION_CBC = "AES/CBC/PKCS5Padding";
	private static final String TRANSFORMATION_CBC = "AES/CBC/NoPadding";

	private static final String ALGORITHM = "AES";
	private static final String HMAC_ALGORITHM = "HmacSHA256";
	
	/**
	 * 고정IV [참고: IV를 한블록 추가해서 주고 받는 방식, nounce를 이용하여 생성하는 방법이 있다.]
	 * IV는 블록SIZE와 동일하다. AES는 128bit 블록암호화 알고리즘 이므로 IV = 128bit(16byte) 
	 */
	private static final String IV_B64_STR = "CYWk0iMm3iN0v9WorOGzJw=="; 
	public static final String IV_STR = "00000000000000000000000000000000"; 
	private static final String SECRET_KEY_256_B64_STR = "1os1lftlA73WF92uyeDVo/+Cu64wuOGOtxpQSQ3BYZE=";
	private static final String SECRET_KEY_128_B64_STR = "gr6K+RFfqfYxlAxvJYklxw==";

	
	/**
	 * MAC인증코드생성 (메시지 변조 여부 확인에 사용)
	 * 인증키(MAC_KEY)는 서로 교환
	 */
	private static final String DEFAULT_MAC_KEY = "ry1+J+367TOtf2mr28jwdvlHaWQCHPc0CKDE9h2Mk1U="; 
	
	
	
	
	

	
	/**
	 * IV를 생설할 16byte 랜덤 바이트를 추출한다.
	 * 추출한 랜덤 바이트는 Base64로 인코딩한 문자열로 변환하여 리턴한다.
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String makeIV() throws UnsupportedEncodingException{
		
		SecureRandom sramdom = new SecureRandom();
		byte[] ivData = new byte[16];
		for(int i = 0 ; i < 1000 ; i ++){
			sramdom.nextBytes(ivData);
		}
		return  new String(Base64.encodeBase64(ivData),"UTF-8");
	}
	
	
	public static byte[] getIV () throws UnsupportedEncodingException{
		return Base64.decodeBase64(IV_B64_STR.getBytes("UTF-8"));
	}
	
	
	public static IvParameterSpec getIV_1 () throws UnsupportedEncodingException{
		return new IvParameterSpec(DatatypeConverter.parseHexBinary(IV_STR) );
	}
	
	
	/**
	 * 비밀키 생성
	 * @param keysize
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey generateSecreyKey(int keysize) throws NoSuchAlgorithmException{
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		keyGenerator.init(keysize);
		return keyGenerator.generateKey();
	}
	
	/**
	 * 비밀키를 Base64로 Encoding
	 * @param skey
	 * @return
	 */
	public static String encSecrectKeyToB64Str(SecretKey skey){
		return new String(Base64.encodeBase64(skey.getEncoded()));
	}

	
	/**
	 * 비밀키 리턴(256)
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static SecretKey getSecretKey_256(String skey_b64Str) throws UnsupportedEncodingException {
		String secret_key_b64Str = null;
		if(skey_b64Str == null){
			secret_key_b64Str = SECRET_KEY_256_B64_STR;
		}
		else{
			secret_key_b64Str = skey_b64Str;
		}
		
		byte[] secretKey = Base64.decodeBase64(secret_key_b64Str.getBytes("UTF-8"));
		SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
		return keySpec;
	}

	/**
	 * 비밀키 리턴(128)
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static SecretKey getSecretKey_128(String skey_b64Str) throws UnsupportedEncodingException {
		String secret_key_b64Str = null;
		if(skey_b64Str == null){
			secret_key_b64Str = SECRET_KEY_128_B64_STR;
		}
		else{
			secret_key_b64Str = skey_b64Str;
		}
		byte[] secretKey = Base64.decodeBase64(secret_key_b64Str.getBytes("UTF-8"));
		SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
		return keySpec;
	}
	
	
	
	/**
	 * AES 128 암호문 (운영모드:CBC) 리턴(Base64로 Encoding 된 문자열)
	 * @param secretKey
	 * @param plainStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String encryptAes128Str(SecretKey secretKey , String plainStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		
		IvParameterSpec ivParameterSpec = new IvParameterSpec(getIV());
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
		
		byte[] plain = plainStr.getBytes("UTF-8");
		byte[] encrypt = cipher.doFinal(plain);
		String encryptStr =new String( Base64.encodeBase64(encrypt));
		
		return encryptStr;
				
	}
	
	
	public static byte[] encryptAes128(SecretKey secretKey , byte[] plainBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,getIV_1());
		
		byte[] encrypt = cipher.doFinal(plainBytes);
		
		return encrypt;
				
	}
	
	/**
	 * AES 128 (운영모드:CBC) 복호문(평문)리턴
	 * @param secretKey
	 * @param encStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String decryptAes128Str(SecretKey secretKey , String encStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		IvParameterSpec ivParameterSpec = new IvParameterSpec(getIV());
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
		
		byte[] decryptArr = Base64.decodeBase64(encStr.getBytes());
		byte[] decrypt = cipher.doFinal(decryptArr);
		
		
		String plainText = new String(decrypt,"UTF-8");
		return plainText;
		
		
	}
	
	

	public static byte[] decryptAes128(SecretKey secretKey , byte[] encBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.DECRYPT_MODE, secretKey,getIV_1());
		
		byte[] decrypt = cipher.doFinal(encBytes);
		return decrypt;
	}
	
	/**
	 * AES 256 (운영모드:CBC) 암호문 리턴(Base64로 Encoding 된 문자열)
	 * @param secretKey
	 * @param plainStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String encryptAes256Str(SecretKey secretKey , String plainStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		IvParameterSpec ivParameterSpec = new IvParameterSpec(getIV());
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
		
		
		byte[] plain = plainStr.getBytes("UTF-8");
		byte[] encrypt = cipher.doFinal(plain);
		String encryptStr =new String( Base64.encodeBase64(encrypt));
		
		return encryptStr;
				
	}
	

	
	/**
	 * AES 256 (운영모드:CBC) 복호문(평문)리턴
	 * @param secretKey
	 * @param encStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String decryptAes256Str(SecretKey secretKey , String encStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		IvParameterSpec ivParameterSpec = new IvParameterSpec(getIV());
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_CBC);
		cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
		
		byte[] decryptArr =  Base64.decodeBase64(encStr.getBytes());
		byte[] decrypt = cipher.doFinal(decryptArr);
		
		
		String plainText = new String(decrypt,"UTF-8");
		return plainText;
		
		
	}
	

	
	
	/**
	 * MAC인증키 생성
	 * @param keysize
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey generateHMAC_Key() throws NoSuchAlgorithmException{
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		return keyGenerator.generateKey();
	}
	
	/**
	 * DEFAULT_MAC 키 리턴(256)
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static SecretKey getDefaultHMacKey() throws UnsupportedEncodingException {
		byte[] makKey = Base64.decodeBase64(DEFAULT_MAC_KEY.getBytes("UTF-8"));
		SecretKeySpec keySpec = new SecretKeySpec(makKey, "HmacSHA256");
		return keySpec;
	}

	
	/**
	 * HMAC 인증코드 리턴(Base64로 Encoding 된 문자열)
	 * @param secretKey
	 * @param plainStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String createHMAC_CODE(SecretKey hmackey , String msgstr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		mac.init(hmackey);
		byte[] macdata = mac.doFinal(msgstr.getBytes("UTF-8"));
		return new String( Base64.encodeBase64(macdata));				
	}
	
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception  {
//
//		secretKey_b64Str : O1f/F0C6gNJJpjbTGkqayccyiT9MCblQmM9C0jkuNVo=
//				encText : OEtDQkEwOVdpYVd1THBqeUFjb1NnZ1l3VEtuWnk2VEJHeklxdCtaNjVuaHRxYmNPb3pvWVJ4d3lqcWRjQ2ZDcElEQW9nbHJZSnJFN0NubWVHVUdtSnl4cVFiRjFMYlVvakQ2ZHRzQ1ptc09ZRTRGS1FyMXN0ZGR3ams4VXJUVy9ZdGU1aEk1OEFBNjRUMjljRms3STEyVHd4Rlhnd3lMMmJsQ3MzUXhnOFBhSTdObm1aVFhQL3RUZm1mUXFmVjVtVklZbTcxMExpRUVGTmY5SzhnMVdvbUZQaTY3aFpxdFJxRytESVlGdmJiK1RzOGRaNzlrS1BvRCtUQzl5ekpZandKaFYra3dRYkVlRzlnSFZxVFlmK0hON0syL3hEdnJNaEVWanNSV1F0MFR5ajh0dFNYSmFwZzFvakJqN1VUeFM=

		
		
		
		String bbb = "MIAGCSqGSIb3DQEHA6CAMIACAQIxggEhMIIBHQIBAoAFZHVtbXkwDQYJKoZIhvcNAQEBBQAEggEAAHmYF8qUrhHii36ErhYospG0jSFy9alM1oH37UjLmskeGXM0iNzbHbkBGXHfG7FMxdhG/wfWX+xpUHTbNsgKdDzJpjNw0tANO0De+ljqffihkIdc8B+zS0OhN5zsI7lU5mbU+uXHQK/JQiSbIZS+JZ9GvK1Wd7800ODRFdSWUQ07zzFT6lF5/IUMYXW1JoMvch9psJpRZ8RGJqofHxk9CzZvv5bZVmyyQJgAgKhfcOpz6C9bpoPgV/5DqMLuFS8VlNIWlQoOKUgQeJeHAHiMwMdU2iVEsCHoSUTyaNrfxcQ7fW0uuD+rTAZtlDGXK+dEtxy5pXL17h8LX5Uby01eXzCABgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBAvBm0VdukKRZ1931mt0+K4oIAEcMUxB8BoeLLJiH8Z5ER+JpFbYhTlx+2N31lY5kciFnVdFuif5CNXNU47xyow2mmIVdR4udWUs70dO/KYWdrY8J0gqR4Da3g/pLS6nrWMqFy1oyw0AcClt2HJjKa8naFEcov5cxNXB97XN84Ho8ogwk0AAAAAAAAAAAAA";
		String ccc = "ew0KICJwYW4iOiIxMjM0MTg5NzQxMjE0NTU0IiwNCiAiZXhwIjoiMTExNiIsDQogImN2diI6IjQ1NiIsDQ ogImNhcmRIb2xkZXJOYW1lIjoiZm9vIGJhciINCn0=";
		System.out.println(new String(Base64.decodeBase64(ccc.getBytes())));
		System.out.println(new String(Base64.decodeBase64(bbb.getBytes())));
		
		SecretKey sk = getSecretKey_256("O1f/F0C6gNJJpjbTGkqayccyiT9MCblQmM9C0jkuNVo=");
		String enc = "OEtDQkEwOVdpYVd1THBqeUFjb1NnZ1l3VEtuWnk2VEJHeklxdCtaNjVuaHRxYmNPb3pvWVJ4d3lqcWRjQ2ZDcElEQW9nbHJZSnJFN0NubWVHVUdtSnl4cVFiRjFMYlVvakQ2ZHRzQ1ptc09ZRTRGS1FyMXN0ZGR3ams4VXJUVy9ZdGU1aEk1OEFBNjRUMjljRms3STEyVHd4Rlhnd3lMMmJsQ3MzUXhnOFBhSTdObm1aVFhQL3RUZm1mUXFmVjVtVklZbTcxMExpRUVGTmY5SzhnMVdvbUZQaTY3aFpxdFJxRytESVlGdmJiK1RzOGRaNzlrS1BvRCtUQzl5ekpZandKaFYra3dRYkVlRzlnSFZxVFlmK0hON0syL3hEdnJNaEVWanNSV1F0MFR5ajh0dFNYSmFwZzFvakJqN1VUeFM=";
		String encArr = new String(Base64.decodeBase64(enc.getBytes()));
		System.out.println(encArr);
		
		String dec    = AES_CRYPTO_PAC.decryptAes256Str(sk, encArr);
		
		System.out.println(dec);
		
		//바디 엔코딩
		String device_id = "zaqwsxcde";
		String device_trans_no = "20150513194202";
		String secret_random = "pQUfjeuw1BWMrxQyF1KxgivI6B3Je18wIB3xO+mU4HI=";
		String device_dt = "20150513194226";
		
		
		
		
		//hmac_code 생성
		byte[] makKey = Base64.decodeBase64("G6yoJq/WVhK0SEnn1q5o5vryYCwwkevYlcCP4M1J358=".getBytes("UTF-8"));
		SecretKeySpec keySpec = new SecretKeySpec(makKey, "HmacSHA256");
		
		/**
		 * HMAC_CODE 생성시 문자열을 아래와 같은 순서로 클라이언트와 동일하게 맞추어야 한다.
		 * W_DEVICE_ID + W_DEVICE_TRANS_NO + W_SECRET_RANDOM + W_DEVICE_DT
		 */
		String bodyValueStr  = new StringBuffer().append(device_id)
                .append(device_trans_no)
                .append(secret_random)
                .append(device_dt).toString();

		String hmac_code = createHMAC_CODE(keySpec, bodyValueStr);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println(hmac_code);
	
		
		
		
		
		
		/******************************************************************************************/
		//AES128 암호화 테스트
		/******************************************************************************************/
//		String plainText1 ="1234567890123456222222222222222222222222222222";
//		String kB64Str = SECRET_KEY_128_B64_STR;
//		SecretKey skey = getSecretKey_128(kB64Str);
//		String encryptStr128 = SMCORE_AES_CRYPTO_PAC.encryptAes128Str(skey,plainText1);
//		System.out.println(encryptStr128);
//		System.out.println(Base64.decodeBase64(encryptStr128.getBytes()).length);
//		System.out.println();
//		
//		String decryptStr128 = SMCORE_AES_CRYPTO_PAC.decryptAes128Str(skey,encryptStr128);
//		System.out.println("decryptStr128 : " +decryptStr128);

		
		
//		/******************************************************************************************/
//		//AES256 암호화 테스트
//		/******************************************************************************************/
//		SecretKey skey = SMCORE_AES_CRYPTO_PAC.getSecretKey_256(null);
//		
//		String encryptStr256_1 = SMCORE_AES_CRYPTO_PAC.encryptAes256Str(skey,plainText1);
//		String encryptStr256_2 = SMCORE_AES_CRYPTO_PAC.encryptAes256Str(skey,plainText1);
//		String encryptStr256_3 = SMCORE_AES_CRYPTO_PAC.encryptAes256Str(skey,plainText1);
//		System.out.println("encryptStr256_1 : \r\n"+encryptStr256_1);
//		System.out.println("encryptStr256_2 : \r\n"+encryptStr256_2);
//		System.out.println("encryptStr256_3 : \r\n"+encryptStr256_3);
//		
//		
//		String decryptStr256_1 = SMCORE_AES_CRYPTO_PAC.decryptAes256Str(skey,encryptStr256_1);
//		String decryptStr256_2 = SMCORE_AES_CRYPTO_PAC.decryptAes256Str(skey,encryptStr256_2);
//		String decryptStr256_3 = SMCORE_AES_CRYPTO_PAC.decryptAes256Str(skey,encryptStr256_3);
//		System.out.println("decryptStr256 : \r\n" +decryptStr256_1);
//		System.out.println("decryptStr256 : decryptStr256_1" +decryptStr256_2);
//		System.out.println("decryptStr256 : decryptStr256_1" +decryptStr256_3);
//		
		
		
		

	}





}
