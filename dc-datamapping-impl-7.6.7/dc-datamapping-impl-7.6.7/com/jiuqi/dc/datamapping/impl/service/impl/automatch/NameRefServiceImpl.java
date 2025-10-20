/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.service.impl.automatch;

import com.jiuqi.dc.datamapping.impl.service.impl.automatch.AbstractRefServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NameRefServiceImpl
extends AbstractRefServiceImpl {
    @Override
    public String getCode() {
        return "NAME";
    }

    @Override
    public String getName() {
        return "\u540d\u79f0\u5339\u914d";
    }
}

