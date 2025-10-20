/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
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
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgVersionController")
@RequestMapping(value={"/org/version/binary"})
public class OrgVersionController {
    @Autowired
    private OrgVersionService orgVersionService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        OrgVersionDTO param = (OrgVersionDTO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDTO.class);
        OrgVersionDO rs = this.orgVersionService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        OrgVersionDTO param = (OrgVersionDTO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDTO.class);
        PageVO<OrgVersionDO> rs = this.orgVersionService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        OrgVersionDO param = (OrgVersionDO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/split"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object split(@RequestBody byte[] binaryData) {
        OrgVersionDO param = (OrgVersionDO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.split(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        OrgVersionDO param = (OrgVersionDO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        OrgVersionDO param = (OrgVersionDO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/status/change"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object changeStatus(@RequestBody byte[] binaryData) {
        List dataList = JSONUtil.parseArray((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.changeStatus(dataList);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/cache/sync"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object syncCache(@RequestBody byte[] binaryData) {
        OrgVersionDO param = (OrgVersionDO)JSONUtil.parseObject((byte[])binaryData, OrgVersionDO.class);
        R rs = this.orgVersionService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

