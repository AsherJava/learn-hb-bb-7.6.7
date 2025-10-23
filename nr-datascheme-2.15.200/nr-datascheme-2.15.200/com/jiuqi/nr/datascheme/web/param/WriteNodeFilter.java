/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils;

public class WriteNodeFilter
implements NodeFilter {
    public boolean test(ISchemeNode t) {
        if (NodeType.SCHEME_GROUP.getValue() == t.getType()) {
            return DataSchemeBeanUtils.getDataSchemeAuthService().canWriteGroup(t.getKey());
        }
        if (NodeType.SCHEME.getValue() == t.getType()) {
            return DataSchemeBeanUtils.getDataSchemeAuthService().canWriteScheme(t.getKey());
        }
        return true;
    }
}

