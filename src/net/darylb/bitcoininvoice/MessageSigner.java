package net.darylb.bitcoininvoice;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.bouncycastle.util.encoders.Base64;

public class MessageSigner {

	private PrivateKey key;

	public MessageSigner(PrivateKey key) {
		this.key = key;
	}
	
	public String getBase64EncodedSig(String message) throws InvalidKeyException, SignatureException {
		try {
			return Base64.toBase64String(getSig(message.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// should never happen
			throw new RuntimeException(e);
		}
	}

	public byte[] getSig(byte[] message) throws InvalidKeyException, SignatureException {
		Signature dsa;
		try {
			dsa = Signature.getInstance("SHA1withRSA");
		} catch (NoSuchAlgorithmException e) {
			// should never happen
			throw new RuntimeException(e);
		}
		dsa.initSign(key);
		dsa.update(message);
		return dsa.sign();
	}

}
