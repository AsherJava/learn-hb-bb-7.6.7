/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  org.apache.shiro.authz.annotation.Logical
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.service.OrgAuthService;
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
@RequestMapping(value={"/org/auth"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgAuthController {
    @Autowired
    private OrgAuthService orgAuthService;

    @PostMapping(value={"/mgr/detail/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listDetail(@RequestBody OrgAuthDTO orgAuthDTO) {
        return MonoVO.just(this.orgAuthService.listDetail(orgAuthDTO));
    }

    @PostMapping(value={"/mgr/detail/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateDetail(@RequestBody List<OrgAuthDO> orgAuths) {
        return MonoVO.just((Object)this.orgAuthService.updateDetail(orgAuths));
    }

    @PostMapping(value={"/mgr/rule/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listRule(@RequestBody OrgAuthDTO orgAuthDTO) {
        return MonoVO.just(this.orgAuthService.listRule(orgAuthDTO));
    }

    @PostMapping(value={"/path/find"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object findPath(@RequestBody OrgAuthDTO param) {
        return MonoVO.just((Object)this.orgAuthService.findPath(param));
    }

    @PostMapping(value={"/mgr/rule/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateRule(@RequestBody List<OrgAuthDO> orgAuthList) {
        return MonoVO.just((Object)this.orgAuthService.updateRule(orgAuthList));
    }

    @PostMapping(value={"/category/exist"})
    Object existCategoryAuth(@RequestBody OrgDTO param) {
        return MonoVO.just((Object)this.orgAuthService.existCategoryAuth(param));
    }

    @PostMapping(value={"/data/find"})
    Object findAuth(@RequestBody OrgAuthFindDTO orgAuthFindDTO) {
        return MonoVO.just(this.orgAuthService.listAuthOrg(orgAuthFindDTO.getUserDO(), orgAuthFindDTO.getOrgDTO()));
    }

    @PostMapping(value={"/data/exist"})
    Object existDataAuth(@RequestBody OrgAuthFindDTO orgAuthFindDTO) {
        return MonoVO.just((Object)this.orgAuthService.existDataAuth(orgAuthFindDTO));
    }
}

