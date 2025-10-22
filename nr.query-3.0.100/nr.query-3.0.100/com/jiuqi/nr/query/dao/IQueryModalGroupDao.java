/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;

public interface IQueryModalGroupDao {
    public Boolean InsertQueryModalGroup(QueryModalGroup var1);

    public Boolean UpdateQueryModalGroup(QueryModalGroup var1);

    public Boolean DeleteQueryModalGroupById(String var1, List<QueryModalGroup> var2, List<QueryModalDefine> var3);

    public QueryModalGroup GetQueryModalGroupById(String var1);

    public List<QueryModalGroup> GetAllQueryModalGroups();

    public List<QueryModalGroup> GetAllQueryModalGroups(QueryModelType var1);

    public List<QueryModalGroup> getModalGroupByParentId(String var1);

    public List<QueryModalGroup> getModalGroupByParentId(String var1, QueryModelType var2);

    public List<QueryModalGroup> getGroupsByCondition(String var1, String[] var2);

    public List<QueryModalGroup> getModalGroupByTitle(String var1, String var2);

    public List<QueryModalGroup> getAllChartModalGroups();
}

