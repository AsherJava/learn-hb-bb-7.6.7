/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.deploy.ManualDeployTableExecutor
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.migration.attachment.business;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.deploy.ManualDeployTableExecutor;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMigrationUpgradeExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentMigrationUpgradeExecutor.class);
    private static final String F_OBJ_KEY = "OBJ_KEY";
    private static final String F_OBJ_PNAME = "OBJ_PNAME";
    private static final String F_OBJ_PVALUE = "OBJ_PVALUE";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ReturnObject execute() {
        Connection connection = this.createConnection();
        try {
            List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
            for (DataScheme dataScheme : allDataScheme) {
                String tableName = "NR_FILE_" + dataScheme.getBizCode();
                TableModelDefine tableModelDefineByName = this.dataModelService.getTableModelDefineByName(tableName);
                if (tableModelDefineByName == null) {
                    boolean result;
                    ManualDeployTableExecutor manualDeployTableExecutor;
                    DesignTableModelDefine oldFilePoolTable = this.designDataModelService.getTableModelDefineByCode("NR_FILE_" + dataScheme.getCode());
                    DesignTableModelDefine newFilePoolTable = this.designDataModelService.getTableModelDefineByCode("NR_FILE_" + dataScheme.getBizCode());
                    if (null != oldFilePoolTable && null == newFilePoolTable) {
                        oldFilePoolTable.setName("NR_FILE_" + dataScheme.getBizCode());
                        oldFilePoolTable.setCode("NR_FILE_" + dataScheme.getBizCode());
                        this.designDataModelService.updateTableModelDefine(oldFilePoolTable);
                        this.dataModelDeployService.deployTableUnCheck(oldFilePoolTable.getID());
                        manualDeployTableExecutor = new ManualDeployTableExecutor();
                        manualDeployTableExecutor.deleteRow(dataScheme);
                    }
                    if (null == (tableModelDefineByName = this.dataModelService.getTableModelDefineByName(tableName)) && !(result = (manualDeployTableExecutor = new ManualDeployTableExecutor()).manuaDdeployFilePoolTable(dataScheme))) {
                        logger.error("\u6570\u636e\u5173\u8054\u8868\u521b\u5efa\u5931\u8d25\uff1a{}", (Object)dataScheme.getCode());
                        continue;
                    }
                }
                Map<String, List<String>> groupKeyAndFileKeys = this.searchFileKeys(connection, tableName);
                List<String> fieldKeys = this.searchTempTableData(connection, dataScheme.getKey());
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, dataScheme.getKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
                NvwaQueryModel queryModel = new NvwaQueryModel();
                TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName("NR_FILE_" + dataScheme.getBizCode());
                List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
                for (ColumnModelDefine filePoolField : filePoolFields) {
                    queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
                }
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
                INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
                List columns = queryModel.getColumns();
                for (String fieldKey : fieldKeys) {
                    this.insertAssociatedTable(queryEnvironment, executorContext, dataAccessContext, dimensionChanger, updatableDataAccess, columns, fieldKey, groupKeyAndFileKeys);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            ReturnObject returnObject = ReturnObject.Error(e.getMessage());
            return returnObject;
        }
        finally {
            this.closeConnection(connection);
            logger.info("\u9644\u4ef6\u5347\u7ea7\u5b8c\u6210");
        }
        return ReturnObject.Success();
    }

    private void insertAssociatedTable(QueryEnvironment queryEnvironment, ExecutorContext executorContext, DataAccessContext dataAccessContext, DimensionChanger dimensionChanger, INvwaUpdatableDataAccess updatableDataAccess, List<NvwaQueryColumn> columns, String fieldKey, Map<String, List<String>> groupKeyAndFileKeys) throws Exception {
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(new DimensionValueSet());
        IDataTable dataTable = dataQuery.executeQuery(executorContext);
        for (int i = 0; i < dataTable.getTotalCount(); ++i) {
            List<String> fileKeys;
            String groupKey = dataTable.getItem(i).getAsString(0);
            if (!StringUtils.isNotEmpty((String)groupKey) || null == (fileKeys = groupKeyAndFileKeys.get(groupKey)) || fileKeys.isEmpty()) continue;
            DimensionValueSet dimensionValueSet = dataTable.getItem(i).getRowKeys();
            for (String fileKey : fileKeys) {
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                String id = UUID.randomUUID().toString();
                for (int j = 0; j < columns.size(); ++j) {
                    if (columns.get(j).getColumnModel().getCode().equals("ID")) {
                        iNvwaDataRow.setValue(j, (Object)id);
                        continue;
                    }
                    if (columns.get(j).getColumnModel().getCode().equals("GROUPKEY")) {
                        iNvwaDataRow.setValue(j, (Object)groupKey);
                        continue;
                    }
                    if (columns.get(j).getColumnModel().getCode().equals("FILEKEY")) {
                        iNvwaDataRow.setValue(j, (Object)fileKey);
                        continue;
                    }
                    if (columns.get(j).getColumnModel().getCode().equals("FIELD_KEY")) {
                        iNvwaDataRow.setValue(j, (Object)fieldKey);
                        continue;
                    }
                    if (columns.get(j).getColumnModel().getCode().equals("ISDELETE")) {
                        iNvwaDataRow.setValue(j, (Object)"0");
                        continue;
                    }
                    String dimensionName = dimensionChanger.getDimensionName(columns.get(j).getColumnModel());
                    iNvwaDataRow.setValue(j, dimensionValueSet.getValue(dimensionName));
                }
            }
        }
        iNvwaDataUpdator.commitChanges(dataAccessContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, List<String>> searchFileKeys(Connection connection, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT oss.").append(F_OBJ_KEY).append(", oss.").append(F_OBJ_PVALUE);
        sql.append(" FROM OSS_B_").append("JTABLEAREA").append("_M oss ");
        sql.append(" LEFT JOIN ").append(tableName).append(" t ON oss.").append(F_OBJ_PVALUE).append("= t.").append("GROUPKEY").append(" AND oss.").append(F_OBJ_KEY).append("= t.").append("FILEKEY");
        sql.append(" WHERE oss.").append(F_OBJ_PNAME).append("='").append("fileGroupKey").append("'");
        sql.append(" AND t.").append("ID").append(" IS NULL");
        HashMap<String, List<String>> groupKeyAndFileKeys = new HashMap<String, List<String>>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql.toString());
            try (ResultSet rs = statement.executeQuery();){
                while (rs.next()) {
                    String groupKey = rs.getString(F_OBJ_PVALUE);
                    String fileKey = rs.getString(F_OBJ_KEY);
                    List fileKeys = (List)groupKeyAndFileKeys.get(groupKey);
                    if (null == fileKeys) {
                        ArrayList<String> fileKeyss = new ArrayList<String>();
                        fileKeyss.add(fileKey);
                        groupKeyAndFileKeys.put(groupKey, fileKeyss);
                        continue;
                    }
                    if (fileKeys.contains(fileKey)) continue;
                    fileKeys.add(fileKey);
                }
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.close(statement);
        }
        return groupKeyAndFileKeys;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> searchTempTableData(Connection connection, String dataSchemeKey) {
        StringBuilder sql = new StringBuilder();
        sql.append("select df.df_key ");
        sql.append("from nr_datascheme_field df ");
        sql.append("left join nr_param_datalink dl ");
        sql.append("on df.df_key = dl.dl_field_key ");
        sql.append("where df.df_ds_key = '").append(dataSchemeKey).append("' ");
        sql.append("and df.df_datatype = '5006' ");
        sql.append("and length(dl.dl_key) > 0 ");
        sql.append("group by df.df_key");
        ArrayList<String> fieldKeys = new ArrayList<String>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql.toString());
            try (ResultSet rs = statement.executeQuery();){
                while (rs.next()) {
                    fieldKeys.add(rs.getString("df_key"));
                }
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.close(statement);
        }
        return fieldKeys;
    }

    public Connection createConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    public void closeConnection(Connection connection) {
        if (null != connection) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    private void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

