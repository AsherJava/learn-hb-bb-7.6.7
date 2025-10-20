/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.common.MonoVO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.domain.common.MonoVO;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/version"})
public class BaseDataVersionController {
    @Autowired
    private BaseDataVersionService baseDataVersionService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody BaseDataVersionDTO param) {
        return MonoVO.just((Object)this.baseDataVersionService.get(param));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody BaseDataVersionDTO param) {
        return MonoVO.just(this.baseDataVersionService.list(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody BaseDataVersionDO baseDataVersionDO) {
        return MonoVO.just((Object)this.baseDataVersionService.add(baseDataVersionDO));
    }

    @PostMapping(value={"/split"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object split(@RequestBody BaseDataVersionDO baseDataVersionDO) {
        return MonoVO.just((Object)this.baseDataVersionService.split(baseDataVersionDO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object update(@RequestBody BaseDataVersionDO baseDataVersionDO) {
        return MonoVO.just((Object)this.baseDataVersionService.update(baseDataVersionDO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object remove(@RequestBody BaseDataVersionDO baseDataVersionDO) {
        return MonoVO.just((Object)this.baseDataVersionService.remove(baseDataVersionDO));
    }

    @PostMapping(value={"/status/change"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object changeStatus(@RequestBody List<BaseDataVersionDO> dataList) {
        return MonoVO.just((Object)this.baseDataVersionService.changeStatus(dataList));
    }

    @PostMapping(value={"/cache/sync"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object syncCache(@RequestBody BaseDataVersionDO baseDataVersionDO) {
        return MonoVO.just((Object)this.baseDataVersionService.syncCache(baseDataVersionDO));
    }
}

