package crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

//import org.apache.commons.net.util.Base64;


public class PBKDF2_Utils {
	
	
	
    // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
    // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
	public static String algorithm = "PBKDF2WithHmacSHA1";
//	private static int derivedKeyLength = 512;
//	private static int derivedKeyLength = 256;
//	private static int iterations = 4096;
//	private static int saltSize = 20;
	

	 
	public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt,int keyLength,int iterationCnt)  throws NoSuchAlgorithmException, InvalidKeySpecException {
	       
		// Encrypt the clear-text password using the same salt that was used to
		// encrypt the original password
	    
//		System.out.println("attemptedPassword : " +attemptedPassword);
//		System.out.println("encryptedPassword : " +Base64.encodeBase64String(encryptedPassword));
		
		byte[] encryptedAttemptedPassword = createEncryptedPassword(attemptedPassword, salt, keyLength,iterationCnt);
//		System.out.println("test : " + Base64.encodeBase64String(encryptedAttemptedPassword));
		
		// Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
	    
		
		
		
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	   
	}

	 
	 
	 
	    
	public static byte[] createEncryptedPassword(String password, byte[] salt, int keyLength, int iterationCnt)    throws NoSuchAlgorithmException, InvalidKeySpecException {
	        
        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        
        // Pick an iteration count that works for you. The NIST recommends at
        // least 1,000 iterations:
        // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
        // iOS 4.x reportedly uses 10,000:
        // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
	       
		KeySpec spec = 	spec = new PBEKeySpec(password.toCharArray(), salt, iterationCnt, keyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
	        
		return f.generateSecret(spec).getEncoded();
	    
	}

	    


	    
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) throws Exception {

//	
//	    	18:28:32 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::verifyHashedPasswd - alg : PBKDF2WithHmacSHA1
//	    	18:28:32 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::verifyHashedPasswd - iCnt : 4096
//	    	18:28:32 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::verifyHashedPasswd - keyLength : 18
//	    	18:28:32 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::verifyHashedPasswd - salt : 5gacNchqkwUrdXpZ/00XG2Q3ChZdjYhN
//	    	18:28:32 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::verifyHashedPasswd - hashedPwd : elH0QVv+09a8xhGBpRUttRn/CKVit3mubHVXS4uiGBU=
//	    	18:28:33 [http-nio-10001-exec-3] INFO  com.brilliantts.fuzepay.app.fpapi.service.impl.FuzePayMemberServiceImpl::login_Tx - result : false
	    	
	    	
	    	
//	    		"ycR34D3vdxIFSQDobb6eQpnYQyF/jVfD";
//	    		oAb+yda96SEaRX3prX7yD7zw
//	    	
	    	
//	    		byte[] saltBytes = Base64.decode("ycR34D3vdxIFSQDobb6eQpnYQyF/jVfD");
	    	
	    		int saltLenth = 24;
	    		int keyLength = 18*8;
	    		byte[] saltBytes = SECURE_RANDOM_PAC.createRandomBytes(saltLenth);
	    	    byte[] hash = createEncryptedPassword("1234", saltBytes, keyLength,4096);
//	    	    System.out.println(new String(Base64.encode(hash)));
	        System.out.println(DatatypeConverter.printHexBinary(hash));
	        
	        
	        String apasswd = "1234";
//	        saltBytes = Base64.decode("5gacNchqkwUrdXpZ/00XG2Q3ChZdjYhN");
//	        byte[] hashed  = createEncryptedPassword("apasswd", saltBytes, 18,4096);
	        boolean a = authenticate(apasswd, hash, saltBytes,18*8,4096);
	        System.out.println(a);
	        
	    }

}
