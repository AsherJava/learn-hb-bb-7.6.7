/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util.intern;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.JtableContextConvertUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class FormLockedState {
    FormLockedState() {
    }

    public static boolean isFormLocked(DimensionParamsVO param, String orgId, String formId) {
        JtableContext jtableContext = JtableContextConvertUtils.convert2JtableContext(param, orgId);
        FormLockParam lockParam = new FormLockParam();
        lockParam.setContext(jtableContext);
        jtableContext.setFormKey(formId);
        IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(IFormLockService.class);
        Boolean isLock = formLockService.isFormLocked(lockParam);
        return isLock;
    }

    public static boolean isFormLocked(DataEntryContext dataEntryContext, String formId) {
        JtableContext jtableContext = JtableContextConvertUtils.convert2JtableContext(dataEntryContext);
        jtableContext.setFormKey(formId);
        FormLockParam lockParam = new FormLockParam();
        lockParam.setContext(jtableContext);
        IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(IFormLockService.class);
        Boolean isLock = formLockService.isFormLocked(lockParam);
        return isLock;
    }

    public static String filterLockedForm(Collection<FormDefine> formDefines, String taskId, String schemeId, Map<String, DimensionValue> dimensionSetMap) {
        DataEntryContext newEnvContext = new DataEntryContext();
        newEnvContext.setTaskKey(taskId);
        newEnvContext.setFormSchemeKey(schemeId);
        newEnvContext.setDimensionSet(dimensionSetMap);
        StringBuffer lockedFormTitles = new StringBuffer(128);
        ArrayList<FormDefine> removedFormDefines = new ArrayList<FormDefine>();
        for (FormDefine formDefine : formDefines) {
            boolean isFormLocked = FormLockedState.isFormLocked(newEnvContext, formDefine.getKey());
            if (!isFormLocked) continue;
            lockedFormTitles.append(formDefine.getTitle()).append(",");
            removedFormDefines.add(formDefine);
        }
        formDefines.removeAll(removedFormDefines);
        if (lockedFormTitles.length() > 0) {
            lockedFormTitles.setLength(lockedFormTitles.length() - 1);
        }
        return lockedFormTitles.toString();
    }
}

