/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public class GenerateParam {
    private DimensionCollection dimensionCollection;
    private List<FormDefine> formDefines;
    private FormSchemeDefine formSchemeDefine;
    private IBatchAccessResult batchAccessResult;
    private TitleShowSetting titleShowSetting;

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<FormDefine> getFormDefines() {
        return this.formDefines;
    }

    public void setFormDefines(List<FormDefine> formDefines) {
        this.formDefines = formDefines;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public IBatchAccessResult getBatchAccessResult() {
        return this.batchAccessResult;
    }

    public void setBatchAccessResult(IBatchAccessResult batchAccessResult) {
        this.batchAccessResult = batchAccessResult;
    }

    public TitleShowSetting getTitleShowSetting() {
        return this.titleShowSetting;
    }

    public void setTitleShowSetting(TitleShowSetting titleShowSetting) {
        this.titleShowSetting = titleShowSetting;
    }
}

