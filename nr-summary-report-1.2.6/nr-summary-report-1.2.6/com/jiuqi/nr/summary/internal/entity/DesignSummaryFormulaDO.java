/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.DesignSummaryFormula;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;

@DBAnno.DBTable(dbTable="NR_SUMMARY_FORMULA_DES")
public class DesignSummaryFormulaDO
extends SummaryFormulaDO
implements DesignSummaryFormula {
    @Override
    public void setTitle(String title) {
    }
}

