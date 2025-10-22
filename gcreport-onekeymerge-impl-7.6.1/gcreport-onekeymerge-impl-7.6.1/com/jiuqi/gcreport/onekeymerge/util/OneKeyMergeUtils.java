/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.QueryCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.definition.common.JDBCHelper
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.internal.service.FormLockServiceImpl
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskProcessEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.QueryCondition;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.definition.common.JDBCHelper;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.internal.service.FormLockServiceImpl;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OneKeyMergeUtils {
    private static IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(FormLockServiceImpl.class);
    private static IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);

    public static boolean efdcProcessON(TaskState state) {
        if (null == state) {
            return false;
        }
        return state.equals((Object)TaskState.PROCESSING) || state.equals((Object)TaskState.WAITING) || state.equals((Object)TaskState.CANCELING);
    }

    public static boolean efdcProcessEnd(TaskState state) {
        if (null == state) {
            return false;
        }
        return state.equals((Object)TaskState.CANCELED) || state.equals((Object)TaskState.FINISHED);
    }

    public static boolean efdcProcessError(TaskState state) {
        if (null == state) {
            return false;
        }
        return state.equals((Object)TaskState.ERROR) || state.equals((Object)TaskState.OVERTIME) || state.equals((Object)TaskState.NONE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean checkTableDataEmpty(String sql, int length) {
        boolean empty;
        ResultSet resultSet;
        PreparedStatement prep;
        Connection conn;
        JDBCHelper jdbcHelper;
        block5: {
            jdbcHelper = (JDBCHelper)SpringContextUtils.getBean(JDBCHelper.class);
            conn = null;
            prep = null;
            resultSet = null;
            empty = true;
            try {
                conn = jdbcHelper.getConnection();
                prep = conn.prepareStatement(sql);
                resultSet = prep.executeQuery();
                if (!resultSet.next()) break block5;
                for (int i = 1; i <= length; ++i) {
                    Object object = resultSet.getObject(i);
                    if (null == object) continue;
                    empty = false;
                }
            }
            catch (SQLException e) {
                try {
                    e.printStackTrace();
                }
                catch (Throwable throwable) {
                    jdbcHelper.close(conn, prep, resultSet);
                    throw throwable;
                }
                jdbcHelper.close(conn, prep, resultSet);
            }
        }
        jdbcHelper.close(conn, prep, resultSet);
        return empty;
    }

    public static NpContextUser getUser() {
        ContextUser user;
        NpContextUser retUser = new NpContextUser();
        if (NpContextHolder.getContext() != null && (user = NpContextHolder.getContext().getUser()) != null) {
            BeanUtils.copyProperties(user, retUser);
            return retUser;
        }
        retUser.setId(null);
        retUser.setName(null);
        return retUser;
    }

    public static void getResultFromDataSumAsyncTask(ReturnObject returnObject, AsyncTask asyncTask) {
        String detail = (String)asyncTask.getDetail();
        if (StringUtils.isNotEmpty((CharSequence)detail)) {
            Map jsonObject = (Map)JsonUtils.readValue((String)detail, (TypeReference)new TypeReference<Map<String, Object>>(){});
            String objectString = String.valueOf(jsonObject.get("message"));
            String msg = null;
            if (StringUtils.isNotEmpty((CharSequence)objectString)) {
                ArrayList messages = (ArrayList)JsonUtils.readValue((String)objectString, (TypeReference)new TypeReference<ArrayList<String>>(){});
                List collect = messages.stream().map(o -> o.replace("\n", "")).collect(Collectors.toList());
                msg = String.join((CharSequence)"\n", collect);
            }
            returnObject.setErrorMessage(msg);
        }
    }

    public static void getResultFromAsyncTaskDetailEFDC(ReturnObject returnObject, String detail) {
        if (StringUtils.isEmpty((CharSequence)detail)) {
            return;
        }
        Map jsonObject = (Map)JsonUtils.readValue((String)detail, (TypeReference)new TypeReference<Map<String, Object>>(){});
        Object message = jsonObject.get("message");
        try {
            if (message == null) {
                Map formMessage = (Map)jsonObject.get("formMessage");
                Set strings = formMessage.keySet();
                StringBuilder messageString = new StringBuilder();
                for (String string : strings) {
                    messageString.append(formMessage.get(string));
                }
                returnObject.setErrorMessage(messageString.toString());
            } else {
                returnObject.setErrorMessage(message.toString());
            }
        }
        catch (Exception e) {
            returnObject.setErrorMessage(detail);
        }
    }

    public static List<String> getFilterForms(String taskKey, String schemeKey, Map<String, DimensionValue> dimensionValueMap) {
        ArrayList<String> ret = new ArrayList<String>();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(schemeKey);
        jtableContext.setDimensionSet(dimensionValueMap);
        jtableContext.setTaskKey(taskKey);
        jtableContext.setVariableMap(new HashMap());
        IDataEntryParamService paramService = (IDataEntryParamService)SpringContextUtils.getBean(IDataEntryParamService.class);
        List runtimeFormList = paramService.getRuntimeFormList(jtableContext);
        runtimeFormList.forEach(formGroupData -> {
            List collect = formGroupData.getReports().stream().filter(formData -> !formData.getTitle().contains("\u5c01\u9762")).map(FormData::getKey).collect(Collectors.toList());
            ret.addAll(collect);
        });
        return ret;
    }

    public static List<String> getFilterLockedAndHiddenForm(String schemeKey, String orgCode, GcActionParamsVO paramsVO) {
        FormUploadStateTool formUploadStateTool = FormUploadStateTool.getInstance();
        Map<String, DimensionValue> dimensionValueMap = OneKeyMergeUtils.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgCode, paramsVO.getSelectAdjustCode());
        DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.getDimensionParamsVO(orgCode, paramsVO);
        List<String> filterForms = OneKeyMergeUtils.getFilterForms(paramsVO.getTaskId(), schemeKey, dimensionValueMap);
        ArrayList writeAccessDescs = new ArrayList(formUploadStateTool.writeable(dimensionParamsVO, filterForms));
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < filterForms.size(); ++i) {
            ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(i);
            if (!accessDesc.getAble().booleanValue()) continue;
            list.add(filterForms.get(i));
        }
        return list;
    }

    private static DimensionParamsVO getDimensionParamsVO(String orgCode, GcActionParamsVO paramsVO) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setCurrency(paramsVO.getCurrency());
        dimensionParamsVO.setCurrencyId(paramsVO.getCurrency());
        dimensionParamsVO.setOrgId(orgCode);
        dimensionParamsVO.setOrgType(paramsVO.getOrgType());
        dimensionParamsVO.setOrgTypeId(paramsVO.getOrgType());
        dimensionParamsVO.setPeriodStr(paramsVO.getPeriodStr());
        dimensionParamsVO.setSchemeId(paramsVO.getSchemeId());
        dimensionParamsVO.setTaskId(paramsVO.getTaskId());
        dimensionParamsVO.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
        return dimensionParamsVO;
    }

    public static List<String> getLockedForm(String schemeKey, Map<String, DimensionValue> dimensionValueMap) {
        FormLockParam param = new FormLockParam();
        JtableContext context = new JtableContext();
        param.setContext(context);
        context.setFormSchemeKey(schemeKey);
        context.setDimensionSet(dimensionValueMap);
        return new ArrayList<String>(formLockService.getLockedFormKeysMap(param, false).keySet());
    }

    public static List<String> getLockedForm(String schemeKey, String orgCode, GcActionParamsVO paramsVO) {
        Map<String, DimensionValue> dimensionValueMap = OneKeyMergeUtils.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgCode, paramsVO.getSelectAdjustCode());
        DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.getDimensionParamsVO(orgCode, paramsVO);
        List<String> filterForms = OneKeyMergeUtils.getFilterForms(paramsVO.getTaskId(), schemeKey, dimensionValueMap);
        ArrayList writeAccessDescs = new ArrayList(FormUploadStateTool.getInstance().writeable(dimensionParamsVO, filterForms));
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < filterForms.size(); ++i) {
            ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(i);
            if (accessDesc.getAble().booleanValue()) continue;
            list.add(filterForms.get(i));
        }
        return list;
    }

    public static void buildSetpMessage(TaskLog taskLog, ReturnObject returnObject) {
        String msg = returnObject.getErrorMessage();
        if (msg == null) {
            msg = "";
        }
        if (returnObject.isSuccess()) {
            if (!StringUtils.isEmpty((CharSequence)msg)) {
                taskLog.writeWarnLog(msg, null);
            }
        } else {
            taskLog.writeErrorLog(msg, null);
            throw new RuntimeException(msg);
        }
    }

    private static List<String> getAllFormInScheme(String schemeKey) {
        return iRunTimeViewController.queryAllFormDefinesByFormScheme(schemeKey).stream().filter(formDefine -> !formDefine.getTitle().contains("\u5c01\u9762\u4ee3\u7801")).map(formDefine -> formDefine.getKey()).collect(Collectors.toList());
    }

    public static DimensionParamsVO convert2DimParamVO(GcActionParamsVO paramsVO) {
        DimensionParamsVO vo = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, vo);
        vo.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
        return vo;
    }

    public static boolean checkBalanceFormLockState(String diffUnitId, GcActionParamsVO paramsVO, TaskLog taskLog) {
        Set formDefines = NrTool.getFormDefineByTableName((String)paramsVO.getSchemeId(), (String)"GC_FLOATBALANCE");
        ArrayList formDefineList = new ArrayList(formDefines);
        List formKeys = formDefineList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.getDimensionParamsVO(diffUnitId, paramsVO);
        List writeAccessDescs = FormUploadStateTool.getInstance().writeable(dimensionParamsVO, formKeys);
        StringBuilder lockedFormTitles = new StringBuilder();
        for (int i = 0; i < formDefineList.size(); ++i) {
            ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(i);
            if (!accessDesc.getAble().booleanValue()) continue;
            lockedFormTitles.append(((FormDefine)formDefineList.get(i)).getTitle()).append(",");
        }
        if (StringUtils.isNotEmpty((CharSequence)lockedFormTitles)) {
            taskLog.writeWarnLog("\u5dee\u989d\u5355\u4f4d\u4ee5\u4e0b\u8868\u88ab\u9501\u5b9a\uff0c\u81ea\u52a8\u8df3\u8fc7\uff1a" + lockedFormTitles.toString(), Float.valueOf(taskLog.getProcessPercent()), Integer.valueOf(100));
            return false;
        }
        return true;
    }

    public static void checkStopOrNot(GcActionParamsVO paramsVO) {
        GcOnekeyMergeService onekeyMergeService = (GcOnekeyMergeService)SpringContextUtils.getBean(GcOnekeyMergeService.class);
        if (onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId())) {
            throw new BusinessRuntimeException("\u624b\u52a8\u505c\u6b62");
        }
    }

    public static QueryCondition buildQueryCondition(GcActionParamsVO paramsVO, String hbUnitId) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setTaskID(paramsVO.getTaskId());
        queryCondition.setSchemeID(paramsVO.getSchemeId());
        queryCondition.setCurrency(paramsVO.getCurrency());
        queryCondition.setAcctYear(paramsVO.getAcctYear());
        queryCondition.setAcctPeriod(paramsVO.getAcctPeriod());
        queryCondition.setAdjustment(paramsVO.getAdjTypeId());
        queryCondition.setPeriodType(paramsVO.getPeriodType());
        queryCondition.setOrg_type(paramsVO.getOrgType());
        queryCondition.setPeriodStr(paramsVO.getPeriodStr());
        queryCondition.setOrgid(hbUnitId);
        queryCondition.setIsAll("false");
        queryCondition.setIsFilterZero(Boolean.valueOf(false));
        queryCondition.setSumSubjectCodeByLevel(Boolean.FALSE);
        queryCondition.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
        return queryCondition;
    }

    public static String generateSN(String code, String taskLogId) {
        return code + "_" + taskLogId;
    }

    private static Map<String, DimensionValue> buildDimensionMap(String taskId, String currencyId, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(orgType, periodStr, orgId);
        String orgTypeId = currentUnit.getOrgTypeId();
        Assert.isNotEmpty((String)orgTypeId, (String)(currentUnit.getTitle() + "\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a"), (Object[])new Object[0]);
        return DimensionUtils.buildDimensionMap((String)taskId, (String)currencyId, (String)periodStr, (String)orgType, (String)orgId, (String)selectAdjustCode);
    }

    public static Set<String> calcAllChildSubjectCodeSet(ReWriteSubject subject) {
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        if (subject.isFromPrimaryWpSetting() || !CollectionUtils.isEmpty((Collection)subject.getSubjectCodeSet())) {
            return subject.getSubjectCodeSet();
        }
        HashSet<String> allChildrenCodesContanisSelf = new HashSet<String>(subject.getOriginSubjectCodeSet());
        Set allChildrenSubjectCodes = consolidatedSubjectService.listAllChildrenCodes((String)subject.getOriginSubjectCodeSet().iterator().next(), subject.getSystemId());
        allChildrenCodesContanisSelf.addAll(CollectionUtils.isEmpty((Collection)allChildrenSubjectCodes) ? new HashSet() : allChildrenSubjectCodes);
        return allChildrenCodesContanisSelf;
    }

    public static MergeTaskEO buildMergeTask(GcActionParamsVO param) {
        MergeTaskEO mergeTaskEO = new MergeTaskEO();
        mergeTaskEO.setCreateTime(new Date());
        mergeTaskEO.setSchemeId(param.getSchemeId());
        mergeTaskEO.setNrTaskId(param.getTaskId());
        mergeTaskEO.setDataTime(param.getPeriodStr());
        mergeTaskEO.setId(UUIDUtils.newUUIDStr());
        mergeTaskEO.setTaskState(TaskStateEnum.WAITTING.getCode());
        Map dimensionValueMap = DimensionUtils.buildDimensionMap((String)param.getTaskId(), (String)param.getCurrency(), (String)param.getPeriodStr(), (String)param.getOrgType(), (String)param.getOrgId(), (String)param.getSelectAdjustCode());
        mergeTaskEO.setDims(JsonUtils.writeValueAsString((Object)dimensionValueMap));
        return mergeTaskEO;
    }

    public static MergeTaskProcessEO buildMergeTaskProcess(List<GcOrgCacheVO> orgs, GcActionParamsVO param) {
        List orgIds = orgs.stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        String orgIdStr = String.join((CharSequence)",", orgIds);
        MergeTaskProcessEO mergeTaskProcessEO = new MergeTaskProcessEO();
        mergeTaskProcessEO.setCreateTime(new Date());
        mergeTaskProcessEO.setNrTaskId(param.getTaskId());
        Map dimensionValueMap = DimensionUtils.buildDimensionMap((String)param.getTaskId(), (String)param.getCurrency(), (String)param.getPeriodStr(), (String)param.getOrgType(), (String)param.getOrgId(), (String)param.getSelectAdjustCode());
        mergeTaskProcessEO.setDims(JsonUtils.writeValueAsString((Object)dimensionValueMap));
        mergeTaskProcessEO.setTaskCodes(String.join((CharSequence)",", param.getTaskCodes()));
        mergeTaskProcessEO.setTaskState(TaskStateEnum.EXECUTING.getCode());
        mergeTaskProcessEO.setId(param.getTaskLogId());
        mergeTaskProcessEO.setUserName(NpContextHolder.getContext().getUserName());
        mergeTaskProcessEO.setOrgId(orgIdStr);
        mergeTaskProcessEO.setDataTime(param.getPeriodStr());
        mergeTaskProcessEO.setProcess(0.0);
        mergeTaskProcessEO.setMergeType(param.getMergeType().getCode());
        mergeTaskProcessEO.setConfigSchemeName(param.getConfigSchemeName());
        return mergeTaskProcessEO;
    }

    public static String buildTaskCodeLog(List<String> taskCodes) {
        TaskTypeEnum[] values;
        ArrayList<String> taskTitles = new ArrayList<String>();
        HashSet<String> taskCodesSet = new HashSet<String>(taskCodes);
        for (TaskTypeEnum taskTypeEnum : values = TaskTypeEnum.values()) {
            if (!taskCodesSet.contains(taskTypeEnum.getCode())) continue;
            taskTitles.add(taskTypeEnum.getStateInfo());
        }
        if (!CollectionUtils.isEmpty(taskTitles)) {
            return String.join((CharSequence)"/", taskTitles);
        }
        return "";
    }
}

