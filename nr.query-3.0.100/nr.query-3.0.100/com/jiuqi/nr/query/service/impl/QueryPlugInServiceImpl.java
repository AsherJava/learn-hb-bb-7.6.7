/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.gather.ActionItem;
import com.jiuqi.nr.query.gather.IGathers;
import com.jiuqi.nr.query.gather.ITreeGathers;
import com.jiuqi.nr.query.service.IQueryPlugInService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPlugInServiceImpl
implements IQueryPlugInService {
    @Autowired
    private List<IGathers> gathers;

    @Override
    public Tree<ActionItem> getActions() {
        Tree tree = new Tree((Object)new ActionItem("rootNode", "\u6839\u8282\u70b9", "\u6839\u8282\u70b9"));
        for (IGathers gather : this.gathers) {
            if (!gather.getGatherType().getCode().equals(QueryConst.GatherType.ACTION.getCode())) continue;
            Tree treeItem = ((ITreeGathers)gather).gather();
            tree.addChild(treeItem);
        }
        return tree;
    }
}

