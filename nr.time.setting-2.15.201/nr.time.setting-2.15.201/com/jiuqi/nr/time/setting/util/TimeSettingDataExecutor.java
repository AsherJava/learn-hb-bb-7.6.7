/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentity.entity.DataEntityType
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.util.TimeSettingObserver;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class TimeSettingDataExecutor
implements CustomClassExecutor {
    Logger logger = LoggerFactory.getLogger(TimeSettingDataExecutor.class);
    private static final String TABLE_UNIT_TIME = "nr_unit_set_time";
    private static final String COL_FORMSCHEME_ID = "formscheme_id";
    private static final String COL_PERIOD = "period";
    private static final String COL_UNIT_ID = "unit_id";
    private static final String COL_OPERATOR = "operator";
    private static final String COL_SUBMIT_STARTTIME = "submit_starttime";
    private static final String COL_DEADLINETIME = "submit_deadlinetime";
    private static final String COL_REPAY_DEADLINE = "repay_deadline";
    private static final String COL_OPERATOR_UNITID = "operator_unitid";
    private static final String COL_CREATETIME = "createtime";
    private static final String COL_UPDATETIME = "updatetime";
    private static final String COL_UNIT_LEVEL = "unit_level";
    private JdbcTemplate jdbcTemplate;
    private IDesignTimeViewController designTimeViewController;
    private TimeSettingObserver timeSettingObserver;
    private IFormSchemeService formSchemeService;
    private IRunTimeViewController runTimeViewController;
    private DataEntityService dataEntityService;
    private IDimensionProvider dimensionProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private DataModelService dataModelService;
    private DataEngineAdapter dataEngineAdapter;
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    public void execute(DataSource dataSource) throws Exception {
        try {
            this.init();
            this.logger.info("===========================\u5f00\u59cb\u8fc1\u79fb\u586b\u62a5\u65f6\u95f4\u8868\u76f8\u5173\u5386\u53f2\u6570\u636e=========================");
            List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
            for (DesignTaskDefine designTaskDefine : allTaskDefines) {
                List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine formScheme : formSchemes) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\u586b\u62a5\u65f6\u95f4\u8868\u521b\u5efaNR_TIME_SETTING_" + formScheme.getFormSchemeCode() + ";");
                    this.timeSettingObserver.deployTable(designTaskDefine, (FormSchemeDefine)formScheme);
                    sb.append("\u5386\u53f2\u586b\u62a5\u65f6\u95f4\u8868nr_unit_set_time\u6570\u636e,\u8fc1\u79fb\u81f3NR_TIME_SETTING_" + formScheme.getFormSchemeCode() + ",");
                    this.moveData((FormSchemeDefine)formScheme, designTaskDefine.getDw(), sb);
                    this.logger.info(sb.toString());
                }
            }
            this.logger.info("===========================\u7ed3\u675f\u8fc1\u79fb\u586b\u62a5\u65f6\u95f4\u8868\u76f8\u5173\u5386\u53f2\u6570\u636e=========================");
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u62a5\u9519", e);
        }
    }

    private void init() {
        this.jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
        this.designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
        this.timeSettingObserver = (TimeSettingObserver)BeanUtil.getBean(TimeSettingObserver.class);
        this.formSchemeService = (IFormSchemeService)BeanUtil.getBean(IFormSchemeService.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataEntityService = (DataEntityService)BeanUtil.getBean(DataEntityService.class);
        this.dimensionProvider = (IDimensionProvider)BeanUtil.getBean(IDimensionProvider.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.dataEngineAdapter = (DataEngineAdapter)BeanUtil.getBean(DataEngineAdapter.class);
        this.iNvwaDataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
    }

    private void moveData(FormSchemeDefine formScheme, String dw, StringBuilder sb) {
        try {
            ArrayList<TimeSettingInfo> deadlineInfos = new ArrayList<TimeSettingInfo>();
            List<TimeSettingInfo> hisData = this.queryHisData(formScheme.getKey());
            for (TimeSettingInfo timeSettingInfo : hisData) {
                String unitId = timeSettingInfo.getUnitId();
                String period = timeSettingInfo.getPeriod();
                List<DimensionValueSet> dimesions = this.appendReportDimension(formScheme, unitId, period, dw);
                for (DimensionValueSet dimension : dimesions) {
                    TimeSettingInfo timeSetting = new TimeSettingInfo();
                    timeSetting.setDimensionValueSet(dimension);
                    timeSetting.setFormSchemeKey(timeSettingInfo.getFormSchemeKey());
                    timeSetting.setOperator(timeSettingInfo.getOperator());
                    timeSetting.setOperatorOfUnitId(timeSettingInfo.getOperatorOfUnitId());
                    timeSetting.setSubmitStartTime(timeSettingInfo.getSubmitStartTime());
                    timeSetting.setDeadLineTime(timeSettingInfo.getDeadLineTime());
                    timeSetting.setRepayDeadline(timeSettingInfo.getRepayDeadline());
                    timeSetting.setUnitLevel(timeSettingInfo.getUnitLevel());
                    timeSetting.setCreateTime(timeSettingInfo.getCreateTime());
                    timeSetting.setUpdateTime(timeSettingInfo.getUpdateTime());
                    deadlineInfos.add(timeSetting);
                }
            }
            sb.append("\u8fc1\u79fb\u5355\u4f4d\u4e2a\u6570:" + hisData.size() + ";");
            this.saveOrUpdateDeadlineInfo(deadlineInfos, formScheme);
            sb.append("\u63d2\u5165\u6570\u636e\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error("\u586b\u62a5\u65f6\u95f4\u8868NR_TIME_SETTING_" + formScheme.getFormSchemeCode() + "\u8fc1\u79fb\u6570\u636e\u62a5\u9519", e);
        }
    }

    private List<TimeSettingInfo> queryHisData(String formSchemeKey) {
        List<TimeSettingInfo> deadTimeResultList = new ArrayList<TimeSettingInfo>();
        try {
            String sql = "SELECT * FROM nr_unit_set_time t WHERE t.formscheme_id = ?";
            deadTimeResultList = this.jdbcTemplate.query(sql, (rs, row) -> this.createData(rs), new Object[]{formSchemeKey}).stream().collect(Collectors.toList());
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u67e5\u8be2\u62a5\u9519", e);
        }
        return deadTimeResultList;
    }

    private TimeSettingInfo createData(ResultSet rs) {
        TimeSettingInfo setTimeResult = new TimeSettingInfo();
        try {
            Timestamp updateTime;
            Timestamp repayDeadline;
            Timestamp submitStarttime;
            setTimeResult.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
            setTimeResult.setPeriod(rs.getString(COL_PERIOD));
            setTimeResult.setUnitId(rs.getString(COL_UNIT_ID));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp time = rs.getTimestamp(COL_DEADLINETIME);
            if (time != null) {
                Date date2 = new Date(time.getTime());
                setTimeResult.setDeadLineTime(sdf.format(date2));
            }
            if ((submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME)) != null) {
                Date submitDate = new Date(submitStarttime.getTime());
                setTimeResult.setSubmitStartTime(sdf.format(submitDate));
            }
            if ((repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE)) != null) {
                Date repayDate = new Date(repayDeadline.getTime());
                setTimeResult.setRepayDeadline(sdf.format(repayDate));
            }
            setTimeResult.setOperator(rs.getString(COL_OPERATOR));
            setTimeResult.setOperatorOfUnitId(rs.getString(COL_OPERATOR_UNITID));
            setTimeResult.setUnitLevel(rs.getInt(COL_UNIT_LEVEL));
            Timestamp createTime = rs.getTimestamp(COL_CREATETIME);
            if (createTime != null) {
                Date createDate = new Date(createTime.getTime());
                setTimeResult.setCreateTime(sdf.format(createDate));
            }
            if ((updateTime = rs.getTimestamp(COL_UPDATETIME)) != null) {
                Date updateDate = new Date(updateTime.getTime());
                setTimeResult.setUpdateTime(sdf.format(updateDate));
            }
        }
        catch (Exception e) {
            this.logger.error("\u7ec4\u88c5\u6570\u636e\u62a5\u9519");
        }
        return setTimeResult;
    }

    public void saveOrUpdateDeadlineInfo(List<TimeSettingInfo> deadlineInfos, FormSchemeDefine formScheme) {
        if (deadlineInfos == null || deadlineInfos.size() == 0) {
            return;
        }
        try {
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
            INvwaUpdatableDataSet executeQuery = updatableDataAccess.executeQueryForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
            for (TimeSettingInfo deadlineInfo : deadlineInfos) {
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
                            appendRow.setValue(index, (Object)deadlineInfo.getCreateTime());
                            break;
                        }
                        case "updatetime": {
                            appendRow.setValue(index, (Object)deadlineInfo.getUpdateTime());
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
            this.logger.error("\u66f4\u65b0\u6570\u636e\u62a5\u9519", e);
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

    private List<DimensionValueSet> appendReportDimension(FormSchemeDefine formScheme, String unitId, String period, String dw) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(unitId);
        List<Map<String, DimensionValue>> reportDimension = this.setReportDimension(formScheme, unitIds, period, dw);
        for (Map<String, DimensionValue> map : reportDimension) {
            DimensionValueSet dimension = new DimensionValueSet();
            for (Map.Entry<String, DimensionValue> dim : map.entrySet()) {
                String key = dim.getKey();
                DimensionValue dimValue = dim.getValue();
                dimension.setValue(key, (Object)dimValue.getValue());
            }
            dims.add(dimension);
        }
        return dims;
    }

    private List<Map<String, DimensionValue>> setReportDimension(FormSchemeDefine formScheme, List<String> unitIds, String period, String dw) {
        Map<String, List<String>> reportDimensionValue = this.getReportDimValueofDimName(formScheme.getKey(), period);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (reportDimensionValue != null && reportDimensionValue.size() > 0) {
            for (Map.Entry<String, List<String>> value : reportDimensionValue.entrySet()) {
                dimensionValueSet.setValue(value.getKey(), value.getValue());
            }
        }
        String tableName = this.dimensionProvider.getDimensionNameByEntityId(dw);
        dimensionValueSet.setValue(tableName, unitIds);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionSet);
        return dimensionSetList;
    }

    private Map<String, List<String>> getReportDimValueofDimName(String formSchemeKey, String period) {
        HashMap<String, List<String>> valueMap = new HashMap<String, List<String>>();
        List reportDimensionKey = this.formSchemeService.getReportDimensionKey(formSchemeKey);
        for (String entityKey : reportDimensionKey) {
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(entityKey);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            try {
                List keys;
                ExecutorContext executorContext = this.executorContext(formSchemeKey);
                IDataEntity iEntityTable = this.dataEntityService.getIEntityTable(entityView, executorContext, dimensionValueSet, formSchemeKey);
                DataEntityType type = iEntityTable.type();
                IDataEntityRow allRow = iEntityTable.getAllRow();
                if (allRow == null) continue;
                if (DataEntityType.DataEntity.equals((Object)type)) {
                    List rowList = allRow.getRowList();
                    keys = rowList.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                    valueMap.put(dimensionName, keys);
                    continue;
                }
                if (!DataEntityType.DataEntityAdjust.equals((Object)type)) continue;
                List adjustPeriods = allRow.getAdjustPeriod();
                keys = adjustPeriods.stream().map(e -> e.getCode()).collect(Collectors.toList());
                if (!"ADJUST".equals(entityKey)) continue;
                valueMap.put(entityKey, keys);
            }
            catch (Exception e2) {
                this.logger.error(e2.getMessage(), e2);
            }
        }
        return valueMap;
    }

    private ExecutorContext executorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(new DimensionValueSet());
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }
}

