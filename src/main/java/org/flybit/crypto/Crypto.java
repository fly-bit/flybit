package org.flybit.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.flybit.util.Converter;


public class Crypto {
    private static final Log log = LogFactory.getLog(Crypto.class);
    public static byte[] getPublicKey(byte[] keySeed) {
        final byte[] publicKey = new byte[32];
        Curve25519.keygen(publicKey, null, Arrays.copyOf(keySeed, keySeed.length));
        return publicKey;
    }
    
    public static byte[] getPublicKey(String secretPhrase) {
        final byte[] publicKey = new byte[32];
        Curve25519.keygen(publicKey, null, sha256().digest(Converter.toBytes(secretPhrase)));
        return publicKey;
    }
    
    public static byte[] sign(byte[] message, String secretPhrase) {
        final byte[] publicKey = new byte[32];
        final byte[] secretKey = new byte[32];
        final MessageDigest digest = sha256();
        Curve25519.keygen(publicKey, secretKey, digest.digest(Converter.toBytes(secretPhrase)));

        final byte[] messageHush = digest.digest(message);

        digest.update(messageHush);
        final byte[] x = digest.digest(secretKey);

        final byte[] Y = new byte[32];
        Curve25519.keygen(Y, null, x);

        digest.update(messageHush);
        final byte[] h = digest.digest(Y);

        final byte[] v = new byte[32];
        Curve25519.sign(v, h, x, secretKey);

        final byte[] signature = new byte[64];
        System.arraycopy(v, 0, signature, 0, 32);
        System.arraycopy(h, 0, signature, 32, 32);
        return signature;
    }
    

    public static boolean verify(byte[] signature, byte[] message, byte[] publicKey, boolean enforceCanonical) {
        try {
            if (signature.length != 64) {
                return false;
            }
            if (enforceCanonical && !Curve25519.isCanonicalSignature(signature)) {
                log.debug("Rejecting non-canonical signature");
                return false;
            }

            if (enforceCanonical && !Curve25519.isCanonicalPublicKey(publicKey)) {
                log.debug("Rejecting non-canonical public key");
                return false;
            }

            final byte[] Y = new byte[32];
            final byte[] v = new byte[32];
            System.arraycopy(signature, 0, v, 0, 32);
            final byte[] h = new byte[32];
            System.arraycopy(signature, 32, h, 0, 32);
            Curve25519.verify(Y, v, h, publicKey);

            final MessageDigest digest = Crypto.sha256();
            final byte[] m = digest.digest(message);
            digest.update(m);
            final byte[] h2 = digest.digest(Y);

            return Arrays.equals(h, h2);
        } catch (final RuntimeException e) {
            log.error("Error verifying signature", e);
            return false;
        }
    }

    public static MessageDigest md5() {
        return getMessageDigest("MD5");
    }
    
    public static MessageDigest sha256() {
        return getMessageDigest("SHA-256");
    }
    public static MessageDigest sha1() {
        return getMessageDigest("SHA-1");
    }
    public static RIPEMD160Digest ripemd160() {
        final RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
        return ripemd160Digest;
    }
    
    public static byte[] ripemd160Digest(byte[] bytes) {
        final RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
        ripemd160Digest.update(bytes, 0, bytes.length);
        final byte[] result = new byte[ripemd160Digest.getDigestSize()];
        ripemd160Digest.doFinal (result, 0);
        return result;
    }
    
    public static MessageDigest getMessageDigest(String algorithm) {
        
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
            return md;
        } catch (final NoSuchAlgorithmException nsae) {
            throw new InternalError(algorithm+ " not supported", nsae);
        }
    }
}
