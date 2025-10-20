/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.StartReadyEvent
 */
package com.jiuqi.dc.base.impl.org;

import com.jiuqi.dc.base.common.event.StartReadyEvent;
import com.jiuqi.dc.base.impl.org.OrgCurrencyFieldInitUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component(value="orgFieldInitListener")
public class OrgFieldInitListener
implements ApplicationListener<StartReadyEvent> {
    @Override
    public void onApplicationEvent(StartReadyEvent event) {
        OrgCurrencyFieldInitUtil.syncOrgCurrencyField();
    }
}

