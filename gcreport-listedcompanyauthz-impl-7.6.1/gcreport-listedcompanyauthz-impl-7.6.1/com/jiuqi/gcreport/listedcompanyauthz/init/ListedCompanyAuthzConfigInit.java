/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.gcreport.listedcompanyauthz.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.listedcompanyauthz.dao.impl.ListedCompanyAuthzDaoImpl;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class ListedCompanyAuthzConfigInit
implements ModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(ListedCompanyAuthzConfigInit.class);

    public void init(ServletContext servletContext) throws Exception {
    }

    public void initWhenStarted(ServletContext servletContext) throws Exception {
        try {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u662f\u5426\u6709\u7a7f\u900f\u6743\u9650\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u5f00\u59cb\u6267\u884c");
            ListedCompanyAuthzDaoImpl listedCompanyAuthzDao = (ListedCompanyAuthzDaoImpl)SpringContextUtils.getBean(ListedCompanyAuthzDaoImpl.class);
            String sql = " SELECT COUNT(1) FROM GC_LISTEDCOMPANY_AUTHZ WHERE ISPENETRATE IS NULL ";
            JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
            Integer count = (Integer)jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<Integer>(){

                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next() && rs.getObject(1) != null) {
                        return rs.getInt(1);
                    }
                    return 0;
                }
            }, new Object[0]);
            if (count != null && count > 0) {
                String updateSql = " UPDATE GC_LISTEDCOMPANY_AUTHZ SET ISPENETRATE = 1 WHERE ISPENETRATE IS NULL ";
                int updateCount = listedCompanyAuthzDao.execute(updateSql);
                this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u662f\u5426\u6709\u7a7f\u900f\u6743\u9650\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u6267\u884c\u5b8c\u6210,\u66f4\u65b0{}\u6761\u6570\u636e", (Object)updateCount);
            } else {
                this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u662f\u5426\u6709\u7a7f\u900f\u6743\u9650\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u65e0\u9700\u6267\u884c");
            }
        }
        catch (Exception e) {
            this.logger.error("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u662f\u5426\u6709\u7a7f\u900f\u6743\u9650\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u6267\u884c\u5f02\u5e38", e);
        }
    }
}

