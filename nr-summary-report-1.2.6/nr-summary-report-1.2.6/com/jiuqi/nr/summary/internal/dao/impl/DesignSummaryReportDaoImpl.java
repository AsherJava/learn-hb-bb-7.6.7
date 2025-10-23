/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryReportDao;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryReportDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import java.time.Instant;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class DesignSummaryReportDaoImpl
extends AbstractSummaryReportDao<DesignSummaryReportDO> {
    @Override
    public Class<DesignSummaryReportDO> getClz() {
        return DesignSummaryReportDO.class;
    }

    @Override
    public ResultSetExtractor<DesignSummaryReportDO> resultSetExtractor() {
        return rs -> {
            DesignSummaryReportDO reportDO = new DesignSummaryReportDO();
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
    public RowMapper<DesignSummaryReportDO> rowMapper() {
        return (rs, rowNum) -> {
            DesignSummaryReportDO reportDO = new DesignSummaryReportDO();
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

