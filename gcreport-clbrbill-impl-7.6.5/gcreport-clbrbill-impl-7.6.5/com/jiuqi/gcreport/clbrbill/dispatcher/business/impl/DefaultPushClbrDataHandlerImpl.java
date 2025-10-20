/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business.impl;

import com.jiuqi.gcreport.clbrbill.dispatcher.business.AbstractPushClbrDataHandler;
import org.springframework.stereotype.Service;

@Service
public class DefaultPushClbrDataHandlerImpl
extends AbstractPushClbrDataHandler {
    @Override
    public String[] getSysCode() {
        return new String[]{"DEFAULT"};
    }
}

