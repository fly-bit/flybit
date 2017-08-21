
package org.flybit.domain;

public enum TransactionType {

    PAYMENT((byte)0);

    private byte code;
    
    
    TransactionType(byte code){
        this.code =  code;
    }

    public byte code(){
        return code;
    }
    
    public TransactionType fromCode(byte code){
        for (final TransactionType transactionType : TransactionType.values()) {
            if(transactionType.code() == code){
                return transactionType;
            }
        }
        return null;
    }
}
