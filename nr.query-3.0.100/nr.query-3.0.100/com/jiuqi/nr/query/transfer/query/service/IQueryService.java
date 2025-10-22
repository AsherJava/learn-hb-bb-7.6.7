/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.query.transfer.query.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;

public interface IQueryService {
    public List<QueryModalGroup> getGroup(QueryModelType var1);

    public QueryModalGroup getGroup(String var1);

    public List<QueryModalGroup> getChildrenGroup(String var1);

    public String[] getGroupPath(String var1);

    public Boolean insertTemplate(QueryModalDefine var1) throws JQException;

    public Boolean insertGroup(QueryModalGroup var1) throws JQException;
}

