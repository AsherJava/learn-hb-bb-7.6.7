/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.gather.IDataentryFormFilter
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataSnapshot.common;

import com.jiuqi.nr.dataentry.gather.IDataentryFormFilter;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSnapshotFormFilter
implements IDataentryFormFilter {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public boolean doFilter(JtableContext context, String formKey) {
        FormDefine formData;
        if (context.getVariableMap() != null && context.getVariableMap().containsKey("DATASNAPSHOT") && ((formData = this.iRunTimeViewController.queryFormById(formKey)) == null || formData.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formData.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formData.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) || formData.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS))) {
            return false;
        }
        if (context.getVariableMap() != null && context.getVariableMap().containsKey("SNAPSHOTCOMPSREFORMS")) {
            List formList = (List)context.getVariableMap().get("SNAPSHOTCOMPSREFORMS");
            if (formList.size() == 0 || formList.size() == 1 && ((String)formList.get(0)).equals("ALLFORM")) {
                return true;
            }
            for (String form : formList) {
                if (!formKey.equals(form)) continue;
                return true;
            }
            return false;
        }
        return true;
    }
}

