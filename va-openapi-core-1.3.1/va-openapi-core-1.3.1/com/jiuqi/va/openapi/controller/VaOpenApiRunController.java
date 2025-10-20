/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO
 *  com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.openapi.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO;
import com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO;
import com.jiuqi.va.openapi.service.impl.help.VaOpenApiRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/openApi/anon"})
public class VaOpenApiRunController {
    @Autowired
    private VaOpenApiRunService openApiRunService;

    @PostMapping(value={"/token/get"})
    R getToken(@RequestBody OpenApiGetTokenDTO openApi) {
        return this.openApiRunService.getToken(openApi);
    }

    @PostMapping(value={"/token/validate"})
    R validateToken(@RequestBody OpenApiValidateTokenDTO openApi) {
        return this.openApiRunService.validateToken(openApi);
    }
}

