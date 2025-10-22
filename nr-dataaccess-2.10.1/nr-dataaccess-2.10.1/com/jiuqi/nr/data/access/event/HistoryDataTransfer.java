/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.data.access.event;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.event.StatusTableCreateUtil;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class HistoryDataTransfer
implements CustomClassExecutor {
    private IRunTimeViewController runTimeViewController;
    private DataSchemeService dataSchemeService;
    private IDatabase database;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private StatusTableCreateUtil statusTableCreateUtil;
    private IEntityMetaService iEntityMetaService;
    private DataModelService dataModelService;
    private IDesignDataSchemeService designDataSchemeService;

    public void execute(DataSource dataSource) throws Exception {
        this.executeTransfer(dataSource);
    }

    public void executeTransfer(DataSource dataSource) throws Exception {
        Connection connectin = DataSourceUtils.getConnection((DataSource)dataSource);
        this.init(connectin);
        this.logger.info("===========================\u5f00\u59cb\u8fc1\u79fb\u72b6\u6001\u8868\u76f8\u5173\u5386\u53f2\u6570\u636e=========================");
        try {
            this.transfer(connectin);
            this.logger.info("===========================\u8fc1\u79fb\u72b6\u6001\u8868\u76f8\u5173\u5386\u53f2\u6570\u636e\u5b8c\u6210!=========================");
        }
        catch (Exception e) {
            throw new AccessException("\u8fc1\u79fb\u72b6\u6001\u8868\u6570\u636e\u5f02\u5e38", e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connectin, (DataSource)dataSource);
        }
    }

    private void init(Connection connectin) throws SQLException {
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataSchemeService = (DataSchemeService)BeanUtil.getBean(DataSchemeService.class);
        this.database = DatabaseManager.getInstance().findDatabaseByConnection(connectin);
        this.statusTableCreateUtil = (StatusTableCreateUtil)BeanUtil.getBean(StatusTableCreateUtil.class);
        this.iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.designDataSchemeService = (IDesignDataSchemeService)BeanUtil.getBean(IDesignDataSchemeService.class);
    }

    private List<Future> transfer(Connection connectin) {
        List tasks = this.runTimeViewController.getAllTaskDefines();
        Map<String, List<TaskDefine>> taskGroup = tasks.stream().collect(Collectors.groupingBy(TaskDefine::getDataScheme));
        ArrayList<Future> resFture = new ArrayList<Future>();
        this.logger.info("===============\u6309\u6570\u636e\u65b9\u6848\u8fc1\u79fb\u72b6\u6001\u8868\u6570\u636e================");
        for (Map.Entry<String, List<TaskDefine>> entry : taskGroup.entrySet()) {
            List<TaskDefine> taskDefines = entry.getValue();
            String dataSchemeKey = entry.getKey();
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
            if (dataScheme == null) {
                this.logger.error("\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\uff0c\u6ca1\u6709\u627e\u5230\u6570\u636e\u65b9\u6848\uff0c\u65b9\u6848Key\uff1a".concat(dataSchemeKey));
                continue;
            }
            this.executeTask(taskDefines, dataScheme, connectin);
        }
        return resFture;
    }

    private void executeTask(List<TaskDefine> taskDefines, DataSchemeDTO dataScheme, Connection connectin) {
        Map<String, String> tableMap = this.initTable(dataScheme);
        String dimKeys = ((TaskDefine)taskDefines.stream().findFirst().get()).getDims();
        IEntityDefine otherDim = null;
        ArrayList<String> dimNames = new ArrayList<String>();
        if (Objects.nonNull(dimKeys)) {
            String[] dimAry;
            for (String key : dimAry = dimKeys.split(";")) {
                otherDim = this.iEntityMetaService.queryEntity(key);
                if (otherDim == null) continue;
                dimNames.add(otherDim.getDimensionName().toUpperCase());
            }
        }
        for (TaskDefine taskDefine : taskDefines) {
            List<FormSchemeDefine> formSchemes = this.queryFormSchemes(taskDefine.getKey());
            this.transferData(formSchemes, connectin, tableMap, dimNames, dataScheme.getKey());
        }
        this.logger.info("\u6570\u636e\u65b9\u6848\u3010{}\u3011\u4e0b\u7684\u76f8\u5173\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\u5b8c\u6210", (Object)dataScheme.getTitle());
    }

    private Map<String, String> initTable(DataSchemeDTO dataScheme) {
        HashMap<String, String> tableMap = new HashMap<String, String>();
        String dataSchemeKey = dataScheme.getKey();
        try {
            String dataPublishTable = TableConsts.getSysTableName("NR_DATAPUBLISH_%s", dataScheme.getBizCode());
            this.statusTableCreateUtil.initDataPublishDeploy(dataSchemeKey, (DataScheme)dataScheme, false);
            tableMap.put("dataPublishTable", dataPublishTable);
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01dataSchem:".concat(dataSchemeKey), e);
        }
        try {
            String formLockTable = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK", dataScheme.getBizCode());
            this.statusTableCreateUtil.initFormLockDeploy(dataSchemeKey, (DataScheme)dataScheme, false);
            tableMap.put("formLockTable", formLockTable);
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01dataSchem:".concat(dataSchemeKey), e);
        }
        try {
            String unitStateTable = TableConsts.getSysTableName("NR_UNITSTATE_%s", dataScheme.getBizCode());
            this.statusTableCreateUtil.initUnitStateDeploy(dataSchemeKey, (DataScheme)dataScheme, false);
            tableMap.put("unitStateTable", unitStateTable);
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01dataSchem:".concat(dataSchemeKey), e);
        }
        try {
            String secretTable = TableConsts.getSysTableName("NR_SECRETLEVEL_%s", dataScheme.getBizCode());
            this.statusTableCreateUtil.initSecretDeploy(dataSchemeKey, (DataScheme)dataScheme, false);
            tableMap.put("secretTable", secretTable);
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01dataSchem:".concat(dataSchemeKey), e);
        }
        return tableMap;
    }

    private List<String> queryReportDimNames(String dataSchemeKey) {
        ArrayList<String> dimNames = new ArrayList<String>();
        List dimensions = this.designDataSchemeService.getReportDimension(dataSchemeKey);
        for (DataDimension dimension : dimensions) {
            IEntityDefine reportDim = this.iEntityMetaService.queryEntity(dimension.getDimKey());
            if (reportDim == null) continue;
            dimNames.add(reportDim.getDimensionName().toUpperCase());
        }
        return dimNames;
    }

    private void transferData(List<FormSchemeDefine> formSchemeDefines, Connection connection, Map<String, String> tableMap, List<String> allDims, String dataSchemeKey) {
        List<String> reportDimNames = this.queryReportDimNames(dataSchemeKey);
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            try {
                String dataPublish = tableMap.get("dataPublishTable");
                if (dataPublish == null) {
                    this.logger.warn(String.format("\u672a\u627e\u5230\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868,\u62a5\u8868\u65b9\u6848\uff1a%s", formSchemeDefine.getKey()));
                    continue;
                }
                this.transferDataPublish(formSchemeDefine, dataPublish, connection, reportDimNames);
                String formLockTable = tableMap.get("formLockTable");
                if (formLockTable == null) {
                    this.logger.warn(String.format("\u672a\u627e\u5230\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868,\u62a5\u8868\u65b9\u6848\uff1a%s", formSchemeDefine.getKey()));
                    continue;
                }
                this.transferFormLock(formSchemeDefine, formLockTable, connection, allDims);
                String secretTable = tableMap.get("secretTable");
                if (secretTable == null) {
                    this.logger.warn(String.format("\u672a\u627e\u5230\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868,\u62a5\u8868\u65b9\u6848\uff1a%s", formSchemeDefine.getKey()));
                    continue;
                }
                this.transferSecret(formSchemeDefine, secretTable, connection, allDims);
                String endUploadTable = tableMap.get("unitStateTable");
                if (endUploadTable == null) {
                    this.logger.warn(String.format("\u672a\u627e\u5230\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868,\u62a5\u8868\u65b9\u6848\uff1a%s", formSchemeDefine.getKey()));
                    continue;
                }
                this.transferUnitState(formSchemeDefine, endUploadTable, connection, reportDimNames);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage());
            }
        }
    }

    private void transferDataPublish(final FormSchemeDefine formSchemeDefine, String newTable, final Connection connection, List<String> dimNames) {
        String oldTableName = String.format("%s%s", "DE_DAPU_", formSchemeDefine.getFormSchemeCode());
        try {
            TableModelDefine oldTable = this.dataModelService.getTableModelDefineByName(oldTableName);
            if (oldTable == null) {
                this.logger.info("\u672a\u627e\u5230\u5386\u53f2-\u6570\u636e\u53d1\u5e03\u8868");
                return;
            }
            List fields = this.dataModelService.getColumnModelDefinesByTable(oldTable.getID());
            HashSet<String> groupCols = new HashSet<String>();
            ArrayList<String> newTableFields = new ArrayList<String>(){
                {
                    this.add("DP_ID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add("DP_FORMSCHEMEKEY");
                    this.add("DP_FORMKEY");
                    this.add("DP_ISPUBLISH");
                    this.add("DP_USER");
                    this.add("DP_UPDATETIME");
                }
            };
            ArrayList<String> oldFields = new ArrayList<String>(){
                {
                    this.add(String.format("min(%s)", "DAPU_RECID"));
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add(String.format("'%s' as %s", formSchemeDefine.getKey(), "DP_FORMSCHEMEKEY"));
                    this.add(String.format("min(%s)", "DAPU_FORMKEY"));
                    this.add(String.format("min(%s)", "DAPU_ISPUBLISH"));
                    this.add(String.format("'%s' as %s", ObjectUtils.isEmpty(NpContextHolder.getContext().getUserId()) ? "" : NpContextHolder.getContext().getUserId(), "DP_USER"));
                    this.add(DataEngineUtil.buildDateValueSql((IDatabase)HistoryDataTransfer.this.database, (Object)new Date(), (Connection)connection) + " as " + "DP_UPDATETIME");
                }
            };
            groupCols.add("MDCODE");
            groupCols.add("PERIOD");
            dimNames.forEach(groupCols::add);
            this.addOtherDim(fields, (List<String>)newTableFields, (List<String>)oldFields, dimNames);
            this.batchInsert(connection, newTable, oldTableName, (List<String>)newTableFields, (List<String>)oldFields, groupCols);
        }
        catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\u5f02\u5e38\uff01").append("\u3010taleName\u3011= ").append(newTable);
            buf.append("\uff0c\u3010oldTableName\u3011= ").append(oldTableName).append(" \u3010cause\u3011= ").append(e.getMessage());
            throw new AccessException(buf.toString());
        }
    }

    private void transferFormLock(final FormSchemeDefine formSchemeDefine, String newTable, final Connection connection, List<String> dimNames) throws Exception {
        String oldTableName = String.format("%s%s", "SYS_FMLK_", formSchemeDefine.getFormSchemeCode());
        try {
            TableModelDefine oldTable = this.dataModelService.getTableModelDefineByName(oldTableName);
            if (oldTable == null) {
                this.logger.info("\u672a\u627e\u5230\u5386\u53f2-\u9501\u5b9a\u72b6\u6001\u8868");
                return;
            }
            List fields = this.dataModelService.getColumnModelDefinesByTable(oldTable.getID());
            ArrayList<String> newTableFields = new ArrayList<String>(){
                {
                    this.add("FL_ID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add("FL_FORMSCHEMEKEY");
                    this.add("FL_FORMKEY");
                    this.add("FL_ISLOCK");
                    this.add("FL_USER");
                    this.add("FL_UPDATETIME");
                }
            };
            ArrayList<String> oldFields = new ArrayList<String>(){
                {
                    this.add("FMLK_RECID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add(String.format("'%s' as %s", formSchemeDefine.getKey(), "FCK"));
                    this.add("FMLK_FORMKEY");
                    this.add("FMLK_ISLOCK");
                    this.add("FMLK_USER");
                    this.add(DataEngineUtil.buildDateValueSql((IDatabase)HistoryDataTransfer.this.database, (Object)new Date(), (Connection)connection) + " as " + "FL_UPDATETIME");
                }
            };
            this.addOtherDim(fields, (List<String>)newTableFields, (List<String>)oldFields, dimNames);
            this.batchInsert(connection, newTable, oldTableName, (List<String>)newTableFields, (List<String>)oldFields, new HashSet<String>());
        }
        catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("\u9501\u5b9a\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\u5f02\u5e38\uff01").append("\u3010taleName\u3011= ").append(newTable);
            buf.append("\uff0c\u3010oldTableName\u3011= ").append(oldTableName).append(" \u3010cause\u3011= ").append(e.getMessage());
            throw new AccessException(buf.toString());
        }
    }

    private void transferSecret(final FormSchemeDefine formSchemeDefine, String newTable, Connection connection, List<String> dimNames) throws Exception {
        String oldTableName = String.format("%s%s", "NR_SECRETLEVEL_", formSchemeDefine.getFormSchemeCode());
        try {
            TableModelDefine oldTable = this.dataModelService.getTableModelDefineByName(oldTableName);
            if (oldTable == null) {
                this.logger.info("\u672a\u627e\u5230\u5386\u53f2-\u5bc6\u7ea7\u72b6\u6001\u8868");
                return;
            }
            ArrayList<String> newTableFields = new ArrayList<String>(){
                {
                    this.add("SL_ID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add("SL_FORMSCHEMEKEY");
                    this.add("SL_FORMKEY");
                    this.add("SL_LEVEL");
                    this.add("SL_USER");
                    this.add("SL_UPDATETIME");
                }
            };
            ArrayList<String> oldFields = new ArrayList<String>(){
                {
                    this.add("SL_ID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add(String.format("'%s' as %s", formSchemeDefine.getKey(), "SL_FORMSCHEMEKEY"));
                    this.add(HistoryDataTransfer.this.buildNvalSql("SL_FORMKEY", "00000000-0000-0000-0000-000000000000"));
                    this.add("SL_LEVEL");
                    this.add(String.format("'%s' as %s", ObjectUtils.isEmpty(NpContextHolder.getContext().getUserId()) ? "" : NpContextHolder.getContext().getUserId(), "SL_USER"));
                    this.add("SL_UPDATETIME");
                }
            };
            this.batchInsert(connection, newTable, oldTableName, (List<String>)newTableFields, (List<String>)oldFields, new HashSet<String>());
        }
        catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("\u5bc6\u7ea7\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\u5f02\u5e38\uff01").append("\u3010taleName\u3011= ").append(newTable);
            buf.append("\uff0c\u3010oldTableName\u3011= ").append(oldTableName).append(" \u3010cause\u3011= ").append(e.getMessage());
            throw new AccessException(buf.toString());
        }
    }

    private void transferUnitState(final FormSchemeDefine formSchemeDefine, String newTable, Connection connection, List<String> dimNames) throws Exception {
        String oldTableName = String.format("%s%s", "SYS_STATE_", formSchemeDefine.getFormSchemeCode());
        try {
            TableModelDefine oldTable = this.dataModelService.getTableModelDefineByName(oldTableName);
            if (oldTable == null) {
                this.logger.info("\u672a\u627e\u5230\u5386\u53f2-\u7ec8\u6b62\u586b\u62a5\u72b6\u6001\u8868");
                return;
            }
            ArrayList<String> newTableFields = new ArrayList<String>(){
                {
                    this.add("US_ID");
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add("US_FORMSCHEMEKEY");
                    this.add("US_STATE");
                    this.add("US_USER");
                    this.add("US_UPDATETIME");
                }
            };
            ArrayList<String> oldFields = new ArrayList<String>(){
                {
                    this.add(DataEngineUtil.buildcreateUUIDSql((IDatabase)HistoryDataTransfer.this.database, (boolean)false));
                    this.add("MDCODE");
                    this.add("PERIOD");
                    this.add(String.format("'%s' as %s", formSchemeDefine.getKey(), "US_FORMSCHEMEKEY"));
                    this.add("S_STATE");
                    this.add("S_USERID");
                    this.add("CREATETETIME");
                }
            };
            this.batchInsert(connection, newTable, oldTableName, (List<String>)newTableFields, (List<String>)oldFields, new HashSet<String>());
        }
        catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("\u7ec8\u6b62\u586b\u62a5\u8868\u6570\u636e\u8fc1\u79fb\u5f02\u5e38\uff01").append("\u3010taleName\u3011= ").append(newTable);
            buf.append("\uff0c\u3010oldTableName\u3011= ").append(oldTableName).append(" \u3010cause\u3011= ").append(e.getMessage());
            throw new AccessException(buf.toString());
        }
    }

    private List<FormSchemeDefine> queryFormSchemes(String taskKey) {
        try {
            List formSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            return formSchemes;
        }
        catch (Exception e) {
            throw new AccessException("\u72b6\u6001\u5f15\u64ce\u6570\u636e\u8868\u8fc1\u79fb-\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u5931\u8d25");
        }
    }

    private void addOtherDim(List<ColumnModelDefine> fieldDefines, List<String> newTableFields, List<String> oldFields, List<String> dimNames) {
        if (CollectionUtils.isEmpty(dimNames)) {
            return;
        }
        for (String dimName : dimNames) {
            Optional<ColumnModelDefine> field = fieldDefines.stream().filter(e -> e.getCode().toUpperCase().equals(dimName)).findAny();
            if (!field.isPresent()) continue;
            String fieldName = field.get().getCode();
            newTableFields.add(fieldName);
            oldFields.add(fieldName);
        }
    }

    private void batchInsert(Connection connection, String newTable, String oldTable, List<String> newTableFileds, List<String> oldTableFields, Set<String> groupCols) throws SQLException {
        this.logger.info("\u72b6\u6001\u8868\u6570\u636e\u8fc1\u79fb\uff0c\u3010{}\u3011->\u3010{}\u3011", (Object)oldTable, (Object)newTable);
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ").append(newTable);
        sql.append("(");
        sql.append(StringUtils.join(newTableFileds, (String)","));
        sql.append(")");
        sql.append(" select ");
        sql.append(StringUtils.join(oldTableFields, (String)","));
        sql.append(" from ").append(oldTable);
        if (!CollectionUtils.isEmpty(groupCols)) {
            sql.append(" group by ");
            groupCols.forEach(col -> sql.append((String)col).append(","));
            sql.setLength(sql.length() - 1);
        }
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());){
            statement.executeUpdate();
            this.logger.info("\u8fc1\u79fbSQL:" + sql);
        }
    }

    private String buildNvalSql(String filed, String defultValue) {
        String nvl = this.database.getDescriptor().getNVLName();
        return String.format("%s(%s,'%s')", nvl, filed, defultValue);
    }
}

