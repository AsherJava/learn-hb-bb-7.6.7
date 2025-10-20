/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeFieldMappingProvider
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.sap_s4.fieldmapping;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeFieldMappingProvider;
import com.jiuqi.bde.plugin.sap_s4.BdeSapS4PluginType;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4AssistUtil;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4DefaultAssistPojo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractBdeSapS4FieldMappingProvider
extends AbstractBdeFieldMappingProvider {
    private final Logger logger = LoggerFactory.getLogger(AbstractBdeSapS4FieldMappingProvider.class);
    @Autowired
    private BdeSapS4PluginType pluginType;
    @Autowired
    protected DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private SapS4AssistUtil assistUtil;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected List<SapS4DefaultAssistPojo> getAssistList() {
        return this.assistUtil.getDefaultAssistList();
    }

    protected List<Column> getColumnsByTableName(String dbSchemeCode, String dataSourceCode, String tableName) {
        List<Column> columnsByMetaData = this.getColumnsByMetaData(dbSchemeCode, dataSourceCode, tableName);
        this.logger.info("SAPS4\u6839\u636e\u7269\u7406\u8868\u3010{}\u3011\u83b7\u53d6\u5230\u7684\u5143\u6570\u636e\u4fe1\u606f{}", (Object)tableName, (Object)JsonUtils.writeValueAsString(columnsByMetaData));
        if (!CollectionUtils.isEmpty(columnsByMetaData)) {
            return columnsByMetaData;
        }
        List<Column> columnsByTbOrView = this.getColumnsByTbOrView(dbSchemeCode, dataSourceCode, tableName);
        this.logger.info("SAPS4\u6839\u636e\u5bf9\u8c61\u3010{}\u3011\u67e5\u8be2\u6570\u636e\u5e93\u83b7\u53d6\u5230\u7684\u5143\u6570\u636e\u4fe1\u606f{}", (Object)tableName, (Object)JsonUtils.writeValueAsString(columnsByTbOrView));
        return columnsByTbOrView;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Column> getColumnsByMetaData(String dbSchemeCode, String dataSourceCode, String tableName) {
        ArrayList<Column> columnList = new ArrayList<Column>();
        HashSet<String> columnNameSet = new HashSet<String>();
        Connection connection = null;
        try {
            connection = this.dynamicDataSourceService.getConnection(dataSourceCode);
            ResultSet columns = connection.getMetaData().getColumns(null, dbSchemeCode, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME").toUpperCase();
                if (columnNameSet.contains(columnName)) continue;
                Column column = new Column();
                column.setName(columnName);
                column.setTitle(column.getName());
                columnList.add(column);
                columnNameSet.add(columnName);
            }
        }
        catch (SQLException e) {
            this.logger.error("SAPS4\u6839\u636e\u7269\u7406\u8868\u3010{}\u3011\u83b7\u53d6\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25", (Object)tableName, (Object)e);
        }
        finally {
            this.dataSourceService.closeConnection(dataSourceCode, connection);
        }
        return columnList;
    }

    private List<Column> getColumnsByTbOrView(String dbSchemeCode, String dataSourceCode, String tableName) {
        try {
            String SQL_TMPL = "SELECT * FROM " + tableName;
            String sql = this.dynamicDataSourceService.getPageSql(dataSourceCode, SQL_TMPL, 1, 1);
            return (List)this.dynamicDataSourceService.query(dataSourceCode, sql, null, (ResultSetExtractor)new ResultSetExtractor<List<Column>>(){

                public List<Column> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    ArrayList<Column> columnList = new ArrayList<Column>();
                    HashSet<String> columnNameSet = new HashSet<String>();
                    for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
                        String colName = rs.getMetaData().getColumnLabel(colIdx).toUpperCase();
                        if (columnNameSet.contains(colName)) continue;
                        Column column = new Column();
                        columnNameSet.add(colName);
                        column.setName(colName);
                        column.setTitle(colName);
                        columnList.add(column);
                    }
                    return columnList;
                }
            });
        }
        catch (Exception e) {
            this.logger.error("SAPS4\u6839\u636e\u5bf9\u8c61\u3010{}\u3011\u67e5\u8be2\u6570\u636e\u5e93\u83b7\u53d6\u8868\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25", (Object)tableName, (Object)e);
            return CollectionUtils.newArrayList();
        }
    }

    public Integer showOrder() {
        return 2;
    }
}

