/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;

public class InvalidData {
    private CKDExpEntity ckdExpEntity;
    private String message;

    public InvalidData(CKDExpEntity ckdExpEntity, String message) {
        this.ckdExpEntity = ckdExpEntity;
        this.message = message;
    }

    public CKDExpEntity getCkdExpEntity() {
        return this.ckdExpEntity;
    }

    public void setCkdExpEntity(CKDExpEntity ckdExpEntity) {
        this.ckdExpEntity = ckdExpEntity;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

