/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  org.apache.shiro.authz.annotation.Logical
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.organization.domain.OrgActionAuthDO;
import com.jiuqi.va.organization.domain.OrgActionAuthDTO;
import com.jiuqi.va.organization.service.OrgActionAuthService;
import java.util.List;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/org/action/mgr"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgActionAuthController {
    @Autowired
    private OrgActionAuthService orgActionAuthService;

    @PostMapping(value={"/detail/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listDetail(@RequestBody OrgActionAuthDTO param) {
        return MonoVO.just(this.orgActionAuthService.listDetail(param));
    }

    @PostMapping(value={"/detail/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateDetail(@RequestBody List<OrgActionAuthDO> datas) {
        return MonoVO.just((Object)this.orgActionAuthService.updateDetail(datas));
    }

    @PostMapping(value={"/getUserAuth"})
    Object getUserAuth(@RequestBody OrgActionAuthDTO param) {
        return MonoVO.just(this.orgActionAuthService.getUserAuth(param));
    }
}

