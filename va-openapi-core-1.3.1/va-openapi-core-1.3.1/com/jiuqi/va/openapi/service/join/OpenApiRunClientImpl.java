/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO
 *  com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO
 *  com.jiuqi.va.feign.client.OpenApiRunClient
 */
package com.jiuqi.va.openapi.service.join;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO;
import com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO;
import com.jiuqi.va.feign.client.OpenApiRunClient;
import com.jiuqi.va.openapi.service.impl.help.VaOpenApiRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class OpenApiRunClientImpl
implements OpenApiRunClient {
    @Autowired
    private VaOpenApiRunService openApiRunService;

    public R getToken(OpenApiGetTokenDTO openApi) {
        return this.openApiRunService.getToken(openApi);
    }

    public R validateToken(OpenApiValidateTokenDTO openApi) {
        return this.openApiRunService.validateToken(openApi);
    }
}

