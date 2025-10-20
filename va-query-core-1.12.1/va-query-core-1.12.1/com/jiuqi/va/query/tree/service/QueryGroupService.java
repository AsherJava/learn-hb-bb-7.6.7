/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.tree.vo.GroupVO
 */
package com.jiuqi.va.query.tree.service;

import com.jiuqi.va.query.tree.vo.GroupVO;
import java.util.List;

public interface QueryGroupService {
    public List<GroupVO> getGroupsByParentGroupId(String var1);

    public GroupVO getGroupById(String var1);

    public boolean hasGroupByCode(String var1);

    public void save(GroupVO var1);
}

