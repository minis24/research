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
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class RSA_CRYPTO_PAC {

	
	//자바 암호화 
	// JCA(JAVA CRYPTOGRAPHY ARCHYTECTURE) 와 3가지 확장패키지로 구성 (JCE,JAAS,JSSE)
	//  : 전자서명 지원
	//  : 키싸생성,서명,메시지다이제스트,인증서들을 지원
	//    추상 클래스와 인터페이스들의 집합
	//    실제 구현은 서드파티 나 SUN에 해당하는 프로바이더에서 구현함.
	
	//  1. JCE (JAVA CRYPTOGRAPHY EXTENSION)
	//    --> JCA에서 생성한 키쌍으로 비대칭 암호와 지원
	//  2. JAAS (JAVA AUTHENTIFICATION AND AUTHORIZATION SERVICE)
	//    --> 사용자 인증과 허가
	//  3. JSSE (JAVA SECURE SOCKET EXTENSION)
	//    --> 자바소켓을 확장하여 보안SSL전송 레이어를 제공

	
	
	
	private static final String USER_DIR_SEPARATOR = File.separator;
	private static final String CRYPTO_ALGORITHM = "RSA";
	
	/**
	 * RSAES (RSA Encrypt Scheme)
	 * --> PKCS1Padding
	 * --> OAEP 난수화 패딩 :PKCS1Padding 보다 보안강도가 높다.(최대 190byte)
	 */
	private static final String RSAES_OAEP_PADDING_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	private static final String RSA_ECB_PKCS1_PADDIN_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private static final String RSAES_OAEP_BC_ALGORITHM = "RSA/None/OAEPWithSHA-256AndMGF1Padding";
	private static final String RSAES_OAEP_ALGORITHM = RSAES_OAEP_BC_ALGORITHM;
	
	
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String CRYPTO_SAVE_PATH = USER_DIR+USER_DIR_SEPARATOR+"cryptokey";
	private static final String CRYPTO_PUBKEY_FILENAME = "rsaX509pubkey.key";
	private static final String CRYPTO_PRIKEY_FILENAME = "rsaPKCS8pubkey.key";
	
	
	
	static{
		Security.addProvider(new BouncyCastleProvider());
		Provider provider = Security.getProvider("BC");
		System.out.println(provider);
		if(provider != null){
			System.out.println("Bbbbbbbbbbbbbbbbbbbbbbbbb");
		}
	}
	
	
	/**
	 * 키쌍을 생성한다.
	 * @param algorithm "RSA"
	 * @param nKeyLen   "2048","1024"
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static KeyPair generateKeyPair(int nKeyLen) throws NoSuchAlgorithmException {
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance(CRYPTO_ALGORITHM);
		genKeyPair.initialize(nKeyLen);
		return genKeyPair.generateKeyPair();
	}
	
	
	
	
	
	/**
	 * RSA 공개키를 이용하여 암호화 한다. 이렇게 암호화 된 데이터는 키쌍이 맞는 개인키로만 풀 수 있다.
	 * 암호화 할 수 있는 데이터의 길이는 최대 245bytes 이다.
	 * 암호화 된 결과는 1024 키의 경우 128bytes, 2048 키의 경우 256bytes 가 출력된다.
	 * @param pubKey RSA 공개키
	 * @param plainText 암호화 할 임의의 데이터
	 * @return 암호화 된 데이터 리턴
	 * @throws Exception
	 */
	public static byte[] doRSAPubKeyEncrypt(PublicKey pubKey, byte[] plainText) throws Exception {
		Cipher cipher = Cipher.getInstance(RSAES_OAEP_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(plainText);
	}
	
	/**
	 * RSA 개인키로 복호화를 수행한다. 이때 암호화 된 데이터는 키쌍이 맞는 공개키로 암호화 한 데이터의 경우에만 복호화가 가능하다.
	 * @param priKey RSA 개인키
	 * @param cipherText 암호화 된 데이터
	 * @return 복호화 된 데이터 리턴
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception 복호화 실패
	 */
	public static byte[] doRSAPriKeyDecrypt(PrivateKey priKey, byte[] cipherText) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
		Cipher cipher = Cipher.getInstance(RSAES_OAEP_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return cipher.doFinal(cipherText);
	}

	
	
	
	/**
	 * RSA 개인키를 이용하여 암호화 한다. 이렇게 암호화 된 데이터는 키쌍이 맞는 공개키로만 풀 수 있다.
	 * 암호화 할 수 있는 데이터의 길이는 최대 117bytes 이다.
	 * 암호화 된 결과는 1024 키의 경우 128bytes, 2048 키의 경우 256bytes 가 출력된다.
	 * @param priKey RSA 개인키
	 * @param plainText 암호화 할 임의의 데이터
	 * @return 암호화 된 데이터 리턴
	 * @throws Exception
	 */
	public static byte[] doRSAPriKeyEncrypt(PrivateKey priKey, byte[] plainText) throws Exception {
		Cipher cipher = Cipher.getInstance(RSAES_OAEP_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		return cipher.doFinal(plainText);
	}
	
	
	/**
	 * RSA 공개키로 복호화를 수행한다. 이때 암호화 된 데이터는 키쌍이 맞는 개인키로 암호화 한 데이터의 경우에만 복호화가 가능하다.
	 * @param pubKey RSA 공개키
	 * @param cipherText 암호화 된 데이터
	 * @return 복호화 된 데이터 리턴
	 * @throws Exception 복호화 실패
	 */
	public static byte[] doRSAPubKeyDecrypt(PublicKey pubKey, byte[] cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(RSAES_OAEP_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(cipherText);
	}
	
	
	
	
	/**
	 * RSA 키를 이용하여 전자서명값을 생성한다.
	 * 임의의 데이터 길이는 제한이 없다.
	 * 서명값 결과는 1024 키의 경우 128bytes, 2048 키의 경우 256bytes 가 출력된다.
	 * @param hashAlg 서명 시 사용할 해쉬 알고리즘
	 * @param priKey 개인키
	 * @param plainText 전자서명 하고자 하는 임의의 데이터
	 * @return 서명값 리턴
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws Exception 서명값 생성 실패 시 발생, 지원하지 않는 알고리즘 등
	 */
	public static byte[] doRSASign(String hashAlg, PrivateKey priKey, byte[] plainText) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		String algorithm = hashAlg +"with" + priKey.getAlgorithm();
		Signature sign = Signature.getInstance(algorithm);
		sign.initSign(priKey);
		sign.update(plainText);
		return sign.sign();
	}

	/**
	 * 전자서명 값을 검증한다.
	 * @param hashAlg 전자 서명시 사용한 해쉬 알고리즘
	 * @param pubKey 공개키
	 * @param plainText 전자서명 한 원본 데이터
	 * @param signature 전자서명 값
	 * @return 전자서명 검증 성공 여부
	 * @throws Exception 전자서명 검증 실패
	 */
	public static boolean doRSAVerify(String hashAlg, PublicKey pubKey, byte[] plainText, byte[] signature) throws Exception {
		String algorithm = hashAlg +"with" + pubKey.getAlgorithm();
		Signature sign = Signature.getInstance(algorithm);
		sign.initVerify(pubKey);
		sign.update(plainText);
		return sign.verify(signature);
	}
	
	
	
	
	
	
	
	
	/**
	 * 테스트 지원 메서드 ( 생성된 키쌍을 파일로 저장함)
	 * @throws NoSuchAlgorithmException
	 */
	private static void saveRSAKeyPair() throws NoSuchAlgorithmException {
		System.out.println(CRYPTO_SAVE_PATH);
		
		
		
		// 키쌍 생성
		KeyPair keyPair = RSA_CRYPTO_PAC.generateKeyPair(2048);
		
		PublicKey  mPubKey = keyPair.getPublic();
		PrivateKey mPriKey = keyPair.getPrivate();
		
		
		File publeyFile = new File(new File(CRYPTO_SAVE_PATH),CRYPTO_PUBKEY_FILENAME);
		File prileyFile = new File(new File(CRYPTO_SAVE_PATH),CRYPTO_PRIKEY_FILENAME);
		
		System.out.println(publeyFile);
		System.out.println(publeyFile);
		
		ByteUtils.saveFileFromByte(publeyFile, mPubKey.getEncoded());
		ByteUtils.saveFileFromByte(prileyFile, mPriKey.getEncoded());
		
		
	}
	
	
	
	


	public static void keypair_verify(byte[] prikey_bytes, byte[] pubkey_bytes) throws Exception{
				
				KeyFactory kf = KeyFactory.getInstance("RSA");
				PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(prikey_bytes);
				PrivateKey priKey = kf.generatePrivate(pkcs8Spec);
				System.out.println("PRIKEY_ALG : " +priKey.getAlgorithm());

				
				X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(pubkey_bytes);
				PublicKey pubKey =  kf.generatePublic(x509Spec);
				System.out.println("PUBKEY_ALG : " +pubKey.getAlgorithm());
						
						
						
				
				String plainText = "abcde";
				System.out.println("plaintText : " + plainText);
				
				//RSA 암호화
				byte[] encByte = RSA_CRYPTO_PAC.doRSAPubKeyEncrypt(pubKey, plainText.getBytes());
				System.out.println("QA encByte : \r\n"+Base64.encodeBase64String(encByte));
				
				//RSA 복호
				byte[] decByte =  RSA_CRYPTO_PAC.doRSAPriKeyDecrypt(priKey, encByte);
				System.out.println("decByte : \r\n" +new String(decByte) );

			
				
	}
	
	private static byte[] readPublicKey_from_pubkeyFile(File pubkeyFile) {
		byte[] pubKey_bytes = ByteUtils.readByteFromFile(pubkeyFile);
		String pubkey_str = new String(pubKey_bytes);
		System.out.println(pubkey_str);

		String csr_pubkey_prefix = "-----BEGIN PUBLIC KEY-----";
		String csr_pubkey_sufix = "-----END PUBLIC KEY-----";
		int s_index = pubkey_str.indexOf(csr_pubkey_prefix);
		int e_index = pubkey_str.indexOf(csr_pubkey_sufix);
		pubkey_str = pubkey_str.substring(s_index+csr_pubkey_prefix.length(), e_index).trim();
		pubKey_bytes = Base64.decodeBase64(pubkey_str);
//		System.out.println(pubkey_str);
		
		return pubKey_bytes;
	}


	
	public static void main(String[] args) throws Exception {
		
		//키페어 파일 설정
		String keyDirectory = "C:/QryDevSpace/workspace_fuzepay/fuzepay_fpserver/src/main/webapp/WEB-INF/cert/jwt_sign";
		String privateKeyFilename = "BTS-JRF-QA-DER.key";
		String publicKeyFilename = "BTS-JRF-QA-PUB-DER.key";

		//개인키 파일
		File prikeyFile = new File(keyDirectory,privateKeyFilename);
		//공개키 파일
		File pubkeyFile = new File(keyDirectory,publicKeyFilename);
		
		
		//개인키 읽어오기
		byte[] priKey_bytes = ByteUtils.readByteFromFile(prikeyFile);
		System.out.println("prikey:[" +DatatypeConverter.printHexBinary(priKey_bytes));
		
		//공개키 읽어오기
		byte[] pubKey_bytes = readPublicKey_from_pubkeyFile(pubkeyFile);
		System.out.println("pubkey:[" +DatatypeConverter.printHexBinary(pubKey_bytes));
		
		
		String publickey_hex ="30820122300D06092A864886F70D01010105000382010F003082010A0282010100B1BB276B771ACC8E64DEE43FDF76A585EF91CA23E6ED440EC616909EA75D0A3A11B9648A2385FB70C82A1B9CAD79E5DA3DE88A923EF33FC939C86661A0B09FE118BF59F6AC06F9DCC63BF773529933EB0F2D2AF50EA66AF3D6334F3DE11B896531BFB178E83C15144DDB97593BECB90A61F092429397B261621C793A91D1F80B618DA0CF0AC5466BD4176286B5F189C47001297932132B8F8926DFCF9451F3C48748F754659A04FD9025D0499EA0C22E09902920B944D00813446E641DCF103799B189AD9344DD383775C11B0CD28B7AB7CE4DC4D32D3345DFF4C78863C85C821F1D31B1D24F0FE5B3DB41F4F7F9A13BBA253ED6E2B893320B90BB5B7C9DBB030203010001";
		String privatekey_hex="308204BE020100300D06092A864886F70D0101010500048204A8308204A40201000282010100B1BB276B771ACC8E64DEE43FDF76A585EF91CA23E6ED440EC616909EA75D0A3A11B9648A2385FB70C82A1B9CAD79E5DA3DE88A923EF33FC939C86661A0B09FE118BF59F6AC06F9DCC63BF773529933EB0F2D2AF50EA66AF3D6334F3DE11B896531BFB178E83C15144DDB97593BECB90A61F092429397B261621C793A91D1F80B618DA0CF0AC5466BD4176286B5F189C47001297932132B8F8926DFCF9451F3C48748F754659A04FD9025D0499EA0C22E09902920B944D00813446E641DCF103799B189AD9344DD383775C11B0CD28B7AB7CE4DC4D32D3345DFF4C78863C85C821F1D31B1D24F0FE5B3DB41F4F7F9A13BBA253ED6E2B893320B90BB5B7C9DBB0302030100010282010100A9A0BB15C91E4DBF55AC99E2105037D865F71A1B3BEA95BB17FA2F5B0CD732CC8B600B0200B77E6C7CCE1B7E64DF63ADF0CA417F250F9A805E62D2BC38D67A312722722588B83BBF277FD24341C1309916EA0006D54DA5E4453AC15588C474549DAF9C1D8E60D1A988499DE0AFDDD6197FB25095B431E33128A78E588516060A9741C6DDCCE8CCCC97612EEB15C5270650AC97348038323F9C959DCBF9BC50E5EB129C80B536F44F90035C259EEFD214153DED18D5FC20C0F83ECC6E2555997A743EA1C77285D418B82D33C33DA22AA486FF364DCB5852076CB5508EEE7AAF0EB44418BE10CE19B11F40FEE8BD8769793B96323687BDCD1CD3BBA82EB1F37FD102818100DD92AB494286D82D5B44040D95D0725840BAE7EEEC07A32BD2D3BB3C998897FE48A5E1A9B9842EDA8F3278562CFF688506C1A7F41EC5CE842896023F72288D28D51FBCDE062EEDA43241DD1DE467254CD8FB023F73B93D355B2856671607E225FAE3AF190368A2C6C826390B9E73851590FED8386B4B1DE9FF309E03E36C57CB02818100CD589F41A78E6F01FDF81BDA8B1C1960857D4DF7291C39B0A418348250D1E6D269A455AD3815AED1BB594F8A540FE8A35EAA4C61DDE35ED39214DFE015A0499E8630E1F537412F9978868D8D2A4BE446761CC47EE539B2E2F147731D34E902C2405075590E805BB265810A1117CF33FEC04218FBE59DCE8F1BA6E00F335292A90281800AB6C9E38BFCFB3D0E39B6BC756F13E86290E7202D779D1344698A973E9155502D711FFD56042743D842C780DB422A6AAC4DF6F21DFED0A2A157C79A0BCA1C314A44F39C04CE861D3774E9DD452B1BA9A0A05A9678E10F2DA24FCC05C611F18486A235D84ECAE37C1B3785F0A2E3D11E9ED943D94E8EDE4E6F518982849FBB8B028181008FDAFDF6A8C387D10572C3509F13D8535E3122657C4F61BAF5BA5F4ECBFFC08CB7A580C011025FAE5E697AE7956C6FB5C3A3DA4ED57B6BFB1BE3B3D2F053923F9412BAB967342B7C808B02AF5A6A40497C853AC30B2318C713F0D21C2BECF119D4A4891112AFA98D98BE85A6E3DF2CB5D34A7AE63D3E086F091826EF7F2CE1C10281802C91C8489230AE036DF6C5CA11D7DC52D0AE119DBBB6285B1A551D1368FFF2A509919959ED4897A389E908AA215B42F828AFF4940D2BA27901D8D63D107EFD29F2130BE20E5F61DD92E679984D805E9A9C4EBB76C917EAE5B8857EE5BB308597439AAF81791661B3D9644EDADB5A7A6757DE343B895D1C913515322359524024"; 
		
		
		//키페어 검증
		keypair_verify(priKey_bytes,pubKey_bytes);	
		keypair_verify(DatatypeConverter.parseHexBinary(privatekey_hex),DatatypeConverter.parseHexBinary(publickey_hex));	
		
//		-----BEGIN PUBLIC KEY-----
//		MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAogdk+p1TxJouQyvpovyO
//		NmjwA3hCcKDchy5QvhFw8WLZVGqc3AkXCP4P3yHK4w0rgJWBAP9C4TCKehNI5iQA
//		WNrQsHzog7Ru1rkgNpts/nf1uJE0KWNhw1QGdGioWdo41wQJvKwexInCLty1ebpi
//		q6Wg9r4VqiZTb4S9NSgQOUWf/P7w9uZDRvMjLU6MHn1r20c0U0EB88AQ2BF4C5Dc
//		8IzlS84D4fahhu9uQOhjxisrAgWoacaUpA4aFgxUODKP/MjgtbiTkfageyKOzOcq
//		ZJS3Yq+JbdqC71x0XcrOydvEIv1wW5EJd842L+P2oIDrSaczV02MCily5VYTkSbj
//		mQIDAQAB
//		-----END PUBLIC KEY-----

//		String pubkey_str_ = new StringBuffer("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAogdk+p1TxJouQyvpovyO")
//				.append("NmjwA3hCcKDchy5QvhFw8WLZVGqc3AkXCP4P3yHK4w0rgJWBAP9C4TCKehNI5iQA")
//				.append("WNrQsHzog7Ru1rkgNpts/nf1uJE0KWNhw1QGdGioWdo41wQJvKwexInCLty1ebpi")
//				.append("q6Wg9r4VqiZTb4S9NSgQOUWf/P7w9uZDRvMjLU6MHn1r20c0U0EB88AQ2BF4C5Dc")
//				.append("8IzlS84D4fahhu9uQOhjxisrAgWoacaUpA4aFgxUODKP/MjgtbiTkfageyKOzOcq")
//				.append("ZJS3Yq+JbdqC71x0XcrOydvEIv1wW5EJd842L+P2oIDrSaczV02MCily5VYTkSbj")
//				.append("mQIDAQAB").toString();
				
//		keypair_verify(priKey_bytes,Base64.decodeBase64(pubkey_str_));		
		
		
		
		
		
		
//		
//		KeyPair keyPair = generateKeyPair(2048);
//		PublicKey  mPubKey = keyPair.getPublic();
//		PrivateKey mPriKey = keyPair.getPrivate();
//		
//		
//		
//		System.out.println(DatatypeConverter.printHexBinary(mPubKey.getEncoded()));
//		System.out.println(DatatypeConverter.printHexBinary(mPriKey.getEncoded()));
//		
//		String plainText= "abc";
//		byte[] G = RSA_CRYPTO_PAC.doRSASign("SHA256", mPriKey, plainText.getBytes());
//		
//		
//		
//		
//	
//		
//		
//		
//		X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator(); 
//		
//        
//        v3CertGen.setSerialNumber(new BigInteger("1234"));  
//         
//        v3CertGen.setIssuerDN(new X509Principal( "CN=repien, OU=Test Team, O=Company, L=Seoul, C=KR"));  
//        v3CertGen.setNotBefore(new Date( System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30));  
//        v3CertGen.setNotAfter(new Date(  System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365*10)));  
//        v3CertGen.setSubjectDN(new X509Principal( "CN=repien, OU=Test Team, O=Company, L=Seoul, C=KR")); 
// 
//        v3CertGen.setPublicKey(keyPair.getPublic());  
//        v3CertGen.setSignatureAlgorithm("SHA1withRSA");
// 
//        X509Certificate PKCertificate = v3CertGen.generate(keyPair.getPrivate());
// 
//        System.out.println("Cert : " + PKCertificate);
//         
//        File f = new File("/Users/kjk/10.work/201.project/fuze_pay_svr/Cert","a.crt");
//        ByteUtils.saveFileFromByte(f, PKCertificate.getEncoded());
//        
//
//        
//        System.out.println(new String(Base64.encodeBase64(PKCertificate.getPublicKey().getEncoded())));
//        
//        
//        
//        
//       FileInputStream fis = new FileInputStream(f);
//        
//        X509Certificate cert = null;
//		CertificateFactory certificateFactory = CertificateFactory.getInstance("X509"); 
//		cert = (X509Certificate) certificateFactory.generateCertificate(fis);
//        
//		cert.getPublicKey();
//		
//		System.out.println(new String(Base64.encodeBase64(cert.getPublicKey().getEncoded())));
//		
//		 
//		 
//		boolean isValid = RSA_CRYPTO_PAC.doRSAVerify("SHA256", cert.getPublicKey(), plainText.getBytes(), G);
//		System.out.println(isValid);
//		 
//		 
//		 
//		 
//		 
//		 
//		 
//		String receivedEncStrUrlEncode ="FLMRt02QSgj97EA933honB8PjdiJdcDKL98T%2BjnJNYqvhTkYaJVBNGsaCC%2BuX%2FgrxjACzTo1CGFICwCBySyJlGBHgPoriHD27gVlWaI5a%2BjeQu5ftFLLyEe9VJhmMsF86lehVYnKdvH2OgXo4Uhssobv2ZzFvxApuY4hYeLbMBwGP3LLufbXdGdQtx%2BD%2FUM74LB53OV7ew5OdrYFhpdLVQKWymSVfw7YdaiFPsEL7KPX9ckZonaWCNdiD2EK4%2BRCLbO8FEbbFsx1xv4PHj0%2BQVIf6Q7OcQf2qB7qmLaMKNTyExh2RHn7QeUnzKQUfc8YgyyKURpYPGMniHQ3HVf0XQ%3D%3D";
//		String receivedEncStr = "[iEQ0MY4UZlk48myxvd9j76RtbfhXVYtOzFMKUTxIQcEiRAecGC84gloHKHbBkpJ5p8YSC611Lota65jZzmlg3VY+9jNCxtTQjGqed1mK5hfP2dB+j48W+YnoC8+ohu1AXgG3ObiLH06HVI5A3GDm789W0j/vjgj9ypCyRZhulWP/qhrE1vO65TVGgsrL8Lj+xrhjZeiQT91u3EyEnoOJ70BSjfpsx8MSzXhDY0pJJ+GtDBBh0SaqmNsOGQdu23fGluJnjsSILboxywXfr7gbedVRt5foOf1yREU04V4KGs3C+vbf4w/ZPzLigSH87vxbguK9Zx1SUiFRAPWct+ayTQ==";
////		String receivedEncStr ="biseB3skm6J/gtQzd7S0xGOICDPkZ5kwhu3Ssd0FxRKmtixbH90+B+avtUmDGeWzoS6H5zl7uJjnwQ+Z2edTSSJ+RDZ+pfhlIgCxC6GPBARJ2WceT1n8E0wpCLB3nh7KvOImtO5uDTFxRWzt3TkiaIVab4TWMdwLq/YYaSFMZ6uahqAlqDVGcCbrVV7p1jDh0QzlIe5U0sXfqxgciSpKPtlrHFZ61XVTNfqazhBJiXH9/bcLwZzzgu87yUb1Fv81FP4u7iANoTDcpJ6OS6bAVQSo8zC2X3/rDOau7D7+IvYwQOWlgyjC1uSoN1P3U1BUBdvuv8T3i6kpNMUnI89/aA==";
//		String privateKeyStr  ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM9j0FOmp6qG3qtJ3P+nbZ5ft8Mn9Z6NO92+cC4nSSOGuxrDJ18c3Ho3003+eQysyYOc8v0Y5/Z+sN9Go21cC7H25haOsVp2GxzW7UQCE8ojFxZV8t5V5ovIucZbSRhC0q1QNDFG+oQKSa08KPVdgN3ZBeGTduz7Z1hHHGZYsoqVaXqIpATLLfoj5UExFM46OxNhGombJpZx/u9gjdpAjBwD0X0lE5zJQxOxuDgOSwHuvvaSzMkL3iz5+z3d0GNX4VD1Hz1vB+jca4cejOtHt89Qq9FgSeUAb99DO+MCrOsiHNBFCWHhKRxvtFdPm2Q8x7e/RD5BYib4IkN571KyNhAgMBAAECggEAW9ej8mAZOEm9NAryN6vs/BPIQydzqlT0gONiJJL/eLWiFWUoi4g8lzKCRY0NB13a5+Pihjl3KrO+hp0VBx1GMaVM+dKuOLGSCZLb/xa5kRPsOwHdfb3QQVV3cTMi7xy7gQjA01fhWDKmDOqj/VflKUOO6IBtiZl0PCVLLATFInwVblq+FjwM4s84dpMq2TGScmDWA8JNtiZvVz6YlbC/7iomwkeH2ReTC7TojRsM6xKtJBQMLC4NgfTJgIuEZQShPHEJ/mnVd/OA7mmW2TeFn032xu9FerzhkRzfneJtfyfTgcmtIb+9qT7L8jUOMrB9cpjRzhsZhWExrj0yWWnQMQKBgQDrC1+aTaXe+xCPEj9cqFnL+SPQO1N8MdrGJ2AWS3B3GBQqUMVks3yEoYYcXdWpNA9kMini1KXqVJqDqEd++jnvHg8NimaEeDorC57zJNQe2mEbemUCOzdHZwwR/owvFD4If4cK9Z9xtIuhOzQjUKDU9p1zDKqMA6aVD3LImPR1TwKBgQCZh4kbx53Q5uspvh/EAzbGEbGPjeMKWj+Nws1F+emTu09VDyX+3dkKe0yazCYyNxGpzNPygeRjqKtEu8WIayJYvJ30E/Js1FHgTEjLxoUSd9JejVtYhAhkL1dN+j0PNzI00tFtwEO74pnUrJm1qgPZsRnbIL+3ibN376DvEboQTwKBgCoysolLY3oy7PgiuIl0GBsMsadS31wuvy5QJ2oZRSm8gvJN6gJ4TuSo8yDon9Fu7Ux5zRdSQi9d3RfCAIQFfJ03clBmBp3Fz/umbHUOqZ6YalK6mKPb6cLB92LLpnNVWQXBj4ciLewGFANwU537fcR+OipLndrhVOjyCxOEkm71AoGBAIT01i0Dks0dnVvZ9YJ3UhD3s7BhLNSDUXR/1esawUgZt1svMjVkhBhKrQ2FbDKidB57brUd/d68bA2rGybHuMfmGFgOiivrNziqd4RZWFalA760Zf3i4a15Pf8ejZBGuaj7RTjpfv8P/y4phzfGk20LrRuSYdUdYBmmoNVIobyLAoGAZWbZlCcmd4ExvL4MS1RHSAwTlgHFnNDZWxl4UDft37VcVK8aRjyZTZnMLfXiLCfNnB7MQBWLMm2KuvYf1G2aT85KFFORXUNHHxrC8lJ7Ni8yYD2wlV9yRIgzeodhmF6fUQQtcDkzCqNPJCMgXG2og3UkgtG7E2sHQtaxQ55MIjo=";
//		
//		KeyFactory kf = KeyFactory.getInstance("RSA");
//		
//		PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr.getBytes()));
//		PrivateKey priKey = kf.generatePrivate(pkcs8Spec);
//		
//		String decStr = new String(RSA_CRYPTO_PAC.doRSAPriKeyDecrypt(priKey, Base64.decodeBase64(receivedEncStr.getBytes())));
//		
//		System.out.println(decStr);
//		//생성된 키를 파일로 저장 
//		//공개키/개인키 동시 저장됨.
////		saveRSAKeyPair();
//		
//		
//		
//		File publeyFile = new File(new File("C:/smcoreDevFrame_v_001/workspace/ivy_svr_api/src/main/webapp/WEB-INF/rsakey"),CRYPTO_PUBKEY_FILENAME);
//		File prileyFile = new File(new File("C:/smcoreDevFrame_v_001/workspace/ivy_svr_api/src/main/webapp/WEB-INF/rsakey"),CRYPTO_PRIKEY_FILENAME);
//	
//		//공개키 읽어오기
//		byte [] pubKeyArr = ByteUtils.readByteFromFile(publeyFile);
//		byte [] priKeyArr = ByteUtils.readByteFromFile(prileyFile);
//		
//		
////		byte[] key = RandomHashUtil.makeSessionHash(32).getBytes();
////		byte[] keydd = RandomHashUtil.makeSessionHash(32).getBytes();
////		
////		//전자서명
//		
////		PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(priKeyArr);
////		PrivateKey priKey = kf.generatePrivate(pkcs8Spec);
////		byte[] G = SMCORE_RSA_CRYPTO_PAC.doRSASign("SHA256", priKey, key);
////		
////		System.out.println(G.length);
////		
////		//전자서명 검증
//		X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(pubKeyArr);
//		PublicKey pubKey =  kf.generatePublic(x509Spec);
//		System.out.println(pubKey.getAlgorithm());
//		
////		boolean isValid = SMCORE_RSA_CRYPTO_PAC.doRSAVerify("SHA256", pubKey, key, G);
////		
////		System.out.println(isValid);
//		
//		
//		
//		
//		
//		
//		// 공개키 배포용 문자열
//		String strPubKey = new String(Base64.encodeBase64(pubKeyArr));
//		System.out.println(strPubKey);
////		String strPubKey = new String(Base64.encodeBase64(mPubKey.getEncoded()));
////		String strPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuhd1o0cRbs7S9MnjGPf7sPVPy5WW3C0tNE9Za/X9sxGuemEzt1Qpvm6LDImZds54HD/R5f8OGynqzqrIo1wEBJPqC9KCbr+jpfAKvxlQbrssC5EY6SvzgtowMsWhaK5V8zt4xteL57zEdFkl6uA/GxmoONmleiyB+6iH2sqiR9NatLE/OyNnE/TV3hAoVI648HcWYF0+cD0OWtlsH3zAbdSIZZBMDl7j25RyAu9/XLCXk0JJeAwVoJ1zHi+BGXwXfajwn8i+mOZhIZgPj44v2J9p5I90DmoytlvdMwAPgDYfxbzyCUXS/SZrnC4fSH21fL02X6F9Kb96y2F+PxUjYwIDAQAB";
////		System.out.println(strPubKey);
//		
//		
//		// 개인키 배포용 문자열
//		String strPriKey = new String(Base64.encodeBase64(priKeyArr));
////		String strPriKey = new String(Base64.encodeBase64(mPriKey.getEncoded()));
////		String strPriKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6F3WjRxFuztL0yeMY9/uw9U/LlZbcLS00T1lr9f2zEa56YTO3VCm+bosMiZl2zngcP9Hl/w4bKerOqsijXAQEk+oL0oJuv6Ol8Aq/GVBuuywLkRjpK/OC2jAyxaForlXzO3jG14vnvMR0WSXq4D8bGag42aV6LIH7qIfayqJH01q0sT87I2cT9NXeEChUjrjwdxZgXT5wPQ5a2WwffMBt1IhlkEwOXuPblHIC739csJeTQkl4DBWgnXMeL4EZfBd9qPCfyL6Y5mEhmA+Pji/Yn2nkj3QOajK2W90zAA+ANh/FvPIJRdL9JmucLh9IfbV8vTZfoX0pv3rLYX4/FSNjAgMBAAECggEAZPa3b4fucGUsVX13hiN9XgwqZ/VYLUshfIEUrht/svkD5gf4zB2QrG5DaOD671ekGQeTNSsFlz1N1SUZyG/zChzKXfGvrLCy9CPhYL82QW2xvvDRSCj9bGA+yD0rjqUPovEAXTmzAV8MY2e+x8k009o+x+FmA3xEZaqRUuaf10FqoLdO5Msz6owec7bABs6d6v2w+++LPT5oGy0+gSIB48Vdgx9az85avYNMvMFzg9x1q3cs2qbXuxbc1MGFWhSS/YllbkMlyUyExGYnf1rokxQpfhAOcTWQAe0FLvnGytEJWqfvIyIeMZz6wUw/UxfrCqBldTwFgPodG1kKngc9CQKBgQDkLssz9Ra3fRDfvXgfMGfH9zHpdUOlvFPZsxiNKQ51eDiek5HpdfMaCbsim0L0M7RpoFgEAEWXqThjlQBIVXCW55klGxQlgmD4+IORmh4msGiiHubiKncrawI2fXS6XnCr+24dGg2SHSACX25Nx8knyvd6GHG6SyUUiuRoe/8CrQKBgQDQxxIhcq/iPbx6bitWHdGl+iiZmcLWuPtrHetwGyNxyf3KlZ4u4PT3uAQaesJSHVQ03HQBtJnFnHVL7gViKr/RhkUb64Nb3Zrw027R/0xeceXO7ydU3SGUirjJkB7XgdS+RgEIgWaYd/sXKnRu1eP2bLWsHDVDf+EbbeG3L6qQTwKBgGbytbs7WIRwaKyJTyjDLg+J93rc7ShZT3to0ird2ygklWb2LV6nWIbm3QdABXUn3r3JGey0MrG6JzbGgtofsixjUTTsgqppqFaryLBoWDIcZRi/2GsxvZJZNSZgc8mh0jPFvGhk2LqTaV2osJtnB9psODtdy/HA9XnPMD3zs5oBAoGAMYO1ChfAHQEVTVgJkR/7U8jzjhJIglJW1n2/E9Rr4JXOShiX7nhNwN50oq8QnRb1nJ/YJ3tkqNhFfV+TCUgoPBIDG+8MNWJmV81dUBKjUxbiMOTJSDg1uZv43Vz/446YHRRPNGw0H978vu7DtHGn4g7AO3RpMXwHsFCsIpz8b8MCgYEAjH+Qa9T2bbnqmzgbHUIunsUx24rzANGywWqCYzXUbk71TeEo7pydMsDLQNQVVzFFPs75XgTEH9SpWgAkHopts1ZwztrTNEfRKnaodDLlN0zxcNcakTRXXXBdyJkLbEmoVqZ53ejn1qcnCqmYu/1W201XikQk/RS0J7B+Sz5vmls=";
//		
//		System.out.println(strPriKey);
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		String plainText1 = "1234567890";
//		plainText1 += "22345678902234567890";
//		plainText1 += "32345678902234567890";
//		plainText1 += "42345678902234567890";
//		plainText1 += "52345678902234567890";
//		plainText1 += "62345678902234567890";
//		plainText1 += "72345678902234567890";
////		
//		//X509 spec 공개키 생성 
////		byte[] pubKeyArr = Base64.decodeBase64(strPubKey.getBytes());
////		X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(pubKeyArr);
////		PublicKey pubKey =  kf.generatePublic(x509Spec);
//		
//		
//		//RSA 암호화
//		byte[] encByte = RSA_CRYPTO_PAC.doRSAPubKeyEncrypt(pubKey, plainText1.getBytes());
//		System.out.println("encByte : \r\n"+new String(encByte));
//		
//		
//		
//		
//		//개인키 생성
////		byte[] priKeyArr = Base64.decodeBase64(strPriKey.getBytes());
////		PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(priKeyArr);
////		PrivateKey priKey = kf.generatePrivate(pkcs8Spec);
////		
//		byte[] decByte =  RSA_CRYPTO_PAC.doRSAPriKeyDecrypt(priKey, encByte);
//		System.out.println("decByte : \r\n" +new String(decByte) );
//
//		
//		
//		
//		
////		byte[] encByte1 = SMCORE_RSA_CRYPTO_PAC.doRSAPriKeyEncrypt(priKey, plainText1.getBytes());
////		byte[] decByte2 = SMCORE_RSA_CRYPTO_PAC.doRSAPubKeyDecrypt(pubKey, encByte1);
////		
////		System.out.println("prikey dec : \r\n" +new String(decByte2));
//		
//		System.out.println();
//		System.out.println("RSAES_OAEP");
//		plainText1 = "1234567890";
//		plainText1 += "22345678902234567890";
//		plainText1 += "32345678902234567890";
//		plainText1 += "42345678902234567890";
//		plainText1 += "52345678902234567890";
//		plainText1 += "62345678902234567890";
//		plainText1 += "72345678902234567890";
//		plainText1 += "82345678902234567890";//최대길이 (245byte)
//		
//		
//		Cipher c = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
//		c.init(Cipher.ENCRYPT_MODE, pubKey);
//		byte[] encStr1 = c.doFinal(plainText1.getBytes());
//		System.out.println(new String(encStr1));
//		c.init(Cipher.DECRYPT_MODE	, priKey);
//		byte[] decStr1 = c.doFinal(encStr1);
//		
//		System.out.println(new String(decStr1));
		
		
	}













	


}
