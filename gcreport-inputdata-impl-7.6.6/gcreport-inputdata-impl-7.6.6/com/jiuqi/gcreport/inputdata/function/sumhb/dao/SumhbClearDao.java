/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dao;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class SumhbClearDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final int[] offsetSet = new int[]{0, 10};
    private final int offsetChangeHour = 2;

    public int getOffset() {
        int hour = LocalTime.now().getHour();
        return this.offsetSet[hour / 2 % 2];
    }

    @Scheduled(cron="0 50 1/2 * * ?")
    public void cleanTimeoutRequest() {
        int clearOffset = this.getClearOffset();
        for (int index = 1; index <= 10; ++index) {
            String sql = "truncate table GC_SUMHBNOTEMP" + (index + clearOffset);
            this.jdbcTemplate.execute(sql);
        }
    }

    private int getClearOffset() {
        int hour = LocalTime.now().plusHours(-2L).getHour();
        return this.offsetSet[hour / 2 % 2];
    }
}

