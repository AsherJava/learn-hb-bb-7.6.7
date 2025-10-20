/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.init.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormulaSchemeConfigBillFetchInitService {
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeConfigBillFetchInitService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void doInit() {
        this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u5f00\u59cb\u6267\u884c");
        String sql = " SELECT COUNT(1) FROM GC_FORMULASCHEMECONFIG WHERE CATEGORY IS NULL OR CATEGORY = ''";
        Integer count = (Integer)this.jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<Integer>(){

            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next() && rs.getObject(1) != null) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }, new Object[0]);
        if (count != null && count > 0) {
            String updateSql = " UPDATE GC_FORMULASCHEMECONFIG SET CATEGORY = 'reportFetch', BILLID = '-' WHERE BILLID IS NULL OR BILLID = '' ";
            int updateCount = this.jdbcTemplate.update(updateSql);
            this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u6267\u884c\u5b8c\u6210,\u66f4\u65b0{}\u6761\u6570\u636e", (Object)updateCount);
        } else {
            this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u65e0\u9700\u6267\u884c");
        }
    }
}

