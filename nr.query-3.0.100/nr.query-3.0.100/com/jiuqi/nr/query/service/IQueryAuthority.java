/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;

public interface IQueryAuthority {
    public List<QueryModalDefine> getModalsByCondition(String var1, String[] var2, QueryModelType var3, String var4);

    public List<QueryModalGroup> getGroupsByCondition(String var1, String[] var2, QueryModelType var3, String var4);

    public List<QueryModalDefine> getModalsByGroupId(String var1, QueryModelType var2, String var3);

    public List<QueryModalGroup> getModalGroupByParentId(String var1, QueryModelType var2, String var3);

    public boolean canWriteModal(String var1, QueryModelType var2);

    public boolean canWriteModalGroup(String var1, QueryModelType var2);

    public boolean canDeleteModal(String var1, QueryModelType var2);

    public boolean canDeleteModalGroup(String var1, QueryModelType var2);

    public void grantAllPrivilegesForQueryModel(String var1, QueryModelType var2);

    public void grantAllPrivilegesForQueryModelGroup(String var1, QueryModelType var2);
}

