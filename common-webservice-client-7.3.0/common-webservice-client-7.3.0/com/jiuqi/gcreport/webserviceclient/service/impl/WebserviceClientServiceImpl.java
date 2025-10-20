/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.webserviceclient.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.webserviceclient.service.WebserviceClientService;
import com.jiuqi.gcreport.webserviceclient.utils.WebserviceClientUtil;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebserviceClientServiceImpl
implements WebserviceClientService {
    private Logger logger = LoggerFactory.getLogger(WebserviceClientServiceImpl.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private GcBaseDataService baseDataService;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public AsyncTaskInfo publishWebservicClientTask(WebserviceClientParam webserviceClientParam) {
        WebserviceClientUtil.initWSClientParam(webserviceClientParam);
        String asynTaskID = this.asyncTaskManager.publishTask((Object)webserviceClientParam, "GC_ASYNC_TASK_WEBSERVICE_CLIENT");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public TableModelDefine getWsClientBaseData(String tableName) {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode("MD_WS_CLIENT");
        if (tableModelDefine == null) {
            throw new BusinessRuntimeException("\u83b7\u53d6Webservice\u5ba2\u6237\u7aef\u57fa\u7840\u6570\u636e\u5931\u8d25\u3002");
        }
        return tableModelDefine;
    }
}

