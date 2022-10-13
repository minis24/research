package jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TestJWT {



	public static void main(String args[]) throws Exception {
		
	    System.out.println(new String (Base64.getDecoder().decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9".getBytes())));
		
		
	    /***************************************************************************************/
	    // JWT KeyGen
		// RS256(RSA256) : RSASSA-PKCS1-v1_5 with SHA-256
	    /***************************************************************************************/
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance("RSA");
		genKeyPair.initialize(2048);
		
		KeyPair keypair = genKeyPair.generateKeyPair();
		RSAPublicKey  pubKey = (RSAPublicKey)keypair.getPublic();
		RSAPrivateKey priKey = (RSAPrivateKey)keypair.getPrivate();

		System.out.println(new String (Base64.getEncoder().encode(priKey.getEncoded())));
		System.out.println(new String (Base64.getEncoder().encode(pubKey.getEncoded())));
	    /***************************************************************************************/
	    // JWT 생성
	    /***************************************************************************************/
	    String JWT_TOKEN = JWT.create()
	                    .withIssuer("Issure")
	                    .withExpiresAt(new Date(System.currentTimeMillis() + 5000)) // 만료일
	                    .sign(Algorithm.RSA256(priKey));
	    
	    System.out.println(JWT_TOKEN);
	    
	    
	    
	    
	    /***************************************************************************************/
	    // JWT 검증
	    /***************************************************************************************/
	    String token = JWT_TOKEN;  //생된 토큰
	        Algorithm algorithm = Algorithm.RSA256(pubKey);
	        JWTVerifier verifier = JWT.require(algorithm)
	            .withIssuer("Issure") 
	            .build(); //Reusable verifier instance
	        DecodedJWT jwt = verifier.verify(token);
	        
	        System.out.println(jwt.getPayload());
	        System.out.println(jwt.getAlgorithm());
	        System.out.println(jwt.getSignature());
	}

/*
   jwt.io 사이트에서 deburg 입력할때 (RSA256 알고리즘)
   키값은 아래와 같은 형식 으로 입력해야함.
   
   
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6VAPWe1KA16HirjM0BBoSmWQoz1j8gCpASdp6uuKqqNTXAfo6yewyDVH1+Czy5RqSAsyerI2HDzW4j8qgXZzpMNDC/2N8doAlqoEChZ5eo3Q4vPANqxzlmaD4udD4TFSIWDIK89aEFcn7xrQkoqme94lSKk5PLW7lvURb4YGtUxVjSMsG01LfMnPL4sySAP4djM/HTunLAsDtxRz55VKfayhW3jJnel4ousFcL3n5VisY52mIbGap/wuUJVwTdk8UfAeA69GpjQiKy/nPKZXsbhJXosocIfSWHkIoEc74zZYlEJbYl06ityNgV/QibtaDMEGnjvLyYG63XhnxQCo1QIDAQAB
-----END PUBLIC KEY-----



-----BEGIN PRIVATE KEY-----
MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDpUA9Z7UoDXoeKuMzQEGhKZZCjPWPyAKkBJ2nq64qqo1NcB+jrJ7DINUfX4LPLlGpICzJ6sjYcPNbiPyqBdnOkw0ML/Y3x2gCWqgQKFnl6jdDi88A2rHOWZoPi50PhMVIhYMgrz1oQVyfvGtCSiqZ73iVIqTk8tbuW9RFvhga1TFWNIywbTUt8yc8vizJIA/h2Mz8dO6csCwO3FHPnlUp9rKFbeMmd6Xii6wVwveflWKxjnaYhsZqn/C5QlXBN2TxR8B4Dr0amNCIrL+c8plexuEleiyhwh9JYeQigRzvjNliUQltiXTqK3I2BX9CJu1oMwQaeO8vJgbrdeGfFAKjVAgMBAAECggEBAKcW3heiF+p3zYxsssmjpnz5Ze9ybNyI2oBoELPcgk+Ybn+vboDSyQlpFw2k8D6xCLiVxE4YySXJ8H7RjAhxo3pxGNhCe2Ckpt0/32q2E8PQQrP2q0a8lH825ecfhv6EyvP6kwDFHBQtxvy3cH4ya+6z9nGmN8ahxDnlQtpi2vUE2su+Cc13greVi/sdGdInBXk/7niKP7JduH9OTDWk4ONV0lJHulgQz1iQwKmlU1oupfZIRIoy+NrqGUUROPsrTvMfCfLjzx3RRqNxEZNZxblHuC8ocEXcfEU6WwELa9++/E8T2hOm7enFgbd9Q5pXhP8mwpRMe7B+hjWjqY9grTUCgYEA/V8X+gGnLZpcIVRPg1o6MYBF1WLrZDK6c6fTPHjNT4pZz2J7asL5ODGFyb03LVmxkKJpwUJ5Zu7W2ehQDIqLxXWZ9iaBXA4+LyXdlPGcpnL303cC6IqzN/tyhth0Vpx4nmp7bHLN0q6DIKRH7AzlczFt4HYmGspZ2qwHuZSwSw8CgYEA67uxtAO/enzqgoHNmpwqKFoh7dRho0zCMEPBBopNqHf2ynHvK2DQkK4MG5x0Vi1ebEWwCp/agcPEBILUwGnxPzaKadni4X4IuH9EH6ndhco+ktRbVgvX2mH2sYrJjBuQs4OVBQzo9oxXtFfLZnNcVVYgGm3tZ9ZtTORawLpUXdsCgYEAow5Pn9TwgaiVId3gTegDC1hBmNHQKut6knOuvXwApO+kXJJEWrCfMkqsjuf2WnkejLX42Sauek+6dQHMgkWnLaxBKOge199SpYT41KGo7nWOh8hPVtfr/lsTmTQzqomIFmrwgxpe2vbz7E3M+1xRTZDB5XIjk5oluVR8pDHMQoECgYALkkYR0RFIIPOd/mMqULodCveZZhB45J6vodPKz2UqfOEHZPpZhcIXKAP+Ivru9AErjWwryVb/ga2Qjx+EZm7qLHI06fYiHX6uF8kvSLZQB/J0YKovGhu5Y4+pfWeRUhUTSsU43z5cQ91WQ+z/FXCV28ltorHBpBvvZ1IxHbRgewKBgQDCJaQhs5VOXYrOcdXwMU6glwoQdKuErCRvnGxnen4c5aC8q5DnT/KqKmKdjbVX5vfWHNyE5zSj1PHSi7QnUqn+18etf+8xGVE9gnYRlKMGvwpYJg6pnKOo+NNfdZH1fLO+SRz3Sd7dPTVNIGnlmRnEI+9lYaL6Op96Gl+L0MoLkQ==
-----END PRIVATE KEY-----

		 
		 
		 */
	
	
	
	
	


	
}



