/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.impl.service.AsyncThreadExecutorImpl
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.impl.service.AsyncThreadExecutorImpl;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.bpm.asynctask.ClearSubProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.asynctask.StartSubProcessAsyncTaskExecutor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.systemoptions.WorkflowOptionsResult;
import com.jiuqi.nr.bpm.setting.bean.SubProcessBean;
import com.jiuqi.nr.bpm.setting.pojo.StartState;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.service.IBulidParam;
import com.jiuqi.nr.bpm.setting.service.IDeleteProcess;
import com.jiuqi.nr.bpm.setting.service.IStartProcess;
import com.jiuqi.nr.bpm.setting.service.SettingContextStrategy;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OperateWorkflow {
    private static final Logger logger = LoggerFactory.getLogger(OperateWorkflow.class);
    @Autowired(required=false)
    private IStartProcess starProcess;
    @Autowired(required=false)
    private IDeleteProcess deleteProcess;
    @Autowired
    private SettingUtil untilsMethod;
    @Autowired
    NpApplication npApplication;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Value(value="${jiuqi.nr.workflow.async-unit-num:200}")
    private int asyncUnitNum;
    @Autowired
    private SettingContextStrategy settingContextStrategy;

    public StartState operateProcess(StateChangeObj stateChange, WorkFlowType queryStartType, boolean bindFlag) {
        StartState startState = new StartState();
        WorkflowStatus flowType = this.untilsMethod.queryFlowType(stateChange.getFormSchemeId());
        if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
            startState = stateChange.isStart() ? this.startOpt(stateChange, flowType, bindFlag) : this.deleteOpt(stateChange, flowType, bindFlag);
        } else if (WorkFlowType.FORM.equals((Object)queryStartType) || WorkFlowType.GROUP.equals((Object)queryStartType)) {
            startState = stateChange.isStart() ? this.startOpt(stateChange, flowType, bindFlag) : this.deleteOpt(stateChange, flowType, bindFlag);
        }
        return startState;
    }

    public StartState operateProcess(StateChangeObj stateChange, boolean bindFlag, AsyncTaskMonitor asyncTaskMonitor) {
        StartState startState = new StartState();
        asyncTaskMonitor.progressAndMessage(0.2, "");
        WorkflowStatus flowType = this.untilsMethod.queryFlowType(stateChange.getFormSchemeId());
        WorkFlowType queryStartType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
            asyncTaskMonitor.progressAndMessage(0.3, "");
            startState = stateChange.isStart() ? this.startOpt(stateChange, flowType, bindFlag, asyncTaskMonitor) : this.deleteOpt(stateChange, flowType, bindFlag, asyncTaskMonitor);
            asyncTaskMonitor.progressAndMessage(0.9, "");
        } else if (WorkFlowType.FORM.equals((Object)queryStartType) || WorkFlowType.GROUP.equals((Object)queryStartType)) {
            asyncTaskMonitor.progressAndMessage(0.3, "");
            startState = stateChange.isStart() ? this.startOpt(stateChange, flowType, bindFlag, asyncTaskMonitor) : this.deleteOpt(stateChange, flowType, bindFlag, asyncTaskMonitor);
            asyncTaskMonitor.progressAndMessage(0.9, "");
        }
        return startState;
    }

    private StartState startOpt(StateChangeObj stateChange, WorkflowStatus flowType, boolean bindFlag) {
        StartState startState = new StartState();
        WorkFlowType queryStartType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        IBulidParam bulidParam = this.settingContextStrategy.bulidParam(queryStartType);
        Map<BusinessKey, String> buildBusinessKeyMap = bulidParam.buildBusinessKeyMap(stateChange, flowType, true);
        Future booleanFuture = null;
        try {
            booleanFuture = this.npApplication.asyncRunAsContext(NpContextHolder.getContext(), () -> this.starProcess.startProcess(stateChange.getFormSchemeId(), buildBusinessKeyMap, flowType));
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        while (!booleanFuture.isDone()) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            if (booleanFuture.isDone()) {
                startState.setStarted(true);
                break;
            }
            startState.setStarted(false);
        }
        return startState;
    }

    private StartState deleteOpt(StateChangeObj stateChange, WorkflowStatus flowType, boolean bindFlag) {
        StartState startState = new StartState();
        WorkFlowType queryStartType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        IBulidParam bulidParam = this.settingContextStrategy.bulidParam(queryStartType);
        Map<BusinessKey, String> buildBusinessKeyMap = bulidParam.buildBusinessKeyMap(stateChange, false);
        boolean delete = this.deleteProcess.deleteProcess(stateChange.getFormSchemeId(), buildBusinessKeyMap, flowType, bindFlag, stateChange.isSelectAll(), stateChange.isReportAll(), stateChange.getAdjust());
        if (delete) {
            startState.setStarted(true);
        } else {
            startState.setStarted(false);
        }
        return startState;
    }

    private StartState startOpt(StateChangeObj stateChange, WorkflowStatus flowType, boolean bindFlag, AsyncTaskMonitor asyncTaskMonitor) {
        StartState startState = new StartState();
        ArrayList<String> asynTaskIds = new ArrayList<String>();
        AsyncThreadExecutor asyncThreadExecutor = (AsyncThreadExecutor)BeanUtils.getBean(AsyncThreadExecutorImpl.class);
        WorkFlowType queryStartType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        String workflowKey = this.untilsMethod.queryFlowDefineKey(stateChange.getFormSchemeId());
        IBulidParam bulidParam = this.settingContextStrategy.bulidParam(queryStartType);
        Map<BusinessKey, String> buildBusinessKeyMap = bulidParam.buildBusinessKeyMap(stateChange, flowType, true);
        asyncTaskMonitor.progressAndMessage(0.15, "");
        if (buildBusinessKeyMap != null && buildBusinessKeyMap.size() > 0) {
            Map<String, List<BusinessKey>> classifyData = this.classifyData(buildBusinessKeyMap);
            asyncTaskMonitor.progressAndMessage(0.2, "");
            Set<String> keySet = classifyData.keySet();
            if (keySet != null && keySet.size() > 0) {
                ArrayList<String> unitkeys = new ArrayList<String>(keySet);
                List<List<String>> unitKeysByThreadNum = this.calculateThreadNum(unitkeys);
                asyncTaskMonitor.progressAndMessage(0.25, "");
                for (List<String> unitKeyList : unitKeysByThreadNum) {
                    HashMap<BusinessKey, String> actualData = new HashMap<BusinessKey, String>();
                    for (String unitKey : unitKeyList) {
                        List<BusinessKey> data = classifyData.get(unitKey);
                        for (BusinessKey business : data) {
                            actualData.put(business, workflowKey);
                        }
                    }
                    SubProcessBean subProcessBean = new SubProcessBean();
                    subProcessBean.setFormSchemeKey(stateChange.getFormSchemeId());
                    subProcessBean.setFlowType(flowType);
                    subProcessBean.setBusinessKeyMap(actualData);
                    subProcessBean.setBindFlag(bindFlag);
                    NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                    npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)subProcessBean));
                    npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new StartSubProcessAsyncTaskExecutor());
                    String asynTaskID = asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
                    asynTaskIds.add(asynTaskID);
                }
            }
        }
        this.judgeSyncComplete(asynTaskIds, asyncTaskMonitor, startState);
        return startState;
    }

    private StartState deleteOpt(StateChangeObj stateChange, WorkflowStatus flowType, boolean bindFlag, AsyncTaskMonitor asyncTaskMonitor) {
        StartState startState = new StartState();
        ArrayList<String> asynTaskIds = new ArrayList<String>();
        AsyncThreadExecutor asyncThreadExecutor = (AsyncThreadExecutor)BeanUtils.getBean(AsyncThreadExecutorImpl.class);
        WorkFlowType queryStartType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        String workflowKey = this.untilsMethod.queryFlowDefineKey(stateChange.getFormSchemeId());
        IBulidParam bulidParam = this.settingContextStrategy.bulidParam(queryStartType);
        Map<BusinessKey, String> buildBusinessKeyMap = bulidParam.buildBusinessKeyMap(stateChange, false);
        asyncTaskMonitor.progressAndMessage(0.15, "");
        if (buildBusinessKeyMap != null && buildBusinessKeyMap.size() > 0) {
            Map<String, List<BusinessKey>> classifyData = this.classifyData(buildBusinessKeyMap);
            asyncTaskMonitor.progressAndMessage(0.2, "");
            Set<String> keySet = classifyData.keySet();
            if (keySet != null && keySet.size() > 0) {
                ArrayList<String> unitkeys = new ArrayList<String>(keySet);
                List<List<String>> unitKeysByThreadNum = this.calculateThreadNum(unitkeys);
                asyncTaskMonitor.progressAndMessage(0.25, "");
                for (List<String> unitKeyList : unitKeysByThreadNum) {
                    HashMap<BusinessKey, String> actualData = new HashMap<BusinessKey, String>();
                    for (String unitKey : unitKeyList) {
                        List<BusinessKey> data = classifyData.get(unitKey);
                        for (BusinessKey business : data) {
                            actualData.put(business, workflowKey);
                        }
                    }
                    SubProcessBean subProcessBean = new SubProcessBean();
                    subProcessBean.setFormSchemeKey(stateChange.getFormSchemeId());
                    subProcessBean.setFlowType(flowType);
                    subProcessBean.setBusinessKeyMap(actualData);
                    subProcessBean.setBindFlag(bindFlag);
                    subProcessBean.setAdjust(stateChange.getAdjust());
                    NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                    npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)subProcessBean));
                    npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ClearSubProcessAsyncTaskExecutor());
                    String asynTaskID = asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
                    asynTaskIds.add(asynTaskID);
                }
            }
        }
        this.judgeSyncComplete(asynTaskIds, asyncTaskMonitor, startState);
        return startState;
    }

    private void judgeSyncComplete(List<String> asynTaskIds, AsyncTaskMonitor asyncTaskMonitor, StartState startState) {
        double step = 0.75;
        double scale = 0.75;
        block2: while (true) {
            try {
                while (true) {
                    Iterator<String> it = asynTaskIds.iterator();
                    scale /= (double)asynTaskIds.size();
                    while (it.hasNext()) {
                        step = step + scale > 1.0 ? step : step + scale;
                        String asynTaskId = it.next();
                        TaskState taskState = this.asyncTaskManager.queryTaskState(asynTaskId);
                        if (TaskState.FINISHED.equals((Object)taskState)) {
                            asyncTaskMonitor.progressAndMessage(1.0, "");
                            startState.setStarted(true);
                            asyncTaskMonitor.finish("\u6267\u884c\u5b8c\u6210", (Object)"\u6267\u884c\u5b8c\u6210");
                            it.remove();
                        } else if (TaskState.ERROR.equals((Object)taskState)) {
                            asyncTaskMonitor.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u6267\u884c\u51fa\u9519", null);
                            startState.setStarted(false);
                            logger.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u6267\u884c\u51fa\u9519");
                        } else if (TaskState.OVERTIME.equals((Object)taskState)) {
                            asyncTaskMonitor.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u7b49\u5f85\u8d85\u65f6", null);
                            startState.setStarted(false);
                            logger.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u7b49\u5f85\u8d85\u65f6");
                        }
                        if (!(step < 1.0)) continue;
                        asyncTaskMonitor.progressAndMessage(step, "");
                        startState.setStarted(false);
                    }
                    if (asynTaskIds.size() == 0) {
                        asyncTaskMonitor.progressAndMessage(1.0, "");
                        startState.setStarted(true);
                        asyncTaskMonitor.finish("\u6267\u884c\u5b8c\u6210", (Object)"\u6267\u884c\u5b8c\u6210");
                        break block2;
                    }
                    Thread.sleep(500L);
                }
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            break;
        }
    }

    public List<Map<BusinessKey, String>> calculateThreadNum(Map<BusinessKey, String> buildBusinessKeyMap) {
        List<Object> splitList = new ArrayList();
        int asyncNum = WorkflowOptionsResult.asyncNum();
        List<Map<BusinessKey, String>> splitByGroupSize = OperateWorkflow.splitByChunkSize(buildBusinessKeyMap, this.asyncUnitNum);
        splitList = splitByGroupSize.size() < asyncNum ? splitByGroupSize : OperateWorkflow.splitByGroupSize(buildBusinessKeyMap, asyncNum);
        return splitList;
    }

    private Map<String, List<BusinessKey>> classifyData(Map<BusinessKey, String> buildBusinessKeyMap) {
        HashMap<String, List<BusinessKey>> map = new HashMap<String, List<BusinessKey>>();
        if (buildBusinessKeyMap != null && buildBusinessKeyMap.size() > 0) {
            for (Map.Entry<BusinessKey, String> value : buildBusinessKeyMap.entrySet()) {
                BusinessKey businessKey = value.getKey();
                String unitKey = value.getValue();
                ArrayList<BusinessKey> list = (ArrayList<BusinessKey>)map.get(unitKey);
                if (list == null) {
                    list = new ArrayList<BusinessKey>();
                    list.add(businessKey);
                } else {
                    list.add(businessKey);
                }
                map.put(unitKey, list);
            }
        }
        return map;
    }

    public static <K, V> List<Map<K, V>> splitByChunkSize(Map<K, V> originalMap, int groupSize) {
        ArrayList<Map<K, V>> result = new ArrayList<Map<K, V>>();
        HashMap<K, V> currentGroup = new HashMap<K, V>();
        int currentSize = 0;
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            if (currentSize >= groupSize) {
                result.add(currentGroup);
                currentGroup = new HashMap();
                currentSize = 0;
            }
            currentGroup.put(entry.getKey(), entry.getValue());
            ++currentSize;
        }
        if (!currentGroup.isEmpty()) {
            result.add(currentGroup);
        }
        return result;
    }

    public static <K, V> List<Map<K, V>> splitByGroupSize(Map<K, V> originalMap, int groupCount) {
        ArrayList<Map<K, V>> result = new ArrayList<Map<K, V>>();
        int groupSize = (int)Math.ceil((double)originalMap.size() / (double)groupCount);
        HashMap<K, V> currentGroup = new HashMap<K, V>();
        int currentSize = 0;
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            if (currentSize >= groupSize) {
                result.add(currentGroup);
                currentGroup = new HashMap();
                currentSize = 0;
            }
            currentGroup.put(entry.getKey(), entry.getValue());
            ++currentSize;
        }
        if (!currentGroup.isEmpty()) {
            result.add(currentGroup);
        }
        return result;
    }

    public List<List<String>> calculateThreadNum(List<String> canUploadUnit) {
        List<Object> splitList = new ArrayList();
        int asyncNum = WorkflowOptionsResult.asyncNum();
        List<List<String>> splitListTemp = OperateWorkflow.fixedGrouping(canUploadUnit, this.asyncUnitNum);
        splitList = splitListTemp.size() < asyncNum ? splitListTemp : OperateWorkflow.averageAssign(canUploadUnit, asyncNum);
        return splitList;
    }

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int number = source.size() / n;
        int offset = 0;
        for (int i = 0; i < n; ++i) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                --remainder;
                ++offset;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0) {
            return null;
        }
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = source.size() / n;
        for (int i = 0; i < size; ++i) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }
}

