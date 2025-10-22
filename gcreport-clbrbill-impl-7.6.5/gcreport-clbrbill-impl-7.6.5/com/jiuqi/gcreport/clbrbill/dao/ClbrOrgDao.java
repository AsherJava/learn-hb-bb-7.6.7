/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.clbrbill.dao;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClbrOrgDao {
    private static final Logger logger = LoggerFactory.getLogger(ClbrOrgDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findOrgCodeBySrcOrgCode(String srcOrgCode) {
        ArrayList codes = new ArrayList();
        String sql = String.format("SELECT CODE FROM MD_ORG WHERE SRCORGCODE='%s' and STOPFLAG=0 and RECOVERYFLAG=0 ORDER BY VALIDTIME DESC", srcOrgCode);
        this.jdbcTemplate.query(sql, rs -> {
            while (rs.next()) {
                codes.add(rs.getString("CODE"));
            }
            return codes;
        });
        logger.info("\u534f\u540c\u5e73\u53f0\u6839\u636e\u5171\u4eab\u5355\u4f4d\u4ee3\u7801[{}]\u67e5\u51fa\u5355\u4f4d\u6570\u636e\u6761\u6570: {}", (Object)srcOrgCode, (Object)codes.size());
        if (!codes.isEmpty()) {
            logger.info("\u534f\u540c\u5e73\u53f0\u6839\u636e\u5171\u4eab\u5355\u4f4d\u4ee3\u7801[{}]\u67e5\u8be2\u5230\u534f\u540c\u5355\u4f4d\u4ee3\u7801: {}", (Object)srcOrgCode, codes.get(0));
            return (String)codes.get(0);
        }
        return null;
    }

    public String findSrcOrgCodeByOrgCode(String orgCode) {
        ArrayList codes = new ArrayList();
        String sql = String.format("SELECT SRCORGCODE FROM MD_ORG WHERE CODE='%s' and STOPFLAG=0 and RECOVERYFLAG=0", orgCode);
        this.jdbcTemplate.query(sql, rs -> {
            while (rs.next()) {
                codes.add(rs.getString("SRCORGCODE"));
            }
            return codes;
        });
        logger.info("\u534f\u540c\u5e73\u53f0\u6839\u636e\u7ec4\u7ec7\u4ee3\u7801[{}]\u67e5\u51fa\u5355\u4f4d\u6570\u636e\u6761\u6570: {}", (Object)orgCode, (Object)codes.size());
        if (!codes.isEmpty()) {
            logger.info("\u534f\u540c\u5e73\u53f0\u6839\u636e\u7ec4\u7ec7\u4ee3\u7801[{}]\u67e5\u8be2\u5230\u5171\u4eab\u5355\u4f4d\u4ee3\u7801: {}", (Object)orgCode, codes.get(0));
            return (String)codes.get(0);
        }
        return null;
    }
}

