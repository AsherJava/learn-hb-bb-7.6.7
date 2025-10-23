/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.zb;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.zb.DataFieldTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.zb.DataSchemeTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.zb.DataTableTreeNodeBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZbTreeService
extends AbstractTreeService {
    @Autowired
    private DataSchemeTreeNodeBuilder dataSchemeTreeNodeBuilder;
    @Autowired
    private DataTableTreeNodeBuilder dataTableTreeNodeBuilder;
    @Autowired
    private DataFieldTreeNodeBuilder dataFieldTreeNodeBuilder;

    @Override
    public String getId() {
        return "zb_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(this.dataSchemeTreeNodeBuilder);
        customDataProviderList.add(this.dataTableTreeNodeBuilder);
        customDataProviderList.add(this.dataFieldTreeNodeBuilder);
    }
}

