/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.preview;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.FlexibleSolutionGroupTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.FlexibleSolutionTreeNodeBuilder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlexibleSumSolutionTreeServiceImpl
extends AbstractTreeService {
    @Override
    public String getId() {
        return "flexible_sum_solution_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(new FlexibleSolutionGroupTreeNodeBuilder());
        customDataProviderList.add(new FlexibleSolutionTreeNodeBuilder());
    }
}

