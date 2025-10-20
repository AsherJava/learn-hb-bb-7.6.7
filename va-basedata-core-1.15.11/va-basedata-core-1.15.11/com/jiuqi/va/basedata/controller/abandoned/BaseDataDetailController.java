/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.MonoVO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.MonoVO;
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
@RequestMapping(value={"/baseData/detail"})
public class BaseDataDetailController {
    @Autowired
    private BaseDataDetailService baseDataDetailService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody BaseDataDetailDO param) {
        return MonoVO.just(this.baseDataDetailService.get(param));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody BaseDataDetailDO param) {
        return MonoVO.just(this.baseDataDetailService.list(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody BaseDataDetailDO param) {
        return MonoVO.just((Object)this.baseDataDetailService.add(param));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object delete(@RequestBody BaseDataDetailDO param) {
        return MonoVO.just((Object)this.baseDataDetailService.delete(param));
    }
}

