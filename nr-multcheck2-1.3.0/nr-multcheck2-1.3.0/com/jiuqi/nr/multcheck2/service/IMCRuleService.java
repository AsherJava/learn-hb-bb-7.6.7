/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import java.util.List;

public interface IMCRuleService {
    public List<ITree<OrgTreeNode>> buildRuleGroupTree();

    public CheckGroupingVO getRuleGroupByKey(String var1);
}

