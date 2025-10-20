/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.conversion.api.ConversionClient
 *  com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.conversion.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.api.ConversionClient;
import com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO;
import com.jiuqi.gcreport.conversion.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ConversionController
implements ConversionClient {
    @Autowired
    private ConversionService conversionService;

    public BusinessResponseEntity<ConversionInitEnvDTO> getConversionInitEnv() {
        ConversionInitEnvDTO dto = this.conversionService.getConversionInitEnv();
        return BusinessResponseEntity.ok((Object)dto);
    }
}

