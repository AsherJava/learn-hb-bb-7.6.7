/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.type.CollectionType
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo
 *  com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.integritycheck.asynctask.CanceledInfo
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.enumcheck.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.enumcheck.common.CompositeKey;
import com.jiuqi.nr.enumcheck.common.EnumCheckGroupInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckGroupItemInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckResInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.enumcheck.common.EnumTableItem;
import com.jiuqi.nr.enumcheck.common.EnumTableResultInfo;
import com.jiuqi.nr.enumcheck.common.ExportEnumCheckResParam;
import com.jiuqi.nr.enumcheck.common.ExportEnumCheckResult;
import com.jiuqi.nr.enumcheck.common.FormFieldWrapper;
import com.jiuqi.nr.enumcheck.common.QueryEnumCheckResGroupParam;
import com.jiuqi.nr.enumcheck.common.QueryEnumCheckResParam;
import com.jiuqi.nr.enumcheck.common.SaveEnumCheckResParam;
import com.jiuqi.nr.enumcheck.dao.EnumCheckResDao;
import com.jiuqi.nr.enumcheck.helper.EnumCheckHelper;
import com.jiuqi.nr.enumcheck.service.IEnumCheckService;
import com.jiuqi.nr.enumcheck.utils.EnumCheckExecutor;
import com.jiuqi.nr.enumcheck.utils.TmpTableUtils;
import com.jiuqi.nr.integritycheck.asynctask.CanceledInfo;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumCheckServiceImpl
implements IEnumCheckService {
    private static final Logger logger = LoggerFactory.getLogger(EnumCheckServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private TmpTableUtils tmpTableUtils;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private EnumCheckHelper enumCheckHelper;
    @Autowired
    private DataQueryHelper dataQueryHelper;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private EnumCheckResDao enumCheckResDao;
    @Autowired
    private IDataCheckCommonService dataCheckCommonService;
    private static final int PAGER_COUNT = 30;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EnumCheckResInfo enumDataCheck(EnumDataCheckParam enumDataCheckParam, AsyncTaskMonitor asyncTaskMonitor) {
        EnumCheckResInfo resultInfo;
        block40: {
            resultInfo = new EnumCheckResInfo();
            DimensionCollection dims = enumDataCheckParam.getDims();
            if (CollectionUtils.isEmpty(dims.getDimensionCombinations())) {
                return null;
            }
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(enumDataCheckParam.getTaskKey());
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.08, "getData");
            }
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.1, "authFilter");
            }
            List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(enumDataCheckParam.getFormSchemeKey());
            resultInfo.setFormCount(formKeys.size());
            ArrayList<EnumDataCheckResultItem> enumDataCheckResultItems = new ArrayList<EnumDataCheckResultItem>();
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.15, "running");
            }
            Object periodDimValue = dims.combineDim().getValue("DATATIME");
            IEntityDefine dwEntityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
            try {
                DataCheckDimInfo dataCheckDimInfo = this.dataCheckCommonService.queryDims(enumDataCheckParam.getTaskKey(), enumDataCheckParam.getFormSchemeKey(), enumDataCheckParam.getDims());
                resultInfo.setDimNameTitleMap(dataCheckDimInfo.getDimNameTitleMap());
                resultInfo.setDimRange(dataCheckDimInfo.getDimRange());
                resultInfo.setDimNameIsShowMap(dataCheckDimInfo.getDimNameIsShowMap());
                HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                for (int i = 0; i < dims.combineDim().size(); ++i) {
                    String dimName = dims.combineDim().getName(i);
                    if (null != dataCheckDimInfo.getDimNameIsShowMap() && null != dataCheckDimInfo.getDimNameIsShowMap().get(dimName) && !((Boolean)dataCheckDimInfo.getDimNameIsShowMap().get(dimName)).booleanValue()) continue;
                    Object dimValue = dims.combineDim().getValue(i);
                    DimensionValue dimensionValue = new DimensionValue();
                    dimensionValue.setName(dimName);
                    if (dimValue instanceof String) {
                        dimensionValue.setValue((String)dimValue);
                    } else if (dimValue instanceof List) {
                        dimensionValue.setValue(String.join((CharSequence)";", (List)dimValue));
                    }
                    dimensionSet.put(dimName, dimensionValue);
                }
                resultInfo.setDimensionSet(dimensionSet);
            }
            catch (Exception e) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5f02\u5e38\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (null != asyncTaskMonitor && asyncTaskMonitor.isCancel()) {
                CanceledInfo canceledInfo = new CanceledInfo();
                canceledInfo.setFormNum(0);
                asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                return resultInfo;
            }
            HashSet<String> enumTableDic = new HashSet<String>();
            ArrayList<ITempTable> tempTableList = new ArrayList<ITempTable>();
            try {
                boolean enableNrdb = this.nrdbHelper.isEnableNrdb();
                String periodStr = (String)periodDimValue;
                PeriodWrapper periodWrapper = null;
                if (!com.jiuqi.np.definition.common.StringUtils.isEmpty((String)periodStr)) {
                    periodWrapper = new PeriodWrapper(periodStr);
                }
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, enumDataCheckParam.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                EnumCheckExecutor enumCheckExecutor = EnumCheckExecutor.createChecker(periodWrapper, executorContext, enumDataCheckParam, dwEntityDefine.getDimensionName(), enableNrdb);
                if (enableNrdb) {
                    enumCheckExecutor.prepareDataEnableNrdb();
                } else {
                    List<ITempTable> tmpTables = enumCheckExecutor.prepareData();
                    tempTableList.addAll(tmpTables);
                }
                HashSet<String> untityDic = new HashSet<String>(enumCheckExecutor.getSelUnitKeys());
                resultInfo.setSelEntityCount(untityDic.size());
                resultInfo.setSelEnumDicCount(this.getEnumCount(enumDataCheckParam));
                HashSet<String> errorEntityKeys = new HashSet<String>();
                for (int i = 0; i < formKeys.size(); ++i) {
                    if (null != asyncTaskMonitor && asyncTaskMonitor.isCancel()) {
                        CanceledInfo canceledInfo = new CanceledInfo();
                        canceledInfo.setFormNum(i);
                        asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                        EnumCheckResInfo enumCheckResInfo = resultInfo;
                        return enumCheckResInfo;
                    }
                    if (asyncTaskMonitor != null) {
                        double process = (double)(i + 1) / (double)formKeys.size();
                        asyncTaskMonitor.progressAndMessage(0.15 + 0.84 * process, "running");
                    }
                    List<EnumDataCheckResultItem> errInForms = enumCheckExecutor.executeCheck(taskDefine, dwEntityDefine.getId(), (String)formKeys.get(i), enumTableDic);
                    enumDataCheckResultItems.addAll(errInForms);
                    errorEntityKeys.addAll(errInForms.stream().map(EnumDataCheckResultItem::getMasterEntityKey).collect(Collectors.toSet()));
                    if (enumCheckExecutor.getErrorCount() < 1000) continue;
                    resultInfo.setHasTooManyError(true);
                    break;
                }
                resultInfo.setErrorEntityKeys(errorEntityKeys);
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.progressAndMessage(0.99, "finish");
                }
            }
            catch (Exception e) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5f02\u5e38\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
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
                    logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5f02\u5e38\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            if (null != asyncTaskMonitor && asyncTaskMonitor.isCancel()) {
                CanceledInfo canceledInfo = new CanceledInfo();
                canceledInfo.setFormNum(formKeys.size());
                asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                return resultInfo;
            }
            SaveEnumCheckResParam saveEnumCheckResParam = new SaveEnumCheckResParam();
            saveEnumCheckResParam.setTaskKey(enumDataCheckParam.getTaskKey());
            saveEnumCheckResParam.setPeriod((String)periodDimValue);
            saveEnumCheckResParam.setFormSchemeKey(enumDataCheckParam.getFormSchemeKey());
            saveEnumCheckResParam.setEnumDataCheckResultItems(enumDataCheckResultItems);
            if (asyncTaskMonitor != null) {
                saveEnumCheckResParam.setAsyncTaskId(enumDataCheckParam.getBatchId());
            }
            EnumCheckResultSaveInfo saveInfo = this.saveEnumCheckResults(saveEnumCheckResParam);
            resultInfo.setSaveInfo(saveInfo);
            resultInfo.setCheckResCount(enumDataCheckResultItems.size());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                this.enumCheckResDao.insert(enumDataCheckParam.getBatchId(), enumDataCheckParam.getFormSchemeKey(), objectMapper.writeValueAsString((Object)resultInfo));
            }
            catch (JsonProcessingException e) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5f02\u5e38\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                if (asyncTaskMonitor == null) break block40;
                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
            }
        }
        return resultInfo;
    }

    private int getEnumCount(EnumDataCheckParam enumDataCheckParam) {
        List<String> enumNames = enumDataCheckParam.getEnumNames();
        if (null == enumNames || enumNames.isEmpty()) {
            EnumTableResultInfo enumTableResultInfo = this.queryAllEnumTables(enumDataCheckParam);
            return enumTableResultInfo.getEnumDataCheckResult().size();
        }
        return enumNames.size();
    }

    @Override
    public EnumTableResultInfo queryAllEnumTables(EnumDataCheckParam enumDataCheckParam) {
        EnumTableResultInfo result = new EnumTableResultInfo();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(enumDataCheckParam.getTaskKey());
        ArrayList<EnumTableItem> resultList = new ArrayList<EnumTableItem>();
        result.setEnumDataCheckResult(resultList);
        try {
            List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(enumDataCheckParam.getFormSchemeKey());
            HashSet<String> enumDic = new HashSet<String>();
            for (String formKey : formKeys) {
                List<FormFieldWrapper> allEnumOfForm = this.enumCheckHelper.getAllEnumOfForm(taskDefine.getDataScheme(), taskDefine.getDw(), formKey);
                for (FormFieldWrapper ffw : allEnumOfForm) {
                    if (enumDic.contains(ffw.getRefTableName())) continue;
                    EnumTableItem enumTableItem = new EnumTableItem();
                    enumTableItem.setEnumName(ffw.getRefEntity().getCode());
                    enumTableItem.setEnumTitle(ffw.getRefEntity().getTitle());
                    enumDic.add(ffw.getRefTableName());
                    resultList.add(enumTableItem);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public EnumCheckResultSaveInfo saveEnumCheckResults(SaveEnumCheckResParam saveEnumCheckResParam) {
        try {
            return this.insertEnumCheckResults(saveEnumCheckResParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private EnumCheckResultSaveInfo insertEnumCheckResults(SaveEnumCheckResParam saveEnumCheckResParam) throws Exception {
        Object[] arg;
        int r;
        Object[] arg2;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(saveEnumCheckResParam.getFormSchemeKey());
        String tableName = "NR_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(saveEnumCheckResParam.getTaskKey(), tableName);
        HashMap resultsByUnit = new HashMap();
        HashMap resultsByEnum = new HashMap();
        HashMap resultsByForm = new HashMap();
        HashSet<CompositeKey> errorUnitKeys = new HashSet<CompositeKey>();
        HashSet<CompositeKey> errorEnumCodes = new HashSet<CompositeKey>();
        HashSet<CompositeKey> errorFormKeys = new HashSet<CompositeKey>();
        EnumCheckResultSaveInfo enumCheckResultSaveInfo = new EnumCheckResultSaveInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        List<EnumDataCheckResultItem> enumDataCheckResultItems = saveEnumCheckResParam.getEnumDataCheckResultItems();
        for (EnumDataCheckResultItem resultItem : enumDataCheckResultItems) {
            Iterator dimNameValueMap = new HashMap<String, String>(resultItem.getDimNameValueMap());
            CompositeKey unitCompositeKey = new CompositeKey(resultItem.getMasterEntityKey(), (Map<String, String>)((Object)dimNameValueMap));
            if (!errorUnitKeys.contains(unitCompositeKey)) {
                ArrayList<EnumDataCheckResultItem> unitGroupList = new ArrayList<EnumDataCheckResultItem>();
                unitGroupList.add(resultItem);
                errorUnitKeys.add(unitCompositeKey);
                resultsByUnit.put(unitCompositeKey, unitGroupList);
            } else if (resultsByUnit.containsKey(unitCompositeKey)) {
                ArrayList<EnumDataCheckResultItem> tmpLstForUnit = (ArrayList<EnumDataCheckResultItem>)resultsByUnit.get(unitCompositeKey);
                if (null != tmpLstForUnit) {
                    tmpLstForUnit.add(resultItem);
                } else {
                    tmpLstForUnit = new ArrayList<EnumDataCheckResultItem>();
                    tmpLstForUnit.add(resultItem);
                }
                resultsByUnit.put(unitCompositeKey, tmpLstForUnit);
            }
            CompositeKey enumCompositeKey = new CompositeKey(resultItem.getEnumCode(), (Map<String, String>)((Object)dimNameValueMap));
            if (!errorEnumCodes.contains(enumCompositeKey)) {
                ArrayList<EnumDataCheckResultItem> enumGroupList = new ArrayList<EnumDataCheckResultItem>();
                enumGroupList.add(resultItem);
                errorEnumCodes.add(enumCompositeKey);
                resultsByEnum.put(enumCompositeKey, enumGroupList);
            } else if (resultsByEnum.containsKey(enumCompositeKey)) {
                ArrayList<EnumDataCheckResultItem> tmpLstForEnum = (ArrayList<EnumDataCheckResultItem>)resultsByEnum.get(enumCompositeKey);
                if (null != tmpLstForEnum) {
                    tmpLstForEnum.add(resultItem);
                } else {
                    tmpLstForEnum = new ArrayList<EnumDataCheckResultItem>();
                    tmpLstForEnum.add(resultItem);
                }
                resultsByEnum.put(enumCompositeKey, tmpLstForEnum);
            }
            CompositeKey formCompositeKey = new CompositeKey(resultItem.getFormKey(), (Map<String, String>)((Object)dimNameValueMap));
            if (!errorFormKeys.contains(formCompositeKey)) {
                ArrayList<EnumDataCheckResultItem> formGroupList = new ArrayList<EnumDataCheckResultItem>();
                formGroupList.add(resultItem);
                errorFormKeys.add(formCompositeKey);
                resultsByForm.put(formCompositeKey, formGroupList);
                continue;
            }
            if (!resultsByForm.containsKey(formCompositeKey)) continue;
            ArrayList<EnumDataCheckResultItem> tmpLstForForm = (ArrayList<EnumDataCheckResultItem>)resultsByForm.get(formCompositeKey);
            if (null != tmpLstForForm) {
                tmpLstForForm.add(resultItem);
            } else {
                tmpLstForForm = new ArrayList<EnumDataCheckResultItem>();
                tmpLstForForm.add(resultItem);
            }
            resultsByForm.put(formCompositeKey, tmpLstForForm);
        }
        enumCheckResultSaveInfo.setErrorEnumCount(errorEnumCodes.size());
        enumCheckResultSaveInfo.setErrorUnitCount(errorUnitKeys.size());
        enumCheckResultSaveInfo.setErrorFormCount(errorFormKeys.size());
        Date newDate = new Date();
        ArrayList<Object[]> args = new ArrayList<Object[]>(errorEnumCodes.size() + errorUnitKeys.size() + errorFormKeys.size());
        for (Map.Entry entry : resultsByUnit.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), saveEnumCheckResParam.getAsyncTaskId(), ((CompositeKey)entry.getKey()).getStrPart(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 0, newDate, entry.getKey(), ((EnumDataCheckResultItem)((List)entry.getValue()).get(0)).getEntityOrder()};
            args.add(arg2);
        }
        for (Map.Entry entry : resultsByEnum.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), saveEnumCheckResParam.getAsyncTaskId(), ((CompositeKey)entry.getKey()).getStrPart(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 1, newDate, entry.getKey()};
            args.add(arg2);
        }
        for (Map.Entry entry : resultsByForm.entrySet()) {
            arg2 = new Object[]{UUID.randomUUID().toString(), saveEnumCheckResParam.getAsyncTaskId(), ((CompositeKey)entry.getKey()).getStrPart(), objectMapper.writeValueAsString(entry.getValue()), SettingUtil.getCurrentUser().getName(), 2, newDate, entry.getKey()};
            args.add(arg2);
        }
        if (args.size() > 0) {
            EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formSchemeDefine.getKey());
            IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityView, saveEnumCheckResParam.getPeriod(), saveEnumCheckResParam.getFormSchemeKey(), false);
            HashMap untityOrderDic = this.entityQueryHelper.entityOrderByKey(entityTable);
            HashMap formOrderDic = this.entityQueryHelper.formOrderByKey(formSchemeDefine.getKey());
            args.sort((o1, o2) -> {
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return this.compareResultItem(untityOrderDic, formOrderDic, (Object[])o1, (Object[])o2);
            });
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
        TableModelDefine enumCheckResTable = this.dataModelService.getTableModelDefineByName(libraryTableName);
        if (null == enumCheckResTable) {
            logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1");
            throw new NotFoundTableDefineException(new String[]{"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1"});
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List ecrFields = this.dataModelService.getColumnModelDefinesByTable(enumCheckResTable.getID());
        for (ColumnModelDefine ecrField : ecrFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator nvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            List columns = queryModel.getColumns();
            for (Object[] objects : argsOrderByUnit) {
                INvwaDataRow nvwaDataRow = nvwaDataUpdator.addInsertRow();
                block30: for (int i = 0; i < columns.size(); ++i) {
                    ColumnModelDefine columnModel = ((NvwaQueryColumn)columns.get(i)).getColumnModel();
                    switch (columnModel.getCode()) {
                        case "MJZD_KEY": {
                            nvwaDataRow.setValue(i, objects[0]);
                            continue block30;
                        }
                        case "MJZD_ASYNCTASKID": {
                            nvwaDataRow.setValue(i, objects[1]);
                            continue block30;
                        }
                        case "MJZD_GROUPKEY": {
                            nvwaDataRow.setValue(i, objects[2]);
                            continue block30;
                        }
                        case "MJZD_GROUPDETAIL": {
                            nvwaDataRow.setValue(i, objects[3]);
                            continue block30;
                        }
                        case "MJZD_OPERATOR": {
                            nvwaDataRow.setValue(i, objects[4]);
                            continue block30;
                        }
                        case "MJZD_VIEWTYPE": {
                            nvwaDataRow.setValue(i, objects[5]);
                            continue block30;
                        }
                        case "MJZD_UPTIME": {
                            nvwaDataRow.setValue(i, objects[6]);
                            continue block30;
                        }
                        case "MJZD_UNITORDER": {
                            nvwaDataRow.setValue(i, objects[8]);
                            continue block30;
                        }
                        default: {
                            String dimensionName = columnModel.getName();
                            Map<String, String> dimNameValue = ((CompositeKey)objects[7]).getMapPart();
                            if ("ADJUST".equals(dimensionName) && dimNameValue.containsKey(dimensionName)) {
                                nvwaDataRow.setValue(i, (Object)"0");
                                continue block30;
                            }
                            String dimValue = dimNameValue.get(dimensionName);
                            nvwaDataRow.setValue(i, (Object)dimValue);
                        }
                    }
                }
            }
            nvwaDataUpdator.commitChanges(context);
            enumCheckResultSaveInfo.setSaveStatus(true);
        }
        catch (Exception e) {
            logger.error("\u4fdd\u5b58\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            enumCheckResultSaveInfo.setSaveStatus(false);
        }
        return enumCheckResultSaveInfo;
    }

    public int compareResultItem(HashMap<String, Integer> untityOrderDic, HashMap<String, Integer> formOrderDic, Object[] o1, Object[] o2) {
        int result = Integer.compare((Integer)o1[5], (Integer)o2[5]);
        if (result != 0) {
            return result;
        }
        int viewType = (Integer)o1[5];
        if (viewType == 0) {
            int order1 = untityOrderDic.get(o1[2].toString());
            int order2 = untityOrderDic.get(o1[2].toString());
            return Integer.compare(order1, order2);
        }
        if (viewType == 2) {
            int order1 = formOrderDic.get(o1[2].toString());
            int order2 = formOrderDic.get(o1[2].toString());
            return Integer.compare(order1, order2);
        }
        return 0;
    }

    @Override
    public EnumCheckGroupInfo queryEnumCheckResultGroup(QueryEnumCheckResGroupParam queryEnumCheckResGroupParam) throws Exception {
        EnumCheckGroupInfo enumCheckGroupInfo = new EnumCheckGroupInfo();
        LinkedHashMap<String, List<String>> groupkeyResMap = this.queryResultsGroup(queryEnumCheckResGroupParam);
        if (groupkeyResMap.isEmpty()) {
            return null;
        }
        List<String> resultGroups = new ArrayList<String>();
        for (Map.Entry<String, List<String>> groupkeyResEntry : groupkeyResMap.entrySet()) {
            String result = this.combinedResults(groupkeyResEntry.getValue());
            resultGroups.add(result);
        }
        List<String> orgCodes = queryEnumCheckResGroupParam.getOrgCodes();
        if (orgCodes.isEmpty()) {
            int startIndex = queryEnumCheckResGroupParam.getPage() * 30;
            int endIndex = Math.min(queryEnumCheckResGroupParam.getPage() * 30 + 30, resultGroups.size());
            resultGroups = resultGroups.subList(startIndex, endIndex);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<EnumCheckGroupItemInfo> enumCheckGroupInfoList = new ArrayList<EnumCheckGroupItemInfo>();
        for (String item : resultGroups) {
            EnumCheckGroupItemInfo enumCheckGroupItemInfo = new EnumCheckGroupItemInfo();
            CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
            ArrayList<EnumDataCheckResultItem> showGroupItem = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(item, (JavaType)javaType);
            if (!orgCodes.isEmpty()) {
                ArrayList<EnumDataCheckResultItem> showGroupItemFilter = new ArrayList<EnumDataCheckResultItem>();
                block2: for (EnumDataCheckResultItem enumDataCheckResultItem : showGroupItem) {
                    for (String orgCode : orgCodes) {
                        if (!enumDataCheckResultItem.getMasterEntityKey().equals(orgCode)) continue;
                        showGroupItemFilter.add(enumDataCheckResultItem);
                        continue block2;
                    }
                }
                if (showGroupItemFilter.isEmpty()) continue;
                showGroupItem = showGroupItemFilter;
            }
            enumCheckGroupItemInfo.setGroupCount(showGroupItem.size());
            if ("0".equals(queryEnumCheckResGroupParam.getViewType())) {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getEntityTitle());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getMasterEntityKey());
            } else if ("1".equals(queryEnumCheckResGroupParam.getViewType())) {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getEnumTitle());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getEnumCode());
            } else {
                enumCheckGroupItemInfo.setGroupTitle(((EnumDataCheckResultItem)showGroupItem.get(0)).getBbfz());
                enumCheckGroupItemInfo.setGroupKey(((EnumDataCheckResultItem)showGroupItem.get(0)).getFormKey());
            }
            enumCheckGroupInfoList.add(enumCheckGroupItemInfo);
        }
        if (!orgCodes.isEmpty()) {
            int startIndex = queryEnumCheckResGroupParam.getPage() * 30;
            int endIndex = Math.min(queryEnumCheckResGroupParam.getPage() * 30 + 30, enumCheckGroupInfoList.size());
            enumCheckGroupInfoList = enumCheckGroupInfoList.subList(startIndex, endIndex);
        }
        enumCheckGroupInfo.setGroupTotalCount(groupkeyResMap.size());
        enumCheckGroupInfo.setEnumCheckGroupInfoList(enumCheckGroupInfoList);
        return enumCheckGroupInfo;
    }

    @NotNull
    private String combinedResults(List<String> results) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < results.size(); ++i) {
            if (i > 0) {
                resultBuilder.append(results.get(i).substring(1));
            } else {
                resultBuilder.append(results.get(i));
            }
            if (i == results.size() - 1) continue;
            resultBuilder.delete(resultBuilder.length() - 1, resultBuilder.length());
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private LinkedHashMap<String, List<String>> queryResultsGroup(QueryEnumCheckResGroupParam queryEnumCheckResGroupParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(queryEnumCheckResGroupParam.getFormSchemeKey());
        String tableName = "NR_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumCheckResGroupParam.getTaskKey(), tableName);
        int viewType = Integer.parseInt(queryEnumCheckResGroupParam.getViewType());
        LinkedHashMap<String, List<String>> result = new LinkedHashMap<String, List<String>>();
        TableModelDefine enumCheckResTable = this.dataModelService.getTableModelDefineByName(libraryTableName);
        if (null == enumCheckResTable) {
            throw new NotFoundTableDefineException(new String[]{"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1"});
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ColumnModelDefine orderColum = null;
        List ecrFields = this.dataModelService.getColumnModelDefinesByTable(enumCheckResTable.getID());
        for (ColumnModelDefine ecrField : ecrFields) {
            if ("MJZD_GROUPDETAIL".equals(ecrField.getCode())) {
                queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
                continue;
            }
            if ("MJZD_ASYNCTASKID".equals(ecrField.getCode())) {
                queryModel.getColumnFilters().put(ecrField, queryEnumCheckResGroupParam.getAsyncTaskId());
                continue;
            }
            if ("MJZD_VIEWTYPE".equals(ecrField.getCode())) {
                queryModel.getColumnFilters().put(ecrField, viewType);
                continue;
            }
            if ("MJZD_UNITORDER".equals(ecrField.getCode())) {
                orderColum = ecrField;
                continue;
            }
            if (!"MJZD_GROUPKEY".equals(ecrField.getCode())) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
        }
        Map<String, String> selectDims = queryEnumCheckResGroupParam.getSelectDims();
        if (selectDims != null && !selectDims.isEmpty()) {
            StringBuilder dimFilter = new StringBuilder();
            dimFilter.append("(");
            for (Map.Entry<String, String> entry : selectDims.entrySet()) {
                String dimName = entry.getKey();
                String dimValue = entry.getValue();
                dimFilter.append("(");
                dimFilter.append("isnull(").append(libraryTableName).append("[").append(dimName).append("]) or ");
                dimFilter.append(libraryTableName).append("[").append(dimName).append("] = '").append(dimValue).append("')");
                dimFilter.append(" and ");
            }
            dimFilter.delete(dimFilter.length() - 5, dimFilter.length());
            dimFilter.append(")");
            queryModel.setFilter(dimFilter.toString());
        }
        queryModel.getOrderByItems().add(new OrderByItem(orderColum, false));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            List columns = queryModel.getColumns();
            int groupKeyindex = 0;
            int resColumIndex = 0;
            for (int i = 0; i < columns.size(); ++i) {
                if ("MJZD_GROUPKEY".equals(((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode())) {
                    groupKeyindex = i;
                    continue;
                }
                resColumIndex = i;
            }
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            for (int i = 0; i < dataRows.size(); ++i) {
                DataRow item = dataRows.get(i);
                String groupKey = item.getString(groupKeyindex);
                String resultItem = item.getString(resColumIndex);
                if (!result.containsKey(groupKey)) {
                    ArrayList<String> resultItems = new ArrayList<String>();
                    resultItems.add(resultItem);
                    result.put(groupKey, resultItems);
                    continue;
                }
                result.get(groupKey).add(resultItem);
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String queryEnumCheckResults(QueryEnumCheckResParam queryEnumCheckResParam) throws Exception {
        List<String> results = this.queryGroupItemData(queryEnumCheckResParam);
        String result = this.combinedResults(results);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
        ArrayList<EnumDataCheckResultItem> resultInfo = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(result, (JavaType)javaType);
        List<String> orgCodes = queryEnumCheckResParam.getOrgCodes();
        if (!orgCodes.isEmpty()) {
            ArrayList<EnumDataCheckResultItem> resultInfoFilter = new ArrayList<EnumDataCheckResultItem>();
            block0: for (EnumDataCheckResultItem enumDataCheckResultItem : resultInfo) {
                for (String string : orgCodes) {
                    if (!enumDataCheckResultItem.getMasterEntityKey().equals(string)) continue;
                    resultInfoFilter.add(enumDataCheckResultItem);
                    continue block0;
                }
            }
            resultInfo = resultInfoFilter;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(queryEnumCheckResParam.getTaskKey());
        HashMap<String, IEntityTable> dimNameTableMap = new HashMap<String, IEntityTable>();
        String dimsStr = taskDefine.getDims();
        if (StringUtils.isNotEmpty((String)dimsStr)) {
            String[] dimArrays;
            for (String dimArra : dimArrays = dimsStr.split(";")) {
                IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(dimArra);
                if (null == dimEntityDefine) continue;
                IEntityQuery query = this.entityQueryHelper.getDimEntityQuery(dimArra, queryEnumCheckResParam.getPeriodDimValue());
                IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, queryEnumCheckResParam.getFormSchemeKey(), true);
                dimNameTableMap.put(dimEntityDefine.getDimensionName(), entityTable);
            }
        }
        for (EnumDataCheckResultItem enumDataCheckResultItem : resultInfo) {
            if (null == enumDataCheckResultItem.getDimNameValueMap() || enumDataCheckResultItem.getDimNameValueMap().isEmpty()) continue;
            enumDataCheckResultItem.setDimNameCodeMap(enumDataCheckResultItem.getDimNameValueMap());
            HashMap<String, String> dimNameTitle = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : enumDataCheckResultItem.getDimNameValueMap().entrySet()) {
                IEntityRow entityRow = ((IEntityTable)dimNameTableMap.get(entry.getKey())).findByEntityKey(entry.getValue());
                String dimTitle = null != entityRow ? entityRow.getTitle() : entry.getKey();
                dimNameTitle.put(entry.getKey(), dimTitle);
            }
            enumDataCheckResultItem.setDimNameValueMap(dimNameTitle);
        }
        if (resultInfo.size() <= 0) {
            return null;
        }
        int pageSize = queryEnumCheckResParam.getPageSize();
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int n = queryEnumCheckResParam.getPage() * pageSize;
        int endIndex = Math.min(queryEnumCheckResParam.getPage() * pageSize + pageSize, resultInfo.size());
        List showResults = resultInfo.subList(n, endIndex);
        return objectMapper.writeValueAsString(showResults);
    }

    private List<String> queryGroupItemData(QueryEnumCheckResParam queryEnumCheckResParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(queryEnumCheckResParam.getFormSchemeKey());
        String tableName = "NR_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(queryEnumCheckResParam.getTaskKey(), tableName);
        TableModelDefine enumCheckResTable = this.dataModelService.getTableModelDefineByName(libraryTableName);
        if (null == enumCheckResTable) {
            throw new NotFoundTableDefineException(new String[]{"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1"});
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List ecrFields = this.dataModelService.getColumnModelDefinesByTable(enumCheckResTable.getID());
        for (ColumnModelDefine ecrField : ecrFields) {
            if ("MJZD_GROUPDETAIL".equals(ecrField.getCode())) {
                queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
                continue;
            }
            if ("MJZD_ASYNCTASKID".equals(ecrField.getCode())) {
                queryModel.getColumnFilters().put(ecrField, queryEnumCheckResParam.getAsyncTaskId());
                continue;
            }
            if (!"MJZD_GROUPKEY".equals(ecrField.getCode())) continue;
            queryModel.getColumnFilters().put(ecrField, queryEnumCheckResParam.getGroupKey());
        }
        Map<String, String> selectDims = queryEnumCheckResParam.getSelectDims();
        if (selectDims != null && !selectDims.isEmpty()) {
            StringBuilder dimFilter = new StringBuilder();
            dimFilter.append("(");
            for (Map.Entry<String, String> entry : selectDims.entrySet()) {
                String dimName = entry.getKey();
                String dimValue = entry.getValue();
                dimFilter.append("(");
                dimFilter.append("isnull(").append(libraryTableName).append("[").append(dimName).append("]) or ");
                dimFilter.append(libraryTableName).append("[").append(dimName).append("] = '").append(dimValue).append("')");
                dimFilter.append(" and ");
            }
            dimFilter.delete(dimFilter.length() - 5, dimFilter.length());
            dimFilter.append(")");
            queryModel.setFilter(dimFilter.toString());
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            ArrayList<String> results = new ArrayList<String>();
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            for (int i = 0; i < dataRows.size(); ++i) {
                DataRow item = dataRows.get(i);
                results.add(item.getString(0));
            }
            return results;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ExportEnumCheckResult> exportEnumCheckResult(ExportEnumCheckResParam exportEnumCheckResParam) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(exportEnumCheckResParam.getFormSchemeKey());
        String tableName = "NR_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(exportEnumCheckResParam.getTaskKey(), tableName);
        TableModelDefine enumCheckResTable = this.dataModelService.getTableModelDefineByName(libraryTableName);
        if (null == enumCheckResTable) {
            throw new NotFoundTableDefineException(new String[]{"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1"});
        }
        ArrayList<String> resData = new ArrayList<String>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List ecrFields = this.dataModelService.getColumnModelDefinesByTable(enumCheckResTable.getID());
        for (ColumnModelDefine ecrField : ecrFields) {
            if (ecrField.getCode().equals("MJZD_GROUPDETAIL")) {
                queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
                continue;
            }
            if (ecrField.getCode().equals("MJZD_ASYNCTASKID")) {
                queryModel.getColumnFilters().put(ecrField, exportEnumCheckResParam.getAsyncTaskId());
                continue;
            }
            if (!ecrField.getCode().equals("MJZD_VIEWTYPE")) continue;
            queryModel.getColumnFilters().put(ecrField, 1);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            for (int i = 0; i < dataRows.size(); ++i) {
                DataRow item = dataRows.get(i);
                resData.add(item.getString(0));
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u5931\u8d25\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList enumDataCheckResultItem = new ArrayList();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, EnumDataCheckResultItem.class);
        for (String item : resData) {
            ArrayList<EnumDataCheckResultItem> resultItems = (ArrayList<EnumDataCheckResultItem>)objectMapper.readValue(item, (JavaType)javaType);
            List<String> orgCodes = exportEnumCheckResParam.getOrgCodes();
            if (!orgCodes.isEmpty()) {
                ArrayList<EnumDataCheckResultItem> resultInfoFilter = new ArrayList<EnumDataCheckResultItem>();
                block5: for (EnumDataCheckResultItem resultItem : resultItems) {
                    for (String orgCode : orgCodes) {
                        if (!resultItem.getMasterEntityKey().equals(orgCode)) continue;
                        resultInfoFilter.add(resultItem);
                        continue block5;
                    }
                }
                resultItems = resultInfoFilter;
            }
            enumDataCheckResultItem.addAll(resultItems);
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(exportEnumCheckResParam.getTaskKey());
        HashMap<String, IEntityTable> dimNameTableMap = new HashMap<String, IEntityTable>();
        String dimsStr = taskDefine.getDims();
        if (StringUtils.isNotEmpty((String)dimsStr)) {
            String[] dimArrays = dimsStr.split(";");
            for (String dimArra : dimArrays) {
                IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(dimArra);
                if (null == dimEntityDefine) continue;
                IEntityQuery query = this.entityQueryHelper.getDimEntityQuery(dimArra, exportEnumCheckResParam.getPeriodDimValue());
                IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, exportEnumCheckResParam.getFormSchemeKey(), true);
                dimNameTableMap.put(dimEntityDefine.getDimensionName(), entityTable);
            }
        }
        ArrayList<ExportEnumCheckResult> results = new ArrayList<ExportEnumCheckResult>();
        for (EnumDataCheckResultItem enumDataCheckResult : enumDataCheckResultItem) {
            ExportEnumCheckResult result = new ExportEnumCheckResult();
            result.setEntityTitle(enumDataCheckResult.getEntityTitle());
            result.setEnumTitle(enumDataCheckResult.getEnumTitle());
            result.setEnumCode(enumDataCheckResult.getEnumCode());
            result.setField(enumDataCheckResult.getField());
            result.setBbfz(enumDataCheckResult.getBbfz());
            result.setErrorKind(enumDataCheckResult.getErrorKind());
            Map<String, String> dimNameValueMap = enumDataCheckResult.getDimNameValueMap();
            if (null != dimNameValueMap && !dimNameValueMap.isEmpty()) {
                HashMap<String, String> dimNameValueTitleMap = new HashMap<String, String>();
                for (Map.Entry<String, String> entry : dimNameValueMap.entrySet()) {
                    String dimName = entry.getKey();
                    String dimValue = entry.getValue();
                    IEntityTable entityTable = (IEntityTable)dimNameTableMap.get(dimName);
                    if (null == entityTable) continue;
                    dimNameValueTitleMap.put(dimName, entityTable.findByEntityKey(dimValue).getTitle());
                }
                result.setDimNameValueTitleMap(dimNameValueTitleMap);
            }
            results.add(result);
        }
        return results;
    }
}

