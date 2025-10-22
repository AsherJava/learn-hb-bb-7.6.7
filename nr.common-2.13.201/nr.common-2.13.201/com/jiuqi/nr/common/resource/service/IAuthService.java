/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.common.resource.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import com.jiuqi.nr.common.resource.bean.ResourceWebImpl;
import java.util.List;

public interface IAuthService {
    public List<ITree<ResourceWebImpl>> getRootNode(String var1, String var2, boolean var3, Object var4);

    public List<ITree<ResourceWebImpl>> getChildNode(ResourceWebImpl var1, boolean var2);

    public List<ITree<ResourceWebImpl>> getRootNode(String var1, String var2, Object var3);

    public List<ITree<ResourceWebImpl>> getChildNode(ResourceWebImpl var1);

    public List<ITree<ResourceWebImpl>> getSelectedNode(List<ResourceWebImpl> var1) throws JQException;

    public void savePrivilege(PrivilegeWebImpl var1) throws JQException;

    public List<ResourceWebImpl> getPrivilege(List<ResourceWebImpl> var1);
}

