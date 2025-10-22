/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeReportTagDao;
import com.jiuqi.nr.definition.reportTag.service.IRuntimeReportTagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeReportTagService
implements IRuntimeReportTagService {
    @Autowired
    private RuntimeReportTagDao reportTagDao;

    @Override
    public List<ReportTagDefine> queryAllTagsByRptKey(String rptKey) {
        return this.reportTagDao.queryByRptKey(rptKey);
    }
}

