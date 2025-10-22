/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.nodemap.TreeNode
 */
package com.jiuqi.nr.batch.summary.storage.condition;

import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProviderImpl;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.itreebase.nodemap.TreeNode;

public class CustomConditionRowImpl
extends CustomCalibreRowDefine
implements CustomConditionRow {
    private CustomConditionRowProviderImpl wrapper;

    public CustomConditionRowImpl(CustomConditionRowProviderImpl wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String[] getPath() {
        TreeNode<CustomConditionRow> node = this.wrapper.getDataMap().get(this.getCode());
        return node.getPath();
    }

    @Override
    public int getMaxDepth() {
        TreeNode<CustomConditionRow> node = this.wrapper.getDataMap().get(this.getCode());
        return node.getMaxDepth();
    }

    @Override
    public int getChildCount() {
        TreeNode<CustomConditionRow> node = this.wrapper.getDataMap().get(this.getCode());
        return node.getChildren().size();
    }

    @Override
    public int getAllChildCount() {
        TreeNode<CustomConditionRow> node = this.wrapper.getDataMap().get(this.getCode());
        return node.getAllChildren().size();
    }
}

