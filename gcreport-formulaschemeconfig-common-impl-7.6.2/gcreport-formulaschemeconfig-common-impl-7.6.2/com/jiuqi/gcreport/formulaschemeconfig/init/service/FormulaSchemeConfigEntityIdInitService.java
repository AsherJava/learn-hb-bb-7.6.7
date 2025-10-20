/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.init.service;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.init.service.FormulaSchemeConfigBillFetchInitService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class FormulaSchemeConfigEntityIdInitService {
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeConfigBillFetchInitService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void doInit() {
        this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u53e3\u5f84\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u5f00\u59cb\u6267\u884c");
        String sql = "SELECT TASKID ,COUNT(1) FROM GC_FORMULASCHEMECONFIG WHERE CATEGORY  = 'reportFetch' AND (ENTITYID IS NULL OR ENTITYID = '' OR ENTITYID = '#') GROUP BY TASKID ";
        List taskKeyList = (List)this.jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<List<String>>(){

            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<String> formulaList = new ArrayList<String>();
                while (rs.next()) {
                    if (StringUtils.isEmpty((String)rs.getString(1)) || rs.getInt(2) == 0) continue;
                    formulaList.add(rs.getString(1));
                }
                return formulaList;
            }
        }, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)taskKeyList)) {
            this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u53e3\u5f84\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u65e0\u9700\u6267\u884c");
            return;
        }
        for (String taskKey : taskKeyList) {
            try {
                boolean enableTaskMultiOrg = FormulaSchemeConfigUtils.enableTaskMultiOrg(taskKey);
                if (enableTaskMultiOrg) {
                    this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u4efb\u52a1{}\u542f\u7528\u591a\u53e3\u5f84\u6570\u636e-\u81ea\u52a8\u8df3\u8fc7", (Object)taskKey);
                    continue;
                }
                String entityId = FormulaSchemeConfigUtils.getEntityIdByTaskKeyAndCtx(taskKey);
                String updateEntityIdSql = " UPDATE GC_FORMULASCHEMECONFIG SET ENTITYID = ? WHERE CATEGORY  = 'reportFetch' AND TASKID = ? AND (ENTITYID IS NULL OR ENTITYID = '' OR ENTITYID = '#') ";
                int updateEntityCount = this.jdbcTemplate.update(updateEntityIdSql, new Object[]{entityId, taskKey});
                this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u4efb\u52a1{}\u53e3\u5f84\u5b57\u6bb5\u6570\u636e\u5904\u7406\u4e3a{}-\u6267\u884c\u5b8c\u6210,\u66f4\u65b0{}\u6761\u6570\u636e", taskKey, entityId, updateEntityCount);
            }
            catch (Exception e) {
                this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u4efb\u52a1{}\u53e3\u5f84\u5b57\u6bb5\u4fee\u590d\u51fa\u73b0\u9519\u8bef", (Object)taskKey, (Object)e);
            }
        }
    }
}

