/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.common.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcServiceController {
    @Autowired
    private ServiceConfigProperties prop;
    final String API_BASE_PATH = "/api/datacenter/v1/service";

    @GetMapping(value={"/api/datacenter/v1/service/getServiceName"})
    public BusinessResponseEntity<String> getServiceName() {
        if ("DC".equals(this.prop.getServiceName())) {
            return BusinessResponseEntity.ok((Object)"\u4e00\u672c\u8d26");
        }
        return BusinessResponseEntity.ok((Object)"BDE");
    }
}

