/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvestIdDataRepair {
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute() throws Exception {
        this.logger.info("\u6295\u8d44\u53f0\u8d26\u591a\u65f6\u671f\u62c6\u5206\u5f02\u5e38\u6570\u636e\u4fee\u590d\uff1a\u4fee\u590d\u5f00\u59cb");
        String sql = "SELECT ID FROM GC_INVESTBILL WHERE SRCID = ID";
        List idList = EntNativeSqlDefaultDao.getInstance().selectFirstList(String.class, sql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)idList)) {
            this.logger.info("\u6295\u8d44\u53f0\u8d26\u591a\u65f6\u671f\u62c6\u5206\u5f02\u5e38\u6570\u636e\u4fee\u590d\uff1a\u672a\u67e5\u51fasrcid\u4e0eid\u76f8\u540c\u7684\u95ee\u9898\u6570\u636e\uff0c\u8df3\u8fc7\u4fee\u590d");
            return;
        }
        Map<String, String> newId2OldIdMap = idList.stream().collect(Collectors.toMap(item -> UUIDUtils.newUUIDStr(), item -> item));
        this.logger.info("\u6295\u8d44\u53f0\u8d26\u591a\u65f6\u671f\u62c6\u5206\u5f02\u5e38\u6570\u636e\u4fee\u590d\uff1a\u5171\u67e5\u51fa" + idList.size() + "\u6761srcid\u4e0eid\u76f8\u540c\u7684\u6570\u636e\uff0c\u5f00\u59cb\u4fee\u590d\u6295\u8d44\u53f0\u8d26\u4e3b\u8868\u53ca\u5b50\u8868");
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        String updateBillSql = "UPDATE GC_INVESTBILL SET ID = ? WHERE ID = ? \n";
        String updateBillItemSql = "UPDATE GC_INVESTBILLITEM SET MASTERID = ? WHERE MASTERID = ? \n";
        String insertMappingSql = "INSERT INTO REPAIR_M_GC_INVESTBILL_ID (NEWID,OLDID) VALUES( ?, ?) \n";
        ArrayList<Object[]> updateBillArgs = new ArrayList<Object[]>();
        for (Map.Entry<String, String> newId : newId2OldIdMap.entrySet()) {
            Object[] args = new Object[]{newId.getKey(), newId.getValue()};
            updateBillArgs.add(args);
        }
        jdbcTemplate.batchUpdate(updateBillSql, updateBillArgs);
        jdbcTemplate.batchUpdate(updateBillItemSql, updateBillArgs);
        jdbcTemplate.batchUpdate(insertMappingSql, updateBillArgs);
        this.logger.info("\u6295\u8d44\u53f0\u8d26\u591a\u65f6\u671f\u62c6\u5206\u5f02\u5e38\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }
}

