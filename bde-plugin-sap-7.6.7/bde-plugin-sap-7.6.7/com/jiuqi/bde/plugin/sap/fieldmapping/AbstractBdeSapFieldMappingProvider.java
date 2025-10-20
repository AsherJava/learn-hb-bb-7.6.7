/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeFieldMappingProvider
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.apache.commons.io.IOUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.sap.fieldmapping;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.AbstractBdeFieldMappingProvider;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.init.SapDefaultAssistPojo;
import com.jiuqi.bde.plugin.sap.util.SapAssistUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractBdeSapFieldMappingProvider
extends AbstractBdeFieldMappingProvider {
    private final Logger logger = LoggerFactory.getLogger(AbstractBdeSapFieldMappingProvider.class);
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    protected DynamicDataSourceService dynamicDataSourceService;
    @Autowired
    private SapAssistUtil assistUtil;
    static final String TRIM_TEMPLATE = "TRIM(%1$s.%2$s)";

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected List<FieldDTO> convert2Field(String dataSourceCode, String tableName, String tableTitle, List<Column> columns) {
        Assert.isNotEmpty((String)tableName);
        Assert.isNotEmpty((String)tableTitle);
        if (CollectionUtils.isEmpty(columns)) {
            return CollectionUtils.newArrayList();
        }
        String upperTableName = tableName.toUpperCase();
        String upperTableTitle = tableTitle.toUpperCase();
        String virtualTable = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode)).getVirtualTable();
        return columns.stream().map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(String.format(TRIM_TEMPLATE, upperTableName, col.getName()));
            fieldDTO.setTitle(String.format(TRIM_TEMPLATE, upperTableTitle, col.getTitle()));
            fieldDTO.setTableName(virtualTable);
            fieldDTO.setRuleType(RuleType.NONE.getCode());
            fieldDTO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
            fieldDTO.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
            fieldDTO.setIsolationStrategyFixedFlag(Boolean.valueOf(false));
            fieldDTO.setAdvancedSql("SELECT 'ID' AS ID, 'CODE' AS CODE,'NAME' AS NAME FROM " + virtualTable);
            return fieldDTO;
        }).sorted(new Comparator<FieldDTO>(){

            @Override
            public int compare(FieldDTO o1, FieldDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList());
    }

    protected List<SapDefaultAssistPojo> getAssistList() {
        return this.assistUtil.getDefaultAssistList();
    }

    public static String readFileToString(Resource resource, Charset encoding) throws IOException {
        try (BufferedInputStream br = new BufferedInputStream(resource.getInputStream());){
            String string = IOUtils.toString((InputStream)br, (Charset)encoding);
            return string;
        }
    }

    protected List<Column> getColumnsByTableName(String dbSchemeCode, String dataSourceCode, String tableName) {
        List<Column> columnsByMetaData = this.getColumnsByMetaData(dbSchemeCode, dataSourceCode, tableName);
        this.logger.info("SAP\u6839\u636e\u7269\u7406\u8868\u3010{}\u3011\u83b7\u53d6\u5230\u7684\u5143\u6570\u636e\u4fe1\u606f{}", (Object)tableName, (Object)JsonUtils.writeValueAsString(columnsByMetaData));
        if (!CollectionUtils.isEmpty(columnsByMetaData)) {
            return columnsByMetaData;
        }
        List<Column> columnsByTbOrView = this.getColumnsByTbOrView(dbSchemeCode, dataSourceCode, tableName);
        this.logger.info("SAP\u6839\u636e\u5bf9\u8c61\u3010{}\u3011\u67e5\u8be2\u6570\u636e\u5e93\u83b7\u53d6\u5230\u7684\u5143\u6570\u636e\u4fe1\u606f{}", (Object)tableName, (Object)JsonUtils.writeValueAsString(columnsByTbOrView));
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
            this.logger.error("SAP\u6839\u636e\u7269\u7406\u8868\u3010{}\u3011\u83b7\u53d6\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25", (Object)tableName, (Object)e);
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
            this.logger.error("SAP\u6839\u636e\u5bf9\u8c61\u3010{}\u3011\u67e5\u8be2\u6570\u636e\u5e93\u83b7\u53d6\u8868\u5143\u6570\u636e\u4fe1\u606f\u5931\u8d25", (Object)tableName, (Object)e);
            return CollectionUtils.newArrayList();
        }
    }

    public Integer showOrder() {
        return 1;
    }
}

