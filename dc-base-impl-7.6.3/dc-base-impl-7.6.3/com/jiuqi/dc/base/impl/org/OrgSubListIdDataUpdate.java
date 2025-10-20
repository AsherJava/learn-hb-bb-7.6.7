/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.impl.org;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class OrgSubListIdDataUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgSubListIdDataUpdate.class);
    private static final String LOG_PREFIX = "\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5347\u7ea7\u4fee\u590d-";

    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        try {
            String sql = "SELECT ID FROM MD_ORG_SUBLIST WHERE ID NOT LIKE '%-%'";
            List subListIds = jdbcTemplate.query(sql, (RowMapper)new StringRowMapper());
            if (CollectionUtils.isEmpty((Collection)subListIds)) {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5347\u7ea7\u4fee\u590d-\u9700\u8981\u5347\u7ea7\u7684\u6570\u636e\u4e3a\u7a7a\u3002");
                return;
            }
            HashMap<String, String> subIdMap = new HashMap<String, String>();
            for (String id : subListIds) {
                subIdMap.put(id, UUIDUtils.newUUIDStr());
            }
            jdbcTemplate.update("CREATE TABLE BD_MD_ORG_SUBLIST_20250121 AS SELECT * FROM MD_ORG_SUBLIST ");
            ArrayList<Object[]> params = new ArrayList<Object[]>(subListIds.size());
            for (Map.Entry idMap : subIdMap.entrySet()) {
                params.add(this.updateParam((String)idMap.getValue(), (String)idMap.getKey()));
            }
            jdbcTemplate.batchUpdate("UPDATE MD_ORG_SUBLIST SET ID  = ? WHERE ID = ?", params);
        }
        catch (DataAccessException e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5347\u7ea7\u4fee\u590d-\u7ec4\u7ec7\u673a\u6784\u66f4\u65b0\u8868\u6570\u636e\u5f02\u5e38\u3002", e);
        }
    }

    private Object[] updateParam(String uuid, String guid) {
        return new Object[]{uuid, guid};
    }
}

