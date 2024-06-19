package com.english.exception.file;

public class InvalidExtensionException extends FileUploadException {
    public InvalidExtensionException(String extension) {
		super("非法文件后缀: " + extension, "[FILE] ");
    }
}
