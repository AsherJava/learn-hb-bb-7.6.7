/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.query.task;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.query.sql.service.impl.SqlQueryServiceImpl;
import com.jiuqi.va.query.storage.QueryStorage;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryStorageSyncTask
implements StorageSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(SqlQueryServiceImpl.class);
    @Autowired
    private List<QueryStorage> queryStorages;

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        this.cleatTemTableData();
        for (QueryStorage ps : this.queryStorages) {
            ps.init(tenantName);
        }
    }

    public String getVersion() {
        return "20250324-1153";
    }

    private void cleatTemTableData() {
        try {
            String sql = "truncate table DC_QUERY_TEMPTABLE";
            CommonDao commonDao = DCQuerySpringContextUtils.getBean(CommonDao.class);
            SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql);
            commonDao.executeBySql(sqlDTO);
        }
        catch (Exception e) {
            logger.error("\u6e05\u7a7a\u4e34\u65f6\u8868\u6570\u636e\u5931\u8d25", e);
        }
    }
}

