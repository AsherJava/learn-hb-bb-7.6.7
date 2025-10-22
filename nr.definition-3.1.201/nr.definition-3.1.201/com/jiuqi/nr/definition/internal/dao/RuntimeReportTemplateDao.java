/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.internal.dao.AbstractReportTemplateDao;
import com.jiuqi.nr.definition.internal.impl.ReportTemplateDefineImpl;
import org.springframework.stereotype.Repository;

@Repository
public class RuntimeReportTemplateDao
extends AbstractReportTemplateDao<ReportTemplateDefineImpl> {
    @Override
    public Class<ReportTemplateDefineImpl> getClz() {
        return ReportTemplateDefineImpl.class;
    }
}

