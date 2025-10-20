/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.attachment.task;

import com.jiuqi.va.attachment.storage.AttachmentBizConfirmStorage;
import com.jiuqi.va.attachment.storage.AttachmentBizRecycleBinStorage;
import com.jiuqi.va.attachment.storage.AttachmentBizRefStorage;
import com.jiuqi.va.attachment.storage.AttachmentBizRemarksStorage;
import com.jiuqi.va.attachment.storage.AttachmentBizStorage;
import com.jiuqi.va.attachment.storage.AttachmentBizTemplateFileStorage;
import com.jiuqi.va.attachment.storage.AttachmentModeStorage;
import com.jiuqi.va.attachment.storage.AttachmentSchemeStorage;
import com.jiuqi.va.attachment.storage.FileOptionStorage;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaAttachementCoreStorageSyncTask
implements StorageSyncTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaAttachementCoreStorageSyncTask.class);
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public String getVersion() {
        return "20230117-1557";
    }

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        AttachmentBizTemplateFileStorage.init(tenantName);
        AttachmentBizRemarksStorage.init(tenantName);
        AttachmentSchemeStorage.init(tenantName);
        AttachmentModeStorage.init(tenantName);
        FileOptionStorage.init(tenantName);
        AttachmentBizRefStorage.init(tenantName);
        this.updateBizTemplateFileTables(tenantName);
        this.updateSchemeConfig(tenantName);
        AttachmentBizRecycleBinStorage.init(tenantName);
        AttachmentBizConfirmStorage.init(tenantName);
    }

    private void updateBizTemplateFileTables(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        List tableNames = jDialect.getTableNames(tenantName);
        if (tableNames == null || tableNames.isEmpty()) {
            return;
        }
        for (String tableName : tableNames) {
            String suffix;
            if (!tableName.toUpperCase().startsWith("BIZATTACHMENT_") || !this.isNumeric(suffix = tableName.substring(14))) continue;
            JTableModel jtm = AttachmentBizStorage.getCreateJTM(tenantName, suffix);
            Set newColumns = jtm.getColumns().stream().map(ColumnModel::getColumnName).collect(Collectors.toSet());
            Set tableColumns = jDialect.getTableColumns(tenantName, tableName).stream().map(TableColumnDO::getColumnName).collect(Collectors.toSet());
            boolean hasColumn = newColumns.stream().anyMatch(o -> !tableColumns.contains(o));
            if (!hasColumn) continue;
            try {
                jDialect.updateTable(jtm);
            }
            catch (JTableException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateSchemeConfig(String tenantName) {
        boolean isNewDB = true;
        JDialectUtil jDialect = JDialectUtil.getInstance();
        List columnsList = jDialect.getTableColumns(tenantName, "ATT_SCHEME");
        for (TableColumnDO column : columnsList) {
            if (!column.getColumnName().equalsIgnoreCase("ATTADDRESS")) continue;
            isNewDB = false;
            break;
        }
        if (isNewDB) {
            return;
        }
        String getAllScheme = "select * from ATT_SCHEME where config is null and STOREMODE <> 2";
        SqlDTO sqlDTO = new SqlDTO(tenantName, getAllScheme);
        List schemeList = this.commonDao.listLowerKeyMap(sqlDTO);
        if (schemeList != null && schemeList.size() > 0) {
            try (SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);){
                CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
                String sqlTemp = "update ATT_SCHEME set config=#{param.config, jdbcType=VARCHAR} where id = #{param.id, jdbcType=VARCHAR}";
                sqlDTO = new SqlDTO(tenantName, sqlTemp);
                for (Map schemeMap : schemeList) {
                    HashMap schemeExtendConfig = new HashMap();
                    schemeExtendConfig.put("attaddress", schemeMap.get("attaddress"));
                    schemeExtendConfig.put("port", schemeMap.get("port"));
                    schemeExtendConfig.put("username", schemeMap.get("username"));
                    schemeExtendConfig.put("pwd", schemeMap.get("pwd"));
                    schemeExtendConfig.put("dbname", schemeMap.get("dbname"));
                    schemeExtendConfig.put("datasize", schemeMap.get("datasize"));
                    schemeExtendConfig.put("authflag", schemeMap.get("authflag"));
                    schemeExtendConfig.put("storagebucket", schemeMap.get("storagebucket"));
                    String config = JSONUtil.toJSONString(schemeExtendConfig);
                    sqlDTO.addParam("id", schemeMap.get("id"));
                    sqlDTO.addParam("config", (Object)config);
                    commonDao.executeBySql(sqlDTO);
                }
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }
        try {
            String updateModeConfig = "update ATT_MODE set config = CONCAT('{\"collectionflag\":',CONCAT('\"',CONCAT(collectionflag,'\"}'))) where config is null and collectionflag is not null";
            sqlDTO = new SqlDTO(tenantName, updateModeConfig);
            this.commonDao.executeBySql(sqlDTO);
        }
        catch (Exception e) {
            LOGGER.error("\u5347\u7ea7\u9644\u4ef6\u65b9\u6848\u8868\u7ed3\u6784\u5f02\u5e38", e);
        }
    }

    private boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isDigit(cs.charAt(i))) continue;
            return false;
        }
        return true;
    }
}

