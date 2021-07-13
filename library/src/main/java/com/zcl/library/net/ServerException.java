package com.zcl.library.net;


public class ServerException extends RuntimeException {

    private String mErrorCode;

    public ServerException(String errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }


    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }
}
