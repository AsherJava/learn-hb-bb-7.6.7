/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.dao.impl;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.finalaccountsaudit.multcheck.dao.FormSchemeDao;
import com.jiuqi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormSchemeImpl
implements FormSchemeDao {
    @Autowired
    IRunTimeViewController runTimeViewController;

    @Override
    public String getFormSchemeByTaskAndPeriod(String period, String taskId) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        if (schemePeriodLinkDefine == null || StringUtils.isEmpty((String)schemePeriodLinkDefine.getSchemeKey())) {
            return null;
        }
        return schemePeriodLinkDefine.getSchemeKey();
    }
}

