/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.intf.service;

import com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import java.util.List;

public interface IQueryModelExplainService {
    public ResultObject getExplainByModelIdAndCode(String var1, String var2, String var3) throws Exception;

    public ResultObject deleteExplainByModelIdAndCode(String var1, String var2, String var3) throws Exception;

    public ResultObject upDataExplain(QueryModelExplain var1) throws Exception;

    public ResultObject addExplain(QueryModelExplain var1) throws Exception;

    public ResultObject getExplainByModelId(String var1, String var2) throws Exception;

    public ResultObject batchUpDataExplain(List<QueryModelExplain> var1) throws Exception;

    public ResultObject batchAddExplainByModelId(List<QueryModelExplain> var1) throws Exception;

    public ResultObject deleteExplainByModelId(String var1, String var2) throws Exception;
}

