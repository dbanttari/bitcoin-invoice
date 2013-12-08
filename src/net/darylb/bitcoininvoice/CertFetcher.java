package net.darylb.bitcoininvoice;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CertFetcher {

	public static X509Certificate getCert(String host) throws UnknownHostException, IOException {
		return getCert(host, 443);
	}
	
	public static X509Certificate getCert(String host, int port) throws UnknownHostException, IOException {
		SSLSocketFactory fac = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket sock = (SSLSocket)fac.createSocket(host, port);
		// download http://www.startssl.com/certs/ca.cer
		// cd to your jre's lib/security folder
		// keytool -import -trustcacerts -alias StartSSL -keystore cacerts -file ca.cer -storepass changeit
		Certificate[] certs = sock.getSession().getPeerCertificates();
		sock.close();
		return (X509Certificate)certs[0];
	}

}
