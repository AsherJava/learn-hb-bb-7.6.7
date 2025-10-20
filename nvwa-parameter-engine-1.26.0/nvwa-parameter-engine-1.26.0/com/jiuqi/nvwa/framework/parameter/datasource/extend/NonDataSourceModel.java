/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;

public class NonDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.nvwa.parameter.ds.none";

    public NonDataSourceModel() {
    }

    public NonDataSourceModel(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}

