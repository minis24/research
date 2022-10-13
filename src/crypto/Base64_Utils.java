package crypto;

import org.apache.commons.codec.binary.Base64;

public class Base64_Utils {
	
	

	    
		public static byte[] decode(String base64Str) {
			
			return Base64.decodeBase64(base64Str);
			
		}

		public static String encode(byte[] binaryData) {
			
			return new String (Base64.encodeBase64(binaryData));
			
		}
		
	    
		
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) throws Exception {

	    }

}
