package net.darylb.bitcoininvoice;

import static org.junit.Assert.*;

import java.security.cert.X509Certificate;

import net.darylb.bitcoininvoice.CertFetcher;

import org.junit.Test;

public class CertFetcherTest {

	@Test
	public void testGetCert() throws Exception {
		X509Certificate cert = CertFetcher.getCert("gmail.com");
		//System.out.println(cert.toString());
		assertTrue(cert.getSubjectDN().getName().indexOf("Google") != -1);
	}

}
