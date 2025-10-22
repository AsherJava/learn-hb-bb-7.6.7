/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.common.action.service;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.action.GcActionGather;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcActionService {
    @Autowired
    private GcActionGather actionGather;

    public Object executeAction(String actionCode, String actionParamJson) {
        AbstractGcActionItem actionItem = this.actionGather.findActionByName(actionCode);
        if (actionItem == null) {
            Object[] i18Args = new String[]{actionCode};
            GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args));
        }
        GcActionItemEnv actionItemEnv = new GcActionItemEnv(actionItem, actionParamJson);
        try {
            return actionItem.execute(actionItemEnv);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean isVisibleAction(String actionCode, String visibleContextJson) {
        AbstractGcActionItem actionItem = this.actionGather.findActionByName(actionCode);
        if (actionItem == null) {
            Object[] i18Args = new String[]{actionCode};
            GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args));
        }
        return actionItem.isVisible(visibleContextJson);
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean isEnableAction(String actionCode, String enableContextJson) {
        AbstractGcActionItem actionItem = this.actionGather.findActionByName(actionCode);
        if (actionItem == null) {
            Object[] i18Args = new String[]{actionCode};
            GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.action.notfound", (Object[])i18Args));
        }
        return actionItem.isEnable(enableContextJson);
    }
}

