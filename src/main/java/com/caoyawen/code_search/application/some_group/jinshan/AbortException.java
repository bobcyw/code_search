package com.caoyawen.code_search.application.some_group.jinshan;

/**
 * 一个Runtime类型的Exception
 */
public class AbortException extends RuntimeException{
    public AbortException() {
    }

    public AbortException(String message) {
        super(message);
    }

    public AbortException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbortException(Throwable cause) {
        super(cause);
    }

    public AbortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
