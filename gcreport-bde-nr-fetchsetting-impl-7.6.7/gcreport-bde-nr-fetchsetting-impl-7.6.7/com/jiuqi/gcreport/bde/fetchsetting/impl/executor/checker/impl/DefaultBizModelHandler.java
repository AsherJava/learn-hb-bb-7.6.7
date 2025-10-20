/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultBizModelHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return "DEFAULT";
    }
}

