/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.object.BatchSqlUpdate
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;

public class CheckErrorDesDataUPExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CheckErrorDesDataUPExecutor.class);
    private static final ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
    private static final JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);

    public void execute(DataSource dataSource) throws Exception {
        for (String formSchemeCode : paramUtil.getAllFormScheme()) {
            String ckdTableName = CheckTableNameUtil.getCKDTableName(formSchemeCode);
            try {
                List<String> userId = this.getUserId(ckdTableName);
                Map<String, String> idNickNameMap = paramUtil.getIdNickNameMap(userId);
                idNickNameMap.put("sys_user_admin", "\u7cfb\u7edf\u7ba1\u7406\u5458");
                this.dataUpByTable(ckdTableName, idNickNameMap, dataSource);
                logger.info("\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u8868{}\u6570\u636e\u5347\u7ea7\u5b8c\u6210", (Object)ckdTableName);
            }
            catch (Exception e) {
                logger.error("\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u8868" + ckdTableName + "\u6570\u636e\u5347\u7ea7\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    private List<String> getUserId(String tableName) {
        return jdbcTemplate.queryForList(String.format("SELECT DISTINCT %s from %s", "CKD_USERKEY", tableName), String.class);
    }

    private void dataUpByTable(String tableName, Map<String, String> idNickNameMap, DataSource dataSource) {
        BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(dataSource, String.format("update %s set %s=? where %s=?", tableName, "CKD_USERNAME", "CKD_USERKEY"));
        batchSqlUpdate.setTypes(new int[]{12, 12});
        for (Map.Entry<String, String> entry : idNickNameMap.entrySet()) {
            batchSqlUpdate.update(entry.getValue(), entry.getKey());
        }
        batchSqlUpdate.flush();
    }
}

