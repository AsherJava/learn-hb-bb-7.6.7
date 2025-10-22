/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.finalaccountsaudit.common.INvwaExecuteCallBack;
import com.jiuqi.nr.finalaccountsaudit.common.INvwaRowCallBack;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class NvwaDataEngineHelper {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataEngineHelper.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private JdbcTemplate jdbcTempDao;
    private String tableName;
    private Map<String, Object> conditions;
    private String filter;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTempDao.getDataSource());
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public ColumnModelDefine getColumnModelDefine(String columnCode) {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(this.tableName);
        return this.dataModelService.getColumnModelDefineByCode(table.getID(), columnCode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void query(INvwaRowCallBack rcb) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(this.getTableName());
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        for (ColumnModelDefine column : columns) {
            if (this.getConditions() != null && this.getConditions().containsKey(column.getCode())) {
                queryModel.getColumnFilters().put(column, this.getConditions().get(column.getCode()));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        if (StringUtils.isNotEmpty((String)this.getFilter())) {
            queryModel.setFilter(this.getFilter());
        }
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        MemoryDataSet dataRows = null;
        Connection conn = this.getConnection();
        try {
            dataRows = dataAccess.executeQuery(context);
        }
        catch (Exception e) {
            logger.warn(e.getMessage() + e.getStackTrace());
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTempDao.getDataSource());
            }
        }
        rcb.queryForObject(columns, (MemoryDataSet<NvwaQueryColumn>)dataRows);
    }

    public boolean execute(INvwaExecuteCallBack ecb) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(this.getTableName());
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        for (ColumnModelDefine column : columns) {
            if (this.getConditions() != null && this.getConditions().containsKey(column.getCode())) {
                queryModel.getColumnFilters().put(column, this.getConditions().get(column.getCode()));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        if (StringUtils.isNotEmpty((String)this.getFilter())) {
            queryModel.setFilter(this.getFilter());
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaUpdatableDataSet iNvwaUpdatableDataSet = updatableDataAccess.executeQueryForUpdate(context);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            ecb.execute(iNvwaUpdatableDataSet, iNvwaDataUpdator, columns);
            iNvwaDataUpdator.commitChanges(context);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}

