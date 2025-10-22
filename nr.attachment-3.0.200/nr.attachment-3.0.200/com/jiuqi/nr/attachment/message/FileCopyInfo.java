/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

import com.jiuqi.nr.attachment.message.FileInfo;

public class FileCopyInfo
extends FileInfo {
    private String md5;

    @Override
    public String getMd5() {
        return this.md5;
    }

    @Override
    public void setMd5(String md5) {
        this.md5 = md5;
    }
}

