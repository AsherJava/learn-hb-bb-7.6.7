/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.mobile.approval.client.CnoocMobileClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.mobile.approval.web;

import com.jiuqi.gcreport.mobile.approval.client.CnoocMobileClient;
import com.jiuqi.gcreport.mobile.approval.service.CnoocMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class CnoocMobileRestController
implements CnoocMobileClient {
    @Autowired
    CnoocMobileService cnoocMobileService;

    public String getEncodeUrl(@PathVariable String url) {
        return this.cnoocMobileService.getEncodeUrl(url);
    }
}

