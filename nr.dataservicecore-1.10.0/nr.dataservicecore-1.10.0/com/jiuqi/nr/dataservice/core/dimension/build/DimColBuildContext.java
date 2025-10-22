/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.dataservice.core.dimension.build;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public class DimColBuildContext {
    private TaskDefine taskDefine;
    private FormSchemeDefine formSchemeDefine;
    private DimensionValueSet dimensionValueSet;
    private String mainDimId;
    private String dataSchemeKey;
    private DimensionCollectionBuilder dimensionCollectionBuilder;
    private DimensionProviderFactory dimensionProviderFactory;

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DimensionCollectionBuilder getDimensionCollectionBuilder() {
        return this.dimensionCollectionBuilder;
    }

    public void setDimensionCollectionBuilder(DimensionCollectionBuilder dimensionCollectionBuilder) {
        this.dimensionCollectionBuilder = dimensionCollectionBuilder;
    }

    public DimensionProviderFactory getDimensionProviderFactory() {
        return this.dimensionProviderFactory;
    }

    public void setDimensionProviderFactory(DimensionProviderFactory dimensionProviderFactory) {
        this.dimensionProviderFactory = dimensionProviderFactory;
    }

    public String getMainDimId() {
        return this.mainDimId;
    }

    public void setMainDimId(String mainDimId) {
        this.mainDimId = mainDimId;
    }
}

