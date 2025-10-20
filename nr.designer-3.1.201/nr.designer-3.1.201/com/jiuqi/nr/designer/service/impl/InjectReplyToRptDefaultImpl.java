/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.designer.service.IInjectReplyToRpt;
import com.jiuqi.nr.designer.web.facade.InjectContext;
import org.springframework.stereotype.Component;

@Component
public class InjectReplyToRptDefaultImpl
implements IInjectReplyToRpt {
    @Override
    public byte[] injectReplyToRpt(byte[] rptTemp, InjectContext context) {
        return rptTemp;
    }
}

