/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.datacheckcommon.service;

import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.param.QueryDimParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;

public interface IDataCheckCommonService {
    public DataCheckDimInfo queryDims(QueryDimParam var1) throws Exception;

    public DataCheckDimInfo queryDims(String var1, String var2, DimensionCollection var3) throws Exception;
}

