/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.impl.NvwaUpdatableDataSet
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.data.access.api.IStateWorkFlowService;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.impl.NvwaUpdatableDataSet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class StateWorkFlowServiceImpl
implements IStateWorkFlowService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private static final String FORMID = "FORMID";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private BiFunction<DimensionValueSet, String, DimensionValueSet> reBuildMasterKey = (masterKey, formSchemeKey) -> {
        DimensionValueSet dimensionValue = new DimensionValueSet();
        Object formKey = null;
        if (masterKey.getValue(FORMID) != null) {
            DimensionSet dimensionSet = masterKey.getDimensionSet();
            int size = dimensionSet.size();
            for (int i = 0; i < size; ++i) {
                String dimenName = dimensionSet.get(i);
                Object value = masterKey.getValue(dimenName);
                if (FORMID.equals(dimenName)) {
                    formKey = value;
                    continue;
                }
                dimensionValue.setValue(dimenName, value);
            }
            dimensionValue.setValue("WL_FORMKEY", formKey);
        } else {
            dimensionValue = new DimensionValueSet(masterKey);
            dimensionValue.setValue("WL_FORMKEY", (Object)"00000000-0000-0000-0000-000000000000");
        }
        dimensionValue.setValue("WL_FORMSCHEMEKEY", formSchemeKey);
        return dimensionValue;
    };

    @Override
    public void stateSave(String formSchemeKey, DimensionValueSet masterKey, WorkflowState state) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        Assert.notNull((Object)state, "state is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        masterKey = DimensionValueSetUtil.filterDimensionValueSet(masterKey, "PROCESSKEY");
        DimensionValueSet rowKey = this.reBuildMasterKey.apply(masterKey, formSchemeKey);
        String tableName = null;
        try {
            TableModelDefine table = this.getTableModelDefine(formScheme);
            tableName = table.getName();
            List fields = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            ColumnModelDefine idField = fields.stream().filter(column -> column.getName().equals("WL_ID")).findFirst().get();
            HashMap<String, Object> filterMap = new HashMap<String, Object>();
            filterMap.put("WL_FORMSCHEMEKEY", formSchemeKey);
            INvwaDataSet dataSet = this.nvwaDataEngineQueryUtil.queryDataSet(table.getName(), rowKey, fields, new ArrayList<String>(), filterMap, new HashMap<String, Boolean>(), false);
            ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableName, dataSet, rowKey);
            INvwaDataRow dataRow = dataSet.findRow(arrayKey);
            NvwaUpdatableDataSet nvwaUpdatableDataSet = (NvwaUpdatableDataSet)dataSet;
            if (dataRow == null) {
                dataRow = nvwaUpdatableDataSet.appendRow();
                this.nvwaDataEngineQueryUtil.setRowKey(tableName, dataRow, rowKey);
                dataRow.setValue(fields.indexOf(idField), (Object)UUIDUtils.getKey());
            }
            for (ColumnModelDefine fieldDefine : fields) {
                if (fieldDefine.getCode().equals("WL_STATE")) {
                    dataRow.setValue(fields.indexOf(fieldDefine), (Object)state.getValue());
                    continue;
                }
                if (fieldDefine.getCode().equals("WL_FORMSCHEMEKEY")) {
                    dataRow.setValue(fields.indexOf(fieldDefine), (Object)formSchemeKey);
                    continue;
                }
                if (!fieldDefine.getCode().equals("WL_UPDATETIME")) continue;
                dataRow.setValue(fields.indexOf(fieldDefine), (Object)new Date());
            }
            nvwaUpdatableDataSet.commitChanges(new DataAccessContext(this.dataModelService));
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u72b6\u6001\u8868\u66f4\u65b0\u5931\u8d25\uff01");
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit workflow state data error. masterKeys:");
            logBuilder.append(rowKey.toString());
            logBuilder.append(", formSchemeKey:");
            logBuilder.append(formSchemeKey).append(", state:").append((Object)state);
            logBuilder.append(", tableName:").append(tableName);
            throw new AccessException(logBuilder.toString(), e);
        }
    }

    @Override
    public void stateBatchSave(String formSchemeKey, List<DimensionValueSet> masterKeys, WorkflowState state, DimensionValueSet queryKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull(masterKeys, "masterKeys is must not be null!");
        Assert.notNull((Object)queryKey, "queryKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableName = null;
        try {
            TableModelDefine table = this.getTableModelDefine(formScheme);
            tableName = table.getName();
            List fields = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            ColumnModelDefine idField = fields.stream().filter(column -> column.getName().equals("WL_ID")).findFirst().get();
            HashMap<String, Object> filterMap = new HashMap<String, Object>();
            filterMap.put("WL_FORMSCHEMEKEY", formSchemeKey);
            INvwaDataSet dataSet = this.nvwaDataEngineQueryUtil.queryDataSet(table.getName(), queryKey, fields, new ArrayList<String>(), filterMap, new HashMap<String, Boolean>(), false);
            NvwaUpdatableDataSet nvwaUpdatableDataSet = (NvwaUpdatableDataSet)dataSet;
            for (DimensionValueSet masterKey : masterKeys) {
                DimensionValueSet rowKey = this.reBuildMasterKey.apply(masterKey = DimensionValueSetUtil.filterDimensionValueSet(masterKey, "PROCESSKEY"), formSchemeKey);
                ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableName, dataSet, rowKey);
                INvwaDataRow row = dataSet.findRow(arrayKey);
                if (null == row) {
                    row = nvwaUpdatableDataSet.appendRow();
                    this.nvwaDataEngineQueryUtil.setRowKey(tableName, row, rowKey);
                    row.setValue(fields.indexOf(idField), (Object)UUIDUtils.getKey());
                }
                for (ColumnModelDefine fieldDefine : fields) {
                    if (fieldDefine.getCode().equals("WL_STATE")) {
                        row.setValue(fields.indexOf(fieldDefine), (Object)state.getValue());
                        continue;
                    }
                    if (fieldDefine.getCode().equals("WL_FORMSCHEMEKEY")) {
                        row.setValue(fields.indexOf(fieldDefine), (Object)formSchemeKey);
                        continue;
                    }
                    if (!fieldDefine.getCode().equals("WL_UPDATETIME")) continue;
                    row.setValue(fields.indexOf(fieldDefine), (Object)new Date());
                }
            }
            nvwaUpdatableDataSet.commitChanges(new DataAccessContext(this.dataModelService));
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u72b6\u6001\u8868\u66f4\u65b0\u5931\u8d25\uff01");
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("batch commit workflow state data error. masterKeys:");
            logBuilder.append(", formSchemeKey:");
            logBuilder.append(formSchemeKey);
            logBuilder.append(", tableName:").append(tableName);
            throw new AccessException(logBuilder.toString(), e);
        }
    }

    @Override
    public void stateBatchSave(String formSchemeKey, Map<DimensionValueSet, WorkflowState> stateMap, DimensionValueSet queryKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull(stateMap, "stateMap is must not be null!");
        Assert.notNull((Object)queryKey, "queryKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableName = null;
        try {
            TableModelDefine table = this.getTableModelDefine(formScheme);
            tableName = table.getName();
            List fields = this.dataModelService.getColumnModelDefinesByTable(table.getID());
            ColumnModelDefine idField = fields.stream().filter(column -> column.getName().equals("WL_ID")).findFirst().get();
            HashMap<String, Object> filterMap = new HashMap<String, Object>();
            filterMap.put("WL_FORMSCHEMEKEY", formSchemeKey);
            INvwaDataSet dataSet = this.nvwaDataEngineQueryUtil.queryDataSet(table.getName(), queryKey, fields, new ArrayList<String>(), filterMap, new HashMap<String, Boolean>(), false);
            NvwaUpdatableDataSet nvwaUpdatableDataSet = (NvwaUpdatableDataSet)dataSet;
            for (Map.Entry<DimensionValueSet, WorkflowState> entry : stateMap.entrySet()) {
                DimensionValueSet rowKey = this.reBuildMasterKey.apply(entry.getKey(), formSchemeKey);
                ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableName, dataSet, rowKey);
                INvwaDataRow row = dataSet.findRow(arrayKey);
                if (null == row) {
                    row = nvwaUpdatableDataSet.appendRow();
                    this.nvwaDataEngineQueryUtil.setRowKey(tableName, row, rowKey);
                    row.setValue(fields.indexOf(idField), (Object)UUIDUtils.getKey());
                }
                for (ColumnModelDefine fieldDefine : fields) {
                    if (fieldDefine.getCode().equals("WL_STATE")) {
                        row.setValue(fields.indexOf(fieldDefine), (Object)entry.getValue().getValue());
                        continue;
                    }
                    if (fieldDefine.getCode().equals("WL_FORMSCHEMEKEY")) {
                        row.setValue(fields.indexOf(fieldDefine), (Object)formSchemeKey);
                        continue;
                    }
                    if (!fieldDefine.getCode().equals("WL_UPDATETIME")) continue;
                    row.setValue(fields.indexOf(fieldDefine), (Object)new Date());
                }
            }
            nvwaUpdatableDataSet.commitChanges(new DataAccessContext(this.dataModelService));
        }
        catch (Exception e) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u6d41\u7a0b\u72b6\u6001\u8868\u66f4\u65b0\u5931\u8d25\uff01");
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("batch commit workflow state data error. masterKeys:");
            logBuilder.append(", formSchemeKey:");
            logBuilder.append(formSchemeKey);
            logBuilder.append(", tableName:").append(tableName);
            throw new AccessException(logBuilder.toString(), e);
        }
    }

    private TableModelDefine getTableModelDefine(FormSchemeDefine formScheme) {
        String tableName = this.dataAccesslUtil.getTableName(formScheme, "NR_WORKFLOW_%s");
        return this.dataModelService.getTableModelDefineByName(tableName);
    }
}

