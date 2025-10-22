/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType
 *  com.jiuqi.nr.dataentry.gather.IDataentryFormFilter
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.dataentry;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.dataentry.gather.IDataentryFormFilter;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BSummaryFormFilter
implements IDataentryFormFilter {
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private IRunTimeViewController runTimeViewController;

    public boolean doFilter(JtableContext context, String formKey) {
        boolean canFormShow;
        Map variableMap = context.getVariableMap();
        boolean bl = canFormShow = variableMap == null || variableMap.get("batchGatherSchemeCode") == null;
        if (!canFormShow) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (formDefine != null && (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_FMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM))) {
                return false;
            }
            String summarySchemeKey = variableMap.get("batchGatherSchemeCode").toString();
            SummaryScheme scheme = this.schemeService.findScheme(summarySchemeKey);
            SchemeRangeForm rangeForm = scheme.getRangeForm();
            boolean bl2 = canFormShow = RangeFormType.ALL.compareTo((Enum)rangeForm.getRangeFormType()) == 0;
            if (!canFormShow) {
                List formList = rangeForm.getFormList();
                canFormShow = formList != null && formList.contains(formKey);
            }
        }
        return canFormShow;
    }
}

