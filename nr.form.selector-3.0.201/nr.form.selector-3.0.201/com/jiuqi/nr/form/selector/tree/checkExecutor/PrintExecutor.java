/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  org.json.JSONObject
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class PrintExecutor
implements IFormCheckExecutor {
    protected IFormQueryContext context;

    public PrintExecutor(IFormQueryContext context) {
        this.context = context;
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        ArrayList resultForms = new ArrayList();
        return forms.stream().filter(form -> this.checkForm((FormDefine)form)).collect(Collectors.toList());
    }

    Boolean checkForm(FormDefine form) {
        String taskId = this.context.getTaskKey();
        Boolean formPrint = true;
        if (this.context.getCustomVariable().getJSONObject("printFilterContext").has("batchPrintFilterInfo") && !this.context.getCustomVariable().getJSONObject("printFilterContext").get("batchPrintFilterInfo").toString().equals("null") && this.context.getCustomVariable().getJSONObject("printFilterContext").getJSONObject("batchPrintFilterInfo") != null) {
            JSONObject batchPrintFilterInfo = this.context.getCustomVariable().getJSONObject("printFilterContext").getJSONObject("batchPrintFilterInfo");
            if (batchPrintFilterInfo.has("taskFilterFormList") && batchPrintFilterInfo.getJSONObject("taskFilterFormList") != null && batchPrintFilterInfo.getJSONObject("taskFilterFormList").toMap().size() > 0) {
                Map tempTaskFilterFormList = batchPrintFilterInfo.getJSONObject("taskFilterFormList").toMap();
                HashMap taskFilterFormList = new HashMap();
                for (Map.Entry entry : tempTaskFilterFormList.entrySet()) {
                    taskFilterFormList.put(entry.getKey(), (List)entry.getValue());
                }
                if (taskFilterFormList.get(taskId) != null && ((List)taskFilterFormList.get(taskId)).size() > 0) {
                    List formList = (List)taskFilterFormList.get(taskId);
                    return formList.indexOf(form.getFormCode()) < 0;
                }
            } else if (batchPrintFilterInfo.has("taskNotFilterFormList") && batchPrintFilterInfo.getJSONObject("taskNotFilterFormList") != null && batchPrintFilterInfo.getJSONObject("taskNotFilterFormList").toMap().size() > 0) {
                Map tempTaskTaskNotFilterFormList = batchPrintFilterInfo.getJSONObject("taskNotFilterFormList").toMap();
                HashMap taskNotFilterFormList = new HashMap();
                for (Map.Entry entry : tempTaskTaskNotFilterFormList.entrySet()) {
                    taskNotFilterFormList.put(entry.getKey(), (List)entry.getValue());
                }
                if (taskNotFilterFormList.get(taskId) != null && ((List)taskNotFilterFormList.get(taskId)).size() > 0) {
                    List notFilterFormList = (List)taskNotFilterFormList.get(taskId);
                    return notFilterFormList.indexOf(form.getFormCode()) >= 0;
                }
            }
        }
        return formPrint;
    }
}

