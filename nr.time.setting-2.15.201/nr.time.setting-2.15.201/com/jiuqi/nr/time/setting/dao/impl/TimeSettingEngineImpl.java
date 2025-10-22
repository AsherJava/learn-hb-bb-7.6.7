/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.dao.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.time.setting.bean.TimeSettingDao;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.dao.ITimeSettingDao;
import com.jiuqi.nr.time.setting.util.TdUtils;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSettingEngineImpl
implements ITimeSettingDao {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingEngineImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    UserService<User> userService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private TdUtils tdUtils;

    @Override
    public void saveDeadlineInfo(TimeSettingDao deadlineInfo) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(deadlineInfo.getFormSchemeKey());
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, allColumnModels);
        try {
            INvwaUpdatableDataSet executeQuery = updatableDataAccess.executeQueryForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            ArrayKey arrayKey = dimensionChanger.getArrayKey(deadlineInfo.getDimensionValueSet(), dimensionValueSetColumns);
            INvwaDataRow findRow = executeQuery.findRow(arrayKey);
            if (findRow == null) {
                INvwaDataRow appendRow = executeQuery.appendRow();
                int index = 0;
                for (ColumnModelDefine column : allColumnModels) {
                    switch (column.getCode()) {
                        case "formscheme_id": {
                            appendRow.setValue(index, (Object)deadlineInfo.getFormSchemeKey());
                            break;
                        }
                        case "submit_starttime": {
                            appendRow.setValue(index, (Object)deadlineInfo.getSubmitStartTime());
                            break;
                        }
                        case "submit_deadlinetime": {
                            appendRow.setValue(index, (Object)deadlineInfo.getDeadLineTime());
                            break;
                        }
                        case "repay_daealine": {
                            appendRow.setValue(index, (Object)deadlineInfo.getRepayDeadline());
                            break;
                        }
                        case "operator": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperator());
                            break;
                        }
                        case "operator_unitid": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperatorOfUnitId());
                            break;
                        }
                        case "unit_level": {
                            appendRow.setValue(index, (Object)deadlineInfo.getUnitLevel());
                            break;
                        }
                        case "createtime": {
                            SimpleDateFormat sd = new SimpleDateFormat();
                            String timeStr = sd.format(new Date());
                            appendRow.setValue(index, (Object)timeStr);
                            break;
                        }
                        case "updatetime": {
                            SimpleDateFormat sd1 = new SimpleDateFormat();
                            String timeStr1 = sd1.format(new Date());
                            appendRow.setValue(index, (Object)timeStr1);
                            break;
                        }
                    }
                    ++index;
                }
            }
            executeQuery.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDeadlineInfo(List<TimeSettingDao> deadlineInfos) {
        if (deadlineInfos == null || deadlineInfos.size() == 0) {
            return;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(deadlineInfos.get(0).getFormSchemeKey());
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, allColumnModels);
        try {
            INvwaUpdatableDataSet executeQuery = updatableDataAccess.executeQueryForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            for (TimeSettingDao deadlineInfo : deadlineInfos) {
                DimensionValueSet dimensionValueSet = deadlineInfo.getDimensionValueSet();
                ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionValueSetColumns);
                INvwaDataRow findRow = executeQuery.findRow(arrayKey);
                if (findRow != null) continue;
                INvwaDataRow appendRow = executeQuery.appendRow();
                int index = 0;
                for (ColumnModelDefine column : allColumnModels) {
                    switch (column.getCode()) {
                        case "formscheme_id": {
                            appendRow.setValue(index, (Object)deadlineInfo.getFormSchemeKey());
                            break;
                        }
                        case "submit_starttime": {
                            if (deadlineInfo.getSubmitStartTime() == null) break;
                            Timestamp submitTime = Timestamp.valueOf(deadlineInfo.getSubmitStartTime());
                            appendRow.setValue(index, (Object)submitTime);
                            break;
                        }
                        case "submit_deadlinetime": {
                            if (deadlineInfo.getDeadLineTime() == null) break;
                            Timestamp deadlineTime = Timestamp.valueOf(deadlineInfo.getDeadLineTime());
                            appendRow.setValue(index, (Object)deadlineTime);
                            break;
                        }
                        case "repay_daealine": {
                            if (deadlineInfo.getRepayDeadline() == null) break;
                            Timestamp repayDeadTime = Timestamp.valueOf(deadlineInfo.getRepayDeadline());
                            appendRow.setValue(index, (Object)repayDeadTime);
                            break;
                        }
                        case "operator": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperator());
                            break;
                        }
                        case "operator_unitid": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperatorOfUnitId());
                            break;
                        }
                        case "unit_level": {
                            appendRow.setValue(index, (Object)deadlineInfo.getUnitLevel());
                            break;
                        }
                        case "createtime": {
                            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr = sd.format(new Date());
                            Timestamp createTime = Timestamp.valueOf(timeStr);
                            appendRow.setValue(index, (Object)createTime);
                            break;
                        }
                        case "updatetime": {
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr1 = sd1.format(new Date());
                            Timestamp updateTime = Timestamp.valueOf(timeStr1);
                            appendRow.setValue(index, (Object)updateTime);
                            break;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(column);
                            if (dimensionName == null) break;
                            Object value = dimensionValueSet.getValue(dimensionName);
                            appendRow.setKeyValue(column, value);
                        }
                    }
                    ++index;
                }
            }
            executeQuery.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDeadlineInfo(List<TimeSettingDao> deadlineInfos) {
        if (deadlineInfos == null || deadlineInfos.size() == 0) {
            return;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(deadlineInfos.get(0).getFormSchemeKey());
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, allColumnModels);
        try {
            INvwaUpdatableDataSet executeQuery = updatableDataAccess.executeQueryForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            for (TimeSettingDao deadlineInfo : deadlineInfos) {
                DimensionValueSet dimensionValueSet = deadlineInfo.getDimensionValueSet();
                ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionValueSetColumns);
                INvwaDataRow findRow = executeQuery.findRow(arrayKey);
                INvwaDataRow appendRow = null;
                boolean addFlag = false;
                if (findRow == null) {
                    appendRow = executeQuery.appendRow();
                    addFlag = true;
                } else {
                    appendRow = findRow;
                }
                int index = 0;
                for (ColumnModelDefine column : allColumnModels) {
                    switch (column.getCode()) {
                        case "formscheme_id": {
                            appendRow.setValue(index, (Object)deadlineInfo.getFormSchemeKey());
                            break;
                        }
                        case "submit_starttime": {
                            if (deadlineInfo.getSubmitStartTime() != null) {
                                Timestamp submitStart = Timestamp.valueOf(deadlineInfo.getSubmitStartTime());
                                appendRow.setValue(index, (Object)submitStart);
                                break;
                            }
                            appendRow.setValue(index, null);
                            break;
                        }
                        case "submit_deadlinetime": {
                            if (deadlineInfo.getDeadLineTime() != null) {
                                Timestamp deadLineTime = Timestamp.valueOf(deadlineInfo.getDeadLineTime());
                                appendRow.setValue(index, (Object)deadLineTime);
                                break;
                            }
                            appendRow.setValue(index, null);
                            break;
                        }
                        case "repay_daealine": {
                            if (deadlineInfo.getRepayDeadline() != null) {
                                Timestamp repayDeadTime = Timestamp.valueOf(deadlineInfo.getRepayDeadline());
                                appendRow.setValue(index, (Object)repayDeadTime);
                                break;
                            }
                            appendRow.setValue(index, null);
                            break;
                        }
                        case "operator": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperator());
                            break;
                        }
                        case "operator_unitid": {
                            appendRow.setValue(index, (Object)deadlineInfo.getOperatorOfUnitId());
                            break;
                        }
                        case "unit_level": {
                            appendRow.setValue(index, (Object)deadlineInfo.getUnitLevel());
                            break;
                        }
                        case "createtime": {
                            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr = sd.format(new Date());
                            Timestamp createTime = Timestamp.valueOf(timeStr);
                            appendRow.setValue(index, (Object)createTime);
                            break;
                        }
                        case "updatetime": {
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr1 = sd1.format(new Date());
                            Timestamp updateTime = Timestamp.valueOf(timeStr1);
                            appendRow.setValue(index, (Object)updateTime);
                            break;
                        }
                        default: {
                            String dimensionName;
                            if (!addFlag || (dimensionName = dimensionChanger.getDimensionName(column)) == null) break;
                            Object value = dimensionValueSet.getValue(dimensionName);
                            appendRow.setKeyValue(column, value);
                        }
                    }
                    ++index;
                }
            }
            executeQuery.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeSettingInfo> queryTableData(String formSchemeKey, String period) {
        ArrayList<TimeSettingInfo> results = new ArrayList<TimeSettingInfo>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            if ("formscheme_id".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formSchemeKey);
            }
            if ("DATATIME".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, period);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            List<ColumnModelDefine> dimensionColumns = this.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQueryWithRowKey) {
                TimeSettingInfo timeSettingInfo = new TimeSettingInfo();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                String unitId = dimensionValue.getValue(this.tdUtils.getDwMainDimName(formSchemeKey)).toString();
                timeSettingInfo.setUnitId(unitId);
                this.buildTimeSettingInfo(timeSettingInfo, iNvwaDataRow, allColumnModels);
                results.add(timeSettingInfo);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return results;
    }

    @Override
    public List<TimeSettingInfo> queryTableData(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        ArrayList<TimeSettingInfo> results = new ArrayList<TimeSettingInfo>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            if ("formscheme_id".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formSchemeKey);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && null != (value = dimensionValueSet.getValue(dimensionName))) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            List<ColumnModelDefine> dimensionColumns = this.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQueryWithRowKey) {
                TimeSettingInfo timeSettingInfo = new TimeSettingInfo();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                String unitId = dimensionValue.getValue(this.tdUtils.getDwMainDimName(formSchemeKey)).toString();
                timeSettingInfo.setUnitId(unitId);
                this.buildTimeSettingInfo(timeSettingInfo, iNvwaDataRow, allColumnModels);
                results.add(timeSettingInfo);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return results;
    }

    @Override
    public TimeSettingInfo queryDeadTime(String formSchemeKey, DimensionValueSet dimensionValueSet, String operator) {
        ArrayList<TimeSettingInfo> results = new ArrayList<TimeSettingInfo>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableCode = "NR_TIME_SETTING_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(tableCode);
        List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            if ("formscheme_id".equals(columnModelDefine.getCode())) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formSchemeKey);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && null != (value = dimensionValueSet.getValue(dimensionName))) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            List<ColumnModelDefine> dimensionColumns = this.getDimensionValueSetColumns(table, allColumnModels);
            for (INvwaDataRow iNvwaDataRow : executeQueryWithRowKey) {
                TimeSettingInfo timeSettingInfo = new TimeSettingInfo();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                String unitId = dimensionValue.getValue(this.tdUtils.getDwMainDimName(formSchemeKey)).toString();
                timeSettingInfo.setUnitId(unitId);
                this.buildTimeSettingInfo(timeSettingInfo, iNvwaDataRow, allColumnModels);
                results.add(timeSettingInfo);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return results.size() == 0 ? null : (TimeSettingInfo)results.get(0);
    }

    private void buildTimeSettingInfo(TimeSettingInfo timeSettingInfo, INvwaDataRow iNvwaDataRow, List<ColumnModelDefine> allColumnModels) {
        block18: for (int j = 0; j < allColumnModels.size(); ++j) {
            ColumnModelDefine columnModel = allColumnModels.get(j);
            Object value = iNvwaDataRow.getValue(columnModel);
            switch (columnModel.getCode()) {
                case "formscheme_id": {
                    String key = value.toString();
                    timeSettingInfo.setFormSchemeKey(key);
                    continue block18;
                }
                case "submit_starttime": {
                    if (value == null || !(value instanceof GregorianCalendar)) continue block18;
                    GregorianCalendar gc = (GregorianCalendar)value;
                    Date time = gc.getTime();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timeStr = sd.format(time);
                    timeSettingInfo.setSubmitStartTime(timeStr);
                    continue block18;
                }
                case "submit_deadlinetime": {
                    if (value == null || !(value instanceof GregorianCalendar)) continue block18;
                    GregorianCalendar gc = (GregorianCalendar)value;
                    Date time = gc.getTime();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timeStr = sd.format(time);
                    timeSettingInfo.setDeadLineTime(timeStr);
                    continue block18;
                }
                case "repay_daealine": {
                    if (value == null || !(value instanceof GregorianCalendar)) continue block18;
                    GregorianCalendar gc = (GregorianCalendar)value;
                    Date time = gc.getTime();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timeStr = sd.format(time);
                    timeSettingInfo.setRepayDeadline(timeStr);
                    continue block18;
                }
                case "operator": {
                    String operator = value.toString();
                    timeSettingInfo.setOperator(operator);
                    continue block18;
                }
                case "operator_unitid": {
                    if (value == null) continue block18;
                    String operatorOfUnitId = value.toString();
                    timeSettingInfo.setOperatorOfUnitId(operatorOfUnitId);
                    continue block18;
                }
                case "unit_level": {
                    int unitLevel = (Integer)value;
                    timeSettingInfo.setUnitLevel(unitLevel);
                    continue block18;
                }
            }
        }
    }

    public List<ColumnModelDefine> getDimensionValueSetColumns(TableModelDefine table, List<ColumnModelDefine> columns) {
        ArrayList<ColumnModelDefine> dimensionColumns = new ArrayList<ColumnModelDefine>();
        String bizKeys = table.getBizKeys();
        String[] bizKeyArray = bizKeys.split(";");
        for (int i = 0; i < bizKeyArray.length; ++i) {
            String bizKey = bizKeyArray[i];
            ColumnModelDefine columnModelDefine = columns.stream().filter(e -> e.getID().equals(bizKey)).findFirst().get();
            dimensionColumns.add(columnModelDefine);
        }
        return dimensionColumns;
    }
}

