/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.nr.data.logic.internal.obj.CKRRec;
import com.jiuqi.nr.data.logic.internal.service.ICKRRecService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CKRRecServiceImpl
implements ICKRRecService {
    private static final String TABLE = "NR_CKR_RECORD";
    private static final String COL_KEY = "REC_KEY";
    private static final String COL_BATCH_ID = "REC_BATCH_ID";
    private static final String COL_FM_SCHEME = "REC_FM_SCHEME";
    private static final String COL_USER = "REC_USER";
    private static final String COL_START_TIME = "REC_START_TIME";
    private static final String COL_FINISH_TIME = "REC_FINISH_TIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(CKRRec ckrRec) {
        String sql = "insert into %s(%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?)";
        String format = String.format(sql, TABLE, COL_KEY, COL_BATCH_ID, COL_FM_SCHEME, COL_USER, COL_START_TIME, COL_FINISH_TIME);
        return this.jdbcTemplate.update(format, new Object[]{ckrRec.getKey(), ckrRec.getBatchId(), ckrRec.getFormSchemeKey(), ckrRec.getUserID(), ckrRec.getStartTime(), ckrRec.getFinishTime()});
    }

    @Override
    public int deleteByBatchIds(List<String> batchIds) {
        if (CollectionUtils.isEmpty(batchIds)) {
            return 0;
        }
        String sql = "delete from %s where %s=?";
        String format = String.format(sql, TABLE, COL_BATCH_ID);
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String batchId : batchIds) {
            Object[] o = new Object[]{batchId};
            batchArgs.add(o);
        }
        int[] updateCount = this.jdbcTemplate.batchUpdate(format, batchArgs);
        return Arrays.stream(updateCount).sum();
    }

    @Override
    public int deleteBeforeStartTime(long startTime) {
        String sql = "delete from %s where %s<?";
        String format = String.format(sql, TABLE, COL_START_TIME);
        return this.jdbcTemplate.update(format, new Object[]{startTime});
    }

    @Override
    public List<String> getBatchIdsBeforeTime(long startTime) {
        String sql = String.format("select distinct %s from %s where %s<?", COL_BATCH_ID, TABLE, COL_START_TIME);
        return this.jdbcTemplate.queryForList(sql, String.class, new Object[]{startTime});
    }
}

