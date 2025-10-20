/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 */
package com.jiuqi.va.openapi.mq;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.openapi.service.VaOpenApiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaOpenApiRegisterJoinListener
implements JoinListener {
    @Autowired
    private VaOpenApiService openApiService;

    public String getJoinName() {
        return "OPENAPI_REGISTER";
    }

    public ReplyTo onMessage(String message) {
        List registerDOs = JSONUtil.parseArray((String)message, OpenApiRegisterDO.class);
        if (registerDOs != null) {
            for (OpenApiRegisterDO registerDO : registerDOs) {
                this.openApiService.registerApi(registerDO);
            }
        }
        return new ReplyTo(ReplyStatus.SUCESS);
    }
}

