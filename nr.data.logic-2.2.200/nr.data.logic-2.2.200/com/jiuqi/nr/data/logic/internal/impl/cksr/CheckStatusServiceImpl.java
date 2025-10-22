/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.api.ICheckStatusService;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatus;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatusQueryPar;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatusRecord;
import com.jiuqi.nr.data.logic.api.param.cksr.FormCheckStatusRecord;
import com.jiuqi.nr.data.logic.api.param.cksr.UnitCheckStatusRecord;
import com.jiuqi.nr.data.logic.api.param.cksr.UnitFormCheckStatusRecord;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.internal.impl.cksr.CheckStatusHelper;
import com.jiuqi.nr.data.logic.internal.impl.cksr.CksTableJoinProvider;
import com.jiuqi.nr.data.logic.internal.impl.cksr.obj.CheckStatusTableInfo;
import com.jiuqi.nr.data.logic.internal.obj.NvwaQueryPageInfo;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CheckStatusServiceImpl
implements ICheckStatusService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private CheckStatusHelper checkStatusHelper;
    private static final Logger logger = LoggerFactory.getLogger(CheckStatusServiceImpl.class);

    @Override
    public Collection<CheckStatusRecord> getCheckStatusRecords(CheckStatusQueryPar par) {
        CheckStatusTableInfo tableInfo = this.getTableInfo(par);
        if (tableInfo == null) {
            return Collections.emptyList();
        }
        NvwaQueryModel nvwaQueryModel = this.checkStatusHelper.getDetailQueryModel(par, tableInfo);
        MemoryDataSet<NvwaQueryColumn> dataRows = this.getDataRows(nvwaQueryModel);
        if (dataRows == null || dataRows.isEmpty()) {
            logger.debug("\u67e5\u8be2\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u660e\u7ec6\u6570\u636e\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return this.getCheckStatusRecords(tableInfo, dataRows, nvwaQueryModel);
    }

    @NonNull
    private Collection<CheckStatusRecord> getCheckStatusRecords(CheckStatusTableInfo tableInfo, MemoryDataSet<NvwaQueryColumn> dataRows, NvwaQueryModel nvwaQueryModel) {
        LinkedHashMap<String, CheckStatusRecord> checkStatusRecordMap = new LinkedHashMap<String, CheckStatusRecord>();
        Map<String, String> cksSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(tableInfo.getFormScheme(), tableInfo.getCksTable().getName());
        Map<String, String> cksSubSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(tableInfo.getFormScheme(), tableInfo.getCksSubTable().getName());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableInfo.getCksTable().getName());
        for (DataRow dataRow : dataRows) {
            CheckStatusRecord checkStatusRecord = CheckStatusServiceImpl.getCheckStatusRecord(dataRow, nvwaQueryModel, cksSplitKeyValue, cksSubSplitKeyValue, dimensionChanger);
            if (checkStatusRecordMap.containsKey(checkStatusRecord.getRecordKey())) {
                ((CheckStatusRecord)checkStatusRecordMap.get(checkStatusRecord.getRecordKey())).getErrorCount().putAll(checkStatusRecord.getErrorCount());
                continue;
            }
            checkStatusRecordMap.put(checkStatusRecord.getRecordKey(), checkStatusRecord);
        }
        return new ArrayList<CheckStatusRecord>(checkStatusRecordMap.values());
    }

    @NonNull
    private static CheckStatusRecord getCheckStatusRecord(DataRow dataRow, NvwaQueryModel nvwaQueryModel, Map<String, String> cksSplitKeyValue, Map<String, String> cksSubSplitKeyValue, DimensionChanger dimensionChanger) {
        CheckStatusRecord checkStatusRecord = new CheckStatusRecord();
        int checkType = 0;
        int errorCount = 0;
        block22: for (int i = 0; i < nvwaQueryModel.getColumns().size(); ++i) {
            ColumnModelDefine columnModel = ((NvwaQueryColumn)nvwaQueryModel.getColumns().get(i)).getColumnModel();
            switch (columnModel.getCode()) {
                case "CKS_REC_KEY": {
                    checkStatusRecord.setRecordKey(dataRow.getString(i));
                    continue block22;
                }
                case "CKS_BATCH_ID": {
                    checkStatusRecord.setCheckActionID(dataRow.getString(i));
                    continue block22;
                }
                case "CKS_ACTION": {
                    checkStatusRecord.setCheckActionType(ActionEnum.getByCode(dataRow.getInt(i)));
                    continue block22;
                }
                case "CKS_FLS_KEY": {
                    checkStatusRecord.setFormulaSchemeKey(dataRow.getString(i));
                    continue block22;
                }
                case "CKS_FRM_KEY": {
                    checkStatusRecord.setFormKey(dataRow.getString(i));
                    continue block22;
                }
                case "CKS_CK_STATE": {
                    checkStatusRecord.setCheckStatus(CheckStatus.getByValue(dataRow.getInt(i)));
                    continue block22;
                }
                case "CKS_CK_TIME": {
                    Calendar calendar = dataRow.getDate(i);
                    Instant checkTime = Instant.ofEpochMilli(calendar.getTimeInMillis());
                    checkStatusRecord.setCheckTime(checkTime);
                    continue block22;
                }
                case "CKS_S_CK_TYPE": {
                    checkType = dataRow.getInt(i);
                    continue block22;
                }
                case "CKS_S_ERR_COUNT": {
                    errorCount = dataRow.getInt(i);
                    continue block22;
                }
                default: {
                    String dimensionName;
                    if (cksSplitKeyValue.containsKey(columnModel.getCode()) || cksSubSplitKeyValue.containsKey(columnModel.getCode()) || !StringUtils.hasText(dimensionName = dimensionChanger.getDimensionName(columnModel.getCode()))) continue block22;
                    checkStatusRecord.getDimensionValueSet().setValue(dimensionName, dataRow.getValue(i));
                }
            }
        }
        checkStatusRecord.getErrorCount().put(checkType, errorCount);
        return checkStatusRecord;
    }

    @Nullable
    private MemoryDataSet<NvwaQueryColumn> getDataRows(NvwaQueryModel nvwaQueryModel) {
        return this.getDataRows(nvwaQueryModel, null);
    }

    @Nullable
    private MemoryDataSet<NvwaQueryColumn> getDataRows(NvwaQueryModel nvwaQueryModel, PagerInfo pagerInfo) {
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        dataAccessContext.setSqlJoinProvider((ISqlJoinProvider)new CksTableJoinProvider());
        NvwaQueryPageInfo pageInfo = CommonUtils.getNvwaQueryPageInfo(pagerInfo);
        MemoryDataSet dataRows = null;
        if (pageInfo != null) {
            INvwaPageDataAccess pageDataAccess = this.dataAccessProvider.createPageDataAccess(nvwaQueryModel);
            try {
                dataRows = pageDataAccess.executeQuery(dataAccessContext, pageInfo.getStartIndex(), pageInfo.getEndIndex());
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2CKS\u8868\u6570\u636e\u5931\u8d25:{}", (Object)e.getMessage(), (Object)e);
            }
        } else {
            INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            try {
                dataRows = readOnlyDataAccess.executeQuery(dataAccessContext);
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2CKS\u8868\u6570\u636e\u5931\u8d25:{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return dataRows;
    }

    public Collection<FormCheckStatusRecord> getFormCheckStatusRecords(CheckStatusQueryPar par) {
        CheckStatusTableInfo tableInfo = this.getTableInfo(par);
        if (tableInfo == null) {
            return Collections.emptyList();
        }
        NvwaQueryModel formQueryModel = this.checkStatusHelper.getFormQueryModel(par, tableInfo);
        MemoryDataSet<NvwaQueryColumn> dataRows = this.getDataRows(formQueryModel);
        if (dataRows == null || dataRows.isEmpty()) {
            logger.debug("\u67e5\u8be2\u62a5\u8868\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u6570\u636e\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return this.getFormCheckStatusRecords(tableInfo, dataRows, formQueryModel);
    }

    private Collection<FormCheckStatusRecord> getFormCheckStatusRecords(CheckStatusTableInfo tableInfo, MemoryDataSet<NvwaQueryColumn> dataRows, NvwaQueryModel formQueryModel) {
        HashMap<String, FormCheckStatusRecord> formCheckStatusRecordMap = new HashMap<String, FormCheckStatusRecord>();
        for (DataRow dataRow : dataRows) {
            FormCheckStatusRecord formCheckStatusRecord = new FormCheckStatusRecord();
            int checkType = 0;
            int errorCount = 0;
            block13: for (int i = 0; i < formQueryModel.getColumns().size(); ++i) {
                ColumnModelDefine column = ((NvwaQueryColumn)formQueryModel.getColumns().get(i)).getColumnModel();
                switch (column.getCode()) {
                    case "CKS_FRM_KEY": {
                        formCheckStatusRecord.setFormKey(dataRow.getString(i));
                        continue block13;
                    }
                    case "CKS_CK_STATE": {
                        formCheckStatusRecord.setCheckStatus(CheckStatus.getByValue(dataRow.getInt(i)));
                        continue block13;
                    }
                    case "CKS_S_CK_TYPE": {
                        checkType = dataRow.getInt(i);
                        continue block13;
                    }
                    case "CKS_S_ERR_COUNT": {
                        errorCount = dataRow.getInt(i);
                        continue block13;
                    }
                }
            }
            formCheckStatusRecord.getErrorCount().put(checkType, errorCount);
            if (formCheckStatusRecordMap.containsKey(formCheckStatusRecord.getFormKey())) {
                ((FormCheckStatusRecord)formCheckStatusRecordMap.get(formCheckStatusRecord.getFormKey())).getErrorCount().put(checkType, errorCount);
                continue;
            }
            formCheckStatusRecordMap.put(formCheckStatusRecord.getFormKey(), formCheckStatusRecord);
        }
        return new ArrayList<FormCheckStatusRecord>(formCheckStatusRecordMap.values());
    }

    public Collection<UnitCheckStatusRecord> getUnitCheckStatusRecords(CheckStatusQueryPar par) {
        CheckStatusTableInfo tableInfo = this.getTableInfo(par);
        if (tableInfo == null) {
            return Collections.emptyList();
        }
        NvwaQueryModel unitQueryModel = this.checkStatusHelper.getUnitQueryModel(par, tableInfo);
        MemoryDataSet<NvwaQueryColumn> dataRows = this.getDataRows(unitQueryModel);
        if (dataRows == null || dataRows.isEmpty()) {
            logger.debug("\u67e5\u8be2\u5355\u4f4d\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u6570\u636e\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return this.getUnitCheckStatusRecords(tableInfo, dataRows, unitQueryModel);
    }

    private Collection<UnitCheckStatusRecord> getUnitCheckStatusRecords(CheckStatusTableInfo tableInfo, MemoryDataSet<NvwaQueryColumn> dataRows, NvwaQueryModel unitQueryModel) {
        HashMap<String, UnitCheckStatusRecord> unitCheckStatusRecordMap = new HashMap<String, UnitCheckStatusRecord>();
        for (DataRow dataRow : dataRows) {
            UnitCheckStatusRecord unitCheckStatusRecord = new UnitCheckStatusRecord();
            int checkType = 0;
            int errorCount = 0;
            block13: for (int i = 0; i < unitQueryModel.getColumns().size(); ++i) {
                ColumnModelDefine column = ((NvwaQueryColumn)unitQueryModel.getColumns().get(i)).getColumnModel();
                switch (column.getCode()) {
                    case "MDCODE": {
                        unitCheckStatusRecord.setUnitKey(dataRow.getString(i));
                        continue block13;
                    }
                    case "CKS_CK_STATE": {
                        unitCheckStatusRecord.setCheckStatus(CheckStatus.getByValue(dataRow.getInt(i)));
                        continue block13;
                    }
                    case "CKS_S_CK_TYPE": {
                        checkType = dataRow.getInt(i);
                        continue block13;
                    }
                    case "CKS_S_ERR_COUNT": {
                        errorCount = dataRow.getInt(i);
                        continue block13;
                    }
                }
            }
            unitCheckStatusRecord.getErrorCount().put(checkType, errorCount);
            if (unitCheckStatusRecordMap.containsKey(unitCheckStatusRecord.getUnitKey())) {
                ((UnitCheckStatusRecord)unitCheckStatusRecordMap.get(unitCheckStatusRecord.getUnitKey())).getErrorCount().put(checkType, errorCount);
                continue;
            }
            unitCheckStatusRecordMap.put(unitCheckStatusRecord.getUnitKey(), unitCheckStatusRecord);
        }
        return new ArrayList<UnitCheckStatusRecord>(unitCheckStatusRecordMap.values());
    }

    public Collection<UnitFormCheckStatusRecord> getUnitFormCheckStatusRecords(CheckStatusQueryPar par) {
        CheckStatusTableInfo tableInfo = this.getTableInfo(par);
        if (tableInfo == null) {
            return Collections.emptyList();
        }
        NvwaQueryModel unitFormQueryModel = this.checkStatusHelper.getUnitFormQueryModel(par, tableInfo);
        MemoryDataSet<NvwaQueryColumn> dataRows = this.getDataRows(unitFormQueryModel);
        if (dataRows == null || dataRows.isEmpty()) {
            logger.debug("\u67e5\u8be2\u5355\u4f4d\u62a5\u8868\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u6570\u636e\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return this.getUnitFormCheckStatusRecords(tableInfo, dataRows, unitFormQueryModel);
    }

    private Collection<UnitFormCheckStatusRecord> getUnitFormCheckStatusRecords(CheckStatusTableInfo tableInfo, MemoryDataSet<NvwaQueryColumn> dataRows, NvwaQueryModel unitFormQueryModel) {
        HashMap<String, UnitFormCheckStatusRecord> unitFormCheckStatusRecordHashMap = new HashMap<String, UnitFormCheckStatusRecord>();
        for (DataRow dataRow : dataRows) {
            UnitFormCheckStatusRecord unitFormCheckStatusRecord = new UnitFormCheckStatusRecord();
            int checkType = 0;
            int errorCount = 0;
            block15: for (int i = 0; i < unitFormQueryModel.getColumns().size(); ++i) {
                ColumnModelDefine column = ((NvwaQueryColumn)unitFormQueryModel.getColumns().get(i)).getColumnModel();
                switch (column.getCode()) {
                    case "MDCODE": {
                        unitFormCheckStatusRecord.setUnitKey(dataRow.getString(i));
                        continue block15;
                    }
                    case "CKS_FRM_KEY": {
                        unitFormCheckStatusRecord.setFormKey(dataRow.getString(i));
                        continue block15;
                    }
                    case "CKS_CK_STATE": {
                        unitFormCheckStatusRecord.setCheckStatus(CheckStatus.getByValue(dataRow.getInt(i)));
                        continue block15;
                    }
                    case "CKS_S_CK_TYPE": {
                        checkType = dataRow.getInt(i);
                        continue block15;
                    }
                    case "CKS_S_ERR_COUNT": {
                        errorCount = dataRow.getInt(i);
                        continue block15;
                    }
                }
            }
            unitFormCheckStatusRecord.getErrorCount().put(checkType, errorCount);
            String mapKey = unitFormCheckStatusRecord.getUnitKey() + "_" + unitFormCheckStatusRecord.getFormKey();
            if (unitFormCheckStatusRecordHashMap.containsKey(mapKey)) {
                ((UnitFormCheckStatusRecord)unitFormCheckStatusRecordHashMap.get(mapKey)).getErrorCount().put(checkType, errorCount);
                continue;
            }
            unitFormCheckStatusRecordHashMap.put(mapKey, unitFormCheckStatusRecord);
        }
        return new ArrayList<UnitFormCheckStatusRecord>(unitFormCheckStatusRecordHashMap.values());
    }

    private CheckStatusTableInfo getTableInfo(CheckStatusQueryPar par) {
        String formSchemeKey = par.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            logger.error("FormScheme not found: {}", (Object)formSchemeKey);
            return null;
        }
        CheckStatusTableInfo tableInfo = this.checkStatusHelper.checkTableModel(formScheme);
        if (tableInfo == null) {
            logger.error("CKS\u8868\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u67e5\u8be2\u5ba1\u6838\u72b6\u6001");
        }
        return tableInfo;
    }
}

