package pkcs7;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;
import java.util.Collection;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.KeyTransRecipientInformation;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OutputEncryptor;

public class CMS_PKCS7_UTIL {

	private static final String DATA_TO_ENCRYPT = "{\"fpan\":\"4974013055554646\",\"exp\":\"0316\"}";

	private static BouncyCastleProvider bcProvider;
	private static PrivateKey privKey;
	private static PublicKey pubKey;
	
	
	
	static {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			privKey = kp.getPrivate();
			pubKey = kp.getPublic();
			bcProvider = new BouncyCastleProvider();
			Security.addProvider(bcProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
	
	
	
		// Encrypt
		byte[] encData = encryptPKCS7(DATA_TO_ENCRYPT.getBytes());
		System.out.println("Encrypted data = " + new String(Base64.getEncoder().encode(encData)));
	
		// Decrypt
		byte[] result = decryptPKCS7(encData);
		System.out.println("Decrypted data = " + new String(result));

	}
	
	
	
	private static byte[] encryptPKCS7(byte[] plainData) throws Exception {
		
		// set up the generator
		CMSEnvelopedDataGenerator gen = new CMSEnvelopedDataGenerator();
//		gen.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(new byte[] { 0x01 }, pubKey).setProvider(bcProvider));
		gen.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator("12345678".getBytes(), pubKey).setProvider(bcProvider));
		
		// create the enveloped-data object
		CMSProcessableByteArray data = new CMSProcessableByteArray(plainData);
		
		
		BcCMSContentEncryptorBuilder builder = new BcCMSContentEncryptorBuilder(CMSAlgorithm.AES256_CBC);
		OutputEncryptor oe = builder.build();
		CMSEnvelopedData enveloped = gen.generate(data, oe);
	
		return enveloped.getEncoded();
	}
	
	
	
	
	
	private static byte[] decryptPKCS7(byte[] encryptedData) throws Exception {
		
		CMSEnvelopedData enveloped = new CMSEnvelopedData(encryptedData);
		
		byte[] ap = enveloped.getEncryptionAlgParams();
		System.out.println(new String(ap));
		
		AlgorithmIdentifier ai = enveloped.getContentEncryptionAlgorithm();
		ASN1ObjectIdentifier asni = ai.getAlgorithm();
		
		System.out.println(asni.getId());
		System.out.println(asni.toString());
		
		Collection<RecipientInformation> recip = enveloped.getRecipientInfos().getRecipients();
		KeyTransRecipientInformation rinfo = (KeyTransRecipientInformation) recip.iterator().next();
		byte[] contents = rinfo.getContent(new JceKeyTransEnvelopedRecipient(privKey).setProvider(bcProvider));
		
		return (contents);
	}


	


}
