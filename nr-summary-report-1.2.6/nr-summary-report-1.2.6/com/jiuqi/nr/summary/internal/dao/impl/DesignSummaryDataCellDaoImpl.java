/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryDataCellDao;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryDataCellDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class DesignSummaryDataCellDaoImpl
extends AbstractSummaryDataCellDao<DesignSummaryDataCellDO> {
    @Override
    public Class<DesignSummaryDataCellDO> getClz() {
        return DesignSummaryDataCellDO.class;
    }

    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }
}

