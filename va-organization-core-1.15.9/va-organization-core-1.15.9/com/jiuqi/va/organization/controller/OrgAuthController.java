/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  org.apache.shiro.authz.annotation.Logical
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.domain.OrgAuthVO;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgAuthController")
@RequestMapping(value={"/org/auth/binary"})
public class OrgAuthController {
    @Autowired
    private OrgAuthService orgAuthService;

    @PostMapping(value={"/mgr/detail/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listDetail(@RequestBody byte[] binaryData) {
        OrgAuthDTO param = (OrgAuthDTO)JSONUtil.parseObject((byte[])binaryData, OrgAuthDTO.class);
        PageVO<OrgAuthVO> rs = this.orgAuthService.listDetail(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/mgr/detail/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateDetail(@RequestBody byte[] binaryData) {
        List orgAuths = JSONUtil.parseArray((byte[])binaryData, OrgAuthDO.class);
        R rs = this.orgAuthService.updateDetail(orgAuths);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/mgr/rule/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listRule(@RequestBody byte[] binaryData) {
        OrgAuthDTO param = (OrgAuthDTO)JSONUtil.parseObject((byte[])binaryData, OrgAuthDTO.class);
        PageVO<OrgAuthVO> rs = this.orgAuthService.listRule(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/path/find"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object findPath(@RequestBody byte[] binaryData) {
        OrgAuthDTO param = (OrgAuthDTO)JSONUtil.parseObject((byte[])binaryData, OrgAuthDTO.class);
        R rs = this.orgAuthService.findPath(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/mgr/rule/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateRule(@RequestBody byte[] binaryData) {
        List orgAuths = JSONUtil.parseArray((byte[])binaryData, OrgAuthDO.class);
        R rs = this.orgAuthService.updateRule(orgAuths);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/category/exist"})
    Object existCategoryAuth(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgAuthService.existCategoryAuth(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/data/find"})
    Object findAuth(@RequestBody byte[] binaryData) {
        OrgAuthFindDTO param = (OrgAuthFindDTO)JSONUtil.parseObject((byte[])binaryData, OrgAuthFindDTO.class);
        Set<String> rs = this.orgAuthService.listAuthOrg(param.getUserDO(), param.getOrgDTO());
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/data/exist"})
    Object existDataAuth(@RequestBody byte[] binaryData) {
        OrgAuthFindDTO param = (OrgAuthFindDTO)JSONUtil.parseObject((byte[])binaryData, OrgAuthFindDTO.class);
        R rs = this.orgAuthService.existDataAuth(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

