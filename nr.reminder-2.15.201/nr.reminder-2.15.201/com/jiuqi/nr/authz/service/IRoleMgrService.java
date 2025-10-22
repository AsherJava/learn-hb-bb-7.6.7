/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.authz.service;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.nr.authz.bean.RoleWebImpl;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface IRoleMgrService {
    public List<RoleWebImpl> getChildren(RoleWebImpl var1);

    public List<ITree<RoleWebImpl>> getRootNode();

    public List<ITree<RoleWebImpl>> getChildNode(RoleWebImpl var1);

    public List<ITree<RoleWebImpl>> getSearchTree(RoleWebImpl var1);

    public List<Role> getAllRoles();

    public List<Role> getRolesByIds(List<String> var1);

    public List<RoleWebImpl> searchRoleByFuzzyQuery(RoleWebImpl var1);

    public RoleWebImpl getRoleDetail(RoleWebImpl var1);

    public int getVisibleCount();
}

