package net.darylb.bitcoininvoice;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.bouncycastle.util.encoders.Base64;

public class MessageVerifier {

	private PublicKey key;

	public MessageVerifier(PublicKey key) {
		this.key = key;
	}

	public boolean isValid(String message, String sig) throws SignatureException, InvalidKeyException {
		try {
			return isValid(message.getBytes("UTF-8"), Base64.decode(sig));
		} catch (UnsupportedEncodingException e) {
			// should never happen
			throw new RuntimeException(e);
		}
	}

	private boolean isValid(byte[] message, byte[] signature) throws SignatureException, InvalidKeyException {
		Signature sig;
		try {
			sig = Signature.getInstance("SHA1withRSA");
		} catch (NoSuchAlgorithmException e) {
			// should never happen
			throw new RuntimeException(e);
		}
		sig.initVerify(key);
		sig.update(message);
		return sig.verify(signature);
	}
}
