/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlExtAfterSchemeClient
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtAfterSchemeVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlExtAfterSchemeClient;
import com.jiuqi.gcreport.samecontrol.service.ExtAfterSchemeService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtAfterSchemeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ExtAfterSchemeController
implements SameCtrlExtAfterSchemeClient {
    @Autowired
    private ExtAfterSchemeService extAfterSchemeService;

    public BusinessResponseEntity<Object> getSameCtrlExtAfterScheme(SameCtrlExtAfterSchemeVO sameCtrlExtAfterScheme) {
        return BusinessResponseEntity.ok(this.extAfterSchemeService.getSameCtrlExtAfterScheme(sameCtrlExtAfterScheme));
    }
}

