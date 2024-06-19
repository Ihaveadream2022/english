package com.english.exception.file;

import com.english.exception.ServiceRuntimeException;

public class FileUploadException extends ServiceRuntimeException {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, String serviceType) {
        super(message);
        super.setServiceType(serviceType);
    }
}