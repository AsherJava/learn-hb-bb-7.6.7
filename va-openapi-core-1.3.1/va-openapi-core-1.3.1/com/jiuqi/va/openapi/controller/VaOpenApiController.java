/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.openapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import com.jiuqi.va.openapi.service.VaOpenApiService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/openApi"})
public class VaOpenApiController {
    @Autowired
    private JoinTemplate joinTemplate;
    @Autowired
    private VaOpenApiService openApiService;

    @PostMapping(value={"/register/sync"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    R sync(@RequestBody ObjectNode param) {
        try {
            JsonNode type = param.get("type");
            if (type != null && type.asText().equals("1")) {
                this.openApiService.removeApi(new OpenApiRegisterDO());
            }
            this.joinTemplate.send("OPENAPI_COLLECT", ShiroUtil.getTenantName());
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        return R.ok((String)"\u64cd\u4f5c\u6210\u529f\uff0c\u8bf7\u7a0d\u540e\u5237\u65b0\u67e5\u770b");
    }

    @PostMapping(value={"/register/tree"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    TreeVO<OpenApiRegisterDO> tree(@RequestBody OpenApiRegisterDO param) {
        return this.openApiService.treeApi(param);
    }

    @PostMapping(value={"/auth/list"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    PageVO<OpenApiAuthDO> list(@RequestBody OpenApiAuthDTO param) {
        param.setPagination(false);
        int total = this.openApiService.countAuth(param);
        if (total == 0) {
            return new PageVO(true);
        }
        param.setPagination(true);
        List<OpenApiAuthDO> dataList = this.openApiService.listAuth(param);
        return new PageVO(dataList, total);
    }

    @PostMapping(value={"/auth/add"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    R add(@RequestBody OpenApiAuthDO openApiDO) {
        return this.openApiService.addAuth(openApiDO);
    }

    @PostMapping(value={"/auth/update"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    R update(@RequestBody OpenApiAuthDO openApiDO) {
        return this.openApiService.updateAuth(openApiDO);
    }

    @PostMapping(value={"/auth/remove"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    R remove(@RequestBody List<OpenApiAuthDO> objs) {
        return this.openApiService.removeAuth(objs);
    }

    @PostMapping(value={"/auth/stop"})
    @RequiresPermissions(value={"vaOpenApi:auth:mgr"})
    R stop(@RequestBody List<OpenApiAuthDO> objs) {
        return this.openApiService.stopAuth(objs);
    }
}

