/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.preview;

import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionTreeNodeBuilder;
import com.jiuqi.nr.summary.vo.NodeType;
import org.springframework.util.StringUtils;

public class FlexibleSolutionTreeNodeBuilder
extends SolutionTreeNodeBuilder {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        String nodeKey;
        if (treeQueryParam != null && StringUtils.hasLength(nodeKey = treeQueryParam.getNodeKey())) {
            int type = (Integer)treeQueryParam.getCustomValue("type");
            return type == NodeType.SUMMARY_SOLUTION_GROUP.getCode();
        }
        return false;
    }
}

