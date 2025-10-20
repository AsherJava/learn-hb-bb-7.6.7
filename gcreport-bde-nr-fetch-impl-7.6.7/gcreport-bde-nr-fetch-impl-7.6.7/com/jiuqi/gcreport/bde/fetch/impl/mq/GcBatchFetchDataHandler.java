/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.bde.log.fetch.dto.FetchLogDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataentry.provider.DimensionValueProvider
 *  com.jiuqi.nr.dataentry.service.ICurrencyService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.gcreport.bde.fetch.impl.mq;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLogVO;
import com.jiuqi.gcreport.bde.fetch.impl.intf.GcFetchInfo;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeBatchFetchDataHandler;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeBatchFetchDataHandlerGather;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchDataExecuteService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBatchFetchDataHandler
implements ITaskHandler {
    private static final Logger log = LoggerFactory.getLogger(GcBatchFetchDataHandler.class);
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IBeforeBatchFetchDataHandlerGather fetchDataHandlerGather;
    @Autowired
    private GcFetchDataExecuteService executeService;
    private static final String SPECIAL_QUEUE_FLAG = "BATCH_FETCH";

    public String getName() {
        return FetchTaskType.NR_BATCH_FETCH.getCode();
    }

    public String getTitle() {
        return FetchTaskType.NR_BATCH_FETCH.getTitle();
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(preParam, "#");
        return params;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        TaskHandleResult result;
        try {
            result = new TaskHandleResult();
            TaskHandleMsg handleMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
            String runnerId = handleMsg.getRunnerId();
            GcFetchInfo fetchInfo = (GcFetchInfo)JsonUtils.readValue((String)param, GcFetchInfo.class);
            BdeCommonUtil.initNpUser((String)fetchInfo.getUserName());
            FetchTaskUtil.buildNrCtxByOrgType((String)fetchInfo.getRpUnitType());
            EfdcInfo efdcInfo = fetchInfo.getEfdcInfo();
            String taskKey = efdcInfo.getTaskKey();
            String formSchemeKey = efdcInfo.getFormSchemeKey();
            DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)SpringContextUtils.getBean(DimensionCollectionUtil.class);
            DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(efdcInfo.getDimensionSet(), efdcInfo.getFormSchemeKey());
            ArrayList allSplitDimensionValueList = new ArrayList();
            for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
                Iterator<Object> dimensionValueMap = new HashMap();
                for (Object key : efdcInfo.getDimensionSet().keySet()) {
                    DimensionValue dimensionValue = new DimensionValue((DimensionValue)efdcInfo.getDimensionSet().get(key));
                    dimensionValue.setValue(dimensionCombination.getValue((String)key).toString());
                    dimensionValueMap.put(key, dimensionValue);
                }
                allSplitDimensionValueList.add(dimensionValueMap);
            }
            BatchBdeFetchLogVO batchBdeFetchLogVO = new BatchBdeFetchLogVO();
            if (allSplitDimensionValueList.isEmpty()) {
                DimensionCombination dimensionCombination;
                batchBdeFetchLogVO.setErrorLog("\u6240\u9009\u5355\u4f4d\u4e0e\u5176\u4ed6\u7ef4\u5ea6\u65e0\u5173\u8054\u5173\u7cfb");
                monitor.progressAndLog(1.0, "BDE\u6279\u91cf\u53d6\u6570\u51fa\u9519:\u6240\u9009\u5355\u4f4d\u4e0e\u5176\u4ed6\u7ef4\u5ea6\u65e0\u5173\u8054\u5173\u7cfb");
                dimensionCombination = result;
                return dimensionCombination;
            }
            List<EfdcInfo> efdcInfoList = new ArrayList<EfdcInfo>();
            for (String string : efdcInfo.getDimensionSet().keySet()) {
                if (!string.equals("ADJUST")) continue;
                for (Map map : allSplitDimensionValueList) {
                    map.put("ADJUST", efdcInfo.getDimensionSet().get("ADJUST"));
                }
            }
            for (Map map : allSplitDimensionValueList) {
                EfdcInfo subEfdcInfo = new EfdcInfo();
                HashMap hashMap = new HashMap();
                hashMap.putAll(efdcInfo.getVariableMap());
                BeanUtils.copyProperties(efdcInfo, subEfdcInfo);
                subEfdcInfo.setVariableMap(hashMap);
                if (map.get("MD_CURRENCY") != null && ("PROVIDER_BASECURRENCY".equals(((DimensionValue)map.get("MD_CURRENCY")).getValue()) || "PROVIDER_PBASECURRENCY".equals(((DimensionValue)map.get("MD_CURRENCY")).getValue()))) {
                    JtableContext jtableContext = new JtableContext();
                    jtableContext.setDimensionSet(map);
                    jtableContext.setTaskKey(taskKey);
                    jtableContext.setFormSchemeKey(formSchemeKey);
                    List currency = this.currencyService.getCurrencyInfo(jtableContext, ((DimensionValue)map.get("MD_CURRENCY")).getValue());
                    if (!CollectionUtils.isEmpty((Collection)currency)) {
                        ((DimensionValue)map.get("MD_CURRENCY")).setValue((String)currency.get(0));
                    }
                }
                subEfdcInfo.setDimensionSet(map);
                DimensionValue periodDim = (DimensionValue)map.get("DATATIME");
                if (periodDim == null) {
                    log.info("\u65f6\u671f\u4e3a\u7a7a\u8df3\u8fc7\u53d6\u6570");
                    continue;
                }
                subEfdcInfo.setFormSchemeKey(formSchemeKey);
                String taskId = UUIDUtils.newHalfGUIDStr();
                subEfdcInfo.getVariableMap().put("REQUEST_TASK_ID", taskId);
                efdcInfoList.add(subEfdcInfo);
            }
            if (!CollectionUtils.isEmpty(efdcInfoList)) {
                batchBdeFetchLogVO.setTaskNum(efdcInfoList.size());
            }
            for (IBeforeBatchFetchDataHandler iBeforeBatchFetchDataHandler : this.fetchDataHandlerGather.getHandlerList()) {
                efdcInfoList = iBeforeBatchFetchDataHandler.rewriteEfdcInfo(runnerId, efdcInfoList);
            }
            efdcInfoList = efdcInfoList == null ? CollectionUtils.newArrayList() : efdcInfoList;
            FetchLogDTO logResult = new FetchLogDTO(efdcInfoList.size());
            Object var16_23 = null;
            FetchInitTaskDTO fetchInitTask = null;
            ArrayList<FetchInitTaskDTO> arrayList = new ArrayList<FetchInitTaskDTO>();
            for (int i = 0; i < efdcInfoList.size(); ++i) {
                EfdcInfo efdcInfo2 = efdcInfoList.get(i);
                if (Boolean.FALSE.equals(efdcInfo2.getVariableMap().get("ETL_SUCCESS"))) {
                    FetchItemLogDTO fetchLog = this.executeService.buildFetchLogInfo(efdcInfo2);
                    fetchLog.setLog((String)efdcInfo2.getVariableMap().get("ETL_INFO"));
                    logResult.addFailedItem(fetchLog);
                    continue;
                }
                try {
                    fetchInitTask = this.executeService.buildFetchInitTask(efdcInfo2);
                    fetchInitTask.setForceSkipEtlHandle(Boolean.valueOf(true));
                }
                catch (Exception e) {
                    FetchItemLogDTO fetchLog = this.executeService.buildFetchLogInfo(efdcInfo2);
                    fetchLog.setLog(e.getMessage());
                    logResult.addFailedItem(fetchLog);
                    continue;
                }
                arrayList.add(fetchInitTask);
            }
            result.setPreParam(JsonUtils.writeValueAsString(arrayList));
            result.setSuccess(Boolean.valueOf(!arrayList.isEmpty()));
            result.appendLog(JsonUtils.writeValueAsString((Object)logResult));
        }
        finally {
            NpContextHolder.clearContext();
        }
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return FetchDimType.BATCH;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public String getSpecialQueueFlag() {
        return SPECIAL_QUEUE_FLAG;
    }
}

