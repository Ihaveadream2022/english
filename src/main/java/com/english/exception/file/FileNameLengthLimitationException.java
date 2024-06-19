package com.english.exception.file;

public class FileNameLengthLimitationException extends FileUploadException {
    public FileNameLengthLimitationException() {
		super("文件名过长", "[FILE] ");
    }
}
