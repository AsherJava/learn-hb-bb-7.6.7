/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.impl.AbstractSummaryFormulaDao;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryFormulaDO;
import org.springframework.stereotype.Repository;

@Repository
public class DesignSummaryFormulaDaoImpl
extends AbstractSummaryFormulaDao<DesignSummaryFormulaDO> {
    @Override
    public Class<DesignSummaryFormulaDO> getClz() {
        return DesignSummaryFormulaDO.class;
    }
}

