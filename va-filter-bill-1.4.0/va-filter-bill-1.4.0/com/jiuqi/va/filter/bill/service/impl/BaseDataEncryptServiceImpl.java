/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.filter.bill.service.impl;

import com.jiuqi.va.filter.bill.service.BaseDataEncryptService;
import org.springframework.stereotype.Service;

@Service
public class BaseDataEncryptServiceImpl
implements BaseDataEncryptService {
    private byte[] mak;

    @Override
    public byte[] getMAK() {
        if (this.mak == null) {
            this.mak = "XsXzH6eP5W1nY8BbnlqQbw==".getBytes();
        }
        return this.mak;
    }

    @Override
    public String getSecretKey() {
        return "DES";
    }
}

