/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlOrgViewClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlOrgViewClient;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOrgViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class SameCtrlOrgViewController
implements SameCtrlOrgViewClient {
    @Autowired
    private SameCtrlOrgViewService sameCtrlOrgViewService;

    public BusinessResponseEntity<Object> setOrgViewStopFlag() {
        try {
            this.sameCtrlOrgViewService.setOrgViewStopFlag();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> getSameCtrlViewId(String formSchemeKey) {
        String sameCtrlViewId = this.sameCtrlOrgViewService.getSameCtrlViewId(formSchemeKey);
        return BusinessResponseEntity.ok().data((Object)sameCtrlViewId);
    }
}

