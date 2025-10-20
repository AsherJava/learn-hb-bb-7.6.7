/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.service.impl.automatch;

import com.jiuqi.dc.datamapping.impl.service.impl.automatch.AbstractRefServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CodeRefServiceImpl
extends AbstractRefServiceImpl {
    @Override
    public String getCode() {
        return "CODE";
    }

    @Override
    public String getName() {
        return "\u4ee3\u7801\u5339\u914d";
    }
}

