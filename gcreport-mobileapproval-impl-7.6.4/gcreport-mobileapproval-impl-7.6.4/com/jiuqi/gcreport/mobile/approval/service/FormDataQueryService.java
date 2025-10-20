/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FieldDataInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.mobile.approval.service;

import com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FieldDataInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo;
import com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface FormDataQueryService {
    public List<List<FieldDataInfo>> formDataQuery(FormDataQueryInfo var1);

    public void previewFile(String var1, HttpServletResponse var2);

    public ActionDataReturnInfo queryWorkflowDataInfo(QueryParamInfo var1);
}

