/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.external.callback.service;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitFinBizmodelTableService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class})
    public void doInit() {
        String[][] bizModelData;
        if (!this.selectCount("BDE_BIZMODEL_FINDATA").booleanValue()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String[] data : bizModelData = new String[][]{{UUIDUtils.newHalfGUIDStr(), "INIT_KMYEB", "\u79d1\u76ee\u4f59\u989d\u8868", "BALANCE", "0", "1", "NC,C,JF,DF,JL,DL,YE,BQNUM,LJNUM", null, "0"}, {UUIDUtils.newHalfGUIDStr(), "INIT_FZYEB", "\u8f85\u52a9\u4f59\u989d\u8868", "ASSBALANCE", "0", "2", "NC,C,JF,DF,JL,DL,YE,BQNUM,LJNUM", null, "1"}, {UUIDUtils.newHalfGUIDStr(), "INIT_XJLLYEB", "\u73b0\u91d1\u6d41\u91cf\u4f59\u989d\u8868", "XJLLBALANCE", "0", "3", "BQNUM,LJNUM", null, "1"}, {UUIDUtils.newHalfGUIDStr(), "INIT_CFLYEB", "\u91cd\u5206\u7c7b\u4f59\u989d\u8868", "CFLBALANCE", "0", "4", "JNC,DNC,JYH,DYH", null, "0"}, {UUIDUtils.newHalfGUIDStr(), "INIT_FZCFLYEB", "\u8f85\u52a9\u4f59\u989d\u91cd\u5206\u7c7b\u8868", "ASSCFLBALANCE", "0", "5", "JNC,DNC,JYH,DYH", null, "1"}, {UUIDUtils.newHalfGUIDStr(), "INIT_DJYEB", "\u62b5\u51cf\u4f59\u989d\u8868", "DJYEBALANCE", "0", "6", "JNC,DNC,JYH,DYH", null, "1"}, {UUIDUtils.newHalfGUIDStr(), "INIT_ZLYEB", "\u8d26\u9f84\u4f59\u989d\u8868", "AGINGBALANCE", "0", "7", "HXNC,HXYE", null, "1"}, {UUIDUtils.newHalfGUIDStr(), "INIT_CEDXB", "\u5dee\u989d\u62b5\u9500\u8868", "CEDXBALANCE", "0", "8", "DXNC,DXJNC,DXDNC,DXYE,DXJYH,DXDYH", null, "1"}}) {
            batchArgs.add(new Object[]{data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), BigDecimal.valueOf(Integer.parseInt(data[5])), data[6], data[7], data[8]});
        }
        this.updateBatch("BDE_BIZMODEL_FINDATA", batchArgs);
    }

    private Boolean selectCount(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Integer count = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class);
        return count == null || count <= 0;
    }

    private void updateBatch(String tableName, List<Object[]> batchArgs) {
        if (CollectionUtils.isEmpty(batchArgs)) {
            return;
        }
        String sql = "INSERT INTO " + tableName + "(ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,FETCHTYPES,DIMENSIONS,SELECTALL) VALUES(?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}

