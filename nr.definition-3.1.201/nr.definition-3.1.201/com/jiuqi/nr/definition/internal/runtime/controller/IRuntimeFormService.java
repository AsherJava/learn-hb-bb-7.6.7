/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.util.List;

public interface IRuntimeFormService {
    public FormDefine queryForm(String var1);

    public List<FormDefine> queryForms(List<String> var1);

    public FormDefine queryFormByCodeInScheme(String var1, String var2);

    public List<FormDefine> queryFormDefinesByTask(String var1);

    public List<FormDefine> queryFormDefinesByFormScheme(String var1);

    public List<String> queryFormKeysByFormScheme(String var1);

    public List<FormDefine> getFormsInGroupOrderByGroupLink(String var1, boolean var2);

    public List<FormDefine> getFormsInGroup(String var1, boolean var2);

    public int getFormsCountInGroup(String var1, boolean var2);

    public BigDataDefine getReportDataFromForm(String var1);

    public String getFillingGuideFromForm(String var1);

    public String getFrontScriptFromForm(String var1);

    public String getSurveyDataFromForm(String var1);

    public RunTimeFormGroupLink queryFormGroupLink(String var1, String var2) throws Exception;

    public List<RunTimeFormGroupLink> queryFormGroupLink(String var1);

    default public FormDefine queryForm(String formKey, String formScheme) {
        return this.queryForm(formKey);
    }

    default public List<FormDefine> listFormByGroup(String formGroupKey, String formSchemeKey) {
        return this.getFormsInGroupOrderByGroupLink(formGroupKey, false);
    }

    default public BigDataDefine getFormStyle(String formKey, String formSchemeKey) {
        return this.getReportDataFromForm(formKey);
    }
}

