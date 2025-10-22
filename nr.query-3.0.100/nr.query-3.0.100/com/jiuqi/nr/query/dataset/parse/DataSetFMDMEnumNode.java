/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;

public abstract class DataSetFMDMEnumNode
extends DataSetDimensionNode {
    private static final long serialVersionUID = 579464649006954858L;
    protected DataSetDimensionNode fmdmNode;

    public DataSetFMDMEnumNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName, DataSetDimensionNode fmdmNode) {
        super(entityTableName, entityModel, sourceTableName, sourceFieldName);
        this.fmdmNode = fmdmNode;
    }

    public DataSetFMDMEnumNode(String entityTableName, String entityFieldName, DataSetDimensionNode fmdmNode) {
        super(entityTableName, entityFieldName);
        this.fmdmNode = fmdmNode;
    }
}

