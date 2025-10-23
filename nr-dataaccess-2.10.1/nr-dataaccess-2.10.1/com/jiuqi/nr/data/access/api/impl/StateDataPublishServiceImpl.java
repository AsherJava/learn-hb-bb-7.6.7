/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.data.access.api.IStateDataPublishService;
import com.jiuqi.nr.data.access.api.param.PublishParam;
import com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class StateDataPublishServiceImpl
implements IStateDataPublishService {
    private static final Logger logger = LoggerFactory.getLogger(StateDataPublishServiceImpl.class);
    private static final String SYS_ADMIN = "sys_user_admin";
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;

    @Override
    public boolean isEnableDataPublis(String taskKey) {
        Assert.notNull((Object)taskKey, (String)"taskKey is must not be null");
        String dataPubObj = this.taskOptionController.getValue(taskKey, "DATA_PUBLISHING");
        return "1".equals(dataPubObj);
    }

    @Override
    public Boolean isDataPublished(PublishParam publishParam) {
        Assert.notNull((Object)publishParam.getFormSchemeKey(), (String)"formSchemeKey is must not be null");
        Assert.notNull((Object)publishParam.getMasterKey(), (String)"masterKey is must not be null");
        Assert.isTrue((!CollectionUtils.isEmpty(publishParam.getFormKeys()) ? 1 : 0) != 0, (String)"formKeys is must not be null");
        boolean isDataPublished = false;
        String formSchemeKey = publishParam.getFormSchemeKey();
        DimensionCollection dimensionCollection = publishParam.getMasterKey();
        String formKey = (String)publishParam.getFormKeys().stream().findFirst().get();
        FormSchemeDefine formScheme = this.queryFormSchemeDefine(formSchemeKey);
        if (!this.isEnableDataPublis(formScheme.getTaskKey())) {
            return false;
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        DimensionValueSet masterKeys = this.dataAccesslUtil.filterReportDims(formScheme, dimensionValueSet);
        try {
            TableModelDefine dataPublishTable = this.queryPublishTable(formScheme);
            String tableKey = dataPublishTable.getID();
            List columns = this.dataModelService.getColumnModelDefinesByTable(dataPublishTable.getID());
            DimensionValueSet queryKey = this.rebuildDimKey(masterKeys, formSchemeKey, formKey);
            INvwaDataSet dataTable = this.getDataPublishDataTable(queryKey, dataPublishTable, columns, true);
            ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(dataPublishTable.getName(), dataTable, queryKey);
            INvwaDataRow findRow = dataTable.findRow(arrayKey);
            if (findRow != null) {
                Object value = findRow.getValue(this.dataModelService.getColumnModelDefineByCode(tableKey, "DP_ISPUBLISH"));
                isDataPublished = "1".equals(value);
            }
        }
        catch (Exception e) {
            logger.error("\u72b6\u6001\u5f15\u64ce-\u6570\u636e\u53d1\u5e03\u72b6\u6001\u67e5\u8be2\u5f02\u5e38", e);
            throw new AccessException(e);
        }
        return isDataPublished;
    }

    @Override
    public List<DataPublishReturnInfo> dataPublish(PublishParam publishParam, AsyncTaskMonitor asyncTaskMonitor) {
        Assert.notNull((Object)publishParam.getFormSchemeKey(), (String)"formSchemeKey is must not be null");
        Assert.notNull((Object)publishParam.getMasterKey(), (String)"masterKey is must not be null");
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6570\u636e\u6743\u9650\u670d\u52a1", OperLevel.USER_OPER);
        String formSchemeKey = publishParam.getFormSchemeKey();
        DimensionCollection dimensionCollection = publishParam.getMasterKey();
        List<String> formKeys = publishParam.getFormKeys();
        boolean isPublish = publishParam.isPublish();
        FormSchemeDefine formScheme = this.queryFormSchemeDefine(formSchemeKey);
        List dimensionSetList = dimensionCollection.getDimensionCombinations();
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String entityDimName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        List<String> collect = dimensionSetList.stream().map(e -> (String)e.getValue(entityDimName)).collect(Collectors.toList());
        DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimensionName));
        String dateTime = formScheme.getDateTime();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setPeriod(dateTime, periodCode);
        logDimensionCollection.setDw(entityId, collect.toArray(new String[0]));
        String content = isPublish ? "\u53d1\u5e03" : "\u53d6\u6d88\u53d1\u5e03";
        logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u6570\u636e" + content + "\u5f00\u59cb", "\u6570\u636e" + content + "\u5f00\u59cb");
        double weight = dimensionSetList.size() > 0 ? 1.0 / (double)dimensionSetList.size() : 1.0;
        double index = 0.0;
        ArrayList<DataPublishReturnInfo> returnInfoList = new ArrayList<DataPublishReturnInfo>();
        TableModelDefine dataPublishTable = this.queryPublishTable(formScheme);
        List columns = this.dataModelService.getColumnModelDefinesByTable(dataPublishTable.getID());
        DimensionValueSet valueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        DimensionValueSet masterKeys = this.dataAccesslUtil.filterReportDims(formScheme, valueSet);
        DimensionValueSet queryKey = this.rebuildDimKey(masterKeys, formSchemeKey, formKeys);
        INvwaDataSet table = this.getDataPublishDataTable(queryKey, dataPublishTable, columns, false);
        INvwaUpdatableDataSet dataTable = (INvwaUpdatableDataSet)table;
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        List<DimensionValueSet> dimensionList = DimensionValueSetUtil.getDimensionSetList(masterKeys);
        Map canFormsPublish = null;
        for (DimensionValueSet dimensionCombination : dimensionList) {
            DimensionValueSet currentDim = this.dataAccesslUtil.filterReportDims(formScheme, dimensionCombination);
            index += 1.0;
            boolean canReadUnPublish = false;
            try {
                String dimValue = String.valueOf(currentDim.getValue(entityDimName));
                canReadUnPublish = this.entityAuthorityService.canPublishEntity(entityId, dimValue, Consts.DATE_VERSION_MIN_VALUE, Consts.DATE_VERSION_MAX_VALUE);
            }
            catch (UnauthorizedEntityException e2) {
                logger.error(e2.getMessage(), e2);
            }
            if (canReadUnPublish) {
                if (Objects.isNull(canFormsPublish) && !CollectionUtils.isEmpty(formKeys)) {
                    List<Object> orgList = new ArrayList();
                    Object orgs = masterKeys.getValue(entityDimName);
                    if (orgs instanceof List) {
                        orgList = (List)orgs;
                    } else if (orgs instanceof String) {
                        orgList = Arrays.asList(((String)orgs).split(";"));
                    }
                    canFormsPublish = this.definitionAuthorityProvider.batchQueryCanPublishWithDuty(formKeys, orgList, entityId);
                }
                try {
                    if (!CollectionUtils.isEmpty(formKeys)) {
                        Map orgForms = (Map)canFormsPublish.get(dimensionCombination.getValue(entityDimName).toString());
                        for (String formKey : formKeys) {
                            if (!Objects.nonNull(orgForms) || !Objects.nonNull(orgForms.get(formKey)) || !((Boolean)orgForms.get(formKey)).booleanValue()) continue;
                            currentDim = this.rebuildDimKey(currentDim, formSchemeKey, formKey);
                            this.setRowValue(dataTable, currentDim, dataPublishTable, columns, isPublish ? "1" : "0", formSchemeKey);
                        }
                    }
                    currentDim = this.rebuildDimKey(currentDim, formSchemeKey, formKeys);
                    this.setRowValue(dataTable, currentDim, dataPublishTable, columns, isPublish ? "1" : "0", formSchemeKey);
                }
                catch (Exception e3) {
                    throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{"DP_ISPUBLISH" + formScheme.getFormSchemeCode()});
                }
            } else {
                DataPublishReturnInfo returnInfo = new DataPublishReturnInfo();
                returnInfo.setStatus(1);
                returnInfo.setEntity(String.valueOf(currentDim.getValue(entityDimName)));
                returnInfo.setMessage("\u6ca1\u6709\u5355\u4f4d\u7684\u6570\u636e\u53d1\u5e03\u6743\u9650");
                returnInfoList.add(returnInfo);
            }
            asyncTaskMonitor.progressAndMessage(index * weight, currentDim.getValue(entityDimName) + "\u53d1\u5e03\u5b8c\u6210");
        }
        logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u6570\u636e" + content + "\u5b8c\u6210", "\u6570\u636e" + content + "\u5b8c\u6210");
        try {
            dataTable.commitChanges(context);
        }
        catch (Exception e4) {
            logHelper.error(formScheme.getTaskKey(), logDimensionCollection, "\u6570\u636e" + content + "\u51fa\u9519", "\u6570\u636e" + content + "\u51fa\u9519" + e4.getMessage());
            throw new AccessException("\u6570\u636e\u53d1\u5e03\u51fa\u9519\uff01");
        }
        return returnInfoList;
    }

    @Override
    public List<String> getPublishedFormKeys(PublishParam param) {
        Assert.notNull((Object)param.getFormSchemeKey(), (String)"formSchemeKey is must not be null");
        Assert.notNull((Object)param.getMasterKey(), (String)"masterKey is must not be null");
        ArrayList<String> formKeys = new ArrayList<String>();
        String formSchemeKey = param.getFormSchemeKey();
        DimensionCollection dimensionCollection = param.getMasterKey();
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(formSchemeKey);
        if (!this.isEnableDataPublis(formSchemeDefine.getTaskKey())) {
            return formKeys;
        }
        DimensionValueSet valueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        if (valueSet.hasAnyNull()) {
            return formKeys;
        }
        DimensionValueSet masterKeys = this.dataAccesslUtil.filterReportDims(formSchemeDefine, valueSet);
        try {
            TableModelDefine dataPublishTable = this.queryPublishTable(formSchemeDefine);
            List columns = this.dataModelService.getColumnModelDefinesByTable(dataPublishTable.getID());
            DimensionValueSet queryKey = this.rebuildDimKey(masterKeys, formSchemeKey, null);
            INvwaDataSet dataTable = this.getDataPublishDataTable(queryKey, dataPublishTable, columns, true);
            int totalCount = dataTable.size();
            Map columnsMap = columns.stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity(), (o, n) -> o));
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow item = dataTable.getRow(i);
                Object value = item.getValue((ColumnModelDefine)columnsMap.get("DP_ISPUBLISH"));
                if (!"1".equals(value)) continue;
                Object formKeyValue = item.getValue((ColumnModelDefine)columnsMap.get("DP_FORMKEY"));
                formKeys.add(String.valueOf(formKeyValue));
            }
        }
        catch (Exception e) {
            logger.error("\u72b6\u6001\u5f15\u64ce-\u6570\u636e\u53d1\u5e03\u72b6\u6001\u67e5\u8be2\u5f02\u5e38", e);
            throw new AccessException(e);
        }
        return formKeys;
    }

    @Override
    public List<DataPublishBatchReadWriteResult> getBatchResult(PublishParam param) {
        Assert.notNull((Object)param.getFormSchemeKey(), (String)"formSchemeKey is must not be null");
        Assert.notNull((Object)param.getMasterKey(), (String)"masterKey is must not be null");
        String formSchemeKey = param.getFormSchemeKey();
        DimensionCollection dimensionCollection = param.getMasterKey();
        List formKeys = param.getFormKeys();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        FormSchemeDefine formScheme = this.queryFormSchemeDefine(formSchemeKey);
        if (!this.isEnableDataPublis(formScheme.getTaskKey())) {
            return Collections.emptyList();
        }
        ArrayList<DataPublishBatchReadWriteResult> result = new ArrayList<DataPublishBatchReadWriteResult>();
        DimensionValueSet valueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        DimensionValueSet masterKeys = this.dataAccesslUtil.filterReportDims(formScheme, valueSet);
        try {
            TableModelDefine dataPublishTable = this.queryPublishTable(formScheme);
            DimensionValueSet queryKey = this.rebuildDimKey(masterKeys, formSchemeKey, formKeys);
            List columns = this.dataModelService.getColumnModelDefinesByTable(dataPublishTable.getID());
            List columnsList = columns.stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList());
            int publishIndex = columnsList.indexOf("DP_ISPUBLISH");
            INvwaDataSet dataTable = this.getDataPublishDataTable(queryKey, dataPublishTable, columns, true);
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                if (CollectionUtils.isEmpty(formKeys)) {
                    formKeys = this.runtimeView.queryAllFormKeysByFormScheme(formSchemeKey);
                }
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                for (String formKey : formKeys) {
                    dimensionValueSet.setValue("DP_FORMSCHEMEKEY", (Object)formSchemeKey);
                    dimensionValueSet.setValue("DP_FORMKEY", (Object)formKey);
                    ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(dataPublishTable.getName(), dataTable, dimensionValueSet);
                    INvwaDataRow item = dataTable.findRow(arrayKey);
                    DataPublishBatchReadWriteResult batchReadWriteResult = new DataPublishBatchReadWriteResult();
                    batchReadWriteResult.setFormKey(formKey);
                    dimensionValueSet.clearValue("DP_FORMSCHEMEKEY");
                    dimensionValueSet.clearValue("DP_FORMKEY");
                    batchReadWriteResult.setDimensionSet(dimensionValueSet);
                    if (item != null) {
                        Object value = item.getValue(publishIndex);
                        batchReadWriteResult.setPublished("1".equals(value));
                    } else {
                        batchReadWriteResult.setPublished(false);
                    }
                    result.add(batchReadWriteResult);
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u72b6\u6001\u5f15\u64ce-\u6570\u636e\u53d1\u5e03\u72b6\u6001\u6279\u91cf\u67e5\u8be2\u5f02\u5e38", e2);
            throw new AccessException(e2);
        }
        return result;
    }

    private void setRowValue(INvwaUpdatableDataSet dataTable, DimensionValueSet masterKey, TableModelDefine tableDefine, List<ColumnModelDefine> columns, String isPublish, String formSchemeKey) throws Exception {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKey);
        ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableDefine.getName(), (INvwaDataSet)dataTable, dimensionValueSet);
        INvwaDataRow findRow = dataTable.findRow(arrayKey);
        List columnsList = columns.stream().map(e -> e.getName()).collect(Collectors.toList());
        if (findRow == null) {
            INvwaDataRow appendRow = dataTable.appendRow();
            this.nvwaDataEngineQueryUtil.setRowKey(tableDefine.getName(), appendRow, dimensionValueSet);
            appendRow.setValue(columnsList.indexOf("DP_ID"), (Object)UUID.randomUUID().toString());
            appendRow.setValue(columnsList.indexOf("DP_ISPUBLISH"), (Object)isPublish);
            appendRow.setValue(columnsList.indexOf("DP_FORMSCHEMEKEY"), (Object)formSchemeKey);
            appendRow.setValue(columnsList.indexOf("DP_USER"), (Object)this.getCurrentUserId());
            appendRow.setValue(columnsList.indexOf("DP_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
        } else {
            findRow.setValue(columnsList.indexOf("DP_ISPUBLISH"), (Object)isPublish);
            findRow.setValue(columnsList.indexOf("DP_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
            findRow.setValue(columnsList.indexOf("DP_USER"), (Object)this.getCurrentUserId());
        }
    }

    private INvwaDataSet getDataPublishDataTable(DimensionValueSet masterKey, TableModelDefine dataPublishTable, List<ColumnModelDefine> columns, boolean readOnly) {
        INvwaDataSet table = this.nvwaDataEngineQueryUtil.queryDataSetWithRowKey(dataPublishTable.getName(), masterKey, columns, new ArrayList<String>(), new HashMap<String, Boolean>(), readOnly);
        return table;
    }

    private TableModelDefine queryPublishTable(FormSchemeDefine formScheme) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = TableConsts.getSysTableName("NR_DATAPUBLISH_%s", dataScheme.getBizCode());
        TableModelDefine publishTable = this.dataModelService.getTableModelDefineByName(tableName);
        return publishTable;
    }

    private FormSchemeDefine queryFormSchemeDefine(String formSchemeKey) {
        return this.runtimeView.getFormScheme(formSchemeKey);
    }

    private String getCurrentUserId() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        if (user != null) {
            return user.getId();
        }
        return SYS_ADMIN;
    }

    private DimensionValueSet rebuildDimKey(DimensionValueSet masterKeys, String formSchemeKey, Object formKey) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        if (formSchemeKey != null) {
            dimensionValueSet.setValue("DP_FORMSCHEMEKEY", (Object)formSchemeKey);
        }
        if (formKey != null) {
            dimensionValueSet.setValue("DP_FORMKEY", formKey);
        }
        return dimensionValueSet;
    }
}

