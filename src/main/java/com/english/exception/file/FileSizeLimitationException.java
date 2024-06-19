package com.english.exception.file;

public class FileSizeLimitationException extends FileUploadException {
    public FileSizeLimitationException() {
		super("文件质量过大", "[FILE] ");
    }
}
