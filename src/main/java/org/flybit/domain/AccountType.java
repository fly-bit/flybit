package org.flybit.domain;

public enum AccountType {
    GOD((byte)0),
    NORMAL((byte)1);

    private byte code;
    
    
    AccountType(byte code){
        this.code =  code;
    }

    public byte code(){
        return code;
    }
    
    public static AccountType fromCode(byte code){
        for (final AccountType accountType : AccountType.values()) {
            if(accountType.code() == code){
                return accountType;
            }
        }
        return null;
    }
}