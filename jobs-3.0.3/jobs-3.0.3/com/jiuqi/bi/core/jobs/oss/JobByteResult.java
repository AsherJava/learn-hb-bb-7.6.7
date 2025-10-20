/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.oss;

import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;

public class JobByteResult
extends AbstractJobResult {
    private byte[] bytes;

    public JobByteResult(String name, byte[] bytes) {
        this.bytes = bytes;
        this.setName(name);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}

