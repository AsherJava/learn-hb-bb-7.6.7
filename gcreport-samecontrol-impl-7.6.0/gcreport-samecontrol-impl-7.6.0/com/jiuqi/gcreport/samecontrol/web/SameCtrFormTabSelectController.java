/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrFormTabSelectClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.api.SameCtrFormTabSelectClient;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlFormTabSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class SameCtrFormTabSelectController
implements SameCtrFormTabSelectClient {
    @Autowired
    private SameCtrlFormTabSelectService formTabSelectService;

    public BusinessResponseEntity<String> queryFormData(String formKey) {
        return BusinessResponseEntity.ok((Object)this.formTabSelectService.queryFormData(formKey, null));
    }
}

