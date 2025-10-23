/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.exception;

import com.jiuqi.nr.file.exception.FileException;

public class FileNotFoundException
extends FileException {
    private static final long serialVersionUID = 5546456135119471035L;
    private final String fileKey;

    public FileNotFoundException(String fileKey) {
        super("file '" + fileKey + "' not found.");
        this.fileKey = fileKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }
}

