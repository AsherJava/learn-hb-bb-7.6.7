/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.reportTag.service.impl;

import com.jiuqi.nr.definition.reportTag.InjectContext;
import com.jiuqi.nr.definition.reportTag.service.IInjectReplyToRpt;
import org.springframework.stereotype.Component;

@Component
public class InjectReplyToRptDefaultImpl
implements IInjectReplyToRpt {
    @Override
    public byte[] injectReplyToRpt(byte[] rptTemp, InjectContext context) {
        return rptTemp;
    }
}

