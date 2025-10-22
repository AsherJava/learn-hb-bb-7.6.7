/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  org.json.JSONArray
 */
package com.jiuqi.nr.dataSnapshot.common;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;

public class ResultExportExecutor
implements IFormCheckExecutor {
    protected IFormQueryContext context;

    public ResultExportExecutor(IFormQueryContext context) {
        this.context = context;
    }

    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        return forms.stream().filter(form -> this.checkForm((FormDefine)form)).collect(Collectors.toList());
    }

    Boolean checkForm(FormDefine form) {
        if (form == null || form.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || form.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || form.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) || form.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS)) {
            return false;
        }
        if (this.context.getCustomVariable().getJSONObject("DataSnapshotComparisonResultExportContext").has("showForms") && !this.context.getCustomVariable().getJSONObject("DataSnapshotComparisonResultExportContext").get("showForms").toString().equals("null") && this.context.getCustomVariable().getJSONObject("DataSnapshotComparisonResultExportContext").getJSONArray("showForms") != null) {
            JSONArray list = this.context.getCustomVariable().getJSONObject("DataSnapshotComparisonResultExportContext").getJSONArray("showForms");
            if ("ALLFORM".equals(list.toList().get(0)) || list.toList().contains(form.getKey())) {
                return true;
            }
            return false;
        }
        return true;
    }
}

