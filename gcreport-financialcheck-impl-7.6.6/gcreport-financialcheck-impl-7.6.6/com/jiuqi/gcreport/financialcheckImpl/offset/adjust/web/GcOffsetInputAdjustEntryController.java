/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.offset.GcOffsetInputAdjustEntryClient
 *  com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.web;

import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.GcOffsetInputAdjustEntryService;
import com.jiuqi.gcreport.financialcheckapi.offset.GcOffsetInputAdjustEntryClient;
import com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcOffsetInputAdjustEntryController
implements GcOffsetInputAdjustEntryClient {
    @Autowired
    private GcOffsetInputAdjustEntryService gcOffsetInputAdjustEntryService;

    public List<SubjectInfoVO> listSubjectInfo(String systemId) {
        return this.gcOffsetInputAdjustEntryService.listSubjectInfo(systemId);
    }
}

