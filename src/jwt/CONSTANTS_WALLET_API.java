package jwt;



public class CONSTANTS_WALLET_API {
		
	
	
	//Header
	public static final String HEADER_AUTHORIZATION			= "Authorization";
	public static final String HEADER_CONTENT_TYPE 			= "Content-Type";
	public static final String HEADER_CONTENT_TYPE_VALUE 	= "application/json";

	public static final String KEY_BEARER					= "bearer";
	
	
	//Key-Value
	public static final String KEY_CORRELATIONID        = "correlationId";
	public static final String KEY_DEVICE_DATA			= "deviceData";
	public static final String KEY_DEVICE_ID				= "deviceId";	
	public static final String KEY_RESPONSE_CONTEXT		= "responseContext";	
	public static final String KEY_RESPONSE_CODE		= "responseCode";	
	public static final Object KEY_RESPONSE_MSG 		= "responseMsg";
	
	//JWT 
	public static final String KEY_TIME_ZONE          			= "CET";
	public static final String KEY_JWT_RSA      					= "RSA";
	public static final String KEY_JWT         					= "JWT";
	public static final String KEY_JWT_CLAIM_NONCE				= "nonce";
	public static final String KEY_JWT_HEADER_TYP				= "typ";		
	public static final String KEY_JWT_HEADER_ALG				= "alg";
	public static final String KEY_JWT_HEADER_ALG_RS256		= "RS256";
	public static final String KEY_JWT_CLAIM_AUD 				= "aud";	
	public static final String KEY_JWT_CLAIM_AUD_ENROLLMENT	= "enrollment";	
	public static final int   EXP_MILLIS      	    			= 1000*60*60*24;   //24시간

	

		
}
