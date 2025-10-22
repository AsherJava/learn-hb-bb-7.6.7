/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.service.SubjectMergeFlagService
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.subject;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.service.SubjectMergeFlagService;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectMergeFlagServiceImpl
implements SubjectMergeFlagService {
    @Autowired
    private ConsolidatedSystemService systemService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    public String getSubjectMergeFlag(String subjectCode) {
        if (StringUtils.isEmpty((String)subjectCode)) {
            return "";
        }
        List<ConsolidatedSystemEO> systemEOS = this.systemService.getConsolidatedSystemEOS();
        ArrayList<String> systemNames = new ArrayList<String>();
        for (int i = 0; i < systemEOS.size(); ++i) {
            ConsolidatedSystemEO consolidatedSystemVO = systemEOS.get(i);
            ConsolidatedSubjectEO consolidatedSubjectEO = this.consolidatedSubjectService.getSubjectByCode(consolidatedSystemVO.getId(), subjectCode);
            if (consolidatedSubjectEO == null) continue;
            systemNames.add(consolidatedSystemVO.getSystemName());
        }
        if (systemNames.isEmpty()) {
            return "";
        }
        return String.join((CharSequence)";", systemNames);
    }
}

