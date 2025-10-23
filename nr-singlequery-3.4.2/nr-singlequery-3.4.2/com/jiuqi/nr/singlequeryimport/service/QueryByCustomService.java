/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;

public interface QueryByCustomService {
    public QueryResult query(QueryConfigInfo var1) throws Exception;

    public QueryResult querySamples(QueryConfigInfo var1) throws Exception;
}

