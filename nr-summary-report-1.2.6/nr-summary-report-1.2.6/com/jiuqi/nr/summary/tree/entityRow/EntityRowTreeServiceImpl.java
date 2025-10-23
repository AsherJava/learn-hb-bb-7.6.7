/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.entityRow;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.entityRow.EntityRowDataProvider;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EntityRowTreeServiceImpl
extends AbstractTreeService {
    @Override
    public String getId() {
        return "entityRow_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(new EntityRowDataProvider());
    }
}

