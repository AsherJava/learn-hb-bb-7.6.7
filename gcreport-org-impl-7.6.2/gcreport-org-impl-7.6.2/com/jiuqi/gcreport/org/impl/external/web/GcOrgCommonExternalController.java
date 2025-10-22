/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiResponse
 *  io.swagger.annotations.ApiResponses
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.external.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.impl.external.service.GcOrgExternalService;
import com.jiuqi.gcreport.org.impl.external.vo.GcOrgExternalApiParam;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u5408\u5e76\u7ec4\u7ec7\u673a\u6784API"})
public class GcOrgCommonExternalController {
    private static final String API_PATH = "/api/gcreport/v1/orgExternal/";
    @Autowired
    private GcOrgExternalService externalService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/api/gcreport/v1/orgExternal/operate"})
    @ApiOperation(value="\u63a5\u53e3\u63cf\u8ff0\uff1a\u64cd\u4f5c\u7ec4\u7ec7\u673a\u6784")
    @ApiResponses(value={@ApiResponse(code=200, message="\u64cd\u4f5c\u6210\u529f")})
    public BusinessResponseEntity<Object> operate(@RequestBody GcOrgExternalApiParam param) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setMgrFlag("super");
        ShiroUtil.bindUser((UserLoginDTO)userLoginDTO);
        ShiroUtil.bindTenantName((String)"__default_tenant__");
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)"__default_tenant__");
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        BusinessResponseEntity<Object> operate = null;
        try {
            operate = this.externalService.operate(param);
        }
        catch (Exception e) {
            BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.error((Throwable)e);
            return businessResponseEntity;
        }
        finally {
            ShiroUtil.unbindUser();
            ShiroUtil.unbindTenantName();
            ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            ThreadContext.remove((Object)"NONE_AUTH_KEY");
        }
        return operate;
    }
}

