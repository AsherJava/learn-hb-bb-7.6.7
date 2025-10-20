/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.calculate.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GCConversionWorkPaperClient {
    @PostMapping(value={"/api/gcreport/v1/conversion/gcConversionWorkPaperAction"})
    public BusinessResponseEntity<Object> gcConversionWorkPaperAction(@RequestBody(required=false) String var1) throws Exception;
}

