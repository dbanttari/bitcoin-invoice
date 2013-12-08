package net.darylb.bitcoininvoice;

import static org.junit.Assert.*;

import java.security.SignatureException;

import org.junit.Test;

public class BitcoinInvoiceVerifierTest {

	static final String qrcode = "bitcoin:1NYTSZeZ6axJhnR41AfxxB4ks5fEgkjDQ8";
	static final String signedqrcode = qrcode + "?sigdomain=darylb.net&sig=b9FUOZOmvlIYAMD6FH4mTw9fipCLi8WSnN9laSg+lRagU5EwHe9VFN0NlX1B/KKNtcKlbqBel1C4WOh9bH2uibg5eNKBwDXUWenLk+T3i5G5iUn0uG5SNtz69zOYAloFRn5E8CAFXElqoBFj24XcU6tgRJuFv7EwFMGiNhenaauHaohB8sYr8HNKqoeLC5zMbG9sB/Cy/8N3Vj7QSFYh4bQt4W/JI/ai8Fq4E7U/EUHR+HINb/X/SwUxXMva6LuDRQq/zhhNUFmbtd1ahne/F/ADm8UQMM1LPj/ZMtbpE6EUEW5+VkFYiaK2tOIBauQb/brstOcIkxZgS7VdC3+Qgw==";
	
	@Test
	public void testValid() throws Exception {
		//System.out.println(qrdecode.length());
		assertEquals("darylb.net", BitcoinInvoiceVerifier.verifyInvoice(signedqrcode));
	}

	@Test
	public void testInvalid() throws Exception{
		try {
			BitcoinInvoiceVerifier.verifyInvoice(signedqrcode.replace("xxB", "xxC")); // only one bit changed
		} catch (SignatureException e) {
			// this is expected
		} 
	}

	@Test
	public void testNotVerified() throws Exception {
		assertEquals(null, BitcoinInvoiceVerifier.verifyInvoice(qrcode)); // not a signed address
	}

}
