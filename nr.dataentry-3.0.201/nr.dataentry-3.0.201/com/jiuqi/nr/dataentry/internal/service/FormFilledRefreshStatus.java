/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.service.IFormDataStatusService;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormFilledRefreshStatus
implements IRefreshStatus {
    @Autowired
    private IFormDataStatusService formDataStatusService;
    @Autowired
    private ITaskOptionController iTaskOptionController;

    @Override
    public boolean getEnable(String taskKey, String formSchemeKey) {
        String dataentryStaObj = this.iTaskOptionController.getValue(taskKey, "DATAENTRY_STATUS");
        return dataentryStaObj != null && (dataentryStaObj.contains("0") || dataentryStaObj.contains("1") || dataentryStaObj.contains("2"));
    }

    @Override
    public String getName() {
        return "formFilled";
    }

    @Override
    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.FORM;
    }

    public Object getStatus(JtableContext context) throws Exception {
        return this.formDataStatusService.getFilledFormkey(context);
    }
}

