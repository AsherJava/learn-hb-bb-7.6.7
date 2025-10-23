/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package com.jiuqi.nr.datascheme.web.tree;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;

public class NoOtherDimFilter
implements NodeFilter {
    public boolean test(ISchemeNode node) {
        Object data;
        if (node != null && (data = node.getData()) != null && data instanceof DataDimension) {
            DimensionType dimensionType = ((DataDimension)data).getDimensionType();
            return dimensionType != DimensionType.PERIOD && dimensionType != DimensionType.DIMENSION;
        }
        return true;
    }
}

