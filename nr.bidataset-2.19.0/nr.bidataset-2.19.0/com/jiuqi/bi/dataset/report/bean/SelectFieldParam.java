/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.bi.dataset.report.bean;

import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelFieldParam;
import com.jiuqi.bi.dataset.report.remote.controller.vo.SelectFieldVo;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;

public class SelectFieldParam
implements IReportDsModelFieldParam {
    private SelectFieldVo selectFieldVo;
    private FormDefine formDefine;

    public SelectFieldParam(SelectFieldVo selectFieldVo, FormDefine formDefine) {
        this.selectFieldVo = selectFieldVo;
        this.formDefine = formDefine;
    }

    @Override
    public String getKey() {
        return this.selectFieldVo.getFieldCode();
    }

    @Override
    public String getCode() {
        return this.selectFieldVo.getCode();
    }

    @Override
    public String getTitle() {
        return this.selectFieldVo.getFieldTitle();
    }

    @Override
    public String getTableName() {
        return this.selectFieldVo.getTableName();
    }

    @Override
    public String getExpression() {
        return this.selectFieldVo.getExpression();
    }

    @Override
    public boolean isFmdmField() {
        return FormType.FORM_TYPE_NEWFMDM.equals((Object)this.formDefine.getFormType());
    }

    @Override
    public String getDim() {
        return this.formDefine.getMasterEntitiesKey().split(";")[0];
    }

    @Override
    public String getTaskKey() {
        return this.selectFieldVo.getTaskId();
    }
}

