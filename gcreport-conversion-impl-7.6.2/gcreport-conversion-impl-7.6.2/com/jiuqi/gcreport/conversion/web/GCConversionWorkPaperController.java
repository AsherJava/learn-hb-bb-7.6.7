/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.conversion.api.GCConversionWorkPaperClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.conversion.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.api.GCConversionWorkPaperClient;
import com.jiuqi.gcreport.conversion.service.GCConversionWorkPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GCConversionWorkPaperController
implements GCConversionWorkPaperClient {
    @Autowired
    private GCConversionWorkPaperService gcConversionWorkPaperService;

    public BusinessResponseEntity<Object> gcConversionWorkPaperAction(String actionParamJson) throws Exception {
        return BusinessResponseEntity.ok((Object)this.gcConversionWorkPaperService.conversionWorkPaper(actionParamJson).toString());
    }
}

