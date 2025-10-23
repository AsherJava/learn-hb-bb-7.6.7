/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package com.jiuqi.nr.formulaeditor.common;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import java.util.HashSet;
import java.util.Set;

public class EditorSchemeNodeFilter
implements NodeFilter {
    private final String dataSchemeKey;
    private final Set<String> dims = new HashSet<String>();

    public boolean test(ISchemeNode t) {
        int type = t.getType();
        Object data = t.getData();
        if (NodeType.DIM.getValue() == type) {
            if (!t.getKey().startsWith(this.dataSchemeKey + ":")) {
                return false;
            }
            if (data instanceof DataDimension) {
                return ((DataDimension)data).getDimensionType() != DimensionType.PERIOD;
            }
        }
        return true;
    }

    public EditorSchemeNodeFilter(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

