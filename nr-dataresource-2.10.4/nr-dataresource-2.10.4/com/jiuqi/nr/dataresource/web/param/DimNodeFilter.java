/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 */
package com.jiuqi.nr.dataresource.web.param;

import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;

public class DimNodeFilter
implements NodeFilter {
    private SceneUtilService sceneUtil;

    public DimNodeFilter(SceneUtilService sceneUtil) {
        this.sceneUtil = sceneUtil;
    }

    public boolean test(ISchemeNode iSchemeNode) {
        String key;
        if (NodeType.DIM.getValue() == iSchemeNode.getType() && (key = iSchemeNode.getKey()).indexOf(":") > -1) {
            String schemeKey = key.split(":")[0];
            String dimKey = key.split(":")[1];
            return this.sceneUtil.isAddSceneByScheme(schemeKey, dimKey);
        }
        return true;
    }
}

