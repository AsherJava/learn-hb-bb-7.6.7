/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryFormulaDao;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;
import org.springframework.stereotype.Repository;

@Repository
public class SummaryFormulaDaoImpl
extends AbstractSummaryFormulaDao<SummaryFormulaDO> {
    @Override
    public Class<SummaryFormulaDO> getClz() {
        return SummaryFormulaDO.class;
    }
}

