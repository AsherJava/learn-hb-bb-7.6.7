/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryDataCellDao;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import com.jiuqi.nr.summary.utils.SummaryReportTransUtil;
import org.springframework.stereotype.Repository;

@Repository(value="com.jiuqi.nr.summary.internal.dao.impl.SummaryDataCellDaoImpl")
public class SummaryDataCellDaoImpl
extends AbstractSummaryDataCellDao<SummaryDataCellDO> {
    @Override
    public Class<SummaryDataCellDO> getClz() {
        return SummaryDataCellDO.class;
    }

    public Class<?> getExternalTransCls() {
        return SummaryReportTransUtil.class;
    }
}

