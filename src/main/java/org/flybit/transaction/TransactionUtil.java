
package org.flybit.transaction;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.flybit.crypto.Crypto;
import org.flybit.domain.Transaction;
import org.flybit.util.Converter;

public class TransactionUtil {

    public static byte[] hashAfterSign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey) {
        final byte[] hash = Crypto.sha256().digest(getBytesAfterSign(transaction, senderPublicKey, recipentPublicKey));
        transaction.setHash(hash);
        return hash;
    }

    public static byte[] hashBeforeSign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey) {
        final byte[] hash = Crypto.sha256().digest(getBytesBeforeSign(transaction, senderPublicKey, recipentPublicKey));
        transaction.setHash(hash);
        return hash;
    }

    public static String hashStrAfterSign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey) {
        return Converter.toHexString(hashAfterSign(transaction, senderPublicKey, recipentPublicKey));
    }

    public static byte[] getBytesBeforeSign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey) {
        final ByteBuffer buffer = ByteBuffer.allocate(97);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(transaction.getTransactionType());// 1 byte
        buffer.putInt(transaction.getTransactionVersion());// 4 bytes
        buffer.putLong(transaction.getCreationInstant());// 8 bytes
        buffer.putLong(transaction.getExpirationInstant());// 8 bytes
        buffer.put(senderPublicKey);// 32
        buffer.put(recipentPublicKey);// 32
        buffer.putLong(transaction.getAmount());// 8
        buffer.putInt(transaction.getFee());// 4

        return buffer.array();
    }

    public static byte[] getBytesAfterSign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey) {
        if (transaction.getSignature() == null) {
            throw new IllegalStateException("Transaction is not signed yet");
        }
        
        final byte[] bytes = getBytesBeforeSign(transaction, senderPublicKey, recipentPublicKey);
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length+64);
        buffer.put(bytes);
        buffer.put(transaction.getSignature());
        return buffer.array();
    }

    public static void sign(Transaction transaction, byte[] senderPublicKey, byte[] recipentPublicKey, String secretPhrase){
        final byte[] signature = Crypto.sign(hashBeforeSign(transaction, senderPublicKey, recipentPublicKey), secretPhrase);
        transaction.setSignature(signature);
    }
}
