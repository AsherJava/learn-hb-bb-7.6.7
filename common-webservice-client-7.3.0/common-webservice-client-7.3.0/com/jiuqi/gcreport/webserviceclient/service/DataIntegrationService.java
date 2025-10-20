/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.webserviceclient.service;

import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationTaskTreeVo;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtParam;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import java.util.List;
import java.util.Set;

public interface DataIntegrationService {
    public AsyncTaskInfo publishDataIntegrationtTask(DataIntegrationtParam var1);

    public List<DataIntegrationTaskTreeVo> listDataIntegrationTaskTree(Set<String> var1);
}

