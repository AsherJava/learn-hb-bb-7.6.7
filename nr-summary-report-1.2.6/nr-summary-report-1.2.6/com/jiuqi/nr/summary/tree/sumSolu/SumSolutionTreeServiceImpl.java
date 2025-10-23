/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.sumSolu;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionGroupTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionTreeNodeBuilder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SumSolutionTreeServiceImpl
extends AbstractTreeService {
    @Override
    public String getId() {
        return "sum_solution_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(new SolutionGroupTreeNodeBuilder());
        customDataProviderList.add(new SolutionTreeNodeBuilder());
    }
}

