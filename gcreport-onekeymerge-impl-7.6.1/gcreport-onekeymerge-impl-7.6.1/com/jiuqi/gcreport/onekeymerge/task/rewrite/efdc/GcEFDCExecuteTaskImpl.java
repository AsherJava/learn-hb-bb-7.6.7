/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.efdc.asynctask.EFDCAsyncTaskExecutor
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.efdc;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.efdc.asynctask.EFDCAsyncTaskExecutor;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcEFDCExecuteTaskImpl {
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private FinishCalcConfig finishCalcConfig;
    private final Logger logger = LoggerFactory.getLogger(GcEFDCExecuteTaskImpl.class);

    public ReturnObject doTask(GcActionParamsVO paramsVO, String formKeys, String orgId) {
        ReturnObject returnObject;
        block5: {
            returnObject = new ReturnObject();
            EfdcInfo efdcInfo = new EfdcInfo();
            efdcInfo.setTaskKey(paramsVO.getTaskId());
            efdcInfo.setFormSchemeKey(paramsVO.getSchemeId());
            efdcInfo.setFormKey(formKeys);
            efdcInfo.setVariableMap(new HashMap());
            Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgId, paramsVO.getSelectAdjustCode());
            efdcInfo.setDimensionSet(dimensionValueMap);
            try {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setTaskKey(efdcInfo.getContext().getTaskKey());
                npRealTimeTaskInfo.setFormSchemeKey(efdcInfo.getContext().getFormSchemeKey());
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)efdcInfo));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EFDCAsyncTaskExecutor());
                String efdcAsyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
                int i = 0;
                while (true) {
                    AsyncTask asyncTask;
                    TaskState state;
                    if (OneKeyMergeUtils.efdcProcessON(state = (asyncTask = this.asyncTaskManager.querySimpleTask(efdcAsyncTaskID)).getState())) {
                        Thread.sleep(1000L);
                        if (++i <= 60 * this.finishCalcConfig.getTimeOut()) continue;
                        throw new RuntimeException("EFDC\u6267\u884c\u8d85\u65f6\uff0c\u4efb\u52a1\u7ed3\u675f\u3002\u8bf7\u975e\u4e1a\u52a1\u7e41\u5fd9\u65f6\u6bb5\uff0c\u518d\u91cd\u65b0\u5b8c\u6210\u5408\u5e76\u3002");
                    }
                    if (OneKeyMergeUtils.efdcProcessEnd(state)) {
                        returnObject.setSuccess(true);
                        String queryDetail = (String)this.asyncTaskManager.queryDetail(efdcAsyncTaskID);
                        OneKeyMergeUtils.getResultFromAsyncTaskDetailEFDC(returnObject, queryDetail);
                        break block5;
                    }
                    if (OneKeyMergeUtils.efdcProcessError(state)) break;
                }
                returnObject.setSuccess(false);
                String queryDetail = (String)this.asyncTaskManager.queryDetail(efdcAsyncTaskID);
                OneKeyMergeUtils.getResultFromAsyncTaskDetailEFDC(returnObject, queryDetail);
            }
            catch (Exception e) {
                returnObject.setSuccess(false);
                returnObject.setErrorMessage(e.getMessage());
                this.logger.error(e.getMessage(), e);
            }
        }
        return returnObject;
    }
}

