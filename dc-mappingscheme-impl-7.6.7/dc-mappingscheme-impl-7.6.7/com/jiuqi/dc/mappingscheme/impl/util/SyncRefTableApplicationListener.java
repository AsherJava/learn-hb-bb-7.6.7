/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.SyncTableEvent
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.dc.base.common.event.SyncTableEvent;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.domain.common.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SyncRefTableApplicationListener
implements ApplicationListener<SyncTableEvent> {
    @Autowired
    private DataSchemeService dataSchemeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(String tenantName) {
        ShiroUtil.bindTenantName((String)tenantName);
    }

    @Override
    @Order(value=501)
    public void onApplicationEvent(SyncTableEvent event) {
        try {
            this.execute(event.getTenantName());
        }
        catch (Exception e) {
            this.logger.error("\u540c\u6b65\u8868\u7ed3\u6784\u51fa\u9519", e);
        }
    }
}

