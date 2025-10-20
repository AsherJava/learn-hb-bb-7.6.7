/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.jdialect.Type
 *  com.jiuqi.va.paramsync.domain.VaParamTableColumnModel
 *  com.jiuqi.va.paramsync.domain.VaParamTableModel
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.event.VaParamSyncTableCacheEvent
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.paramsync.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.jdialect.Type;
import com.jiuqi.va.paramsync.domain.VaParamTableColumnModel;
import com.jiuqi.va.paramsync.domain.VaParamTableModel;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.event.VaParamSyncTableCacheEvent;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.service.VaParamSyncTableService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class VaParamSyncTableResourcesTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger logger = LoggerFactory.getLogger(VaParamSyncTableResourcesTransferModule.class);
    private Boolean tableSyncExport;
    @Autowired
    private VaParamSyncTableService vaParamSyncTableService;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public String getName() {
        return "VaParamSyncTableResources";
    }

    public String getTitle() {
        return "\u6574\u8868\u8d44\u6e90";
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName("default");
        category.setTitle("\u9ed8\u8ba4");
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void importModelInfo(String category, String info) {
        Map tableDataMaps;
        if (!StringUtils.hasText(info)) {
            logger.warn("info\u4e3a\u7a7a");
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            tableDataMaps = (Map)objectMapper.readValue(info, (TypeReference)new TypeReference<Map<String, List<Map<String, Object>>>>(){});
        }
        catch (JsonProcessingException e) {
            logger.error("json\u89e3\u6790\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            return;
        }
        if (CollectionUtils.isEmpty(tableDataMaps)) {
            logger.warn("tableDataMaps\u4e3a\u7a7a");
            return;
        }
        List<VaParamTableSyncDTO> tableParams = this.vaParamSyncTableService.getTableParams();
        if (CollectionUtils.isEmpty(tableParams)) {
            logger.warn("tableParams\u4e3a\u7a7a");
            return;
        }
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        try {
            for (Map.Entry entry : tableDataMaps.entrySet()) {
                String tableNameNodeId = (String)entry.getKey();
                String[] split = tableNameNodeId.split("\\|");
                String nodeId = split[1];
                if (!StringUtils.hasText(nodeId)) {
                    logger.error("\u5f53\u524dnodeId\u4e3a\u7a7a{}", (Object)tableNameNodeId);
                    continue;
                }
                List dataList = (List)entry.getValue();
                if (CollectionUtils.isEmpty(dataList)) continue;
                this.insertIntoTable(tableParams, tableNameNodeId, dataList, commonDao, sqlSession);
            }
        }
        catch (Exception e) {
            logger.error("\u6574\u8868\u8d44\u6e90\u5bfc\u5165\u6570\u636e\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
        }
        finally {
            sqlSession.close();
        }
    }

    private void insertIntoTable(List<VaParamTableSyncDTO> tableParams, String tableNameNodeId, List<Map<String, Object>> dataList, CommonDao commonDao, SqlSession sqlSession) {
        String[] split = tableNameNodeId.split("\\|");
        String nodeId = split[1];
        String tableName = split[0];
        List tempList = tableParams.stream().filter(x -> nodeId.equalsIgnoreCase(x.getName())).filter(x -> !CollectionUtils.isEmpty(x.getTables())).collect(Collectors.toList());
        for (VaParamTableSyncDTO tableParam : tempList) {
            List tables = tableParam.getTables();
            VaParamTableModel table = tables.stream().filter(x -> tableName.equalsIgnoreCase(x.getTableName())).findAny().orElse(null);
            if (Objects.isNull(table)) continue;
            this.insertTableExecute(dataList, tableName, commonDao, sqlSession, table);
        }
    }

    private void insertTableExecute(List<Map<String, Object>> dataList, String tableName, CommonDao commonDao, SqlSession sqlSession, VaParamTableModel vaParamTableModel) {
        if (CollectionUtils.isEmpty(dataList) || !StringUtils.hasText(tableName) || Objects.isNull(vaParamTableModel)) {
            return;
        }
        SqlDTO insertSqlDTO = new SqlDTO("__default_tenant__", null);
        SqlDTO deleteSqlDTO = new SqlDTO("__default_tenant__", "delete from " + tableName);
        try {
            commonDao.executeBySql(deleteSqlDTO);
            LogUtil.add((String)"\u53c2\u6570\u5bfc\u5165\u6574\u8868\u8d44\u6e90", (String)("\u5220\u9664" + tableName), (String)"", (String)"", (String)"");
            int size = dataList.size();
            for (int i = 0; i < size; ++i) {
                List columns = vaParamTableModel.getColumns();
                String insertSql = this.buildInsertSql(tableName, columns);
                insertSqlDTO.setSql(insertSql);
                insertSqlDTO.setParam(null);
                VaParamSyncTableResourcesTransferModule.packageInsertSql(dataList, columns, i, insertSqlDTO);
                commonDao.executeBySql(insertSqlDTO);
                if ((i + 1) % 500 != 0 && i != size - 1) continue;
                sqlSession.commit();
                sqlSession.clearCache();
            }
            this.emitTableSyncEvent(vaParamTableModel.getTableName());
        }
        catch (Exception e) {
            logger.error("\u540c\u6b65\u6570\u636e\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            sqlSession.rollback();
        }
    }

    private static void packageInsertSql(List<Map<String, Object>> dataList, List<VaParamTableColumnModel> columns, int i, SqlDTO insertSqlDTO) {
        for (VaParamTableColumnModel column : columns) {
            Map<String, Object> map = dataList.get(i);
            Object value = map.get(column.getColumnName());
            if (value != null) {
                if ((column.getColumnType() == Type.TIMESTAMP || column.getColumnType() == Type.DATE) && value instanceof Number) {
                    value = new Date(((Number)value).longValue());
                }
                if (column.getColumnType() == Type.NUMERIC) {
                    if (value instanceof String) {
                        value = new BigDecimal((String)value);
                    } else if (!(value instanceof BigDecimal)) {
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumFractionDigits(0);
                        nf.setMaximumFractionDigits(20);
                        nf.setGroupingUsed(false);
                        value = new BigDecimal(nf.format(value));
                    }
                }
            }
            insertSqlDTO.addParam(column.getColumnName(), value);
        }
    }

    private void emitTableSyncEvent(String tableName) {
        VaParamSyncTableCacheEvent event = new VaParamSyncTableCacheEvent((Object)((Object)((Object)this)).getClass().getName());
        event.setTableName(tableName);
        ApplicationContextRegister.publishEvent((ApplicationEvent)event);
    }

    private String buildInsertSql(String tableName, List<VaParamTableColumnModel> columns) {
        int j;
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ");
        insertSql.append(tableName);
        insertSql.append("(");
        int size = columns.size();
        VaParamTableColumnModel column = null;
        for (j = 0; j < size; ++j) {
            column = columns.get(j);
            insertSql.append(column.getColumnName());
            if (j == size - 1) continue;
            insertSql.append(",");
        }
        insertSql.append(") values (");
        for (j = 0; j < size; ++j) {
            column = columns.get(j);
            insertSql.append("#{param.");
            insertSql.append(column.getColumnName());
            if (column.getColumnType() == Type.VARCHAR || column.getColumnType() == Type.NVARCHAR) {
                insertSql.append(",jdbcType=VARCHAR");
            } else if (column.getColumnType() == Type.INTEGER || column.getColumnType() == Type.NUMERIC) {
                insertSql.append(",jdbcType=NUMERIC");
            } else if (column.getColumnType() == Type.DATE || column.getColumnType() == Type.TIMESTAMP) {
                insertSql.append(",jdbcType=TIMESTAMP");
            } else if (column.getColumnType() == Type.CLOB) {
                insertSql.append(",jdbcType=CLOB");
            }
            insertSql.append("}");
            if (j == size - 1) continue;
            insertSql.append(",");
        }
        insertSql.append(")");
        return insertSql.toString();
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        if (this.tableSyncExport == null) {
            Object vaParamSyncTableResourcesExport = ApplicationContextRegister.getBean((String)"vaParamSyncTableResourcesExport");
            this.tableSyncExport = vaParamSyncTableResourcesExport != null;
        }
        if (!CollectionUtils.isEmpty(this.vaParamSyncTableService.getTableParams()) && this.tableSyncExport.booleanValue()) {
            if (StringUtils.hasText(parent)) {
                return super.getBusinessNodes(category, parent);
            }
            ArrayList<VaParamTransferBusinessNode> list = new ArrayList<VaParamTransferBusinessNode>();
            for (VaParamTableSyncDTO optionTransferIntf : this.vaParamSyncTableService.getTableParams()) {
                VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
                node.setId(optionTransferIntf.getName());
                node.setName(optionTransferIntf.getName());
                node.setTitle(optionTransferIntf.getTitle());
                node.setType(this.getName());
                node.setTypeTitle(this.getTitle());
                list.add(node);
            }
            return list;
        }
        return super.getBusinessNodes(category, parent);
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        for (VaParamTableSyncDTO optionTransferIntf : this.vaParamSyncTableService.getTableParams()) {
            if (!optionTransferIntf.getName().equals(nodeId)) continue;
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(optionTransferIntf.getName());
            node.setName(optionTransferIntf.getName());
            node.setTitle(optionTransferIntf.getTitle());
            node.setType(this.getName());
            node.setTypeTitle(this.getTitle());
            return node;
        }
        return super.getBusinessNode(category, nodeId);
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        for (VaParamTableSyncDTO optionTransferIntf : this.vaParamSyncTableService.getTableParams()) {
            if (!optionTransferIntf.getName().equals(nodeId)) continue;
            VaParamTransferFolderNode node = new VaParamTransferFolderNode();
            node.setName(optionTransferIntf.getName());
            node.setTitle(optionTransferIntf.getTitle());
            return Collections.singletonList(node);
        }
        return super.getPathFolders(category, nodeId);
    }

    public String getExportModelInfo(String category, String nodeId) {
        for (VaParamTableSyncDTO optionTransferIntf : this.vaParamSyncTableService.getTableParams()) {
            if (!optionTransferIntf.getName().equals(nodeId)) continue;
            List tables = optionTransferIntf.getTables();
            HashMap<String, List> tableDatas = new HashMap<String, List>();
            for (VaParamTableModel table : tables) {
                String tableName = table.getTableName();
                StringBuilder sql = new StringBuilder();
                sql.append("select ");
                for (VaParamTableColumnModel column : table.getColumns()) {
                    sql.append(column.getColumnName() + " as \"" + column.getColumnName() + "\",");
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(" from " + tableName);
                SqlDTO sqlDTO = new SqlDTO("__default_tenant__", sql.toString());
                List maps = this.commonDao.listMap(sqlDTO);
                tableDatas.put(tableName + "|" + nodeId, maps);
            }
            return JSONUtil.toJSONString(tableDatas);
        }
        return super.getExportModelInfo(category, nodeId);
    }
}

