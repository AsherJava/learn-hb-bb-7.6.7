/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlExtractBdeReport {
    @Autowired
    AsyncTaskManager asyncTaskManager;
    @Autowired
    private GcFetchService gcFetchService;
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlExtractBdeReport.class);

    public void executeBdeExtract(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        boolean flag;
        String queryDetail;
        block8: {
            SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
            SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
            EfdcInfo efdcInfo = new EfdcInfo();
            efdcInfo.setTaskKey(sameCtrlOffsetCond.getTaskId());
            efdcInfo.setFormSchemeKey(sameCtrlOffsetCond.getSchemeId());
            queryDetail = "";
            Map<String, DimensionValue> dimensionValueMap = this.buildDimensionMap(sameCtrlOffsetCond, sameCtrlExtractManageCond);
            efdcInfo.setDimensionSet(dimensionValueMap);
            flag = true;
            try {
                AsyncTaskInfo taskInfo = this.gcFetchService.fetchData(efdcInfo);
                int i = 0;
                while (true) {
                    AsyncTaskInfo asyncTask;
                    TaskState state;
                    if (this.efdcProcessON(state = (asyncTask = this.gcFetchService.queryFetchTask(taskInfo.getId())).getState())) {
                        Thread.sleep(1000L);
                        if (++i <= 300) continue;
                        throw new RuntimeException("BDE\u6267\u884c\u8d85\u65f6\uff0c\u4efb\u52a1\u7ed3\u675f\u3002");
                    }
                    if (this.efdcProcessEnd(state)) {
                        queryDetail = ConverterUtils.getAsString((Object)this.asyncTaskManager.queryDetail(taskInfo.getId()));
                        break block8;
                    }
                    if (this.efdcProcessError(state)) break;
                }
                flag = false;
                queryDetail = ConverterUtils.getAsString((Object)this.asyncTaskManager.queryDetail(taskInfo.getId()));
                if (StringUtils.isEmpty((String)queryDetail)) {
                    queryDetail = this.asyncTaskManager.queryResult(taskInfo.getId());
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                queryDetail = queryDetail + e.getMessage();
                logger.error("BDE\u6267\u884c\u7ebf\u7a0b\u88ab\u4e2d\u65ad\uff1a" + queryDetail, e);
                throw new RuntimeException(queryDetail);
            }
            catch (Exception e) {
                queryDetail = queryDetail + e.getMessage();
                logger.error("BDE\u6267\u884c\u7ed3\u679c\uff1a" + queryDetail, e);
                throw new RuntimeException(queryDetail);
            }
        }
        if (!flag) {
            throw new RuntimeException(queryDetail);
        }
        logger.info("BDE\u6267\u884c\u7ed3\u679c\uff1a" + queryDetail);
    }

    private boolean efdcProcessError(TaskState state) {
        return state.equals((Object)TaskState.ERROR) || state.equals((Object)TaskState.OVERTIME) || state.equals((Object)TaskState.NONE);
    }

    private boolean efdcProcessON(TaskState state) {
        return state.equals((Object)TaskState.PROCESSING) || state.equals((Object)TaskState.WAITING) || state.equals((Object)TaskState.CANCELING);
    }

    private boolean efdcProcessEnd(TaskState state) {
        return state.equals((Object)TaskState.CANCELED) || state.equals((Object)TaskState.FINISHED);
    }

    private Map<String, DimensionValue> buildDimensionMap(SameCtrlOffsetCond sameCtrlOffsetCond, SameCtrlExtractManageCond sameCtrlExtractManageCond) {
        GcOrgCacheVO currentUnit = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode());
        String orgTypeId = currentUnit.getOrgTypeId();
        Assert.isNotEmpty((String)orgTypeId, (String)(currentUnit.getTitle() + "\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a"), (Object[])new Object[0]);
        return DimensionUtils.buildDimensionMap((String)sameCtrlOffsetCond.getTaskId(), (String)sameCtrlOffsetCond.getCurrencyCode(), (String)sameCtrlOffsetCond.getPeriodStr(), (String)orgTypeId, (String)sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getVirtualCode(), (String)sameCtrlOffsetCond.getSelectAdjustCode());
    }
}

