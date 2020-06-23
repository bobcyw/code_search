package com.caoyawen.code_search.dataStruct;

public class OperatorException extends Exception{
    public OperatorException() {
    }

    public OperatorException(String message) {
        super(message);
    }

    public OperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperatorException(Throwable cause) {
        super(cause);
    }

    public OperatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
