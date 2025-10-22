/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.authz.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.authz.bean.UserQueryParam;
import com.jiuqi.nr.authz.bean.UserQueryParamExtend;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface IUserTreeService {
    public List<ITree<UserTreeNode>> getUsersByIds(List<String> var1);

    public List<ITree<UserTreeNode>> getUserByUserQueryParam(UserQueryParam var1) throws JQException;

    public List<UserTreeNode> getUserListByUserQueryParam(UserQueryParam var1) throws JQException;

    public long getUserCountByUserQueryParam(UserQueryParam var1);

    public List<ITree<UserTreeNode>> getUsersByUserQueryParamExtend(UserQueryParamExtend var1) throws JQException;
}

