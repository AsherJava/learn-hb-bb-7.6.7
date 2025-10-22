/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.internal.service.FormLockServiceImpl;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormLockRefreshStatus
implements IRefreshStatus {
    @Autowired
    private FormLockServiceImpl formLockService;
    @Autowired
    private ITaskOptionController iTaskOptionController;

    @Override
    public boolean getEnable(String taskKey, String formSchemeKey) {
        String formLockObj = this.iTaskOptionController.getValue(taskKey, "FORM_LOCK");
        return "3".equals(formLockObj) || "2".equals(formLockObj) || "1".equals(formLockObj);
    }

    @Override
    public String getName() {
        return "formLock";
    }

    @Override
    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.UNIT;
    }

    public Map<String, String> getStatus(JtableContext context) throws Exception {
        FormLockParam formLockParam = new FormLockParam();
        formLockParam.setContext(context);
        formLockParam.setLock(true);
        Map<String, String> formLockMap = this.formLockService.getLockedFormKeysMap(formLockParam, false);
        return formLockMap;
    }
}

