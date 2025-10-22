/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.type.CollectionType
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.dataentry.internal.service.BatchParallelMonitor
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.designer.common.NrDesignLogHelper
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.annotation.Resource
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.dataentry.internal.service.BatchParallelMonitor;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.FormFieldWrapper;
import com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckGroupInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckGroupItemInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckResultDefine;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckResultSaveInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumTableItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumTableResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.QueryEnumResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.EnumDataChecker;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.service.IEnumDataCheckService;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class EnumDataCheckServiceImpl
implements IEnumDataCheckService {
    private static final Logger logger = LoggerFactory.getLogger(EnumDataCheckServiceImpl.class);
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Resource
    private JdbcTemplate jdbcTpl;
    @Resource
    IRunTimeViewController viewCtrl;
    @Autowired
    TmpTableUtils tmpTableUtils;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    DataQueryHelper dataQueryHelper;
    private static final int PAGER_COUNT = 30;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EnumDataCheckResultInfo enumDataCheck(EnumDataCheckInfo enumDataCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        FormSchemeDefine fmScheme;
        EnumDataCheckResultInfo resultInfo = new EnumDataCheckResultInfo();
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        JtableContext jtableContext = enumDataCheckInfo.getContext();
        TaskDefine task = null;
        try {
            fmScheme = this.runTimeAuthViewController.getFormScheme(jtableContext.getFormSchemeKey());
            task = this.runTimeAuthViewController.queryTaskDefine(fmScheme.getTaskKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.08, "getData");
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeAuthViewController.getRuntimeView(), this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.1, "authFilter");
        }
        if (StringUtil.isEmpty((String)enumDataCheckInfo.getFormKeys())) {
            enumDataCheckInfo.setFormKeys(String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey())));
        }
        String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey()));
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(jtableContext, FormKeys, Consts.FormAccessLevel.FORM_READ);
        List entityList = this.jtableParamService.getEntityList(fmScheme.getMasterEntitiesKey());
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        for (EntityViewData entityInfo : entityList) {
            if (!entityInfo.isMasterEntity()) continue;
            targetEntityInfo = entityInfo;
            break;
        }
        ArrayList<String> resultList = new ArrayList<String>();
        resultInfo.setEnumDataCheckResult(resultList);
        List forms = batchDimensionValueFormInfo.getForms();
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.15, "running");
        }
        DimensionValue periodDimensionValue = (DimensionValue)jtableContext.getDimensionSet().get("DATATIME");
        ArrayList<String> periodList = new ArrayList<String>();
        if (periodDimensionValue == null || StringUtils.isEmpty((String)periodDimensionValue.getValue())) {
            periodList.add("");
        } else {
            for (String periodStr : periodDimensionValue.getValue().split(";")) {
                periodList.add(periodStr.trim());
            }
        }
        HashSet<String> untityDic = new HashSet<String>();
        HashSet<String> enumTableDic = new HashSet<String>();
        ArrayList<ITempTable> tempTableList = new ArrayList<ITempTable>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            block15: for (int periodIndex = 0; periodIndex < periodList.size(); ++periodIndex) {
                String periodStr = (String)periodList.get(periodIndex);
                PeriodWrapper periodWrapper = null;
                if (!StringUtils.isEmpty((String)periodStr)) {
                    periodWrapper = new PeriodWrapper(periodStr);
                }
                EnumDataChecker enumDataChecker = EnumDataChecker.CreateChecker(periodWrapper, executorContext, enumDataCheckInfo, targetEntityInfo.getDimensionName());
                List<ITempTable> tmpTables = enumDataChecker.prepareData();
                tempTableList.addAll(tmpTables);
                for (String unitKey : enumDataChecker.getSelUnitKeys()) {
                    if (untityDic.contains(unitKey)) continue;
                    untityDic.add(unitKey);
                }
                double formStartProgress = (double)periodIndex / (double)periodList.size();
                double formEndProgress = (double)(periodIndex + 1) / (double)periodList.size();
                BatchParallelMonitor monitor = new BatchParallelMonitor(asyncTaskMonitor);
                monitor.setStartProgress(formStartProgress, formEndProgress);
                enumDataChecker.setMonitor((IMonitor)monitor);
                double currentProcess = 0.2;
                int i = 0;
                for (String formKey : forms) {
                    monitor.onProgress(currentProcess + 0.8 * ((double)i++ * 1.0 / (double)forms.size()));
                    List<EnumDataCheckResultItem> errInForms = enumDataChecker.executeCheck(task, targetEntityInfo.getKey(), periodWrapper, formKey, enumTableDic);
                    for (EnumDataCheckResultItem item : errInForms) {
                        resultList.add(objectMapper.writeValueAsString((Object)item));
                    }
                    if (enumDataChecker.getErrorCount() < 1000) continue;
                    resultInfo.setHasTooManyError(true);
                    continue block15;
                }
            }
            resultInfo.setSelEntityCount(untityDic.size());
            resultInfo.setSelEnumDicCount(this.getEnumCount(enumDataCheckInfo));
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(1.0, "finish");
            }
            if (asyncTaskMonitor != null) {
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                asyncTaskMonitor.finish("task_success_info", (Object)resultInfo);
            }
            NrDesignLogHelper.log((String)"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", (String)"\u6267\u884c\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", (int)NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (Exception e) {
            NrDesignLogHelper.log((String)"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", (String)e.getMessage(), (int)NrDesignLogHelper.LOGLEVEL_INFO);
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
            }
        }
        finally {
            try {
                if (tempTableList.size() > 0) {
                    for (ITempTable tempTable : tempTableList) {
                        this.tmpTableUtils.dropTempTable(tempTable);
                    }
                }
            }
            catch (Exception e) {
                NrDesignLogHelper.log((String)"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", (String)e.getMessage(), (int)NrDesignLogHelper.LOGLEVEL_INFO);
            }
        }
        QueryEnumResultInfo queryEnumResultInfo = new QueryEnumResultInfo();
        queryEnumResultInfo.setEnumDataCheckResultInfo(resultInfo);
        if (asyncTaskMonitor != null) {
            queryEnumResultInfo.setAsyncTaskId(asyncTaskMonitor.getTaskId());
        }
        queryEnumResultInfo.setContext(enumDataCheckInfo.getContext());
        EnumCheckResultSaveInfo saveInfo = this.saveEnumCheckResults(queryEnumResultInfo);
        resultInfo.setSaveInfo(saveInfo);
        return resultInfo;
    }

    private int getEnumCount(EnumDataCheckInfo enumDataCheckInfo) {
        ArrayList<String> enums = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)enumDataCheckInfo.getEnumNames())) {
            for (String enumName : enumDataCheckInfo.getEnumNames().split(";")) {
                enums.add(enumName);
            }
        } else {
            EnumTableResultInfo enumTableResultInfo = this.queryAllEnumTables(enumDataCheckInfo, null);
            return enumTableResultInfo.getEnumDataCheckResult().size();
        }
        return enums.size();
    }

    @Override
    public EnumTableResultInfo queryAllEnumTables(EnumDataCheckInfo enumDataCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        EnumTableResultInfo result = new EnumTableResultInfo();
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        JtableContext jtableContext = enumDataCheckInfo.getContext();
        FormSchemeDefine formScheme = null;
        TaskDefine task = null;
        try {
            formScheme = this.runTimeAuthViewController.getFormScheme(jtableContext.getFormSchemeKey());
            task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{enumDataCheckInfo.getContext().getFormSchemeKey()});
        }
        String dataSchemeId = task.getDataScheme();
        EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formScheme.getKey());
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.1, "getData");
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeAuthViewController.getRuntimeView(), this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        List forms = this.viewCtrl.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey());
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.15, "authFilter");
        }
        if (StringUtil.isEmpty((String)enumDataCheckInfo.getFormKeys())) {
            enumDataCheckInfo.setFormKeys(String.join((CharSequence)";", forms));
        }
        ArrayList<EnumTableItem> resultList = new ArrayList<EnumTableItem>();
        result.setEnumDataCheckResult(resultList);
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.3, "travers");
        }
        try {
            BatchParallelMonitor monitor = new BatchParallelMonitor(asyncTaskMonitor);
            monitor.setStartProgress(0.0, 1.0);
            double perProcess = 0.8 / (double)forms.size();
            double currentProcess = 0.15;
            HashSet<String> enumDic = new HashSet<String>();
            for (String formKey : forms) {
                if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                    return result;
                }
                monitor.onProgress(currentProcess);
                monitor.setStep(perProcess);
                for (FormFieldWrapper ffw : this.entityQueryHelper.getAllEnumOfForm(dataSchemeId, entityView.getEntityId(), formKey)) {
                    if (enumDic.contains(ffw.getRefTableName())) continue;
                    EnumTableItem enumTableItem = new EnumTableItem();
                    enumTableItem.setEnumName(ffw.getRefEntity().getCode());
                    enumTableItem.setEnumTitle(ffw.getRefEntity().getTitle());
                    enumDic.add(ffw.getRefTableName());
                    resultList.add(enumTableItem);
                }
            }
            if (asyncTaskMonitor != null) {
                String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
                asyncTaskMonitor.finish("task_success_info", (Object)objectToJson);
            }
        }
        catch (Exception e) {
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
            }
            return result;
        }
        return result;
    }

    @Override
    public EnumDataCheckResultInfo enumDataCheckResult(String asyncTaskID) {
        Object object = this.asyncTaskManager.queryDetail(asyncTaskID);
        if (null != object) {
            return (EnumDataCheckResultInfo)object;
        }
        return null;
    }

    @Override
    public EnumCheckResultSaveInfo saveEnumCheckResults(QueryEnumResultInfo enumDataCheckInfo) {
        try {
            return this.insertEnumCheckResults(enumDataCheckInfo);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private EnumCheckResultSaveInfo insertEnumCheckResults(QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        Object[] arg;
        int r;
        Object[] arg2;
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(queryEnumResultInfo.getContext().getFormSchemeKey());
        String tableName = "SYS_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumResultInfo.getContext().getTaskKey(), tableName);
        this.deleteResults(libraryTableName);
        HashMap resultsByUnit = new HashMap();
        HashMap resultsByEnum = new HashMap();
        HashMap resultsByForm = new HashMap();
        HashSet<String> errorUnitKeys = new HashSet<String>();
        HashSet<String> errorEnumCodes = new HashSet<String>();
        HashSet<String> errorFormKeys = new HashSet<String>();
        EnumCheckResultSaveInfo enumCheckResultSaveInfo = new EnumCheckResultSaveInfo();
        EnumDataCheckResultItem resultItem = new EnumDataCheckResultItem();
        ObjectMapper objectMapper = new ObjectMapper();
        for (String item : queryEnumResultInfo.getEnumDataCheckResultInfo().getEnumDataCheckResult()) {
            resultItem = (EnumDataCheckResultItem)objectMapper.readValue(item, EnumDataCheckResultItem.class);
            if (!errorUnitKeys.contains(resultItem.getMasterEntityKey())) {
                ArrayList<EnumDataCheckResultItem> unitGroupList = new ArrayList<EnumDataCheckResultItem>();
                unitGroupList.add(resultItem);
                errorUnitKeys.add(resultItem.getMasterEntityKey());
                resultsByUnit.put(resultItem.getMasterEntityKey(), unitGroupList);
            } else if (resultsByUnit.containsKey(resultItem.getMasterEntityKey())) {
                ArrayList<EnumDataCheckResultItem> tmpLstForUnit = (ArrayList<EnumDataCheckResultItem>)resultsByUnit.get(resultItem.getMasterEntityKey());
                if (tmpLstForUnit != null) {
                    tmpLstForUnit.add(resultItem);
                } else {
                    tmpLstForUnit = new ArrayList<EnumDataCheckResultItem>();
                    tmpLstForUnit.add(resultItem);
                }
                resultsByUnit.put(resultItem.getMasterEntityKey(), tmpLstForUnit);
            }
            if (!errorEnumCodes.contains(resultItem.getEnumCode())) {
                ArrayList<EnumDataCheckResultItem> enumGroupList = new ArrayList<EnumDataCheckResultItem>();
                enumGroupList.add(resultItem);
                errorEnumCodes.add(resultItem.getEnumCode());
                resultsByEnum.put(resultItem.getEnumCode(), enumGroupList);
            } else if (resultsByEnum.containsKey(resultItem.getEnumCode())) {
                ArrayList<EnumDataCheckResultItem> tmpLstForEnum = (ArrayList<EnumDataCheckResultItem>)resultsByEnum.get(resultItem.getEnumCode());
                if (tmpLstForEnum != null) {
                    tmpLstForEnum.add(resultItem);
                } else {
                    tmpLstForEnum = new ArrayList<EnumDataCheckResultItem>();
                    tmpLstForEnum.add(resultItem);
                }
                resultsByEnum.put(resultItem.getEnumCode(), tmpLstForEnum);
            }
            if (!errorFormKeys.contains(resultItem.getFormKey())) {
                ArrayList<EnumDataCheckResultItem> formGroupList = new ArrayList<EnumDataCheckResultItem>();
                formGroupList.add(resultItem);
                errorFormKeys.add(resultItem.getFormKey());
                resultsByForm.put(resultItem.getFormKey(), formGroupList);
                continue;
            }
            if (!resultsByForm.containsKey(resultItem.getFormKey())) continue;
            ArrayList<EnumDataCheckResultItem> tmpLstForForm = (ArrayList<EnumDataCheckResultItem>)resultsByForm.get(resultItem.getFormKey());
            if (tmpLstForForm != null) {
                tmpLstForForm.add(resultItem);
            } else {
                tmpLstForForm = new ArrayList<EnumDataCheckResultItem>();
                tmpLstForForm.add(resultItem);
            }
            resultsByForm.put(resultItem.getFormKey(), tmpLstForForm);
        }
        enumCheckResultSaveInfo.setErrorEnumCount(errorEnumCodes.size());
        enumCheckResultSaveInfo.setErrorUnitCount(errorUnitKeys.size());
        enumCheckResultSaveInfo.setErrorFormCount(errorFormKeys.size());
        Date newDate = new Date();
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s,%s, %s) VALUES (?, ?, ?, ?,?, ?, ?, ?)", libraryTableName, "MJZD_KEY", "MJZD_ASYNCTASKID", "MJZD_GROUPKEY", "MJZD_GROUPDETAIL", "MJZD_OPERATOR", "MJZD_VIEWTYPE", "MJZD_UPTIME", "MJZD_UNITORDER");
        ArrayList<Object> args = new ArrayList<Object>(errorEnumCodes.size() + errorUnitKeys.size() + errorFormKeys.size());
        for (Map.Entry entry : resultsByUnit.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), queryEnumResultInfo.getAsyncTaskId(), entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 0, newDate, ((EnumDataCheckResultItem)((List)entry.getValue()).get(0)).getEntityOrder()};
            args.add(arg2);
        }
        for (Map.Entry entry : resultsByEnum.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), queryEnumResultInfo.getAsyncTaskId(), entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 1, newDate};
            args.add(arg2);
        }
        for (Map.Entry entry : resultsByForm.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), queryEnumResultInfo.getAsyncTaskId(), entry.getKey(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 2, newDate};
            args.add(arg2);
        }
        if (args != null && args.size() > 0) {
            try {
                EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formSchemeDefine.getKey());
                IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityView, null, queryEnumResultInfo.getContext().getFormSchemeKey(), false);
                final HashMap<String, Integer> untityOrderDic = this.entityQueryHelper.entityOrderByKey(entityTable);
                final HashMap<String, Integer> formOrderDic = this.entityQueryHelper.formOrderByKey(formSchemeDefine.getKey());
                args.sort(new Comparator<Object>(){

                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null) {
                            return -1;
                        }
                        if (o2 == null) {
                            return 1;
                        }
                        return EnumDataCheckServiceImpl.this.compareResultItem(untityOrderDic, formOrderDic, (Object[])o1, (Object[])o2);
                    }
                });
            }
            catch (Exception entityView) {
                // empty catch block
            }
        }
        ArrayList<Object[]> argsOrderByUnit = new ArrayList<Object[]>(errorEnumCodes.size() + errorUnitKeys.size() + errorFormKeys.size());
        for (r = 0; r < resultsByUnit.size(); ++r) {
            int length = ((Object[])args.get(r)).length;
            arg = new Object[length];
            System.arraycopy(args.get(r), 0, arg, 0, length);
            argsOrderByUnit.add(arg);
        }
        for (r = resultsByUnit.size(); r < args.size(); ++r) {
            int length = ((Object[])args.get(r)).length;
            arg = new Object[length + 1];
            System.arraycopy(args.get(r), 0, arg, 0, length);
            arg[length] = r;
            argsOrderByUnit.add(arg);
        }
        final ArrayList<EnumCheckResultDefine> resultDefines = new ArrayList<EnumCheckResultDefine>();
        for (Object[] obj : argsOrderByUnit) {
            EnumCheckResultDefine res = new EnumCheckResultDefine();
            res.setKey(obj[0].toString());
            res.setAsyncTaskId(obj[1].toString());
            res.setGroupKey(obj[2].toString());
            res.setGroupDetail(obj[3].toString());
            res.setOperator(obj[4].toString());
            res.setViewType(Integer.parseInt(obj[5].toString()));
            res.setUnitOrder(Integer.parseInt(obj[7].toString()));
            resultDefines.add(res);
        }
        final java.sql.Date newDate2 = new java.sql.Date(newDate.getTime());
        int[] n = this.jdbcTpl.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                EnumCheckResultDefine res = (EnumCheckResultDefine)resultDefines.get(i);
                ps.setString(1, res.getKey());
                ps.setString(2, res.getAsyncTaskId());
                ps.setString(3, res.getGroupKey());
                try {
                    ps.setString(4, EnumDataCheckServiceImpl.compressToJsonString(res.getGroupDetail()));
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ps.setString(5, res.getOperator());
                ps.setInt(6, res.getViewType());
                ps.setDate(7, newDate2);
                ps.setInt(8, res.getUnitOrder());
            }

            public int getBatchSize() {
                return resultDefines.size();
            }
        });
        if (n.length > 0) {
            enumCheckResultSaveInfo.setSaveStatus(true);
        } else {
            enumCheckResultSaveInfo.setSaveStatus(false);
        }
        return enumCheckResultSaveInfo;
    }

    public static String compressToJsonString(String json) throws IOException {
        if (json == null || json.isEmpty()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);){
            gzipOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
        }
        byte[] compressedBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(compressedBytes);
    }

    public static String decompressFromJsonString(String compressedJsonString) throws IOException {
        if (compressedJsonString == null || compressedJsonString.isEmpty()) {
            return null;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(compressedJsonString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);){
            int len;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            String string = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
            return string;
        }
    }

    public int compareResultItem(HashMap<String, Integer> untityOrderDic, HashMap<String, Integer> formOrderDic, Object[] o1, Object[] o2) {
        int result = Integer.compare((Integer)o1[5], (Integer)o2[5]);
        if (result != 0) {
            return result;
        }
        int viewType = (Integer)o1[5];
        if (viewType == 0) {
            int order1 = untityOrderDic.get(o1[2].toString());
            int order2 = untityOrderDic.get(o2[2].toString());
            return Integer.compare(order1, order2);
        }
        if (viewType == 2) {
            int order1 = formOrderDic.get(o1[2].toString());
            int order2 = formOrderDic.get(o2[2].toString());
            return Integer.compare(order1, order2);
        }
        return 0;
    }

    private void deleteResults(String tableName) throws SQLException {
        GregorianCalendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        ((Calendar)c).add(13, -259200);
        Date dateBefore = c.getTime();
        String sql = "delete  from " + tableName + " where mjzd_uptime <=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.jdbcTpl.getDataSource());
        jdbcTemplate.update(sql, pss -> pss.setDate(1, new java.sql.Date(dateBefore.getTime())));
    }

    @Override
    public String queryEnumCheckResults(QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        String results = this.queryGroupItemData(queryEnumResultInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
        ArrayList<EnumDataCheckResultItem> resultInfo = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(EnumDataCheckServiceImpl.decompressFromJsonString(results), (JavaType)javaType);
        List<String> orgCodes = queryEnumResultInfo.getOrgCodes();
        if (!orgCodes.isEmpty()) {
            ArrayList<EnumDataCheckResultItem> resultInfoFilter = new ArrayList<EnumDataCheckResultItem>();
            block0: for (EnumDataCheckResultItem enumDataCheckResultItem : resultInfo) {
                for (String orgCode : orgCodes) {
                    if (!enumDataCheckResultItem.getMasterEntityKey().equals(orgCode)) continue;
                    resultInfoFilter.add(enumDataCheckResultItem);
                    continue block0;
                }
            }
            resultInfo = resultInfoFilter;
        }
        if (resultInfo.size() <= 0) {
            return null;
        }
        int pageSize = queryEnumResultInfo.getItemPageSize();
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int startIndex = queryEnumResultInfo.getItemOffset() * pageSize;
        int endIndex = queryEnumResultInfo.getItemOffset() * pageSize + pageSize >= resultInfo.size() ? resultInfo.size() : queryEnumResultInfo.getItemOffset() * pageSize + pageSize;
        List showResults = resultInfo.subList(startIndex, endIndex);
        return objectMapper.writeValueAsString(showResults);
    }

    private String queryGroupItemData(QueryEnumResultInfo queryEnumResultInfo) {
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(queryEnumResultInfo.getContext().getFormSchemeKey());
        String tableName = "SYS_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumResultInfo.getContext().getTaskKey(), tableName);
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append("MJZD_GROUPDETAIL").append(" from ").append(libraryTableName).append(" \n ");
        sql.append(" where ").append("MJZD_ASYNCTASKID").append(" = ? ").append(" and ").append("MJZD_GROUPKEY").append(" = ?");
        return (String)this.jdbcTpl.query(sql.toString(), (ResultSetExtractor)new ResultSetExtractor<Object>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Reader is;
                    try {
                        is = rs.getCharacterStream(1);
                    }
                    catch (SQLException e) {
                        Clob clobData = rs.getClob(1);
                        is = clobData.getCharacterStream();
                    }
                    BufferedReader br = new BufferedReader(is);
                    StringBuffer sb = new StringBuffer();
                    try {
                        String s = br.readLine();
                        while (s != null) {
                            sb.append(s);
                            s = br.readLine();
                        }
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    finally {
                        try {
                            br.close();
                            is.close();
                        }
                        catch (IOException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    return sb.toString();
                }
                return null;
            }
        }, new Object[]{queryEnumResultInfo.getAsyncTaskId(), queryEnumResultInfo.getGroupKey()});
    }

    @Override
    public EnumCheckGroupInfo queryEnumCheckResultGroup(QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        EnumCheckGroupInfo enumCheckGroupInfo = new EnumCheckGroupInfo();
        List<String> resultGroups = this.queryResultsGroup(queryEnumResultInfo);
        if (resultGroups.size() <= 0) {
            return null;
        }
        List<String> orgCodes = queryEnumResultInfo.getOrgCodes();
        if (orgCodes.isEmpty()) {
            int startIndex = queryEnumResultInfo.getGroupOffset() * 30;
            int endIndex = queryEnumResultInfo.getGroupOffset() * 30 + 30 >= resultGroups.size() ? resultGroups.size() : queryEnumResultInfo.getGroupOffset() * 30 + 30;
            resultGroups = resultGroups.subList(startIndex, endIndex);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<EnumCheckGroupItemInfo> enumCheckGroupInfoList = new ArrayList<EnumCheckGroupItemInfo>();
        for (String item : resultGroups) {
            EnumCheckGroupItemInfo enumCheckGroupItemInfo = new EnumCheckGroupItemInfo();
            CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
            ArrayList<EnumDataCheckResultItem> showGroupItem = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(EnumDataCheckServiceImpl.decompressFromJsonString(item), (JavaType)javaType);
            if (!orgCodes.isEmpty()) {
                ArrayList<EnumDataCheckResultItem> showGroupItemFilter = new ArrayList<EnumDataCheckResultItem>();
                block1: for (EnumDataCheckResultItem enumDataCheckResultItem : showGroupItem) {
                    for (String orgCode : orgCodes) {
                        if (!enumDataCheckResultItem.getMasterEntityKey().equals(orgCode)) continue;
                        showGroupItemFilter.add(enumDataCheckResultItem);
                        continue block1;
                    }
                }
                if (showGroupItemFilter.isEmpty()) continue;
                showGroupItem = showGroupItemFilter;
            }
            enumCheckGroupItemInfo.setGroupCount(showGroupItem.size());
            if (queryEnumResultInfo.getViewType().equals("0")) {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getEntityTitle());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getMasterEntityKey());
            } else if (queryEnumResultInfo.getViewType().equals("1")) {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getEnumTitle());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getEnumCode());
            } else {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getBbfz());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getFormKey());
            }
            enumCheckGroupInfoList.add(enumCheckGroupItemInfo);
        }
        if (!orgCodes.isEmpty()) {
            int startIndex = queryEnumResultInfo.getGroupOffset() * 30;
            int endIndex = queryEnumResultInfo.getGroupOffset() * 30 + 30 >= enumCheckGroupInfoList.size() ? enumCheckGroupInfoList.size() : queryEnumResultInfo.getGroupOffset() * 30 + 30;
            enumCheckGroupInfoList = enumCheckGroupInfoList.subList(startIndex, endIndex);
        }
        enumCheckGroupInfo.setGroupTotalCount(enumCheckGroupInfoList.size());
        enumCheckGroupInfo.setEnumCheckGroupInfoList(enumCheckGroupInfoList);
        return enumCheckGroupInfo;
    }

    private List<String> queryResultsGroup(QueryEnumResultInfo queryEnumResultInfo) {
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(queryEnumResultInfo.getContext().getFormSchemeKey());
        String tableName = "SYS_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumResultInfo.getContext().getTaskKey(), tableName);
        int viewType = Integer.parseInt(queryEnumResultInfo.getViewType());
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append("MJZD_GROUPDETAIL").append(" from ").append(libraryTableName).append(" \n ");
        sql.append(" where ").append("MJZD_ASYNCTASKID").append(" = ? and ").append("MJZD_VIEWTYPE").append(" = ?").append(" order by ").append("MJZD_UNITORDER");
        return this.jdbcTpl.queryForList(sql.toString(), String.class, new Object[]{queryEnumResultInfo.getAsyncTaskId(), viewType});
    }

    @Override
    public List<EnumDataCheckResultItem> queryEnumCheckResultExport(QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        FormSchemeDefine formSchemeDefine = this.viewCtrl.getFormScheme(queryEnumResultInfo.getContext().getFormSchemeKey());
        String tableName = "SYS_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumResultInfo.getContext().getTaskKey(), tableName);
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append("MJZD_GROUPDETAIL").append(" from ").append(libraryTableName).append(" \n ");
        sql.append(" where ").append("MJZD_ASYNCTASKID").append(" = ?").append(" and ").append("MJZD_VIEWTYPE").append(" = 1");
        List resData = this.jdbcTpl.queryForList(sql.toString(), String.class, new Object[]{queryEnumResultInfo.getAsyncTaskId()});
        ArrayList<EnumDataCheckResultItem> enumDataCheckResultItem = new ArrayList<EnumDataCheckResultItem>();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
        for (String item : resData) {
            ArrayList<EnumDataCheckResultItem> resultItems = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(EnumDataCheckServiceImpl.decompressFromJsonString(item), (JavaType)javaType);
            List<String> orgCodes = queryEnumResultInfo.getOrgCodes();
            if (!orgCodes.isEmpty()) {
                ArrayList<EnumDataCheckResultItem> resultInfoFilter = new ArrayList<EnumDataCheckResultItem>();
                block1: for (EnumDataCheckResultItem resultItem : resultItems) {
                    for (String orgCode : orgCodes) {
                        if (!resultItem.getMasterEntityKey().equals(orgCode)) continue;
                        resultInfoFilter.add(resultItem);
                        continue block1;
                    }
                }
                resultItems = resultInfoFilter;
            }
            enumDataCheckResultItem.addAll((Collection<EnumDataCheckResultItem>)resultItems);
        }
        return enumDataCheckResultItem;
    }
}

