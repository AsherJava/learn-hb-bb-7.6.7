/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
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
import com.jiuqi.va.organization.domain.OrgActionAuthDO;
import com.jiuqi.va.organization.domain.OrgActionAuthDTO;
import com.jiuqi.va.organization.domain.OrgActionAuthVO;
import com.jiuqi.va.organization.service.OrgActionAuthService;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgActionAuthController")
@RequestMapping(value={"/org/action/binary"})
public class OrgActionAuthController {
    @Autowired
    private OrgActionAuthService orgActionAuthService;

    @PostMapping(value={"/mgr/detail/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object listDetail(@RequestBody byte[] binaryData) {
        OrgActionAuthDTO param = (OrgActionAuthDTO)JSONUtil.parseObject((byte[])binaryData, OrgActionAuthDTO.class);
        PageVO<OrgActionAuthVO> rs = this.orgActionAuthService.listDetail(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/mgr/detail/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr", "vaOrg:authorized:mgr"}, logical=Logical.OR)
    Object updateDetail(@RequestBody byte[] binaryData) {
        List datas = JSONUtil.parseArray((byte[])binaryData, OrgActionAuthDO.class);
        R rs = this.orgActionAuthService.updateDetail(datas);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/getUserAuth"})
    Object getUserAuth(@RequestBody byte[] binaryData) {
        OrgActionAuthDTO param = (OrgActionAuthDTO)JSONUtil.parseObject((byte[])binaryData, OrgActionAuthDTO.class);
        Set<String> rs = this.orgActionAuthService.getUserAuth(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }
}

