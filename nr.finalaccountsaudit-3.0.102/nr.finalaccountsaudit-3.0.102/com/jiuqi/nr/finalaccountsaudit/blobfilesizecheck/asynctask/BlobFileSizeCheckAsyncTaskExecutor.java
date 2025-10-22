/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckParam;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.service.BlobFileSizeCheckService;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.web.BlobFileSizeCheckController;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableBean;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_BLOBFILESIZECHECK", groupTitle="\u9644\u4ef6\u5ba1\u6838")
public class BlobFileSizeCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BlobFileSizeCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        BlobFileSizeCheckService blobFileSizeCheckService = (BlobFileSizeCheckService)BeanUtil.getBean(BlobFileSizeCheckService.class);
        BlobFileSizeCheckController blobSession = (BlobFileSizeCheckController)BeanUtil.getBean(BlobFileSizeCheckController.class);
        DataDefinitionRuntimeController2 dataDefinitionRuntimeController = (DataDefinitionRuntimeController2)BeanUtil.getBean(DataDefinitionRuntimeController2.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BLOBFILESIZECHECK.getName(), jobContext);
        try {
            Object args = new Object();
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            }
            BlobFileSizeCheckReturnInfo result = null;
            BlobFileSizeCheckParam param = null;
            if (args instanceof BlobFileSizeCheckParam) {
                param = (BlobFileSizeCheckParam)args;
                if (param.getSelBlobItem() == null || param.getSelBlobItem().size() == 0) {
                    List<BlobFormStruct> items = blobSession.getBlobTablesAndFields(param.getContext().getFormSchemeKey());
                    param.setSelBlobItem(items);
                }
            } else if (args instanceof JtableContext) {
                param = new BlobFileSizeCheckParam();
                Object auditScope = null;
                param.setSelBlobItem(null);
                MultCheckTableBean.AuditScopeAttachment item = auditScope;
                HashMap<String, BlobFormStruct> formMaps = new HashMap<String, BlobFormStruct>();
                Set<MultCheckTableBean.ZBInfo> zbs = item.getZbList();
                if (zbs != null) {
                    for (MultCheckTableBean.ZBInfo zb : zbs) {
                        FormDefine formDefine = runTimeViewController.queryFormById(zb.getFormKey());
                        FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(zb.getFieldkey());
                        if (formDefine == null || fieldDefine == null) continue;
                        BlobFormStruct formStruct = null;
                        if (formMaps.containsKey(zb.getFormKey())) {
                            formStruct = (BlobFormStruct)formMaps.get(zb.getFormKey());
                        } else {
                            formStruct = new BlobFormStruct();
                            formStruct.setFlag(formDefine.getFormCode());
                            formStruct.setKey(formDefine.getKey());
                            formStruct.setTitle(formDefine.getTitle());
                            formMaps.put(zb.getFormKey(), formStruct);
                        }
                        BlobFieldStruct fieldStruct = new BlobFieldStruct();
                        fieldStruct.setDataLinkKey(zb.getDataLinkKey());
                        fieldStruct.setKey(fieldDefine.getKey());
                        fieldStruct.setFlag(fieldDefine.getCode());
                        fieldStruct.setFormCode(formDefine.getFormCode());
                        fieldStruct.setFormKey(formDefine.getKey());
                        fieldStruct.setFormTitle(formDefine.getTitle());
                        fieldStruct.setTitle(fieldDefine.getTitle());
                        formStruct.getChildren().add(fieldStruct);
                    }
                    param.setSelBlobItem(new ArrayList<BlobFormStruct>(formMaps.values()));
                }
            } else {
                throw new Exception("\u4f20\u9012\u53c2\u6570\u7c7b\u578b\u9519\u8bef\u3002");
            }
            result = blobFileSizeCheckService.blobFileSizeCheck(param, (AsyncTaskMonitor)asyncTaskMonitor);
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.finish(finishInfo, (Object)result);
            }
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error(nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BLOBFILESIZECHECK.getName();
    }
}

