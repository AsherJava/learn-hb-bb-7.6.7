/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.preview;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.PreviewUnitDataProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreviewUnitTreeServiceImpl
extends AbstractTreeService {
    @Autowired
    private PreviewUnitDataProvider previewUnitDataProvider;

    @Override
    public String getId() {
        return "preview_unit_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(this.previewUnitDataProvider);
    }
}

