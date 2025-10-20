/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.tree.vo.GroupVO
 */
package com.jiuqi.va.query.tree.dao;

import com.jiuqi.va.query.tree.vo.GroupVO;
import java.util.List;

public interface QueryGroupDao {
    public List<GroupVO> getGroupsByParentGroupId(String var1);

    public GroupVO getGroupById(String var1);

    public void save(GroupVO var1);

    public void delete(String var1);

    public boolean hasGroupByGroupId(String var1);

    public void update(GroupVO var1);

    public void updateByCode(GroupVO var1);

    public boolean hasGroupByCode(String var1);

    public GroupVO getGroupByCode(String var1);

    public List<GroupVO> getAllGroups();
}

