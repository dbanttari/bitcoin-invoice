package net.darylb.bitcoininvoice;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitcoinInvoiceVerifier {

	
	static final String TOKEN_SIGDOMAIN = "sigdomain=";
	static final String TOKEN_SIG = "sig=";
	static final Pattern PATTERN_SIGDOMAIN = Pattern.compile("[\\?&]" + TOKEN_SIGDOMAIN);
	static final Pattern PATTERN_SIG = Pattern.compile("[\\?&]" + TOKEN_SIG);
	static final int PATTERN_SIGDOMAIN_LENGTH = TOKEN_SIGDOMAIN.length() + 1;
	static final int PATTERN_SIG_LENGTH = TOKEN_SIG.length() + 1;

	public static String verifyInvoice(String qrdecode) throws UnknownHostException, IOException, InvalidKeyException, SignatureException {
		Matcher m = PATTERN_SIGDOMAIN.matcher(qrdecode);
		if(!m.find()) {
			// not an invoice, return true
			return null;
		}
		int pos = m.start();
		int pos2 = qrdecode.indexOf('&', pos+PATTERN_SIGDOMAIN_LENGTH);
		String host = qrdecode.substring(pos+PATTERN_SIGDOMAIN_LENGTH, pos2);
		X509Certificate cert = CertFetcher.getCert(host);
		
		m = PATTERN_SIG.matcher(qrdecode);
		if(!m.find()) {
			// not an invoice, return true
			return null;
		}
		pos = m.start();
		String message = qrdecode.substring(0, pos+PATTERN_SIG_LENGTH);
		String sig = qrdecode.substring(pos+PATTERN_SIG_LENGTH, qrdecode.length());
		MessageVerifier verifier = new MessageVerifier(cert.getPublicKey());
		if(verifier.isValid(message, sig)) {
			return host; 
		}
		throw new SignatureException("Signature does not match address.");
	}

}
