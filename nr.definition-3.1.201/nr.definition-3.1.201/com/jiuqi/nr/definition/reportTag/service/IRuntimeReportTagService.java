/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.reportTag.service;

import com.jiuqi.nr.definition.facade.ReportTagDefine;
import java.util.List;

public interface IRuntimeReportTagService {
    public List<ReportTagDefine> queryAllTagsByRptKey(String var1);
}

