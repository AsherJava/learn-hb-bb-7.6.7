/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.handler;

import java.io.InputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.Assert;

public class CommonInputStreamResource
extends InputStreamResource {
    private int length;
    private String fileName;

    public CommonInputStreamResource(InputStream inputStream, int length, String fileName) {
        super(inputStream);
        Assert.hasText(fileName, "\u6587\u4ef6\u540d\u4e0d\u53ef\u4e3a\u7a7a");
        this.length = length;
        this.fileName = fileName;
    }

    @Override
    public String getFilename() {
        return this.fileName;
    }

    @Override
    public long contentLength() {
        int estimate = this.length;
        return estimate == 0 ? 1L : (long)estimate;
    }
}

