/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  io.jsonwebtoken.Claims
 */
package com.jiuqi.va.openapi.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import io.jsonwebtoken.Claims;
import java.util.List;

public interface VaOpenApiService {
    public R registerApi(OpenApiRegisterDO var1);

    public R removeApi(OpenApiRegisterDO var1);

    public TreeVO<OpenApiRegisterDO> treeApi(OpenApiRegisterDO var1);

    public OpenApiAuthDO getAuth(OpenApiAuthDTO var1);

    public List<OpenApiAuthDO> listAuth(OpenApiAuthDTO var1);

    public int countAuth(OpenApiAuthDTO var1);

    public R addAuth(OpenApiAuthDO var1);

    public R updateAuth(OpenApiAuthDO var1);

    public R removeAuth(List<OpenApiAuthDO> var1);

    public R stopAuth(List<OpenApiAuthDO> var1);

    public String createJWT(OpenApiAuthDO var1);

    public Claims parseJWT(String var1);
}

