/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.DesignSummaryDataCell;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;

@DBAnno.DBTable(dbTable="NR_SUMMARY_DATACELL_DES")
public class DesignSummaryDataCellDO
extends SummaryDataCellDO
implements DesignSummaryDataCell {
    @Override
    public void setName(String name) {
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public void setDesc(String desc) {
    }

    @Override
    public void setOrder(String order) {
    }
}

