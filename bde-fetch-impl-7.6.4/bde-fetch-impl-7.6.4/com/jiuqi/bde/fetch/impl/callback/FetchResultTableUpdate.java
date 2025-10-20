/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bde.fetch.impl.callback;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

public class FetchResultTableUpdate
implements CustomClassExecutor {
    private static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE %1$s_%2$d";
    private static final Logger logger = LoggerFactory.getLogger(FetchResultTableUpdate.class);

    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Integer tableNum = ((Environment)ApplicationContextRegister.getBean(Environment.class)).getProperty("jiuqi.bde.fetch.result.table.num", Integer.class, 20);
        ArrayList<String> sqls = new ArrayList<String>();
        for (int i = 1; i <= tableNum; ++i) {
            sqls.add(String.format(TRUNCATE_TABLE_SQL, "BDE_RESULT_FIXED", i));
            sqls.add(String.format(TRUNCATE_TABLE_SQL, "BDE_RESULT_FLOATCOL", i));
        }
        logger.info("\u5f00\u59cb\u6e05\u9664\u7ed3\u679c\u8868\u6570\u636e");
        logger.info("\u6e05\u9664\u7ed3\u679c\u8868\u6570\u636eSQL:\r\n", (Object)String.join((CharSequence)";\r\n", sqls));
        for (String sql : sqls) {
            jdbcTemplate.update(sql);
        }
        logger.info("\u7ed3\u675f\u6e05\u9664\u7ed3\u679c\u8868\u6570\u636e");
    }
}

