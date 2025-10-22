/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.dao;

import com.jiuqi.nr.efdc.bean.TransferEntityBean;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import java.util.List;
import java.util.UUID;

public interface SoluctionsDao {
    public String insert(QueryObjectImpl var1);

    public void update(QueryObjectImpl var1);

    public QueryObjectImpl find(UUID var1);

    public QueryObjectImpl find(QueryObjectImpl var1);

    public void delete(QueryObjectImpl var1);

    public void deleteAll(List<QueryObjectImpl> var1);

    public List<QueryObjectImpl> findAll();

    public List<QueryObjectImpl> getSolutions(QueryObjectImpl var1);

    public List<QueryObjectImpl> getSolutions2(QueryObjectImpl var1);

    public List<QueryObjectImpl> getSolutions(QueryObjectImpl var1, Boolean var2);

    public List<TransferEntityBean> findAllData();

    public void batchUpdate(List<TransferEntityBean> var1);

    public int countSolutionByTask(String var1);
}

