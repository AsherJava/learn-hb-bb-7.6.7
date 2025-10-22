/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 */
package com.jiuqi.gcreport.unionrule.vo;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import java.util.ArrayList;

public class SubjectITree<E extends GcBaseDataVO>
extends ITree {
    public void appendChild(ITree child) {
        if (super.getChildren() == null) {
            super.setChildren(new ArrayList());
        }
        super.getChildren().add(child);
    }

    public SubjectITree(E data) {
        super(data);
    }

    public SubjectITree() {
    }
}

