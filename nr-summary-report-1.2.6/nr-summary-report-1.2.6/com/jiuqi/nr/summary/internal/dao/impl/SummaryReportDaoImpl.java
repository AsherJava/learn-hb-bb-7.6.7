/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryReportDao;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import java.time.Instant;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository(value="com.jiuqi.nr.summary.internal.dao.impl.SummaryReportDaoImpl")
public class SummaryReportDaoImpl
extends AbstractSummaryReportDao<SummaryReportDO> {
    @Override
    public Class<SummaryReportDO> getClz() {
        return SummaryReportDO.class;
    }

    @Override
    public ResultSetExtractor<SummaryReportDO> resultSetExtractor() {
        return rs -> {
            SummaryReportDO reportDO = new SummaryReportDO();
            reportDO.setKey(rs.getString("SR_KEY"));
            reportDO.setName(rs.getString("SR_NAME"));
            reportDO.setTitle(rs.getString("SR_TITLE"));
            reportDO.setSummarySolutionKey(rs.getString("SS_KEY"));
            reportDO.setModifyTime(Instant.ofEpochMilli(rs.getTimestamp("SR_MODIFY_TIME").getTime()));
            reportDO.setOrder(rs.getString("SR_ORDER"));
            return reportDO;
        };
    }

    @Override
    public RowMapper<SummaryReportDO> rowMapper() {
        return (rs, rowNum) -> {
            SummaryReportDO reportDO = new SummaryReportDO();
            reportDO.setKey(rs.getString("SR_KEY"));
            reportDO.setName(rs.getString("SR_NAME"));
            reportDO.setTitle(rs.getString("SR_TITLE"));
            reportDO.setSummarySolutionKey(rs.getString("SS_KEY"));
            reportDO.setModifyTime(Instant.ofEpochMilli(rs.getTimestamp("SR_MODIFY_TIME").getTime()));
            reportDO.setOrder(rs.getString("SR_ORDER"));
            return reportDO;
        };
    }

    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }
}

