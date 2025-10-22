/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.examine.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.nr.examine.job.TodoMessageCleanJobFactory;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TodoMessageCleanJobExecutor
extends JobExecutor {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TodoMessageCleanJobFactory.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        assert (dataSource != null) : "\u83b7\u53d6\u6570\u636e\u6e90\u5f02\u5e38";
        logger.info("\u5f00\u59cb\u6267\u884c\u8ba1\u5212\u4efb\u52a1\uff1a\u5f85\u529e\u6d88\u606f\u76f8\u5173\u8868\u6570\u636e\u6e05\u9664");
        int result = (Integer)this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MSG_TODO WHERE COMPLETETIME IS NOT NULL", Integer.class);
        if (result > 0) {
            for (MSG_TABLES tableName : MSG_TABLES.values()) {
                try {
                    String sql = "DELETE FROM " + (Object)((Object)tableName) + " WHERE MSGID IN (SELECT DISTINCT(MSGID) FROM MSG_TODO WHERE COMPLETETIME IS NOT NULL)";
                    this.jdbcTemplate.execute(sql);
                    logger.info("{}\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)tableName);
                }
                catch (Exception e) {
                    logger.error((Object)((Object)tableName) + "\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
                }
            }
        }
        try {
            this.jdbcTemplate.execute("DELETE FROM MSG_TODO WHERE COMPLETETIME IS NOT NULL");
            logger.info("MSG_TODO\u8868\u6570\u636e\u6e05\u9664\u6210\u529f");
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("MSG_TODO\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    private static enum MSG_TABLES {
        MSG_MAIN,
        MSG_PARTICIPANT,
        MSG_EXTEND,
        MSG_STATUS,
        MSG_TODO_LANG;

    }
}

