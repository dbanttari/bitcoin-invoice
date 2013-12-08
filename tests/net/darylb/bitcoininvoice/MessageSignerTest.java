package net.darylb.bitcoininvoice;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Ignore;
import org.junit.Test;

public class MessageSignerTest {

	public static final String PRIVATE_KEY_LOCATION = "/ssl/darylb.net/darylb.net.pem";
	public static final String PRIVATE_KEY_DOMAIN = "darylb.net";
	
	@Test
	@Ignore
	public void testSignMessage() throws IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		FileReader fileReader = new FileReader(new File(PRIVATE_KEY_LOCATION));
		PemReader r = new PemReader(fileReader);
		try {
			PemObject o = r.readPemObject();
		    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(o.getContent());
		    KeyFactory kf;
			try {
				kf = KeyFactory.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				// should never happen
				throw new RuntimeException(e);
			}
		    PrivateKey key = kf.generatePrivate(spec);
			assertEquals("RSA", key.getAlgorithm());
			MessageSigner signer = new MessageSigner(key);
			String sig = signer.getBase64EncodedSig("bitcoin:1NYTSZeZ6axJhnR41AfxxB4ks5fEgkjDQ8?sigdomain=" + PRIVATE_KEY_DOMAIN + "&sig=");
			System.out.println(sig);
			assertEquals("b9FUOZOmvlIYAMD6FH4mTw9fipCLi8WSnN9laSg+lRagU5EwHe9VFN0NlX1B/KKNtcKlbqBel1C4WOh9bH2uibg5eNKBwDXUWenLk+T3i5G5iUn0uG5SNtz69zOYAloFRn5E8CAFXElqoBFj24XcU6tgRJuFv7EwFMGiNhenaauHaohB8sYr8HNKqoeLC5zMbG9sB/Cy/8N3Vj7QSFYh4bQt4W/JI/ai8Fq4E7U/EUHR+HINb/X/SwUxXMva6LuDRQq/zhhNUFmbtd1ahne/F/ADm8UQMM1LPj/ZMtbpE6EUEW5+VkFYiaK2tOIBauQb/brstOcIkxZgS7VdC3+Qgw==", sig);
		} finally {
			r.close();
			fileReader.close();
		}
	}

}
