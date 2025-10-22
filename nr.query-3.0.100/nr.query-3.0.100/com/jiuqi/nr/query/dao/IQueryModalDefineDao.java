/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;

public interface IQueryModalDefineDao {
    public Boolean insertQueryModalDefine(QueryModalDefine var1);

    public String updateQueryModalDefine(QueryModalDefine var1);

    public String deleteQueryModalDefineById(String var1);

    public QueryModalDefine getQueryModalDefineById(String var1);

    public QueryModalDefine queryModalDefineById(String var1);

    public List<QueryModalDefine> getAllQueryModalDefines();

    public List<String> getAllQueryTasks();

    public List<QueryModalDefine> getModalsByGroupId(String var1) throws Exception;

    public List<QueryModalDefine> getModalsByGroupId(String var1, QueryModelType var2) throws Exception;

    public List<QueryModalDefine> getModalsByCondition(String var1, String[] var2) throws Exception;

    public List<QueryModalDefine> getModalsByTitle(String var1, String var2) throws Exception;

    public List<QueryModalDefine> getOtherModals(QueryModelType var1, List<QueryModalGroup> var2);

    public List<QueryModalDefine> getChartModals() throws Exception;

    public List<QueryModalDefine> getAllModalsByModelType(QueryModelType var1);

    public Boolean updateQueryModalNoOptBlock(QueryModalDefine var1);
}

