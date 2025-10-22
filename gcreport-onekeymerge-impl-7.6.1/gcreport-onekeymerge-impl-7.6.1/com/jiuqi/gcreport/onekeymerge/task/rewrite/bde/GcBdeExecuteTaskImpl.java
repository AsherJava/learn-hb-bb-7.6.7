/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.bde;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBdeExecuteTaskImpl {
    @Resource
    private GcOnekeyMergeService onekeyMergeService;
    @Resource
    private FinishCalcConfig finishCalcConfig;
    @Autowired
    private GcFetchService gcFetchService;
    private final Logger logger = LoggerFactory.getLogger(GcBdeExecuteTaskImpl.class);

    public ReturnObject doTask(GcActionParamsVO paramsVO, String formKeys, String orgId) {
        ReturnObject returnObject;
        block6: {
            returnObject = new ReturnObject();
            EfdcInfo efdcInfo = new EfdcInfo();
            efdcInfo.setTaskKey(paramsVO.getTaskId());
            efdcInfo.setFormSchemeKey(paramsVO.getSchemeId());
            efdcInfo.setFormKey(formKeys);
            Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgId, paramsVO.getSelectAdjustCode());
            efdcInfo.setDimensionSet(dimensionValueMap);
            try {
                GcOrgTypeUtils.setContextEntityId((String)paramsVO.getOrgType());
                AsyncTaskInfo taskInfo = this.gcFetchService.fetchData(efdcInfo);
                while (TaskState.OUTOFQUEUE.equals((Object)taskInfo.getState())) {
                    taskInfo = this.gcFetchService.fetchData(efdcInfo);
                    Thread.sleep(5000L);
                }
                int i = 0;
                while (true) {
                    TaskState state;
                    if (OneKeyMergeUtils.efdcProcessON(state = (taskInfo = this.gcFetchService.queryFetchTask(taskInfo.getId())).getState())) {
                        Thread.sleep(1000L);
                        if (++i <= 60 * this.finishCalcConfig.getTimeOut()) continue;
                        throw new RuntimeException("BDE\u6267\u884c\u8d85\u65f6\uff0c\u4efb\u52a1\u7ed3\u675f\u3002\u8bf7\u975e\u4e1a\u52a1\u7e41\u5fd9\u65f6\u6bb5\uff0c\u518d\u91cd\u65b0\u5b8c\u6210\u5408\u5e76\u3002");
                    }
                    if (OneKeyMergeUtils.efdcProcessEnd(state)) {
                        returnObject.setSuccess(true);
                        String queryDetail = ConverterUtils.getAsString((Object)taskInfo.getDetail());
                        returnObject.setData((Object)queryDetail);
                        break block6;
                    }
                    if (OneKeyMergeUtils.efdcProcessError(state)) break;
                }
                returnObject.setSuccess(false);
                String queryDetail = ConverterUtils.getAsString((Object)taskInfo.getDetail());
                returnObject.setErrorMessage(queryDetail);
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

