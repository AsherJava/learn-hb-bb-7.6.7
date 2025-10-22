/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgToolApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcOrgToolClient {
    public static final String API_PATH = "/api/gcreport/v1/orgtool";

    @GetMapping(value={"/api/gcreport/v1/orgtool/mode"})
    public String getOperateMode();

    @PostMapping(value={"/api/gcreport/v1/orgtool/initCache"})
    public BusinessResponseEntity<String> initOrgCache(@RequestBody GcOrgToolApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgtool/orgcache/refresh"})
    public BusinessResponseEntity<String> refreshOrgCache(@RequestBody GcOrgToolApiParam var1);
}

