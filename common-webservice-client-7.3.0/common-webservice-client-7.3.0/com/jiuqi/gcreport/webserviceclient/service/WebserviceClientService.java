/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.webserviceclient.service;

import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface WebserviceClientService {
    public AsyncTaskInfo publishWebservicClientTask(WebserviceClientParam var1);

    public TableModelDefine getWsClientBaseData(String var1);
}

