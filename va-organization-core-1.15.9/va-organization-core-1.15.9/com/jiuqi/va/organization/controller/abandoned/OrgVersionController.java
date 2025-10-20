/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/org/version"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgVersionController {
    @Autowired
    private OrgVersionService orgVersionService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody OrgVersionDTO param) {
        return MonoVO.just((Object)this.orgVersionService.get(param));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody OrgVersionDTO param) {
        return MonoVO.just(this.orgVersionService.list(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object add(@RequestBody OrgVersionDO orgVersionDO) {
        return MonoVO.just((Object)this.orgVersionService.add(orgVersionDO));
    }

    @PostMapping(value={"/split"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object split(@RequestBody OrgVersionDO orgVersionDO) {
        return MonoVO.just((Object)this.orgVersionService.split(orgVersionDO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object update(@RequestBody OrgVersionDO orgVersionDO) {
        return MonoVO.just((Object)this.orgVersionService.update(orgVersionDO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object remove(@RequestBody OrgVersionDO orgVersionDO) {
        return MonoVO.just((Object)this.orgVersionService.remove(orgVersionDO));
    }

    @PostMapping(value={"/status/change"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object changeStatus(@RequestBody List<OrgVersionDO> dataList) {
        return MonoVO.just((Object)this.orgVersionService.changeStatus(dataList));
    }

    @PostMapping(value={"/cache/sync"})
    @RequiresPermissions(value={"vaOrg:version:mgr"})
    Object syncCache(@RequestBody OrgVersionDO orgVersionDO) {
        return MonoVO.just((Object)this.orgVersionService.syncCache(orgVersionDO));
    }
}

