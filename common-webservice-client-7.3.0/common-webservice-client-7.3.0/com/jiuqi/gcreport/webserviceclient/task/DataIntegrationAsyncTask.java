/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  com.jiuqi.nr.etl.common.NrdlTask
 *  com.jiuqi.nr.etl.service.INrDataIntegrationService
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.webserviceclient.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.webserviceclient.task.DataIntegrationExecuter;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.gcreport.webserviceclient.utils.WebserviceClientUtil;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtParam;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DataIntegrationAsyncTask
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private INrDataIntegrationService iNrDataIntegrationService;
    @Lazy
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    private Logger logger = LoggerFactory.getLogger(DataIntegrationAsyncTask.class);

    public void execute(Object args, AsyncTaskMonitor monitor) {
        block5: {
            ArrayList<String> executeMessages = new ArrayList<String>();
            DataIntegrationtParam dataIntegrationtParam = (DataIntegrationtParam)args;
            dataIntegrationtParam.setExecuteMessages(executeMessages);
            try {
                WebserviceClientUtil.createAsyncTask(monitor, "\u3010\u4fe1\u606f\u3011 \u5f00\u59cb\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6267\u884c", executeMessages);
                WebserviceClientUtil.modifyAsyncTaskState(monitor, 0.1, "\u3010\u4fe1\u606f\u3011 \u521d\u59cb\u5316\u53c2\u6570\u4fe1\u606f", executeMessages);
                DataIntegrationtkExecuteParams etlTaskExecuteParams = this.getEtlTaskExecuteParams(dataIntegrationtParam);
                new DataIntegrationExecuter().executeEtlTask(monitor, etlTaskExecuteParams, executeMessages);
                if (monitor != null) {
                    if (monitor.isCancel()) {
                        String retStr = "\u4efb\u52a1\u53d6\u6d88";
                        monitor.canceled(retStr, executeMessages);
                    } else {
                        monitor.finish("\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6267\u884c\u5b8c\u6210", executeMessages);
                    }
                }
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(dataIntegrationtParam.getTaskKey());
                LogHelper.info((String)"\u6570\u636e\u5f55\u5165-\u6570\u636e\u96c6\u6210", (String)("etl\u4efb\u52a1-" + dataIntegrationtParam.getEtlTaskName() + "-\u62a5\u8868\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + dataIntegrationtParam.getPeriodString() + "-\u65e5\u5fd7\uff1a\n" + StringUtils.join((Object[])executeMessages.toArray(), (String)";")));
                dataIntegrationtParam.setStatus(true);
            }
            catch (Exception e) {
                dataIntegrationtParam.setStatus(false);
                executeMessages.add("\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6267\u884c\u6267\u884c\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
                this.logger.error("\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6267\u884c\u6267\u884c\u5f02\u5e38", e);
                if (monitor == null) break block5;
                monitor.error(e.getMessage(), (Throwable)e, StringUtils.join((Object[])executeMessages.toArray(), (String)";"));
            }
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNC_TASK_DATA_INTEGRATIONT";
    }

    private DataIntegrationtkExecuteParams getEtlTaskExecuteParams(DataIntegrationtParam dataIntegrationtParam) {
        DataIntegrationtkExecuteParams etlTaskExecuteParams = new DataIntegrationtkExecuteParams();
        etlTaskExecuteParams.setEtlTaskKey(this.getEtlTaskKey(dataIntegrationtParam));
        EtlServeEntity serveEntity = DataIntegrationUtil.getEtlServeEntity();
        String address = serveEntity.getAddress();
        if (StringUtils.isEmpty((String)address)) {
            throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
        }
        etlTaskExecuteParams.setUrl(address);
        String userName = serveEntity.getUserName();
        if (StringUtils.isEmpty((String)userName)) {
            throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
        }
        etlTaskExecuteParams.setUserName(userName);
        String pw = DataIntegrationUtil.getPassword(serveEntity);
        etlTaskExecuteParams.setPw(pw);
        JSONObject etlTaskParam = new JSONObject();
        etlTaskParam.put("TASKKEY", (Object)dataIntegrationtParam.getTaskKey());
        etlTaskParam.put("SCHEMEKEY", (Object)dataIntegrationtParam.getFormSchemeKey());
        etlTaskParam.put("DATATIME", (Object)dataIntegrationtParam.getPeriodString());
        Map<String, DimensionValue> dimensionSet = dataIntegrationtParam.getDimensionSet();
        if (dimensionSet.get("MD_CURRENCY") != null) {
            etlTaskParam.put("CURRENCY", (Object)dimensionSet.get("MD_CURRENCY").getValue());
        }
        if (dimensionSet.get("MD_GCORGTYPE") != null) {
            etlTaskParam.put("ORGTYPE", (Object)dimensionSet.get("MD_GCORGTYPE").getValue());
        }
        String unitCode = StringUtils.join((Object[])dataIntegrationtParam.getOrgCodeList().toArray(), (String)";");
        etlTaskParam.put("UNITCODE", (Object)unitCode);
        String formKey = StringUtils.join((Object[])dataIntegrationtParam.getFormKeyList().toArray(), (String)";");
        etlTaskParam.put("FORMKEY", (Object)formKey);
        List formDefineList = this.iRunTimeViewController.queryFormsById(dataIntegrationtParam.getFormKeyList());
        String formCode = StringUtils.join((Object[])formDefineList.stream().map(FormDefine::getFormCode).toArray(), (String)";");
        etlTaskParam.put("FORMCODE", (Object)formCode);
        etlTaskParam.put("USER", (Object)NpContextHolder.getContext().getUserName());
        etlTaskExecuteParams.setParam(etlTaskParam);
        return etlTaskExecuteParams;
    }

    private String getEtlTaskKey(DataIntegrationtParam dataIntegrationtParam) {
        String etlTaskKey = "";
        for (NrdlTask task : this.iNrDataIntegrationService.getAllTask()) {
            if (!task.getTaskName().equals(dataIntegrationtParam.getEtlTaskName())) continue;
            etlTaskKey = task.getTaskGuid();
        }
        if (StringUtils.isEmpty((String)etlTaskKey)) {
            throw new BusinessRuntimeException("etl\u670d\u52a1\u672a\u67e5\u8be2\u5230\u540d\u79f0\u4e3a" + dataIntegrationtParam.getEtlTaskName() + "\u7684\u4efb\u52a1");
        }
        return etlTaskKey;
    }
}

